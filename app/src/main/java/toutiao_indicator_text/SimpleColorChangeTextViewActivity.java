package toutiao_indicator_text;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;

import com.silang.highgradeui.R;

public class SimpleColorChangeTextViewActivity extends AppCompatActivity {


    SimpleColorChangeTextView st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_color_change_text_view);
        st = findViewById(R.id.st);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(st, "percent", 0f, 1f);
                animator.setDuration(2000);//时间1s
                animator.start();
            }
        }, 1000);
    }
}