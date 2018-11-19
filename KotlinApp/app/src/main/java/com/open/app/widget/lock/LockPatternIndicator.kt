package com.open.app.widget.lock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.open.app.R

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
class LockPatternIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var cellBoxWidth: Int = 0
    private var cellBoxHeight: Int = 0
    private var radius: Int = 0
    private val offset = 2
    private var defaultPaint: Paint? = null
    private var selectPaint: Paint? = null
    private val mIndicatorCells = Array<Array<IndicatorCell?>>(3) { arrayOfNulls(3) }

    init {
        this.init()
    }


    private fun init() {
        //initViewSize(context, attrs);
        initRadius()
        initPaint()
        init9IndicatorCells()
    }

    /**
     * init view size
     * @param context
     * @param attrs
     */
    @Deprecated("")
    private fun initViewSize(context: Context, attrs: AttributeSet) {
        for (i in 0 until attrs.attributeCount) {
            val name = attrs.getAttributeName(i)
            if ("layout_width" == name) {
                val value = attrs.getAttributeValue(i)
                this.mWidth = LockPatternUtil.changeSize(context, value)
                //Log.e(TAG, "layout_width:" + value);
            }
            if ("layout_height" == attrs.getAttributeName(i)) {
                val value = attrs.getAttributeValue(i)
                this.mHeight = LockPatternUtil.changeSize(context, value)
                //Log.e(TAG, "layout_height:" + value);
            }
        }
        //check the width is or not equals height
        //if not throw exception
        if (this.mWidth != this.mHeight) {
            throw IllegalArgumentException("the width must be equals height")
        }
    }

    private fun initRadius() {
        this.radius = (this.mWidth - offset * 2) / 4 / 2
        this.cellBoxHeight = (this.mHeight - offset * 2) / 3
        this.cellBoxWidth = (this.mWidth - offset * 2) / 3
    }

    private fun initPaint() {
        defaultPaint = Paint()
        defaultPaint!!.color = resources.getColor(R.color.grey_b2b2b2)
        defaultPaint!!.strokeWidth = 3.0f
        defaultPaint!!.style = Paint.Style.STROKE
        defaultPaint!!.isAntiAlias = true

        selectPaint = Paint()
        selectPaint!!.color = resources.getColor(R.color.blue_01aaee)
        selectPaint!!.strokeWidth = 3.0f
        selectPaint!!.style = Paint.Style.FILL
        selectPaint!!.isAntiAlias = true
    }

    /**
     * initialize nine cells
     */
    private fun init9IndicatorCells() {
        val distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.radius
        for (i in 0..2) {
            for (j in 0..2) {
                mIndicatorCells[i][j] = IndicatorCell(distance * j + radius + offset, distance * i + radius + offset, 3 * i + j + 1)
            }
        }
    }

    /**
     * set nine indicator cells size
     */
    private fun set9IndicatorCellsSize() {
        val distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.radius
        for (i in 0..2) {
            for (j in 0..2) {
                mIndicatorCells[i][j]?.x = distance * j + radius + offset
                mIndicatorCells[i][j]?.y = distance * i + radius + offset
            }
        }
    }

    /**
     * set indicator
     * @param cells
     */
    fun setIndicator(cells: List<LockPatternView.Cell>) {
        for (cell in cells) {
            for (i in mIndicatorCells.indices) {
                for (j in 0 until mIndicatorCells[i].size) {
                    if (cell.index === mIndicatorCells[i][j]?.index) {
                        //Log.e(TAG, String.valueOf(cell.getRow() * 3 + cell.getColumn() + 1));
                        mIndicatorCells[i][j]?.status = IndicatorCell.STATE_CHECK
                    }
                }
            }
        }
        this.postInvalidate()
    }

    /**
     * set default indicator
     */
    fun setDefaultIndicator() {
        for (i in mIndicatorCells.indices) {
            for (j in 0 until mIndicatorCells[i].size) {
                mIndicatorCells[i][j]?.status = IndicatorCell.STATE_NORMAL
            }
        }
        this.postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawToCanvas(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.mWidth = measuredWidth
        this.mHeight = measuredHeight
        if (this.mWidth != this.mHeight) {
            throw IllegalArgumentException("the width must be equals height")
        }
        this.initRadius()
        this.set9IndicatorCellsSize()
        this.invalidate()
    }

    /**
     * draw the view to canvas
     * @param canvas
     */
    private fun drawToCanvas(canvas: Canvas) {
        for (i in mIndicatorCells.indices) {
            for (j in 0 until mIndicatorCells[i].size) {
                if (mIndicatorCells[i][j]?.status == IndicatorCell.STATE_NORMAL) {
                    canvas.drawCircle(mIndicatorCells[i][j]!!.x.toFloat(), mIndicatorCells[i][j]!!.y.toFloat(), radius.toFloat(), defaultPaint!!)
                } else if (mIndicatorCells[i][j]?.status == IndicatorCell.STATE_CHECK) {
                    canvas.drawCircle(mIndicatorCells[i][j]!!.x.toFloat(), mIndicatorCells[i][j]!!.y.toFloat(), radius.toFloat(), selectPaint!!)
                }
            }
        }
    }

     class IndicatorCell {
        var x: Int = 0// the center x of circle
        var y: Int = 0// the center y of circle
        var status = 0//default
        var index: Int = 0// the cell value

        constructor() {}

        constructor(x: Int, y: Int, index: Int) {
            this.x = x
            this.y = y
            this.index = index
        }

        companion object {
            @JvmStatic
            val STATE_NORMAL = 0

            @JvmStatic
            val STATE_CHECK = 1
        }
    }

    companion object {

        @JvmStatic
        private val TAG = "LockPatternIndicator"
    }

}
