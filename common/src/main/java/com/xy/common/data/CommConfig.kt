package com.xy.common.data

import android.graphics.Typeface
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


sealed class AppFontsType {
    abstract val appFontsType: String

    object DEFAULT : AppFontsType() {
        override val appFontsType: String
            get() = Typeface.DEFAULT.toString()
    }

    object BOLD : AppFontsType() {
        override val appFontsType: String
            get() = Typeface.BOLD.toString()
    }

    object BARLOW_SEMI_BOLD : AppFontsType() {
        override val appFontsType: String
            get() = "fonts/Barlow-SemiBold.ttf"
    }

    object ALI_MA_MA : AppFontsType() {
        override val appFontsType: String
            get() = "fonts/AlimamaAgileVF-Thin.ttf"
    }

}