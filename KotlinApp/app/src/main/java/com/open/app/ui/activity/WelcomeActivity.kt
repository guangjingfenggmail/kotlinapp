package com.open.app.ui.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.open.app.R
import com.open.app.model.data.VersionBean
import com.open.app.databinding.ActivityWelcomeBinding
import com.open.app.viewmodel.WelcomeViewModel
import com.open.app.widget.CountdownTextView
import kotlinx.android.synthetic.main.activity_welcome.*
import com.open.app.widget.CountdownTextView.CountdownCompleteCallback

class WelcomeActivity : AppCompatActivity(), CountdownCompleteCallback, EventPresenter {
    lateinit var mViewModel: WelcomeViewModel
    lateinit var mBinding: ActivityWelcomeBinding
    var countTimer: CountdownTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityWelcomeBinding>(this@WelcomeActivity, R.layout.activity_welcome)
        mViewModel = WelcomeViewModel()
        mViewModel.setVersionObObservable(VersionBean(1, "version " + "1.0.0"))

        countTimer = countTimerView
        countTimer?.init("%s", 5, this)
        countTimer?.start()
        mBinding.viewmodel = mViewModel
        mBinding.event = this
    }

    override fun onCompleteCallback() {
        var intent: Intent = Intent()
        intent.setClass(this, EntryGuiderActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onClick(view: View, viewModel: WelcomeViewModel) {
        Toast.makeText(this, "onClick000", Toast.LENGTH_LONG).show()
        countTimer?.stop()
        finish()
    }

    override fun onClick(view: View) {
        Toast.makeText(this, "onClick111", Toast.LENGTH_SHORT).show()
        countTimer?.stop()
        finish()
    }

}