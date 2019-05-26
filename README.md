# WSelectView

筛选框

## 使用方式

- 依赖

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.qyxghcl:WSelectView:v1.2'
}

```

- 使用

(1)添加配置表
**配置表需在设置数据之前配置,如不配置,则采取默认配置**

```
//1.全部采取个性化配置
mSelectView.setSelectConfig(new SelectViewConfig() {
            /**
             * 标题布局资源文件 默认layout.select_view_item_linear
             */
            @Override
            public int titleStyle() {
                return layout.select_view_item_linear;
            }

            /**
             * 条目布局资源文件 默认layout.select_view_item_grid
             */
            @Override
            public int itemStyle() {
                return layout.select_view_item_grid;
            }

            /**
             * 条目单行列数 默认3
             */
            @Override
            public int columnCount() {
                return 3;
            }

            /**
             * 是否为全局单选 默认false
             */
            @Override
            public boolean isAllSingle() {
                return false;
            }
        });

//2.采取部分个性化配置(选取单独方法重写即可)
 mSelectView.setSelectConfig(new SelectViewConfigImpl() {
            @Override
            public boolean isAllSingle() {
                return true;
            }
        });

```

支持配置项:

| 配置项           | 默认值                                    |
| ---------------- | ----------------------------------------- |
| 标题布局资源文件 | @LayoutRes layout.select_view_item_linear |
| 条目布局资源文件 | @LayoutRes layout.select_view_item_grid   |
| 条目单行列数     | int 3                                     |
| 是否为全局单选   | boolean false                             |

(2)设置数据

```
mSelectView.setData(@NonNull List<SelectViewData> list);

//SelectViewData默认包含两种创建方式
//1.标题创建
SelectViewData selectViewData = new SelectViewData<SelectViewItem>("标题1");
//2.条目创建
List<SelectViewItem> items = new ArrayList<>();
/**
 * @params items List<SelectViewItem> 条目列表
 * @params isSingleSelect 这个列表中的条目是否单选 (只适用于config中支持多选的情况)
 * @params tag int 列表标识 (tag作为条目列表group的标识)
 */
SelectViewData selectViewData = new SelectViewData<>(items, true, 1);

//SelectViewItem
//自行条目数据需继承SelectViewItem 参数id作为是否相同判断的唯一标识
```

(3)数据设置

```
mSelectView.setDataDelegation(new SelectViewAdapter.DataDelegation() {
  /**
   * 标题数据设置
   */
   @Override
   public void setTitle(View view, SelectViewItem item) {
      TextView viewById = view.findViewById(R.id.linear_text);
      viewById.setText(item.getSelectViewTitle());
   }

   /**
    * 条目数据设置,可根据item的属性,来选择不同的设置方式
    */
   @Override
   public void setItem(View view, SelectViewItem item) {
      TextView viewById = view.findViewById(R.id.text_gird);
      boolean selectViewSingle = item.isSelectViewSingle();
      if (item.isSelectViewCheck()){
        if (selectViewSingle){
          viewById.setBackgroundResource(R.drawable.select_view_sp_r20_ff8384);
        }else {
          viewById.setBackgroundResource(R.drawable.select_view_sp_r20_st_ff8384);
        }
          viewById.setTextColor(Color.RED);
        }else {
          viewById.setBackgroundResource(R.drawable.select_view_sp_r20_f5f5f5);
          viewById.setTextColor(Color.parseColor("#333333"));
        }
        viewById.setText(item.getSelectViewTitle());
      }
  });
```

(4)监听以及其他 api

```
/**
 * 条目点击监听
 */
mSelectView.setOnItemClickListener(new SelectViewAdapter.OnItemClickListener() {
  @Override
  public void onItemClick(SelectViewItem item) {
    long id = item.getId();
  }
});

/**
 * 当前所选择的全部条目
 */
List<SelectViewItem> selectList = mSelectView.getSelectList();

/**
 * 重置回初始值
 */
mSelectView.reset();
```

- API

| api                                                                    | 功能                 |
| ---------------------------------------------------------------------- | -------------------- |
| setSelectConfig(SelectViewConfig config)                               | 设置配置项           |
| setData(@NonNull List<SelectViewData> list)                            | 设置数据             |
| setDataDelegation(SelectViewAdapter.DataDelegation delegation)         | 视图填充             |
| setOnItemClickListener(SelectViewAdapter.OnItemClickListener listener) | 单选监听             |
| getSelectList()                                                        | 当前所选择的全部条目 |
| reset()                                                                | 重置回初始值         |
