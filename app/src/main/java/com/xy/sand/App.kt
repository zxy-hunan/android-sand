package com.xy.sand

import com.alibaba.android.arouter.launcher.ARouter
import com.xy.mviframework.base.BaseApp

/**
 * @file App
 * @author zxy
 * @date 2024/7/19 8:14
 * @brief application
 */
class App :BaseApp() {

    override fun onCreate() {
        super.onCreate()
        initNetWork()
        initArouter()
    }

    private fun initNetWork() {
        BASEURL = BuildConfig.BASE_URL_PRODUCTION
    }

    private fun initArouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

}