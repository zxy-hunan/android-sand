package com.xy.user.page.acy

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.models
import com.dylanc.longan.intentExtras
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.util.clickDebounce
import com.xy.common.util.initFragment
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.R
import com.xy.user.databinding.ActivityClassifyBinding
import com.xy.user.databinding.ActivitySettingsBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.frg.ArticleListFrg
import com.xy.user.page.frg.viewhelper.rvSetting
import com.xy.user.vm.UserVm

/**
 * @file SettingsAcy
 * @author zxy
 * @date 2024/11/28 15:38
 * @brief settings
 */
@Route(path = ARouterConfig.User.ArticleClassifyAct.PATH)
class ArticleClassifyAcy :MviAcy<ActivityClassifyBinding, UserVm, UserIntent>(UserVm::class.java,
    windowBgRes = com.xy.common.R.color.white){
    var tabList = mutableListOf<String>()

    private val frgList = mutableListOf<Fragment>()
    private val frg0 by lazy { ArticleListFrg.newInstance(0) }
    private val frg1 by lazy { ArticleListFrg.newInstance(1) }
    private val frg2 by lazy { ArticleListFrg.newInstance(2) }
    private val frg3 by lazy { ArticleListFrg.newInstance(3) }

    private val comId by intentExtras(ARouterConfig.User.ArticleClassifyAct.COM_ID, 0)

    init {
        frgList.apply {
            add(frg0)
            add(frg1)
            add(frg2)
            add(frg3)
        }

        tabList.apply {
            add("后端")
            add("前端")
            add("移动端")
            add("其他")
        }
    }
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        binding.titleBar.setTitle("创作者中心")
        binding.viewPager.run {
            isUserInputEnabled = true
            initFragment(supportFragmentManager, lifecycle, frgList).run {
                offscreenPageLimit = frgList.size
            }
        }
        binding.viewPager.currentItem = comId

        TabLayoutMediator(binding.tablayout,binding.viewPager) { tab, position ->
            tab.text= tabList[position]
        }.attach()


    }

    override fun observe() {
    }

    override fun onListener() {
    }
}