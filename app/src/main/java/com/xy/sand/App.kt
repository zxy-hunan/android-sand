package com.xy.sand

import GlobalHeaderInterceptor
import GsonConverter
import SerializationConverter
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.drake.net.NetConfig
import com.drake.net.cookie.PersistentCookieJar
import com.drake.net.interceptor.LogRecordInterceptor
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDebug
import com.drake.net.okhttp.setRequestInterceptor
import com.drake.statelayout.StateConfig
import com.king.image.imageviewer.loader.GlideImageLoader
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xy.mviframework.base.BaseApp
import com.xy.mviframework.network.tool.SHOW_LOG
import okhttp3.Cache
import okhttp3.OkHttpClient
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory
import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager
import java.io.InputStream
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * @file App
 * @author zxy
 * @date 2024/7/19 8:14
 * @brief application
 * https://api.apiopen.top/swagger/index.html#/%E5%BC%80%E6%94%BE%E6%8E%A5%E5%8F%A3/get_api_getMiniVideo
 *
 * https://zhuanlan.zhihu.com/p/339180011
 * https://zhuanlan.zhihu.com/p/272164713
 * https://www.mockplus.cn/example/rp/100009/?hmsr=zhihu
 */
class App :BaseApp() {

    override fun onCreate() {
        super.onCreate()
        handleSSLHandshake()
        initNetWork()
        initArouter()
        smartRefreshInit()
        initStateConfig()
        initMojito()
        initVideoPlayer()
        SHOW_LOG = true
    }

    private fun initMojito() {
//        Mojito.initialize(GlideImageLoader.with(applicationContext), SketchImageLoadFactory())
    }

    private fun initStateConfig() {
        StateConfig.apply {
            emptyLayout= com.xy.common.R.layout.layout_empty
            errorLayout= com.xy.common.R.layout.layout_error
            loadingLayout= com.xy.common.R.layout.layout_loading
        }

        Glide.get(this).registry.replace<GlideUrl, InputStream>(GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(getNoCheckOkHttpClient()!!))
    }

    private fun initNetWork() {
        BASEURL = BuildConfig.BASE_URL_PRODUCTION
        initNet()
    }

    private fun initNet() {

        NetConfig.initialize("https://peapix.com/bing/", this) {

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // 本框架支持Http缓存协议和强制缓存模式
            cache(Cache(cacheDir, 1024 * 1024 * 128)) // 缓存设置, 当超过maxSize最大值会根据最近最少使用算法清除缓存来限制缓存大小

            // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
            setDebug(BuildConfig.DEBUG)

            // AndroidStudio OkHttp Profiler 插件输出网络日志
            addInterceptor(LogRecordInterceptor(BuildConfig.DEBUG))

            // 添加持久化Cookie管理
            cookieJar(PersistentCookieJar(this@App))

            // 仅开发模式启用通知栏监听网络日志, 该框架存在下载大文件时内存溢出崩溃请等待官方修复 https://github.com/ChuckerTeam/chucker/issues/1068
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    ChuckerInterceptor.Builder(this@App)
                        .collector(ChuckerCollector(this@App))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
            }

            // 添加请求拦截器, 可配置全局/动态参数
            setRequestInterceptor(GlobalHeaderInterceptor())

            // 数据转换器
            setConverter(GsonConverter())

        }

    }


    private fun initArouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }


    private fun initVideoPlayer() {
        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
            //使用使用IjkPlayer解码
            .setPlayerFactory(IjkPlayerFactory.create())
//            //使用ExoPlayer解码
//            .setPlayerFactory(ExoMediaPlayerFactory.create())
            //使用MediaPlayer解码
            .setPlayerFactory(AndroidMediaPlayerFactory.create())
            .build());
    }

    private fun smartRefreshInit() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(this)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(this) }
    }

    private fun handleSSLHandshake() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls<X509Certificate>(0)
                }

                override fun checkClientTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
                override fun checkServerTrusted(certs: Array<X509Certificate?>?, authType: String?) {}
            })
            val sc = SSLContext.getInstance("TLS")
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
        } catch (ignored: Exception) {
        }
    }


    fun getNoCheckOkHttpClient(): OkHttpClient? {
        val ssl: SSLSocketFactory? = getNoCheckSSLSocketFactory()
        val trustManager: X509TrustManager? = getTrustManager()
        return ssl?.let {
            trustManager?.let { it1 ->
                OkHttpClient.Builder()
                    .connectTimeout(TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS)
                    .readTimeout(TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS)
                    .writeTimeout(TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS)
                    .sslSocketFactory(it, it1)
                    .hostnameVerifier { hostname, session -> true }
                    .retryOnConnectionFailure(true)
                    .build()
            }
        }
    }

    fun getNoCheckSSLSocketFactory(): SSLSocketFactory? {
        return try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(getTrustManager()), SecureRandom())
            sslContext.socketFactory
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }


    /**
     * 获得信任管理器TrustManager,不做任何校验
     *
     * @return X509TrustManager
     */
    fun getTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(serverX509Certificates: Array<X509Certificate>, s: String) {}

            /**
             * 只支持正序或者逆序存放的证书链，如果证书链顺序打乱的将不支持 我们以下认定x509Certificates数组里从0-end如果是设备证书到ca root证书是正序的
             * 反之是倒序的
             */
            override fun checkServerTrusted(x509Certificates: Array<X509Certificate>, s: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return emptyArray()
            }
        }
    }

}