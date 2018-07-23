package com.digvijaysingh.kiwi.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.digvijaysingh.kiwi.Activity.PageViewActivity;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.pojo.PojoNews;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.DeviceViewHolder> {

    private Context mContext;
    private List<PojoNews> data;


    public NewsAdapter(Context mContext, List<PojoNews> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override

    public NewsAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsAdapter.DeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_news,
                parent, false));
    }

    @Override
    public void onBindViewHolder(NewsAdapter.DeviceViewHolder holder, final int position) {
        holder.tvResult.setText(data.get(position).Name);
        holder.ivImage.setImageResource(data.get(position).Image);
        holder.tvDescription.setText(data.get(position).Description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        CardView card;
        TextView tvResult,tvDescription;
        public DeviceViewHolder(View itemView) {
            super(itemView);
            tvResult=(TextView)itemView.findViewById(R.id.tvResult);
            tvDescription=(TextView)itemView.findViewById(R.id.tvDescription);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, PageViewActivity.class).putExtra("url",data.get(getAdapterPosition()).url));
                }
            });
        }
    }
}
