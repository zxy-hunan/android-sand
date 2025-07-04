package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.models
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.dialog.OperDialog
import com.xy.common.util.MmkvRepository
import com.xy.common.util.clickDebounce
import com.xy.common.widget.view.SwitchButton
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
class SettingsAcy : MviAcy<SettingActivityBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {

        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }
        //autologin
        binding.sbSettingSwitch.setChecked(MmkvRepository.autoLogin)
        binding.sbSettingSwitch.setOnCheckedChangeListener(object : SwitchButton.OnCheckedChangeListener {
            override fun onCheckedChanged(button: SwitchButton, checked: Boolean) {
                MmkvRepository.autoLogin = checked
            }

        })
        //login
        binding.sbSettingExit.setLeftText(if (MmkvRepository.loginToken.isEmpty()) "login" else "out login")
        binding.sbSettingExit.clickDebounce {
            if (MmkvRepository.loginToken.isEmpty()) {
                ARouterConfig.User.LoginAct.push()
            } else {
                MmkvRepository.resetToken()
            }
        }
        //nav
        binding.tbTopBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })
        //password
        binding.sbSettingPassword.clickDebounce {
            OperDialog.showdevelopeingDialog()
        }
        //agreement
        binding.sbSettingAgreement.clickDebounce {
            OperDialog.showdevelopeingDialog()
        }
        //cache
        binding.sbSettingCache.clickDebounce {
            OperDialog.showdevelopeingDialog()
        }

        binding.sbSettingAbout.clickDebounce {
            ARouterConfig.User.AboutAcy.push("http://zhiniao.xyz")
        }

    }

    override fun observe() {
    }

    override fun onListener() {
    }
}