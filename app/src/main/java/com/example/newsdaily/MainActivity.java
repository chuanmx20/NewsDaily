package com.example.newsdaily;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import NewsUI.NewsBoxData;

import java.net.*;
import java.io.*;

import jsonBean.DataItem;
import jsonBean.Response;
import NewsUI.*;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    //娱乐、军事、教育、文化、健康、财经、体育、汽车、科技、社会
    String[] categoriesList = {"娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};
    ArrayList<NewsFragment> newsFragments;
    SearchBar searchBar;
    TabLayout tagBar;
    ViewPager viewPager;
    String keyWords = "科技";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagBar = (TabLayout) findViewById(R.id.news_tab_layout);
        viewPager = findViewById(R.id.news_view_pager);
        newsFragments = new ArrayList<>();

        //初始化碎片实例
        for (String category : categoriesList) {
            NewsFragment newsFragment = new NewsFragment(category);
            newsFragments.add(newsFragment);
        }

        newsFragments.get(0).refreshData();

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        tagBar.setupWithViewPager(viewPager);

        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN && !searchBar.getText().toString().isEmpty())
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow((IBinder) getWindow().getDecorView().getWindowToken(), 0);
                    keyWords = searchBar.getText().toString();
                    for (NewsFragment newsFragment : newsFragments) {
                        newsFragment.setKeyWords(keyWords);
                    }
                    viewPager.getAdapter().notifyDataSetChanged();
                    searchBar.clearFocus();
                }
                return false;
            }
        });
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        NewsFragment curFragment = null;

        public NewsFragment getCurFragment() {
            return curFragment;
        }

        public FragmentAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return newsFragments.get(position);
        }

        @Override
        public int getCount() {
            return newsFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return categoriesList[position];
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            curFragment = (NewsFragment) object;
            curFragment.refreshData();
            super.setPrimaryItem(container, position, object);
        }
    }
}