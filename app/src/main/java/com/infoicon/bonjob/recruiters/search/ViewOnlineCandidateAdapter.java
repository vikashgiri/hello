package com.infoicon.bonjob.recruiters.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.retrofit.response.GetCandidateSearchResponse;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class ViewOnlineCandidateAdapter extends RecyclerView.Adapter {

    private String TAG = this.getClass().getSimpleName();
    private FragmentActivity context;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private OnLoadMoreListener onLoadMoreListener;

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private List<GetCandidateSearchResponse.AllCandidatesBean> allCandidatesBeanList;
    // private RecyclerView recyclerView;
    private SearchForCandidateFragment searchForCandidateFragment;

    Object companyLat, companyLng;

    public ViewOnlineCandidateAdapter(FragmentActivity activity, List<GetCandidateSearchResponse.AllCandidatesBean> allCandidatesBeanList, RecyclerView recyclerView, SearchForCandidateFragment searchForCandidateFragment) {
        this.context = activity;
        this.allCandidatesBeanList = allCandidatesBeanList;
        //this.recyclerView = recyclerView;
        this.searchForCandidateFragment = searchForCandidateFragment;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        onLoadMoreListener.onLoadMore();
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            Logger.e(TAG + " call VIEW_ITEM");
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_online_candidate, parent, false);
            vh = new ViewOnlineCandidateAdapter.MyViewHolder(v);
        } else {
            Logger.e(TAG + " call VIEW_PROG");
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
            vh = new ViewOnlineCandidateAdapter.ProgressViewHolder(v);
        }
        return vh;
    }

    public void setLoaded() {
        Logger.e(TAG + " setLoaded call");
        loading = false;
    }

    /** current latlong for calculate distance */
    public void addLatlong(Object companyLat, Object companyLng) {
        this.companyLat = companyLat;
        this.companyLng = companyLng;
    }

    public void setOnLoadMoreListener(ViewOnlineCandidateAdapter.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).textViewName.setText(allCandidatesBeanList.get(position).getFirst_name() + " " +
                    allCandidatesBeanList.get(position).getLast_name());
            ((MyViewHolder) holder).tvCandidateStatus.setText(allCandidatesBeanList.get(position).getCurrent_status_name());
            if (!allCandidatesBeanList.get(position).getUser_pic().equals(""))
                ImageLoader.loadImageWithCircle(context, allCandidatesBeanList.get(position).getUser_pic(), ((MyViewHolder) holder).imgViewCandidate);
            else ((MyViewHolder) holder).imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

            ((MyViewHolder) holder).tvPercentage.setText(allCandidatesBeanList.get(position).getMatchingPercent() + "%");
            ((MyViewHolder) holder).progressTimer.setProgress(allCandidatesBeanList.get(position).getMatchingPercent());
            String city=allCandidatesBeanList.get(position).getCity();

            if (!(city == null || city.trim().isEmpty() ||  city.trim().equalsIgnoreCase("-")
                    || city.trim().equalsIgnoreCase(" ")))
            {
                if (!companyLat.equals("") && !companyLng.equals("")) {
                if (companyLat != null &&
                        allCandidatesBeanList.get(position).getLattitude() != null &&
                        !allCandidatesBeanList.get(position).getLattitude().equals("") &&
                        !allCandidatesBeanList.get(position).getLongitude().equals("")&&
                        !allCandidatesBeanList.get(position).getLattitude().equals("0.000000") &&
                        !allCandidatesBeanList.get(position).getLongitude().equals("0.000000")) {
                    String distance = UtilsMethods.calculateDistanceInKm(String.valueOf(companyLat),
                            String.valueOf(companyLng),
                            String.valueOf(allCandidatesBeanList.get(position).getLattitude()),
                            String.valueOf(allCandidatesBeanList.get(position).getLongitude()));
                    ((MyViewHolder) holder).tvLocation.setText(distance + "KM - " + allCandidatesBeanList.get(position).getCity());
                } else
                    ((MyViewHolder) holder).tvLocation.setText(allCandidatesBeanList.get(position).getCity());
            } else {
                ((MyViewHolder) holder).tvLocation.setText(allCandidatesBeanList.get(position).getCity());
            }
        }
        else
            {
                ((MyViewHolder) holder).tvLocation.setText(context.getResources().getString(R.string.non_show));

            }

            ((MyViewHolder) holder).rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CandidateProfileActivity.class);
                    intent.putExtra(Keys.CANDIDATE_ID, allCandidatesBeanList.get(position).getId());
                    intent.putExtra(Keys.JOB_TITLE, allCandidatesBeanList.get(position).getPosition_held());
                    intent.putExtra(Keys.APPLIED_ID, "");
                    intent.putExtra(Keys.FROM, Keys.FROM_SEARCH_CANDIDATE);
                    context.startActivityForResult(intent, Keys.CANDIDATE_SELECT_CODE);
                }
            });

        } else {
            Logger.e(TAG + " progressBar setIndeterminate");

            ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);

            /*if (!loading) {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            } else {
                ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
            }*/
        }
    }

    @Override
    public int getItemViewType(int position) {
        return allCandidatesBeanList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public int getItemCount() {
        return allCandidatesBeanList == null ? 0 : allCandidatesBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewName;
        @BindView(R.id.tvCandidateStatus) CustomsTextView tvCandidateStatus;
        @BindView(R.id.tvLocation) CustomsTextView tvLocation;
        @BindView(R.id.tvPercentage) CustomsTextView tvPercentage;
        //@BindView(R.id.progressTimer) ProgressBar progressTimer;
        @BindView(R.id.progressTimer) RoundCornerProgressBar progressTimer;
        //  @BindView(R.id.imgViewJob) CircularImageView imgViewCandidate;
        @BindView(R.id.imgViewJob) CircleImageView imgViewCandidate;
        @BindView(R.id.linearLayoutProgress) LinearLayout linearLayoutProgress;
        @BindView(R.id.rlMain) RelativeLayout rlMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (searchForCandidateFragment.fromFilter) {
                linearLayoutProgress.setVisibility(View.VISIBLE);
            } else {
                linearLayoutProgress.setVisibility(View.GONE);
            }
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar) ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
