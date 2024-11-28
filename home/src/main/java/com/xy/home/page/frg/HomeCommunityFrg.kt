package com.xy.home.page.frg

import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.util.clickDebounce
import com.xy.home.data.ArticleModel
import com.xy.home.databinding.FragmentHomeCommunityBinding
import com.xy.home.intent.MainIntent
import com.xy.home.page.adapter.CommunityTopAdapter
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeCommunityFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief HomeCommunityFrg
 */
class HomeCommunityFrg() :MviFragment<FragmentHomeCommunityBinding,MainVm,MainIntent>(MainVm::class.java) {
    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        binding.stvPost.clickDebounce {
            ARouterConfig.Post.PostAcy.push()
        }
    }

    override fun lazyLoad() {
        viewModel.page = 1
        viewModel.articleList()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.ArticleList -> {

                    startBanner(it.list)
                }
            }
        }
    }

    private fun startBanner(itemData: List<ArticleModel>) {

        binding.communityTop
            .setAdapter(CommunityTopAdapter(datas = itemData))
            .setIntercept(false)
            .setOnBannerListener { data, position -> }
            .start()

    }

    override fun onListens() {
    }
}