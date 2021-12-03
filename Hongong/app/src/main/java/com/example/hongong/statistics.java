package com.example.hongong;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class statistics extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpecDay = tabHost.newTabSpec("Day").setIndicator("일별");
        tabSpecDay.setContent(R.id.tabDay);
        tabHost.addTab(tabSpecDay);

        TabHost.TabSpec tabSpecWeek = tabHost.newTabSpec("Week").setIndicator("주별");
        tabSpecWeek.setContent(R.id.tabWeek);
        tabHost.addTab(tabSpecWeek);

        TabHost.TabSpec tabSpecMonth = tabHost.newTabSpec("Month").setIndicator("월별");
        tabSpecMonth.setContent(R.id.tabMonth);
        tabHost.addTab(tabSpecMonth);

        tabHost.setCurrentTab(0);
    }
}
