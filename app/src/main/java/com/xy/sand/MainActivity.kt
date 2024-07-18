package com.xy.sand

import android.os.Bundle
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.sand.databinding.ActivityMainBinding

class MainActivity : MviAcy<ActivityMainBinding,MainVm,MainIntent>(MainVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun observe() {
    }


    override fun onListener() {
    }
}