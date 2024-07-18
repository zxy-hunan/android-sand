package com.xy.sand

import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.default.apiRetrofit

/**
 * @file MainVm
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainVm : BaseViewModel<MainIntent>() {
    val apiService: MainService by lazy {
        apiRetrofit.create(MainService::class.java)
    }
}