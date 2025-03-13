package com.xy.common.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drake.brv.utils.setup
import com.xy.common.R
import com.xy.common.data.model.ArticleModel
import com.xy.common.databinding.ItemArticleBinding

/**
 * @file BizViewHelper
 * @author zxy
 * @date 2025/3/12 14:54
 * @brief
 */

fun RecyclerView.bindArticleList() {
    this.setup {
        addType<ArticleModel>(com.xy.common.R.layout.item_article)
        onBind {
            val data = getModel<ArticleModel>()
            val item = getBinding<ItemArticleBinding>()

            item.tvTitle.text = data.title
            item.tvContent.text = data.content

            item.tvName.text = data.sysUser.nickName
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

        R.id.cl_root.onClick {
            val data = getModelOrNull<ArticleModel>() ?: return@onClick
            ARouterConfig.Home.H5Act.push(data.arpath)
        }

    }
}


fun ImageView.load(url: String) {
    var transurl = if (url.startsWith("http")) url else "http://gyuelife.online" + url
    Glide.with(this).load(transurl).placeholder(
        com.xy.common.R.drawable.icon_default
    ).into(this)
}

fun View.ShowOrRemove(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}