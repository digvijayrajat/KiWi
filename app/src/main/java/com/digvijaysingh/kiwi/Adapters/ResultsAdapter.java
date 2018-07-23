package com.digvijaysingh.kiwi.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.digvijaysingh.kiwi.Activity.DetailsActivity;
import com.digvijaysingh.kiwi.Activity.ResultsActivity;
import com.digvijaysingh.kiwi.R;
import com.digvijaysingh.kiwi.Activity.SearchActivity;
import com.digvijaysingh.kiwi.Utils.Constant;
import com.digvijaysingh.kiwi.pojo.Page;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.DeviceViewHolder> {
    /**
     * Declation of variables
     *
     * */
    private Context mContext;
    private List<Page> data;
    private int lastPosition=-1;


    public ResultsAdapter(Context mContext, List<Page> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ResultsAdapter.DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultsAdapter.DeviceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_item_result,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.tvResult.setText(data.get(position).getTitle());
        if (data.get(position).getThumbnail()!=null && data.get(position).getThumbnail().getSource()!=null )
            Glide.with(mContext).load(data.get(position).getThumbnail().getSource()).apply(RequestOptions.circleCropTransform()).into(holder.ivImage);
        else
            holder.ivImage.setImageResource(R.drawable.ic_place);
        if (data.get(position).getTerms()!=null && data.get(position).getTerms().getDescription()!=null && data.get(position).getTerms().getDescription().size()>0)
            holder.tvDescription.setText(data.get(position).getTerms().getDescription().get(0));
        else
            holder.tvDescription.setText("");
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvResult,tvDescription;
        DeviceViewHolder(View itemView) {
            super(itemView);
            tvResult=(TextView)itemView.findViewById(R.id.tvResult);
            tvDescription=(TextView)itemView.findViewById(R.id.tvDescription);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            /**
             * Starting new Activity with shared element transaction
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,DetailsActivity.class);

                    //((SearchActivity)mContext).saveToPrefs(data.get(getAdapterPosition()).getTitle()+":"+data.get(getAdapterPosition()).getPageid());

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
    @Override
    public void onViewDetachedFromWindow(DeviceViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}



