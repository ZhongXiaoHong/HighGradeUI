package swipeRefresh_viewpager_conflict;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomVPInner extends ViewPager {

    private float startX;
    private float startY;
    private float x;
    private float y;
    private float deltaX;
    private float deltaY;

    public CustomVPInner(Context context) {
        super(context);
    }

    public CustomVPInner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //TODO 使用内部拦截法
      //  innerIntercept(ev);
        return super.dispatchTouchEvent(ev);
    }

    //TODO 内部拦截
    private void innerIntercept(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                x = ev.getX();
                y = ev.getY();
                deltaX = Math.abs(x - startX);
                deltaY = Math.abs(y - startY);
                if (deltaX < deltaY) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
    }

    //TODO 内部拦截方法，强制设置Vp是嵌套滑动
    //TODO 这样requestDisallowInterceptTouchEvent才会生效
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return true;//super.isNestedScrollingEnabled();
//    }
}
