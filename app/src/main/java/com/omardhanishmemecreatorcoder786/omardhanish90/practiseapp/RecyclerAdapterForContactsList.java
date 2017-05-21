package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 19-05-2017.
 */

public class RecyclerAdapterForContactsList extends RecyclerView.Adapter<RecyclerAdapterForContactsList.MyViewHolder> {


    private List<StringNumber> homeFragmentRowItemList,filterList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;
        TextView email;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);
            email = (TextView) view.findViewById(R.id.email);
        }
    }


    public RecyclerAdapterForContactsList(Context context , List<StringNumber> list) {
        this.homeFragmentRowItemList = list;
        this.mContext = context;
        this.filterList = new ArrayList<StringNumber>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.homeFragmentRowItemList);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StringNumber itematpos = filterList.get(position);
        holder.name.setText(itematpos.name);
        holder.number.setText("");
        holder.email.setText("");
        if (itematpos.emails.size() > 0 && itematpos.emails.get(0) != null) {
            holder.email.setText(itematpos.emails.get(0).address);
        }
        if (itematpos.numbers.size() > 0 && itematpos.numbers.get(0) != null) {
            holder.number.setText(itematpos.numbers.get(0).number);
        }
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(homeFragmentRowItemList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (StringNumber item : homeFragmentRowItemList) {
                        if (item.name.toLowerCase().contains(text.toLowerCase()) ||
                                item.name.toLowerCase().contains(text.toLowerCase())) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

}




