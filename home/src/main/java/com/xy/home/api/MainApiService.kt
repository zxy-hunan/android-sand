package com.xy.home.api

import com.xy.common.data.BaseModel
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.CommModel
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @file MainApiService
 * @author zxy
 * @date 2024/7/19 11:31
 * @brief mainapiserviice
 */
interface MainApiService {
    @GET("system/article/list")
    fun articleList(@QueryMap params: Map<String,String>): Flow<BaseRes<List<ArticleModel>>>

    @POST("system/comm")
    fun articleComm(@Header("Authorization") token: String,@Body params: Map<String,String>): Flow<BaseRes<BaseModel>>

    @GET("system/comm/list")
    fun commList(@Header("Authorization") token: String, @QueryMap params: Map<String,String>): Flow<BaseRes<List<CommModel>>>


}