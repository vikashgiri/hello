package com.infoicon.bonjob.recruiters.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse.DataBean.GalleryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class ProfileVisitorAdapter extends RecyclerView.Adapter<ProfileVisitorAdapter.MyViewHolder> {

    private FragmentActivity context;
    private List<GalleryBean> galleryBeanList;

    public ProfileVisitorAdapter(FragmentActivity activity, List<GalleryBean> gallery) {
        this.context = activity;
        galleryBeanList = gallery;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_visitor, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvDesc.setVisibility(View.VISIBLE);

        ImageLoader.loadImageRoundCallback(context, galleryBeanList.get(position).getImage(), holder.imageViewBG, holder.progress);
        if(galleryBeanList.get(position).getDescription().equals(""))
       {
           holder.tvDesc.setVisibility(View.GONE);

       }
       else
       {
           holder.tvDesc.setText(galleryBeanList.get(position).getDescription());

       }
    }

    @Override
    public int getItemCount() {
        return galleryBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewBG) ImageView imageViewBG;
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;
        @BindView(R.id.progress) ProgressBar progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
