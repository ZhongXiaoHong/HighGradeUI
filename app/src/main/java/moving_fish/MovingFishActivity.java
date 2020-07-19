package moving_fish;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.silang.highgradeui.R;

public class MovingFishActivity extends AppCompatActivity {

    ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_fish);
        final FishDrawable drawable = new FishDrawable();

        imageView = findViewById(R.id.iv);
        drawable.setImageView(imageView);
      //  imageView.setImageDrawable(drawable);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             //   ofInt(Object target, String propertyName, int... values)
                ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat(drawable,"fishMainAngle",0,360);
                objectAnimator.setDuration(3000);
                objectAnimator.start();


            }
        },2000);

    }
}