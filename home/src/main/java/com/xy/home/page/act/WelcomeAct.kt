package com.xy.home.page.act

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import com.google.android.material.animation.AnimatorSetCompat.playTogether
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.AppFontsType
import com.xy.common.util.AppFontsUtil
import com.xy.common.util.mContext
import com.xy.home.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @file WelcomeAct
 * @author zxy
 * @date 2024/7/19 8:55
 * @brief welcome page
 */
class WelcomeAct : Activity() {
    lateinit var jumpJob: Job
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wel)
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
            transparentBar()
            fitsSystemWindows(false)
        }
        var tvSlogen = findViewById<TextView>(R.id.tv_slogen)
        AppFontsUtil.setControlFonts(mContext, tvSlogen, AppFontsType.BARLOW_SEMI_BOLD)
        gotoMainPage()

        /*splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            splashScreenViewProvider.iconView?.let {
                showSplashIconExitAnimator(it) {
                    splashScreenViewProvider.remove()
                }
            }
        }*/
    }



    private fun showSplashIconExitAnimator(iconView: View, onExit: () -> Unit = {}) {
        val alphaOut = ObjectAnimator.ofFloat(iconView, "alpha", 1f, 0f).apply {
            duration = 300 // 动画时长 300ms
            interpolator = AccelerateInterpolator() // 加速效果
        }

        val scaleOut = ObjectAnimator.ofPropertyValuesHolder(
            iconView,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0f), // X轴缩放
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0f)  // Y轴缩放
        ).apply {
            duration = 400
            interpolator = OvershootInterpolator() // 弹性效果
        }

        val slideUp = ObjectAnimator.ofFloat(
            iconView,
            "translationY",
            0f,
            -iconView.height.toFloat() // 向上滑动一个视图高度
        ).apply {
            duration = 500
            interpolator = DecelerateInterpolator() // 减速效果
        }


        AnimatorSet().run {
            playTogether(alphaOut, scaleOut, slideUp)
            doOnEnd { onExit() }
        }
    }

    private fun gotoMainPage() {
        //携程 非阻塞式
        jumpJob = GlobalScope.launch {
            Thread.sleep(3000)
            startActivity(Intent(this@WelcomeAct, TestAcy::class.java))
//            startActivity(Intent(this@WelcomeAct, MainActivity::class.java))
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        jumpJob.cancel()
    }

}