package com.xy.common.dialog

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.SnackbarUtils.dismiss
import com.dylanc.longan.topActivity
import com.dylanc.viewbinding.getBinding
import com.xy.common.R
import com.xy.common.data.model.Culture
import com.xy.common.databinding.DialogCultureBinding
import com.xy.common.util.mContext
import com.xy.common.util.parseCultureJson
import com.xy.common.util.readJsonFile
import com.xy.common.widget.htextview.HTextView
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.gson
import com.xy.mviframework.network.tool.logE
import per.goweii.layer.core.ktx.onClick
import per.goweii.layer.core.ktx.onClickToDismiss
import per.goweii.layer.core.ktx.onPreDismiss
import per.goweii.layer.core.ktx.onPreShow
import per.goweii.layer.dialog.DialogLayer
import per.goweii.layer.dialog.ktx.backgroundDimDefault
import per.goweii.layer.dialog.ktx.contentAnimator
import per.goweii.layer.dialog.ktx.contentView
import per.goweii.layer.dialog.ktx.gravity

/**
 * @file CultureDialog
 * @author zxy
 * @date 2025/3/21 17:04
 * @brief
 */
object CultureDialog {
    fun dayCultureDialog() {
//        var cultureStr=readJsonFile("caocao.json",topActivity)
//        logE(LOG_TAG,"cultureStr",cultureStr)
        val cultureList= readJsonFile("caocao.json",topActivity).parseCultureJson<Culture>()
        logE(LOG_TAG,"cultureStr",cultureList)
        val culture=cultureList.random()
        val mDialogLayer = DialogLayer(topActivity)
        mDialogLayer.contentView(R.layout.dialog_culture)
            .gravity(Gravity.CENTER)
            .backgroundDimDefault()
            .onPreDismiss {

            }
            .gravity(Gravity.CENTER)
            .backgroundDimDefault()
            .onPreShow {
                var binding = viewHolder.content.getBinding<DialogCultureBinding>()
                if(culture!=null){
                    val tvTitle= TextView(mContext)
                    tvTitle.text=culture.title
                    binding.llRoot.addView(tvTitle)

                    culture.paragraphs?.let {
                        it.forEach {
                            val tvContent= TextView(mContext)
                            tvContent.text=it
                            tvContent.setTextColor(Color.BLACK)
                            binding.llRoot.addView(tvContent)
                        }
                    }
                }

            }
            .onPreDismiss {
            }
            .show()
    }

}