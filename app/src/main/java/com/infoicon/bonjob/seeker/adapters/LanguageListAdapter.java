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

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.MyViewHolder> {

    private Context context;
    private List<GetSeekerProfileResponse.DataBean.LanguagesBean> languages;

    public LanguageListAdapter(Context activity, List<GetSeekerProfileResponse.DataBean.LanguagesBean> languages) {
        this.context = activity;
        this.languages=languages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_language_list, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvLanguageName.setText(languages.get(position).getSeeker_lang());
        holder.tvLanguageStatus.setText(languages.get(position).getLang_proficiency_name());
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvLanguageName) CustomsTextViewBold tvLanguageName;
        @BindView(R.id.tvLanguageStatus) CustomsTextView tvLanguageStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           // tvLanguageName.setTextAppearance(context, R.style.boldText);
            tvLanguageStatus.setTextAppearance(context, R.style.italicText);
        }
    }
}
