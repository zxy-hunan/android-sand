package com.xy.home.page.frg

import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.AppFontsType
import com.xy.common.data.Common
import com.xy.home.R
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.BingImgModel
import com.xy.common.data.model.KyVideoModel
import com.xy.common.util.AppFontsUtil
import com.xy.common.util.MmkvRepository
import com.xy.common.util.clickDebounce
import com.xy.common.util.setSemiBoldFonts
import com.xy.common.view.bindArticleList
import com.xy.home.databinding.FragmentHomeBinding
import com.xy.home.databinding.ItemTopArticleBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @file HomeFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief homefragment
 */
class HomeFrg() : MviFragment<FragmentHomeBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initView() {

        immersionBar {

//            statusBarAlpha(0.1f)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }

//        binding.ivHome.loadAny(if(MmkvRepository.homeFlag) R.drawable.home_am else R.drawable.home_dance)

        binding.ivHome.setAnimation(if(MmkvRepository.homeFlag) com.xy.common.R.raw.bookn else com.xy.common.R.raw.dance)
        binding.ivHome.playAnimation()


        binding.rvTop.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(5, dp = true)
            includeVisible = true
        }.setup {
            addType<ArticleModel>(R.layout.item_top_article)

            onBind {
                val data = getModel<ArticleModel>()
                val item = getBinding<ItemTopArticleBinding>()

                item.tvNum.text = (position + 1).toString() + ""
                item.tvNum.setSemiBoldFonts()

                item.tvNum.shapeDrawableBuilder.solidColor = when (modelPosition) {
                    0 -> Color.parseColor("#F75000")
                    1 -> Color.parseColor("#FFA042")
                    2 -> Color.parseColor("#FFE66F")
                    else -> Color.parseColor("#FF5722")
                }
                item.tvNum.shapeDrawableBuilder.intoBackground()

                item.tvConetent.text = data.title
                AppFontsUtil.setControlFonts(mContext, item.tvConetent, AppFontsType.BARLOW_SEMI_BOLD)
            }
        }

        binding.rvList.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(10, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
            startVisible = true
        }.bindArticleList()

        setHomeFlagClick()
        binding.flSearch.clickDebounce{
            ARouterConfig.Home.SearchAcy.push()
        }
    }

    override fun lazyLoad() {
//        binding.prf.refresh()

    }

    override fun observe() {
        intentCallBack { it ->
            when (it) {
                is MainIntent.ArticleList -> {
                    Log.e("MainIntent", "ArticleList: ")
//                    toast("it.size:${it.list.size}")
                    binding.prf.finishRefresh()
                    binding.prf.finishLoadMore()
                    binding.prf.showContent()
                    if (it.list.size < 10) {
                        binding.prf.setNoMoreData(true)
                    }
                    if (viewModel.isRefresh) {
                        binding.rvList.models = it.list
                    } else {
                        binding.rvList.addModels(it.list)
                    }


                    val topList = (binding.rvList.models as List<ArticleModel>)
                        .filter { it.images.isNullOrEmpty() && it.videos == null }
                        .take(3)
                    binding.rvTop.models = topList
                }

                else -> {

                }
            }
        }
    }

    private fun setHomeFlagClick(){
        binding.ivHome.setOnClickListener {

            when(MmkvRepository.homeFlag){
                true -> {
                    MmkvRepository.homeFlag = false
//                    binding.ivHome.loadAny(R.drawable.home_dance)
                    binding.ivHome.setAnimation(com.xy.common.R.raw.dance)
                    binding.ivHome.playAnimation()
                    toast("心猿意马")
                }
                false -> {
                    MmkvRepository.homeFlag = true
//                    binding.ivHome.loadAny(R.drawable.home_am)
                    binding.ivHome.setAnimation(com.xy.common.R.raw.bookn)
                    binding.ivHome.playAnimation()
                    toast("心无旁贷")
                }
            }
//            startRefresh()
            binding.prf.refreshing()
        }
    }

    private fun startRefresh() {
        viewModel.page = 1
        getData {
            viewModel.refresh(Common.Default, it)
        }
    }

    override fun onListens() {
        binding.prf.run {
            onRefresh {
                startRefresh()
            }
            onLoadMore {
                viewModel.page++
                getData {
                    viewModel.loadMore(Common.Default, it)
                }

            }
        }
        binding.prf.refreshing()
    }


    private fun getData(onSuccess: (List<ArticleModel>) -> Unit = {}) {
        if(MmkvRepository.homeFlag){
            onSuccess.invoke(listOf())
            return
        }
        lifecycleScope.launch {
            try {
                val (videoRes, imageRes) = coroutineScope {
                    val videoDeferred = async { viewModel.wrapKyMediaRequest("video") }
                    val imageDeferred = async { viewModel.wrapKyMediaRequest("image") }
                    Pair(
                        videoDeferred.await(),
                        imageDeferred.await(),
                    )
                }

                withContext(Dispatchers.Main) {
                    val listAll = mutableListOf<ArticleModel>()
                    videoRes.forEach {
                        it as KyVideoModel
                        val model = ArticleModel()
                        model.videos = it
                        listAll.add(model)
                    }

                    val groupSize = (1..3).random()

                    imageRes.chunked(groupSize).forEach { group ->
                        group as List<BingImgModel>
                        val model = ArticleModel()
                        model.images = group
                        model.title = group[0].title
                        model.sysUser.avatar = group[0].imageUrl
                        model.sysUser.nickName = group[0].copyright
                        listAll.add(model)
                    }

                    onSuccess.invoke(listAll)

                }
            } catch (e: Exception) {
                // 统一错误处理
                Toast.makeText(context, "请求失败: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}




