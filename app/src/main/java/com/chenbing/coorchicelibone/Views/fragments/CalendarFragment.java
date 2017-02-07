package com.chenbing.coorchicelibone.Views.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.chenbing.iceweather.R;
import com.chenbing.coorchicelibone.AdaptersAndItemViews.BaseItemViewHolder;
import com.chenbing.coorchicelibone.Views.BaseView.BaseFragment;
import com.chenbing.coorchicelibone.Views.BaseView.BaseRelativeLayout;
import com.chenbing.coorchicelibone.interfaces.ItemViewSetDataFunction;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Project Name:IceWeather
 * Author:CoorChice
 * Date:2017/2/4
 * Notes:
 */

public class CalendarFragment extends BaseFragment {
  public static final String KEY_DATE = "key_date";

  @BindView(R.id.header)
  TextView tvHeader;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private View rootView;
  private List<DateInfo> dateList = new ArrayList<>();
  private Date initialDate;

  public static Fragment getInstance(Date currentDate) {
    Fragment instance = new CalendarFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(KEY_DATE, currentDate);
    instance.setArguments(bundle);
    return instance;
  };

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_calendar_pager, container, false);
    ButterKnife.bind(this, rootView);
    initData();
    initView();
    addListener();
    return rootView;
  }

  @Override
  protected void initData() {
    dateList.clear();

    initialDate = (Date) getArguments().getSerializable(KEY_DATE);  //获取传过来的Date，用于确定初始的calendar
    Calendar calendar = Calendar.getInstance(Locale.CHINA); //获取China区Calendar实例，实际是GregorianCalendar的一个实例
    calendar.setTime(initialDate); //初始化日期
    int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  //获得当前日期所在月份有多少天（或者说day的最大值)，用于后面的计算

    Calendar calendarClone = (Calendar) calendar.clone(); //克隆一个Calendar再进行操作，避免造成混乱
    calendarClone.set(Calendar.DAY_OF_MONTH, 1);  //将日期调到当前月份的第一天
    int startDayOfWeek = calendarClone.get(Calendar.DAY_OF_WEEK); //获得当前日期所在月份的第一天是星期几
    calendarClone.set(Calendar.DAY_OF_MONTH, maxDay); //将日期调到当前月份的最后一天
    int endDayOfWeek = calendarClone.get(Calendar.DAY_OF_WEEK); //获得当前日期所在月份的最后一天是星期几

    /**
     * 计算上一个月在本月日历页出现的那几天.
     * 比如，startDayOfWeek = 3，表示当月第一天是星期二，所以日历向前会空出2天的位置，那么让上月的最后两天显示在星期日和星期一的位置上.
     */
    int startEmptyCount = startDayOfWeek - 1; //上月在本月日历页因该出现的天数。
    Calendar preCalendar = (Calendar) calendar.clone();  //克隆一份再操作
    preCalendar.set(Calendar.DAY_OF_MONTH, 1); //将日期调到当月第一天
    preCalendar.add(Calendar.DAY_OF_MONTH, -startEmptyCount); //向前推移startEmptyCount天
    for (int i = 0; i < startEmptyCount; i++) {
      DateInfo dateInfo = new DateInfo(); //使用DateInfo来储存所需的相关信息
      dateInfo.setDate(preCalendar.getTime());
      dateInfo.setType(DateInfo.PRE_MONTH); //标记日期信息的类型为上个月
      dateList.add(dateInfo); //将日期添加到数组中
      preCalendar.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
    }

    /**
     * 计算当月的每一天日期
     */
    calendar.set(Calendar.DAY_OF_MONTH, 1); //由于是获取当月日期信息，所以直接操作当月Calendar即可。将日期调为当月第一天
    for (int i = 0; i < maxDay; i++) {
      DateInfo dateInfo = new DateInfo();
      dateInfo.setDate(calendar.getTime());
      dateInfo.setType(DateInfo.CURRENT_MONTH);  //标记日期信息的类型为当月
      dateList.add(dateInfo);
      calendar.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
    }

    /**
     * 计算下月在本月日历页出现的那几天。
     * 比如，endDayOfWeek = 6，表示当月第二天是星期五，所以日历向后会空出1天的位置，那么让下月的第一天显示在星期六的位置上。
     */
    int endEmptyCount = 7 - endDayOfWeek; //下月在本月日历页上因该出现的天数
    Calendar afterCalendar = (Calendar) calendar.clone(); //同样，克隆一份在操作
    for (int i = 0; i < endEmptyCount; i++) {
      DateInfo dateInfo = new DateInfo();
      dateInfo.setDate(afterCalendar.getTime());
      dateInfo.setType(DateInfo.AFTER_MONTH); //将DateInfo类型标记为下个月
      dateList.add(dateInfo);
      afterCalendar.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
    }
  }

  @Override
  protected void initView() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
    tvHeader.setText(dateFormat.format(initialDate));
    initRecyclerView();
  }

  private void initRecyclerView() {
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    CalendarRvAdapter adapter = new CalendarRvAdapter(getActivity(), dateList);
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void addListener() {

  }

  private static class CalendarRvAdapter extends RecyclerView.Adapter {
    private List<DateInfo> dateList = new ArrayList<>();
    private Context context;

    private CalendarRvAdapter(Context context, List<DateInfo> dateList) {
      this.context = context;
      this.dateList = dateList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new BaseItemViewHolder(new CalendarCell(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      if (position >= 7) {
        ((ItemViewSetDataFunction) (holder.itemView)).setData(dateList.get(position - 7));
      } else {
        DateInfo dateInfo = new DateInfo();
        dateInfo.setType(DateInfo.WEEK_TITLE);
        dateInfo.setWeekTitle("星期" + getWeekSuffix(position));

        ((ItemViewSetDataFunction) (holder.itemView)).setData(dateInfo);
      }

    }

    private String getWeekSuffix(int position) {
      String suffix = "";
      switch (position) {
        case 0:
          suffix = "日";
          break;
        case 1:
          suffix = "一";
          break;
        case 2:
          suffix = "二";
          break;
        case 3:
          suffix = "三";
          break;
        case 4:
          suffix = "四";
          break;
        case 5:
          suffix = "五";
          break;
        case 6:
          suffix = "六";
          break;

      }

      return suffix;
    }

    @Override
    public int getItemCount() {
      return dateList.size() + 7;
    }
  }

  public static class CalendarCell extends BaseRelativeLayout implements ItemViewSetDataFunction {
    @BindView(R.id.day)
    TextView tvDay;


    public CalendarCell(Context context) {
      super(context);
    }

    public CalendarCell(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    public CalendarCell(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayout() {
      return R.layout.item_calendar_cell;
    }

    @Override
    public <T> void setData(T t) {
      DateInfo dateInfo = (DateInfo) t;
      if (dateInfo.getType() == DateInfo.PRE_MONTH || dateInfo.getType() == DateInfo.AFTER_MONTH){
        tvDay.setBackgroundColor(getResources().getColor(R.color.gray_f2));
      }

      if (dateInfo.getType() != DateInfo.WEEK_TITLE){
        tvDay.setText("" + dateInfo.getDate().getDate());
      } else {
        tvDay.setGravity(Gravity.CENTER);
        tvDay.setText(dateInfo.getWeekTitle());
      }
    }
  }

  private static class DateInfo {
    private static final int PRE_MONTH = 1;
    private static final int CURRENT_MONTH = PRE_MONTH + 1;
    private static final int AFTER_MONTH = CURRENT_MONTH + 1;
    private static final int WEEK_TITLE = AFTER_MONTH + 1;

    private Date date;
    private int type;
    private String weekTitle;

    public Date getDate() {
      return date;
    }

    public void setDate(Date date) {
      this.date = date;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getWeekTitle() {
      return weekTitle;
    }

    public void setWeekTitle(String weekTitle) {
      this.weekTitle = weekTitle;
    }
  }
}
