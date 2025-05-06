package com.xy.hot.page.frg

import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.hot.databinding.FragmentHotBinding
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HotFrg
 * @author zxy
 * @date 2025/5/6 17:42
 * @brief Hot Video
 */
class HotFrg () : MviFragment<FragmentHotBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun observe() {
    }

    override fun onListens() {
    }
}