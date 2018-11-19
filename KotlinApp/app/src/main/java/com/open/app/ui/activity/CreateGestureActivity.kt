package com.open.app.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.open.app.R
import com.open.app.utils.ACache
import com.open.app.widget.lock.LockPatternIndicator
import com.open.app.widget.lock.LockPatternUtil
import com.open.app.widget.lock.LockPatternView
import java.util.ArrayList

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/19.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/

class CreateGestureActivity : Activity() {
    lateinit var lockPatternIndicator: LockPatternIndicator
    lateinit var lockPatternView: LockPatternView
    lateinit var resetBtn: Button
    lateinit var messageTv: TextView

    private var mChosenPattern: List<LockPatternView.Cell>? = null
    private var aCache: ACache? = null

    /**
     * 手势监听
     */
    private var patternListener = object : LockPatternView.OnPatternListener {
        override fun onPatternStart() {
            lockPatternView.removePostClearPatternRunnable()
            //updateStatus(Status.DEFAULT, null);
            lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
        }

        override fun onPatternComplete(pattern: List<LockPatternView.Cell>) {
            //Log.e(TAG, "--onPatternDetected--");
            if (mChosenPattern == null && pattern.size >= 4) {
                mChosenPattern = ArrayList(pattern)
                updateStatus(Status.CORRECT, pattern)
            } else if (mChosenPattern == null && pattern.size < 4) {
                updateStatus(Status.LESSERROR, pattern)
            } else if (mChosenPattern != null) {
                if (mChosenPattern == pattern) {
                    updateStatus(Status.CONFIRMCORRECT, pattern)
                } else {
                    updateStatus(Status.CONFIRMERROR, pattern)
                }
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gesture)
        this.init()
    }

    private fun init() {
        aCache = ACache.get(this@CreateGestureActivity)
        lockPatternIndicator = findViewById(R.id.lockPatterIndicator)
        lockPatternView = findViewById(R.id.lockPatternView)
        resetBtn = findViewById(R.id.resetBtn)
        messageTv = findViewById(R.id.messageTv)
        resetBtn.setOnClickListener { resetLockPattern() }

        lockPatternView.setOnPatternListener(patternListener)
    }

    /**
     * 更新状态
     * @param status
     * @param pattern
     */
    private fun updateStatus(status: Status, pattern: List<LockPatternView.Cell>?) {
        messageTv.setTextColor(resources.getColor(status.colorId))
        messageTv.setText(status.strId)
        when (status) {
            CreateGestureActivity.Status.DEFAULT -> lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
            CreateGestureActivity.Status.CORRECT -> {
                updateLockPatternIndicator()
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
            }
            CreateGestureActivity.Status.LESSERROR -> lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
            CreateGestureActivity.Status.CONFIRMERROR -> {
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR)
                lockPatternView.postClearPatternRunnable(DELAYTIME)
            }
            CreateGestureActivity.Status.CONFIRMCORRECT -> {
                saveChosenPattern(pattern)
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
                setLockPatternSuccess()
            }
        }
    }

    /**
     * 更新 Indicator
     */
    private fun updateLockPatternIndicator() {
        if (mChosenPattern == null)
            return
        lockPatternIndicator.setIndicator(mChosenPattern!!)
    }

    /**
     * 重新设置手势
     */
    internal fun resetLockPattern() {
        mChosenPattern = null
        lockPatternIndicator.setDefaultIndicator()
        updateStatus(Status.DEFAULT, null)
        lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT)
    }

    /**
     * 成功设置了手势密码(跳到首页)
     */
    private fun setLockPatternSuccess() {
        Toast.makeText(this, "create gesture success", Toast.LENGTH_SHORT).show()
    }

    /**
     * 保存手势密码
     */
    private fun saveChosenPattern(cells: List<LockPatternView.Cell>?) {
        val bytes = LockPatternUtil.patternToHash(cells)
        aCache!!.put(GESTURE_PASSWORD, bytes)
    }

    private enum class Status private constructor( val strId: Int,  val colorId: Int) {
        //默认的状态，刚开始的时候（初始化状态）
        DEFAULT(R.string.create_gesture_default, R.color.grey_a5a5a5),
        //第一次记录成功
        CORRECT(R.string.create_gesture_correct, R.color.grey_a5a5a5),
        //连接的点数小于4（二次确认的时候就不再提示连接的点数小于4，而是提示确认错误）
        LESSERROR(R.string.create_gesture_less_error, R.color.red_f4333c),
        //二次确认错误
        CONFIRMERROR(R.string.create_gesture_confirm_error, R.color.red_f4333c),
        //二次确认正确
        CONFIRMCORRECT(R.string.create_gesture_confirm_correct, R.color.grey_a5a5a5)
    }

    companion object {

        @JvmStatic
        private val DELAYTIME = 600L

        @JvmStatic
        private val TAG = "CreateGestureActivity"

        @JvmStatic
        val GESTURE_PASSWORD = "GesturePassword"
    }
}
