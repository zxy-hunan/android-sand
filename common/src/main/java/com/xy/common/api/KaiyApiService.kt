package com.xy.common.api

import com.xy.common.data.model.KyBaseModel
import com.xy.common.data.model.KyImageResultModel
import com.xy.common.data.model.KyVideoResultModel
import com.zyx_hunan.baseutil.net.ApiPath
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @file MainApiService
 * @author zxy
 * @date 2024/7/19 11:31
 * @brief mainapiserviice
 */
interface KaiyApiService : ApiPath {
    @GET("getImages")
    fun getImages(@QueryMap params: Map<String,String>): Observable<KyBaseModel<KyImageResultModel>>


    @GET("getHaoKanVideo")
    fun getHaoKanVideo(@QueryMap params: Map<String,String>): Observable<KyBaseModel<KyVideoResultModel>>
}