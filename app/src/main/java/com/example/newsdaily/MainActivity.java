package com.example.newsdaily;

import NewsUI.SearchBar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.andy.library.*;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //娱乐、军事、教育、文化、健康、财经、体育、汽车、科技、社会
    String[] categoriesList = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};
    ArrayList<ChannelBean> channelBeans;
    ArrayList<ChannelBean> curChannels;
    ArrayList<NewsFragment> newsFragments;
    SearchBar searchBar;
    TabLayout tagBar;
    ViewPager viewPager;
    String keyWords = "";
    NewsFragment curFragment = null;
    View channelManageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ReconstructDatabase(this);

        tagBar = (TabLayout) findViewById(R.id.news_tab_layout);
        viewPager = findViewById(R.id.news_view_pager);
        channelManageBtn = findViewById(R.id.moreChannelBtn);
        newsFragments = new ArrayList<>();

        channelBeans = new ArrayList<>();
        curChannels = new ArrayList<>();
        for (String channel : categoriesList) {
            ChannelBean bean = new ChannelBean(channel, true);
            channelBeans.add(bean);
            curChannels.add(bean);
            newsFragments.add(new NewsFragment(channel));
        }

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), newsFragments));

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
                    if (curFragment != null)
                        curFragment.refreshData();
                    searchBar.clearFocus();
                }
                return false;
            }
        });

        channelManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.startChannelForResult(MainActivity.this, channelBeans);

            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            String jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);

            Gson gson = new Gson();
            channelBeans = gson.fromJson(jsonStr, new TypeToken<ArrayList<ChannelBean>>(){}.getType());

            for (NewsFragment fragment : newsFragments) {
                getSupportFragmentManager().openTransaction().remove(fragment);
            }
            getSupportFragmentManager().openTransaction().commit();

            newsFragments.clear();
            curChannels.clear();
            for (ChannelBean channel : channelBeans) {
                if (channel.isSelect()) {
                    newsFragments.add(new NewsFragment(channel.getName()));
                    curChannels.add(channel);
                }
            }
            viewPager.getAdapter().notifyDataSetChanged();

            tagBar.setupWithViewPager(viewPager);


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



    public static void ReconstructDatabase(Context applicationContext){
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(applicationContext);
        schemaGenerator.deleteTables(new SugarDb(applicationContext).getDB());
        SugarContext.init(applicationContext);
        schemaGenerator.createDatabase(new SugarDb(applicationContext).getDB());
    }

}