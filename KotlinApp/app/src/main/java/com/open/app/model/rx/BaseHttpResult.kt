package com.open.app.model.rx

import java.io.Serializable


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
class BaseHttpResult<T>(var status:Int,var message:String,var data:T) :Serializable{

    override fun toString(): String {
        return "BaseHttpResult{" +
                "status:" +status
                ",message:" +message
                ",data:" +data
                "}"
    }

}