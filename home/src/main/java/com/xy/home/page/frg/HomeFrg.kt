package com.xy.home.page.frg

import android.util.Log
import com.dylanc.longan.toast
import com.xy.home.databinding.FragmentHomeBinding
import com.xy.home.intent.MainIntent
import com.xy.home.vm.MainVm
import com.xy.mviframework.base.ui.vb.frg.MviFragment

/**
 * @file HomeFrg
 * @author zxy
 * @date 2024/7/22 14:01
 * @brief homefragment
 */
class HomeFrg() :MviFragment<FragmentHomeBinding,MainVm,MainIntent>(MainVm::class.java) {
    override fun initView() {
    }

    override fun lazyLoad() {
        viewModel.articleList()
    }

    override fun observe() {
        intentCallBack {
            when(it){
                is MainIntent.ArticleList->{
                    Log.e("MainIntent", "ArticleList: ", )
                    toast("it.size:${it.list.size}")
                }
            }
        }
    }

    override fun onListens() {
    }
}