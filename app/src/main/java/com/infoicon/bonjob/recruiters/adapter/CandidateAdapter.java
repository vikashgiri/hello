package com.infoicon.bonjob.recruiters.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.retrofit.response.GetCandidateListResponse.DataBean.AppliedListBean;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<AppliedListBean> appliedListBean;
    private String currentDate;

    public CandidateAdapter(Context activity) {
        this.context = activity;
        appliedListBean = new ArrayList<>();
    }

    public void addCandidateData(AppliedListBean listBean) {
        appliedListBean.add(listBean);
        notifyDataSetChanged();
    }

    public void clear() {
        appliedListBean.clear();
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public void removeCandidate(int position) {
        try {
            appliedListBean.remove(position);
            notifyDataSetChanged();
        } catch (IndexOutOfBoundsException ex) {
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_candidate, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (!appliedListBean.get(position).getUser_pic().equals(""))
            ImageLoader.loadImageWithCircle(context, appliedListBean.get(position).getUser_pic(), holder.imgViewCandidate);
        else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        if (!appliedListBean.get(position).getCurrent_status().equals("")) {
            holder.tvCurrentStatus.setVisibility(View.VISIBLE);
            holder.tvCurrentStatus.setText(appliedListBean.get(position).
                    getCurrent_status_name());
        } else {
            holder.tvCurrentStatus.setVisibility(View.GONE);
        }

        holder.job_title.setText(appliedListBean.get(position).getJob_title());
        holder.textViewName.setText(appliedListBean.get(position).getFirst_name() + " " +
                appliedListBean.get(position).getLast_name());

        holder.tvExperience.setText(UtilsMethods.getExperience(context, appliedListBean.get(position).getExperience()));
        /*String exp = appliedListBean.get(position).getExperience();
        if (!exp.equals("")) {

        } else {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, "0"));
        }*/

       /* int seconds = UtilsMethods.getSecondLeft(appliedListBean.get(position).getCreatedOn(), currentDate);
        holder.progressTimer.setProgress(seconds);
        holder.tvTimer.setText(UtilsMethods.convertTimeFormat(seconds));*/


        int minutes = UtilsMethods.getMinuteDiff(appliedListBean.get(position).getCreatedOn(), currentDate);

        Logger.e(TAG + " "
                + appliedListBean.get(position).getAplied_id() + " "
                + appliedListBean.get(position).getJob_title() + " : " + minutes);
        if (minutes > 2880) {
            holder.progressTimer.setProgress(0);
            holder.tvTimer.setText(context.getResources().getString(R.string.app_expire));
        } else {
            Logger.e(TAG + " 2 : "
                    + appliedListBean.get(position).getAplied_id() + " " +
                    appliedListBean.get(position).getJob_title() + " : " + minutes);
            int seconds = UtilsMethods.getSecondLeft(appliedListBean.get(position).getCreatedOn(), currentDate);
            holder.progressTimer.setProgress(seconds);
            holder.tvTimer.setText(UtilsMethods.convertTimeFormat(seconds));


            if (holder.myCountDownTimer != null) {
                holder.myCountDownTimer.cancel();
            }
            holder.myCountDownTimer = new MyCountDownTimer
                    (holder.progressTimer, holder.tvTimer,
                            seconds * 1000L, 1000);
            holder.myCountDownTimer.start();
        }

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.CANDIDATE_ID, appliedListBean.get(position).getUser_id());
                intent.putExtra(Keys.JOB_TITLE, appliedListBean.get(position).getJob_title());
                intent.putExtra(Keys.APPLIED_ID, appliedListBean.get(position).getAplied_id());
                intent.putExtra(Keys.POSITION, position);
                intent.putExtra(Keys.FROM, Keys.FROM_APPLIED_CANDIDATE);
                ((HomeRecruiterActivity) context).startActivityForResult(intent, Keys.CANDIDATE_SELECT_CODE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return appliedListBean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewName;
        @BindView(R.id.tvCurrentStatus) CustomsTextView tvCurrentStatus;
        @BindView(R.id.tvExperience) CustomsTextView tvExperience;
        @BindView(R.id.job_title) CustomsTextViewBold job_title;
        @BindView(R.id.imgViewJob) ImageView imgViewCandidate;
        // @BindView(R.id.progressTimer) ProgressBar progressTimer;
        @BindView(R.id.progressTimer) RoundCornerProgressBar progressTimer;
        @BindView(R.id.tvTimer) CustomsTextView tvTimer;
        @BindView(R.id.rlMain) RelativeLayout rlMain;
        MyCountDownTimer myCountDownTimer;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    /** countdown timer for applied jobs */
    public class MyCountDownTimer extends CountDownTimer {
        RoundCornerProgressBar progressTimer;
        CustomsTextView tvTimer;

        public MyCountDownTimer(RoundCornerProgressBar progressTimer, CustomsTextView tvTimer, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.progressTimer = progressTimer;
            this.tvTimer = tvTimer;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progressTimer.setProgress(millisUntilFinished / 1000);
            tvTimer.setText(UtilsMethods.convertTimeFormat((int) millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            progressTimer.setProgress(0);
            tvTimer.setText(context.getResources().getString(R.string.app_expire));
        }
    }
}
