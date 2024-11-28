package com.xy.common.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.text.TextUtilsCompat
import androidx.core.text.inSpans
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.drake.brv.BindingAdapter
import com.dylanc.longan.design.observeDataEmpty
import com.dylanc.longan.isNavigationBarVisible
import com.dylanc.longan.isPasswordVisible
import com.dylanc.longan.navigationBarHeight
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.isGesture
import com.xy.mviframework.base.BaseApp
import com.xy.mviframework.network.tool.logI


/**
 * 页面工具
 */

val mContext
    get() = BaseApp.instance.applicationContext

@JvmOverloads
fun Float.mm2px(context: Context = mContext): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_MM,
        this,
        context.resources.displayMetrics
    )
}

@JvmOverloads
fun Int.mm2px(context: Context = mContext): Float {
    return this.toFloat().mm2px(context)
}

/**
 * 判断是否为 RTL
 */
fun isRtl(context: Context = mContext): Boolean {
    return TextUtilsCompat.getLayoutDirectionFromLocale(
        context.resources.configuration.locale
    ) == ViewCompat.LAYOUT_DIRECTION_RTL
}

@JvmOverloads
fun <T : View> T.autoViewRtl(context: Context = mContext): T {
    this.scaleX = if(isRtl()) -1.0f else 1.0f
    return this
}

@JvmOverloads
fun ImageView.autoImageViewRtl(context: Context = mContext): ImageView {
    this.scaleX = if(isRtl()) -1.0f else 1.0f
    return this
}

@JvmOverloads
fun Float.sp2px(context: Context = mContext): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
}

@JvmOverloads
fun Int.sp2px(context: Context = mContext): Float {
    return this.toFloat().sp2px(context)
}

@JvmOverloads
fun Float.dp2px(context: Context = mContext): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}

@JvmOverloads
fun Int.dp2px(context: Context = mContext): Float {
    return this.toFloat().dp2px(context)
}

fun Int.px2pt(context: Context = mContext): Float {
    val displayMetrics = context.resources.displayMetrics
    val density = displayMetrics.density
    return  this.toBigDecimal().div(density.toBigDecimal()).toFloat()
}

@JvmOverloads
fun Double.dpF2px(context: Context = mContext): Float {
    return this.toFloat().dp2px(context)
}

@JvmOverloads
fun Float.px2dp(context: Context = mContext): Float {
    return this / context.resources.displayMetrics.scaledDensity
}

@JvmOverloads
fun Int.px2dp(context: Context = mContext): Float {
    return this / context.resources.displayMetrics.scaledDensity
}

@JvmOverloads
fun Int.px2mm(context: Context = mContext): Float {
    val dm: DisplayMetrics = context.resources.displayMetrics
    return this / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, dm)
}


/**
 * 这个函数是判断事件触发点是否包含在控件内
 * event.getX()和event.getY()是获取的点击位置的在该点击组件中的 相对位置
 * event.getRawX()和event.getRawY()是获取点击点在屏幕中的位置
 * 而view.getLeft()等是获取的组件在其父容器中的位置，
 * 所以要通过view.getLocationOnScreen(point)来得到该组件在整个屏幕中的位置
 */
fun View.isContain(x: Float, y: Float): Boolean {
    val point = IntArray(2)
    getLocationOnScreen(point)
    return x >= point[0] && x <= point[0] + width && y >= point[1] && y <= point[1] + height
}

/**
 * 设置控件的背景 圆角等
 * radii 长度为8
 */
@JvmOverloads
fun View.setRoundRectBg(color: Int = Color.WHITE, cornerRadius: Int = 15, radii: FloatArray) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius.toFloat())
        cornerRadii = radii
    }
}

/**
 * 控件View.VISIBLE
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isShow(): Boolean = this.visibility == View.VISIBLE

/**
 * 控件View.INVISIBLE
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

/**
 * 控件View.GONE
 */
fun View.remove() {
    this.visibility = View.GONE
}

fun View.showOrRemove(isShow: Boolean) {
    if (isShow) {
        this.show()
    } else {
        this.remove()
    }
}

fun View.showOrHide(isShow: Boolean) {
    if (isShow) {
        this.show()
    } else {
        this.hide()
    }
}

/**
 * 设置背景颜色
 */
fun View.asBgColor(
    @ColorRes resId: Int,
    context: Context = mContext,
) {
    setBackgroundColor(resId.asColor(context))
}

/**
 * 设置文字颜色
 */
fun TextView.asTextColor(
    @ColorRes resId: Int,
    context: Context = mContext,
) {
    setTextColor(resId.asColor(context))
}

/**
 * 设置文字颜色
 */
fun TextView.asTextColor(
    colorString: String,
    context: Context = mContext,
) {
    setTextColor(colorString.asColor(context))
}

/**
 * viewPager2适配fragment
 */
fun ViewPager2.initFragment(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    fragments: List<Fragment>,
): ViewPager2 {
//    offscreenPageLimit = fragments.size - 1
    //设置适配器
    adapter = object : FragmentStateAdapter(fm, lifecycle) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}


fun TextView.addTextWatcher(
    beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
    afterTextChanged: ((Editable) -> Unit)? = null,
) {

    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //// 文本改变之前的处理逻辑
            beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // // 文本正在改变的处理逻辑
            onTextChanged?.invoke(s, start, before, count)
        }

        override fun afterTextChanged(s: Editable) {
            // 文本改变之后的处理逻辑
            afterTextChanged?.invoke(s)
        }

    })

}


