package com.infoicon.bonjob.recruiters.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.recruiters.myOffer.SelectedFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetNonSelectCandidateResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectedCandidateResponse.SelectedDataBean;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * Created by infoicona on 18/3/17.
 */

public class SelectedCandidateAdapter extends RecyclerView.Adapter<SelectedCandidateAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Activity context;
    private ArrayList<SelectedDataBean> dataBeanArrayList;
    private SelectedFragment selectedFragment;

    public SelectedCandidateAdapter(Activity activity, SelectedFragment selectedFragment) {
        this.context = activity;
        this.selectedFragment = selectedFragment;
        dataBeanArrayList = new ArrayList<>();

    }

    public void addData(SelectedDataBean selectedDataBean) {
        dataBeanArrayList.add(selectedDataBean);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (dataBeanArrayList != null && dataBeanArrayList.size() > 0) {
            dataBeanArrayList.clear();
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_selected, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (dataBeanArrayList.get(position).getAplied_id().equals("")) {
            holder.linearHireUnSelect.setVisibility(View.GONE);
        } else {
            holder.linearHireUnSelect.setVisibility(View.VISIBLE);
        }

        holder.textViewName.setText(dataBeanArrayList.get(position).getFirst_name() + " " +
                dataBeanArrayList.get(position).getLast_name());
        holder.tvJobTitle.setText(dataBeanArrayList.get(position).getJob_title());

        if (!dataBeanArrayList.get(position).getCurrent_status().equals("")) {
            holder.tvCurrentStatus.setVisibility(View.VISIBLE);
            holder.tvCurrentStatus.setText(dataBeanArrayList.get(position).
                    getCurrent_status_name());
        } else {
            holder.tvCurrentStatus.setVisibility(View.GONE);
        }
        holder.tvExperience.setText(UtilsMethods.getExperience(context, dataBeanArrayList.get(position).getExperience()));

        /*List<String> exp = dataBeanArrayList.get(position).getExperience();
        if (exp.size() > 0) {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, dataBeanArrayList.get(position).getExperience().get(0)));
        } else {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, "0"));
        }*/

        if (!dataBeanArrayList.get(position).getUser_pic().equals(""))
            ImageLoader.loadImageWithCircle(context, dataBeanArrayList.get(position).getUser_pic(), holder.imgViewCandidate);
        else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.CANDIDATE_ID, dataBeanArrayList.get(position).getUser_id());
                intent.putExtra(Keys.JOB_TITLE, dataBeanArrayList.get(position).getJob_title());
                intent.putExtra(Keys.APPLIED_ID, dataBeanArrayList.get(position).getAplied_id());
                intent.putExtra(Keys.JOB_IMAGE, dataBeanArrayList.get(position).getJob_image());
                intent.putExtra(Keys.DESCRIPTION, dataBeanArrayList.get(position).getJob_image());
                intent.putExtra(Keys.FROM, Keys.SHOW_CHAT_HIRE_OPTION);
                ((HomeRecruiterActivity) context).startActivityForResult(intent, Keys.CANDIDATE_HIRED_CODE);
            }
        });

        holder.buttonNotSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceNonSelectCandidate(position);
            }
        });

        holder.buttonHired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.CANDIDATE_ID, dataBeanArrayList.get(position).getUser_id());
                intent.putExtra(Keys.JOB_TITLE, dataBeanArrayList.get(position).getJob_title());
                intent.putExtra(Keys.APPLIED_ID, dataBeanArrayList.get(position).getAplied_id());
                intent.putExtra(Keys.JOB_IMAGE, "");
                intent.putExtra(Keys.DESCRIPTION, "");
                intent.putExtra(Keys.FROM, Keys.SHOW_CHAT_HIRE_OPTION);
                ((HomeRecruiterActivity) context).startActivityForResult(intent, Keys.CANDIDATE_HIRED_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewName;
        @BindView(R.id.tvCurrentStatus) CustomsTextView tvCurrentStatus;
        @BindView(R.id.tvJobTitle) CustomsTextView tvJobTitle;
        @BindView(R.id.tvExperience) CustomsTextView tvExperience;
        @BindView(R.id.imgViewJob) ImageView imgViewCandidate;
        @BindView(R.id.buttonNotSelected) CustomsButton buttonNotSelected;
        @BindView(R.id.buttonHired) CustomsButton buttonHired;
        @BindView(R.id.rlMain) RelativeLayout rlMain;
        @BindView(R.id.linearHireUnSelect) LinearLayout linearHireUnSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /**
     * call service for non select candidate
     * @param position
     */
    private void serviceNonSelectCandidate(int position) {
        Logger.e(TAG + " : closed job position: " + position + " : " + dataBeanArrayList.size());

        final SpotsDialog spotsDialog = new SpotsDialog(context, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.APPLIED_ID, dataBeanArrayList.get(position).getAplied_id());
        retrofit.Call<GetNonSelectCandidateResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getNonSelectResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetNonSelectCandidateResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetNonSelectCandidateResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetNonSelectCandidateResponse getNonSelectCandidateResponse = response.body();
                if (getNonSelectCandidateResponse.isSuccess()) {
                    selectedFragment.nonSelectCandidate(dataBeanArrayList.get(position));
                    dataBeanArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } else {
                    if (getNonSelectCandidateResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(context, getNonSelectCandidateResponse.getMsg());
                    } else
                        CommonUtils.showSimpleMessageBottom(context, getNonSelectCandidateResponse.getMsg());
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
