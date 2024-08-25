package com.xy.home.intent

import com.xy.common.data.BaseModel
import com.xy.home.data.ArticleModel
import com.xy.mviframework.base.vm.BaseIntent

/**
 * @file MainIntent
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief mainIntent
 */
sealed class MainIntent : BaseIntent() {
    data class ArticleList(val list:List<ArticleModel>) : MainIntent()
}