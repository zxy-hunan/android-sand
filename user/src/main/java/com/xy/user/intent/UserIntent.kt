package com.xy.user.intent

import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.UserModel
import com.xy.mviframework.base.vm.BaseIntent

/**
 * @file UserIntent
 * @author zxy
 * @date 2024/7/22 14:28
 * @brief user
 */
sealed class UserIntent : BaseIntent() {
    data class LoginSuccess(val token:String) : UserIntent()

    data class UserInfoSuccess(val info: UserModel) : UserIntent()

    data class ArticleList(val list:List<ArticleModel>) : UserIntent()
}