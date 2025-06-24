package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
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
    }

    override fun onListener() {
    }
}