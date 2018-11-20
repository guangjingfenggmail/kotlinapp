package com.open.app.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.open.app.model.data.CommonPagerBean

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

open class CommonPagerAdapter<T> : PagerAdapter {
    var context: Context? = null
    var list: List<T>? = null

    constructor(context: Context, list: List<T>) : super() {
        this.context = context
        this.list = list
    }

    fun getItem(position: Int): T? {
        return list?.get(position) ?: null
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
//        return super.instantiateItem(container, position)
        return View(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    fun updateUI(list: List<T>) {
        this.list = list
        notifyDataSetChanged()
    }
}