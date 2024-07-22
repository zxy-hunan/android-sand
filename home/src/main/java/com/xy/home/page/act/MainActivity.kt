package com.xy.home.page.act

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.FragmentUtils
import com.xy.home.R
import com.xy.home.databinding.ActivityMainBinding
import com.xy.home.intent.MainIntent
import com.xy.home.page.frg.HomeCommunityFrg
import com.xy.home.page.frg.HomeFrg
import com.xy.home.vm.MainVm
import com.xy.im.page.frg.NotiFrg
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.page.frg.UserFrg

/**
 * @file MainActivity
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainActivity : MviAcy<ActivityMainBinding, MainVm, MainIntent>(MainVm::class.java) {
    var homeFrg: Fragment? = null
    var homeCommunityFrg: Fragment? = null
    var notiFrg: Fragment? = null
    var userFrg: Fragment? = null

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.articleList()
    }

    override fun initView() {
        homeFrg = HomeFrg()
        FragmentUtils.add(supportFragmentManager, homeFrg!!, R.id.ll_content)
        navSelectedAction()

    }

    override fun observe() {
    }


    override fun onListener() {
    }


    private fun navSelectedAction() {
        binding.bnv.setOnNavigationItemSelectedListener {
            HomeFrg()
            when (it.itemId) {
                R.id.action_home -> {
                    if (homeFrg == null) { homeFrg = HomeFrg()}
                    homeFrg?.let {
                        FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                    }
                }

                R.id.action_dashboard -> {
                    if (homeCommunityFrg == null) { homeCommunityFrg = HomeCommunityFrg()}
                    homeCommunityFrg?.let {
                        FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                    }
                }

                R.id.action_notifications -> {
                    if (notiFrg == null) { notiFrg = NotiFrg()}
                    notiFrg?.let {
                        FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                    }
                }

                R.id.action_user -> {
                    if (userFrg == null) { userFrg = UserFrg()}
                    userFrg?.let {
                        FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                    }
                }

                else -> {}
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}