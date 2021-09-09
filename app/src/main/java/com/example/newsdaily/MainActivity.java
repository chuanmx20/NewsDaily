package com.example.newsdaily;

import NewsUI.SearchBar;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.andy.library.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ReconstructDatabase(this);

        fragments.add(new MainFragment());
        fragments.add(new SetupFragment());

        ViewPager contentViewPager = findViewById(R.id.content_view_pager);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        contentViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        contentViewPager.setOffscreenPageLimit(2);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_main:
                        contentViewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_setup:
                        contentViewPager.setCurrentItem(1);
                        break;
                }
                return false;
            }
        });
        contentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                bottomNav.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


    public static void ReconstructDatabase(Context applicationContext){
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(applicationContext);
        schemaGenerator.deleteTables(new SugarDb(applicationContext).getDB());
        SugarContext.init(applicationContext);
        schemaGenerator.createDatabase(new SugarDb(applicationContext).getDB());
    }


    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}