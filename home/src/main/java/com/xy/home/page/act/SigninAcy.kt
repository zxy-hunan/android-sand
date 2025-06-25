package com.xy.home.page.act

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.xy.home.databinding.SearchActivityBinding
import com.xy.home.databinding.SigninActivityBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file SearchAcy
 * @author zxy
 * @date 2025/6/25 10:40
 * @brief 签到
 */
@Route(path = ARouterConfig.Home.SigninAcy.PATH)
class SigninAcy : MviAcy<SigninActivityBinding, MainVm, MainIntent>(MainVm::class.java) {
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

    }

    override fun observe() {
        intentCallBack { it ->
            when (it) {
                is MainIntent.ArticleList -> {


                }

                else -> {

                }
            }
        }
    }

    override fun onListener() {
    }
}