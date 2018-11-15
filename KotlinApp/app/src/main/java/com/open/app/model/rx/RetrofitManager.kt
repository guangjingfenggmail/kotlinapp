package com.open.app.model.rx

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


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

class RetrofitManager {
    companion object {
        @JvmStatic
        val BASE_URL: String = "http://news-at.zhihu.com/api/4/"

        @JvmStatic
        lateinit var client: OkHttpClient

        @JvmStatic
        @Volatile
        lateinit var retrofit: Retrofit


        @JvmStatic
        fun <T> getService(clazz: Class<T>): T {
            return getRetrofit2().create(clazz)
        }

        @JvmStatic
        fun getRetrofit2(): Retrofit {
//            if (retrofit == null) {
                synchronized(RetrofitManager::class.java) {
//                    if (retrofit == null) {
                        var httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                        client = OkHttpClient.Builder()
                                .addInterceptor(httpLoggingInterceptor)
                                .addInterceptor(addQueryParameterInterceptor())
                                .addInterceptor(addHeaderInterceptor())
                                .connectTimeout(60, TimeUnit.SECONDS)
                                .readTimeout(60, TimeUnit.SECONDS)
                                .writeTimeout(60, TimeUnit.SECONDS)
                                .build()

                        retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .client(client)
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                    }
//                }
//            }
            return retrofit
        }

        @JvmStatic
        fun addQueryParameterInterceptor(): Interceptor {
            var interceptor: Interceptor = QueryParameterInterceptor()
            return interceptor
        }

        @JvmStatic
        fun addHeaderInterceptor(): Interceptor {
            var interceptor: Interceptor = HeaderInterceptor()
            return interceptor
        }

        @JvmStatic
        fun <T> enqueueAdapter(call: Call<T>, callback: ResponseCallback<T>): Call<T> {
            call.enqueue(callback)
            return call
        }

        @JvmStatic
        fun addCacheInterceptor():Interceptor{
            var interceptor:Interceptor = CacheInterceptor()
            return interceptor
        }
    }

    class QueryParameterInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var originRequest: Request = chain.request()
            var request: Request

            var url: HttpUrl = originRequest.url().newBuilder()
                    .addQueryParameter("111", "")
                    .addQueryParameter("222", "")
                    .build()
            request = originRequest.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }


    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var originRequest: Request = chain.request()
            var requestBuilder: Request.Builder = originRequest.newBuilder()
                    .header("token", "")
                    .header("333", "")
                    .method(originRequest.method(), originRequest.body())
            var request: Request = requestBuilder.build()
            return chain.proceed(request)
        }
    }


    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
//            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build()

            var response: Response = chain.proceed(request)
//            var maxAge: Int = 0
//            response.newBuilder()
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .removeHeader("Retrofit")
//                    .build()
//            val maxStale = 60 * 60 * 24 * 28
//            response.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" +
//                            maxStale)
//                    .removeHeader("nyn")
//                    .build();
            return response
        }
    }

}