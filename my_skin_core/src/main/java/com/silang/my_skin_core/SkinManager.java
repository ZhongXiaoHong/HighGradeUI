package com.silang.my_skin_core;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Observable;

public class SkinManager extends Observable {

    private static SkinManager Instance;

    private Application app;

    private Resources appResource;


    private SkinManager(Application app) {

        this.app = app;
        this.appResource = app.getResources();
        SkinResources.init(app);
        SkinPreference.init(app);
        app.registerActivityLifecycleCallbacks(new SkinActivityLifeCycle());
        loadSkin(SkinPreference.getInstance().getSkin());

    }


    public static void init(Application app) {

        if (Instance == null) {
            synchronized (SkinManager.class) {
                if (Instance == null) {
                    Instance = new SkinManager(app);
                }
            }
        }
    }

    public static SkinManager getInstance() {

        return Instance;
    }


    public void loadSkin(String path) {
        //TODO 还原默认的皮肤
        if (TextUtils.isEmpty(path)) {
            SkinPreference.getInstance().setSkin("");

        } else {
            try {

                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);//   addAssetPath
                addAssetPathMethod.setAccessible(true);
                addAssetPathMethod.invoke(assetManager, path);
                Resources skinresource = new Resources(assetManager, appResource.getDisplayMetrics(), appResource.getConfiguration());
                //TODO 获取皮肤包包名
                PackageManager pmr = app.getPackageManager();
                PackageInfo info = pmr.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                String packageName = info.packageName;
                SkinResources.getInstance().applySkin(skinresource,packageName);
                SkinPreference.getInstance().setSkin(path);
                //TODO 设置状态改变，通知观察者
                setChanged();
                notifyObservers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
