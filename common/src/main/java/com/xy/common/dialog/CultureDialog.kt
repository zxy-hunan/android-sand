package com.xy.common.dialog

import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.dylanc.longan.topActivity
import com.dylanc.viewbinding.getBinding
import com.xy.common.R
import com.xy.common.data.AppFontsType
import com.xy.common.data.model.Culture
import com.xy.common.databinding.DialogCultureBinding
import com.xy.common.util.AppFontsUtil
import com.xy.common.util.mContext
import com.xy.common.util.parseCultureJson
import com.xy.common.util.readJsonFile
import com.xy.common.widget.htextview.scale.ScaleTextView
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.logE
import per.goweii.layer.core.ktx.onPreDismiss
import per.goweii.layer.core.ktx.onPreShow
import per.goweii.layer.dialog.DialogLayer
import per.goweii.layer.dialog.ktx.backgroundDimDefault
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
        val cultureList = readJsonFile("caocao.json", topActivity).parseCultureJson<Culture>()
        logE(LOG_TAG, "cultureStr", cultureList)
        val culture = cultureList.random()
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
                if (culture != null) {
                    val tvTitle = TextView(mContext)
                    tvTitle.setTextColor(Color.BLACK)
                    tvTitle.text = culture.title
                    tvTitle.textSize = 18f
                    tvTitle.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    AppFontsUtil.setControlFonts(mContext, tvTitle, AppFontsType.BARLOW_SEMI_BOLD)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // 宽度
                        LinearLayout.LayoutParams.WRAP_CONTENT // 高度
                    )
                    layoutParams.bottomMargin = 20
                    tvTitle.layoutParams = layoutParams

                    binding.llRoot.addView(tvTitle)

                    culture.paragraphs?.let {
                        it.forEach {
                            val tvContent = TextView(mContext)
                            tvContent.text = it
                            tvContent.setTextColor(Color.BLACK)


                            tvContent.textSize = 16f
                            tvContent.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            AppFontsUtil.setControlFonts(mContext, tvContent, AppFontsType.ALI_MA_MA)
                            val layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, // 宽度
                                LinearLayout.LayoutParams.WRAP_CONTENT // 高度
                            )
                            layoutParams.bottomMargin = 20
                            tvContent.layoutParams = layoutParams



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