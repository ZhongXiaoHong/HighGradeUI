package toutiao_indicator_text;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.silang.highgradeui.R;

public class SimpleColorChangeTextViewActivity extends AppCompatActivity {

    SimpleColorChangeTextView st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_color_change_text_view);

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleColorChangeTextViewActivity.this, SimpleColorChangeTextViewActivity2.class));
            }
        });
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