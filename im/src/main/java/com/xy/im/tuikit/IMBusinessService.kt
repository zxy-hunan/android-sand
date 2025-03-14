package com.xy.im.tuikit

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.qcloud.tuicore.ServiceInitializer
import com.tencent.qcloud.tuicore.TUILogin
import com.tencent.qcloud.tuicore.interfaces.ITUINotification
import com.tencent.qcloud.tuicore.interfaces.TUICallback
import com.xy.common.util.MmkvRepository
import com.xy.mviframework.network.tool.LOG_TAG
import com.xy.mviframework.network.tool.logE

/**
 * @file IMBusinessService
 * @author zxy
 * @date 2025/1/23 16:31
 * @brief
 */
class IMBusinessService : ServiceInitializer(), ITUINotification {
    var userSig = "eJwtzE0LgkAUheH-MuuQ*fBqCK3CCpEyrIR2AzPK1UpzLhFE-z1Tl*c58H7YKc29l*1ZxKTH2WLcaOyDsMSRyTqa3ZlGdx0aFomAc*6DBH967LvD3g4OAHK4JiW8-y1UYbAMlJJzBashm7S53mfXLD5SJVse7*rmWYTJpUgFOHkmu96CFvVhc3Mr9v0B7pswxQ__"
    fun checkIMLogin(onLoginCallBack: () -> Unit = { }) {

        if (!TUILogin.isUserLogined() && V2TIMManager.getInstance().loginStatus != V2TIMManager.V2TIM_STATUS_LOGINING) {

            TUILogin.login(getAppContext(), MmkvRepository.imAppId, "test", userSig, object : TUICallback() {
                override fun onSuccess() {
                    logE(LOG_TAG, "im login onSuccess")
                }

                override fun onError(errorCode: Int, errorMessage: String?) {
                    logE(LOG_TAG, "im login onError,errorCode:$errorCode,errorMessage,$errorMessage")
                }

            })

        }
    }

    override fun onNotifyEvent(key: String?, subKey: String?, param: MutableMap<String, Any>?) {

    }

    override fun init(context: Context?) {
        super.init(context)
        initIMLogin()
    }

    private fun initIMLogin() {
        val fragmentLifecycleCallbacksImpl = FragmentLifecycleCallbacksImpl()
        (context as Application).registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                when (activity.javaClass.simpleName) {
                    "MainActivity" -> {
                        if (activity is FragmentActivity) {
                            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                                fragmentLifecycleCallbacksImpl, true
                            )
                        }
                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                when (activity.javaClass.simpleName) {
                    "MainActivity" -> {
                        checkIMLogin()
                    }
                }
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }

}

class FragmentLifecycleCallbacksImpl : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentAttached(
        fm: FragmentManager, f: Fragment, context: Context,
    ) {
    }

    override fun onFragmentCreated(
        fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?,
    ) {
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
    }
}
