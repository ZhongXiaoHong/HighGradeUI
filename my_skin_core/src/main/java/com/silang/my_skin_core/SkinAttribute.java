package com.silang.my_skin_core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class SkinAttribute {

    //TODO 需要换肤
    private List<TargetView> needChangeSkinViews = new ArrayList<>();
    private static final List<String> ATTRIBUTES = new ArrayList<>();

    static {
        ATTRIBUTES.add("src");
        ATTRIBUTES.add("background");
        ATTRIBUTES.add("textColor");
        ATTRIBUTES.add("drawableLeft");
        ATTRIBUTES.add("drawableRight");
        ATTRIBUTES.add("drawableTop");
        ATTRIBUTES.add("drawableBottom");
    }

    //TODO 筛选符合可能需要换肤的View

    public void filter(View view, AttributeSet attributeSet) {
        List<TargetPair> pairs = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            //TODO 获得属性名，比如textColor
            String attributeName = attributeSet.getAttributeName(i);
            if (ATTRIBUTES.contains(attributeName)) {
                //TODO 属性名对应的属性值，形如：“@7712456”
                String value = attributeSet.getAttributeValue(i);
                //TODO 写死的属性值，不换肤
                if (value.startsWith("#")) {
                    continue;
                }
                int resId = 0;
                //TODO
                if (value.startsWith("?")) {
                    int attrId = Integer.parseInt(value.substring(1));
                    //TODO
                    resId = ThemeUtil.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //@12356
                    //TODO 取@之后的值作为int值，取第一位之后的子串
                    resId = Integer.parseInt(value.substring(1));

                }
                if (resId != 0) {
                    TargetPair pair = new TargetPair(attributeName, resId);
                    pairs.add(pair);
                }

            }
        }

        if (!pairs.isEmpty()) {
            TargetView tView = new TargetView(view, pairs);
            needChangeSkinViews.add(tView);
        }

    }

    public void applySkin() {
        for (TargetView needChangeSkinView : needChangeSkinViews) {
            View view = needChangeSkinView.view;
            //TODO 默认的Resource
            Resources appResource = view.getContext().getResources();
            AssetManager appAssetsManager = appResource.getAssets();

            for (TargetPair pair : needChangeSkinView.pairs) {
                //pair.attributeName;  属性名textColor
                //pair.resId 资源Id  比如@String/name对应的ID
                //资源的名字
//                String typeName = appResource.getResourceTypeName(pair.resId);
//                String entryName = appResource.getResourceEntryName(pair.resId);
//                int newId = resources.getIdentifier(entryName, typeName, "com.example.skinpk");
                switch (pair.attributeName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(pair.resId);
                        if(background instanceof  Integer){
                            view.setBackgroundColor((Integer)background);
                        }else{
                            ViewCompat.setBackground(view,(Drawable)background);
                        }
//                        if (typeName.equals("drawable")) {
//                         //   ViewCompat.setBackground(view, DrawableCompat.);
//
//                        } else if (typeName.equals("color")) {
//                            int color = resources.getColor(newId);
//                            view.setBackgroundColor(color);
//                        }

                        break;
                }

            }
        }
    }
}

class TargetView {
    View view;
    List<TargetPair> pairs;

    public TargetView(View view, List<TargetPair> pairs) {
        this.view = view;
        this.pairs = pairs;
    }
}

class TargetPair {
    String attributeName;
    int resId;

    public TargetPair(String attributeName, int resId) {
        this.attributeName = attributeName;
        this.resId = resId;
    }
}
