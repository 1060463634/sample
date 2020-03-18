package com.example.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sample.R;

import java.util.List;

public class RecyclerViewTestAdapter extends RecyclerView.Adapter<RecyclerViewTestAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mlists;
    private LayoutInflater mInflater;

    public RecyclerViewTestAdapter(Context context, List<String> lists) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mlists = lists;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.fruitName.setText(mlists.get(position));
    }

    @Override
    public int getItemCount() {
        return mlists.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            fruitName = view.findViewById(R.id.list_tv);
        }

    }
}