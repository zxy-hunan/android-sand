package com.xy.user.page.acy

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.longan.intentExtras
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.databinding.AboutActivityBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file PersonalAcy
 * @author zxy
 * @date 2025/6/12 9:51
 * @brief 关于
 */
@Route(path = ARouterConfig.User.AboutAcy.PATH)
class AboutAcy : MviAcy<AboutActivityBinding, UserVm, UserIntent>(UserVm::class.java){

    private lateinit var mAgentWeb: AgentWeb

    private val URL by intentExtras(ARouterConfig.User.AboutAcy.URL, "")

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }
//nav
        binding.tbTopBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        initWebView()
    }

    override fun observe() {
    }

    override fun onListener() {
    }

    private fun initWebView() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.container, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(mWebChromeClient)
            .setWebViewClient(mWebViewClient)
//            .setMainFrameErrorView(
//                R.layout.loadingstate_common_error,
//                R.id.loading_state_common_error_retry_btn
//            )
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .go(URL)

        mAgentWeb.agentWebSettings.webSettings.mediaPlaybackRequiresUserGesture = false
        mAgentWeb.agentWebSettings.webSettings.setSupportZoom(false)
        mAgentWeb.agentWebSettings.webSettings.loadWithOverviewMode = true
        mAgentWeb.agentWebSettings.webSettings.builtInZoomControls = true
        mAgentWeb.agentWebSettings.webSettings.displayZoomControls = false
        mAgentWeb.agentWebSettings.webSettings.allowFileAccess = true
        mAgentWeb.agentWebSettings.webSettings.textZoom = 100
        // 允许加载本地文件html  file协议, 这可能会造成不安全 , 建议重写关闭
        mAgentWeb.agentWebSettings.webSettings.allowFileAccess = true
        // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
        mAgentWeb.agentWebSettings.webSettings.allowFileAccessFromFileURLs = true
        // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
        mAgentWeb.agentWebSettings.webSettings.allowUniversalAccessFromFileURLs = true

    }


    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }
    }


    private val mWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?,
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        @SuppressLint("WebViewClientOnReceivedSslError")
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
        }

        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
        }

    }
}