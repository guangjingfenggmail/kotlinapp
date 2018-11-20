package com.open.app.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.open.app.R
import com.open.app.databinding.ActivityNewsRecycleBinding
import com.open.app.model.data.ContactBean
import com.open.app.ui.adapter.NewsRecycleViewAdapter
import com.open.app.viewmodel.NewsRecycleViewModel
import kotlinx.android.synthetic.main.activity_news_recycle.*

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

class NewsRecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityNewsRecycleBinding = DataBindingUtil.setContentView(this@NewsRecycleActivity, R.layout.activity_news_recycle)

        var viewmodel: NewsRecycleViewModel = NewsRecycleViewModel()
        binding.view = this
        binding.viewmodel = viewmodel

        var mRecycleview: RecyclerView = recycleview
        var linearLayoutManager : LinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        mRecycleview.layoutManager = linearLayoutManager
        mRecycleview.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        mRecycleview.adapter = NewsRecycleViewAdapter(ArrayList<ContactBean>())

        viewmodel.loadData()
    }
}