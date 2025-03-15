package com.xy.home.page.act

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.longan.intentExtras
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.model.ArticleModel
import com.xy.home.databinding.ActivityMarkdownBinding
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
class MarkdownAcy :MviAcy<ActivityMarkdownBinding, MainVm, MainIntent>(MainVm::class.java, windowBgRes = com.xy.common.R.color.white) {


    private val articleModel by intentExtras<ArticleModel>(ARouterConfig.Home.MarkdownAct.ARTICLE_MODE, ArticleModel())
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            titleBarMarginTop(binding.viewLine)
        }
        Markwon.create(mContext).setMarkdown(binding.tvContent,articleModel.content)
    }

    override fun observe() {
    }

    override fun onListener() {
    }

}