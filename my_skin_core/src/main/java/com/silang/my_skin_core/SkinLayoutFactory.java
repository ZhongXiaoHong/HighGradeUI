package com.silang.my_skin_core;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

//TODO 每一个Activity有一个LayoutInflater
//TODO 一个LayoutInflater对应有一个Factory2
//TODO
public class SkinLayoutFactory implements LayoutInflater.Factory2, Observer {


    private SkinAttribute skinAttribute = new SkinAttribute();

    private static final String[] ANDROID_SDK_VIEW_PRE_NAME = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    //TODO 构造方法的签名，会经常用到这个签名，
    //TODO 为了避免每一次都new,定义成static  final 复用
    private static final Class[] CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};

    //TODO 缓存类名对应的Constructor,避免每次都要反射找Constructor
    private static HashMap<String, Constructor<? extends View>> cacheConstructorMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        View view = createViewFromTag(name, context, attrs);
        if (view == null) {
            view = creatCustomView(context, name, attrs);
        }
        //TODO 过滤View如果是需要换肤的View，则保存起来
        if (view != null) {
            skinAttribute.filter(view, attrs);
        }
        return view;
    }

    private View creatCustomView(Context context, String className, AttributeSet attributeSet) {

        return createView(context, className, attributeSet);
    }


    //TODO 只创建Android系统原生的View
    private View createViewFromTag(String name, Context context, AttributeSet attributeSet) {


        //TODO 不等于-1，表示存在点，则是自定义View
        if (-1 != name.indexOf(".")) {
            return null;
        }


        //TODO  创建Android系统原生的View
        //TODO  很直接、很暴力，既然确定是Android原生的View，前缀也就那么几种
        //TODO  直接挨个前缀拼接尝试能不能找到类，找到则反射创建View
        for (int i = 0; i < ANDROID_SDK_VIEW_PRE_NAME.length; i++) {

            String fullClassName = new StringBuffer(ANDROID_SDK_VIEW_PRE_NAME[i]).append(name).toString();
            View view = createView(context, fullClassName, attributeSet);
            if (view != null) {
                return view;
            }
        }

        return null;

    }


    //TODO 拼接全路径类名，反射创建View
    private View createView(Context context, String className, AttributeSet attributeSet) {
        try {

            Constructor<? extends View> constructor;
            if (cacheConstructorMap.containsKey(className)) {
                constructor = cacheConstructorMap.get(className);
            } else {
                Class<? extends View> clazz = context.getClassLoader().loadClass(className).asSubclass(View.class);
                constructor = clazz.getConstructor(CONSTRUCTOR_SIGNATURE);
                cacheConstructorMap.put(className, constructor);
            }
            return constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        skinAttribute.applySkin();
    }

}
