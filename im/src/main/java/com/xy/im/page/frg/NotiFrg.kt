package com.xy.im.page.frg

import com.xy.im.databinding.FragmentNotiBinding
import com.xy.im.intent.NotiIntent
import com.xy.im.vm.NotiVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file NotiFrg
 * @author zxy
 * @date 2024/7/22 14:19
 * @brief im notifi
 */
class NotiFrg : MviFragment<FragmentNotiBinding, NotiVm, NotiIntent>(NotiVm::class.java){
    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun observe() {
    }

    override fun onListens() {
    }
}