package com.open.app.viewmodel

import android.databinding.*
import android.support.v4.view.ViewPager
import com.open.app.model.data.CommonPagerBean
import com.open.app.ui.adapter.EntryGuiderPagerAdapter


class EntryGuiderViewModel : BaseObservable() {
    var items: ObservableList<CommonPagerBean> = ObservableArrayList<CommonPagerBean>()
    var i: ObservableInt = ObservableInt();

    companion object {

        @JvmStatic
        @BindingAdapter("items")
        public fun setItems(viewPager: ViewPager, items: List<CommonPagerBean>) {
//            if(viewPager.adapter is EntryGuiderPagerAdapter){
//                var adapter :EntryGuiderPagerAdapter = viewPager.adapter as EntryGuiderPagerAdapter
//                if (adapter!=null)
//                    adapter.updateUI(items)
//            }
        }

//        @JvmStatic
//        @BindingAdapter("listener2")
//        public fun setListener2(viewPager: ViewPager, i: Int) {
//            viewPager.setOnPageChangeListener(ObservableOnPageChangeListener(viewPager))
//        }

    }


//    class ObservableOnPageChangeListener(var viewPager2: ViewPager) : ViewPager.OnPageChangeListener {
//        var misScrolled: Boolean = false
//        override fun onPageScrollStateChanged(p0: Int) {
//            Log.d("EntryGuiderViewModel", "onPageScrollStateChanged p0==" + p0)
//            when (p0) {
//            ViewPager.SCROLL_STATE_DRAGGING ->
//                misScrolled = false
//            ViewPager.SCROLL_STATE_SETTLING ->
//                misScrolled = true;
//            ViewPager.SCROLL_STATE_IDLE -> {
//                if (viewPager2.getCurrentItem() == viewPager2.adapter!!.count - 1 && !misScrolled) {
//                    var intent: Intent = Intent()
//                    intent.setClass(viewPager2.context, ContactActivity::class.java)
//                    viewPager2.context.startActivity(intent)
//                    Log.d("EntryGuiderActivity", "toContact")
//                }
//                misScrolled = true
//            }
//        }
//
//        }
//
//        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//            Log.d("EntryGuiderViewModel", "onPageScrolled p0==" + p0)
//        }
//
//        override fun onPageSelected(p0: Int) {
//            Log.d("EntryGuiderViewModel", "onPageSelected p0==" + p0)
//        }
//    }


}