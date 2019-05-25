package com.widget;

import com.selectview.R;

/**
 * 筛选view默认配置项
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectViewConfigImpl implements SelectViewConfig {
    @Override
    public int contentHeight() {
        return 710;
    }

    @Override
    public int titleStyle() {
        return R.layout.select_view_item_linear;
    }

    @Override
    public int itemStyle() {
        return R.layout.select_view_item_grid;
    }

    @Override
    public int columnCount() {
        return 3;
    }

    @Override
    public boolean isAllSingle() {
        return false;
    }
}
