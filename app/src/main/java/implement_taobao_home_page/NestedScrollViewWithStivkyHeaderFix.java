package implement_taobao_home_page;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/***
 * 实现TabLayout吸顶
 * 通过设置TabLayout+ViewPager == 屏幕高度 == NestedScrollViewWithStivkyHeader高度
 */
public class NestedScrollViewWithStivkyHeaderFix extends NestedScrollView {

    private View contentView;  //TabLayout+ViewPager的父布局
    private View headerView;

    public NestedScrollViewWithStivkyHeaderFix(@NonNull Context context) {
        super(context);
    }

    public NestedScrollViewWithStivkyHeaderFix(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollViewWithStivkyHeaderFix(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO 当完成inflate时候回调
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = ((ViewGroup) getChildAt(0)).getChildAt(1);
        headerView = ((ViewGroup) getChildAt(0)).getChildAt(0);

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
//        lp.height = getMeasuredHeight();
//        contentView.setLayoutParams(lp);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        if (lp.height != getMeasuredHeight()) {
            lp.height = getMeasuredHeight();
            contentView.setLayoutParams(lp);
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // super.onNestedPreScroll(target, dx, dy, consumed, type);

        // TODO 向上滑动。若当前纵向滑动距离小于headerView的高度，则表示headerView还可见，需要将headerView滑动至不可见
        boolean hideTop = dy > 0 && getScrollY() < headerView.getMeasuredHeight();
        if (hideTop) {
            scrollBy(0, dy);
            //TODO 记录ScrollView此次纵向滑了多少，这是一个数组，是一个引用，可以将结果带回去
            //TODO 这样嵌套滑动孩子可以计算自己还可以接着滑
            consumed[1] = dy;
        }

    }
}
