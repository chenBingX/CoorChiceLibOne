package com.chenbing.coorchicelibone.CustemViews.filter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenbing.iceweather.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.*;

/**
 * Created by coorchice on 2017/11/20.
 */

public class TestDataCreator {

    public Map<MagicData<String>, List<MagicData<FilterDetailData>>> createFlightFilterTestDatas(List<MagicData<FilterDetailData>> conditions){
        Map<MagicData<String>, List<MagicData<FilterDetailData>>> testDatas = new LinkedHashMap<>();

        BindDataLogic<MagicData<FilterDetailData>> unlimitedLogic_1 = initUnlimitedLogic_1(conditions);
        BindDataLogic<MagicData<FilterDetailData>> checkBoxLogic = initCheckBoxLogic(conditions);
        BindDataLogic<MagicData<FilterDetailData>> airportLogic = initAirportLogic(conditions);
        BindDataLogic<MagicData<FilterDetailData>> airportCityLogic = initAirportCityLogic(conditions);

        // 初始化公用的Builder模版，用于创建统一的数据类型MagicData
        MagicData.Builder rvLeftBuilder = MagicData.templateBuilder(R.layout.item_type);
        MagicData.Builder textSelectBuilder = MagicData.templateBuilder(R.layout.item_text_select);
        MagicData.Builder boxSelectBuilder = MagicData.templateBuilder(R.layout.item_box_select, checkBoxLogic);
        MagicData.Builder boxSelectWithImageBuilder = MagicData.templateBuilder(R.layout.item_box_select_with_image, checkBoxLogic);
        MagicData.Builder airportBuilder = MagicData.templateBuilder(R.layout.item_text_select, airportLogic);
        MagicData.Builder airportCityBuilder = MagicData.templateBuilder(R.layout.item_airport_city, airportCityLogic);

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
        testDatas.put(rvLeftBuilder.setData("机型").build(), airplaneType);

        List<MagicData<FilterDetailData>> airport = new ArrayList<>();
        airport.add(airportCityBuilder
                .setData(new FilterDetailData("北京", "city"))
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("不限", "北京"))
                .setState(true)
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("南苑机场", "北京"))
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("首都国际机场", "北京"))
                .build());
        airport.add(airportCityBuilder
                .setData(new FilterDetailData("上海", "city"))
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("不限", "上海"))
                .setState(true)
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("浦东国际机场", "上海"))
                .build());
        airport.add(airportBuilder
                .setData(new FilterDetailData("虹桥国际机场", "上海"))
                .build());
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

        return testDatas;
    }

    private BindDataLogic<MagicData<FilterDetailData>> initUnlimitedLogic_1(List<MagicData<FilterDetailData>> conditions) {
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

    private BindDataLogic<MagicData<FilterDetailData>> initCheckBoxLogic(List<MagicData<FilterDetailData>> conditions) {
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
                        ivIcon3.setImageResource(ivIcon3.getResources().getIdentifier(image, "drawable", ivIcon3.getContext().getPackageName()));
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
                            parent.getAdapter().notifyItemChanged(i);
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
                            Runnable notifyAdapterChange = (Runnable) type.getTag();
                            if (notifyAdapterChange != null) {
                                notifyAdapterChange.run();
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
                            parent.getAdapter().notifyItemChanged(unlimitPosition);
                        }
                    }
                });
            }
        };


    }

    private BindDataLogic<MagicData<FilterDetailData>> initAirportLogic(List<MagicData<FilterDetailData>> conditions) {
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
                            break;
                        }
                    }
                }
                final MagicData<String> type = temp;
                magicData.setTag(type);
                String content = TextUtils.isEmpty(data.getContent()) ? "" : data.getContent();
                final TextView tvDes = itemView.findViewById(R.id.tv_des);
                final ImageView ivSelect = itemView.findViewById(R.id.iv_select);

                tvDes.setText(content);
                if (magicData.isState()) {
                    tvDes.setTextColor(Color.parseColor("#ef9d09"));
                    ivSelect.setVisibility(VISIBLE);
                } else {
                    tvDes.setTextColor(Color.parseColor("#333333"));
                    ivSelect.setVisibility(GONE);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (magicData.isState()) {
                            return;
                        }
                        tvDes.setTextColor(Color.parseColor("#ef9d09"));
                        ivSelect.setVisibility(VISIBLE);
                        magicData.setState(true);
                        if (magicData.isState() && !magicData.getData().getContent().equals("不限")) {
                            conditions.add(magicData);
                        }

                        String city = "";
                        if (magicData != null) {
                            city = magicData.getData().getImage();
                        }

                        int unlimitSelectCount = 0;
                        for (int i = 0; i < datas.size(); i++) {
                            final MagicData<FilterDetailData> tempMagicData = datas.get(i);
                            if (tempMagicData.getData().getImage() != null && tempMagicData != magicData && (tempMagicData.getData().getImage()).equals(city)) {
                                tempMagicData.setState(false);
                                parent.getAdapter().notifyItemChanged(i);
                                conditions.remove(tempMagicData);
                            }
                            if (tempMagicData.isState() && tempMagicData.getData().getContent().equals("不限")) {
                                unlimitSelectCount++;
                            }
                        }

                        if (unlimitSelectCount == 2 && type != null && type.isState()) {
                            type.setState(false);
                            Runnable notifyAdapterChange = (Runnable) type.getTag();
                            if (notifyAdapterChange != null) {
                                notifyAdapterChange.run();
                            }
                        } else if (type != null && !type.isState()) {
                            type.setState(true);
                            Runnable notifyAdapterChange = (Runnable) type.getTag();
                            if (notifyAdapterChange != null) {
                                notifyAdapterChange.run();
                            }
                        }
                    }
                });
            }
        };
    }

    private BindDataLogic<MagicData<FilterDetailData>> initAirportCityLogic(List<MagicData<FilterDetailData>> conditions) {
        return new BindDataLogic<MagicData<FilterDetailData>>() {
            @Override
            public void bindData(RecyclerView parent, View itemView, List<MagicData<FilterDetailData>> datas, int position, Object... state) {
                ((TextView) itemView).setText(datas.get(position).getData().getContent());
            }
        };
    }

}
