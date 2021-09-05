package com.example.newsdaily;

import NewsUI.NewsBoxData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapter extends ArrayAdapter<NewsBoxData> {
    private Context mContext;
    private int mResource;
    /**
     * Constructor. This constructor will result in the underlying data collection being
     * immutable, so methods such as {@link #clear()} will throw an exception.
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public MyAdapter(@NonNull Context context, int resource, @NonNull List<NewsBoxData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

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

            LoadImage loadImage = new LoadImage(imageView);
            loadImage.execute(getItem(position).getCoverImgURL()[0]);
            title.setText(getItem(position).getTitle());
            des.setText(getItem(position).getDescription());
            pub.setText(getItem(position).getPublisher());
        } else {
            TextView title = convertView.findViewById(R.id.Title);
            TextView des = convertView.findViewById(R.id.Description);
            TextView pub = convertView.findViewById(R.id.publisher);
            convertView.findViewById(R.id.coverImg).setVisibility(View.GONE);

            title.setWidth(parent.getWidth());
            des.setWidth(parent.getWidth());
            pub.setWidth(parent.getWidth());

            title.setText(getItem(position).getTitle());
            des.setText(getItem(position).getDescription());
            pub.setText(getItem(position).getPublisher());
        }

        return convertView;
    }
}
