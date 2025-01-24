import com.alibaba.android.arouter.launcher.ARouter

class ARouterConfig {
    object Home{
        private const val GROUP: String = "/home"

        object HomeDetailAcy {

            const val PATH = "$GROUP/page/act/homeDetail"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }


        object H5Act {

            const val PATH = "$GROUP/page/act/h5Acy"
            const val ARTICLE_URL = "ARTICLE_URL"

            fun push(url:String="") {
                ARouter.getInstance()
                    .build(PATH)
                    .withString(ARTICLE_URL,url)
                    .navigation()
            }
        }

    }

    object Post{
        private const val GROUP: String = "/post"

        object PostAcy {

            const val PATH = "$GROUP/page/act/postCommunityAct"

            fun push() {
                ARouter.getInstance()
                    .build(PATH)
                    .navigation()
            }
        }

    }
}