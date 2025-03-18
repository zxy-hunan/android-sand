package com.xy.home.vm

import com.xy.common.data.Common
import com.xy.common.data.model.ArticleModel
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.home.api.MainApiService
import com.xy.home.intent.MainIntent
import com.xy.mviframework.network.def.apiRetrofit
import kotlinx.serialization.json.JsonNull.content

/**
 * @file MainVm
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainVm : ZhiNiaoBaseViewModel<MainIntent>() {
    val mainService: MainApiService by lazy {
        apiRetrofit.create(MainApiService::class.java)
    }


    var isRefresh = true

    fun refresh(com:Common) {
        isRefresh = true
        page = 1
        articleList("${com.no}")
    }

    fun loadMore(com:Common) {
        isRefresh = false
        page++
        articleList("${com.no}")
    }

    fun articleList(comId: String = "${Common.Hot.no}") {
        articleListReq(comId) {
            _intent.emitCoroutine(MainIntent.ArticleList(it))
        }
    }


//    {content: "哈哈", userId: 100, articleId: 74, parId: null, replyId: null}

    fun articleComm(content: String = "",userId:String="",articleId:String="",parId:String="",replyId:String="")  {
        val dftMap = mutableMapOf<String, String>()
        dftMap["content"] = content
        dftMap["userId"] = userId
        dftMap["articleId"] = articleId
        dftMap["parId"] = parId
        dftMap["replyId"] = replyId

        mainService.articleComm(dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
        })
    }
}