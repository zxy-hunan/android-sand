package com.xy.common.arouter.user

import ARouterConfig.Home.H5Act.ARTICLE_URL
import com.alibaba.android.arouter.launcher.ARouter

class ARouterConfig {
    object User {
        private const val GROUP: String = "/user"

        object LoginAct {

            const val PATH = "$GROUP/page/acy/login"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }


        object SettingsAct {

            const val PATH = "$GROUP/page/acy/settings"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }

        object PersonalAcy {

            const val PATH = "$GROUP/page/acy/personalacy"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }

        object AboutAcy {

            const val PATH = "$GROUP/page/acy/aboutacy"
            const val URL = "URL"
            fun push(url: String = "") {
                ARouter.getInstance()
                    .build(PATH)
                    .withString(URL, url)
                    .navigation()
            }
        }


        object ArticleClassifyAct {

            const val PATH = "$GROUP/page/acy/classify"
            const val COM_ID = "com_id"
            fun push(comId: Int = 0) {//默认 后端
                ARouter.getInstance()
                    .build(PATH)
                    .withInt(COM_ID, comId)
                    .navigation()
            }
        }

    }
}