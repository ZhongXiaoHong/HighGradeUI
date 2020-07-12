package implement_taobao_home_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.silang.highgradeui.R;

import java.util.ArrayList;
import java.util.List;

public class NestedScrollViewRecyclerViewActivity extends AppCompatActivity {


    ViewPager2 vp;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_view_recycler_view);
        vp = findViewById(R.id.vp);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
        vp.setAdapter(pagerAdapter);

        final String[] labels = new String[]{"tab1", "tab2", "tab3"};
        new TabLayoutMediator(tabLayout, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();

    }

    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }
}