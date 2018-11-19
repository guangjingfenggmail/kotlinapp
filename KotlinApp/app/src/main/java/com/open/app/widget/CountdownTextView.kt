package com.open.app.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import java.util.*
import kotlin.*


public class CountdownTextView : TextView {
    var mSeconds: Long = 0
    lateinit var mFormat: String
    lateinit var mTimer: Timer
    lateinit var mTimerTask: CountTimerTask
    val WHAT_COUNT_DOWN_TICK: Int = 1
    val TAG: String = "CountdownTextView"
    lateinit var mContext: Context
    lateinit var mCallback: CountdownCompleteCallback

    constructor(context: Context?) : super(context) {
        mContext = context!!
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context!!
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context!!
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        mContext = context!!
    }


    public fun init(format: String, seconds: Long, callback: CountdownCompleteCallback) {
        mTimer = Timer()
        mSeconds = seconds
        mCallback = callback
        if (format != null)
            mFormat = format
        mTimerTask = CountTimerTask()
    }


    inner class CountTimerTask() : TimerTask() {
        override fun run() {
            if (mSeconds > 0) {
                mSeconds--
                mHandler.sendEmptyMessage(WHAT_COUNT_DOWN_TICK)
            }
            Log.d("CountTimerTask", "mSeconds==" + mSeconds)
        }
    }

    fun start() {
        mTimer?.schedule(mTimerTask, 0, 1000)
    }


    fun stop(){
        if (mTimerTask != null)
            mTimerTask?.cancel()

        if (mTimer != null)
            mTimer.cancel()
    }

    private var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                WHAT_COUNT_DOWN_TICK -> {
                    if (mSeconds <= 0) {
                        setText(String.format(mFormat, "00"))
                        if (mTimerTask != null)
                            mTimerTask?.cancel()

                        if (mTimer != null)
                            mTimer.cancel()

                        if (mCallback != null)
                            mCallback.onCompleteCallback()

                        removeCallbacksAndMessages(-1)
                    } else
                        setText(String.format(mFormat, second2TimeSecond(mSeconds)))
                }
            }
        }
    }


    interface CountdownCompleteCallback {
        fun onCompleteCallback()
    }


    fun second2TimeSecond(second: Long): String {
        var seconds: Long = second % 60
        var secondsStr: String = ""

        if (seconds < 10) {
            secondsStr = "0"
            if (seconds == 0L)
                secondsStr += "0"
            else
                secondsStr += seconds.toString()
        } else
            secondsStr = seconds.toString()

        return secondsStr
    }
}