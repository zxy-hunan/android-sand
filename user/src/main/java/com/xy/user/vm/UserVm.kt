package com.xy.user.vm

import com.xy.mviframework.base.vm.BaseViewModel
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.intent.UserIntent

/**
 * @file UserVm
 * @author zxy
 * @date 2024/7/22 14:28
 * @brief user
 */
class UserVm : BaseViewModel<UserIntent>() {
    fun getSettingTagList():MutableList<UserTagModel>{
        val list= mutableListOf<UserTagModel>()
        list.add(UserTagModel("1","1",UserTag.OTHER))
        list.add(UserTagModel("1","1",UserTag.OTHER))
        list.add(UserTagModel("setting","1",UserTag.SETTING))
        return list
    }
}