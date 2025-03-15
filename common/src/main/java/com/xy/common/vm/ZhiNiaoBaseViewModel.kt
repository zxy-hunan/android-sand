package com.xy.common.vm

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.xy.common.api.ArticleApiService
import com.xy.common.data.ArticleTotalNum
import com.xy.common.data.model.ArticleModel
import com.xy.common.util.MmkvRepository
import com.xy.mviframework.base.vm.BaseIntent
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.api.HttpBy
import com.xy.mviframework.network.def.BaseRes
import com.xy.mviframework.network.def.apiRetrofit
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.logD
import com.xy.mviframework.network.tool.logE
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
    val baseApiRetrofit: ArticleApiService by lazy {
        apiRetrofit.create(ArticleApiService::class.java)
    }

/*    protected fun <T> Flow<BaseRes<T>>.HttpCoroutine(
        onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {},
        onCompleteData: (BaseRes<T>) -> Unit = {}
    ): Job {
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
    }*/

    var page = 1

    @SuppressLint("SuspiciousIndentation")
    fun articleListReq(comId: String = "", onSuccess: (List<ArticleModel>) -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>("pageNum" to "$page", "pageSize" to "10")
        if (comId.isNotBlank()) {
            dftMap["comId"] = comId
        }
        baseApiRetrofit.articleList(dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            onSuccess.invoke(it)
        })
    }

    fun articleNumReq(userId: String = "", type: Int = ArticleTotalNum.Common.no, onSuccess: (Int) -> Unit = {}) {
        if (userId.isEmpty()) return
        val dftMap = mutableMapOf<String, String>("pageNum" to "$page", "pageSize" to "1000", "isSelf" to "0", "userId" to userId)
        when (type) {
            ArticleTotalNum.Common.no -> {
                baseApiRetrofit.articleCommList(MmkvRepository.loginToken,dftMap).HttpCoroutine(onError = {
                    logE(LOG_TAG,type, "onError:${it.message}")
                    onSuccess.invoke(0)
                }, onOriginSuccess = {
                    logE(LOG_TAG,type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }

            ArticleTotalNum.Article.no -> {
                baseApiRetrofit.articleList(dftMap).HttpCoroutine(onError = {
                    onSuccess.invoke(0)
                    logE(LOG_TAG,type, "onError:${it.message}")
                }, onOriginSuccess = {
                    logE(LOG_TAG,type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }

            ArticleTotalNum.Star.no -> {
                baseApiRetrofit.articleStarList(MmkvRepository.loginToken,dftMap).HttpCoroutine(onError = {
                    logE(LOG_TAG,type, "onError:${it.message}")
                    onSuccess.invoke(0)
                }, onOriginSuccess = {
                    logE(LOG_TAG,type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }
            else ->{
                onSuccess.invoke(0)
            }
        }


    }

}