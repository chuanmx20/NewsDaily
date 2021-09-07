package ChannelManagement;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import com.andy.library.*;

public class ChannelManagementActivity extends AppCompatActivity {
    ArrayList<String> channelList;
    TabLayout mTab;
    Button mBtn;
    ArrayList<ChannelBean> channelBeans;
    String stringExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void initData() {
        channelBeans = new ArrayList<>();
        for (String channel : channelList) {
            channelBeans.add(new ChannelBean(channel, true));
        }
    }

    void initView() {
    }

}
