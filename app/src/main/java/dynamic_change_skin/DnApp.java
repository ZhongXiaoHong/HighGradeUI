package dynamic_change_skin;

import android.app.Application;

import com.silang.mydn_skin_core.DnSkinManager;

public class DnApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DnSkinManager.init(this);
    }
}
