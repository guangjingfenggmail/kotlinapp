package com.open.app.model.data

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
class LastestBean:Serializable{

    lateinit var date:String
    lateinit var stories:List<ContactBean>
//    lateinit var top_stories:List<TopStoriesBean>

//    inner class StorieBean:Serializable{
//        lateinit var images:List<String>
//        lateinit var type:String
//        lateinit var id:String
//        lateinit var ga_prefix:String
//    }

//    inner class TopStoriesBean:Serializable{
//        lateinit var image:String
//        lateinit var type:String
//        lateinit var id:String
//        lateinit var ga_prefix:String
//    }
}