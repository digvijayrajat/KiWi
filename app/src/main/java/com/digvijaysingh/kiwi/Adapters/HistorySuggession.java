package com.digvijaysingh.kiwi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digvijaysingh.kiwi.Activity.PageViewActivity;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Utils.Constant;

import java.util.List;


public class HistorySuggession extends RecyclerView.Adapter<HistorySuggession.DeviceViewHolder> {
    /**
     * Declation of variables
     * */
    private Context mContext;
    private List<String> data;


    public HistorySuggession(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override

    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_history,
                parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, final int position) {
        holder.tvResult.setText(data.get(position).split(":")[0]);
      }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvResult;
        public DeviceViewHolder(View itemView) {
            super(itemView);
            tvResult=(TextView)itemView.findViewById(R.id.tvResult);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            /**
             * Starting new Activity with shared element transaction
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, PageViewActivity.class).putExtra(Constant.KEY_URL,Constant.WIKI_PAGE_API+data.get(getAdapterPosition()).split(":")[1]));
                }
            });
        }
    }
}


