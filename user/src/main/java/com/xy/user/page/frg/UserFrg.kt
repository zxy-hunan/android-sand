package com.xy.user.page.frg

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.models
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.arouter.user.ARouterConfig
import com.xy.common.data.ArticleTotalNum
import com.xy.common.dialog.OperDialog
import com.xy.common.util.MmkvRepository
import com.xy.common.util.clickDebounce
import com.xy.common.util.setAliFonts
import com.xy.common.util.setSemiBoldFonts
import com.xy.common.view.load
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.user.data.UserTagModel
import com.xy.user.databinding.FragmentUserBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.frg.viewhelper.rvBase
import com.xy.user.page.frg.viewhelper.rvSetting
import com.xy.user.page.frg.viewhelper.rvTop
import com.xy.user.vm.UserVm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @file UserFrg
 * @author zxy
 * @date 2024/7/22 14:29
 * @brief user
 */
class UserFrg : MviFragment<FragmentUserBinding, UserVm, UserIntent>(UserVm::class.java) {
    override fun initView() {

        immersionBar {

            statusBarDarkFont(true)

            titleBarMarginTop(binding.viewLine)
        }

        binding.rvSetting.rvSetting()
        binding.rvMiddle.rvBase()
        binding.rvHigh.rvBase()
        binding.rvTop.rvTop()

        binding.rvSetting.models = viewModel.getSettingTagList()
        binding.rvMiddle.models = viewModel.getMiddleTagList()
        binding.rvHigh.models = viewModel.getHighTagList()
        topTagList = viewModel.getTopTagList()
        binding.rvTop.models = topTagList

        setFonts()
        getUserInfo()

        binding.clTop.clickDebounce {
            if (MmkvRepository.loginToken.isEmpty()) {
                ARouterConfig.User.LoginAct.push()
            } else {
                ARouterConfig.User.PersonalAcy.push()
            }
        }

        binding.ivSetting.clickDebounce {
            ARouterConfig.User.SettingsAct.push()
        }

        binding.ivScan.clickDebounce {
            OperDialog.showdevelopeingDialog()
        }
    }

    private fun setFonts() {
        binding.tvName.setSemiBoldFonts()
        binding.tvSlogen.setAliFonts()
        binding.tvCenter.setSemiBoldFonts()
        binding.tvMore.setSemiBoldFonts()
    }

    var topTagList = mutableListOf<UserTagModel>()

    override fun lazyLoad() {

    }

    override fun observe() {

        intentCallBack {
            when (it) {
                is UserIntent.UserInfoSuccess -> {
                    binding.tvName.text = it.info.nickName
                    binding.ivHead.load(it.info.avatar ?: "")
                }

                else -> {}
            }
        }

        SharedFlowBus.observer<Int>(key = FlowBusTag.LoginSuccess(), lifecycle = lifecycle) {
            getUserInfo()
        }
    }

    override fun onListens() {
    }


    private fun getUserInfo() {
        viewModel.getInfo {
            lifecycleScope.launch {
                try {
                    val userId = MmkvRepository.userModel.userId.toString()
                    val (articleResult, commonResult, starResult) = coroutineScope {
                        val articleDeferred = async { viewModel.wrapArticleNumRequest(userId, ArticleTotalNum.Article) }
                        val commonDeferred = async { viewModel.wrapArticleNumRequest(userId, ArticleTotalNum.Common) }
                        val starDeferred = async { viewModel.wrapArticleNumRequest(userId, ArticleTotalNum.Star) }
                        Triple(
                            articleDeferred.await(),
                            commonDeferred.await(),
                            starDeferred.await()
                        )
                    }

                    withContext(Dispatchers.Main) {
                        topTagList[0].tagUrl = articleResult
                        topTagList[1].tagUrl = commonResult
                        topTagList[2].tagUrl = starResult
                        binding.rvTop.models = topTagList
                    }
                } catch (e: Exception) {
                    // 统一错误处理
                    Toast.makeText(context, "请求失败: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}