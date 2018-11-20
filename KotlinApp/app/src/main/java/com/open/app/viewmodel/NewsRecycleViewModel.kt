package com.open.app.viewmodel

import android.databinding.*
import android.support.v7.widget.RecyclerView
import com.open.app.BR
import com.open.app.model.data.ContactBean
import com.open.app.model.data.LastestBean
import com.open.app.model.rx.callback.ResponseCallback
import com.open.app.model.service.ContactService
import com.open.app.ui.adapter.NewsRecycleViewAdapter
import retrofit2.Call
import retrofit2.Response

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/20.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/

class NewsRecycleViewModel : BaseObservable() {
    var items: ObservableList<ContactBean> = ObservableArrayList<ContactBean>()

    companion object {
        @BindingAdapter("items2")
        @JvmStatic
        fun setItems(listView: RecyclerView, items: List<ContactBean>) {
            if (listView.adapter is NewsRecycleViewAdapter) {
                val adapter: NewsRecycleViewAdapter = listView.adapter as NewsRecycleViewAdapter
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
        ContactService.latest(ContactCallback())
    }


    inner class ContactCallback : ResponseCallback<LastestBean> {

        override fun onResponse(call: Call<LastestBean>, response: Response<LastestBean>) {
            var bean: LastestBean? = response.body()
            items.clear()
            items.addAll(bean?.stories ?: ArrayList<ContactBean>())
            notifyPropertyChanged(BR.empty)
        }

        override fun onFailure(call: Call<LastestBean>, t: Throwable) {

        }
    }
}