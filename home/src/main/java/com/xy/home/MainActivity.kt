package com.xy.home

import android.os.Bundle
import com.xy.home.databinding.ActivityMainBinding
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file MainActivity
 * @author zxy
 * @date 2024/7/18 19:18
 * @brief main
 */
class MainActivity : MviAcy<ActivityMainBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun observe() {
    }


    override fun onListener() {
    }
}