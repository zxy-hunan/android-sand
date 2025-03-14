package com.xy.common.data

import com.xy.common.R

/**
 * @file CommConfig
 * @author zxy
 * @date 2024/7/22 15:37
 * @brief config
 */
object CommConfig {
    var pageNo: Int = 1
    var pageSize:String="10"
}

sealed class Common {
    abstract val no: Int


    object Default : Common() {
        override val no: Int
            get() = 2


    }

    object Hot : Common() {
        override val no: Int
            get() = 5


    }

}


sealed class ArticleTotalNum {
    abstract val no: Int


    object Common : ArticleTotalNum() {
        override val no: Int
            get() = 0
    }

    object Article : ArticleTotalNum() {
        override val no: Int
            get() = 1
    }

    object Star : ArticleTotalNum() {
        override val no: Int
            get() = 2
    }

}