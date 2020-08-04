package swipeRefresh_viewpager_conflict;

import android.content.Context;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.lang.reflect.Method;

public class CustomSRL2 extends SwipeRefreshLayout {
    public CustomSRL2(Context context) {
        super(context);
    }

    public CustomSRL2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float lastX = 0;
    float lastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
       // return innerIntercept(ev);
        // return outerIntercept(ev);

    }

    //TODO 外部拦截
    private boolean outerIntercept(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            lastX = ev.getX();
            lastY = ev.getY();
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {

            //TODO 横向距离大于纵向距离
            if (Math.abs(ev.getX() - lastX) > Math.abs(ev.getY() - lastY)) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }


    //TODO  内部拦截
    private boolean innerIntercept(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }

    Method method;
//
//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean b) {
//        // super.requestDisallowInterceptTouchEvent(b);
//        //TODO  为了向下兼容
//
//
//        try {
//            if (method == null) {
//                Class<ViewGroup> clazz = ViewGroup.class;
//                method = clazz.getDeclaredMethod("requestDisallowInterceptTouchEvent", boolean.class);
//            }
//            method.setAccessible(true);
//            Object obj = (ViewGroup)this;
//            method.invoke(obj, b);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
