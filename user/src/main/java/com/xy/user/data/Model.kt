package com.xy.user.data

/**
 * @file Model
 * @author zxy
 * @date 2024/8/9 10:55
 * @brief user model
 */

data class UserTagModel(var tagName: String, var tagUrl: String, var tag: String, var res: Int = 0)


sealed class UserTag {
    abstract val tag: String

    object SETTING : UserTag() {
        override val tag: String
            get() = "1"
    }

    object OTHER : UserTag() {
        override val tag: String
            get() = "2"
    }

    object PERSONAL : UserTag() {
        override val tag: String
            get() = "3"
    }


    object ADJUST : UserTag() {
        override val tag: String
            get() = "4"
    }
}

sealed class ArticleTag {
    abstract val tag: String

    object ZERO : ArticleTag() {
        override val tag: String
            get() = "0"
    }

    object ONE : ArticleTag() {
        override val tag: String
            get() = "1"
    }

    object TWO : ArticleTag() {
        override val tag: String
            get() = "2"
    }

    object THREE : ArticleTag() {
        override val tag: String
            get() = "3"
    }
}