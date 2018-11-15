package com.open.app.model.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.open.app.model.data.ContactBean


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
class ContactDatabase(){

    fun getContactPhone(context: Context):List<ContactBean>{
        var list:MutableList<ContactBean> = ArrayList<ContactBean>()
        var uri:Uri = ContactsContract.Contacts.CONTENT_URI
        var projection:Array<String> = arrayOf(ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME)
        var cursor:Cursor
        try {
            cursor = context.contentResolver.query(uri,projection,null,null,null)
            if (cursor!=null && cursor.moveToFirst()){
                do {
                    var detailCursor:Cursor
                    var numbers:MutableList<String> = ArrayList<String>()
                    var id:Long = cursor.getLong(0)
                    var name:String = cursor.getString(1)
                    var phoneProjection:Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    try {
                        detailCursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                phoneProjection,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,
                                null,null)
                        if (detailCursor!=null && detailCursor.moveToFirst()){
                            do {
                                var number:String = detailCursor.getString(0)
                                numbers.add(number)
                            }while (detailCursor.moveToNext())
                            detailCursor.close()
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }finally {

                    }
                    list.add(ContactBean(name,numbers,""))
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return list
    }

}