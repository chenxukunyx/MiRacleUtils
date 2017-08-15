package com.miracle.libs.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Create with Android studio
 *
 * @fuction: 管理和回收activity
 * @author: chenxukun
 * @date: 2017-08-14
 * @time: 17:32
 * @age: 24
 */
public class AppDavikActivityMgr {

    private static Stack<Activity> activityStack = new Stack<Activity>();

    private static AppDavikActivityMgr mInstance;

    public static AppDavikActivityMgr getInstace() {
        if (mInstance == null) {
            synchronized (AppDavikActivityMgr.class) {
                if (mInstance == null) {
                    mInstance = new AppDavikActivityMgr();
                }
            }
        }
        return mInstance;
    }

    /**
     * 堆栈中销毁并移除
     * @param activity
     */
    public void removeActivity(Activity activity) {
        AppLogMessageMgr.i("AppDavikActivityMgr-->>removeActivity", activity != null ? activity.toString() : "");
        if (null != activity) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        if (null != activityStack && activityStack.size() > 0) {
            Stack<Activity> tempStack = new Stack<>();
            for (Activity activity : activityStack) {
                if (null != activity) {
                    tempStack.add(activity);
                    activity.finish();
                }
            }
            activityStack.removeAll(tempStack);
        }
        AppLogMessageMgr.i("AppDavikActivityMgr-->>removeAllActivity", "removeAllActivity");
        System.gc();
        System.exit(0);
    }

    /**
     * 获取当前Act对象
     * @return Activity 当前act
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()){
            activity = activityStack.lastElement();
        }
        AppLogMessageMgr.i("AppDavikActivityMgr-->>currentActivity", activity + "");
        return activity;
    }

    /**
     * 获得当前Act的类名
     * @return String
     */
    public String getCurrentActivityName() {
        String actSimpleName = "";
        if (!activityStack.empty()) {
            actSimpleName = activityStack.lastElement().getClass().getSimpleName();
        }
        AppLogMessageMgr.i("AppDavikActivityMgr-->>getCurrentActivityName", actSimpleName);
        return actSimpleName;
    }

    /**
     * 将Act纳入推栈集合中
     * @param activity Act对象
     */
    public void addActivity(Activity activity) {
        AppLogMessageMgr.i("AppDavikActivityMgr-->>addActivity",  activity != null ? activity.toString() : "");
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity
     * @param cls
     * @return void
     */
    public void exitApp(Class<?> cls) {
        AppLogMessageMgr.i("AppDavikActivityMgr-->>exitApp", "exitApp-->>占用内存：" + Runtime.getRuntime().totalMemory());
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
        }
        System.gc();
        System.exit(0);
    }
}
