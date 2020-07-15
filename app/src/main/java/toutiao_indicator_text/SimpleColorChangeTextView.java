package toutiao_indicator_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SimpleColorChangeTextView extends AppCompatTextView {
    Paint paint;
    private String text = "钟离四郎";
    private float percent;

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        System.out.println("************************"+percent);
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
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);

        paint.setColor(Color.BLUE);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);

        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        Paint.FontMetrics fm = paint.getFontMetrics();
        //TODO (fm.descent - fm.ascent) / 2 实际上文字的中间线到底部的距离
        float detaY = ((fm.descent - fm.ascent) / 2 - fm.descent);
        float textWith = paint.measureText(text);
        int start = getWidth() / 2 ;//+ (int) (textWith * percent);
        canvas.drawText(text, start, getHeight() / 2 + detaY, paint);

        clipCanvas(canvas,detaY);

    }

    private void clipCanvas(Canvas canvas,float detaY) {

        canvas.save();
        paint.setColor(Color.RED);
        float textWith = paint.measureText(text);
        int left = getWidth()/ 2 -(int)textWith/2;
        int top = 0;
        int right = left + (int) (textWith * percent);
        int bottom = getHeight();
        //Rect rect = new Rect();

      //  canvas.drawRect(left,top,right,bottom,paint);
        canvas.clipRect(left,top,right,bottom);

        canvas.drawText(text, getWidth() / 2, getHeight() / 2 + detaY, paint);
        canvas.restore();



    }
}
