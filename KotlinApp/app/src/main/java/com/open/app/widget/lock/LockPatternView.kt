package com.open.app.widget.lock

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import com.open.app.R
import java.util.ArrayList

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
class LockPatternView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var movingX: Float = 0.toFloat()
    private var movingY: Float = 0.toFloat()
    private var isActionMove = false
    private var isActionDown = false//default action down is false
    private var isActionUp = true//default action up is true

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var cellRadius: Int = 0
    private var cellInnerRadius: Int = 0
    private var cellBoxWidth: Int = 0
    private var cellBoxHeight: Int = 0
    //in stealth mode (default is false)
    /**
     * @return Whether the view is in stealth mode.
     */
    /**
     * Set whether the view is in stealth mode.  If true, there will be no
     * visible feedback as the user enters the pattern.
     * @param inStealthMode Whether in stealth mode.
     */
    var isInStealthMode = false
    //haptic feed back (default is false)
    /**
     * @return Whether the view has tactile feedback enabled.
     */
    /**
     * Set whether the view will use tactile feedback.  If true, there will be
     * tactile feedback as the user enters the pattern.
     * @param tactileFeedbackEnabled Whether tactile feedback is enabled
     */
    var isTactileFeedbackEnabled = false
    //set delay time
    private var delayTime = 600L
    //set offset to the boundary
    private val offset = 10
    //draw view used paint
    lateinit var defaultPaint: Paint
    lateinit var selectPaint: Paint
    lateinit var errorPaint: Paint
    lateinit var trianglePath: Path
    lateinit var triangleMatrix: Matrix

    private var mCells : Array<Array<Cell>> = Array(3) { arrayOf(Cell(),Cell(),Cell()) }
    private var sCells = ArrayList<Cell>()
    private var patterListener: OnPatternListener? = null

    private val mClearPatternRunnable = Runnable { this@LockPatternView.setPattern(DisplayMode.DEFAULT) }

    init {
        this.init()
    }

    /**
     * initialize
     */
    private fun init() {
        this.initCellSize()
        this.init9Cells()
        this.initPaints()
        this.initPaths()
        this.initMatrixs()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.drawToCanvas(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measuredHeight
        this.mWidth = measuredWidth
        this.mHeight = measuredHeight
        //Log.e(TAG, "(width: " + width + "  ,  height" + height + ")");
        if (width != height) {
            throw IllegalArgumentException("the width must be equals height")
        }
        this.initCellSize()
        this.set9CellsSize()
        this.invalidate()
    }

    /**
     * draw the view to canvas
     * @param canvas
     */
    private fun drawToCanvas(canvas: Canvas) {

        for (i in mCells.indices) {
            for (j in 0 until mCells[i].size) {
                if (mCells[i][j].status == Cell.STATE_CHECK) {
                    selectPaint!!.style = Paint.Style.STROKE
                    canvas.drawCircle(mCells[i][j].x.toFloat(), mCells[i][j].y.toFloat(),
                            this.cellRadius.toFloat(), this.selectPaint!!)
                    selectPaint!!.style = Paint.Style.FILL
                    canvas.drawCircle(mCells[i][j].x.toFloat(), mCells[i][j].y.toFloat(),
                            this.cellInnerRadius.toFloat(), this.selectPaint!!)
                } else if (mCells[i][j].status == Cell.STATE_NORMAL) {
                    canvas.drawCircle(mCells[i][j].x.toFloat(), mCells[i][j].y.toFloat(),
                            this.cellRadius.toFloat(), this.defaultPaint!!)
                } else if (mCells[i][j].status == Cell.STATE_CHECK_ERROR) {
                    errorPaint!!.style = Paint.Style.STROKE
                    canvas.drawCircle(mCells[i][j].x.toFloat(), mCells[i][j].y.toFloat(),
                            this.cellRadius.toFloat(), this.errorPaint!!)
                    errorPaint!!.style = Paint.Style.FILL
                    canvas.drawCircle(mCells[i][j].x.toFloat(), mCells[i][j].y.toFloat(),
                            this.cellInnerRadius.toFloat(), this.errorPaint!!)
                }
            }
        }

        if (sCells.size > 0) {
            //temporary cell: at the beginning the cell is the first of sCells
            var tempCell = sCells[0]

            for (i in 1 until sCells.size) {
                val cell = sCells[i]
                if (cell.status == Cell.STATE_CHECK) {
                    //drawLineIncludeCircle(tempCell, cell, canvas , selectPaint);
                    drawLine(tempCell, cell, canvas, selectPaint)
                    //drawTriangle(tempCell, cell, canvas, selectPaint);
                    drawNewTriangle(tempCell, cell, canvas, selectPaint)
                } else if (cell.status == Cell.STATE_CHECK_ERROR) {
                    //drawLineIncludeCircle(tempCell, cell, canvas, errorPaint);
                    drawLine(tempCell, cell, canvas, errorPaint)
                    //drawTriangle(tempCell, cell, canvas, errorPaint);
                    drawNewTriangle(tempCell, cell, canvas, errorPaint)
                }
                tempCell = cell
            }

            if (isActionMove && !isActionUp) {
                //canvas.drawLine(tempCell.getX(), tempCell.getY(), movingX, movingY, selectPaint);
                this.drawLineFollowFinger(tempCell, canvas, selectPaint)
            }
        }
    }

    /**
     * initialize the view size (include the view width and the view height fro the AttributeSet)
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
            }
            if ("layout_height" == attrs.getAttributeName(i)) {
                val value = attrs.getAttributeValue(i)
                this.mHeight = LockPatternUtil.changeSize(context, value)
            }
        }
        //check the width is or not equals height.
        //if not throw exception
        if (this.mWidth != this.mHeight) {
            throw IllegalArgumentException("the width must be equals height")
        }
    }

    /**
     * initialize cell size (include circle radius, inner circle radius,
     * cell box width, cell box height)
     */
    private fun initCellSize() {
        this.cellRadius = (this.mWidth - offset * 2) / 4 / 2
        this.cellInnerRadius = this.cellRadius / 3
        this.cellBoxWidth = (this.mWidth - offset * 2) / 3
        this.cellBoxHeight = (this.mHeight - offset * 2) / 3
    }

    /**
     * initialize nine cells
     */
    private fun init9Cells() {
        //the distance between the center of two circles
        val distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.cellRadius
        for (i in 0..2) {
            for (j in 0..2) {
                mCells[i][j] = Cell(distance * j + cellRadius + offset,
                        distance * i + cellRadius + offset, i, j, 3 * i + j + 1)
            }
        }
    }

    /**
     * set nine cells size
     */
    private fun set9CellsSize() {
        val distance = this.cellBoxWidth + this.cellBoxWidth / 2 - this.cellRadius
        for (i in 0..2) {
            for (j in 0..2) {
                mCells[i][j].x = distance * j + cellRadius + offset
                mCells[i][j].y = distance * i + cellRadius + offset
            }
        }
    }

    /**
     * initialize paints
     */
    private fun initPaints() {
        defaultPaint = Paint()
        defaultPaint!!.color = resources.getColor(R.color.blue_78d2f6)
        defaultPaint!!.strokeWidth = 2.0f
        defaultPaint!!.style = Paint.Style.STROKE
        defaultPaint!!.isAntiAlias = true

        selectPaint = Paint()
        selectPaint!!.color = resources.getColor(R.color.blue_00aaee)
        selectPaint!!.strokeWidth = 3.0f
        //selectPaint.setStyle(Style.STROKE);
        selectPaint!!.isAntiAlias = true

        errorPaint = Paint()
        errorPaint!!.color = resources.getColor(R.color.red_f3323b)
        errorPaint!!.strokeWidth = 3.0f
        //errorPaint.setStyle(Style.STROKE);
        errorPaint!!.isAntiAlias = true
    }

    /**
     * initialize paths
     */
    private fun initPaths() {
        trianglePath = Path()
    }

    /**
     * initialize matrixs
     */
    private fun initMatrixs() {
        triangleMatrix = Matrix()
    }

    /**
     * draw line include circle
     * (the line include inside the circle, the method is deprecated)
     * @param preCell
     * @param nextCell
     * @param canvas
     * @param paint
     */
    @Deprecated("")
    private fun drawLineIncludeCircle(preCell: Cell, nextCell: Cell, canvas: Canvas, paint: Paint) {
        canvas.drawLine(preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat(), paint)
    }

    /**
     * draw line not include circle (check whether the cell between two cells )
     * @param preCell
     * @param nextCell
     * @param canvas
     * @param paint
     */
    private fun drawLine(preCell: Cell, nextCell: Cell, canvas: Canvas, paint: Paint) {
        val centerCell = getCellBetweenTwoCells(preCell, nextCell)
        if (centerCell != null && sCells.contains(centerCell)) {
            drawLineNotIncludeCircle(centerCell, preCell, canvas, paint)
            drawLineNotIncludeCircle(centerCell, nextCell, canvas, paint)
        } else {
            drawLineNotIncludeCircle(preCell, nextCell, canvas, paint)
        }
    }

    /**
     * draw line not include circle (the line do not show inside the circle)
     * @param preCell
     * @param nextCell
     * @param canvas
     * @param paint
     */
    private fun drawLineNotIncludeCircle(preCell: Cell, nextCell: Cell, canvas: Canvas, paint: Paint) {
        val distance = LockPatternUtil.getDistanceBetweenTwoPoints(
                preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat())
        val x1 = this.cellRadius / distance * (nextCell.x - preCell.x) + preCell.x
        val y1 = this.cellRadius / distance * (nextCell.y - preCell.y) + preCell.y
        val x2 = (distance - this.cellRadius) / distance * (nextCell.x - preCell.x) + preCell.x
        val y2 = (distance - this.cellRadius) / distance * (nextCell.y - preCell.y) + preCell.y
        canvas.drawLine(x1, y1, x2, y2, paint)
    }

    /**
     * get the cell between two cells (it has the limitation: the pattern must be 3x3)
     * @param preCell previous cell
     * @param nextCell next cell
     * @return Cell
     */
    private fun getCellBetweenTwoCells(preCell: Cell, nextCell: Cell): Cell? {
        //two cells are in the same row
        return if (preCell.row == nextCell.row) {
            if (Math.abs(nextCell.column - preCell.column) > 1) {
                mCells[preCell.row][1]
            } else {
                null
            }
        } else if (preCell.column == nextCell.column) {
            if (Math.abs(nextCell.row - preCell.row) > 1) {
                mCells[1][preCell.column]
            } else {
                null
            }
        } else if (Math.abs(nextCell.column - preCell.column) > 1 && Math.abs(nextCell.row - preCell.row) > 1) {
            mCells[1][1]
        } else {
            null
        }//opposite angles
        //two cells are in the same column
    }

    /**
     * draw line follow finger
     * (do not draw line inside the selected cell,
     * but it is only the starting cell not the other's cell)
     * @param preCell
     * @param canvas
     * @param paint
     */
    private fun drawLineFollowFinger(preCell: Cell, canvas: Canvas, paint: Paint) {
        val distance = LockPatternUtil.getDistanceBetweenTwoPoints(
                preCell.x.toFloat(), preCell.y.toFloat(), movingX, movingY)
        if (distance > this.cellRadius) {
            val x1 = this.cellRadius / distance * (movingX - preCell.x) + preCell.x
            val y1 = this.cellRadius / distance * (movingY - preCell.y) + preCell.y
            canvas.drawLine(x1, y1, movingX, movingY, paint)
        }
    }

    /**
     * draw triangle
     * @param preCell the previous selected cell
     * @param nextCell the next selected cell
     * @param canvas
     * @param paint
     */
    @Deprecated("")
    private fun drawTriangle(preCell: Cell, nextCell: Cell, canvas: Canvas, paint: Paint) {
        val distance = LockPatternUtil.getDistanceBetweenTwoPoints(preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat())
        val x = this.cellInnerRadius * 2 / distance * (nextCell.x - preCell.x) + preCell.x
        val y = this.cellInnerRadius * 2 / distance * (nextCell.y - preCell.y) + preCell.y

        val angleX = LockPatternUtil.getAngleLineIntersectX(
                preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat(), distance)
        val angleY = LockPatternUtil.getAngleLineIntersectY(
                preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat(), distance)
        val x1: Float
        val y1: Float
        val x2: Float
        val y2: Float
        //slide right down
        if (angleX >= 0 && angleX <= 90 && angleY >= 0 && angleY <= 90) {
            x1 = x - (cellInnerRadius * Math.cos(Math.toRadians((angleX - 30).toDouble()))).toFloat()
            y1 = y - (cellInnerRadius * Math.sin(Math.toRadians((angleX - 30).toDouble()))).toFloat()
            x2 = x - (cellInnerRadius * Math.sin(Math.toRadians((angleY - 30).toDouble()))).toFloat()
            y2 = y - (cellInnerRadius * Math.cos(Math.toRadians((angleY - 30).toDouble()))).toFloat()
        } else if (angleX >= 0 && angleX <= 90 && angleY > 90 && angleY <= 180) {
            x1 = x - (cellInnerRadius * Math.cos(Math.toRadians((angleX + 30).toDouble()))).toFloat()
            y1 = y + (cellInnerRadius * Math.sin(Math.toRadians((angleX + 30).toDouble()))).toFloat()
            x2 = x - (cellInnerRadius * Math.sin(Math.toRadians((180 - angleY + 30).toDouble()))).toFloat()
            y2 = y + (cellInnerRadius * Math.cos(Math.toRadians((180 - angleY + 30).toDouble()))).toFloat()
        } else if (angleX > 90 && angleX <= 180 && angleY >= 90 && angleY < 180) {
            x1 = x + (cellInnerRadius * Math.cos(Math.toRadians((180f - angleX - 30f).toDouble()))).toFloat()
            y1 = y + (cellInnerRadius * Math.sin(Math.toRadians((180f - angleX - 30f).toDouble()))).toFloat()
            x2 = x + (cellInnerRadius * Math.sin(Math.toRadians((180f - angleY - 30f).toDouble()))).toFloat()
            y2 = y + (cellInnerRadius * Math.cos(Math.toRadians((180f - angleY - 30f).toDouble()))).toFloat()
        } else {
            x1 = x + (cellInnerRadius * Math.cos(Math.toRadians((180 - angleX + 30).toDouble()))).toFloat()
            y1 = y - (cellInnerRadius * Math.sin(Math.toRadians((180 - angleX + 30).toDouble()))).toFloat()
            x2 = x + (cellInnerRadius * Math.sin(Math.toRadians((angleY + 30).toDouble()))).toFloat()
            y2 = y - (cellInnerRadius * Math.cos(Math.toRadians((angleY + 30).toDouble()))).toFloat()
        }//slide left down
        //slide left up
        //slide right up
        trianglePath!!.reset()
        trianglePath!!.moveTo(x, y)
        trianglePath!!.lineTo(x1, y1)
        trianglePath!!.lineTo(x2, y2)
        trianglePath!!.close()
        canvas.drawPath(trianglePath!!, paint)
    }

    /**
     * draw new triangle
     * @param preCell
     * @param nextCell
     * @param canvas
     * @param paint
     */
    private fun drawNewTriangle(preCell: Cell, nextCell: Cell, canvas: Canvas, paint: Paint) {
        val distance = LockPatternUtil.getDistanceBetweenTwoPoints(preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat())
        val x = preCell.x.toFloat()
        val y = (preCell.y - this.cellInnerRadius * 2).toFloat()

        val x1 = x - this.cellInnerRadius / 2
        val y1 = y + (this.cellInnerRadius * CONSTANT_COS_30).toFloat()
        val x2 = x + this.cellInnerRadius / 2

        val angleX = LockPatternUtil.getAngleLineIntersectX(
                preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat(), distance)
        val angleY = LockPatternUtil.getAngleLineIntersectY(
                preCell.x.toFloat(), preCell.y.toFloat(), nextCell.x.toFloat(), nextCell.y.toFloat(), distance)

        trianglePath!!.reset()
        trianglePath!!.moveTo(x, y)
        trianglePath!!.lineTo(x1, y1)
        trianglePath!!.lineTo(x2, y1)
        trianglePath!!.close()
        //slide right down and right up
        if (angleX >= 0 && angleX <= 90) {
            triangleMatrix!!.setRotate(180 - angleY, preCell.x.toFloat(), preCell.y.toFloat())
        } else {
            triangleMatrix!!.setRotate(angleY - 180, preCell.x.toFloat(), preCell.y.toFloat())
        }//slide left up and left down
        trianglePath!!.transform(triangleMatrix)
        canvas.drawPath(trianglePath!!, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val ex = event.x
        val ey = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> handleActionDown(ex, ey)
            MotionEvent.ACTION_MOVE -> handleActionMove(ex, ey)
            MotionEvent.ACTION_UP -> handleActionUp()
        }
        return true
    }

    /**
     * handle action down
     * @param ex
     * @param ey
     */
    private fun handleActionDown(ex: Float, ey: Float) {
        isActionMove = false
        isActionDown = true
        isActionUp = false

        this.setPattern(DisplayMode.DEFAULT)

        if (this.patterListener != null) {
            this.patterListener!!.onPatternStart()
        }

        val cell = checkSelectCell(ex, ey)
        if (cell != null) {
            addSelectedCell(cell)
        }
    }

    /**
     * handle action move
     * @param ex
     * @param ey
     */
    private fun handleActionMove(ex: Float, ey: Float) {
        isActionMove = true
        movingX = ex
        movingY = ey
        val cell = checkSelectCell(ex, ey)
        if (cell != null) {
            addSelectedCell(cell)
        }
        this.setPattern(DisplayMode.NORMAL)
    }

    /**
     * handle action up
     */
    private fun handleActionUp() {
        isActionMove = false
        isActionUp = true
        isActionDown = false

        this.setPattern(DisplayMode.NORMAL)

        if (this.patterListener != null) {
            this.patterListener!!.onPatternComplete(sCells)
        }
    }

    /**
     * check user's touch moving is or not in the area of cells
     * @param x
     * @param y
     * @return
     */
    private fun checkSelectCell(x: Float, y: Float): Cell? {
        for (i in mCells.indices) {
            for (j in 0 until mCells[i].size) {
                val cell = mCells[i][j]
                if (LockPatternUtil.checkInRound(cell.x.toFloat(), cell.y.toFloat(), 80.toFloat(), x, y, (this.cellRadius / 4).toFloat())) {
                    return cell
                }
            }
        }
        return null
    }

    /**
     * add selected cell
     * @param cell
     */
    private fun addSelectedCell(cell: Cell) {
        if (!sCells.contains(cell)) {
            cell.status = Cell.STATE_CHECK
            handleHapticFeedback()
            sCells.add(cell)
        }
        setPattern(DisplayMode.NORMAL)
    }

    /**
     * handle haptic feedback
     * (if mEnableHapticFeedback true: has haptic else not have haptic)
     */
    private fun handleHapticFeedback() {
        if (isTactileFeedbackEnabled) {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                    HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING or HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
        }
    }

    /**
     * set default selected cell (the method is deprecated)
     * @param value (ex: 2,1,3,6,4,5)
     * @return the selected cell
     */
    @Deprecated("")
    fun setDefaultSelectedCell(value: String): List<Cell> {
        val str = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in str.indices) {
            val `val` = Integer.valueOf(str[i])!!
            if (`val` <= 3) {
                addSelectedCell(mCells[0][`val` - 1])
            } else if (`val` <= 6) {
                addSelectedCell(mCells[1][`val` - 4])
            } else {
                addSelectedCell(mCells[2][`val` - 7])
            }
        }
        return sCells
    }

    /**
     * the display mode of the pattern
     */
    enum class DisplayMode {
        //show default pattern (the default pattern is initialize status)
        DEFAULT,
        //show selected pattern normal
        NORMAL,
        //show selected pattern error
        ERROR
    }

    /**
     * set pattern
     * @param mode (details see the DisplayMode)
     */
    fun setPattern(mode: DisplayMode) {
        when (mode) {
            LockPatternView.DisplayMode.DEFAULT -> {
                for (cell in sCells) {
                    cell.status = Cell.STATE_NORMAL
                }
                sCells.clear()
            }
            LockPatternView.DisplayMode.NORMAL -> {
            }
            LockPatternView.DisplayMode.ERROR -> for (cell in sCells) {
                cell.status = Cell.STATE_CHECK_ERROR
            }
        }
        this.handleStealthMode()
    }

    /**
     * handle the stealth mode (if true: do not post invalidate; false: post invalidate)
     */
    private fun handleStealthMode() {
        if (!isInStealthMode) {
            this.postInvalidate()
        }
    }

    /**
     * remove the post delay clear pattern
     */
    fun removePostClearPatternRunnable() {
        this.removeCallbacks(mClearPatternRunnable)
    }

    /**
     * delay clear pattern
     * @param delay the delay time (if delay less than 0, it will be 600L)
     */
    fun postClearPatternRunnable(delay: Long) {
        if (delay >= 0L) {
            delayTime = delay
        }
        this.removeCallbacks(mClearPatternRunnable)
        this.postDelayed(mClearPatternRunnable, delayTime)
    }

    fun setOnPatternListener(patternListener: OnPatternListener) {
        this.patterListener = patternListener
    }

    /**
     * callback interface
     */
    interface OnPatternListener {
        fun onPatternStart()
        fun onPatternComplete(cells: List<Cell>)
    }

      class Cell {

        var x: Int = 0// the x position of circle's center point
        var y: Int = 0// the y position of circle's center point
        var row: Int = 0// the cell in which row
        var column: Int = 0// the cell in which column
        var index: Int = 0// the cell value
        var status = 0//default status

        constructor() {}

        constructor(x: Int, y: Int, row: Int, column: Int, index: Int) {
            this.x = x
            this.y = y
            this.row = row
            this.column = column
            this.index = index
        }

        companion object {

            //default status
            @JvmStatic
             val STATE_NORMAL = 0

            //checked status
            @JvmStatic
             val STATE_CHECK = 1

            //checked error status
            @JvmStatic
             val STATE_CHECK_ERROR = 2
        }

    }

    companion object {
        @JvmStatic
        private val TAG = "LockPatternView"

        @JvmStatic
        private val CONSTANT_COS_30 = Math.cos(Math.toRadians(30.0))
    }

}
