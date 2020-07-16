package moving_fish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.silang.highgradeui.R;

public class MovingFishActivity extends AppCompatActivity {

    ImageView  imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_fish);
        imageView = findViewById(R.id.iv);
        imageView.setImageDrawable(new FishDrawable());
    }
}