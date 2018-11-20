package com.open.app.ui.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.open.app.R
import com.open.app.databinding.AdapterContactItemBinding
import com.open.app.model.data.ContactBean
import com.open.app.viewmodel.item.ContactItemViewModel

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

class NewsRecycleViewAdapter : RecyclerView.Adapter<NewsRecycleViewAdapter.ViewHolder> {
    lateinit var list: List<ContactBean>

    constructor(list: List<ContactBean>):super(){
        setData(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var viewmodel: ContactItemViewModel = ContactItemViewModel()
        viewmodel.setContact(list.get(position))
        holder.binding.viewmodel = viewmodel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         var binding:AdapterContactItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                 R.layout.adapter_contact_item,parent,false)
        binding.viewmodel = ContactItemViewModel()
        var holder: ViewHolder = ViewHolder(binding.root,binding)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<ContactBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var binding: AdapterContactItemBinding
        constructor(itemView: View,binding: AdapterContactItemBinding) : super(itemView) {
            this.binding = binding
        }
    }
}