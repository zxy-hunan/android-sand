package com.xy.user.page.frg

import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.os.bundleOf
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.utils.addModels
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.gyf.immersionbar.ktx.immersionBar
import com.xy.common.data.model.ArticleModel
import com.xy.common.view.bindArticleList
import com.xy.mviframework.base.ui.vb.frg.MviFragment
import com.xy.user.databinding.FragmentArticleListBinding
import com.xy.user.intent.UserIntent
import com.xy.user.page.acy.LoginAcy
import com.xy.user.vm.UserVm

/**
 * @file UserFrg
 * @author zxy
 * @date 2024/7/22 14:29
 * @brief user
 */
class ArticleListFrg : MviFragment<FragmentArticleListBinding, UserVm, UserIntent>(UserVm::class.java) {
    private var comId = 0
    companion object {
        fun newInstance(position: Int) = ArticleListFrg().apply {
            arguments = bundleOf("position" to position)
        }
    }

    override fun initView() {
        arguments?.getInt("position", 0)?.let {
            comId = it
        }



        binding.rvList.linear().divider {
            orientation = DividerOrientation.HORIZONTAL
            setDivider(10, dp = true)
            setColor(Color.parseColor("#f5f5f5"))
            startVisible = true
        }.bindArticleList()

    }



    override fun lazyLoad() {
        binding.prf.refresh()
    }

    override fun observe() {
        intentCallBack {
            when (it) {
                is UserIntent.ArticleList -> {
                    binding.prf.finishRefresh()
                    binding.prf.finishLoadMore()
                    if (it.list.size < 10) {
                        binding.prf.setNoMoreData(true)
                    }
                    if(viewModel.isRefresh){
                        binding.rvList.models = it.list
                    }else{
                        binding.rvList.addModels(it.list)
                    }
                }
                else ->{

                }
            }
        }
    }

    override fun onListens() {
        binding.prf.run {
            onRefresh {
                viewModel.refresh("$comId")
            }
            onLoadMore {
                viewModel.loadMore("$comId")
            }
        }
    }


    private fun gotoLogin() {
        startActivity(Intent(mActivity, LoginAcy::class.java))
//        ARouterConfig.User.LoginAct.push()
    }
}