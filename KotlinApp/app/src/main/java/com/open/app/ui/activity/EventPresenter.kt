package com.open.app.ui.activity

import android.view.View
import com.open.app.viewmodel.WelcomeViewModel

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
interface EventPresenter {

    /**
     * view click 事件　
     */
    fun onClick(view: View, viewmodel: WelcomeViewModel)


    fun onClick(view:View)


}