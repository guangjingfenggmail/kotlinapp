package com.open.app.model.rx.transformer

import com.open.app.model.rx.error.ErrorObjectformer
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
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
class ObectTransformer<Object> : Observable.Transformer<Object, Object> {

    override fun call(tansFormerObservable: Observable<Object>): Observable<Object> {
        return tansFormerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorObjectformer.getInstance<Object>())
    }
}