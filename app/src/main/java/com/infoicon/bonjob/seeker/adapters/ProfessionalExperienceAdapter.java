package com.infoicon.bonjob.seeker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class ProfessionalExperienceAdapter extends RecyclerView.Adapter<ProfessionalExperienceAdapter.MyViewHolder> {

    private Context context;
    private List<GetSeekerProfileResponse.DataBean.ExperienceBean> experience;

    public ProfessionalExperienceAdapter(Context activity, List<GetSeekerProfileResponse.DataBean.ExperienceBean> experience) {
        this.context = activity;
        this.experience = experience;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_professional_experience, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvProfile.setText(experience.get(position).getPosition_held_name());
        String exp = getExp(context, experience.get(position).getExperience());
        holder.tvEvenormoandie.setText(experience.get(position).getCompany_name() + " - " + exp);
        if (experience.get(position).getDescription().equalsIgnoreCase("")) {
            holder.textViewDesc.setVisibility(View.GONE);
        } else {
            holder.textViewDesc.setVisibility(View.VISIBLE);
            holder.textViewDesc.setText(experience.get(position).getDescription());
        }

    }

    private String getExp(Context context, String exps) {
        String exp=String.valueOf(Integer.parseInt(exps)-1);
if((Integer.parseInt(exps)-1)<0)
{
    exp="0";
}

        String experience = "";
        if (exp.equals("0")) {
            experience = "0 " + context.getResources().getString(R.string.year);
        } else if (exp.equals("1")) {
            experience = "<1 " + context.getResources().getString(R.string.years);
        } else if (exp.equals("2")) {
            experience = "1-2 " + context.getResources().getString(R.string.years);
        } else if (exp.equals("3")) {
            experience = "3-4 " + context.getResources().getString(R.string.years);
        } else if (exp.equals("4")) {
            experience = "5 " + context.getResources().getString(R.string.years_plus);
        }
        return experience;
    }

    @Override
    public int getItemCount() {
        return experience.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvProfile) CustomsTextViewBold tvProfile;
        @BindView(R.id.tvEvenormoandie) CustomsTextView tvEvenormoandie;
        @BindView(R.id.textViewDesc) CustomsTextView textViewDesc;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //  tvProfile.setTextAppearance(context, R.style.boldText);
            tvEvenormoandie.setTextAppearance(context, R.style.italicText);

        }

    }
}
