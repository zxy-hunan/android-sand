package com.xy.home

import com.xy.mviframework.network.default.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @file MainService
 * @author zxy
 * @date 2024/7/18 19:23
 * @brief mainservice
 */
interface MainService {
    @POST("{test}")
    fun test(@Path(value = "test", encoded = true) test:String): Flow<BaseResponse<String>>
}