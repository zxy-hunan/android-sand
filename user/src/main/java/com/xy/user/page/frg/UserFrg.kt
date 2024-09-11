package com.xy.user.page.frg

import android.content.Intent
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.TAG
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.user.R
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.databinding.FragmentUserBinding
import com.xy.user.databinding.ItemSettingBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.acy.LoginAcy
import com.xy.user.page.frg.viewhelper.rvBase
import com.xy.user.page.frg.viewhelper.rvSetting
import com.xy.user.page.frg.viewhelper.rvTop
import com.xy.user.vm.UserVm

/**
 * @file UserFrg
 * @author zxy
 * @date 2024/7/22 14:29
 * @brief user
 */
class UserFrg : MviFragment<FragmentUserBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initView() {

        immersionBar {

            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }

        binding.rvSetting.rvSetting()
        binding.rvMiddle.rvBase()
        binding.rvHigh.rvBase()
        binding.rvTop.rvTop()
    }



    override fun lazyLoad() {
        binding.rvSetting.models = viewModel.getSettingTagList()
        binding.rvMiddle.models = viewModel.getMiddleTagList()
        binding.rvHigh.models = viewModel.getHighTagList()
        binding.rvTop.models = viewModel.getTopTagList()

    }

    override fun observe() {
    }

    override fun onListens() {
    }


    private fun gotoLogin() {
        startActivity(Intent(mActivity, LoginAcy::class.java))
//        ARouterConfig.User.LoginAct.push()
    }
}