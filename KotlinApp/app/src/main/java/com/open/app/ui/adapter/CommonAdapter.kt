package com.open.app.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.open.app.model.data.ContactBean


/**
 * ****************************************************************************************************************************************************************************
 *
 * @author : guangjing.feng
 * @createTime:  2018/11/15
 * @version: 1.0.0
 * @modifyTime: 2018/11/15
 * @modifyAuthor: guangjing.feng
 * @description:
 * ****************************************************************************************************************************************************************************
 */

open class CommonAdapter<T> : BaseAdapter {
    lateinit var list: List<T>
    lateinit var context: Context

    constructor(context: Context, list: List<T>) {
        setData(list)
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        return convertView
    }

    override fun getItem(position: Int): T {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setData(list: List<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }
}