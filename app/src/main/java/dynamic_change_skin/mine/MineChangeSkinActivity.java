package dynamic_change_skin.mine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.silang.highgradeui.R;

import java.lang.reflect.Field;

public class MineChangeSkinActivity extends AppCompatActivity {

    LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_change_skin);


        try {
            inflater = LayoutInflater.from(this);//TODO
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater, false);
            LayoutInflaterCompat.setFactory2(inflater, new MineFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void changeSkin(View view) {

    }
}