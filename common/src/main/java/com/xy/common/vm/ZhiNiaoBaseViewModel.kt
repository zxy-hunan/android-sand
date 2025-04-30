package com.xy.common.vm

import android.annotation.SuppressLint
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.dylanc.longan.topActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xy.common.api.ArticleApiService
import com.xy.common.api.KaiyApiService
import com.xy.common.data.ArticleTotalNum
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.BingImgModel
import com.xy.common.data.model.KyBaseModel
import com.xy.common.data.model.KyImageModel
import com.xy.common.data.model.KyImageResultModel
import com.xy.common.data.model.KyVideoModel
import com.xy.common.data.model.KyVideoResultModel
import com.xy.common.util.MmkvRepository
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.def.apiRetrofit
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.logE
import com.zyx_hunan.baseutil.net.util.MyObserver
import com.zyx_hunan.baseutil.net.util.NetUtil
import com.zyx_hunan.baseutil.net.util.RxHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch
import kotlin.random.Random


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

    private fun initKYNet() {
        NetUtil.options().setApiPath(KaiyApiService::class.java)
            .setDefault_time(100)
            .setEnableLog(false)
            .setUrl("https://api.apiopen.top/api/")
    }


    fun getKyImages(page: Int = 1,onSuccess: (List<KyImageModel>) -> Unit = {}) {
        initKYNet()
        var map = mutableMapOf<String, String>()
        map["page"] = page.toString()
        map["size"] = "10"
        map["type"] = "beauty"

        val kyService: KaiyApiService = NetUtil.getApiPath() as KaiyApiService
        kyService.getImages(map)
            .compose(RxHelper.observableIO2Main(topActivity))
            .subscribe(object : MyObserver<KyBaseModel<KyImageResultModel>>(topActivity) {
                override fun onComplete(isError: Boolean?) {
                }

                override fun onFailure(e: Throwable?, errorMsg: String?) {
                }

                override fun onSuccess(result: KyBaseModel<KyImageResultModel>?) {
                    logE(LOG_TAG, "onSuccess result: ${result?.result?.total}")
                    result?.result?.list?.let {
                        logE(LOG_TAG, "onSuccess list: ${it}")
                        onSuccess.invoke(it)
                    }
                }


            })
    }


    fun getKyVideos(page: Int = 1, size: Int = 10, onSuccess: (List<KyVideoModel>) -> Unit = {}) {
        initKYNet()
        //https://api.apiopen.top/api/getHaoKanVideo?page=1&size=10
        var map = mutableMapOf<String, String>()
        map["page"] = "$page"
        map["size"] = "$size"

        val kyService: KaiyApiService = NetUtil.getApiPath() as KaiyApiService
        kyService.getHaoKanVideo(map)
            .compose(RxHelper.observableIO2Main(topActivity))
            .subscribe(object : MyObserver<KyBaseModel<KyVideoResultModel>>(topActivity) {
                override fun onComplete(isError: Boolean?) {
                    logE(LOG_TAG, "onComplete : $isError")
                }

                override fun onFailure(e: Throwable?, errorMsg: String?) {
                    logE(LOG_TAG, "onFailure : $errorMsg")
                    onSuccess.invoke(listOf())
                }

                override fun onSuccess(result: KyBaseModel<KyVideoResultModel>?) {
                    logE(LOG_TAG, "onSuccess result: ${result?.result?.total}")
                    result?.result?.list?.let {
                        logE(LOG_TAG, "onSuccess list: ${it}")
                        onSuccess.invoke(it)
                    }
                }


            })
    }


    var page = 1

    @SuppressLint("SuspiciousIndentation")
    fun articleListReq(comId: String = "", onSuccess: (List<ArticleModel>) -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>("pageNum" to "$page", "pageSize" to "10")
        if (comId.isNotBlank()) {
            dftMap["comId"] = comId
        }
        baseApiRetrofit.articleList(dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            GlobalScope.launch {
//                delay(Random.nextInt(100, 2501).toLong())
                onSuccess.invoke(it)
            }
        })
    }

    fun articleNumReq(userId: String = "", type: Int = ArticleTotalNum.Common.no, onSuccess: (Int) -> Unit = {}) {
        if (userId.isEmpty()) return
        val dftMap = mutableMapOf<String, String>("pageNum" to "$page", "pageSize" to "1000", "isSelf" to "0", "userId" to userId)
        when (type) {
            ArticleTotalNum.Common.no -> {
                baseApiRetrofit.articleCommList(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
                    logE(LOG_TAG, type, "onError:${it.message}")
                    onSuccess.invoke(0)
                }, onOriginSuccess = {
                    logE(LOG_TAG, type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }

            ArticleTotalNum.Article.no -> {
                baseApiRetrofit.articleList(dftMap).HttpCoroutine(onError = {
                    onSuccess.invoke(0)
                    logE(LOG_TAG, type, "onError:${it.message}")
                }, onOriginSuccess = {
                    logE(LOG_TAG, type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }

            ArticleTotalNum.Star.no -> {
                baseApiRetrofit.articleStarList(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
                    logE(LOG_TAG, type, "onError:${it.message}")
                    onSuccess.invoke(0)
                }, onOriginSuccess = {
                    logE(LOG_TAG, type, "onSuccess: ${it.total}")
                    onSuccess.invoke(it.total)
                })
            }

            else -> {
                onSuccess.invoke(0)
            }
        }


    }



    private fun initBingKYNet() {
        NetUtil.options().setApiPath(KaiyApiService::class.java)
            .setDefault_time(100)
            .setEnableLog(true)
            .setUrl("https://peapix.com/bing/")
    }


    fun getBingImages(onSuccess: (List<BingImgModel>) -> Unit = {}) {
        initBingKYNet()
        val countryList= mutableListOf<String>("au","br","ca","cn","de","fr","in","it","jp","es","gb","us")
        var map = mutableMapOf<String, String>()
        map["country"] = countryList.random()

        val kyService: KaiyApiService = NetUtil.getApiPath() as KaiyApiService
        kyService.getBingImages(map)
            .compose(RxHelper.observableIO2Main(topActivity))
            .subscribe(object : MyObserver<MutableList<BingImgModel>>(topActivity) {
                override fun onComplete(isError: Boolean?) {
                    logE(LOG_TAG, "getBingImages onComplete result: ${isError}")
                }

                override fun onFailure(e: Throwable?, errorMsg: String?) {
                    logE(LOG_TAG, "getBingImages onFailure result: ${errorMsg},  ${e}")
                }

                override fun onSuccess(result: MutableList<BingImgModel>?) {
                    result?.let {
                        logE(LOG_TAG, "getBingImages onSuccess result: ${it}, ")

                    }
//                    result?.result?.list?.let {
//                        logE(LOG_TAG, "onSuccess list: ${it}")
//                        onSuccess.invoke(it)
//                    }
                }


            })
    }

    fun fetchImages(onSuccess: (MutableList<BingImgModel>) -> Unit = {}) = scopeNetLife {
        val data= Get<MutableList<BingImgModel>>("feed"){
            val countryList= mutableListOf<String>("au","br","ca","cn","de","fr","in","it","jp","es","gb","us")
            param("country",countryList.random())
        }.await()
        logE(LOG_TAG, "fetchUserInfo onSuccess result: ${data}")
//        val gson = Gson()
//        val type = object : TypeToken<MutableList<BingImgModel>>() {}.type
//        val bingImgList: MutableList<BingImgModel> = gson.fromJson(data.toString(), type)
        onSuccess.invoke(data)
    }


}