package com.xy.sand

import com.alibaba.android.arouter.launcher.ARouter
import com.drake.statelayout.StateConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xy.mviframework.base.BaseApp
import com.xy.mviframework.network.tool.SHOW_LOG

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
        smartRefreshInit()
        initStateConfig()
        SHOW_LOG = true
    }

    private fun initStateConfig() {
        StateConfig.apply {
            emptyLayout= com.xy.common.R.layout.layout_empty
            errorLayout= com.xy.common.R.layout.layout_error
            loadingLayout= com.xy.common.R.layout.layout_loading
        }
    }

    private fun initNetWork() {
        BASEURL = BuildConfig.BASE_URL_PRODUCTION
    }

    private fun initArouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    private fun smartRefreshInit() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(this)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(this) }
    }


}