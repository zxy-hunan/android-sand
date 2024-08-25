package com.xy.home.page.frg

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.dylanc.longan.toast
import com.dylanc.viewbinding.getBinding
import com.xy.home.R
import com.xy.home.data.ArticleModel
import com.xy.home.databinding.FragmentHomeBinding
import com.xy.home.databinding.ItemArticleBinding
import com.xy.home.databinding.ItemTopArticleBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief homefragment
 */
class HomeFrg() : MviFragment<FragmentHomeBinding, MainVm, MainIntent>(MainVm::class.java) {
    override fun initView() {

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

                item.tvNum.shapeDrawableBuilder.solidColor= when(modelPosition){
                    0 -> Color.parseColor("#F75000")
                    1 -> Color.parseColor("#FFA042")
                    2 -> Color.parseColor("#FFE66F")
                    else -> Color.parseColor("#FF5722")
                }
                item.tvNum.shapeDrawableBuilder.intoBackground()

                item.tvConetent.text = data.title
            }
        }

        binding.rvList.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(5, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
            startVisible = true
        }
            .setup {
                addType<ArticleModel>(R.layout.item_article)
                onBind {
                    val data = getModel<ArticleModel>()
                    val item = getBinding<ItemArticleBinding>()

                    item.tvTitle.text = data.title
                    item.tvContent.text = data.content

                    item.tvName.text = data.sysUser.nickName

                    item.tvStar.text = data.comStar.toString()
                    item.tvCommon.text = data.commNum.toString()

                    item.ivHead.load(data.sysUser.avatar)

                    if (data.imageurl.isEmpty()) {
                        item.ivArticle.visibility = View.GONE
                    } else {
                        item.ivArticle.visibility = View.VISIBLE
                        item.ivArticle.load(data.imageurl)
                    }


                }

            }

    }

    override fun lazyLoad() {
        binding.prf.refresh()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is MainIntent.ArticleList -> {
                    Log.e("MainIntent", "ArticleList: ")
//                    toast("it.size:${it.list.size}")
                    binding.prf.finishRefresh()
                    binding.prf.finishLoadMore()
                    binding.rvList.models = it.list


                    var topList = (binding.rvList.models as List<ArticleModel>).take(3)
                    binding.rvTop.models = topList
                }
            }
        }
    }

    override fun onListens() {
        binding.prf.run {
            onRefresh {
                viewModel.page=1
                viewModel.articleList()
            }
            onLoadMore {
                viewModel.page++
                viewModel.articleList()
            }
        }
    }
}


fun ImageView.load(url: String) {
    var transurl = if (url.startsWith("http")) url else "http://gyuelife.online" + url
    Glide.with(this).load(transurl).placeholder(
        com.xy.common.R.drawable.icon_default
    ).into(this)
}