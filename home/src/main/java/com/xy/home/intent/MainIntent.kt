package com.xy.home.intent

import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.CommModel
import com.xy.mviframework.base.vm.BaseIntent

/**
 * @file MainIntent
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief mainIntent
 */
sealed class MainIntent : BaseIntent() {
    data class ArticleList(val list:List<ArticleModel>) : MainIntent()

    data class CommList(val list:List<CommModel>) : MainIntent()
}