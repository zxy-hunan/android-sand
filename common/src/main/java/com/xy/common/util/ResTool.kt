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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xy.mviframework.base.BaseApp
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.gson
import com.xy.mviframework.network.tool.logE
import java.io.InputStream
import java.nio.charset.Charset

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

@JvmOverloads
fun readJsonFile(fileName: String, context: Context): String {
    var str = ""
    try {
        val inputStream: InputStream = context.assets.open("culture/$fileName")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        logE(LOG_TAG, "readJsonFile inputStream close:")
        str = String(buffer, Charset.forName("UTF-8"))
    } catch (e: Exception) {
        logE(LOG_TAG, "readJsonFile error:${e.message}")
    }
    return str
}

@JvmOverloads
inline fun <reified T> String.parseJson(jsonString: String): T {
    val t = gson.fromJson(jsonString, T::class.java)
    return t
}
@JvmOverloads
inline fun <reified T> String.parseCultureJson(): List<T> {
    val gson = Gson()
    val listType = object : TypeToken<List<T>>() {}.type
    return gson.fromJson(this, listType)
}
