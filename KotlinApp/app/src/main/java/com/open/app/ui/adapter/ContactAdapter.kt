package com.open.app.ui.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.open.app.R
import com.open.app.model.data.ContactBean
import com.open.app.databinding.AdapterContactItemBinding
import com.open.app.viewmodel.item.ContactItemViewModel


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
class ContactAdapter:CommonAdapter<ContactBean>{
    constructor(context: Context, list: List<ContactBean>) : super(context, list)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var bean: ContactBean = list.get(position)
        lateinit var binding: AdapterContactItemBinding

        if (convertView==null) {
            var inflater:LayoutInflater = LayoutInflater.from(context)
            binding = DataBindingUtil.inflate(inflater, R.layout.adapter_contact_item,parent,false)
        }else
            binding = DataBindingUtil.getBinding<AdapterContactItemBinding>(convertView)!!

        var viewmodel:ContactItemViewModel = ContactItemViewModel()
        viewmodel.setContact(bean)
        binding.viewmodel = viewmodel
        return binding.root
    }


}