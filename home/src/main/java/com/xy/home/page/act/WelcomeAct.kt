package com.xy.home.page.act

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wel)
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
            transparentBar()
            fitsSystemWindows(false)
        }
        gotoMainPage()
    }

    private fun gotoMainPage() {
        //携程 非阻塞式
        jumpJob = GlobalScope.launch {
            Thread.sleep(2000)
            startActivity(Intent(this@WelcomeAct, MainActivity::class.java))
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        jumpJob.cancel()
    }

}