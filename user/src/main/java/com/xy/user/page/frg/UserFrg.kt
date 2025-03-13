package com.xy.user.page.frg

import android.content.Intent
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.TAG
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.data.ArticleTotalNum
import com.xy.common.util.brvAdapter
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.logI
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

    var topTagList = mutableListOf<UserTagModel>()

    override fun lazyLoad() {
        binding.rvSetting.models = viewModel.getSettingTagList()
        binding.rvMiddle.models = viewModel.getMiddleTagList()
        binding.rvHigh.models = viewModel.getHighTagList()
        topTagList = viewModel.getTopTagList()
        binding.rvTop.models = topTagList
    }

    override fun observe() {
        SharedFlowBus.observer<Int>(key = FlowBusTag.LoginSuccess(), lifecycle = lifecycle) {
            viewModel.articleNum("100", ArticleTotalNum.Article) {
                topTagList[0].tagUrl = it.toString()
                binding.rvTop.brvAdapter?.notifyDataSetChanged()
                logI(LOG_TAG,0,topTagList)
            }

            viewModel.articleNum("100", ArticleTotalNum.Common) {
                topTagList[1].tagUrl = it.toString()
                binding.rvTop.brvAdapter?.notifyDataSetChanged()
                logI(LOG_TAG,1,topTagList)
            }

            viewModel.articleNum("100", ArticleTotalNum.Star) {
                topTagList[2].tagUrl = it.toString()
                binding.rvTop.brvAdapter?.notifyDataSetChanged()
                logI(LOG_TAG,2,topTagList)
            }
        }
    }

    override fun onListens() {
    }


    private fun gotoLogin() {
        startActivity(Intent(mActivity, LoginAcy::class.java))
//        ARouterConfig.User.LoginAct.push()
    }
}