package com.open.app.viewmodel.item

import android.app.Activity
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.open.app.model.data.CommonPagerBean
import com.squareup.picasso.Picasso

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
class EntrIytemPagerViewModel : BaseObservable {
    val pager: ObservableField<CommonPagerBean> = ObservableField<CommonPagerBean>()

    constructor()


    fun setPagerData(data: CommonPagerBean) {
        pager.set(data)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("src2")
        fun setImage(imageView: ImageView, imageUrl: String) {
            Picasso.with(imageView.getContext()).load(imageUrl).into(imageView)
        }
    }


    @Bindable
    fun getImageUrl(): String {
        var data: CommonPagerBean? = pager?.get()
        return data?.imageUrl ?: ""
    }

    @Bindable
    fun getImageName(): String {
        var data: CommonPagerBean? = pager?.get()
        return data?.imageName ?: ""
    }

    @Bindable
    fun isVisiable(): Boolean {
        var data: CommonPagerBean? = pager?.get()
        return data?.visiable ?: false
    }

}