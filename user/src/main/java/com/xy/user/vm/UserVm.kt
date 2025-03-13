package com.xy.user.vm

import SharedFlowBus
import UserApiService
import android.util.Log
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
import kotlinx.coroutines.flow.SharedFlow

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

    fun login(username: String = "",password: String = "") {
        val dftMap = mutableMapOf<String, String>()
        dftMap["username"] = username
        dftMap["password"] = password

        apiService.login(dftMap).HttpCoroutine(onError = {
            Log.e("UserVm", "token: onError")
        }, onSuccess = {
            Log.e("UserVm", "token: onSuccess ${it}")
            _intent.emitCoroutine(UserIntent.LoginSuccess(it))
            MmkvRepository.loginToken = it
            SharedFlowBus.post(key = FlowBusTag.LoginSuccess(),
                value = 0)

        }, onCompleteData = {
            Log.e("UserVm", "onCompleteData: ${it.toString()}")
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


    fun articleNum(userId: String = "", type: ArticleTotalNum = ArticleTotalNum.Common, onSuccess: (Int) -> Unit = {}){
        articleNumReq(userId, type.no, onSuccess)
    }




    fun getSettingTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("个人中心", "1", UserTag.OTHER.tag, R.drawable.user_smile))
        list.add(UserTagModel("关于我们", "1", UserTag.OTHER.tag, R.drawable.user_help))
        list.add(UserTagModel("提交建议", "1", UserTag.OTHER.tag, R.drawable.user_help))
        list.add(UserTagModel("设置", "1", UserTag.SETTING.tag, R.drawable.user_setting))
        return list
    }


    fun getTopTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("文章", "1", UserTag.OTHER.tag))
        list.add(UserTagModel("点赞", "1", UserTag.OTHER.tag))
        list.add(UserTagModel("评论", "1", UserTag.SETTING.tag))
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