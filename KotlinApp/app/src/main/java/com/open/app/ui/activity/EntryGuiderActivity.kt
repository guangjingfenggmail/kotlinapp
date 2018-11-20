package com.open.app.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.open.app.R
import com.open.app.databinding.ActivityEntryGuiderBinding
import com.open.app.generated.callback.OnClickListener
import com.open.app.model.data.CommonPagerBean
import com.open.app.ui.adapter.EntryGuiderPagerAdapter
import com.open.app.viewmodel.EntryGuiderViewModel
import kotlinx.android.synthetic.main.activity_entry_guider.*

/**
 *viewpager
 */
public class EntryGuiderActivity : AppCompatActivity(),ViewPager.OnPageChangeListener {
    lateinit var binding: ActivityEntryGuiderBinding
    lateinit var list: MutableList<CommonPagerBean>
    var misScrolled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@EntryGuiderActivity, R.layout.activity_entry_guider)
        var viewModel: EntryGuiderViewModel = EntryGuiderViewModel()
        binding.view = this
        binding.viewmodel = viewModel


        var bean = CommonPagerBean()
        bean.imageUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=151422267,791196511&fm=200&gp=0.jpg"

        list = ArrayList<CommonPagerBean>()
        list.add(bean)

        bean = CommonPagerBean()
        bean.imageUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4036412488,4092768788&fm=27&gp=0.jpg"
        list.add(bean)

        bean = CommonPagerBean()
        bean.imageUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2546717774,3910079056&fm=200&gp=0.jpg"
        list.add(bean)

        var adapter: EntryGuiderPagerAdapter = EntryGuiderPagerAdapter(this, list)
        var viewPager: ViewPager = viewPager
        viewPager.adapter = adapter
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(p0: Int) {
//
//            }
//
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//
//            }
//
//            override fun onPageSelected(p0: Int) {
//
//            }
//        })
        viewPager.addOnPageChangeListener(this)
    }


    fun toContact() {
        var intent: Intent = Intent()
        intent.setClass(this, ContactActivity::class.java)
        startActivity(intent)
        finish()
        Log.d("EntryGuiderActivity", "toContact")
    }


    override fun onPageScrollStateChanged(p0: Int) {
        when (p0) {
            ViewPager.SCROLL_STATE_DRAGGING ->
                misScrolled = false
            ViewPager.SCROLL_STATE_SETTLING ->
                misScrolled = true;
            ViewPager.SCROLL_STATE_IDLE -> {
                if (viewPager.getCurrentItem() == viewPager.adapter!!.count - 1 && !misScrolled) {
                    toContact()
                }
                misScrolled = true
            }
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {

    }

}