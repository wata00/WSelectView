package com.widget;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 筛选view条目实体类
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectViewItem implements Serializable {
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

    public SelectViewItem(String selectViewTitle) {
        this.selectViewTitle = selectViewTitle;
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

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        SelectViewItem obj1 = (SelectViewItem) obj;
        if(TextUtils.isEmpty(obj1.selectViewTitle)){
            return false;
        }
        return obj1.selectViewTitle.equals(this.selectViewTitle);
    }
}
