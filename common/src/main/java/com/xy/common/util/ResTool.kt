package com.xy.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.BoolRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import com.xy.mviframework.base.BaseApp

/**
 * 资源文件扩展函数
 */

/**
 * 获取颜色
 */
@JvmOverloads
fun Int.asColor(context: Context = BaseApp.instance.applicationContext) =
    ContextCompat.getColor(context, this)

@JvmOverloads
fun String.asColor(context: Context = BaseApp.instance.applicationContext) =
    this.toColorInt()

/**
 * 获取背景
 */
@SuppressLint("SupportAnnotationUsage")
@JvmOverloads
@DrawableRes
fun Int.asDrawable(context: Context = BaseApp.instance.applicationContext) =
    ContextCompat.getDrawable(context, this)

@SuppressLint("SupportAnnotationUsage")
@JvmOverloads
@BoolRes
fun Int.asBool(context: Context = BaseApp.instance.applicationContext) =
    context.resources.getBoolean(this)

@JvmOverloads
@DimenRes
fun Int.asPixelSize(context: Context = BaseApp.instance.applicationContext) = context.resources.getDimensionPixelSize(this)


@JvmOverloads
fun String.asBitmap(context: Context = BaseApp.instance.applicationContext): Bitmap {
    return BitmapFactory.decodeFile(this)
}

