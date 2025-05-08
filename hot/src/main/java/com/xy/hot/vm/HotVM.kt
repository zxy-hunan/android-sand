package com.xy.hot.vm

import com.xy.common.data.model.LiuVideoModel
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.hot.intent.HotIntent
import com.xy.mviframework.network.def.apiRetrofit

/**
 * @file HotVM
 * @author zxy
 * @date 2025/5/7 9:47
 * @brief
 */
class HotVM : ZhiNiaoBaseViewModel<HotIntent>() {
//    val hotService: MainApiService by lazy {
//        apiRetrofit.create(MainApiService::class.java)
//    }

    fun fetchVideosReq(page: Int = 1, size: Int = 10){
        fetchVideos(page, size){
            _intent.emitCoroutine(HotIntent.KyVideoModelList(it))
        }
    }
}
