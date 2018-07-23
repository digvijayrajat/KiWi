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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digvijaysingh.kiwi.Activity.DetailsActivity;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Activity.SearchActivity;
import com.digvijaysingh.kiwi.pojo.Page;

import java.util.List;


public class SearchSeggetionAdapter extends RecyclerView.Adapter<SearchSeggetionAdapter.DeviceViewHolder> {
  /**
     * Declation of variables
     * 
    * */   
    private Context mContext;
    private List<Page> data;


    public SearchSeggetionAdapter(Context mContext, List<Page> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_sugessions,
                parent, false));
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, final int position) {
        holder.tvResult.setText(data.get(position).getTitle());
        if(data.get(position).getThumbnail()!=null)
        Glide.with(mContext).load(data.get(position).getThumbnail().getSource()).apply(RequestOptions.circleCropTransform()).into(holder.ivImage);
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

                    ((SearchActivity)mContext).saveToPrefs(data.get(getAdapterPosition()).getTitle()+":"+data.get(getAdapterPosition()).getPageid());

                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    if (data.get(getAdapterPosition()).getThumbnail()!=null && data.get(getAdapterPosition()).getThumbnail().getSource()!=null )
                        intent.putExtra("imageUrl",data.get(getAdapterPosition()).getThumbnail().getSource());
                    intent.putExtra("pageid",data.get(getAdapterPosition()).getPageid().toString());
                    if (data.get(getAdapterPosition()).getTerms()!=null && data.get(getAdapterPosition()).getTerms().getDescription()!=null && data.get(getAdapterPosition()).getTerms().getDescription().size()>0)
                        intent.putExtra("description",(data.get(getAdapterPosition()).getTerms().getDescription().get(0)));

                    intent.putExtra("title",data.get(getAdapterPosition()).getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}


