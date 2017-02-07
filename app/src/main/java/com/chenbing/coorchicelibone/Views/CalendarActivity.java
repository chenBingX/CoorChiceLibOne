package com.chenbing.coorchicelibone.Views;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.chenbing.coorchicelibone.AdaptersAndItemViews.Adapters.AppFragmentPagerAdapter.FragmentPagerAdapter;
import com.chenbing.coorchicelibone.Views.BaseView.BaseActivity;
import com.chenbing.coorchicelibone.Views.fragments.CalendarFragment;
import com.chenbing.iceweather.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends BaseActivity {
  @BindView(R.id.calendar_view_pager)
  ViewPager vpCalendar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calendar);
    ButterKnife.bind(this);
    initData();
    initView();
    addListener();
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void initView() {
    initVpCalendar();

  }

  private void initVpCalendar() {
    CalendarViewPagerAdapter vpAdapter = new CalendarViewPagerAdapter(getFragmentManager());
    vpCalendar.setAdapter(vpAdapter);
    vpCalendar.setOffscreenPageLimit(3);
    vpCalendar.setCurrentItem(501);
  }

  @Override
  protected void addListener() {

  }

  private static class CalendarViewPagerAdapter extends FragmentPagerAdapter {
    private Calendar currentCalendar = Calendar.getInstance(Locale.CHINA);
    public CalendarViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return CalendarFragment.getInstance(computeDate(position));
    }

    @Override
    public int getCount() {
      return 1001;
    }

    @Override
    public int getItemPosition(Object object) {
      return super.getItemPosition(object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
      super.finishUpdate(container);
    }

    public Date computeDate(int position){
      Calendar calendar = (Calendar) currentCalendar.clone();
      int offset = position - 501;
      calendar.add(Calendar.MONTH, offset);
      return calendar.getTime();
    }
  }
}
