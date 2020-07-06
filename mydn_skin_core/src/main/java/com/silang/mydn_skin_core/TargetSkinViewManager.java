package com.silang.mydn_skin_core;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class TargetSkinViewManager {

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

    public void addView(View view, AttributeSet attributeSet) {
        List<TargetPair> pairs = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attributeName = attributeSet.getAttributeName(i);
            if (ATTRIBUTES.contains(attributeName)) {
                String value = attributeSet.getAttributeValue(i);
                if (value.startsWith("#")) {
                    continue;
                }
                int resId = 0;
                if (value.startsWith("?")) {

                    int attrId = Integer.parseInt(value.substring(1));
                    resId = ThemeUtil.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //@12356
                    //TODO 取@之后的值作为int值
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

    public void applySkin(Resources resources) {
        for (TargetView needChangeSkinView : needChangeSkinViews) {
            View view = needChangeSkinView.view;
            for (TargetPair pair : needChangeSkinView.pairs) {


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
