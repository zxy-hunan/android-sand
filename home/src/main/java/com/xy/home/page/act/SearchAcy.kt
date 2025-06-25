package com.xy.home.page.act

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.dylanc.longan.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.king.image.imageviewer.ImageViewerSpec.orientation
import com.xy.common.data.Common
import com.xy.common.data.model.ArticleModel
import com.xy.common.view.bindArticleList
import com.xy.home.databinding.SearchActivityBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file SearchAcy
 * @author zxy
 * @date 2025/6/12 15:06
 * @brief search
 */
@Route(path = ARouterConfig.Home.SearchAcy.PATH)
class SearchAcy : MviAcy<SearchActivityBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }

        //nav
        binding.tbTopBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })

        binding.mSearchView.setOnSearchClickListener {
//            toast("搜索中...")
        }

        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.refresh(Common.Default)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        binding.rvList.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(10, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
            startVisible = true
        }.bindArticleList()
    }

    override fun observe() {
        intentCallBack { it ->
            when (it) {
                is MainIntent.ArticleList -> {

                    if (viewModel.isRefresh) {
                        binding.rvList.models = it.list
                    } else {
                        binding.rvList.addModels(it.list)
                    }

                }

                else -> {

                }
            }
        }
    }

    override fun onListener() {
    }
}