/**
 * ViewPager于fragment绑定
 * 通过BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT支持懒加载
 */
fun ViewPager.initFragment(
    manager: FragmentManager,
    fragments: MutableList<Fragment>,
): ViewPager {
    //设置适配器
    adapter = object : FragmentStatePagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount() = fragments.size

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun saveState(): Parcelable? {
            return null
        }
    }
    return this
}

fun TextView.testSize(pxSize: Float) {
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize.toFloat())
}

fun TextView.testSizeByDp(pxSize: Float) {
    this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, pxSize.toFloat())
}

fun RecyclerView.bindEmptyView(owner: LifecycleOwner, view: View) {
    logI("bindEmptyView", "findViewTreeLifecycleOwner")
    this.observeDataEmpty(owner) {
        logI("bindEmptyView", "observeDataEmpty", it)
        if (it) {
            this.remove()
            view.show()
        } else {
            this.show()
            view.remove()
        }
    }
}

fun View.setWidthHeight(width: Int? = null, height: Int? = null) {
    val layoutParams = this.layoutParams as ViewGroup.LayoutParams
    width?.let {
        layoutParams.width = width
    }
    height?.let {
        layoutParams.height = height
    }
    this.layoutParams = layoutParams
}

fun View.setWeight(weight: Int) {
    val layoutParams = this.layoutParams as LinearLayout.LayoutParams
    layoutParams.weight = weight.toFloat()
    this.layoutParams = layoutParams
}

fun View.setMargins(
    marginLeft: Int = this.marginLeft,
    marginTop: Int = this.marginTop,
    marginRight: Int = this.marginRight,
    marginBottom: Int = this.marginBottom
) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    this.layoutParams = layoutParams
}




@JvmOverloads
fun View.marginTopStatusBarHeight() {
    this.post {
        this.setMargins(marginTop = STATUS_BAR_HEIGHT)
    }
}



fun String.appendImgTextEnd(
    @DrawableRes resourceId: Int,
    context: Context = BaseApp.instance.applicationContext,
):SpannableString{
    var  contentSpannableString:String = "${this}  ##"
    return SpannableString(contentSpannableString).appendImgTextEnd(resourceId,contentSpannableString.length-2,contentSpannableString.length)
}

fun SpannableString.appendImgTextEnd(
    @DrawableRes resourceId: Int,
    start:Int,end:Int,
    context: Context = BaseApp.instance.applicationContext,
):SpannableString{
    val drawable: Drawable = context.resources.getDrawable(resourceId,null)
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    var imageSpan  = ImageSpan(drawable)

    this.setSpan(imageSpan,start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

/**
 * 密码显示隐藏
 */
fun EditText.showPwdOb(checkBox: CheckBox) {
    checkBox.setOnCheckedChangeListener { compoundButton, b ->
        val cursorPosition = this.selectionStart
        isPasswordVisible = b
        this.setSelection(cursorPosition)
    }
}

/**
 * 导航栏高度
 */
val NAVIGATION_BAR_HEIGHT
    get() = navigationBarHeight

/**
 * 状态栏高度
 */
val STATUS_BAR_HEIGHT
    get() = ImmersionBar.getStatusBarHeight(BaseApp.instance.applicationContext)

val IS_GESTURE
    get() = BaseApp.instance.applicationContext.isGesture


val RecyclerView.brvAdapter
    get() = adapter as? BindingAdapter


fun View.obEtEnabled(editText: TextView, length: Int) {
    val textViewMap: MutableMap<TextView, Int> = mutableMapOf()

    fun checkEditText() {
        var textHasEmpty = false
        if (textViewMap.filter { it.key.text.length < it.value }.isNotEmpty()) {
            textHasEmpty = true
        }
        if (textHasEmpty) {
            isEnabled = false
            alpha = 0.5f
        } else {
            isEnabled = true
            alpha = 1.0f
        }
    }

    editText.doAfterTextChanged {
        checkEditText()
    }
    textViewMap[editText] = length
    checkEditText()
}

/**
 * 获取设备的最小宽度（以 dp 为单位）
 */
 fun getMinimumWidthInDp(context: Context): Int {
    val resources = context.resources
    val metrics = resources.displayMetrics

    val widthPixels = metrics.widthPixels
    val heightPixels = metrics.heightPixels

    val minPixelWidth = kotlin.math.min(widthPixels, heightPixels)

    val density = metrics.density
    val minDpWidth = (minPixelWidth / density).toInt()

    return minDpWidth
}

fun View.obEtEnabled(vararg editText: TextView) {
    val textViewMap: MutableMap<TextView, Int> = mutableMapOf()

    fun checkEditText() {
        var textHasEmpty = false
        if (textViewMap.filter { it.key.text.length < it.value }.isNotEmpty()) {
            textHasEmpty = true
        }
        if (textHasEmpty) {
            isEnabled = false
            alpha = 0.5f
        } else {
            isEnabled = true
            alpha = 1.0f
        }
    }

    editText.forEach {
        it.doAfterTextChanged {
            checkEditText()
        }
        textViewMap[it] = 1
    }
    checkEditText()
}







