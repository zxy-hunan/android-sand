package com.xy.user.data

/**
 * @file Model
 * @author zxy
 * @date 2024/8/9 10:55
 * @brief user model
 */

data class UserTagModel(var tagName: String, var tagUrl: String, var tag: UserTag)



sealed class UserTag{
    abstract val tag: String

    object SETTING : UserTag() {
        override val tag: String
            get() = "1"
    }

    object OTHER : UserTag() {
        override val tag: String
            get() = "2"
    }
}