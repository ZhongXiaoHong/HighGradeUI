package toutiao_indicator_text;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.silang.highgradeui.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "Zero";
    private String[] mTitles = new String[]{"关注", "热点", "推荐", "长沙", "广州", "深圳", "东莞", "佛山", "惠州", "珠海", "中山", "江门"};
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private TagFragment[] mFragments = new TagFragment[mTitles.length];
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDatas() {

        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = TagFragment.newInstance(mTitles[i]);
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(mViewPager,false);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        for(int i=0;i<mTitles.length;i++){
            tabLayout.getTabAt(i).setText(mTitles[i]);
        }
        mViewPager.setCurrentItem(0);
    }

    private void initViews() {
        mViewPager = findViewById(R.id.id_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

    }


}
