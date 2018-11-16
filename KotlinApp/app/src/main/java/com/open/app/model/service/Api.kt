package com.open.app.model.service

import com.open.app.model.data.LastestBean
import com.open.app.model.data.NewsInfoBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


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
    fun latest(): Call<LastestBean>


    @GET("news/{id}")
    fun getNewsInfo(@Path("id") id: String): Observable<NewsInfoBean>

}