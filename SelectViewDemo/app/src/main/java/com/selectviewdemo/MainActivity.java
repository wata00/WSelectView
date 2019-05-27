package com.selectviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.widget.SelectView;
import com.widget.SelectViewAdapter;
import com.widget.SelectViewData;
import com.widget.SelectViewItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SelectView mSelectView;
    private List<SelectViewData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectView = findViewById(R.id.select_view);
        mockData();
        mSelectView.setData(mList);
        mSelectView.setDataDelegation(new SelectViewAdapter.DataDelegation() {
            @Override
            public void setTitle(View view, SelectViewItem item) {
                TextView viewById = view.findViewById(R.id.linear_text);
                viewById.setText(item.getSelectViewTitle());
            }

            @Override
            public void setItem(View view, SelectViewItem item) {
                TextView viewById = view.findViewById(R.id.text_gird);
                boolean selectViewSingle = item.isSelectViewSingle();
                if (item.isSelectViewCheck()){
                    if (selectViewSingle){
                        viewById.setBackgroundResource(R.drawable.select_view_sp_r18_f83244);
                        viewById.setTextColor(Color.parseColor("#ffffff"));
                    }else {
                        viewById.setBackgroundResource(R.drawable.select_view_sp_r18_st_f83244);
                        viewById.setTextColor(Color.parseColor("#f83244"));
                    }
                }else {
                    viewById.setBackgroundResource(R.drawable.select_view_sp_r18_f6f6f7);
                    viewById.setTextColor(Color.parseColor("#333333"));
                }
                viewById.setText(item.getSelectViewTitle());
            }
        });
        mSelectView.setOnItemClickListener(new SelectViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SelectViewItem item) {

            }
        });

        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SelectViewItem> selectList = mSelectView.getSelectList();
//                SelectViewItem selectViewItem = selectList.get(0);
            }
        });
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectView.reset();
            }
        });
    }

    private void mockData() {
        SelectViewData selectViewData = new SelectViewData<SelectViewItem>("第一题");
        SelectViewData selectViewData1 = new SelectViewData<SelectViewItem>("第二题");
        SelectViewData selectViewData2 = new SelectViewData<SelectViewItem>("第三题");
        SelectViewData selectViewData3 = new SelectViewData<SelectViewItem>("第四题");
        SelectViewData selectViewData4 = new SelectViewData<SelectViewItem>("第五题");
        SelectViewData selectViewData5 = new SelectViewData<SelectViewItem>("第六题");
        SelectViewItem selectViewItem = new SelectViewItem("a");
        SelectViewItem selectViewItem1 = new SelectViewItem("b");
        SelectViewItem selectViewItem2 = new SelectViewItem("c");
        SelectViewItem selectViewItem3 = new SelectViewItem("d");
        SelectViewItem selectViewItem4 = new SelectViewItem("e");
        SelectViewItem selectViewItem5 = new SelectViewItem("f");
        List<SelectViewItem> items = new ArrayList<>();
        items.add(selectViewItem);
        items.add(selectViewItem1);
        SelectViewData selectViewData6 = new SelectViewData<>(items, true, 1);
        List<SelectViewItem> items1 = new ArrayList<>();
        items1.add(selectViewItem2);
        items1.add(selectViewItem3);
        items1.add(selectViewItem4);
        items1.add(selectViewItem5);
        SelectViewData selectViewData7 = new SelectViewData<>(items1, false, 2);
        mList.add(selectViewData);
        mList.add(selectViewData1);
        mList.add(selectViewData6);
        mList.add(selectViewData2);
        mList.add(selectViewData7);
        mList.add(selectViewData3);
        mList.add(selectViewData4);
        mList.add(selectViewData5);
    }
}
