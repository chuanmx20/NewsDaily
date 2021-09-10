package com.example.newsdaily;

import NewsUI.SearchBar;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainFragment extends Fragment {
    public static String[] categoriesList = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};
    public static ArrayList<ChannelBean> channelBeans;
    ArrayList<ChannelBean> curChannels;
    ArrayList<NewsFragment> newsFragments;
    SearchBar searchBar;
    TabLayout tagBar;
    ViewPager viewPager;
    String keyWords = "";
    NewsFragment curFragment = new NewsFragment();
    View channelManageBtn;

    @SuppressLint("SimpleDateFormat")
    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    TextView startDate;
    String startDateString;
    TextView endDate;
    String endDateString;
    DatePickerDialog datePickerDialog;
    int curEditingDate = 0; //1 for start and 2 for end

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tagBar = (TabLayout) view.findViewById(R.id.news_tab_layout);
        viewPager = view.findViewById(R.id.news_view_pager);
        channelManageBtn = view.findViewById(R.id.moreChannelBtn);
        searchBar = view.findViewById(R.id.searchBar);

        startDate = view.findViewById(R.id.start_date);
        endDate = view.findViewById(R.id.end_date);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsFragments = new ArrayList<>();

        channelBeans = new ArrayList<>();
        curChannels = new ArrayList<>();
        for (String channel : categoriesList) {
            ChannelBean bean = new ChannelBean(channel, true);
            channelBeans.add(bean);
            curChannels.add(bean);
            newsFragments.add(new NewsFragment(channel));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), newsFragments));

        tagBar.setupWithViewPager(viewPager);


        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN && !searchBar.getText().toString().isEmpty())
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((IBinder) getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    keyWords = searchBar.getText().toString();

                    refreshNewsByDataChange();

                    searchBar.clearFocus();
                }
                return false;
            }
        });

        channelManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.startChannelForResult(getActivity(), channelBeans);

            }
        });

        initRetrieval();
    }

    private void initRetrieval() {

        startDateString = curFragment.getStartDate();

        endDateString = curFragment.getEndDate();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = makeDateString(year, month, dayOfMonth);
                try {
                    setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };



        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(getActivity(), style);
        datePickerDialog.setOnDateSetListener(dateSetListener);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curEditingDate = 1;
                try {
                    Date start_date = format.parse(startDateString);
                    datePickerDialog.getDatePicker().updateDate(start_date.getYear() + 1900, start_date.getMonth(), start_date.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curEditingDate = 2;
                try {
                    Date end_date = format.parse(endDateString);
                    datePickerDialog.getDatePicker().updateDate(end_date.getYear() + 1900, end_date.getMonth(), end_date.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();
            }
        });

    }

    public static String getCurDate() {
        return format.format(System.currentTimeMillis());
    }

    private void setTime(String date) throws ParseException {
        switch (curEditingDate) {
            case 0 :
                return;
            case 1:
                if (format.parse(date).compareTo(format.parse(endDateString)) >= 0) {
                    Toast.makeText(getActivity().getBaseContext(),"开始日期不能在截止日期后哦", Toast.LENGTH_LONG).show();
                    curEditingDate = 0;
                    return;
                }
                startDate.setText(date);
                startDateString = date;
                break;
            case 2:
                if (format.parse(date).compareTo(format.parse(startDateString)) <= 0) {
                    Toast.makeText(getActivity().getBaseContext(),"截止日期不能在开始日期之前哦", Toast.LENGTH_LONG).show();
                    curEditingDate = 0;
                    return;
                }
                endDate.setText(date);
                endDateString = date;
                break;
        }
        curEditingDate = 0;
        refreshNewsByDataChange();
    }

    private void refreshNewsByDataChange() {
        newsFragments.clear();
        curChannels.clear();
        for (ChannelBean channel : channelBeans) {
            if (channel.isSelect()) {
                newsFragments.add(new NewsFragment(channel.getName(), startDateString, endDateString, keyWords));
                curChannels.add(channel);
            }
        }
        viewPager.getAdapter().notifyDataSetChanged();
        tagBar.setupWithViewPager(viewPager);
    }

    private String makeDateString(int year, int month, int dayOfMonth) {
        //傻逼google为了支持calendar，不让用户构造date，年份都加了1900
        //真tm想干啥干啥，日
        Date date = new Date(year-1900, month, dayOfMonth);
        return format.format(date);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            String jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);

            Gson gson = new Gson();
            channelBeans = gson.fromJson(jsonStr, new TypeToken<ArrayList<ChannelBean>>(){}.getType());

            refreshNewsByDataChange();
        }


    }

    class FragmentAdapter extends FragmentPagerAdapter {
        ArrayList<NewsFragment> mNewsFragment;

        public FragmentAdapter(FragmentManager fm, ArrayList<NewsFragment> newsFragments) {
            super(fm);
            mNewsFragment = newsFragments;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mNewsFragment.get(position);
        }

        @Override
        public int getCount() {
            return mNewsFragment.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String ret =  mNewsFragment.get(position).getCategories();

            if (ret.equals("")) {
                return "全部";
            }
            return ret;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            curFragment = (NewsFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public long getItemId(int position) {
            return mNewsFragment.get(position).hashCode();
        }
    }
}
