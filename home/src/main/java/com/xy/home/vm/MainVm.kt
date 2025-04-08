package com.xy.home.vm

import com.xy.common.data.Common
import com.xy.common.data.model.KyImageModel
import com.xy.common.util.MmkvRepository
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.home.api.MainApiService
import com.xy.home.intent.MainIntent
import com.xy.mviframework.network.def.apiRetrofit

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

    fun refresh(com: Common) {
        isRefresh = true
        page = 1
        articleList("${com.no}")
    }

    fun loadMore(com: Common) {
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

    fun articleComm(content: String = "", userId: String = "", articleId: String = "", parId: String = "", replyId: String = "", onSuccess: () -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>()
        dftMap["content"] = content
        dftMap["userId"] = userId
        dftMap["articleId"] = articleId
        dftMap["parId"] = parId
        dftMap["replyId"] = replyId

        mainService.articleComm(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            onSuccess.invoke()
        })
    }


    fun commList(pageNum: Int = 1, articleId: String = "") {
        val dftMap = mutableMapOf<String, String>()
        dftMap["pageNum"] = "$pageNum"
        dftMap["pageSize"] = "10"
        dftMap["articleId"] = articleId

        mainService.commList(MmkvRepository.loginToken, dftMap).HttpCoroutine(
            onError = {
            }, onSuccess = {
                _intent.emitCoroutine(MainIntent.CommList(it))
            })
    }


    fun articleStar(articleId: String = "", userId: String = "", onSuccess: () -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>()
        dftMap["articleId"] = articleId
        dftMap["userId"] = userId

        mainService.articleStar(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            onSuccess.invoke()
        })
    }


    fun getKyImagesResult() {
        getKyImages{
            _intent.emitCoroutine(MainIntent.KyImageModelList(it))
        }
    }






    fun getCultureList() {
        var list = listOf(
            "唐",
            "宋",
            "花间",
            "南唐",)
        _intent.emitCoroutine(MainIntent.CultureList(list))
    }

}