package com.xy.hot.intent


import com.xy.common.data.model.KyVideoModel
import com.xy.common.data.model.LiuVideoModel
import com.xy.mviframework.base.vm.BaseIntent

/**
 * @file HotIntent
 * @author zxy
 * @date 2025/5/7 9:46
 * @brief
 */
sealed class HotIntent: BaseIntent() {
    data class KyVideoModelList(val list:MutableList<LiuVideoModel>) : HotIntent()
}
