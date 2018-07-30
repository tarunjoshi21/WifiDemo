package com.example.tarun.wifisetup.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tarun.wifisetup.R;

import java.util.List;

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {
    private List<String> wifiList;

    public void swap(List<String> datas)
    {
        if(datas == null || datas.size() == 0)
            return;
        if (wifiList != null && wifiList.size() > 0)
            wifiList.clear();
        wifiList.addAll(datas);
        notifyDataSetChanged();

    }

    public WifiListAdapter(List<String> wifiList) {
        this.wifiList = wifiList;
        Log.i(WifiListAdapter.class.getSimpleName(), "Total wifi: "+wifiList.size());
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wifi_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(wifiList.get(position));
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.wifi_name);
        }
    }
}
