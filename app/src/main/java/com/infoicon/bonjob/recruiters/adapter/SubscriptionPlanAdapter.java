package com.infoicon.bonjob.recruiters.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class SubscriptionPlanAdapter extends RecyclerView.Adapter<SubscriptionPlanAdapter.MyViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<DataBean> subscriptionList;

    public interface OnItemClickListener {
        public void onItemClick(DataBean dataBean);
    }

    public SubscriptionPlanAdapter(Context activity, OnItemClickListener onItemClickListener) {
        this.context = activity;
        this.onItemClickListener = onItemClickListener;
        subscriptionList = new ArrayList<>();
    }

    /** add all subscription */
    public void addAllSubscription(List<DataBean> subscriptionLists) {
        subscriptionList.addAll(subscriptionLists);
        notifyDataSetChanged();
    }

    /** add subscription */
    public void addSubscription(DataBean subscription) {
        subscriptionList.add(subscription);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subscription_plan, null);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (Integer.parseInt(subscriptionList.get(position).getTime_period()) > 1) {
            holder.tvPlanLabel.setTextColor(ContextCompat.getColor(context, R.color.pink_color));
            holder.tvPlanPricing.setTextColor(ContextCompat.getColor(context, R.color.pink_color));
            holder.tvPlanFor.setTextColor(ContextCompat.getColor(context, R.color.pink_color));
        } else {
            holder.tvPlanLabel.setTextColor(ContextCompat.getColor(context, R.color.blue_color));
            holder.tvPlanPricing.setTextColor(ContextCompat.getColor(context, R.color.blue_color));
            holder.tvPlanFor.setTextColor(ContextCompat.getColor(context, R.color.blue_color));
        }

        holder.tvPlanLabel.setText(subscriptionList.get(position).getSubscription_title());
        String arrayPaln[] = subscriptionList.get(position).getDescription().split("€");
        holder.tvPlanPricing.setText(arrayPaln[0] + "€");
        holder.tvPlanFor.setText(arrayPaln[1].trim());

        holder.rlPlanMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(subscriptionList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subscriptionList == null ? 0 : subscriptionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPlanLabel) CustomsTextViewBold tvPlanLabel;
        @BindView(R.id.tvPlanPricing) CustomsTextViewBold tvPlanPricing;
        @BindView(R.id.tvPlanFor) CustomsTextViewBold tvPlanFor;
        @BindView(R.id.rlPlanMonth) RelativeLayout rlPlanMonth;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
