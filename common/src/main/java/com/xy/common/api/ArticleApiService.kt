package com.xy.common.api

import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.StarArticleModel
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

/**
 * @file MainApiService
 * @author zxy
 * @date 2024/7/19 11:31
 * @brief mainapiserviice
 */
interface ArticleApiService {
    @GET("system/article/list")
    fun articleList(@QueryMap params: Map<String,String>): Flow<BaseRes<List<ArticleModel>>>

    //总评论
    @GET("system/comm/list")
    fun articleCommList(@Header("Authorization") token: String,@QueryMap params: Map<String,String>): Flow<BaseRes<List<ArticleModel>>>

    //总文
    @GET("system/article/list")
    fun articleTotalList(@QueryMap params: Map<String,String>): Flow<BaseRes<List<ArticleModel>>>

    //总赞
    @GET("system/star/list")
    fun articleStarList(@Header("Authorization") token: String,@QueryMap params: Map<String,String>): Flow<BaseRes<List<StarArticleModel>>>
}