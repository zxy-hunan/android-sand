package com.xy.common.util

import android.view.View
import com.dylanc.longan.activity
import com.dylanc.longan.doOnClick
import com.dylanc.longan.viewTags

/**
 * 点击事件防抖
 */

/**
 * 防止重复点击
 * @param clickIntervals 重复间隔
 * @param isSharingIntervals 是否整个页面的防抖用一个时间
 * @param onClick  事件响应
 */
@JvmOverloads
fun View.clickDebounce(clickIntervals: Int = 800, isSharingIntervals: Boolean = false, onClick: (View) -> Unit) {
    doOnClick(clickIntervals = clickIntervals, isSharingIntervals = isSharingIntervals, block = {
        onClick.invoke(this)
    })
}

/*var bannerLastTime = 0L
fun Banner<*, *>.setOnBannerListenerDebounce(interval: Long = 600, clickListener: (adapter: Banner<*, *>, position: Int) -> Unit): Banner<*, *> {
    this.setOnBannerListener { data, position ->
        val currentTime = System.currentTimeMillis()
        if (bannerLastTime != 0L && (currentTime - bannerLastTime < interval)) {
            return@setOnBannerListener
        }
        bannerLastTime = currentTime
        clickListener(this, position)
    }
    return this
}*/

private var View.lastClickTime: Long? by viewTags(com.dylanc.longan.R.id.tag_last_click_time)
fun View.doDoubleClick(clickIntervals: Int = 300, isSharingIntervals: Boolean = false, block: () -> Unit) =
    setOnClickListener {
        val view = if (isSharingIntervals) context.activity?.window?.decorView ?: this else this
        val currentTime = System.currentTimeMillis()
        val lastTime = view.lastClickTime ?: 0L
        if (currentTime - lastTime > clickIntervals) {
            view.lastClickTime = currentTime
        } else {
            block()
        }
    }