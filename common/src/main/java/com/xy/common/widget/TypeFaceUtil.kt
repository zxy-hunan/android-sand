


import android.graphics.Typeface
import com.dylanc.longan.context
import com.dylanc.longan.topActivity


object TypeFaceUtil {

    const val FZLL_TYPEFACE = 1

    const val FZDB1_TYPEFACE = 2

    const val FUTURA_TYPEFACE = 3

    const val DIN_TYPEFACE = 4

    const val LOBSTER_TYPEFACE = 5

    val fzlLTypeface by lazy {
        Typeface.createFromAsset(topActivity.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    val fzdb1Typeface by lazy {
        Typeface.createFromAsset(topActivity.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    val futuraTypeface by lazy {
        Typeface.createFromAsset(topActivity.context.assets, "fonts/Futura-CondensedMedium.ttf")
    }

    val dinTypeface by lazy {
        Typeface.createFromAsset(topActivity.context.assets, "fonts/DIN-Condensed-Bold.ttf")
    }

    val lobsterTypeface by lazy {
        Typeface.createFromAsset(topActivity.context.assets, "fonts/Lobster-1.4.otf")
    }
}