package com.infoicon.bonjob.recruiters.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.retrofit.response.GetHiredCandidateResponse.DataBean;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class HiredCandidateAdapter extends RecyclerView.Adapter<HiredCandidateAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private ArrayList<DataBean> beanArrayList;

    public HiredCandidateAdapter(Context activity) {
        this.context = activity;
        beanArrayList = new ArrayList<>();
    }

    public void addData(DataBean dataBean) {
        beanArrayList.add(dataBean);
        notifyDataSetChanged();
    }

    public void clearData() {
        beanArrayList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hired, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textViewName.setText(beanArrayList.get(position).getFirst_name() + " " +
                beanArrayList.get(position).getLast_name());

        if (!beanArrayList.get(position).getCurrent_status().equals("")) {
            holder.tvCurrentStatus.setVisibility(View.VISIBLE);
            holder.tvCurrentStatus.setText(beanArrayList.get(position).
                    getCurrent_status_name());
        } else {
            holder.tvCurrentStatus.setVisibility(View.GONE);
        }

        holder.tvJobTitle.setText(beanArrayList.get(position).getJob_title());

        /*List<String> exp = beanArrayList.get(position).getExperience();
        if (exp.size() > 0) {
            //String experience=beanArrayList.get(position).getExperience().get(0);

            holder.tvExperience.setText(UtilsMethods.getExperience(context, beanArrayList.get(position).getExperience().get(0)));
        } else {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, "0"));
        }*/
        String exp = beanArrayList.get(position).getExperience();
        if (!exp.equals("")) {
            //String experience=beanArrayList.get(position).getExperience().get(0);

            holder.tvExperience.setText(UtilsMethods.getExperience(context, exp));
        } else {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, "0"));
        }

        if (!beanArrayList.get(position).getUser_pic().equals(""))
            ImageLoader.loadImageWithCircle(context, beanArrayList.get(position).getUser_pic(), holder.imgViewCandidate);
        else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.CANDIDATE_ID, beanArrayList.get(position).getUser_id());
                intent.putExtra(Keys.APPLIED_ID, beanArrayList.get(position).getAplied_id());
                intent.putExtra(Keys.JOB_TITLE, beanArrayList.get(position).getJob_title());
                intent.putExtra(Keys.JOB_IMAGE, beanArrayList.get(position).getJob_image());
                intent.putExtra(Keys.DESCRIPTION, beanArrayList.get(position).getJob_description());
                intent.putExtra(Keys.FROM, Keys.FROM_HIRED_CANDIDATE);
                ((HomeRecruiterActivity) context).startActivityForResult(intent, Keys.CANDIDATE_HIRED_CODE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return beanArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewName;
        @BindView(R.id.tvCurrentStatus) CustomsTextView tvCurrentStatus;
        @BindView(R.id.tvJobTitle) CustomsTextView tvJobTitle;
        @BindView(R.id.tvExperience) CustomsTextView tvExperience;
        @BindView(R.id.imgViewJob) ImageView imgViewCandidate;
        @BindView(R.id.rlMain) RelativeLayout rlMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
