package com.open.app.viewmodel

import android.content.Context
import android.databinding.*
import android.widget.ListView
import com.open.app.BR
import com.open.app.model.data.ContactBean
import com.open.app.model.local.ContactDatabase
import com.open.app.permission.PermissionManager
import com.open.app.ui.adapter.ContactAdapter


class ContactViewModel(var mContext: Context) : BaseObservable() {
    var items: ObservableList<ContactBean> = ObservableArrayList<ContactBean>()


    companion object {
        @BindingAdapter("items2")
        @JvmStatic
        fun setItems(listView: ListView, items: List<ContactBean>) {
            if (listView.adapter is ContactAdapter) {
                val adapter: ContactAdapter = listView.adapter as ContactAdapter
                if (adapter != null)
                    adapter.setData(items)
            }
        }

    }

    @Bindable
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun loadData() {
        var database: ContactDatabase = ContactDatabase()
        val mobileList: List<ContactBean> =  database.getContactPhone(mContext)
        items.clear()
        items.addAll(mobileList)
        notifyPropertyChanged(BR.empty)
    }




}