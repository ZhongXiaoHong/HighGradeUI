package toutiao_indicator_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SimpleColorChangeTextView  extends AppCompatTextView {
    Paint paint;
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

    private void  init(){
        paint = new Paint();
        paint.setColor(0xff000000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("钟离四郎",getWidth()/2,getHeight()/2,paint);

    }
}
