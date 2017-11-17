package com.chenbing.coorchicelibone.CustemViews.filter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenbing.coorchicelibone.CustemViews.supertextview.SuperTextView;
import com.chenbing.iceweather.BuildConfig;
import com.chenbing.iceweather.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by coorchice on 2017/11/14.
 */

public class FlightFilter extends FrameLayout implements IFilter<FilterDetailData> {

    private View rootView;

    private TextView tvCancel;
    private TextView tvResultNum;
    private TextView tvComfire;

    private RecyclerView rvLeft;
    private RecyclerView rvRight;
    private float dp;
    private Map<MagicData<String>, List<MagicData<FilterDetailData>>> datas = new LinkedHashMap<>();
    private Map<MagicData<String>, List<MagicData<FilterDetailData>>> datasClone = new LinkedHashMap<>();
    private Map<MagicData<String>, List<MagicData<FilterDetailData>>> testDatas;
    private List<MagicData<FilterDetailData>> conditions;

    private IFilter.OnClickListener onClickListener;
    private RecyclerView rvConditions;
    private CheckBox cbLeft, cbRight;


    public FlightFilter(Context context) {
        super(context);
        init();
    }

    public FlightFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlightFilter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlightFilter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        if (isInEditMode() || BuildConfig.DEBUG) {
            testDatas = new LinkedHashMap<>();

            BindDataLogic<MagicData<FilterDetailData>> unlimitedLogic_1 = initUnlimitedLogic_1();
            BindDataLogic<MagicData<FilterDetailData>> checkBoxLogic = getCheckBoxLogic();

            // 初始化公用的Builder模版，用于创建统一的数据类型MagicData
            MagicData.Builder rvLeftBuilder = MagicData.templateBuilder(R.layout.item_type);
            MagicData.Builder textSelectBuilder = MagicData.templateBuilder(R.layout.item_text_select);
            MagicData.Builder boxSelectBuilder = MagicData.templateBuilder(R.layout.item_box_select, checkBoxLogic);
            MagicData.Builder boxSelectWithImageBuilder = MagicData.templateBuilder(R.layout.item_box_select_with_image, checkBoxLogic);


            List<MagicData<FilterDetailData>> airline = new ArrayList<>();
            airline.add(textSelectBuilder
                    .setData(new FilterDetailData("不限", null))
                    .setBindLogic(unlimitedLogic_1)
                    .setState(true)
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("中国联航", "fkn"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("南方航空", "fnx"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("中国国航", "fca"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("东方航空", "fmu"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("上海航空", "ffm"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("深圳航空", "fvd"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("海南航空", "fcn"))
                    .build());
            airline.add(boxSelectWithImageBuilder
                    .setData(new FilterDetailData("吉祥航空", "fho"))
                    .build());
            testDatas.put(rvLeftBuilder.setData("航空公司").build(), airline);


            AdapterRule<MagicData<FilterDetailData>> rule1 = new AdapterRule<MagicData<FilterDetailData>>() {

                @Override
                public int layout(int position, List<MagicData<FilterDetailData>> data) {
                    int layoutType = data.get(position).getType();
                    if (layoutType == -99) {
                        layoutType = R.layout.item_text_select;
                    }
                    return layoutType;
                }

                @Override
                public void bindData(RecyclerView.Adapter adapter, View itemView, List<MagicData<FilterDetailData>> datas, int position) {
                    final MagicData<FilterDetailData> magicData = datas.get(position);
                    FilterDetailData data = null;
                    if (magicData != null) {
                        data = magicData.getData();
                    }
                    if (magicData != null && data != null) {
                        if (magicData.getBindLogic() != null) {
                            magicData.getBindLogic().bindData(rvRight, itemView, datas, position);
                            return;
                        }
                    }

                }
            };

            List<MagicData<FilterDetailData>> date = new ArrayList<>();
            date.add(textSelectBuilder
                    .setData(new FilterDetailData("不限", null))
                    .setBindLogic(unlimitedLogic_1)
                    .setState(true)
                    .build());
            date.add(boxSelectBuilder
                    .setData(new FilterDetailData("00:00--06:00", null))
                    .build());
            date.add(boxSelectBuilder
                    .setData(new FilterDetailData("06:00--12:00", null))
                    .build());
            date.add(boxSelectBuilder
                    .setData(new FilterDetailData("12:00--18:00", null))
                    .build());
            date.add(boxSelectBuilder
                    .setData(new FilterDetailData("18:00--24:00", null))
                    .build());
            testDatas.put(rvLeftBuilder.setData("起飞时段").build(), date);

            List<MagicData<FilterDetailData>> airplaneType = new ArrayList<>();
            airplaneType.add(textSelectBuilder
                    .setData(new FilterDetailData("不限", null))
                    .setBindLogic(unlimitedLogic_1)
                    .setState(true)
                    .build());
            airplaneType.add(boxSelectBuilder
                    .setData(new FilterDetailData("大型机", null))
                    .build());
            airplaneType.add(boxSelectBuilder
                    .setData(new FilterDetailData("中型机", null))
                    .build());
            airplaneType.add(boxSelectBuilder
                    .setData(new FilterDetailData("其他机型", null))
                    .build());
//            airplaneType.add(new MagicData<>(new FilterDetailData("大型机", null), false, R.layout.item_box_select));
//            airplaneType.add(new MagicData<>(new FilterDetailData("中型机", null), false, R.layout.item_box_select));
//            airplaneType.add(new MagicData<>(new FilterDetailData("小型机", null), false, R.layout.item_box_select));
            testDatas.put(rvLeftBuilder.setData("机型").build(), airplaneType);

            List<MagicData<FilterDetailData>> airport = new ArrayList<>();
            airport.add(new MagicData<>(new FilterDetailData("不限", null), true, R.layout.item_text_select));
            airport.add(new MagicData<>(new FilterDetailData("浦东国际机场", null), false, R.layout.item_text_select));
            airport.add(new MagicData<>(new FilterDetailData("虹桥国际机场", null), false, R.layout.item_text_select));
            airport.add(new MagicData<>(new FilterDetailData("南苑机场", null), false, R.layout.item_text_select));
            airport.add(new MagicData<>(new FilterDetailData("首都国际机场", null), false, R.layout.item_text_select));
            testDatas.put(rvLeftBuilder.setData("机场").build(), airport);

            List<MagicData<FilterDetailData>> sitType = new ArrayList<>();
            sitType.add(textSelectBuilder
                    .setData(new FilterDetailData("不限", null))
                    .setBindLogic(unlimitedLogic_1)
                    .setState(true)
                    .build());
            sitType.add(boxSelectBuilder
                    .setData(new FilterDetailData("经济舱", null))
                    .build());
            sitType.add(boxSelectBuilder
                    .setData(new FilterDetailData("头等/商务舱", null))
                    .build());
            testDatas.put(rvLeftBuilder.setData("舱位").build(), sitType);


        }
        dp = getResources().getDisplayMetrics().density;

        rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_filter, this, true);

        initConditionList();
        findViews();
        initViews();
        if (isInEditMode() || BuildConfig.DEBUG) {
            setDatas(testDatas);
        }
    }

    private void initConditionList() {
        conditions = new ArrayList<MagicData<FilterDetailData>>() {
            @Override
            public boolean add(MagicData<FilterDetailData> data) {
                if (this.contains(data)) {
                    return false;
                }
                boolean add = super.add(data);
                if (rvConditions != null) {
                    MagicAdapter adapter = (MagicAdapter) rvConditions.getAdapter();
                    if (adapter != null) {
                        List adapterDatas = adapter.getDatas();
                        adapterDatas.add(data);
                        int position = adapterDatas.indexOf(data);
                        if (adapterDatas.size() == 1) {
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyItemInserted(position);
                        }
                    }
                    if (this.size() > 0 && rvConditions.getVisibility() == GONE) {
                        rvConditions.setVisibility(VISIBLE);
                    }
                }
                return add;
            }

            @Override
            public boolean remove(Object o) {
                if (!this.contains(o)) {
                    return false;
                }
                boolean remove = super.remove(o);
                if (rvConditions != null) {
                    MagicAdapter adapter = (MagicAdapter) rvConditions.getAdapter();
                    if (adapter != null) {
                        List adapterDatas = adapter.getDatas();
                        int position = adapterDatas.indexOf(o);
                        adapterDatas.remove(o);
                        adapter.notifyItemRemoved(position);
                    }
                    if (this.size() == 0 && rvConditions.getVisibility() == VISIBLE) {
                        rvConditions.setVisibility(GONE);
                    }
                }
                return remove;
            }
        };
    }

    @NonNull
    private BindDataLogic<MagicData<FilterDetailData>> initUnlimitedLogic_1() {
        return new BindDataLogic<MagicData<FilterDetailData>>() {

            @Override
            public void bindData(final RecyclerView parent, View itemView, final List<MagicData<FilterDetailData>> datas, int position, Object... state) {
                final MagicData<FilterDetailData> magicData = datas.get(position);
                FilterDetailData data = MagicData.getData(magicData);
                if (data == null) {
                    return;
                }
                MagicData<String> temp = null;
                if (state != null) {
                    for (Object o : state) {
                        if (o instanceof MagicData) {
                            temp = (MagicData<String>) o;
                            break;
                        }
                    }
                }
                final MagicData<String> type = temp;
                String content = TextUtils.isEmpty(data.getContent()) ? "" : data.getContent();
//                if (type != null) {
//                    if (magicData.isState()) {
//                        type.setVisibility(GONE);
//                    } else if (type.getVisibility() == GONE && magicData.isState()) {
//                        type.setVisibility(VISIBLE);
//                    }
//                }
                if (magicData.isState() && type != null && type.isState()) {
                    type.setState(false);
                    Runnable notifyAdapterChange = (Runnable) type.getTag();
                    if (notifyAdapterChange != null) {
                        notifyAdapterChange.run();
                    }
                }
                final TextView tvDes1 = itemView.findViewById(R.id.tv_des);
                final ImageView ivSelect1 = itemView.findViewById(R.id.iv_select);
                tvDes1.setText(content);
                if (magicData.isState()) {
                    tvDes1.setTextColor(Color.parseColor("#ef9d09"));
                    ivSelect1.setVisibility(VISIBLE);
                } else {
                    tvDes1.setTextColor(Color.parseColor("#333333"));
                    ivSelect1.setVisibility(GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (magicData.isState()) {
                            return;
                        }
                        magicData.setState(true);
                        tvDes1.setTextColor(Color.parseColor("#ef9d09"));
                        ivSelect1.setVisibility(VISIBLE);
                        if (type != null && type.isState()) {
                            type.setState(false);
                            Runnable notifyAdapterChange = (Runnable) type.getTag();
                            if (notifyAdapterChange != null) {
                                notifyAdapterChange.run();
                            }
                        }
                        for (int i = 0; i < datas.size(); i++) {
                            if (i != position) {
                                final MagicData<FilterDetailData> magicData = datas.get(i);
                                if (magicData != null && magicData.isState()) {
                                    conditions.remove(magicData);
                                    magicData.setState(false);
                                    parent.getAdapter().notifyItemChanged(i);
                                }
                            }
                        }
                    }
                });
            }
        };
    }

    @NonNull
    private BindDataLogic<MagicData<FilterDetailData>> getCheckBoxLogic() {
        return new BindDataLogic<MagicData<FilterDetailData>>() {
            @Override
            public void bindData(RecyclerView parent, View itemView, List<MagicData<FilterDetailData>> datas, int position, Object... state) {
                final MagicData<FilterDetailData> magicData = datas.get(position);
                FilterDetailData data = MagicData.getData(magicData);
                if (data == null) {
                    return;
                }
                MagicData<String> temp = null;
                if (state != null) {
                    for (Object o : state) {
                        if (o instanceof MagicData) {
                            temp = (MagicData<String>) o;
                        }
                    }
                }
                final MagicData<String> type = temp;
                magicData.setTag(type);
                String content = TextUtils.isEmpty(data.getContent()) ? "" : data.getContent();
                TextView tvDes2 = itemView.findViewById(R.id.tv_des);
                CheckBox cbBox2 = itemView.findViewById(R.id.cb_box);

                if (type != null && type.getData().equals("航空公司")) {
                    ImageView ivIcon3 = itemView.findViewById(R.id.iv_icon);
                    String image = data.getImage();
                    if (!TextUtils.isEmpty(image)) {
                        ivIcon3.setImageResource(getResources().getIdentifier(image, "drawable", getContext().getPackageName()));
                    }
                }

                tvDes2.setText(content);
                if (magicData.isState()) {
                    tvDes2.setTextColor(Color.parseColor("#ef9d09"));
                } else {
                    tvDes2.setTextColor(Color.parseColor("#333333"));
                }
                cbBox2.setChecked(magicData.isState());
                itemView.setOnClickListener(v -> {
                    magicData.setState(!magicData.isState());
                    cbBox2.setChecked(magicData.isState());
                    boolean haveSelected = false;
                    int unlimitPosition = 0;
                    for (int i = 0; i < datas.size(); i++) {
                        MagicData<FilterDetailData> tempMagicData = datas.get(i);
                        if (tempMagicData.getData().getContent().equals("不限") && tempMagicData.isState()) {
                            tempMagicData.setState(false);
                            rvRight.getAdapter().notifyItemChanged(i);
                            unlimitPosition = i;
                        } else if (tempMagicData.isState()) {
                            haveSelected = true;
                            break;
                        }
                    }
                    if (magicData.isState()) {
                        tvDes2.setTextColor(Color.parseColor("#ef9d09"));
                        if (type != null && !type.isState()) {
                            type.setState(true);
                            Runnable notifyAdpterChange = (Runnable) type.getTag();
                            if (notifyAdpterChange != null) {
                                notifyAdpterChange.run();
                            }
                        }

                        // 添加条件到条件列表中
                        conditions.add(magicData);
                    } else {
                        // 从条件列表中移除条件
                        conditions.remove(magicData);
                        tvDes2.setTextColor(Color.parseColor("#333333"));
                        if (!haveSelected) {
                            MagicData<FilterDetailData> tempMagicData = datas.get(unlimitPosition);
                            tempMagicData.setState(true);
                            rvRight.getAdapter().notifyItemChanged(unlimitPosition);
                        }
                    }
                });
            }
        };
    }

    private void findViews() {
        tvCancel = rootView.findViewById(R.id.tv_cancel);
        tvResultNum = rootView.findViewById(R.id.tv_result_num);
        tvComfire = rootView.findViewById(R.id.tv_confirm);

        rvConditions = rootView.findViewById(R.id.rv_conditions);

        cbLeft = rootView.findViewById(R.id.cb_left);
        cbRight = rootView.findViewById(R.id.cb_right);

        rvLeft = rootView.findViewById(R.id.rv_left);
        rvRight = rootView.findViewById(R.id.rv_right);
    }

    private void initViews() {
        initHeadViews();
        initRvConditions();
        initCheckBox();
        initRvLeft();
        initRvRight();
    }

    private void initHeadViews() {
        tvCancel.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onCancel();
            }
        });

