package com.open.app.model.rx.base

import com.open.app.model.rx.error.ApiException
import rx.Subscriber

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

abstract class BaseSubscriber<T>: Subscriber<T>() {

    override fun onError(e: Throwable?) {
        var apiException: ApiException = e as ApiException;
        onError(apiException)
    }


     abstract fun onError(e: ApiException)
}