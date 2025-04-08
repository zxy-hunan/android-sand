package com.xy.home.page.frg

import android.graphics.Color
import androidx.databinding.DataBindingUtil.getBinding
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.Common
import com.xy.common.util.clickDebounce
import com.xy.home.R
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.KyImageModel
import com.xy.common.util.glide.loadAny
import com.xy.common.util.setSemiBoldFonts
import com.xy.common.view.load
import com.xy.home.databinding.FragmentCultureBinding
import com.xy.home.databinding.FragmentHomeCommunityBinding
import com.xy.home.databinding.ItemCommunityArticleBinding
import com.xy.home.databinding.ItemCultureBinding
import com.xy.home.intent.MainIntent
import com.xy.home.page.adapter.CommunityTopAdapter
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeCultureFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief HomeCultureFrg
 */
class HomeCultureFrg() : MviFragment<FragmentCultureBinding, MainVm, MainIntent>(MainVm::class.java) {
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

    }


    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.KyImageModelList -> {
                    binding.pglRefresh.finishLoadMore()
                    binding.pglRefresh.finishRefresh()
                    binding.pglRefresh.showContent()
                    if (it.list.size < 10) {
                        binding.pglRefresh.setNoMoreData(true)
                    }

                    if (viewModel.isRefresh) {
                        binding.rvCommunity.models = it.list
                    } else {
                        binding.rvCommunity.addModels(it.list)
                    }

                }

                else -> {

                }
            }
        }
    }

    private fun setRv() {
        binding.rvCommunity.grid(2).divider {
            orientation = DividerOrientation.GRID
            setDivider(10, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
            includeVisible = true
        }.setup {
            addType<KyImageModel> { R.layout.item_culture }
            onBind {
                val data = getModel<KyImageModel>()
                val item = getBinding<ItemCultureBinding>()

                item?.apply {
//                    tvCultureTitle.text = data.title
                    ivCulture.loadAny(data.url)
                }
            }
        }
    }

    override fun onListens() {
        binding.pglRefresh.run {
            onRefresh {
                viewModel.getKyImagesResult()
            }
            onLoadMore {
            }
        }
        binding.pglRefresh.refreshing()
    }
}