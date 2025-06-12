package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.databinding.PersonalDataActivityBinding
import com.xy.user.databinding.SettingActivityBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file PersonalAcy
 * @author zxy
 * @date 2025/6/12 9:51
 * @brief 个人中心
 */
@Route(path = ARouterConfig.User.PersonalAcy.PATH)
class PersonalAcy : MviAcy<PersonalDataActivityBinding, UserVm, UserIntent>(UserVm::class.java){
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