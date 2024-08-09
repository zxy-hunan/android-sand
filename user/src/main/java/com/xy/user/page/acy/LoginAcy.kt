package com.xy.user.page.acy

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.databinding.ActivityLoginBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file LoginAcy
 * @author zxy
 * @date 2024/7/23 10:08
 * @brief login
 */
@Route(path = ARouterConfig.User.LoginAct.PATH)
class LoginAcy : MviAcy<ActivityLoginBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar(binding.view)
    }

    override fun observe() {
    }

    override fun onListener() {
    }

    fun immersionBar(statusView: View){

        immersionBar {
            statusBarColor(com.google.android.material.R.color.m3_ref_palette_white)

//            statusBarAlpha(0.1f)
            statusBarDarkFont(true)

            titleBarMarginTop(statusView)
        }
    }
}