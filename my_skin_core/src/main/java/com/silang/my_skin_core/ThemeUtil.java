package com.silang.my_skin_core;

import android.content.Context;
import android.content.res.TypedArray;

public class ThemeUtil {

    public static int[] getResId(Context context, int[] attrs) {
        //TODO  获取属性组
        TypedArray array = context.obtainStyledAttributes(attrs);
        int[] resIds = new int[attrs.length];
        for (int i = 0; i < array.length(); i++) {
            resIds[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return resIds;
    }
}
