package implement_taobao_home_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.silang.highgradeui.R;


public class TestTaobaoHomePageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_taobao_home_page);


    }

    public void test1(View view) {
        startActivity(new Intent(this, ScrollViewRecyclerViewActivity.class));
    }


    public void test2(View view) {
        startActivity(new Intent(this, NestedScrollViewRecyclerViewActivity.class));
    }

    public void test3(View view) {
        startActivity(new Intent(this, NestedScrollViewRecyclerViewWithStickyHeaderActivity.class));
    }

    public void test4(View view) {
        startActivity(new Intent(this, NestedScrollViewRecyclerViewWithStickyHeaderActivityFix.class));
    }
}