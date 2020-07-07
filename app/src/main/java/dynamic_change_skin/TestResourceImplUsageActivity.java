package dynamic_change_skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.silang.highgradeui.R;
import com.silang.mydn_skin_core.DnSkinManager;

import java.io.File;
import java.io.IOException;

public class TestResourceImplUsageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_resource_impl_usage);

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


//        try {
//            String[] files = getAssets().list("/assets/");
//            for (String file : files) {
//                if (file.contains("dn_skin_pk")) {
//
//                    break;
//
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        DnSkinManager.getInstance().loadSkin(Environment.getExternalStorageDirectory()+"/dn_skin_pk.apk");




    }
}