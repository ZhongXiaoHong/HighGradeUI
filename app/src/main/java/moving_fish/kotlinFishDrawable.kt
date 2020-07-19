package moving_fish

import android.graphics.*
import android.graphics.drawable.Drawable

class kotlinFishDrawable() : Drawable() {

    private lateinit var mPaint: Paint
    private lateinit var mPath: Path
    private lateinit var mainPoint: PointF;
    private var fishAngle = 0.0f

    init {
        mPaint = Paint()
        mPaint.setARGB(110, 244, 92, 71)
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.style = Paint.Style.FILL//填充模式
        mPaint.isDither = true //防抖
    }

    init {
        mPath = Path();
        mainPoint = PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)

    }

    private fun calculatePoint(startPoint: PointF, len: Float, angle: Float): PointF {

        var detaY = (-Math.sin(Math.toRadians(angle.toDouble())) * len).toFloat();
        var detaX = (Math.cos(Math.toRadians(angle.toDouble())) * len).toFloat();

        return PointF(startPoint.x + detaX, startPoint.y + detaY)
    }


    companion object {
        var HEAD_RADIUS = 100.00f
        var BODY_LEN = 3.2f * HEAD_RADIUS
        var BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS
        var MIDDLE_CIRCLE_RADIUS = 0.42f * HEAD_RADIUS
        var SMALL_CIRCLE_RADIUS = 0.168f * HEAD_RADIUS
        var WITH = (8.38f * HEAD_RADIUS).toInt()
        var HEIGHT = WITH
        var FIND_RIGHT_SWING_LEN = 0.9F * HEAD_RADIUS
        var SWING_LEN = 1.3F * HEAD_RADIUS
    }

    override fun draw(canvas: Canvas) {
        var headPoint = calculatePoint(mainPoint, 2.6f * HEAD_RADIUS, fishAngle)
        //画头部
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)
        //画左翅
        creatSwing(headPoint, canvas, true)
        //画右翅
        creatSwing(headPoint, canvas, false)
        //画节肢1
        var bottomCenterPoint = calculatePoint(headPoint, BODY_LEN, fishAngle - 180)
        var upCenterPoint = creatSegment(bottomCenterPoint, canvas, 1.12f * HEAD_RADIUS,
                BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, true)
        //画节肢2
        creatSegment(upCenterPoint, canvas, 1.302f * HEAD_RADIUS,
                MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, false)
        //画三角形
        creatTriangle(upCenterPoint, canvas,BIG_CIRCLE_RADIUS,MIDDLE_CIRCLE_RADIUS * 2.7f)
        creatTriangle(upCenterPoint, canvas,BIG_CIRCLE_RADIUS-20,MIDDLE_CIRCLE_RADIUS * 2.7f-10)
        //画身体
        creatBody(headPoint, bottomCenterPoint, canvas)
    }

    private fun creatBody(headPoint: PointF, bottomCenterPoint: PointF, canvas: Canvas) {
        var bodyLeftTopPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        var bodyLeftBottomPoint = calculatePoint(bottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90)

        var bodyRightTopPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        var bodyRightBottomPoint = calculatePoint(bottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90)

        var bodyLeftControl = calculatePoint(headPoint, BODY_LEN * 0.56f,
                fishAngle + 130)
        var bodyRightControl = calculatePoint(headPoint, BODY_LEN * 0.56f,
                fishAngle - 130)
        mPath.reset()
        mPath.moveTo(bodyLeftTopPoint.x, bodyLeftTopPoint.y)
        mPath.quadTo(bodyLeftControl.x, bodyLeftControl.y, bodyLeftBottomPoint.x, bodyLeftBottomPoint.y)

        mPath.lineTo(bodyRightBottomPoint.x, bodyRightBottomPoint.y)
        mPath.quadTo(bodyRightControl.x, bodyRightControl.y, bodyRightTopPoint.x, bodyRightTopPoint.y)
        mPaint.setAlpha(160)
        canvas.drawPath(mPath, mPaint)
    }

    private fun creatTriangle(upCenterPoint: PointF, canvas: Canvas,findCenterLen:Float,len:Float) {

        var centerPoint1 = calculatePoint(upCenterPoint, len, fishAngle - 180)
        var leftPoint1 = calculatePoint(centerPoint1, findCenterLen, fishAngle + 90)
        var leftPoint2 = calculatePoint(centerPoint1, findCenterLen, fishAngle - 90)
        mPath.reset()
        mPath.moveTo(upCenterPoint.x, upCenterPoint.y)
        mPath.lineTo(leftPoint1.x, leftPoint1.y)
        mPath.lineTo(leftPoint2.x, leftPoint2.y)
        canvas.drawPath(mPath, mPaint)
    }

    private fun test(canvas: Canvas, point: PointF) {
        mPaint.setColor(Color.RED)
        mPaint.strokeWidth = 10f
        canvas.drawPoint(point.x, point.y, mPaint)
        mPaint.setARGB(110, 244, 92, 71)
    }

    private fun creatSegment(bottomCenterPoint: PointF, canvas: Canvas, findUpCenterLen: Float, bigR: Float, smallR: Float, drawBigCircle: Boolean): PointF {


        var bottomLeftPoint = calculatePoint(bottomCenterPoint, bigR, fishAngle + 90)
        var bottomRightPoint = calculatePoint(bottomCenterPoint, bigR, fishAngle - 90)

        var upCenterPoint = calculatePoint(bottomCenterPoint, findUpCenterLen, fishAngle - 180)
        var upLeftPoint = calculatePoint(upCenterPoint, smallR, fishAngle + 90)
        var upRightPoint = calculatePoint(upCenterPoint, smallR, fishAngle - 90)

        if (drawBigCircle) {
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigR, mPaint)
        }

        canvas.drawCircle(upCenterPoint.x, upCenterPoint.y, smallR, mPaint)
        mPath.reset()
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(upRightPoint.x, upRightPoint.y)
        mPath.lineTo(upLeftPoint.x, upLeftPoint.y)
        canvas.drawPath(mPath, mPaint)
        return upCenterPoint
    }

    private fun creatSwing(headPoint: PointF, canvas: Canvas, isRightSwing: Boolean) {
        var swingStartPoint = calculatePoint(headPoint, FIND_RIGHT_SWING_LEN, if (isRightSwing) fishAngle - 110 else fishAngle + 110)
        var swingEndPoint = calculatePoint(swingStartPoint, SWING_LEN, if (isRightSwing) fishAngle - 180 else fishAngle + 180)
        var swingControlPoint = calculatePoint(swingStartPoint, SWING_LEN * 1.8f, if (isRightSwing) fishAngle - 115 else fishAngle + 115)

        mPath.reset()
        mPath.moveTo(swingStartPoint.x, swingStartPoint.y)
        mPath.quadTo(swingControlPoint.x, swingControlPoint.y, swingEndPoint.x, swingEndPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT;
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth(): Int {
        return WITH;
    }

    override fun getIntrinsicHeight(): Int {
        return HEIGHT;
    }
}