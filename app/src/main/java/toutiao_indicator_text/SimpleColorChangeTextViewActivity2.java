package toutiao_indicator_text;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;

import com.silang.highgradeui.R;

//TODO  SimpleColorChangeTextViewActivity2
//TODO 优化SimpleColorChangeTextView 过渡绘制的问题
public class SimpleColorChangeTextViewActivity2 extends AppCompatActivity {

    SimpleColorChangeTextView2 st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_color_change_text_view2);
        st = findViewById(R.id.st);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(st, "persent", 0, 1);
                animator.setDuration(2000);

                animator.start();
            }

        }, 2000);
    }
}