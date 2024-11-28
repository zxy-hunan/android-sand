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