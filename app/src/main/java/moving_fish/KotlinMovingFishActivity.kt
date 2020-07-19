package moving_fish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silang.highgradeui.R
import kotlinx.android.synthetic.main.activity_kotlin_moving_fish.*

class KotlinMovingFishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_moving_fish)
        imageView.background = kotlinFishDrawable();
    }
}