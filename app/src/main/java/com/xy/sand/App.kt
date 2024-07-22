package com.xy.sand

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
        BASEURL = BuildConfig.BASE_URL_PRODUCTION
    }

}