        tvComfire.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onConfirm(datasClone);
            }
        });
    }

    private void initRvConditions() {
        rvConditions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        rvConditions.setItemAnimator(new DefaultItemAnimator());
        rvConditions.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int count = parent.getAdapter().getItemCount();
                if (position == 0) {
                    outRect.left = (int) (12 * dp);
                }
                if (position == count - 1) {
                    outRect.right = (int) (12 * dp);
                } else {
                    outRect.right = (int) (6 * dp);
                }
            }
        });

        MagicAdapter<MagicData<FilterDetailData>> adapter = new MagicAdapter<MagicData<FilterDetailData>>(getContext(), conditions);
        adapter.setRule(new AdapterRule<MagicData<FilterDetailData>>() {
            @Override
            public int layout(int position, List<MagicData<FilterDetailData>> datas) {
                return R.layout.item_condition;
            }

            @Override
            public void bindData(RecyclerView.Adapter adapter, View itemView, List<MagicData<FilterDetailData>> datas, int position) {
                MagicData<FilterDetailData> magicData = datas.get(position);
                ((SuperTextView) itemView).setText(magicData.getData().getContent());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        conditions.remove(magicData);
                        magicData.setState(false);

                        MagicAdapter rvRightAdapter;
                        if (rvRight != null && (rvRightAdapter = (MagicAdapter) rvRight.getAdapter()) != null
                                && rvRightAdapter.getDatas().contains(magicData)) {

                            List rvRightDatas = rvRightAdapter.getDatas();
                            int position = rvRightDatas.indexOf(magicData);
                            rvRightAdapter.notifyItemChanged(position);
                        }
                        MagicData<String> tag = (MagicData) magicData.getTag();
                        if (tag != null) {
                            List<MagicData<FilterDetailData>> magicDatas = datasClone.get(tag);
                            boolean hasSelected = false;
                            for (MagicData<FilterDetailData> temp : magicDatas) {
                                if (!temp.getData().getContent().equals("不限") && temp.isState()){
                                    hasSelected = true;
                                    break;
                                }
                            }
                            if (!hasSelected && tag.isState()){
                                tag.setState(false);
                                Runnable notifyAdapterChanged = (Runnable) tag.getTag();
                                if (notifyAdapterChanged != null){
                                    notifyAdapterChanged.run();
                                }
                            }
                            return;
                        }
                        if (magicData == cbLeft.getTag()) {
                            cbLeft.setChecked(false);
                            return;
                        }
                        if (magicData == cbRight.getTag()) {
                            cbRight.setChecked(false);
                            return;
                        }

                    }
                });
            }
        });
        rvConditions.setAdapter(adapter);
    }

    private void initCheckBox() {
        FilterDetailData cbLeftData = new FilterDetailData("有发票", null);
        MagicData cbLeftMagicData = MagicData.builder().setData(cbLeftData).build();
        FilterDetailData cbRightData = new FilterDetailData("隐藏共享航班", null);
        MagicData cbRightMagicData = MagicData.builder().setData(cbRightData).build();
        cbLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    conditions.add(cbLeftMagicData);
                } else {
                    conditions.remove(cbLeftMagicData);
                }
            }
        });
        cbLeft.setTag(cbLeftMagicData);

        cbRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    conditions.add(cbRightMagicData);
                } else {
                    conditions.remove(cbRightMagicData);
                }
            }
        });
        cbRight.setTag(cbRightMagicData);
    }

    private void initRvLeft() {
        rvLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLeft.setItemAnimator(new DefaultItemAnimator());
//        rvLeft.addItemDecoration(new ItemDecoration_Divider(0.5f * dp, Color.parseColor("#e4e4e4")));
    }

    private void initRvRight() {
        rvRight.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRight.setItemAnimator(new DefaultItemAnimator());
        rvRight.addItemDecoration(new ItemDecoration_Divider(0.5f * dp, Color.parseColor("#e0e0e0")));
    }

    @Override
    public void setDatas(Map<MagicData<String>, List<MagicData<FilterDetailData>>> datas) {
        this.datas.clear();
        this.datas.putAll(datas);
        if (this.datas != null) {
            datasClone.clear();
            Iterator<Map.Entry<MagicData<String>, List<MagicData<FilterDetailData>>>> iterator = this.datas.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<MagicData<String>, List<MagicData<FilterDetailData>>> next = iterator.next();
                List<MagicData<FilterDetailData>> valueClone = new ArrayList<>();
                for (MagicData<FilterDetailData> temp : next.getValue()) {
                    try {
                        valueClone.add(temp.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                datasClone.put(next.getKey(), valueClone);
            }
        }

        updateRV();
    }

    private void updateRV() {
        List<MagicData<String>> types = new ArrayList<>();
        if (this.datasClone != null) {
            Iterator<Map.Entry<MagicData<String>, List<MagicData<FilterDetailData>>>> iterator = this.datasClone.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<MagicData<String>, List<MagicData<FilterDetailData>>> next = iterator.next();
                types.add(next.getKey());
            }
        }

        rvLeft.setAdapter(new MagicAdapter<MagicData<String>>(getContext(), types, new AdapterRule<MagicData<String>>() {
            private boolean isFirst = true;
            private MagicData curType;


            @Override
            public int layout(int position, List<MagicData<String>> t) {
                return t.get(position).getType();
            }

            @Override
            public void bindData(RecyclerView.Adapter adapter, View itemView, List<MagicData<String>> datas, int position) {

                MagicData<String> magicData = datas.get(position);
                final String type = MagicData.getData(magicData);
                if (TextUtils.isEmpty(type)) {
                    return;
                }

                SuperTextView stvSelectState = itemView.findViewById(R.id.stv_select_state);
                TextView tvType = itemView.findViewById(R.id.tv_type);

                tvType.setText(type);
                if (magicData.isState()) {
                    stvSelectState.setVisibility(VISIBLE);
                } else {
                    stvSelectState.setVisibility(GONE);
                }
                if (magicData.getTag() == null) {
                    magicData.setTag(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemChanged(position);
                        }
                    });
                }
                if (curType == magicData) {
                    itemView.setBackgroundColor(Color.WHITE);
                }
                itemView.setOnClickListener(v -> {
                    int childCount = rvLeft.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        rvLeft.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                    curType = magicData;
                    itemView.setBackgroundColor(Color.WHITE);
                    showDetail(magicData);
                });
                if (isFirst && position == 0) {
                    isFirst = false;
                    itemView.performClick();
                }
            }
        }));
    }

    private void showDetail(final MagicData<String> type) {
        rvRight.setAdapter(new MagicAdapter<MagicData<FilterDetailData>>(getContext(), datasClone.get(type), new AdapterRule<MagicData<FilterDetailData>>() {
            @Override
            public int layout(int position, List<MagicData<FilterDetailData>> t) {
                int layoutType = t.get(position).getType();
                if (layoutType == -99) {
                    layoutType = R.layout.item_text_select;
                }
                return layoutType;
            }

            @Override
            public void bindData(RecyclerView.Adapter adapter, View itemView, List<MagicData<FilterDetailData>> datas, int position) {
                final MagicData<FilterDetailData> magicData = datas.get(position);
                FilterDetailData data = null;
                if (magicData != null) {
                    data = magicData.getData();
                }
                if (magicData != null && data != null) {
                    if (magicData.getBindLogic() != null) {
                        magicData.getBindLogic().bindData(rvRight, itemView, datas, position, type);
                        return;
                    }

                    String content = TextUtils.isEmpty(data.getContent()) ? "" : data.getContent();
//                    if (data.getContent().equals("不限") && magicData.isState()) {
//                        stvSelectState.setVisibility(GONE);
//                    } else if (stvSelectState.getVisibility() == GONE && magicData.isState()) {
//                        stvSelectState.setVisibility(VISIBLE);
//                    }
                    switch (magicData.getType()) {
                        case R.layout.item_text_select:
                            if (type.equals("舱位") && position == 0 && magicData.isState()) {
//                                stvSelectState.setVisibility(GONE);
                            }
                            TextView tvDes1 = itemView.findViewById(R.id.tv_des);
                            ImageView ivSelect1 = itemView.findViewById(R.id.iv_select);
                            tvDes1.setText(content);
                            if (magicData.isState()) {
                                tvDes1.setTextColor(Color.parseColor("#ef9d09"));
                                ivSelect1.setVisibility(VISIBLE);
                            } else {
                                tvDes1.setTextColor(Color.parseColor("#333333"));
                                ivSelect1.setVisibility(GONE);
                            }
                            itemView.setOnClickListener(v -> {
                                if (magicData.isState()) {

                                } else {
                                    magicData.setState(true);
                                    tvDes1.setTextColor(Color.parseColor("#ef9d09"));
                                    ivSelect1.setVisibility(VISIBLE);
                                    if (!type.equals("舱位")) {
//                                        checkSelect(datas, position, stvSelectState);
                                    } else {
//                                        if (position == 0) {
//                                            stvSelectState.setVisibility(GONE);
//                                        } else {
//                                            stvSelectState.setVisibility(VISIBLE);
//                                        }
                                        for (int i = 0; i < datas.size(); i++) {
                                            MagicData<FilterDetailData> temp = datas.get(i);
                                            if (temp != magicData) {
                                                temp.setState(false);
                                                rvRight.getAdapter().notifyItemChanged(i);
                                            }
                                        }
                                    }
                                }
                            });
                            break;
                        case R.layout.item_box_select:
                            TextView tvDes2 = itemView.findViewById(R.id.tv_des);
                            CheckBox cbBox2 = itemView.findViewById(R.id.cb_box);
                            tvDes2.setText(content);
                            if (magicData.isState()) {
                                tvDes2.setTextColor(Color.parseColor("#ef9d09"));
                            } else {
                                tvDes2.setTextColor(Color.parseColor("#333333"));
                            }
                            cbBox2.setChecked(magicData.isState());
                            FilterDetailData finalData1 = data;
                            itemView.setOnClickListener(v -> {
                                if (finalData1.getContent().equals("不限")) {
                                    magicData.setState(true);
                                    tvDes2.setTextColor(Color.parseColor("#ef9d09"));
                                    cbBox2.setChecked(true);
                                } else {
                                    magicData.setState(!magicData.isState());
                                    cbBox2.setChecked(magicData.isState());
                                    if (magicData.isState()) {
                                        tvDes2.setTextColor(Color.parseColor("#ef9d09"));
                                    } else {
                                        tvDes2.setTextColor(Color.parseColor("#333333"));
                                    }
                                }
//                                checkSelect(datas, position, stvSelectState);
                            });
                            break;
                        case R.layout.item_box_select_with_image:
                            TextView tvDes3 = itemView.findViewById(R.id.tv_des);
                            CheckBox cbBox3 = itemView.findViewById(R.id.cb_box);
                            ImageView ivIcon3 = itemView.findViewById(R.id.iv_icon);
                            tvDes3.setText(content);
                            if (magicData.isState()) {
                                tvDes3.setTextColor(Color.parseColor("#ef9d09"));
                            } else {
                                tvDes3.setTextColor(Color.parseColor("#333333"));
                            }
                            cbBox3.setChecked(magicData.isState());
                            String image = data.getImage();
                            if (!TextUtils.isEmpty(image)) {
                                ivIcon3.setImageResource(getResources().getIdentifier(image, "drawable", getContext().getPackageName()));
                            } else {
                                ivIcon3.setImageResource(R.drawable.yellow_question);
                            }
                            FilterDetailData finalData = data;
                            itemView.setOnClickListener(v -> {
                                if (finalData.getContent().equals("不限")) {
                                    magicData.setState(true);
                                    tvDes3.setTextColor(Color.parseColor("#ef9d09"));
                                    cbBox3.setChecked(true);
                                } else {
                                    magicData.setState(!magicData.isState());
                                    cbBox3.setChecked(magicData.isState());
                                    if (magicData.isState()) {
                                        tvDes3.setTextColor(Color.parseColor("#ef9d09"));
                                    } else {
                                        tvDes3.setTextColor(Color.parseColor("#333333"));
                                    }
                                }
//                                checkSelect(datas, position, stvSelectState);
                            });
                            break;
                    }


                }
            }
        }));
    }

    private void checkSelect(List<MagicData<FilterDetailData>> datas, int position, SuperTextView stvSelectState) {
        MagicData<FilterDetailData> magicData = datas.get(position);
        FilterDetailData data = magicData.getData();
        if (data.getContent().equals("不限")) {
            stvSelectState.setVisibility(GONE);
            for (int i = 0; i < datas.size(); i++) {
                MagicData<FilterDetailData> temp = datas.get(i);
                if (!temp.getData().getContent().equals("不限")) {
                    temp.setState(false);
                    rvRight.getAdapter().notifyItemChanged(i);
                }
            }
        } else {
            stvSelectState.setVisibility(VISIBLE);
            boolean haveSelected = false;
            for (int i = 0; i < datas.size(); i++) {
                MagicData<FilterDetailData> temp = datas.get(i);
                if (temp.getData().getContent().equals("不限")) {
                    temp.setState(false);
                    rvRight.getAdapter().notifyItemChanged(i);
                } else if (temp.isState()) {
                    haveSelected = true;
                }
            }
            if (!haveSelected) {
                for (int i = 0; i < datas.size(); i++) {
                    MagicData<FilterDetailData> temp = datas.get(i);
                    if (temp.getData().getContent().equals("不限")) {
                        temp.setState(true);
                        rvRight.getAdapter().notifyItemChanged(i);
                    }
                }
            }
        }
    }

    @Override
    public void setOnClickListener(IFilter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
