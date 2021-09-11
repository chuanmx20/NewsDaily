package com.NewsDaily.chuanmingxi;

import NewsUI.Collection;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.andy.library.*;
import com.example.chuanmingxi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public static void refreshHistoryList() {
        ((HistoryListFragment) fragments.get(1)).refreshList();
    }
    public static void refreshCollectionList() {
        ((CollectionListFragment) fragments.get(2)).refreshList();
    }
    public static void refreshMainList() {
        ((MainFragment) fragments.get(0)).refreshList();
    }
    public static void refreshAllList() {
        refreshCollectionList();
        refreshMainList();
        refreshHistoryList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.chuanmingxi.R.layout.activity_main);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ReconstructDatabase(AppApplication.getApp());
//        SugarContext.init(AppApplication.getApp());

        fragments.add(new MainFragment());
        fragments.add(new HistoryListFragment());
        fragments.add(new CollectionListFragment());

        ViewPager contentViewPager = findViewById(com.example.chuanmingxi.R.id.content_view_pager);
        BottomNavigationView bottomNav = findViewById(com.example.chuanmingxi.R.id.bottom_nav);

        contentViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        contentViewPager.setOffscreenPageLimit(2);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case com.example.chuanmingxi.R.id.nav_main:
                        contentViewPager.setCurrentItem(0);
                        break;
                    case com.example.chuanmingxi.R.id.nav_history:
                        contentViewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_collection:
                        contentViewPager.setCurrentItem(2);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((MainFragment) fragments.get(0)).onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            String url = data.getStringExtra("url");
            boolean col = data.getBooleanExtra("collect", false);

            Collection.cleanCollect(url);
            if (col) {
                new Collection(url).save();
            }
            refreshCollectionList();
        }
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