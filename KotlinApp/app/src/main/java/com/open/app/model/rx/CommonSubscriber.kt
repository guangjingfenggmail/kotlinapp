package com.open.app.model.rx

import com.open.app.model.rx.base.BaseSubscriber
import com.open.app.model.rx.error.ApiException

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/16.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/

abstract class CommonSubscriber<T> : BaseSubscriber<T>() {

    override fun onStart() {

    }

    override fun onError(e: ApiException) {

    }

    override fun onCompleted() {

    }
}