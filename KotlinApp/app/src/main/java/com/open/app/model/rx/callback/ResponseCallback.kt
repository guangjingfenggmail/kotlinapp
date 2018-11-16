package com.open.app.model.rx.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * ****************************************************************************************************************************************************************************
 *
 * @author : guangjing.feng
 * @createTime:  2018/11/15
 * @version: 1.0.0
 * @modifyTime: 2018/11/15
 * @modifyAuthor: guangjing.feng
 * @description:
 * ****************************************************************************************************************************************************************************
 */
interface ResponseCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }
}