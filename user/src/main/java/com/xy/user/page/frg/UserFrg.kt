package com.xy.user.page.frg

import android.content.Intent
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.TAG
import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.user.R
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.databinding.FragmentUserBinding
import com.xy.user.databinding.ItemSettingBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.acy.LoginAcy
import com.xy.user.vm.UserVm

/**
 * @file UserFrg
 * @author zxy
 * @date 2024/7/22 14:29
 * @brief user
 */
class UserFrg : MviFragment<FragmentUserBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initView() {

        binding.rvSetting.grid(3).setup {
            addType<UserTagModel>(R.layout.item_setting)
            onBind {
             val binding =  getBinding<ItemSettingBinding>()
                val item = getModel<UserTagModel>()
                binding.tvName.text = item.tagName
            }
            onClick(R.id.ll_item,R.id.ll_item) {
                val item = getModel<UserTagModel>()
                when (item.tag) {
                    UserTag.SETTING -> {
                        gotoLogin()
                    }

                    else -> {}
                }
            }
        }
    }



    override fun lazyLoad() {
        binding.rvSetting.models = viewModel.getSettingTagList()
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