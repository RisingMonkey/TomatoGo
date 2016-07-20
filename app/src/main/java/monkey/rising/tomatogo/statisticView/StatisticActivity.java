package monkey.rising.tomatogo.statisticView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TabWidget;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.R;

public class StatisticActivity extends AppCompatActivity {
    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(R.id.tabHost)
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.inject(this);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("分类统计").setContent(R.id.linearLayout));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("日常统计").setContent(R.id.linearLayout2));

    }
}
