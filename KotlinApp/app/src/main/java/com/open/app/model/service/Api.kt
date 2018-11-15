package com.open.app.model.service

import com.open.app.model.data.LastestBean
import retrofit2.Call
import retrofit2.http.GET


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
interface Api {

    @GET("news/latest")
    fun latest() :Call<LastestBean>

}