package com.silang.mydn_skin_core;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class DnSkinManager extends Observable {

    private static DnSkinManager Instance;

    private Application app;

    private DnSkinManager(Application app) {


    }

    public static void init(Application app) {

        if (Instance == null) {
            synchronized (DnSkinManager.class) {
                if (Instance == null) {
                    Instance = new DnSkinManager(app);
                }
            }
        }
    }

    public static DnSkinManager getInstance() {

        return Instance;
    }


    public void loadSkin(String path) {

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath");
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, path);
            Resources skinresource =  new Resources(assetManager, app.getResources().getDisplayMetrics(), app.getResources().getConfiguration());
            notifyObservers(skinresource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
