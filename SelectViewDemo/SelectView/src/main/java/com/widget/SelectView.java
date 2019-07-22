package com.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.selectview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选框
 *
 * @author wm
 * @date 2019/5/25
 */
public class SelectView extends FrameLayout {
    private Context mContext;
    private SelectViewConfig mConfig;
    private RecyclerView mRecyclerView;
    private SelectViewAdapter mAdapter;
    private boolean isSetData;
    /**
     * 初始化时的数据
     */
    private List<SelectViewItem> mFirstList = new ArrayList<>();
    /**
     * 操作的数据
     */
    private List<SelectViewItem> mList = new ArrayList<>();
    /**
     * 筛选之后保存的数据
     */
    private List<SelectViewItem> mRecordList = new ArrayList<>();

    public SelectView(@NonNull Context context) {
        this(context, null);
    }

    public SelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View rootView = inflate(mContext, R.layout.select_view, this);
        mRecyclerView = rootView.findViewById(R.id.recycler);
    }

    /**
     * 设置配置项
     *
     * @param config SelectViewConfig 配置项
     */
    public SelectView setSelectConfig(@NonNull SelectViewConfig config) {
        mConfig = config;
        return this;
    }

    /**
     * 获得recyclerView
     *
     * @return recyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 设置数据
     *
     * @param list 初始化数据
     */
    public void setData(@NonNull List<SelectViewData> list) {
        if (list.size() == 0) {
            return;
        }
        isSetData = true;
        mFirstList.clear();
        mList.clear();
        mRecordList.clear();
        List<SelectViewItem> items = formatList(list);
        mFirstList.addAll(items);
        try {
            List<SelectViewItem> items1 = Utils.deepCopy(items);
            mList.addAll(items1);
            if (getSelectConfig().isRecordSelect()) {
                List<SelectViewItem> items2 = Utils.deepCopy(items);
                mRecordList.addAll(items2);
            }
            initRecyclerView();
            mAdapter.setData(mList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重组数据
     *
     * @param list List<SelectViewData>
     */
    private List<SelectViewItem> formatList(List<SelectViewData> list) {
        List<SelectViewItem> items = new ArrayList<>();
        for (SelectViewData selectViewData : list) {
            int viewType = selectViewData.getViewType();
            if (viewType == SelectViewData.TYPE_TITLE) {
                String title = selectViewData.getTitle();
                if (TextUtils.isEmpty(title)) {
                    title = "";
                }
                SelectViewItem selectViewItem = new SelectViewItem(SelectViewData.TYPE_TITLE, title);
                items.add(selectViewItem);
            } else {
                List<SelectViewItem> data = (List<SelectViewItem>) selectViewData.getData();
                if (data == null || data.size() == 0) {
                    continue;
                }
                for (SelectViewItem datum : data) {
                    datum.setSelectViewType(SelectViewData.TYPE_GRID);
                    datum.setSelectViewTag(selectViewData.getTag());
                    datum.setSelectViewSingle(selectViewData.isSingle());
                    items.add(datum);
                }
            }
        }
        return items;
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        mAdapter = new SelectViewAdapter();
        mAdapter.setSelectViewConfig(getSelectConfig());
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager manager = new GridLayoutManager(
                mContext,
                getSelectConfig().columnCount(),
                LinearLayoutManager.VERTICAL,
                false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                List<SelectViewItem> list = mAdapter.getList();
                if (list == null || list.size() == 0) {
                    return getSelectConfig().columnCount();
                }
                SelectViewItem selectViewItem = list.get(position);
                int viewType = selectViewItem.getSelectViewType();
                if (viewType == SelectViewData.TYPE_TITLE) {
                    return getSelectConfig().columnCount();
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 重置数据
     */
    public void reset() {
        if (!isSetData) {
            return;
        }
        mList.clear();
        if (mFirstList == null || mFirstList.size() == 0) {
            return;
        }
        try {
            List<SelectViewItem> items1 = Utils.deepCopy(mFirstList);
            mList.addAll(items1);
            mAdapter.setData(mList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有筛选数据
     *
     * @return 已筛选数据
     */
    public List<SelectViewItem> getSelectList() {
        if (!isSetData) {
            return new ArrayList();
        }
        if (getSelectConfig().isRecordSelect()) {
            List<SelectViewItem> list = mAdapter.getList();
            List<SelectViewItem> items2;
            try {
                items2 = Utils.deepCopy(list);
                mRecordList.clear();
                mRecordList.addAll(items2);
            } catch (Exception ignored) {

            }
        }
        return mAdapter.getSelectList();
    }

    /**
     * 恢复筛选数据
     */
    public void recoverSelect() {
        if (!isSetData) {
            return;
        }
        mList.clear();
        if (mRecordList == null || mRecordList.size() == 0) {
            mAdapter.setData(mList);
            return;
        }
        try {
            List<SelectViewItem> items1 = Utils.deepCopy(mRecordList);
            mList.addAll(items1);
            mAdapter.setData(mList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置数据处理委托
     *
     * @param delegation 数据处理委托
     */
    public void setDataDelegation(SelectViewAdapter.DataDelegation delegation) {
        mAdapter.setDataDelegation(delegation);
    }

    /**
     * 设置条目点击监听
     *
     * @param listener 条目点击监听
     */
    public void setOnItemClickListener(SelectViewAdapter.OnItemClickListener listener) {
        mAdapter.setItemClickListener(listener);
    }

    /**
     * 获取配置项,如果没有设置,则采用默认
     *
     * @return 配置项
     */
    private SelectViewConfig getSelectConfig() {
        if (mConfig == null) {
            mConfig = new SelectViewConfigImpl();
        }
        return mConfig;
    }

    /**
     * 手动刷新数据
     *
     * @param item SelectViewItem
     */
    public void setNotify(SelectViewItem item) {
        if (item != null) {
            mAdapter.formatData(item);
            mAdapter.notifyDataSetChanged();
        }
    }
}
