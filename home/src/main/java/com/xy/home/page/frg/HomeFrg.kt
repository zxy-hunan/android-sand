package com.xy.home.page.frg

import com.xy.home.databinding.FragmentHomeBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief homefragment
 */
class HomeFrg() :MviFragment<FragmentHomeBinding,MainVm,MainIntent>(MainVm::class.java) {
    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun observe() {
    }

    override fun onListens() {
    }
}