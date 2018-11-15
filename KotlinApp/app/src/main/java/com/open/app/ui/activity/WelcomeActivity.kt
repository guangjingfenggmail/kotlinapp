package com.open.app.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.open.app.R
import com.open.app.model.data.VersionBean
import com.open.app.databinding.ActivityWelcomeBinding
import com.open.app.viewmodel.WelcomeViewModel
import com.open.app.widget.CountdownTextView
import kotlinx.android.synthetic.main.activity_welcome.*
import com.open.app.widget.CountdownTextView.CountdownCompleteCallback

class WelcomeActivity : AppCompatActivity(),CountdownCompleteCallback {
    lateinit var mViewModel: WelcomeViewModel
    lateinit var mBinding:ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityWelcomeBinding>(this@WelcomeActivity, R.layout.activity_welcome)
        mViewModel = WelcomeViewModel()
        var bean: VersionBean = VersionBean(1, "version " + "1.0.0")
        mViewModel.setVersionObObservable(bean)

        var countTimer: CountdownTextView? = countTimer
        countTimer?.init("%s",5,this)
        countTimer?.start()
        mBinding.viewmodel = mViewModel
        mBinding.view = this
    }

    override fun onCompleteCallback() {
        var intent:Intent = Intent()
        intent.setClass(this,ContactActivity::class.java)
        startActivity(intent)
        finish()
    }

}