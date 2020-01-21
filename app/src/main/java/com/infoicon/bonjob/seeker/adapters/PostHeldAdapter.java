package com.infoicon.bonjob.seeker.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class PostHeldAdapter extends RecyclerView.Adapter<PostHeldAdapter.MyViewHolder> {

    private FragmentActivity context;
    String[] mTestArray;

    public PostHeldAdapter(FragmentActivity activity) {
        this.context = activity;
        mTestArray = activity.getResources().getStringArray(R.array.array_pos_held);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_post_held, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(mTestArray[position]);
        holder.tvName.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return mTestArray.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName) CustomsTextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
