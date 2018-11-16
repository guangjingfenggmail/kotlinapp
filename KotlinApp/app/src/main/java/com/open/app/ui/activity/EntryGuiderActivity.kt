package com.open.app.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.open.app.R
import com.open.app.databinding.ActivityEntryGuiderBinding
import com.open.app.model.data.CommonPagerBean
import com.open.app.ui.adapter.EntryGuiderPagerAdapter
import com.open.app.viewmodel.EntryGuiderViewModel
import kotlinx.android.synthetic.main.activity_entry_guider.*

/**
 *
 */
public class EntryGuiderActivity : AppCompatActivity() {
    lateinit var binding: ActivityEntryGuiderBinding
    lateinit var list: MutableList<CommonPagerBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@EntryGuiderActivity, R.layout.activity_entry_guider)

        var viewModel: EntryGuiderViewModel = EntryGuiderViewModel()
        binding.view = this
        binding.viewmodel = viewModel


        list = ArrayList<CommonPagerBean>()
        list.add(CommonPagerBean())
        list.add(CommonPagerBean())
        list.add(CommonPagerBean())

        var adapter: EntryGuiderPagerAdapter = EntryGuiderPagerAdapter(this, list)
        var viewPager: ViewPager = viewPager
        viewPager.adapter = adapter
    }

}