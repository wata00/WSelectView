package com.widget;

import android.support.annotation.LayoutRes;

/**
 * 筛选view配置项
 *
 * @author wm
 * @date 2019/5/25
 */
public interface SelectViewConfig {
    /**
     * 标题样式
     *
     * @return int资源文件
     */
    @LayoutRes
    int titleStyle();

    /**
     * 选中条目样式
     *
     * @return int资源文件
     */
    @LayoutRes
    int itemStyle();

    /**
     * 列数
     *
     * @return int 列数
     */
    int columnCount();

    /**
     * 是否全部单选
     *
     * @return
     */
    boolean isAllSingle();
}
