package com.widget;

import java.util.List;

/**
 * 筛选view数据
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectViewData<T extends SelectViewItem> {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_GRID = 1;

    private int viewType;

    private List<T> data;
    private String title;
    /**
     * 对应标志的groupId,相应的groupId的item会出现同一表现
     */
    private int tag;
    private boolean isSingle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public SelectViewData(String title) {
        this.viewType = TYPE_TITLE;
        this.title = title;
    }

    public SelectViewData(List<T> data, boolean isSingle, int tag) {
        this.viewType = TYPE_GRID;
        this.isSingle = isSingle;
        this.data = data;
        this.tag = tag;
    }

    public List<T> getData() {
        return data;
    }

    public int getViewType() {
        return viewType;
    }
}
