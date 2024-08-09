package com.xy.common.arouter.user
import com.alibaba.android.arouter.launcher.ARouter

class ARouterConfig {
    object User{
        private const val GROUP: String = "/user"

        object LoginAct {

            const val PATH = "$GROUP/page/acy/login"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }

    }
}