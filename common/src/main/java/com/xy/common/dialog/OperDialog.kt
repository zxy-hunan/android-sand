package com.xy.common.dialog

import com.kongzue.dialogx.dialogs.MessageDialog

/**
 * @file OperDialog
 * @author zxy
 * @date 2025/6/24 14:27
 * @brief
 */
object OperDialog {
    fun showdevelopeingDialog(){
        MessageDialog.show(
            "功能开发中",
            "该功能正在开发中，敬请期待！","Confirm"
        )
    }
}