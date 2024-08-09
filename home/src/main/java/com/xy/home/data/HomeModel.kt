package com.xy.home.data

/**
 * @file HomeModel
 * @author zxy
 * @date 2024/8/9 16:52
 * @brief home model
 */

sealed class APPTAG{
    abstract val tag: Int
    object HOME : APPTAG() {
        override val tag: Int
            get() = 0
    }

    object COMMUNITY : APPTAG() {
        override val tag: Int
            get() = 1
    }

    object NOTIFICATION : APPTAG() {
        override val tag: Int
            get() = 2
    }

    object USER : APPTAG() {
        override val tag: Int
            get() = 3
    }
}