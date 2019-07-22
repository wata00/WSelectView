package com.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选view的adapter
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectViewAdapter extends RecyclerView.Adapter {
    private List<SelectViewItem> mList = new ArrayList<>();
    private SelectViewConfig mConfig;
    private DataDelegation mDataDelegation;
    private OnItemClickListener mItemClickListener;

    public void setData(List<SelectViewItem> list) {
        this.mList.clear();
        if (list != null && list.size() > 0) {
            this.mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getSelectViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SelectViewData.TYPE_GRID:
                view = LayoutInflater.from(parent.getContext()).inflate(mConfig.itemStyle(),
                        parent,
                        false);
                return new GridHolder(view);
            case SelectViewData.TYPE_TITLE:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(mConfig.titleStyle(), parent,
                        false);
                return new LinearHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectViewItem selectViewItem = mList.get(position);
        if (selectViewItem.getSelectViewType() == SelectViewData.TYPE_GRID) {
            GridHolder holder1 = (GridHolder) holder;
            holder1.setData(selectViewItem);
        } else {
            LinearHolder holder1 = (LinearHolder) holder;
            holder1.setData(selectViewItem);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    void setSelectViewConfig(SelectViewConfig config) {
        mConfig = config;
    }

    /**
     * 获取选中的列表
     *
     * @return 选中的列表
     */
    List<SelectViewItem> getSelectList() {
        List<SelectViewItem> list = new ArrayList<>();
        if (mList.size() == 0) {
            return null;
        }
        for (SelectViewItem selectViewItem : mList) {
            boolean selectViewCheck = selectViewItem.isSelectViewCheck();
            if (selectViewCheck) {
                list.add(selectViewItem);
            }
        }
        return list;
    }

    /**
     * 获取当前列表
     *
     * @return 当前列表
     */
    List<SelectViewItem> getList() {
        if (mList.size() == 0) {
            return null;
        }
        return new ArrayList<>(mList);
    }

    /**
     * 条目viewHolder
     */
    public class GridHolder extends RecyclerView.ViewHolder {
        View mView;

        GridHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setData(final SelectViewItem item) {
            if (mDataDelegation != null) {
                mDataDelegation.setItem(mView, item);
            }
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    formatData(item);
                    notifyDataSetChanged();
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(item);
                    }
                }
            });
        }
    }

    /**
     * 刷新数据
     *
     * @param item 点击item
     */
    void formatData(SelectViewItem item) {
        boolean allSingle = mConfig.isAllSingle();
        if (allSingle) {
            for (int i = 0; i < mList.size(); i++) {
                SelectViewItem selectViewItem = mList.get(i);
                if (selectViewItem.equals(item)) {
                    selectViewItem.setSelectViewCheck(true);
                    mList.set(i, selectViewItem);
                } else {
                    selectViewItem.setSelectViewCheck(false);
                    mList.set(i, selectViewItem);
                }
            }
            return;
        }
        boolean selectViewSingle = item.isSelectViewSingle();
        int selectViewTag = item.getSelectViewTag();
        boolean selectViewCheck1 = item.isSelectViewCheck();
        if (selectViewSingle) {
            if (!selectViewCheck1) {
                for (int i = 0; i < mList.size(); i++) {
                    SelectViewItem selectViewItem = mList.get(i);
                    int selectViewTag1 = selectViewItem.getSelectViewTag();
                    if (selectViewTag1 == selectViewTag) {
                        if (selectViewItem.equals(item)) {
                            selectViewItem.setSelectViewCheck(true);
                            mList.set(i, selectViewItem);
                        } else {
                            selectViewItem.setSelectViewCheck(false);
                            mList.set(i, selectViewItem);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < mList.size(); i++) {
                SelectViewItem selectViewItem = mList.get(i);
                if (selectViewItem.equals(item)) {
                    boolean selectViewCheck = selectViewItem.isSelectViewCheck();
                    selectViewItem.setSelectViewCheck(!selectViewCheck);
                    mList.set(i, selectViewItem);
                }
            }
        }
    }

    /**
     * 标题viewHolder
     */
    public class LinearHolder extends RecyclerView.ViewHolder {
        View mView;

        LinearHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setData(SelectViewItem item) {
            if (mDataDelegation != null) {
                mDataDelegation.setTitle(mView, item);
            }
        }
    }

    /**
     * 条目点击监听
     */
    public interface OnItemClickListener {
        /**
         * 条目点击监听
         *
         * @param item item条目
         */
        void onItemClick(SelectViewItem item);
    }

    /**
     * 设置条目点击监听
     *
     * @param listener 条目点击监听
     */
    void setItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * 数据设置委托
     */
    public interface DataDelegation {
        /**
         * 设置标题
         *
         * @param view 当前item
         * @param item 标题
         */
        void setTitle(View view, SelectViewItem item);

        /**
         * 设置条目
         *
         * @param view 当前item
         * @param item 条目
         */
        void setItem(View view, SelectViewItem item);
    }

    /**
     * 设置数据
     *
     * @param dataDelegation 设置数据
     */
    void setDataDelegation(DataDelegation dataDelegation) {
        mDataDelegation = dataDelegation;
    }
}
