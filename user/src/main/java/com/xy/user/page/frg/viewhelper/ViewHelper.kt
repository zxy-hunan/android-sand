package com.xy.user.page.frg.viewhelper

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.dialog.CultureDialog
import com.xy.common.util.setAliFonts
import com.xy.common.util.setSemiBoldFonts
import com.xy.user.R
import com.xy.user.data.ArticleTag
import com.xy.user.data.UserTag
import com.xy.user.data.UserTagModel
import com.xy.user.databinding.ItemSettingBinding
import com.xy.user.databinding.ItemSettingsBinding
import com.xy.user.databinding.ItemUserTopBinding
import com.xy.user.page.acy.SettingsAcy

fun RecyclerView.rvSetting(){
    this.linear()
        .divider {
            orientation=DividerOrientation.HORIZONTAL
            setDivider(10)
            setColor(Color.parseColor("#f5f5f5"))
        }
        .setup {
        addType<UserTagModel>(R.layout.item_settings)
        onBind {
            val binding =  getBinding<ItemSettingsBinding>()
            val item = getModel<UserTagModel>()
            binding.tvName.text = item.tagName
            binding.tvName.setAliFonts()
            binding.ivHead.setImageResource(item.res)
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            when (item.tag) {
                UserTag.SETTING.tag -> {
                    ARouterConfig.User.SettingsAct.push()
//                    ARouterConfig.User.LoginAct.push()
                }

                UserTag.PERSONAL.tag -> {
                    ARouterConfig.User.PersonalAcy.push()
                }

                UserTag.OTHER.tag -> {
                    ARouterConfig.User.AboutAcy.push("http://zhiniao.xyz")
                }

                UserTag.ADJUST.tag -> {
                    CultureDialog.dayCultureDialog()
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
            binding.tvName.setAliFonts()
            binding.tvNum.setSemiBoldFonts()
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            when (item.tag) {
                UserTag.SETTING.tag -> {
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
            binding.tvName.setAliFonts()
            if(item.res!=0){
                binding.ivHead.setImageResource(item.res)
            }
        }
        onClick(R.id.ll_item, R.id.ll_item) {
            val item = getModel<UserTagModel>()
            ARouterConfig.User.ArticleClassifyAct.push(item.tag.toInt())
        }
    }
}