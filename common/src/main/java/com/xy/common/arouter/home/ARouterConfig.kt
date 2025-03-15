import com.alibaba.android.arouter.launcher.ARouter
import com.xy.common.data.model.ArticleModel

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
            const val ARTICLE_MODE = "ARTICLE_MODE"

            fun push(url:String="",model: ArticleModel) {
                ARouter.getInstance()
                    .build(PATH)
                    .withSerializable(ARTICLE_MODE,model)
                    .withString(ARTICLE_URL,url)
                    .navigation()
            }
        }

        object MarkdownAct {

            const val PATH = "$GROUP/page/act/MarkdownAcy"
            const val ARTICLE_MODE = "ARTICLE_MODE"

            fun push(model: ArticleModel) {
                ARouter.getInstance()
                    .build(PATH)
                    .withSerializable(ARTICLE_MODE,model)
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