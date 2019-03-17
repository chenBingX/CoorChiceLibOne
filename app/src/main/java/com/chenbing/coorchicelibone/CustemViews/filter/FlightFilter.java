package com.chenbing.coorchicelibone.CustemViews.filter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
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
    private MagicAdapter rvRightAdapter;


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

        dp = getResources().getDisplayMetrics().density;
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_filter, this, true);
        initConditionList();
        findViews();
        initViews();

        if (isInEditMode() || BuildConfig.DEBUG) {
            testDatas = new TestDataCreator().createFlightFilterTestDatas(conditions);
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

                        if (magicData == cbLeft.getTag()) {
                            cbLeft.setChecked(false);
                            return;
                        }
                        if (magicData == cbRight.getTag()) {
                            cbRight.setChecked(false);
                            return;
                        }

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
                            int unlimitedPosition = -1;
                            int unlimitedPosition2 = -1;
                            for (int i = 0; i < magicDatas.size(); i++) {
                                MagicData<FilterDetailData> tempMagicData = magicDatas.get(i);
                                if (tempMagicData.getData().getContent().equals("不限")) {
                                    String image = tempMagicData.getData().getImage();
                                    if (!TextUtils.isEmpty(image)) {
                                        if (unlimitedPosition == -1) {
                                            unlimitedPosition = i;
                                        } else if (unlimitedPosition2 == -1) {
                                            unlimitedPosition2 = i;
                                        }
                                        if (image.equals(magicData.getData().getImage())) {
                                            unlimitedPosition = i;
                                        }
                                    } else {
                                        unlimitedPosition = i;
                                    }

                                } else if (tempMagicData.isState()) {
                                    hasSelected = true;
                                }
                            }

                            if (!hasSelected && tag.isState()) {
                                tag.setState(false);
                                Runnable notifyAdapterChanged = (Runnable) tag.getTag();
                                if (notifyAdapterChanged != null) {
                                    notifyAdapterChanged.run();
                                }

                                if (unlimitedPosition != -1) {
                                    MagicData<FilterDetailData> tempMagicData = magicDatas.get(unlimitedPosition);
                                    tempMagicData.setState(true);
                                    if (rvRight != null && rvRight.getAdapter() != null) {
                                        rvRight.getAdapter().notifyItemChanged(unlimitedPosition);
                                    }
                                }
                            } else if (unlimitedPosition != -1 && unlimitedPosition2 != -1) {
                                MagicData<FilterDetailData> tempMagicData = magicDatas.get(unlimitedPosition);
                                tempMagicData.setState(true);
                                if (rvRight != null && rvRight.getAdapter() != null) {
                                    rvRight.getAdapter().notifyItemChanged(unlimitedPosition);
                                }
                            }


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

        Map<MagicData<String>, List<MagicData<FilterDetailData>>> packagingDatas = packagingData(datas);

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

    /**
     * 将数据包装成统一格式。
     *
     * @param datas
     * @return
     */
    private Map<MagicData<String>, List<MagicData<FilterDetailData>>> packagingData(Map<MagicData<String>, List<MagicData<FilterDetailData>>> datas) {
        return null;
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
                } else {
                    itemView.setBackgroundColor(Color.TRANSPARENT);
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

    private MagicData<String> curType;

    private void showDetail(final MagicData<String> type) {
        curType = type;
        if (rvRightAdapter == null) {
            rvRightAdapter = new MagicAdapter<>(getContext(), datasClone.get(type), new AdapterRule<MagicData<FilterDetailData>>() {
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
                    FilterDetailData data = MagicData.getData(magicData);
                    if (data != null) {
                        if (magicData.getBindLogic() != null) {
                            magicData.getBindLogic().bindData(rvRight, itemView, datas, position, curType);
                        }
                    }
                }
            });
            rvRight.setAdapter(rvRightAdapter);
        } else {
            rvRightAdapter.setDatas(datasClone.get(type));
        }
    }

    @Override
    public void setOnClickListener(IFilter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
