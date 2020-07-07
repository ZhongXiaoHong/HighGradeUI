package com.silang.mydn_skin_core;

import android.app.Activity;
import android.app.Application;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;

public class LayoutInflaterCollecter implements Application.ActivityLifecycleCallbacks {

//    private LayoutInflater.Factory2 factory2;
//
//    public LayoutInflaterCollecter(LayoutInflater.Factory2 factory2) {
//        this.factory2 = factory2;
//    }

    private HashMap<Activity,DnSkinFactory>  factoryHashMap = new HashMap<>();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        try {
            Field mFactorySetField = inflater.getClass().getSuperclass().getSuperclass().getDeclaredField("mFactorySet");
            mFactorySetField.setAccessible(true);
            mFactorySetField.setBoolean(inflater, false);

            //TODO  获取所有的Activity的LayoutInflater
            mFactorySetField.setBoolean(inflater, false);
            DnSkinFactory factory2 = new DnSkinFactory();
            LayoutInflaterCompat.setFactory2(inflater, factory2);
            DnSkinManager.getInstance().addObserver(factory2);
            DnSkinManager.getInstance().dnSkinFactory = factory2;
            factoryHashMap.put(activity,factory2);
        }catch (Exception e){

            System.out.println(e.toString());
        }


    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        DnSkinFactory factory = factoryHashMap.get(activity);
        DnSkinManager.getInstance().deleteObserver(factory);
    }
}
