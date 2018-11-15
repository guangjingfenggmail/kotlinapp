package com.open.app.model.service

import com.open.app.model.data.LastestBean
import com.open.app.model.rx.ResponseCallback
import com.open.app.model.rx.RetrofitManager
import retrofit2.Call


/**
 * ****************************************************************************************************************************************************************************
 *
 * @author : guangjing.feng
 * @createTime:  2018/11/14
 * @version: 1.0.0
 * @modifyTime: 2018/11/14
 * @modifyAuthor: guangjing.feng
 * @description:
 * ****************************************************************************************************************************************************************************
 */
class ContactService() {

    companion object {

        @JvmStatic
        fun latest(callback: ResponseCallback<LastestBean>) {
            var call:Call<LastestBean> = RetrofitManager.getService(Api::class.java)
                    .latest()
            RetrofitManager.enqueueAdapter(call,callback)
        }
    }

}