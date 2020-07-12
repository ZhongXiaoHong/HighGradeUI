package implement_taobao_home_page;

import android.content.Context;
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
public class NestedScrollViewWithStivkyHeader  extends NestedScrollView {

    private View contentView;  //TabLayout+ViewPager的父布局

    public NestedScrollViewWithStivkyHeader(@NonNull Context context) {
        super(context);
    }

    public NestedScrollViewWithStivkyHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollViewWithStivkyHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO 当完成inflate时候回调
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       contentView =  ((ViewGroup)getChildAt(0)).getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        lp.height = getMeasuredHeight();
        contentView.setLayoutParams(lp);
    }
}
