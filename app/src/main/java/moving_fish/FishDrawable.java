package moving_fish;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FishDrawable extends Drawable {

    //鱼头部半径
    private static final float HEAD_RADIUS = 110.0f;
    private static final float FISH_BODY_LEN = HEAD_RADIUS*3.2f;
    //躯干中点坐标
    private PointF middlePoint;
    private Paint paint;
    private  float fishAngle;

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
    }

    //TODO 绘制的时候会调用这个方法
    @Override
    public void draw(@NonNull Canvas canvas) {
        drawHead(canvas);


        canvas.drawRect(0,0,getIntrinsicWidth(),getIntrinsicHeight(),paint);
    }

    //画鱼头
    private void drawHead(@NonNull Canvas canvas) {

        PointF headPoint = calculatePoint(middlePoint, FISH_BODY_LEN / 2, fishAngle);
        canvas.drawCircle(headPoint.x,headPoint.y,HEAD_RADIUS,paint);
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
        float deltaY = (float) (Math.sin(Math.toRadians(angle) - 180) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);

    }
}
