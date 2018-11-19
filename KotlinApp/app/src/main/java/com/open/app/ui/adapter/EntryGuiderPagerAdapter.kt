package com.open.app.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.open.app.R
import com.open.app.databinding.AdapterImageViewpagerBinding
import com.open.app.model.data.CommonPagerBean
import com.open.app.viewmodel.item.EntrIytemPagerViewModel

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/16.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/

class EntryGuiderPagerAdapter : CommonPagerAdapter<CommonPagerBean> {

    constructor(context: Context, list: List<CommonPagerBean>) : super(context, list) {

    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
//        return super.instantiateItem(container, position)
        var binding:AdapterImageViewpagerBinding

//        binding= AdapterImageViewpagerBinding.inflate(LayoutInflater.from(context), container, false)
        binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_image_viewpager,container, false)

        var viewModel: EntrIytemPagerViewModel = EntrIytemPagerViewModel()
        binding.viewmodel = viewModel
        viewModel.setPagerData(getItem(position)?: CommonPagerBean())
        container.addView(binding.root)
        return binding.root
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }


    override fun startUpdate(container: ViewGroup) {
        super.startUpdate(container)
    }


    fun updateUI(list: List<CommonPagerBean>) {
        this.list = list
        notifyDataSetChanged()
    }

}