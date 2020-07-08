package dynamic_change_skin;

import android.app.Application;

import com.silang.my_skin_core.SkinActivityLifeCycle;
import com.silang.my_skin_core.SkinManager;

public class DnApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);

    }
}
