package com.infoicon.bonjob.recruiters.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.retrofit.response.GetSelectedCandidateResponse.NotSelectedDataBean;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class
ArchiveSelectedCandidateAdapter extends RecyclerView.Adapter<ArchiveSelectedCandidateAdapter.MyViewHolder> {

    private Context context;
    ArrayList<NotSelectedDataBean> dataBeanArrayList;

    public ArchiveSelectedCandidateAdapter(Context activity) {
        this.context = activity;
        dataBeanArrayList = new ArrayList<>();
    }


    public void addData(NotSelectedDataBean selectedDataBean) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_archive_selected, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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
            ImageLoader.loadImageWithCircle(context, dataBeanArrayList.
                    get(position).getUser_pic(), holder.imgViewCandidate);
        else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        holder.imgViewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.FROM, Keys.SHOW_CHAT_HIRE_OPTION);
                //  context.startActivity(intent);
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
        @BindView(R.id.tvExperience) CustomsTextView tvExperience;
        @BindView(R.id.tvJobTitle) CustomsTextView tvJobTitle;
        @BindView(R.id.imgViewJob) ImageView imgViewCandidate;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
