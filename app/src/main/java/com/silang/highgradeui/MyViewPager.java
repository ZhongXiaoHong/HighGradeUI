package com.silang.highgradeui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //TODO 不加 super.onMeasure 下方的getChildCount会是 0
        //TODO 为啥呢
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxWith = 0, maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            int withSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), lp.width);
            int heightSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), lp.height);
            child.measure(withSpec, heightSpec);
            maxHeight = Math.max(child.getMeasuredHeight(), maxHeight);
            maxWith = Math.max(child.getMeasuredWidth(), maxWith);
        }

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int with = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(withMode == MeasureSpec.EXACTLY ? with : maxWith, heightMode == MeasureSpec.EXACTLY ? height : maxHeight);

    }
}
