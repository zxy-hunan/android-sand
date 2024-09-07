package com.xy.user.page.frg.viewhelper

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import com.xy.user.R
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.databinding.ItemSettingBinding
import com.xy.user.databinding.ItemUserTopBinding

fun RecyclerView.rvSetting(){
    this.grid(3).setup {
        addType<UserTagModel>(R.layout.item_setting)
        onBind {
            val binding =  getBinding<ItemSettingBinding>()
            val item = getModel<UserTagModel>()
            binding.tvName.text = item.tagName
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            when (item.tag) {
                UserTag.SETTING -> {
//                    gotoLogin()
                }

                else -> {}
            }
        }
    }
}



fun RecyclerView.rvTop(){
    this.grid(3).setup {
        addType<UserTagModel>(R.layout.item_user_top)
        onBind {
            val binding =  getBinding<ItemUserTopBinding>()
            val item = getModel<UserTagModel>()
            binding.tvName.text = item.tagName
            binding.tvNum.text = item.tagUrl
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            when (item.tag) {
                UserTag.SETTING -> {
//                    gotoLogin()
                }

                else -> {}
            }
        }
    }
}


fun RecyclerView.rvBase(){
    this.grid(4).setup {
        addType<UserTagModel>(R.layout.item_setting)
        onBind {
            val binding =  getBinding<ItemSettingBinding>()
            val item = getModel<UserTagModel>()
            binding.tvName.text = item.tagName
            if(item.res!=0){
                binding.ivHead.setImageResource(item.res)
            }
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            when (item.tag) {
                UserTag.SETTING -> {
//                    gotoLogin()
                }

                else -> {}
            }
        }
    }
}