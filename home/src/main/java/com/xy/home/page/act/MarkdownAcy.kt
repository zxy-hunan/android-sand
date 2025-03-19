package com.xy.home.page.act

import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.intentExtras
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.CommModel
import com.xy.common.util.clickDebounce
import com.xy.common.util.remove
import com.xy.common.view.load
import com.xy.home.R
import com.xy.home.databinding.ActivityMarkdownBinding
import com.xy.home.databinding.ItemCommLayoutBinding
import com.xy.home.databinding.ItemTopArticleBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy
import io.noties.markwon.Markwon

/**
 * @file H5Acy
 * @author zxy
 * @date 2025/1/24 15:48
 * @brief
 */
@Route(path = ARouterConfig.Home.MarkdownAct.PATH)
class MarkdownAcy : MviAcy<ActivityMarkdownBinding, MainVm, MainIntent>(MainVm::class.java, windowBgRes = com.xy.common.R.color.white) {


    private val articleModel by intentExtras<ArticleModel>(ARouterConfig.Home.MarkdownAct.ARTICLE_MODE, ArticleModel())
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        binding.tblNav.setTitle("原文")
        binding.clLayout.tvOrigin.remove()

        Markwon.create(mContext).setMarkdown(binding.tvContent, articleModel.content)
        viewModel.commList(1, "${articleModel.id}")
        binding.rvComm.bindCommList()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.CommList -> {
                    binding.rvComm.models=it.list
                }

                else -> {

                }
            }
        }
    }

    override fun onListener() {

    }

}

fun RecyclerView.bindCommList() {
    this.linear().divider {
        orientation = DividerOrientation.HORIZONTAL
        setDivider(5, dp = true)
        includeVisible = true
    }.setup {
        addType<CommModel>(R.layout.item_comm_layout)
        onCreate {
            val item = getBinding<ItemCommLayoutBinding>()
            item.rvChildComm.bindCommList()
        }

        onBind {
            val data = getModel<CommModel>()
            val item = getBinding<ItemCommLayoutBinding>()

            item.tvName.text = data.sysUser.nickName
            item.ivHead.load(data.sysUser.avatar)
            item.tvTitle.text = data.content
            item.rvChildComm.models = data.comComm

        }
    }
}