package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.models
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.util.MmkvRepository
import com.xy.common.util.clickDebounce
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.R
import com.xy.user.databinding.ActivitySettingsBinding
import com.xy.user.databinding.SettingActivityBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.frg.viewhelper.rvSetting
import com.xy.user.vm.UserVm

/**
 * @file SettingsAcy
 * @author zxy
 * @date 2024/11/28 15:38
 * @brief settings
 */
@Route(path = ARouterConfig.User.SettingsAct.PATH)
class SettingsAcy :MviAcy<SettingActivityBinding, UserVm, UserIntent>(UserVm::class.java){
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {

        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }

        /*  binding.stvLogin.clickDebounce {
              ARouterConfig.User.LoginAct.push()
          }

          binding.tblNav.setTitle("Settings")
          binding.rvSetting.rvSetting()

          binding.rvSetting.models = viewModel.getSettingTagList()*/
        binding.sbSettingExit.setLeftText(if(MmkvRepository.loginToken.isEmpty()) "去登录" else "退出登录")
        binding.sbSettingExit.clickDebounce {
            if(MmkvRepository.loginToken.isEmpty()) {
                ARouterConfig.User.LoginAct.push()
            } else {
                ARouterConfig.User.LoginAct.push()
            }
        }
    }

    override fun observe() {
    }

    override fun onListener() {
    }
}