package com.xy.home.page.frg

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.home.R
import com.xy.common.data.model.CnModel
import com.xy.common.util.brvAdapter
import com.xy.common.util.glide.loadAny
import com.xy.home.databinding.FragmentCnBinding
import com.xy.home.databinding.ItemCnBinding
import com.xy.home.databinding.ItemCnChildBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeCultureFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief HomeCNFrg
 */
class HomeCNFrg() : MviFragment<FragmentCnBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        setRv()
    }

    override fun lazyLoad() {

    }


    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.CultureList -> {
                    binding.pglRefresh.finishLoadMore()
                    binding.pglRefresh.finishRefresh()
                    binding.pglRefresh.showContent()
                    if (it.list.size < 10) {
                        binding.pglRefresh.setNoMoreData(true)
                    }

                    if (viewModel.isRefresh) {
                        binding.rvCn.models = it.list
                    } else {
                        binding.rvCn.addModels(it.list)
                    }

                }

                else -> {

                }
            }
        }
    }

    private fun setRv() {
        binding.rvCn.linear().divider {
            orientation = DividerOrientation.VERTICAL
            setDivider(10, dp = true)
        }.setup {
            addType<CnModel> { R.layout.item_cn }
            onCreate {
                when (itemViewType) {
                    R.layout.item_cn -> {
                        var recyclerView: RecyclerView = this.findView<RecyclerView>(R.id.rv_culture_child)
                        recyclerView.apply {
                            this.grid(4).divider {
                                orientation = DividerOrientation.GRID
                                setDivider(10, dp = true)
                                includeVisible = true
                            }.setup {
                                addType<CnModel> { R.layout.item_cn_child }

                            }
                        }
                    }

                    else -> {
                    }
                }
            }
            onBind {
                val data = getModel<CnModel>()
                val item = getBinding<ItemCnBinding>()
                item?.apply {
                    tvTitle.text = data.name
                    rvCultureChild.brvAdapter?.onBind {
                        val data = getModel<CnModel>()
                        val item = getBinding<ItemCnChildBinding>()
                        item.tvChildTitle.text = data.name
                        item.ivChildTitle.loadAny(data.url){
                        }
                    }
                    rvCultureChild.brvAdapter?.models = data.list
                }
            }
        }
    }

    override fun onListens() {
        binding.pglRefresh.run {
            onRefresh {
                viewModel.getCultureList()
            }
            onLoadMore {
            }
        }
        binding.pglRefresh.refreshing()
    }
}