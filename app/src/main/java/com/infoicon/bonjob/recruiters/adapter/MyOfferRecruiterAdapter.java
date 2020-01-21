package com.infoicon.bonjob.recruiters.adapter;

import android.app.Activity;
import android.os.Bundle;
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
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.recruiters.myOffer.MyOfferRecruiterFragment;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetCloseOffersResponse;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse.ActiveJobsBean;
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

public class MyOfferRecruiterAdapter extends RecyclerView.Adapter<MyOfferRecruiterAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Activity context;
    private MyOfferRecruiterFragment myOfferRecruiterFragment;
    private List<ActiveJobsBean> activeJobsBeanList;

    public MyOfferRecruiterAdapter(Activity activity, MyOfferRecruiterFragment myOfferRecruiterFragment) {
        this.context = activity;
        this.myOfferRecruiterFragment = myOfferRecruiterFragment;
        activeJobsBeanList = new ArrayList<>();
    }

    public void addOffer(ActiveJobsBean activeJobsBean) {
        activeJobsBeanList.add(activeJobsBean);
        notifyDataSetChanged();
        Logger.e(TAG + " : active job : " + activeJobsBeanList.size());
    }


    public void clearOffer() {
        activeJobsBeanList.clear();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_offer_recruiter, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!activeJobsBeanList.get(position).getJob_image().equals("")) {
            ImageLoader.loadJobImageCallback(context, activeJobsBeanList.get(position).getJob_image(), holder.imageViewJob, holder.progress_bar);
        } else {
            holder.imageViewJob.setImageResource(R.drawable.default_job);
        }

        holder.tvJobName.setText(activeJobsBeanList.get(position).getJob_title());
        holder.tvDesc.setText(activeJobsBeanList.get(position).getJob_description());

        holder.btnModifyOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ArrayList<ActiveJobsBean> arrayList = new ArrayList<>();
                    arrayList.add(activeJobsBeanList.get(position));
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Keys.JOB_OFFERED, arrayList);
                    ((HomeRecruiterActivity) context).addFragment(new PostJobOfferFragment(), bundle, Keys.POST_JOB, false, true);
               }
        });

        holder.btnCloseOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceCloseOffer(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activeJobsBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnModifyOffer) CustomsButton btnModifyOffer;
        @BindView(R.id.btnCloseOffer) CustomsButton btnCloseOffer;
        @BindView(R.id.imageViewJob) ImageView imageViewJob;
        @BindView(R.id.tvJobName) CustomsTextViewBold tvJobName;
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;
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
    private void serviceCloseOffer(int position) {
        Logger.e(TAG + " : closed job position: " + position + " : " + activeJobsBeanList.size());
        final SpotsDialog spotsDialog = new SpotsDialog(context, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.JOB_ID, activeJobsBeanList.get(position).getJob_id());
        retrofit.Call<GetCloseOffersResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getCloseOfferResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetCloseOffersResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetCloseOffersResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetCloseOffersResponse getCloseOffersResponse = response.body();
                if (getCloseOffersResponse.isSuccess()) {
                    myOfferRecruiterFragment.closeOffer(activeJobsBeanList.get(position));
                    activeJobsBeanList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } else {
                    if (getCloseOffersResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(context, getCloseOffersResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(context, getCloseOffersResponse.getMsg());
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
