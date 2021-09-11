package com.NewsDaily.chuanmingxi;

import NewsUI.Collection;
import NewsUI.NewsBoxData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebStorage;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.chuanmingxi.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryListFragment extends Fragment {
    ArrayList<NewsBoxData> historyData;
    SwipeRefreshLayout clearSwiper;
    public ListView historyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        historyList = view.findViewById(R.id.history_box_list);
        clearSwiper = view.findViewById(R.id.clear_swiper);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        historyData = (ArrayList<NewsBoxData>) NewsBoxData.listAll(NewsBoxData.class);
        historyList.setAdapter(new HistoryBoxAdapter(getContext(), R.layout.news_box, historyData));
        clearSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsBoxData.deleteAll(NewsBoxData.class);
                Collection.deleteAll(Collection.class);
                MainActivity.refreshAllList();
                clearSwiper.setRefreshing(false);
            }
        });
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //页面跳转
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("url", historyData.get(position).getDetailUrl());
                intent.putExtra("collect", Collection.inCollection(historyData.get(position).getDetailUrl()));

                historyData.get(position).save();
                refreshList();
                historyList.invalidate();
                startActivityForResult(intent, 2);
            }
        });
    }

    public void refreshList() {
        historyData = (ArrayList<NewsBoxData>) NewsBoxData.listAll(NewsBoxData.class);
        historyList.setAdapter(new HistoryBoxAdapter(getContext(), R.layout.news_box, historyData));
    }

    static class HistoryBoxAdapter extends ArrayAdapter<NewsBoxData> {
        private Context mContext;
        private int mResource;
        List<NewsBoxData> historyList;

        public HistoryBoxAdapter(@NonNull Context context, int resource, @NonNull List<NewsBoxData> objects) {
            super(context, resource, objects);
            this.mContext = context;
            this.mResource = resource;
            historyList = objects;
        }

        @Nullable
        @Override
        public NewsBoxData getItem(int position) {
            return historyList.get(position);
        }

        @SuppressLint("ViewHolder")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater  = LayoutInflater.from(mContext);

            convertView = layoutInflater.inflate(mResource, parent, false);

            if (getItem(position).hasImg()) {
                ImageView imageView = convertView.findViewById(R.id.coverImg);
                TextView title = convertView.findViewById(R.id.Title);
                TextView des = convertView.findViewById(R.id.Description);
                TextView pub = convertView.findViewById(R.id.publisher);
                TextView pubTime = convertView.findViewById(R.id.publishTime);

                LoadImage loadImage = new LoadImage(imageView);
                loadImage.execute(getItem(position).getCoverImgURL()[0]);
                title.setText(getItem(position).getTitle());
                des.setText(getItem(position).getDescription());
                pub.setText(getItem(position).getPublisher());
                pubTime.setText(getItem(position).getPublishTime());
            } else {
                TextView title = convertView.findViewById(R.id.Title);
                TextView des = convertView.findViewById(R.id.Description);
                TextView pub = convertView.findViewById(R.id.publisher);
                convertView.findViewById(R.id.coverImg).setVisibility(View.GONE);
                TextView pubTime = convertView.findViewById(R.id.publishTime);

                title.setWidth(parent.getWidth());
                des.setWidth(parent.getWidth());
                pub.setWidth(parent.getWidth());

                title.setText(getItem(position).getTitle());
                des.setText(getItem(position).getDescription());
                pub.setText(getItem(position).getPublisher());
                pubTime.setText(getItem(position).getPublishTime());
            }

            return convertView;
        }
    }

}
