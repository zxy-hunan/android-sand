package com.xy.home.vm

import com.xy.common.data.Common
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.CnModel
import com.xy.common.data.model.KyVideoModel
import com.xy.common.util.MmkvRepository
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.home.R
import com.xy.home.api.MainApiService
import com.xy.home.intent.MainIntent
import com.xy.mviframework.network.def.apiRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @file MainVm
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainVm : ZhiNiaoBaseViewModel<MainIntent>() {
    val mainService: MainApiService by lazy {
        apiRetrofit.create(MainApiService::class.java)
    }


    var isRefresh = true

    fun refresh(com: Common) {
        isRefresh = true
        page = 1
        articleList("${com.no}")
    }

    fun loadMore(com: Common) {
        isRefresh = false
        page++
        articleList("${com.no}")
    }

    fun articleList(comId: String = "${Common.Hot.no}") {
        articleListReq(comId) {
            val allList = mutableListOf<ArticleModel>()
            allList.addAll(it)
            getKyVideoResult(page) {
                val videoList = mutableListOf<ArticleModel>()
                it.forEach {
                    val model = ArticleModel()
                    model.videos = it
                    videoList.add(model)
                }
                allList.addAll(videoList)
                allList.shuffle()
                _intent.emitCoroutine(MainIntent.ArticleList(allList))
            }
        }
    }


//    {content: "哈哈", userId: 100, articleId: 74, parId: null, replyId: null}

    fun articleComm(content: String = "", userId: String = "", articleId: String = "", parId: String = "", replyId: String = "", onSuccess: () -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>()
        dftMap["content"] = content
        dftMap["userId"] = userId
        dftMap["articleId"] = articleId
        dftMap["parId"] = parId
        dftMap["replyId"] = replyId

        mainService.articleComm(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            onSuccess.invoke()
        })
    }


    fun commList(pageNum: Int = 1, articleId: String = "") {
        val dftMap = mutableMapOf<String, String>()
        dftMap["pageNum"] = "$pageNum"
        dftMap["pageSize"] = "10"
        dftMap["articleId"] = articleId

        mainService.commList(MmkvRepository.loginToken, dftMap).HttpCoroutine(
            onError = {
            }, onSuccess = {
                _intent.emitCoroutine(MainIntent.CommList(it))
            })
    }


    fun articleStar(articleId: String = "", userId: String = "", onSuccess: () -> Unit = {}) {
        val dftMap = mutableMapOf<String, String>()
        dftMap["articleId"] = articleId
        dftMap["userId"] = userId

        mainService.articleStar(MmkvRepository.loginToken, dftMap).HttpCoroutine(onError = {
        }, onSuccess = {
            onSuccess.invoke()
        })
    }


    fun getKyImagesResult() {
        getKyImages {
            _intent.emitCoroutine(MainIntent.KyImageModelList(it))
        }
    }

    fun getKyVideoResult(page: Int = 1, onSuccess: (List<KyVideoModel>) -> Unit = {}) {
        getKyVideos(page = page, onSuccess = onSuccess)
    }


    fun getCultureList() {
        var list = mutableListOf<CnModel>(
            CnModel(0, 0, "顾思文", mutableListOf(CnModel(name = "经典", url = R.drawable.ic_cn1), CnModel(name = "现代", url = R.drawable.ic_cn2), CnModel(name = "名句", url = R.drawable.ic_cn3))),
            CnModel(0, 0, "现代", mutableListOf(CnModel(name = "汉字", url = R.drawable.ic_cn4), CnModel(name = "成语", url = R.drawable.ic_cn5), CnModel(name = "词语", url = R.drawable.ic_cn6))),
            CnModel(0, 0, "传统", mutableListOf(CnModel(name = "节日", url = R.drawable.ic_cn7), CnModel(name = "节气", url = R.drawable.ic_cn8)))
        )
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            _intent.emitCoroutine(MainIntent.CultureList(list))
        }

    }

}