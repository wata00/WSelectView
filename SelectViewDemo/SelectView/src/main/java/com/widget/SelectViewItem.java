package com.widget;

import java.io.Serializable;

/**
 * 筛选view条目实体类
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectViewItem implements Serializable {
    /**
     * 唯一标识id
     */
    private long id;
    /**
     * 是否选中状态 true:选中 false:未选中
     */
    private boolean isSelectViewCheck;
    /**
     * 类型
     */
    private int selectViewType;
    /**
     * tag标志所属group
     */
    private int selectViewTag;
    /**
     * 标注是否单选
     */
    private boolean isSelectViewSingle;
    /**
     * 标题
     */
    private String selectViewTitle;

    public SelectViewItem() {

    }

    public SelectViewItem(long id, String selectViewTitle) {
        this.selectViewTitle = selectViewTitle;
        this.id = id;
    }

    SelectViewItem(int selectViewType, String selectViewTitle) {
        this.selectViewType = selectViewType;
        this.selectViewTitle = selectViewTitle;
    }

    public String getSelectViewTitle() {
        return selectViewTitle;
    }

    public void setSelectViewTitle(String selectViewTitle) {
        this.selectViewTitle = selectViewTitle;
    }

    public boolean isSelectViewCheck() {
        return isSelectViewCheck;
    }

    public void setSelectViewCheck(boolean check) {
        isSelectViewCheck = check;
    }

    public int getSelectViewType() {
        return selectViewType;
    }

    public void setSelectViewType(int selectViewType) {
        this.selectViewType = selectViewType;
    }

    public int getSelectViewTag() {
        return selectViewTag;
    }

    public void setSelectViewTag(int selectViewTag) {
        this.selectViewTag = selectViewTag;
    }

    public boolean isSelectViewSingle() {
        return isSelectViewSingle;
    }

    public void setSelectViewSingle(boolean selectViewSingle) {
        isSelectViewSingle = selectViewSingle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SelectViewItem other = (SelectViewItem) obj;
        return this.id == other.id;
    }
}
