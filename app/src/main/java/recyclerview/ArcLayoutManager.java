package recyclerview;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ArcLayoutManager extends RecyclerView.LayoutManager {


    public ArcLayoutManager(Context context) {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//TODO  参考LinearLayoutManager的处理
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //TODO 【1】 ViewHolde 回收服用
        detachAndScrapAttachedViews(recycler);

        ////TODO 【2】 开始布局

        int height = getHeight();
        int width = getWidth();

        PointF ccPoint = new PointF(getWidth() / 2, getHeight());
        float ccRadius = getWidth() / 2;


        for (int i = 0; i < 5; i++) {
            //TODO 【2.1】 获取缓存的View,如果没有命中缓存，最后会调用creatViewHolder
            View view = recycler.getViewForPosition(i);
            //TODO 【2.2】 添加View
            addView(view);
            measureChildWithMargins(view, 0, 0);
            PointF p = calculatePoint(ccPoint, ccRadius, (i + 1) * 30);
            int childW = getDecoratedMeasuredWidth(view);
            int childH = getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, (int) (p.x + 0.5f), (int) (p.y + 0.5f), (int) (p.x + childW + 0.5f), (int) (p.y + childH + 0.5f));
        }


    }


    public PointF calculatePoint(PointF startPoint, float length, float angle) {

        float deltaX = (float) (Math.cos(Math.toRadians(angle)) * length);
        float deltaY = (float) (Math.sin(Math.toRadians(angle - 180)) * length);
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);

    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }
}
