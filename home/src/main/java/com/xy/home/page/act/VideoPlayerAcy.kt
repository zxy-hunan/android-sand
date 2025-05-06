package com.xy.home.page.act

import ARouterConfig
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.longan.intentExtras
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.model.KyVideoModel
import com.xy.common.util.clickDebounce
import com.xy.common.util.glide.loadAny
import com.xy.home.databinding.ActivityPlayerBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.MviAcy
import xyz.doikki.videocontroller.StandardVideoController


/**
 * @file VideoPlayerAcy
 * @author zxy
 * @date 2025/5/6 16:20
 * @brief video player
 */

@Route(path = ARouterConfig.Video.VideoPlayerAcy.PATH)
class VideoPlayerAcy : MviAcy<ActivityPlayerBinding, MainVm, MainIntent>(MainVm::class.java) {

//    private val playUrl by intentExtras(ARouterConfig.Video.VideoPlayerAcy.VIDEO_URL, "")

    private val model by intentExtras<KyVideoModel>(ARouterConfig.Video.VideoPlayerAcy.VIDEO_MODE, KyVideoModel())

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarColor(com.xy.common.R.color.black)
            statusBarDarkFont(false)
            titleBarMarginTop(binding.viewLine)
        }

        model?.let {
            if (it.playUrl.isNotBlank()) {
                binding.player.setUrl(it.playUrl) //设置视频地址

                val controller = StandardVideoController(this)
                controller.addDefaultControlComponent(it.title, false)
                binding.player.setVideoController(controller) //设置控制器

                binding.player.start() //开始播放，不调用则不自动播放
            }
            binding.ivHead.loadAny(it.userPic ?: "")
            binding.tvName.text = it.userName ?: ""
            binding.tvContent.text = it.title ?: ""
        }

        binding.ivBack.clickDebounce {
            finish()
        }
    }

    override fun observe() {
    }

    override fun onListener() {
    }

    override fun onPause() {
        super.onPause()
        binding.player.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.player.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.player.release()
    }

    override fun onBackPressed() {
        if (!binding.player.onBackPressed()) {
            super.onBackPressed()
        }
    }

}