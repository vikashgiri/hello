package com.infoicon.bonjob.seeker.myOffers;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CountDownTimerView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.response.GetMyOfferSeekerResponse.DataBean.AppliedListBean;
import com.infoicon.bonjob.seeker.searchJob.JobDetailsFragment;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class MyOfferSeekerAdapter extends RecyclerView.Adapter<MyOfferSeekerAdapter.MyViewHolder> {

    private String TAG = this.getClass().getSimpleName();
    private FragmentActivity context;
    private List<AppliedListBean> appliedListBeanList;
    private String currentDate;

    public MyOfferSeekerAdapter(FragmentActivity activity) {
        this.context = activity;
        appliedListBeanList = new ArrayList<>();
    }


    public void addData(AppliedListBean appliedList) {
        appliedListBeanList.add(appliedList);
        notifyDataSetChanged();
    }

    public void clear() {
        appliedListBeanList.clear();
        }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_offers, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!appliedListBeanList.get(position).getJob_image().equals(""))
            ImageLoader.loadImageWithCircle(context, appliedListBeanList.get(position).getJob_image(), holder.imgViewJob);
        else holder.imgViewJob.setImageResource(R.drawable.default_job);

        holder.textViewTitle.setText(appliedListBeanList.get(position).getJob_title());
      /*  holder.tvDesc.setText(UtilsMethods.getDescription(context,
                appliedListBeanList.get(position).getJob_title(),
                appliedListBeanList.get(position).getNum_of_hours()));*/

        holder.tvDesc.setText(appliedListBeanList.get(position).getJob_description());

        int minutes = UtilsMethods.getMinuteDiff(appliedListBeanList.get(position).getCreatedOn(), currentDate);

        Logger.e(TAG + " "
                + appliedListBeanList.get(position).getAplied_id() + " "
                + appliedListBeanList.get(position).getJob_title() + " : " + minutes);
        //if (minutes > 1440) {
        if (minutes > 2880) {
            holder.progressTimer.setProgress(0);
            holder.tvTimer.setText(context.getResources().getString(R.string.app_expire));
        } else {
            Logger.e(TAG + " 2 : "
                    + appliedListBeanList.get(position).getAplied_id() + " " +
                    appliedListBeanList.get(position).getJob_title() + " : " + minutes);
            int seconds = UtilsMethods.getSecondLeft(appliedListBeanList.get(position).getCreatedOn()
                    , currentDate);
            holder.progressTimer.setProgress(seconds);
            holder.tvTimer.setText(UtilsMethods.convertTimeFormat(seconds));


            if (holder.myCountDownTimer != null) {
                holder.myCountDownTimer.cancel();
            }
            holder.myCountDownTimer = new MyCountDownTimer(holder.progressTimer, holder.tvTimer,
                    seconds * 1000L, 1000);
            holder.myCountDownTimer.start();


          /*  holder.myCountDownTimer = new CountDownTimer(seconds * 1000L, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    holder.progressTimer.setProgress(millisUntilFinished / 1000);
                    holder.tvTimer.setText(UtilsMethods.convertTimeFormat((int) millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    holder.progressTimer.setProgress(0);
                    holder.tvTimer.setText(context.getResources().getString(R.string.app_expire));
                }
            }.start();*/

        }


        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.JOB_ID, appliedListBeanList.get(position).getJob_id());
                bundle.putInt(Keys.POSITION, -1);
                ((HomeSeekerActivity) context).addInnerFragment(jobDetailsFragment, bundle, Keys.JOB_DETAILS, false, true);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return appliedListBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgViewJob) CircleImageView imgViewJob;
        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewTitle;
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;
        @BindView(R.id.tvTimer) CustomsTextView tvTimer;
        @BindView(R.id.progressTimer) RoundCornerProgressBar progressTimer;
        @BindView(R.id.rlMain) RelativeLayout rlMain;

        @BindView(R.id.tvHour) CustomsTextView tvHour;
        @BindView(R.id.tvMinutes) CustomsTextView tvMinutes;
        @BindView(R.id.tvSeconds) CustomsTextView tvSeconds;

        //CountDownTimer myCountDownTimer;
        MyCountDownTimer myCountDownTimer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgViewJob.setBorderWidth(2);
            imgViewJob.setBorderColor(ContextCompat.getColor(context, R.color.border_grey));
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
