package com.silang.highgradeui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class JavaFlowLayout extends ViewGroup {

    //TODO 所有行对应的实体
   private  List<RowBean> rowBeans = new ArrayList<>();

    private static final int VERTICAL_SPACE = dp2px(10);

    //TODO  java代码中使用
    public JavaFlowLayout(Context context) {
        super(context);
    }

    //TODO XML LayoutInflate 反射
    public JavaFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //TODO 有主题style相关的属性
    public JavaFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO 第四个参数是第三个参数的备胎，如果第三个设置为0，或者属性找不到的时候会去第四个中找
    //TODO 第四个如果设置为0，则不起作用
//    public JavaFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    //TODO  度量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //TODO onMeasure可能被调用多次
        //TODO  每一次onMeasure要清除保存孩子们
        //TODO  避免重复添加
        rowBeans.clear();


        //TODO  度量孩子：1.1生成孩子的Spec   1.2调用孩子的measure会导致onMeasure回调从而计算孩子的测量宽高【1】
        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);

            //TODO 【1.1 生成孩子的Spec】
            //TODO  计算孩子的模式+大小，也就是计算孩子的测量规格
            //TODO 第1个参数  爷爷给父亲的测量规格（爸爸可能能够获得的遗产）
            //TODO 第2个参数   父亲的padding(养老钱)
            //TODO 第3参数   孩子的Layoutparams的with(match_parent  wrap_content   dp/px)
            int childWithSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), child.getLayoutParams().width);
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height);

            //TODO 【1.2调用孩子的measure会导致onMeasure回调从而计算孩子的测量宽高】
            child.measure(widthMeasureSpec, heightMeasureSpec);

            //TODO  保存孩子们
            saveChildView(child, MeasureSpec.getSize(widthMeasureSpec));

        }

        //TODO  度量自身:根据具体业务需求计算测量宽高 【2】
        int[] wrapContentWH = getwrapContentWH();
        int measureW = MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY ?  MeasureSpec.getSize(widthMeasureSpec):wrapContentWH[0];
        int measureH = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY ? MeasureSpec.getSize(heightMeasureSpec):wrapContentWH[1];
        setMeasuredDimension(measureW, measureH);
    }

    private RowBean getLastRowBean() {

        if (rowBeans.isEmpty()) {
            RowBean r = new RowBean();
            rowBeans.add(r);
            return r;
        }

        RowBean lastRowBean = rowBeans.get(rowBeans.size() - 1);
        return lastRowBean;
    }

    private boolean needChangeLine(View child, int specWith) {

        RowBean curRowBean = getLastRowBean();

        //TODO 判断最后一行还能不能继续添加View
        List<View> rowChildren = curRowBean.views;

        if (rowChildren.isEmpty()) {
            return false;
        }


        if (curRowBean.with + child.getMeasuredWidth() > specWith) {
            return true;
        }

        return false;

    }

    private void saveChildView(View child, int specWith) {

        boolean changeLine = needChangeLine(child, specWith);

        RowBean curRowBean;
        curRowBean = changeLine ? new RowBean() : getLastRowBean();
        if (changeLine) {
            rowBeans.add(curRowBean);
        }

        //TODO 行高取最高的孩子的高度
        curRowBean.height = child.getMeasuredHeight() > curRowBean.height ? child.getMeasuredHeight() : curRowBean.height;
        //TODO 行宽取孩子们宽的叠加
        curRowBean.with += child.getMeasuredWidth();

        curRowBean.views.add(child);


    }

    private int[] getwrapContentWH() {

        int totalW = 0;
        int totalH = 0;
        for (RowBean rowBean : rowBeans) {
            totalW = rowBean.with > totalW ? rowBean.with : totalW;
            totalH += rowBean.height+VERTICAL_SPACE;
        }
        totalH -=VERTICAL_SPACE;
        totalH += getPaddingBottom()+getPaddingTop();
        totalW +=getPaddingLeft()+getPaddingRight();

        return new int[]{totalW, totalH};

    }

    //TODO 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = getPaddingTop();


        for (int i = 0; i < rowBeans.size(); i++) {

            RowBean rowBean = rowBeans.get(i);
            List<View> rowViews = rowBean.views;
            left = getPaddingLeft();
            right = left;

            top = bottom ;
            if (i > 0 ) {
                top += VERTICAL_SPACE;
            }
            bottom = top + rowBean.height;


            for (View rowView : rowViews) {
                right += rowView.getMeasuredWidth();
                rowView.layout(left, top, right, bottom);
                left = right + 20;
            }
        }


    }

    class RowBean {
        List<View> views = new ArrayList<>();
        int with;
        int height;
    }

    //TODO 不用Context实现单位转换 dp ------->px
    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
