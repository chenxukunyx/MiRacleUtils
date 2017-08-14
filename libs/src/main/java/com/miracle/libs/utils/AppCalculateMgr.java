package com.miracle.libs.utils;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-14
 * @time: 10:00
 * @age: 24
 */

/**
 * 提供app应用计算、算法
 */
public class AppCalculateMgr {

    /**
     * 计算两点间的距离
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    public static double distance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2) + Math.abs(y1 - y2) * Math.abs(y1 - y2));
    }

    /**
     * 计算a（x，y）的角度
     * @param x
     * @param y
     * @return
     */
    public static double point2ToDegree(double x, double y) {
        return Math.toDegrees(Math.atan2(x, y));
    }

    /**
     * 计算点在圆内
     * @param sx
     * @param sy
     * @param r
     * @param x
     * @param y
     * @return
     */
    public static boolean checkInRound(float sx, float sy, float r, float x, float y) {
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }
}