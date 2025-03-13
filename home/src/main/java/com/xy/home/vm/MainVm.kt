package com.xy.home.vm

import com.xy.common.data.Common
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.home.intent.MainIntent

/**
 * @file MainVm
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainVm : ZhiNiaoBaseViewModel<MainIntent>() {
//    val apiService: MainApiService by lazy {
//        apiRetrofit.create(MainApiService::class.java)
//    }


    var isRefresh = true

    fun refresh() {
        isRefresh = true
        page = 1
        articleList("${Common.Hot.no}")
    }

    fun loadMore() {
        isRefresh = false
        page++
        articleList("${Common.Hot.no}")
    }

    fun articleList(comId: String = "") {
        articleListReq(comId) {
            _intent.emitCoroutine(MainIntent.ArticleList(it))
        }
    }


}