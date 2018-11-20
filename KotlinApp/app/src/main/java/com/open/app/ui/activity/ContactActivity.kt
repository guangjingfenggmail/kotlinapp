package com.open.app.ui.activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.open.app.R
import com.open.app.model.data.ContactBean
import com.open.app.databinding.ActivityContactBinding
import com.open.app.model.data.NewsInfoBean
import com.open.app.model.rx.callback.ResponseCallback
import com.open.app.model.service.ContactService
import com.open.app.permission.IPermissionResult
import com.open.app.permission.PermissionConsts
import com.open.app.permission.PermissionManager
import com.open.app.ui.adapter.ContactAdapter
import com.open.app.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_contact.*
import retrofit2.Call
import retrofit2.Response

public class ContactActivity : AppCompatActivity(){
    lateinit var binding: ActivityContactBinding
    lateinit var adapter: ContactAdapter
    var list: ArrayList<ContactBean> = ArrayList<ContactBean>()
    lateinit var mListview: ListView
    lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ContactActivity, R.layout.activity_contact)
        viewModel = ContactViewModel(this)

        binding.view = this
        binding.viewmodel = viewModel

        adapter = ContactAdapter(this, list)
        mListview = listview
        mListview.adapter = adapter
        mListview.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            toDetail(id.toInt())
        })

        PermissionManager.init().checkPermissions(this, 1000, object : IPermissionResult {
            override fun getPermissionFailed(activity: Activity, requestCode: Int, deniedPermissions: Array<out String>?) {
                //获取权限失败
            }

            override fun getPermissionSuccess(activity: Activity, requestCode: Int) {
                //获取权限成功
                viewModel.loadData()
            }
        }, PermissionConsts.CONTACTS)
    }

    fun toDetail(id: Int){
        var bean: ContactBean? = viewModel.items.get(id)
        var id: String = bean?.id ?: ""
//        ContactService.getNewsInfo(id, ContactItemCallback())

        ContactService.getNewsInfo(id, object : ResponseCallback<NewsInfoBean> {
            override fun onFailure(call: Call<NewsInfoBean>, t: Throwable) {
                super.onFailure(call, t)
            }

            override fun onResponse(call: Call<NewsInfoBean>, response: Response<NewsInfoBean>) {
                super.onResponse(call, response)
            }
        })
    }

//    inner class OnItemClickListener1 : AdapterView.OnItemClickListener {
//        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//            toDetail(id.toInt())
//        }
//    }

//    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        toDetail(id.toInt())
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

}