package com.xy.user.vm

import SharedFlowBus
import UserApiService
import android.util.Log
import com.kongzue.dialogx.dialogs.TipDialog
import com.kongzue.dialogx.dialogs.WaitDialog
import com.xy.common.data.ArticleTotalNum
import com.xy.common.data.Common
import com.xy.common.util.MmkvRepository
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.def.apiRetrofit
import com.xy.user.R
import com.xy.user.data.ArticleTag
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.intent.UserIntent
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.SharedFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @file UserVm
 * @author zxy
 * @date 2024/7/22 14:28
 * @brief user
 */
class UserVm : ZhiNiaoBaseViewModel<UserIntent>() {
     val apiService: UserApiService by lazy {
        apiRetrofit.create(UserApiService::class.java)
    }

    fun register(username: String = "",password: String = "") {
        val dftMap = mutableMapOf<String, String>()
        dftMap["username"] = username
        dftMap["password"] = password
        dftMap["code"] = ""
        dftMap["uuid"] = ""

        apiService.register(dftMap).HttpCoroutine(onError = {
            TipDialog.show("register: fail,${it.message}", WaitDialog.TYPE.ERROR)
        }, onSuccess = {
            _intent.emitCoroutine(UserIntent.RegisterSuccess(true))
        })
    }

    fun login(username: String = "",password: String = "") {
        val dftMap = mutableMapOf<String, String>()
        dftMap["username"] = username
        dftMap["password"] = password

        apiService.login(dftMap).HttpCoroutine(onError = {
            Log.e("UserVm", "token: onError")
        }, onSuccess = {
            Log.e("UserVm", "token: onSuccess ${it}")
            _intent.emitCoroutine(UserIntent.LoginSuccess(it))
            MmkvRepository.loginToken = "Bearer "+it
            SharedFlowBus.post(key = FlowBusTag.LoginSuccess(),
                value = 0)

        })
    }


    fun getInfo(onSuccess: () -> Unit={}) {
        apiService.getInfo(MmkvRepository.loginToken).HttpCoroutine(onError = {
        }, onOriginSuccess = {
            Log.e("UserVm", "token: onSuccess ${it}")
            it.user?.let {
                MmkvRepository.userModel = it
                _intent.emitCoroutine(UserIntent.UserInfoSuccess(it))
                onSuccess.invoke()
            }
        })
    }



    var isRefresh = true

    fun refresh(comId: String="") {
        isRefresh = true
        page = 1
        articleList(comId)
    }

    fun loadMore(comId: String="") {
        isRefresh = false
        page++
        articleList(comId)
    }

    private fun articleList(comId: String = "") {
        articleListReq(comId) {
            _intent.emitCoroutine(UserIntent.ArticleList(it))
        }
    }


    fun articleNum(userId: String = "", type: ArticleTotalNum = ArticleTotalNum.Common, onSuccess: (Int) -> Unit = {}) {
        articleNumReq(userId, type.no, onSuccess)
    }


    suspend fun wrapArticleNumRequest(
        param: String,
        type: ArticleTotalNum
    ): String = suspendCoroutine { continuation ->
        articleNum(param, type) { result ->
            continuation.resume(result.toString())
        }
    }

    fun getSettingTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("个人中心", "1", UserTag.PERSONAL.tag, R.drawable.user_personal))
        list.add(UserTagModel("关于我们", "1", UserTag.OTHER.tag, R.drawable.user_about))
        list.add(UserTagModel("提交建议", "1", UserTag.ADJUST.tag, R.drawable.user_ques))
        list.add(UserTagModel("设置", "1", UserTag.SETTING.tag, R.drawable.user_setting))
        return list
    }


    fun getTopTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("文章", "0", UserTag.OTHER.tag))
        list.add(UserTagModel("点赞", "0", UserTag.OTHER.tag))
        list.add(UserTagModel("评论", "0", UserTag.SETTING.tag))
        return list
    }


    fun getMiddleTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("已签到", "1", UserTag.OTHER.tag, R.mipmap.me_set1))
        list.add(UserTagModel("幸运转盘", "1", UserTag.OTHER.tag, R.mipmap.me_set2))
        list.add(UserTagModel("挑战", "1", UserTag.SETTING.tag, R.mipmap.me_set3))
        list.add(UserTagModel("福利兑换", "1", UserTag.SETTING.tag, R.mipmap.me_set4))
        return list
    }


    fun getHighTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("后端", "1", ArticleTag.ZERO.tag, R.mipmap.java))
        list.add(UserTagModel("前端", "1", ArticleTag.ONE.tag, R.mipmap.javascript))
        list.add(UserTagModel("移动端", "1", ArticleTag.TWO.tag, R.mipmap.android))
        list.add(UserTagModel("其他", "1", ArticleTag.THREE.tag, R.mipmap.other))
        return list
    }

}