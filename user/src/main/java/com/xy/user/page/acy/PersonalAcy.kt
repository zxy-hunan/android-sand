package com.xy.user.page.acy

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.util.glide.loadAny
import com.xy.common.view.load
import com.xy.mviframework.base.ui.vb.MviAcy
import com.xy.user.databinding.PersonalDataActivityBinding
import com.xy.user.databinding.SettingActivityBinding
import com.xy.user.intent.UserIntent
import com.xy.user.vm.UserVm

/**
 * @file PersonalAcy
 * @author zxy
 * @date 2025/6/12 9:51
 * @brief 个人中心
 *
 * {"msg":"操作成功","code":200,"permissions":[],"roles":[],
 * "user":{"createBy":"","createTime":"2024-09-02 11:20:44",
 * "updateBy":null,"updateTime":null,"remark":null,
 * "params":{"@type":"java.util.HashMap"},
 * "userId":114,"deptId":null,"userName":"15711974107",
 * "nickName":"星河璀璨","email":"","phonenumber":"",
 * "sex":"0","avatar":"/profile/avatar/2024/09/02/icon_main_page_private_letter_20240902112244A003.png",
 * "password":"$2a$10$J68ztu/TQ69aBrGSIkDnLelcr1BJ8S.7zZIXpAp1hbkSLFwJKZJyq","status":"0","delFlag":"0",
 * "loginIp":"118.250.108.159","loginDate":"2025-04-26T16:16:13.000+08:00","dept":null,"roles":[],"roleIds":null
 * ,"postIds":null,"roleId":null,"admin":false}}
 *
 */
@Route(path = ARouterConfig.User.PersonalAcy.PATH)
class PersonalAcy : MviAcy<PersonalDataActivityBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        immersionBar {
            statusBarColor(com.xy.common.R.color.white)
            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }
        //nav
        binding.tbTopBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                super.onLeftClick(titleBar)
                finish()
            }
        })

        viewModel.getInfo()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is UserIntent.UserInfoSuccess -> {
                    binding.sbPersonDataId.setRightText(it.info.userId.toString())
                    binding.sbPersonDataName.setRightText(it.info.nickName)
                    binding.ivPersonDataAvatar.load(it.info.avatar ?: "")
                }

                else -> {}
            }
        }
    }

    override fun onListener() {
    }
}