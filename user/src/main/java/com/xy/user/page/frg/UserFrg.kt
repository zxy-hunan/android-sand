package com.xy.user.page.frg

import com.xy.common.arouter.user.ARouterConfig
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.user.databinding.FragmentUserBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file UserFrg
 * @author zxy
 * @date 2024/7/22 14:29
 * @brief user
 */
class UserFrg : MviFragment<FragmentUserBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initView() {
        binding.tvUser.setOnClickListener {
            gotoLogin()
        }
    }



    override fun lazyLoad() {
    }

    override fun observe() {
    }

    override fun onListens() {
    }


    private fun gotoLogin() {
//        startActivity(Intent(mActivity, LoginAcy::class.java))
        ARouterConfig.User.LoginAct.push()
    }
}