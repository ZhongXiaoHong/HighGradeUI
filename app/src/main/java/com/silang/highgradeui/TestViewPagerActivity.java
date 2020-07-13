package com.silang.highgradeui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestViewPagerActivity extends AppCompatActivity {

   // ViewPager vp;
    MyViewPager vp;
    List<View> mViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);
        vp = findViewById(R.id.vp);
        TextView tv1 = new TextView(this);
        tv1.setText("1111111111");
        mViews.add(tv1);
        TextView tv2 = new TextView(this);
        tv1.setText("222222222");
        mViews.add(tv2);

        TextView tv3 = new TextView(this);
        tv1.setText("3333333");
        mViews.add(tv3);

        vp.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }


            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //super.destroyItem(container, position, object);
                //删除页卡
                container.removeView(mViews.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //实例化页卡
                container.addView(mViews.get(position));
                return mViews.get(position);
            }


        });
    }
}