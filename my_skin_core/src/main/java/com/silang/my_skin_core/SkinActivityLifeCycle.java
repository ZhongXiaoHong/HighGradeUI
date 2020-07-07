package com.silang.my_skin_core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SkinActivityLifeCycle implements Application.ActivityLifecycleCallbacks {

//    private LayoutInflater.Factory2 factory2;
//
//    public LayoutInflaterCollecter(LayoutInflater.Factory2 factory2) {
//        this.factory2 = factory2;
//    }

    private HashMap<Activity, SkinLayoutFactory> factoryHashMap = new HashMap<>();

    //TODO 会在setContentView之前执行
    //TODO 这样自定义的Factory2接管了系统创建View的工作
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        Log.d("zhong","*******************onActivityCreated**************");
        LayoutInflater inflater = LayoutInflater.from(activity);
        Log.d("zhong","*******************LayoutInflater = **************"+ activity.getClass().getName()+"-----"+inflater.hashCode());
        try {

            //TODO mFactorySet 不同的版本Inflater的具体实现类不同，
            //TODO 比如华为手机是HWLayoutInflate---->PhoneLayoutInflate------>LayoutInflate
            //TODO 比如夜神模拟器PhoneLayoutInflate------>LayoutInflate
            //TODO 华为手机 Field mFactorySetField = inflater.getClass().getSuperclass().getSuperclass().getDeclaredField("mFactorySet");
            //TODO 夜神模拟器   Field mFactorySetField = inflater.getClass().getSuperclass()..getDeclaredField("mFactorySet");
            //TODO 也就是不同设备找  mFactorySet要向上翻阅不同的class,实际上不必如此既然知道是LayoutInflate的mFactorySet，使用LayoutInflate.class一步到位就可以
            Field mFactorySetField = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySetField.setAccessible(true);
            mFactorySetField.setBoolean(inflater, false);
            SkinLayoutFactory factory2 = new SkinLayoutFactory();
            LayoutInflaterCompat.setFactory2(inflater, factory2);
            SkinManager.getInstance().addObserver(factory2);
            factoryHashMap.put(activity, factory2);
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinLayoutFactory factory = factoryHashMap.get(activity);
        SkinManager.getInstance().deleteObserver(factory);
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


}
