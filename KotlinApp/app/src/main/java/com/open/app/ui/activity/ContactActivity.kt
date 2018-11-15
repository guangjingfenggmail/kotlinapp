package com.open.app.ui.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.open.app.R
import com.open.app.model.data.ContactBean
import com.open.app.databinding.ActivityContactBinding
import com.open.app.permission.IPermissionResult
import com.open.app.permission.PermissionConsts
import com.open.app.permission.PermissionManager
import com.open.app.ui.adapter.ContactAdapter
import com.open.app.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_contact.*

public class ContactActivity:AppCompatActivity(){
    lateinit var binding:ActivityContactBinding
    lateinit var adapter: ContactAdapter
    var list:ArrayList<ContactBean> = ArrayList<ContactBean>()
    lateinit var mListview: ListView
    lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ContactActivity, R.layout.activity_contact)
        viewModel = ContactViewModel(this)

        binding.view = this
        binding.viewmodel = viewModel

        adapter = ContactAdapter(this,list)
        mListview = listview
        mListview.adapter = adapter

        PermissionManager.init().checkPermissions(this,  1000, object : IPermissionResult {
            override fun getPermissionFailed(activity: Activity, requestCode: Int,deniedPermissions: Array<out String>?) {
                //获取权限失败
            }

            override fun getPermissionSuccess(activity: Activity, requestCode: Int) {
                //获取权限成功
                viewModel.loadData()
            }
        },PermissionConsts.CONTACTS)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

}