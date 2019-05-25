package com.widget;

import android.content.Context;
import android.util.DisplayMetrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 工具类
 *
 * @author wm
 * @date 2019/5/25
 */
final class Utils {
    /**
     * dip转px
     *
     * @param context context
     * @param dp      dp
     * @return px
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return 屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return 屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * list深copy
     *
     * @param src 数据源
     * @param <T> 泛型
     * @return 新数据
     * @throws IOException io异常
     * @throws ClassNotFoundException 类型转化异常
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }
}
