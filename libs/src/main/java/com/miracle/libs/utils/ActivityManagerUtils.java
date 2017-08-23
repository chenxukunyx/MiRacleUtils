package com.miracle.libs.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Create with Android studio
 *
 * @fuction: activity管理
 * @author: chenxukun
 * @date: 2017-08-23
 * @time: 14:24
 * @age: 24
 */
public class ActivityManagerUtils {
    private static Stack<Activity> mStack;
    private static ActivityManagerUtils mInstance;

    public static ActivityManagerUtils getInstance() {
        if (mInstance == null) {
            mInstance = new ActivityManagerUtils();
        }
        if (mStack == null) {
            mStack = new Stack<>();
        }
        return mInstance;
    }

    public static Activity getActivity(Class<?> cls) {
        if (mStack != null) {
            for (Activity activity : mStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    public void addActivity(Activity activity) {
        mStack.add(activity);
    }

    public Activity getCurrentActivity() {
        return mStack.lastElement();
    }

    public void finishActivity() {
        finishActivity(mStack.lastElement());
    }

    public void finishActivity(Activity activity) {
        if (activity != null && mStack.contains(activity)) {
            mStack.remove(activity);
            activity.finish();
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && mStack.contains(activity)) {
            mStack.remove(activity);
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = mStack.size(); i < size; i++) {
            if (null != mStack.get(i)) {
                finishActivity(mStack.get(i));
            }
        }
        mStack.clear();
    }

    public void exitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
            MLog.e(e.getMessage());
        }
    }
}
