package moving_fish;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FishDrawable extends Drawable {

    //鱼头部半径
    private static final float HEAD_RADIUS = 100.0f;
    private static final float BODY_LENGTH = HEAD_RADIUS * 3.2f;
    private static final float FIND_FINS_LENGTH = 0.9f * HEAD_RADIUS;
    private static final float FINS_LENGTH = 1.3f * HEAD_RADIUS;

    // 大圆的半径
    private float BIG_CIRCLE_RADIUS = 0.7f * HEAD_RADIUS;
    // 中圆的半径
    private float MIDDLE_CIRCLE_RADIUS = 0.6f * BIG_CIRCLE_RADIUS;
    // 小圆半径
    private float SMALL_CIRCLE_RADIUS = 0.4f * MIDDLE_CIRCLE_RADIUS;

    // --寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // --寻找尾部小圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --寻找大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.7f;

    private int BODY_ALPHA = 160;



    //躯干中点坐标
    private PointF middlePoint;
    private Paint paint;
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public float getFishMainAngle() {
        return fishMainAngle;
    }

    public void setFishMainAngle(float fishMainAngle) {
        this.fishMainAngle = fishMainAngle;
        imageView.setBackground(this);
        imageView.invalidate();

    }

    float fishMainAngle=0;
    private Path mPath;


    public FishDrawable() {
        initPaint();
        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//防抖，设置后可以使页面更加光滑
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(110, 244, 92, 71);
        mPath = new Path();
    }

    //TODO 绘制的时候会调用这个方法
    @Override
    public void draw(@NonNull Canvas canvas) {
        paint.setARGB(110, 244, 92, 71);
        float fishAngle = fishMainAngle;
        //画鱼头
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, paint);
        // 画右鱼鳍
        PointF rightSwingStartPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110);
        makeSwing(canvas, rightSwingStartPoint, fishAngle, true);
        //话左鱼鳍
        PointF leftSwingStartPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110);
        makeSwing(canvas, leftSwingStartPoint, fishAngle, false);


        PointF bodyBottomCenterPoint = calculatePoint(middlePoint, 1.6f * HEAD_RADIUS, fishAngle - 180);
        //画节肢1
        PointF upCenterPoint = makeSegment(canvas, bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, MIDDLE_CIRCLE_RADIUS, FIND_MIDDLE_CIRCLE_LENGTH, fishAngle, true);

        makeSegment(canvas, upCenterPoint, MIDDLE_CIRCLE_RADIUS, SMALL_CIRCLE_RADIUS, FIND_SMALL_CIRCLE_LENGTH, fishAngle, false);

        makeTriabgle(canvas, upCenterPoint,BIG_CIRCLE_RADIUS ,FIND_TRIANGLE_LENGTH,fishAngle);
        makeTriabgle(canvas, upCenterPoint,BIG_CIRCLE_RADIUS-20 ,FIND_TRIANGLE_LENGTH-10,fishAngle);

        makeBody(canvas,headPoint,bodyBottomCenterPoint,fishAngle);

        //   canvas.drawRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), paint);
    }

    private void makeTriabgle(Canvas canvas, PointF upCenterPoint,float radius,float len, float fishAngle) {
        //三角形底边中心坐标
        PointF centerPoint = calculatePoint(upCenterPoint, len, fishAngle - 180);
        PointF leftPoint = calculatePoint(centerPoint, radius, fishAngle + 90);
        PointF rightPoint = calculatePoint(centerPoint, radius, fishAngle - 90);
        mPath.reset();
        mPath.moveTo(upCenterPoint.x,upCenterPoint.y);
        mPath.lineTo(leftPoint.x,leftPoint.y);
        mPath.lineTo(rightPoint.x,rightPoint.y);

        canvas.drawPath(mPath,paint);

    }

    private PointF makeSegment(Canvas canvas, PointF bodyBottomCenterPoint, float bigR, float smallR, float findSmallCircleLen, float fishAngle, boolean drawBigCircle) {

        //上梯形的中点坐标
        PointF upCenterPoint = calculatePoint(bodyBottomCenterPoint, findSmallCircleLen, fishAngle - 180);
        PointF bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, bigR, fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(bodyBottomCenterPoint, bigR, fishAngle - 90);
        PointF upperLeftPoint = calculatePoint(upCenterPoint, smallR, fishAngle + 90);
        PointF upperRightPoint = calculatePoint(upCenterPoint, smallR, fishAngle - 90);

        //画大圆
        if (drawBigCircle) {
            canvas.drawCircle(bodyBottomCenterPoint.x, bodyBottomCenterPoint.y, bigR, paint);
        }

        canvas.drawCircle(upCenterPoint.x, upCenterPoint.y, smallR, paint);
        mPath.reset();
        mPath.moveTo(bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.lineTo(upperRightPoint.x, upperRightPoint.y);
        mPath.lineTo(upperLeftPoint.x, upperLeftPoint.y);
        canvas.drawPath(mPath, paint);

        return upCenterPoint;

    }

    private void makeSwing(Canvas canvas, PointF rigthSwingStartPoint, float fishAngle, boolean isRight) {
        float controAngle = 115;
        PointF swingEndPoint = calculatePoint(rigthSwingStartPoint, FINS_LENGTH, isRight ? fishAngle - 180 : fishAngle + 180);
        PointF controlPoint = calculatePoint(rigthSwingStartPoint, FINS_LENGTH * 1.8f, isRight ? fishAngle - controAngle : fishAngle + controAngle);
        //TODO 二姐被萨尔曲线
        mPath.reset();
        mPath.moveTo(rigthSwingStartPoint.x, rigthSwingStartPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, swingEndPoint.x, swingEndPoint.y);
        canvas.drawPath(mPath, paint);

    }


    private void makeBody(Canvas canvas, PointF headPoint,PointF bodyBottomCenterPoint,float fishAngle) {

        // 身体的四个点求出来
        PointF topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90);
        PointF topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90);
        PointF bottomLeftPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle + 90);
        PointF bottomRightPoint = calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS,
                fishAngle - 90);

        // 二阶贝塞尔曲线的控制点 --- 决定鱼的胖瘦
        PointF controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle + 130);
        PointF controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle - 130);

        mPath.reset();
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y);
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y);
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y);
        paint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath, paint);

    }


    //TODO 设置Drawable的透明度
    //TODO 一般是用这个值来设置画笔
    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    //TODO 设置颜色过滤器，可以改变绘制内容
    //TODO  设置在画笔上
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    //TODO 根据setAlpha设置的值来决定返回值
    //TODO  alpha==0  返回  PixelFormat.TRANSPARENT
    //TODO alpha == 255     PixelFormat.OPAQUE
    //TODO alpha == 其他   PixelFormat.TRANSLUCENT
    @Override
    public int getOpacity() {

        return PixelFormat.TRANSLUCENT;
    }


    //TODO 设置Drawable的宽
    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    //TODO 设置Drawable的高
    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    /***
     *
     * @param startPoint
     * @param length
     * @param angle  鱼当前的朝向
     * @return
     */
    public PointF calculatePoint(PointF startPoint, float length, float angle) {

        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);

    }
}
