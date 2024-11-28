package com.xy.home.page.act

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.xy.home.databinding.ActivityPostCommunityBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy

/**
 * @file PostCommunityAct
 * @author zxy
 * @date 2024/11/28 16:53
 * @brief post to community
 */
@Route(path = ARouterConfig.Post.PostAcy.PATH)
class PostCommunityAct : MviAcy<ActivityPostCommunityBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
    }

    override fun observe() {
    }

    override fun onListener() {
    }
}