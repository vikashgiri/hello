package com.infoicon.bonjob.recruiters.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.myOffer.MyOfferRecruiterFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetRenewOfferResponse;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * Created by infoicona on 18/3/17.
 */

public class ArchiveOfferAdapter extends RecyclerView.Adapter<ArchiveOfferAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Activity context;
    private List<GetMyOffersRecruiterResponse.ClosedJobsBean> closedJobsBeanList;
    private MyOfferRecruiterFragment myOfferRecruiterFragment;

    public ArchiveOfferAdapter(Activity activity, MyOfferRecruiterFragment myOfferRecruiterFragment) {
        this.context = activity;
        this.myOfferRecruiterFragment = myOfferRecruiterFragment;
        closedJobsBeanList = new ArrayList<>();
    }

    public void addOffer(GetMyOffersRecruiterResponse.ClosedJobsBean closedJobsBean) {
        closedJobsBeanList.add(closedJobsBean);
        notifyDataSetChanged();
        Logger.e(TAG + " : closed job : " + closedJobsBeanList.size());
    }


    public void clearOffer() {
        closedJobsBeanList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_archive, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvJobName.setText(closedJobsBeanList.get(position).getJob_title());
        holder.tvDesc.setText(closedJobsBeanList.get(position).getJob_description());
        if (!closedJobsBeanList.get(position).getJob_image().equals(""))
            ImageLoader.loadJobImageCallback(context, closedJobsBeanList.get(position).getJob_image(), holder.imageViewJob, holder.progress_bar);
        else holder.imageViewJob.setImageResource(R.drawable.default_job);

        holder.btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceRenewOffer(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return closedJobsBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewJob) ImageView imageViewJob;
        @BindView(R.id.tvJobName) CustomsTextViewBold tvJobName;
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;
        @BindView(R.id.btnRenew) CustomsButton btnRenew;
        @BindView(R.id.progress_bar) ProgressBar progress_bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * call service for close offers
     * @param position
     */
    private void serviceRenewOffer(int position) {
        Logger.e(TAG + " : closed job position: " + position + " : " + closedJobsBeanList.size());

        final SpotsDialog spotsDialog = new SpotsDialog(context, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.JOB_ID, closedJobsBeanList.get(position).getJob_id());
        retrofit.Call<GetRenewOfferResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getRenewOfferResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetRenewOfferResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetRenewOfferResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetRenewOfferResponse getRenewOfferResponse = response.body();
                if (getRenewOfferResponse.isSuccess()) {
                    myOfferRecruiterFragment.renewOffer(closedJobsBeanList.get(position));
                    closedJobsBeanList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } else {
                    if (getRenewOfferResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(context, getRenewOfferResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(context, getRenewOfferResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(context, t.getMessage());
            }
        });
    }

}
