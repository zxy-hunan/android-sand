package com.xy.home.page.act

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.home.databinding.SearchActivityBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file SearchAcy
 * @author zxy
 * @date 2025/6/12 15:06
 * @brief search
 */
@Route(path = ARouterConfig.Home.SearchAcy.PATH)
class SearchAcy : MviAcy<SearchActivityBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }
    }

    override fun observe() {
    }

    override fun onListener() {
    }
}