package com.infoicon.bonjob.seeker.searchJob;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.response.GetCompanyDetailsResponse;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class CompanyJobOfferAdapter extends RecyclerView.Adapter<CompanyJobOfferAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<CompanyAllJobFragmentsResponce.JobsBean> allJobs;

    public CompanyJobOfferAdapter(Context activity, List<CompanyAllJobFragmentsResponce.JobsBean> allJobs) {
        this.context = activity;
        this.allJobs = allJobs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_company_details_job_offer, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        /*holder.tvDesc.setText(UtilsMethods.getDescription(context,
                allJobs.get(position).getJob_title(),
                allJobs.get(position).getNum_of_hours()));*/

        holder.tvDesc.setText(allJobs.get(position).getJob_title());

        holder.tvPostedOnDateTime.setText(UtilsMethods.convertDateTimeFormat(allJobs.get(position).getCreatedOn()));
        if (!allJobs.get(position).getJob_image().equals(""))
            ImageLoader.loadJobImageCallback(context, allJobs.get(position).getJob_image(), holder.imgViewJob, holder.progress_bar);
        else holder.imgViewJob.setImageResource(R.drawable.default_job);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.JOB_ID, allJobs.get(position).getJob_id());
                bundle.putInt(Keys.POSITION, -1);
                ((HomeSeekerActivity) context).addInnerFragment(jobDetailsFragment, bundle, Keys.JOB_DETAILS, false, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allJobs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;
        @BindView(R.id.tvPostedOnDateTime) CustomsTextView tvPostedOnDateTime;
        @BindView(R.id.imgViewJob) ImageView imgViewJob;
        @BindView(R.id.linearLayout) LinearLayout linearLayout;
        @BindView(R.id.subRleativeLayout) RelativeLayout subRleativeLayout;
        @BindView(R.id.progress_bar) ProgressBar progress_bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);




        }
    }
}
