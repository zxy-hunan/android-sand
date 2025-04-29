package com.xy.common.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.xy.common.R
import com.xy.common.data.AppFontsType
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.KyImageModel
import com.xy.common.databinding.ItemArticleBinding
import com.xy.common.databinding.ItemChildImageBinding
import com.xy.common.databinding.ItemImageBinding
import com.xy.common.databinding.ItemVideoBinding
import com.xy.common.util.AppFontsUtil
import com.xy.common.util.dp2px
import com.xy.common.util.glide.loadAny
import com.xy.common.util.mContext
import com.xy.mviframework.network.tool.logI

/**
 * @file BizViewHelper
 * @author zxy
 * @date 2025/3/12 14:54
 * @brief
 */

fun RecyclerView.bindArticleList() {
    this.setup {
        addType<ArticleModel> {
            when (this.videos) {
                null -> {
                    if (this.images.isNullOrEmpty()) {
                        R.layout.item_article
                    } else {
                        R.layout.item_image
                    }
                }

                else -> R.layout.item_video
            }
        }

        onCreate {
            when (itemViewType) {
                R.layout.item_image -> {
                    val item = getBinding<ItemImageBinding>()
                    initOnCreateRvMedia(item.rvImage)
                }
            }
        }

        onBind {
            when (itemViewType) {
                R.layout.item_article -> {

                    val data = getModel<ArticleModel>()
                    val item = getBinding<ItemArticleBinding>()

                    item.tvTitle.text = data.title
                    item.tvContent.text = data.content
                    AppFontsUtil.setControlFonts(mContext, item.tvTitle, AppFontsType.BARLOW_SEMI_BOLD)
                    AppFontsUtil.setControlFonts(mContext, item.tvContent, AppFontsType.ALI_MA_MA)

                    item.tvName.text = data.sysUser.nickName
                    AppFontsUtil.setControlFonts(mContext, item.tvName, AppFontsType.BARLOW_SEMI_BOLD)
                    if (data.starNum != null) {
                        item.tvStar.text = "${data.starNum}"
                    } else {
                        item.tvStar.text = "0"
                    }
                    if (data.commNum != null) {
                        item.tvCommon.text = "${data.commNum}"
                    } else {
                        item.tvCommon.text = "0"
                    }

                    item.ivHead.load(data.sysUser.avatar)

                    if (data.imageurl.isEmpty()) {
                        item.ivArticle.visibility = View.GONE
                    } else {
                        item.ivArticle.visibility = View.VISIBLE
                        item.ivArticle.load(data.imageurl)
                    }

                }

                R.layout.item_video -> {

                    val data = getModel<ArticleModel>()
                    val item = getBinding<ItemVideoBinding>()

                    item.ivVideo.loadAny(data.videos?.coverUrl ?: "")

                    item.ivHead.loadAny(data.videos?.userPic ?: "")
                    item.tvName.text = data.videos?.userName ?: ""
                    item.tvContent.text = data.videos?.title ?: ""
                    item.tvDuration.text = data.videos?.duration ?: ""

                }

                R.layout.item_image -> {

                    val data = getModel<ArticleModel>()
                    val item = getBinding<ItemImageBinding>()

                    data.images?.let {
                        var itemSpanCountNum = itemSpanCount(it)

                        if (itemSpanCountNum > 0) {
                            var bindingAdapter = item.rvImage.adapter as BindingAdapter
                            var layoutManager: GridLayoutManager = item.rvImage.layoutManager as GridLayoutManager

                            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                                override fun getSpanSize(position: Int): Int {
                                    return itemSpanCountNum
                                }
                            }

                            bindingAdapter.onBind {
                                val data = getModel<KyImageModel>()
                                val item = getBinding<ItemChildImageBinding>()
                                item.ivImage.loadAny(data.url)
                            }

                            bindingAdapter.models = it

                        }
                    }

                }

                else -> {

                }
            }

        }

        R.id.cl_root.onClick {
            val data = getModelOrNull<ArticleModel>() ?: return@onClick
            ARouterConfig.Home.H5Act.push(data.arpath, data)
        }

    }
}

fun initOnCreateRvMedia(rv: RecyclerView) {
    rv.grid(spanCount = 6, orientation = RecyclerView.VERTICAL, scrollEnabled = false)
        .divider {
            if (rv.adapter == null) {
                orientation = DividerOrientation.GRID
                setDivider(10.dp2px().toInt())
                includeVisible = false
            }
        }.setup {
            addType<KyImageModel>(R.layout.item_child_image)
        }
}


fun itemSpanCount(datas: List<KyImageModel>): Int {
    val imgList = datas.filterIndexed { index, s ->
        index < 9
    }
    var spanCount = when (imgList.size) {
        1 -> 6
        2 -> 3
        else -> 2
    }
    return spanCount
}


fun ImageView.load(url: String) {
    logI("load url:$url")
    var transurl = if (url.startsWith("http") || url.startsWith("https")) url else "http://gyuelife.online" + url
    Glide.with(this).load(transurl).placeholder(
        com.xy.common.R.drawable.icon_default
    ).into(this)
}


fun View.ShowOrRemove(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}