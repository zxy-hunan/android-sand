package com.xy.home.page.act

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.xy.home.databinding.ActivityHomeDetailBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file HomeDetailAcy
 * @author zxy
 * @date 2024/11/28 14:48
 * @brief detail
 */
@Route(path = ARouterConfig.Home.HomeDetailAcy.PATH)
class HomeDetailAcy : MviAcy<ActivityHomeDetailBinding, MainVm, MainIntent>(MainVm::class.java){
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun observe() {
    }

    override fun onListener() {
    }
}