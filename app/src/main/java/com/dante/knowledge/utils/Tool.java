package com.dante.knowledge.utils;

import android.app.Activity;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * some tools not classified
 */
public class Tool {

    public static void removeFromTransitionManager(Activity activity) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        Class transitionManagerClass = TransitionManager.class;
        try {
            Field runningTransitionsField = transitionManagerClass.getDeclaredField("sRunningTransitions");
            runningTransitionsField.setAccessible(true);
            //noinspection unchecked
            ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> runningTransitions
                    = (ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>>)
                    runningTransitionsField.get(transitionManagerClass);
            if (runningTransitions.get() == null || runningTransitions.get().get() == null) {
                return;
            }
            ArrayMap map = runningTransitions.get().get();
            View decorView = activity.getWindow().getDecorView();
            if (map.containsKey(decorView)) {
                map.remove(decorView);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static long getFileLength(File dir) {
        long length = 0;
        for (File file :
                dir.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else
                length += getFileLength(file);
        }
        return length;
    }

    public static String getFileSize(File dir) {
        BigDecimal bd;
        if (getFileLength(dir) > 1024 * 1024) {
            bd = new BigDecimal(getFileLength(dir) / 1000000);
            return bd.setScale(2, BigDecimal.ROUND_HALF_EVEN) + " M";

        } else {
            //length of file is less than 1 mb, use K as a unit
            bd = new BigDecimal(getFileLength(dir) / 1000);
            return bd.setScale(0, BigDecimal.ROUND_HALF_EVEN) + " k";

        }
    }

}
