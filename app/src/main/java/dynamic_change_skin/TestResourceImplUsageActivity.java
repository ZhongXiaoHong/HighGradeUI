package dynamic_change_skin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.silang.highgradeui.R;
import com.silang.highgradeui.TestViewPagerActivity;
import com.silang.my_skin_core.SkinManager;


public class TestResourceImplUsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zhong","*******************setContentView前**************");
        setContentView(R.layout.activity_test_resource_impl_usage);
        Log.d("zhong","*******************setContentView后**************");


    }


    public void doImg(View view) {

        int resID = R.color.colorAccent;//R.drawable.ic_launcher_background;
        Resources resources = view.getContext().getResources();
        String resourceName = resources.getResourceName(resID);
        String entryName = resources.getResourceEntryName(resID);
        String typeName = resources.getResourceTypeName(resID);
        int id = resources.getIdentifier(resourceName, typeName, resources.getResourcePackageName(resID));
        int id2 = resources.getIdentifier(entryName, typeName, resources.getResourcePackageName(resID));

        Log.d("", "");
    }

    public void dotext(View view) {


     //startActivity(new Intent(this, TestViewPagerActivity.class));

       // MySkinManager.getInstance().loadSkin("assets/my_skin_pk.apk");
        SkinManager.getInstance().loadSkin(Environment.getExternalStorageDirectory()+"/Pictures/my_skin_pk.zip");


    }
}