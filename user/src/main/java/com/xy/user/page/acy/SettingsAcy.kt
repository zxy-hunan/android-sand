package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.util.clickDebounce
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.databinding.ActivitySettingsBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file SettingsAcy
 * @author zxy
 * @date 2024/11/28 15:38
 * @brief settings
 */
@Route(path = ARouterConfig.User.SettingsAct.PATH)
class SettingsAcy :MviAcy<ActivitySettingsBinding, UserVm, UserIntent>(UserVm::class.java){
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        binding.stvLogin.clickDebounce {
            ARouterConfig.User.LoginAct.push()
        }
    }

    override fun observe() {
    }

    override fun onListener() {
    }
}