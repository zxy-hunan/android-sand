package com.xy.home.page.act

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.FragmentUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.home.R
import com.xy.home.data.APPTAG
import com.xy.home.databinding.ActivityMainBinding
import com.xy.home.intent.MainIntent
import com.xy.home.page.frg.HomeCommunityFrg
import com.xy.home.page.frg.HomeFrg
import com.xy.home.vm.MainVm
import com.xy.im.page.frg.NotiFrg
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.page.frg.UserFrg
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener


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

    }

    override fun initView() {
        immersionBar(binding.view)
        homeFrg = HomeFrg()
        FragmentUtils.add(supportFragmentManager, homeFrg!!, R.id.ll_content)
        navSelectedAction()

    }

    override fun observe() {
    }


    override fun onListener() {
    }

    fun immersionBar(statusView: View){

        immersionBar {
//            statusBarColor(com.google.android.material.R.color.m3_ref_palette_white)

//            statusBarAlpha(0.1f)
            statusBarDarkFont(true)

//            titleBarMarginTop(statusView)
        }
    }


    private fun navSelectedAction() {

        val navigationController: NavigationController = binding.bnv.material()
            .addItem(R.mipmap.home, "首页")
            .addItem(R.mipmap.coummity, "热议")
            .addItem(R.mipmap.chat, "会话")
            .addItem(R.mipmap.me, "我").setMode(4)
            .build()

        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener{
            override fun onSelected(index: Int, old: Int) {
                when (index) {
                    APPTAG.HOME.tag -> {
                        if (homeFrg == null) { homeFrg = HomeFrg()}
                        homeFrg?.let {
                            FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                        }
                    }

                    APPTAG.COMMUNITY.tag -> {
                        if (homeCommunityFrg == null) { homeCommunityFrg = HomeCommunityFrg()}
                        homeCommunityFrg?.let {
                            FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                        }
                    }

                    APPTAG.NOTIFICATION.tag -> {
                        if (notiFrg == null) { notiFrg = NotiFrg()}
                        notiFrg?.let {
                            FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                        }
                    }

                    APPTAG.USER.tag -> {
                        if (userFrg == null) { userFrg = UserFrg()}
                        userFrg?.let {
                            FragmentUtils.replace(supportFragmentManager, it, R.id.ll_content)
                        }
                    }


                }
            }

            override fun onRepeat(index: Int) {
            }

        })

        /*binding.bnv.setOnNavigationItemSelectedListener {
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
        }*/
    }

}