package com.open.app.viewmodel

import android.content.Context
import android.databinding.*
import android.widget.ListView
import com.open.app.BR
import com.open.app.model.data.ContactBean
import com.open.app.model.data.LastestBean
import com.open.app.model.rx.ResponseCallback
import com.open.app.model.service.ContactService
import com.open.app.ui.adapter.ContactAdapter
import retrofit2.Call
import retrofit2.Response


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

//    fun loadData() {
//        var database: ContactDatabase = ContactDatabase()
//        val mobileList: List<ContactBean> =  database.getContactPhone(mContext)
//        items.clear()
//        items.addAll(mobileList)
//        notifyPropertyChanged(BR.empty)
//    }

    fun loadData() {
        ContactService.latest(ContactCallback())
    }


   inner class ContactCallback:ResponseCallback<LastestBean>{

        override fun onResponse(call: Call<LastestBean>, response: Response<LastestBean>) {
            var  bean: LastestBean? = response.body()
            items.clear()
            items.addAll(bean?.stories?:ArrayList<ContactBean>())
            notifyPropertyChanged(BR.empty)
        }

        override fun onFailure(call: Call<LastestBean>, t: Throwable) {

        }
    }


}