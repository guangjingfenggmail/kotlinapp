package com.open.app.model.service

import android.util.Log
import com.google.gson.Gson
import com.open.app.model.data.LastestBean
import com.open.app.model.data.NewsInfoBean
import com.open.app.model.rx.*
import com.open.app.model.rx.callback.ResponseCallback
import com.open.app.model.rx.error.ApiException
import com.open.app.model.rx.transformer.ObectTransformer
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
            var call: Call<LastestBean> = RetrofitManager.getService(Api::class.java)
                    .latest()
            RetrofitManager.enqueueAdapter(call, callback)
        }


        @JvmStatic
        fun getNewsInfo(id: String, callback: ResponseCallback<NewsInfoBean>) {
            RetrofitManager.getService(Api::class.java)
                    .getNewsInfo(id)
                    .compose(ObectTransformer<NewsInfoBean>())
                    .subscribe(object : CommonSubscriber<NewsInfoBean>() {
                        override fun onNext(result: NewsInfoBean) {
//                            callBack.onSuccess(result)
                            var gson :Gson = Gson()
                            Log.e("ContactService",gson.toJson(result))
                        }

                        override fun onError(e: ApiException) {
//                            super.onError(e)
//                            callBack.onFailure(e.message)
                            Log.e("ContactService",e.msg)
                        }

                        override fun onStart() {

                        }
                    })

        }

    }

}