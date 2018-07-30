package com.example.tarun.wifisetup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tarun.wifisetup.adapter.WifiListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://stackoverflow.com/questions/5452940/how-can-i-get-android-wifi-scan-results-into-a-list

public class MainActivityJava extends AppCompatActivity {
    private WifiManager mWifiManager;
    private List<ScanResult> mResults;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int size;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.wifi_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!mWifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            mWifiManager.setWifiEnabled(true);
        }

        registerReceiver(mBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        refreshScan();
       // Toast.makeText(MainActivityJava.this, "Start Scan", Toast.LENGTH_SHORT).show();
    }

    /**
     * Broadcast receiver for wifi scanning
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mResults != null)
                mResults.clear();
            Toast.makeText(MainActivityJava.this, "Stop Scan", Toast.LENGTH_SHORT).show();
            mResults = mWifiManager.getScanResults();
            size = mResults.size()-1;
            Toast.makeText(MainActivityJava.this, "Available WiFi networks: "
                    + mResults.size(), Toast.LENGTH_SHORT).show();
            List<String> list = new ArrayList<>();
            while (size >= 0) {
                list.add(mResults.get(size).SSID);
                size --;
            }
            if (mAdapter == null) {
                mAdapter = new WifiListAdapter(list);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            } else {
                WifiListAdapter adapter = (WifiListAdapter) mAdapter;
                adapter.swap(list);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                refreshScan();
                break;

            default:
                break;
        }

        return true;
    }

    private void refreshScan() {
        mWifiManager.startScan();
        Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
