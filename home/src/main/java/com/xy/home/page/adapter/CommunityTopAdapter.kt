package com.xy.home.page.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xy.home.R
import com.xy.home.data.ArticleModel
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils


class CommunityTopAdapter constructor(datas: List<ArticleModel>) :

    BannerAdapter<ArticleModel, CommunityTopAdapter.DynamicBannerViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): DynamicBannerViewHolder {
//        var imageView=ImageView(parent?.context)
//        imageView.layoutParams=ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        imageView.scaleType=ImageView.ScaleType.CENTER_CROP
        parent?.let { return DynamicBannerViewHolder(BannerUtils.getView(parent, R.layout.item_community_top)) }
        return DynamicBannerViewHolder(View(parent?.context))
    }

    override fun onBindView(
        holder: DynamicBannerViewHolder,
        data: ArticleModel,
        position: Int,
        size: Int
    ) {

//        holder.binding.dynamicTopTipIv.loadAny(data.icon.ossPicPath2Url())
//        holder.binding.dynamicTopTipTitleTv.text = data.title
//        holder.binding.dynamicTopTipRemarksTv.text = data.desc




//        holder.imageView.setOnTouchListener(OnTouchListener { v, event ->
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            false
//        })
    }

//    class DynamicBannerViewHolder(val binding: ItemDynamicTopBinding) :
//        RecyclerView.ViewHolder(binding.root)


    class DynamicBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.siv_head)
        val title: TextView = view.findViewById(R.id.tv_name)
        val content: TextView = view.findViewById(R.id.tv_content)

    }

}