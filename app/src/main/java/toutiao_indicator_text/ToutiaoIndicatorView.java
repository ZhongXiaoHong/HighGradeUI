package toutiao_indicator_text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class ToutiaoIndicatorView extends AppCompatTextView {

    public ToutiaoIndicatorView(Context context) {
        super(context);
        intit();
    }

    public ToutiaoIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intit();
    }

    public ToutiaoIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intit();
    }

    private Paint paint;

    private void intit() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int left = 0;
        int top = 0;
        int right = getWidth() / 2;
        int bottom = getHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        setTextColor(Color.BLACK);
        super.onDraw(canvas);
        canvas.restore();

        canvas.save();
        canvas.clipRect(right, top, getWidth(), bottom);
        setTextColor(Color.RED);
        super.onDraw(canvas);
        canvas.restore();


    }
}
