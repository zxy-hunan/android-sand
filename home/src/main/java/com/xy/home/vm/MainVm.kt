package com.xy.home.vm

import UserApiService
import android.annotation.SuppressLint
import android.util.Log
import com.xy.common.data.Common
import com.xy.home.api.MainApiService
import com.xy.home.intent.MainIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.def.apiRetrofit

/**
 * @file MainVm
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainVm : BaseViewModel<MainIntent>() {
    val apiService: MainApiService by lazy {
        apiRetrofit.create(MainApiService::class.java)
    }

    var page = 1
    var isRefresh = true

     fun refresh() {
        isRefresh=true
        page = 1
        articleList("${Common.Hot.no}")
    }

    fun loadMore() {
        isRefresh=false
        page ++
        articleList("${Common.Hot.no}")
    }

    @SuppressLint("SuspiciousIndentation")
    fun articleList(comId:String="") {
        val dftMap = mutableMapOf<String, String>("pageNum" to "$page", "pageSize" to "10")
        if(comId.isNotBlank()){
            dftMap["comId"] = comId
        }
        apiService.articleList(dftMap).HttpCoroutine(onError = {
            Log.e("MainVm", "articleList: onError")
        }, onSuccess = {
            Log.e("MainVm", "articleList: onSuccess $page ${it.size}")
            _intent.emitCoroutine(MainIntent.ArticleList(it))
        })
    }
}