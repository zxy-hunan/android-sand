package com.xy.user.vm

import UserApiService
import android.util.Log
import com.xy.common.vm.ZhiNiaoBaseViewModel
import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.mviframework.network.def.apiRetrofit
import com.xy.user.R
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.intent.UserIntent

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
            Log.e("UserVm", "articleList: onError")
        }, onSuccess = {

//            _intent.emitCoroutine(MainIntent.ArticleList(it))
        }, onCompleteData = {
            Log.e("UserVm", "articleList: onSuccess ${it.toString()}")
        })
    }

    fun getSettingTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("个人中心", "1", UserTag.OTHER, R.drawable.user_smile))
        list.add(UserTagModel("关于我们", "1", UserTag.OTHER, R.drawable.user_help))
        list.add(UserTagModel("提交建议", "1", UserTag.OTHER, R.drawable.user_help))
        list.add(UserTagModel("设置", "1", UserTag.SETTING, R.drawable.user_setting))
        return list
    }


    fun getTopTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("文章", "1", UserTag.OTHER))
        list.add(UserTagModel("点赞", "1", UserTag.OTHER))
        list.add(UserTagModel("评论", "1", UserTag.SETTING))
        return list
    }


    fun getMiddleTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("已签到", "1", UserTag.OTHER, R.mipmap.me_set1))
        list.add(UserTagModel("幸运转盘", "1", UserTag.OTHER, R.mipmap.me_set2))
        list.add(UserTagModel("挑战", "1", UserTag.SETTING, R.mipmap.me_set3))
        list.add(UserTagModel("福利兑换", "1", UserTag.SETTING, R.mipmap.me_set4))
        return list
    }


    fun getHighTagList(): MutableList<UserTagModel> {
        val list = mutableListOf<UserTagModel>()
        list.add(UserTagModel("后端", "1", UserTag.OTHER, R.mipmap.java))
        list.add(UserTagModel("前端", "1", UserTag.OTHER, R.mipmap.javascript))
        list.add(UserTagModel("移动端", "1", UserTag.SETTING, R.mipmap.android))
        list.add(UserTagModel("其他", "1", UserTag.SETTING, R.mipmap.other))
        return list
    }

}