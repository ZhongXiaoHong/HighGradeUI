package recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

//TODO 手写RecyclerView
public class HandWriteRecyclerView extends ViewGroup {

    private Adapter adapter;
    //TODO 内容的偏移量
    private int scrollY;
    //当前显示的View
    private List<View> viewList;
    //行数
    private int rowCount;

    private boolean needReLayout;
    private int width;
    private int height;
    private int[] heights;

    //View的第一行对应第几个Item
    private int  firstRow;

    public HandWriteRecyclerView(Context context) {
        super(context);
        init();
    }

    public HandWriteRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HandWriteRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.viewList = new ArrayList<>();
        this.needReLayout = true;
    }

    private int sumArray(int arry[], int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += arry[i];
        }
        return sum;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //     super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (adapter != null) {
            this.rowCount = adapter.getCount();
            this.heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
            }
        }
        int tmpH = sumArray(heights, 0, heights.length);
        int h = Math.min(tmpH, MeasureSpec.getSize(heightMeasureSpec));
        //TODO  *****************************************************************************************************
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), h);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (needReLayout || changed) {
            needReLayout = false;
            viewList.clear();
            removeAllViews();
            if (adapter != null) {
                width = r - l;
                height = b - t;
            }


            int top =  -scrollY;
            int bottom;
            //rowCount内容数量
            //height 当前控件高度

            for (int i = firstRow; i < rowCount && top < height; i++) {
                bottom= top+heights[i];
            }


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }
}
