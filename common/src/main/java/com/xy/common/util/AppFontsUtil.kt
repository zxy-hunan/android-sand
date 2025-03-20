package com.xy.common.util

import android.content.Context
import android.graphics.Typeface
import android.widget.CheckBox
import android.widget.TextView
import com.xy.common.data.AppFontsType

object AppFontsUtil {
    private fun createFromAsset(context: Context,appFontPath: String): Typeface {
        return Typeface.createFromAsset(context.assets, appFontPath)
    }


    @JvmStatic
    fun setControlFonts(context: Context,textView : TextView, appFontsType: AppFontsType) {
        val customFont: Typeface = when (appFontsType) {
                AppFontsType.ALI_MA_MA -> createFromAsset(context,AppFontsType.ALI_MA_MA.appFontsType)
                AppFontsType.BARLOW_SEMI_BOLD -> createFromAsset(context,AppFontsType.BARLOW_SEMI_BOLD.appFontsType)
                AppFontsType.BOLD -> Typeface.defaultFromStyle(Typeface.BOLD)
                AppFontsType.DEFAULT -> Typeface.DEFAULT
                else -> Typeface.DEFAULT
            }
        textView.typeface = customFont
    }

    @JvmStatic
    fun setControlFonts(context: Context, textView : CheckBox, appFontsType: AppFontsType) {
        val customFont: Typeface = when (appFontsType) {
            AppFontsType.ALI_MA_MA -> createFromAsset(context,AppFontsType.ALI_MA_MA.appFontsType)
            AppFontsType.BARLOW_SEMI_BOLD -> createFromAsset(context,AppFontsType.BARLOW_SEMI_BOLD.appFontsType)
            AppFontsType.BOLD -> Typeface.defaultFromStyle(Typeface.BOLD)
            AppFontsType.DEFAULT -> Typeface.DEFAULT
            else -> Typeface.DEFAULT
        }
        textView.typeface = customFont
    }
}

fun TextView.setSemiBoldFonts(){
    AppFontsUtil.setControlFonts(mContext, this, AppFontsType.BARLOW_SEMI_BOLD)
}

fun TextView.setAliFonts(){
    AppFontsUtil.setControlFonts(mContext, this, AppFontsType.ALI_MA_MA)
}