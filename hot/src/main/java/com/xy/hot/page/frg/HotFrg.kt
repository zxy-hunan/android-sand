package com.xy.hot.page.frg


import android.content.Intent
import android.content.Intent.getIntent
import android.view.View
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.xy.common.data.model.LiuVideoModel
import com.xy.hot.Tiktok2Adapter
import com.xy.hot.databinding.FragmentHotBinding
import com.xy.hot.intent.HotIntent
import com.xy.hot.util.Utils
import com.xy.hot.util.cache.PreloadManager
import com.xy.hot.vm.HotVM
import com.xy.hot.widget.VerticalViewPager
import com.xy.hot.widget.controller.TikTokController
import com.xy.hot.widget.render.TikTokRenderViewFactory
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.util.L

/**
 * @file HotFrg
 * @author zxy
 * @date 2025/5/6 17:42
 * @brief Hot Video
 */
class HotFrg() : MviFragment<FragmentHotBinding, HotVM, HotIntent>(HotVM::class.java) {
    /**
     * 当前播放位置
     */
    private var mCurPos = 0
    private val mVideoList: MutableList<LiuVideoModel> = mutableListOf()
    private var mTiktok2Adapter: Tiktok2Adapter? = null
    private var mPreloadManager: PreloadManager? = null

    private var mController: TikTokController? = null

    var mVideoView: VideoView? = null
    override fun initView() {
        initViewPager()
        initVideoView()
        mPreloadManager = PreloadManager.getInstance(mActivity)
    }


    override fun lazyLoad() {
        viewModel.fetchVideosReq()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is HotIntent.KyVideoModelList -> {
                    addData(it.list)
//                    binding.vvp.setCurrentItem(0)
//                    binding.vvp.post(Runnable { startPlay(0) })
                }

                else -> {}
            }
        }
    }

    override fun onListens() {
    }

    fun addData(list:MutableList<LiuVideoModel>) {
        mVideoList.addAll(list)
        mTiktok2Adapter!!.notifyDataSetChanged()
    }

    private fun initViewPager() {
        binding.vvp.apply {
            offscreenPageLimit = 4
            mTiktok2Adapter = Tiktok2Adapter(mVideoList)
            adapter = mTiktok2Adapter
            overScrollMode = View.OVER_SCROLL_NEVER

            setOnPageChangeListener(object : SimpleOnPageChangeListener() {

                private var mCurItem = 0

                /**
                 * VerticalViewPager是否反向滑动
                 */
                private var mIsReverseScroll = false

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position == mCurItem) {
                        return
                    }
                    mIsReverseScroll = position < mCurItem
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == mCurPos) return
                    startPlay(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                        mCurItem = binding.vvp.currentItem
                    }
                    if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                        mPreloadManager!!.resumePreload(mCurPos, mIsReverseScroll)
                    } else {
                        mPreloadManager!!.pausePreload(mCurPos, mIsReverseScroll)
                    }
                }
            })

        }
    }


    private fun startPlay(position: Int) {
        val count: Int = binding.vvp.childCount
        for (i in 0 until count) {
            val itemView: View = binding.vvp.getChildAt(i)
            val viewHolder = itemView.tag as Tiktok2Adapter.ViewHolder
            if (viewHolder.mPosition === position) {
                mVideoView?.release()
                Utils.removeViewFormParent(mVideoView)
                val tiktokBean: LiuVideoModel = mVideoList[position]
                val playUrl = mPreloadManager!!.getPlayUrl(tiktokBean.playurl)
                L.i("startPlay: position: $position  url: $playUrl")
                mVideoView?.setUrl(playUrl)
                //请点进去看isDissociate的解释
                mController!!.addControlComponent(viewHolder.mTikTokView, true)
                viewHolder.mPlayerContainer.addView(mVideoView, 0)
                mVideoView?.start()
                mCurPos = position
                break
            }
        }
    }


    private fun initVideoView() {
        mVideoView = VideoView(mActivity)
        mVideoView?.setLooping(true)

        //以下只能二选一，看你的需求
        mVideoView?.setRenderViewFactory(TikTokRenderViewFactory.create())
        //        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        mController = TikTokController(mActivity)
        mVideoView?.setVideoController(mController)
    }


}