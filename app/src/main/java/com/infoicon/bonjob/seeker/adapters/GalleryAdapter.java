package com.infoicon.bonjob.seeker.adapters;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.seeker.profile.EditProfileFragment;

import com.infoicon.bonjob.utils.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private static final String TAG = PackageManager.class.getName();
    private Context context;
    EditProfileFragment editProfileFragment;

    ArrayList<GetSeekerProfileResponse.DataBean.GalleryBean> galleryModelArrayList;

    public GalleryAdapter(Context activity, EditProfileFragment editProfileFragment) {
        this.context = activity;
        this.editProfileFragment = editProfileFragment;
        galleryModelArrayList = new ArrayList<>();
    }

    /** method for add photo to gallery */
    public void addPhoto(GetSeekerProfileResponse.DataBean.GalleryBean galleryModel) {
        galleryModelArrayList.add(galleryModel);
        notifyDataSetChanged();
    }

    /** method for remove photo from gallery */
    public void removePhoto(int pos){
        galleryModelArrayList.remove(pos);
        notifyDataSetChanged();
    }

    /** update description of a photo */
    public void updateDescription(int pos,String desc){
        galleryModelArrayList.get(pos).setDescription(desc);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Logger.e(TAG+" :1: "+galleryModelArrayList.size()+" : "+position);
        if (galleryModelArrayList.size()>position){
            Logger.e(TAG+" :2: "+galleryModelArrayList.size()+" : "+position);
            ImageLoader.loadImageRoundCallback(context, galleryModelArrayList.get(position).getImage(), holder.imageView,holder.progress);
        }else {
            holder.progress.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.photo_img);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e(TAG+" :3: "+galleryModelArrayList.size()+" : "+position);
                if (galleryModelArrayList.size()>position) {

                    editProfileFragment.imageEdit(galleryModelArrayList.get(position).getGallery_id(),
                            galleryModelArrayList.get(position).getDescription(),
                            galleryModelArrayList.get(position).getImage(),position);

                } else {
                    editProfileFragment.takePhotoOption(Keys.FROM_GALLERY_ADAPTER);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView) ImageView imageView;
        @BindView(R.id.progress) ProgressBar progress;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
