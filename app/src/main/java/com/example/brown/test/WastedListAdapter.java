package com.example.brown.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brown on 4/21/2018.
 */

public class WastedListAdapter extends RecyclerView.Adapter<WastedListAdapter.DataViewHolder>{

    class DataViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private DataViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }

    }

    private final LayoutInflater mInflater;
    private List<userTables> mWords;

    WastedListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if (mWords != null) {
            userTables current = mWords.get(position);

            holder.wordItemView.setText(current.getmWord());
        } else {
            // covers the case no data
            holder.wordItemView.setText("no word");
        }
    }

    void setmWords(List<userTables> words) {
        mWords = words;
        notifyDataSetChanged();
    }


    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}
