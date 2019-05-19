package com.dalancon.aptdemo.utils;

import android.app.Activity;
import android.view.View;

/**
 * Created by dalancon on 2019/5/18.
 */

public class Utils {

    public static <T extends View> T findViewById(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }
}
