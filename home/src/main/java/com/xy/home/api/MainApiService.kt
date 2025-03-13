package com.xy.home.api

import com.xy.common.data.model.ArticleModel
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
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
}