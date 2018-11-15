package com.open.app.viewmodel.item

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import com.open.app.model.data.ContactBean


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

    fun setContact(contactBean: ContactBean){
        contact.set(contactBean)
    }

    @Bindable
    fun getUserName(): String {
        var bean: ContactBean? = contact.get()
        return bean?.name?:""
    }

    @Bindable
    fun getMobile(): String {
        var bean: ContactBean? = contact.get()
        var builder:StringBuffer = StringBuffer()
        var list:List<String>? = bean?.phones?:ArrayList<String>()
        if (list!=null)
            for (s:String in list){
                builder.append(s).append("\n")
            }
        if (builder.toString().length>0)
            return builder.toString().substring(0,builder.toString().length-1)
        return ""
    }

}