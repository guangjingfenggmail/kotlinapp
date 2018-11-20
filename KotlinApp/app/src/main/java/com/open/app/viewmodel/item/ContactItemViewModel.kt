package com.open.app.viewmodel.item

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.view.View
import android.widget.ImageView
import com.open.app.model.data.ContactBean
import com.open.app.model.data.NewsInfoBean
import com.open.app.model.rx.callback.ResponseCallback
import com.open.app.model.service.ContactService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response


/**
 * ****************************************************************************************************************************************************************************
 *
 * @author : guangjing.feng
 * @createTime:  2018/11/14
 * @version: 1.0.0
 * @modifyTime: 2018/11/14
 * @modifyAuthor: guangjing.feng
 * @description:
 * ****************************************************************************************************************************************************************************
 */

class ContactItemViewModel : BaseObservable {
    var contact: ObservableField<ContactBean> = ObservableField<ContactBean>()

    constructor()

    fun setContact(contactBean: ContactBean) {
        contact.set(contactBean)
    }

    @Bindable
    fun getUserName(): String {
        var bean: ContactBean? = contact.get()
        return bean?.title ?: ""
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
        var bean: ContactBean? = contact.get()
        return bean?.images?.get(0) ?: ""
    }

    @Bindable
    fun getMobile(): String {
        var bean: ContactBean? = contact.get()
        var builder: StringBuffer = StringBuffer()
        var list: List<String>? = bean?.phones ?: ArrayList<String>()
        if (list != null)
            for (s: String in list) {
                builder.append(s).append("\n")
            }
        if (builder.toString().length > 0)
            return builder.toString().substring(0, builder.toString().length - 1)
        return ""
    }

//    fun onItemClick(view: View) {
//        var bean: ContactBean? = contact.get()
//        var id: String = bean?.id ?: ""
////        ContactService.getNewsInfo(id, ContactItemCallback())
//
//        ContactService.getNewsInfo(id, object :ResponseCallback<NewsInfoBean>{
//            override fun onFailure(call: Call<NewsInfoBean>, t: Throwable) {
//                super.onFailure(call, t)
//            }
//
//            override fun onResponse(call: Call<NewsInfoBean>, response: Response<NewsInfoBean>) {
//                super.onResponse(call, response)
//            }
//        })
//    }


//    override fun onFailure(call: Call<NewsInfoBean>, t: Throwable) {
//        super.onFailure(call, t)
//    }
//
//    override fun onResponse(call: Call<NewsInfoBean>, response: Response<NewsInfoBean>) {
//        super.onResponse(call, response)
//    }

//    inner class ContactItemCallback : ResponseCallback<NewsInfoBean> {
//
//        override fun onResponse(call: Call<NewsInfoBean>, response: Response<NewsInfoBean>) {
//            super.onResponse(call, response)
//        }
//
//        override fun onFailure(call: Call<NewsInfoBean>, t: Throwable) {
//            super.onFailure(call, t)
//        }
//
//    }
}