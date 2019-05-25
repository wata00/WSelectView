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
     * 获取屏幕宽度
     *
     * @param context context
     * @return 屏幕宽度(px)
     */
    static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
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
    static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
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
