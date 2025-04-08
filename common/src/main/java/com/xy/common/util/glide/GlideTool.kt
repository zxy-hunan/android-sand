package com.xy.common.util.glide
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.xy.common.R
import com.xy.mviframework.network.tool.logE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.InputStream


/**
 * Created by shine on 2019-08-07.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        builder.setDefaultRequestOptions(
            RequestOptions()
                .frame(0).centerCrop()
                .placeholder(R.drawable.icon_bitmap_default)
                .error(R.drawable.icon_bitmap_default)
                .fallback(R.drawable.icon_bitmap_default)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        )
//        val memoryCacheSizeBytes = 1024.times(1024).times(20).toLong()
        val calculator = MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2f).build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        val diskCacheSizeBytes = 1024.times(1024).times(100).toLong()
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes))
    }

    override fun isManifestParsingEnabled() = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        /*registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(SafeOkHttpClient.client)
        )*/
    }
}

@JvmOverloads
fun ImageView.loadAny(
    any: Any?,
    builder: GlideRequest<Drawable>.() -> Unit = {},
) {
    if (any == null) {
        GlideApp.with(this)
            .load(R.drawable.icon_bitmap_default)
            .error(R.drawable.icon_bitmap_default)
            .placeholder(R.drawable.icon_bitmap_default)
            .into(this)
        return
    }
    logE("loadAny:$any")
    GlideApp.with(this)
        .load(any)
        .error(R.drawable.icon_bitmap_default)
        .placeholder(R.drawable.icon_bitmap_default)
        .apply {
            builder.invoke(this)
        }
        .into(this)
}



//改变bitmap尺寸的方法
fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    // 获得图片的宽高
    val width = bm.width
    val height = bm.height
    // 计算缩放比例
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    // 取得想要缩放的matrix参数
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    // 得到新的图片
    return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
}


//获取屏幕宽度的方法
fun getScreenWidth(context: Context): Int {
    val wm = context
        .getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

@JvmOverloads
fun glideCenterCrop(): Transformation<Bitmap> {
    return CenterCrop()
}

@JvmOverloads
fun glideCircleCrop(): Transformation<Bitmap> {
    return CircleCrop()
}




/*
object SafeOkHttpClient {
    val client: OkHttpClient by lazy {
        OkHttpClient().apply {
            interceptors().add(Interceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .header("Referer", "https://pic.netbian.com/") // 根据目标网站要求添加
                    .build()
                chain.proceed(newRequest)
            })
        }
    }
}*/
