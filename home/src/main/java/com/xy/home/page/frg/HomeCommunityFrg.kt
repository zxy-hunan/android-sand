package com.xy.home.page.frg

import android.graphics.Color
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.Common
import com.xy.common.util.clickDebounce
import com.xy.home.R
import com.xy.common.data.model.ArticleModel
import com.xy.common.view.load
import com.xy.home.databinding.FragmentHomeCommunityBinding
import com.xy.home.databinding.ItemCommunityArticleBinding
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
class HomeCommunityFrg() : MviFragment<FragmentHomeCommunityBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        binding.stvPost.clickDebounce {
            ARouterConfig.Post.PostAcy.push()
        }
        setRv()
    }

    override fun lazyLoad() {
        binding.pglRefresh.refresh()
    }



    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.ArticleList -> {
                    binding.pglRefresh.finishLoadMore()
                    binding.pglRefresh.finishRefresh()
                    if (it.list.size < 10) {
                        binding.pglRefresh.setNoMoreData(true)
                    }

                    if(viewModel.isRefresh){
                        binding.rvCommunity.models=it.list
                    }else{
                        binding.rvCommunity.addModels(it.list)
                    }

                }
                else ->{

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

    private fun setRv() {
        binding.rvCommunity.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(10, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
        }.setup {
            addType<ArticleModel> { R.layout.item_community_article }
            onBind {
                val data = getModel<ArticleModel>()
                val item = getBinding<ItemCommunityArticleBinding>()

                item?.apply {
                    tvTitle.text = data.title
                    ivHead.load(data.imageurl)
                    tvName.text = data.sysUser.nickName
                }
            }
        }
    }

    override fun onListens() {
        binding.pglRefresh.run {
            onRefresh {
                viewModel.refresh(Common.Hot)
            }
            onLoadMore {
                viewModel.loadMore(Common.Hot)
            }
        }
    }
}