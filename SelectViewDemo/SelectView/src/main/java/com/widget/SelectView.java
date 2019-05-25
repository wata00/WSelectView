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
import android.widget.LinearLayout;

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
            throw new RuntimeException("do not set 0");
        }
        isSetData = true;
        mFirstList.clear();
        mList.clear();
        List<SelectViewItem> items = formatList(list);
        mFirstList.addAll(items);
        try {
            List<SelectViewItem> items1 = Utils.deepCopy(items);
            mList.addAll(items1);
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
            if (viewType == SelectViewData.TYPE_LINEAR) {
                String title = selectViewData.getTitle();
                if (TextUtils.isEmpty(title)) {
                    title = "";
                }
                SelectViewItem selectViewItem = new SelectViewItem(SelectViewData.TYPE_LINEAR, title);
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
        fitScreen();
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
                if (viewType == SelectViewData.TYPE_LINEAR) {
                    return getSelectConfig().columnCount();
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 屏幕适配
     */
    private void fitScreen() {
        int height = getSelectConfig().contentHeight();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
        lp.width = Utils.getScreenWidth(mContext);
        lp.height = height;
        mRecyclerView.setLayoutParams(lp);
    }

    /**
     * 重置数据
     */
    public void reset() {
        if (!isSetData) {
            throw new RuntimeException("u should setData first");
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
            throw new RuntimeException("u should setData first");
        }
        return mAdapter.getSelectList();
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
}