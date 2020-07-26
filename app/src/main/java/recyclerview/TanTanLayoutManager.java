package recyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

//TODO 模仿探探
public class TanTanLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//TODO  参考LinearLayoutManager的处理
    }

    //TODO 这个方法必须重写，沙雕谷歌也是这么规定的，但是没有设计成abstract
    //TODO 被回调的时机：1.onLayout初始化的时候    2.数据集更新或者Adapter被替换
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //

        //TODO 【1】 ViewHolde 回收服用
        detachAndScrapAttachedViews(recycler);
        ////TODO 【2】 开始布局
        int bottomPosition = 0;//底部最后一张处于数据集的哪个位置
        //TODO 1000个呢   岂不是不好？？
        int itemCount = getItemCount();//getChildCount(); 不能使用getChildCoount 这个时候还没有addView 呢是0
        if (itemCount > Const.MAX_COUNT) {
            bottomPosition = itemCount - Const.MAX_COUNT; //5-4 = 1
        }


        for (int i = bottomPosition; i < itemCount; i++) {
            //TODO 【2.1】 获取缓存的View,如果没有命中缓存，最后会调用creatViewHolder
            View view = recycler.getViewForPosition(i);
            //TODO 【2.2】 添加View
            addView(view);
            measureChildWithMargins(view, 0, 0);
            //getDecoratedMeasuredWidth 获取View的宽+左右两边Decor的宽度
            int childW = getDecoratedMeasuredWidth(view);
            int childH = getDecoratedMeasuredHeight(view);
            int widthSpace = getWidth() - childW;
            int heightSpace = getHeight() - childH;

            //TODO 【2.3】 计算布局参数
            //View child, int left, int top, int right,int bottom
            int left = widthSpace / 2;
            int top = heightSpace / 2;
            int right = widthSpace / 2 + childW;
            int bottom = heightSpace / 2 + childH;

            System.out.println("left = " + left + "top = " + top + "right = " + right + "bottom = " + bottom);
            //TODO 【2.4】 布局
            layoutDecoratedWithMargins(view, left, top , right, bottom );

            int level = itemCount - i - 1;
            if(level>0){
                if(level<Const.MAX_COUNT-1){
                    view.setTranslationY(Const.TRANSY_GAP*level);
                    view.setScaleX(1-Const.SCALE_GAP*level);
                    view.setScaleY(1-Const.SCALE_GAP*level);
                }else{
                    view.setTranslationY(Const.TRANSY_GAP*(level-1));
                    view.setScaleX(1-Const.SCALE_GAP*(level-1));
                    view.setScaleY(1-Const.SCALE_GAP*(level-1));
                }

            }

            //TODO【3】 手势滑动相关逻辑处理

        }

    }





}
