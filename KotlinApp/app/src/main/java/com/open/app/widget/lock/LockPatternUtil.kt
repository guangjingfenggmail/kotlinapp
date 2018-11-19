package com.open.app.widget.lock

import android.content.Context
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * ****************************************************************************************************************************************************************************
 *
 * @author :guangjing.feng
 * @createTime: 2018/11/19.
 * @version:1.1.0
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *
 * *****************************************************************************************************************************************************************************
 **/
object LockPatternUtil {

    /**
     * change string value (ex: 10.0dip => 20) to int value
     *
     * @param context
     * @param value
     * @return
     */
    @Deprecated("")
    @JvmStatic
    fun changeSize(context: Context, value: String): Int {
        if (value.contains("dip")) {
            val dip = java.lang.Float.valueOf(value.substring(0, value.indexOf("dip")))!!
            return LockPatternUtil.dip2px(context, dip)
        } else if (value.contains("px")) {
            val px = java.lang.Float.valueOf(value.substring(0, value.indexOf("px")))!!
            return px.toInt()
        } else if (value.contains("@")) {
            val px = context.resources.getDimension(Integer.valueOf(value.replace("@", ""))!!)
            return px.toInt()
        } else {
            throw IllegalArgumentException("can not use wrap_content " + "or match_parent or fill_parent or others' illegal parameter")
        }
    }

    /**
     * dip to px
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * check the touch cell is or not in the circle
     *
     * @param sx
     * @param sy
     * @param r      the radius of circle
     * @param x      the x position of circle's center point
     * @param y      the y position of circle's center point
     * @param offset the offset to the frame of the circle
     * (if offset > 0 : the offset is inside the circle; if offset < 0 : the offset is outside the circle)
     * @return
     */
    @JvmStatic
    fun checkInRound(sx: Float, sy: Float, r: Float, x: Float, y: Float, offset: Float): Boolean {
        return Math.sqrt(((sx - x + offset) * (sx - x + offset) + (sy - y + offset) * (sy - y + offset)).toDouble()) < r
    }

    /**
     * get distance between two points
     *
     * @param fpX first point x position
     * @param fpY first point y position
     * @param spX second point x position
     * @param spY second point y position
     * @return
     */
    @JvmStatic
    fun getDistanceBetweenTwoPoints(fpX: Float, fpY: Float, spX: Float, spY: Float): Float {
        return Math.sqrt(((spX - fpX) * (spX - fpX) + (spY - fpY) * (spY - fpY)).toDouble()).toFloat()
    }

    /**
     * get the angle which the line intersect x axis
     *
     * @param fpX
     * @param fpY
     * @param spX
     * @param spY
     * @param distance
     * @return degrees
     */
    @JvmStatic
    fun getAngleLineIntersectX(fpX: Float, fpY: Float, spX: Float, spY: Float, distance: Float): Float {
        return Math.toDegrees(Math.acos(((spX - fpX) / distance).toDouble())).toFloat()
    }

    /**
     * get the angle which the line intersect y axis
     *
     * @param fpX
     * @param fpY
     * @param spX
     * @param spY
     * @param distance
     * @return degrees
     */
    @JvmStatic
    fun getAngleLineIntersectY(fpX: Float, fpY: Float, spX: Float, spY: Float, distance: Float): Float {
        return Math.toDegrees(Math.acos(((spY - fpY) / distance).toDouble())).toFloat()
    }

    /**
     * Generate an SHA-1 hash for the pattern. Not the most secure, but it is at
     * least a second level of protection. First level is that the file is in a
     * location only readable by the system process.
     *
     * @param pattern
     * @return the hash of the pattern in a byte array.
     */
    @JvmStatic
    fun patternToHash(pattern: List<LockPatternView.Cell>?): ByteArray? {
        if (pattern == null) {
            return null
        } else {
            val size = pattern.size
            val res = ByteArray(size)
            for (i in 0 until size) {
                val cell = pattern[i]
                res[i] = cell.index.toByte()
            }
            var md: MessageDigest? = null
            try {
                md = MessageDigest.getInstance("SHA-1")
                return md!!.digest(res)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                return res
            }

        }
    }

    /**
     * Check to see if a pattern matches the saved pattern. If no pattern
     * exists, always returns true.
     *
     * @param pattern
     * @param bytes
     * @return Whether the pattern matches the stored one.
     */
    fun checkPattern(pattern: List<LockPatternView.Cell>?, bytes: ByteArray?): Boolean {
        if (pattern == null || bytes == null) {
            return false
        } else {
            val bytes2 = patternToHash(pattern)
            return Arrays.equals(bytes, bytes2)
        }
    }
}
