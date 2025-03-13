package com.xy.home.page.act

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragment
import com.xy.common.util.initFragment
import com.xy.home.R
import com.xy.common.data.model.APPTAG
import com.xy.home.databinding.ActivityMainBinding
import com.xy.home.intent.MainIntent
import com.xy.home.page.frg.HomeCommunityFrg
import com.xy.home.page.frg.HomeFrg
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.mviframework.network.tool.SHOW_LOG
import com.xy.user.page.frg.UserFrg
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener


/**
 * @file MainActivity
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainActivity : MviAcy<ActivityMainBinding, MainVm, MainIntent>(MainVm::class.java, windowBgRes = com.xy.common.R.color.white) {
    private val frgList = mutableListOf<Fragment>()

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        SHOW_LOG = true
        immersionBar(binding.view)
        initFrg()
        navSelectedAction()

    }

    private fun initFrg() {
        frgList.add(HomeFrg())
        frgList.add(HomeCommunityFrg())
//        frgList.add(NotiFrg())
//        frgList.add(TUIConversationMinimalistFragment())
        frgList.add(TUIConversationFragment())
        frgList.add(UserFrg())
    }

    override fun observe() {
    }


    override fun onListener() {
    }

    fun immersionBar(statusView: View) {

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

        binding.mainVp.run {
//            isUserInputEnabled = false
            initFragment(supportFragmentManager, frgList).run {
                offscreenPageLimit = frgList.size - 1
            }
        }
        navigationController.setupWithViewPager(binding.mainVp)

        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                when (index) {
                    APPTAG.HOME.tag -> {

                    }

                    APPTAG.COMMUNITY.tag -> {

                    }

                    APPTAG.NOTIFICATION.tag -> {

                    }

                    APPTAG.USER.tag -> {

                    }

                }
            }

            override fun onRepeat(index: Int) {
            }

        })

    }

}