package com.xy.home.vm

import android.annotation.SuppressLint
import android.util.Log
import com.xy.home.api.MainApiService
import com.xy.home.intent.MainIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.default.apiRetrofit

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

    @SuppressLint("SuspiciousIndentation")
    fun articleList() {
      var dftMap= mutableMapOf<String, String>("pageNo" to "1","pageSize" to "10")
        apiService.articleList(dftMap).HttpCoroutine(onError = {
            Log.e("MainVm", "articleList: onError", )
        }, onSuccess = {
            Log.e("MainVm", "articleList: onSuccess", )
        })
    }
}