package com.xy.common.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.dylanc.longan.topActivity
import com.dylanc.viewbinding.inflate
import com.xy.common.databinding.LayoutTitlebarBinding
import com.xy.common.util.clickDebounce

/**
 * @file TitleBarLayout
 * @author zxy
 * @date 2025/2/6 11:18
 * @brief titlebar
 */
class TitleBarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val binding = inflate<LayoutTitlebarBinding>()
    init {
        inflate(context, com.xy.common.R.layout.layout_titlebar, this)
        binding.ivBack.clickDebounce {
            topActivity.finish()
        }

    }

    fun setTitle(title:String){
        binding.tvTitle.text = title
    }
}