package toutiao_indicator_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SimpleColorChangeTextView extends AppCompatTextView {
    Paint paint;
    private String text = "钟离四郎";
    private float persent;

    public float getPersent() {
        return persent;
    }

    public void setPersent(float persent) {
        this.persent = persent;
        invalidate();
    }

    public SimpleColorChangeTextView(Context context) {
        super(context);
        init();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xff000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //TODO 绘制横轴纵轴  以屏幕中心为原点
        paint.setColor(Color.RED);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);

        //TODO 设置字体颜色 大小
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(60);

        Paint.FontMetrics fm = paint.getFontMetrics();
        float normalTextHeight = fm.ascent - fm.descent;//TODO 正常字体的高度，不包含leading(填充音符的区域)
        float normalTextCenter = normalTextHeight / 2;//TODO 正常的中心线
        //TODO 但是绘制字体的时候不是以“正常的中心线”作为参照线开是绘制的
        //TODO 而是按照基线开始绘制的，所以基线到“正常的中心线”的偏移：
        float detaY = (normalTextCenter - fm.descent) / 2;

        //TODO getHeight() / 2直接按照这个作为基准线会向上偏移
        //TODO 所以这里直接减掉偏移量
        //TODO 偏移量与选取哪里做基准线无关，即下方y随便取
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - detaY, paint);

        canvas.save();
        paint.setColor(Color.GREEN);
        float textWith = paint.measureText(text);
        float left = getWidth() / 2 - textWith / 2;
        float top = 0;
        float right = left + textWith*persent;
        float bottom = getHeight();
        canvas.clipRect(left,top,right,bottom);
        // canvas.drawRect(left, top, right, bottom, paint);
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 - detaY, paint);

        canvas.restore();

    }
}
