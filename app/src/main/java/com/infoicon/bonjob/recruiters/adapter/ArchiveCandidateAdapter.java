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
import com.infoicon.bonjob.retrofit.response.GetCandidateListResponse.DataBean.ArchivedListBean;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class ArchiveCandidateAdapter extends RecyclerView.Adapter<ArchiveCandidateAdapter.MyViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<ArchivedListBean> archivedListBeen;

    public ArchiveCandidateAdapter(Context activity) {
        this.context = activity;
        archivedListBeen = new ArrayList<>();
    }


    public void addArchiveData(ArchivedListBean listBean) {
        archivedListBeen.add(listBean);
        notifyDataSetChanged();
    }

    public void clearArchiveData() {
        archivedListBeen.clear();

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_archive_candidate, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (!archivedListBeen.get(position).getUser_pic().equals(""))
            ImageLoader.loadImageWithCircle(context, archivedListBeen.get(position).getUser_pic(), holder.imgViewCandidate);
        else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        if (!archivedListBeen.get(position).getCurrent_status().equals("")) {
            holder.tvCurrentStatus.setVisibility(View.VISIBLE);
            holder.tvCurrentStatus.setText(archivedListBeen.get(position).
                    getCurrent_status_name());
        } else {
            holder.tvCurrentStatus.setVisibility(View.GONE);
        }

        holder.job_title.setText(archivedListBeen.get(position).getJob_title());
        holder.textViewName.setText(archivedListBeen.get(position).getFirst_name() + " " +
                archivedListBeen.get(position).getLast_name());

        holder.tvExperience.setText(UtilsMethods.getExperience(context, archivedListBeen.get(position).getExperience()));
        /*String exp = archivedListBeen.get(position).getExperience();
        if (!exp.equals("")) {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, archivedListBeen.get(position).getExperience()));
        } else {
            holder.tvExperience.setText(UtilsMethods.getExperience(context, "0"));
        }
*/

        holder.imgViewCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CandidateProfileActivity.class);
                intent.putExtra(Keys.CANDIDATE_ID, archivedListBeen.get(position).getUser_id());
                intent.putExtra(Keys.JOB_TITLE, archivedListBeen.get(position).getJob_title());
                intent.putExtra(Keys.APPLIED_ID, archivedListBeen.get(position).getAplied_id());
                intent.putExtra(Keys.POSITION, position);
                intent.putExtra(Keys.FROM, Keys.FROM_APPLIED_CANDIDATE);
                // context.startActivity(intent);
                //((HomeRecruiterActivity) context).startActivityForResult(intent, Keys.CANDIDATE_SELECT_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return archivedListBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewName;
        @BindView(R.id.tvCurrentStatus) CustomsTextView tvCurrentStatus;
        @BindView(R.id.tvExperience) CustomsTextView tvExperience;
        @BindView(R.id.job_title) CustomsTextViewBold job_title;
        @BindView(R.id.imgViewJob) ImageView imgViewCandidate;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
