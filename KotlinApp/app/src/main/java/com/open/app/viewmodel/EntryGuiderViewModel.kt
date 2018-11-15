package com.open.app.viewmodel

import android.databinding.BaseObservable
import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v4.view.ViewPager
import com.open.app.model.data.CommonPagerBean


class EntryGuiderViewModel: BaseObservable() {
    var items:ObservableList<CommonPagerBean> = ObservableArrayList<CommonPagerBean>()


    @BindingAdapter("items")
    public fun setItems(viewPager: ViewPager,items:List<CommonPagerBean>){

    }
}