package com.xy.common.vm

import androidx.lifecycle.viewModelScope
import com.xy.mviframework.base.vm.BaseIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.api.HttpBy
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @file ZhiNiaoBaseViewModel
 * @author zxy
 * @date 2025/2/6 15:38
 * @brief
 */
open class ZhiNiaoBaseViewModel<I> : BaseViewModel<I>() {
    protected fun <T> Flow<BaseRes<T>>.HttpCoroutine(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {},
                                                     onCompleteData: (BaseRes<T>) -> Unit = {}): Job {
        return viewModelScope.launch {
            HttpBy(
                onFail = {
                    _baseIntent.emitCoroutine(BaseIntent.ShowLoading(it))
                },
                onError = {
                    _baseIntent.emitCoroutine(BaseIntent.ShowLoading("${it.message}"))
                    onError.invoke(it)
                }, onSuccess = {
//                    _baseIntent.emitCoroutine(BaseIntent.CompletionRefreshOrLoadSuccess())
                    onSuccess.invoke(it)
                },
                onCompleteData = {
                    onCompleteData.invoke(it)
                }
            )
        }
    }

}