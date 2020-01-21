package com.infoicon.bonjob.seeker.searchJob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.ChatActivity;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.chat.StaticConfig;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.login.Users;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.payment.PaymentStatusActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetApplyJobResponse;
import com.infoicon.bonjob.retrofit.response.GetSearchJobResponse;
import com.infoicon.bonjob.singletons.Singleton;

import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by infoicon on 9/6/17.
 */

class AdapterLook extends RecyclerView.Adapter {


    private String TAG = this.getClass().getSimpleName();
    private FragmentActivity activity;
    private final int VIEW_ITEM = 1;
    private final int ADVERTISEMENT = 2;
    private final int VIEW_PROG = 0;
    Context context;
    int arr[] = new int[3];
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    MediaController mediaController;
    GetAllDropDownResponce GetAllDropDownResponce;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    public List<GetSearchJobResponse.DataBean.AllJobsBean> allJobsBeanList;
    private OnLoadMoreListener onLoadMoreListener;
    private SearchJobFragment searchJobFragment;


    public AdapterLook(FragmentActivity activity,
                       List<GetSearchJobResponse.DataBean.AllJobsBean> allJobsBeanList,
                       RecyclerView recyclerView, SearchJobFragment searchJobFragment) {
        this.activity = activity;
        this.allJobsBeanList = allJobsBeanList;
        this.searchJobFragment = searchJobFragment;
        Logger.e(TAG + " : " + allJobsBeanList.size());
        GetAllDropDownResponce = Singleton.getUserInfoInstance().getAllDropdowns();


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
        RecyclerView.ViewHolder vh = null;
        context = parent.getContext();
        mediaController = new MediaController(context);
        /* if (viewType == ADVERTISEMENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dvertisement, parent, false);
            return vh = new AdvertisementViewHolder(v);
        }
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_lookfor, parent, false);
             vh = new MyViewHolder(v);
        }

        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
            return vh = new ProgressViewHolder(v);
        }
        return vh;*/
        View v;
        switch (viewType) {

        /*    case ADVERTISEMENT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dvertisement, parent, false);
                return vh = new AdvertisementViewHolder(v);*/
            case VIEW_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_lookfor, parent, false);
                return vh = new MyViewHolder(v);
            case VIEW_PROG:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_item, parent, false);
                return vh = new ProgressViewHolder(v);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tvcompanyName.setText(allJobsBeanList.get(position).getEnterprise_name());


            ((MyViewHolder) holder).textViewLocation.setText(allJobsBeanList.get(position).getJob_location());

            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.pole_emploi))) {
                if (!allJobsBeanList.get(position).getJob_image().equals(""))
                    ImageLoader.loadJobImageCallback(activity, allJobsBeanList.get(position).getJob_image(), ((MyViewHolder) holder).imageViewCompany, ((MyViewHolder) holder).progress_bar);
                else
                    ((MyViewHolder) holder).imageViewCompany.setImageResource(R.drawable.pole_employ_placeholder);
            }
            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.app_name))) {


                if (!allJobsBeanList.get(position).getJob_image().equals(""))
                    ImageLoader.loadJobImageCallback(activity, allJobsBeanList.get(position).getJob_image(), ((MyViewHolder) holder).imageViewCompany, ((MyViewHolder) holder).progress_bar);
                else
                    ((MyViewHolder) holder).imageViewCompany.setImageResource(R.drawable.default_job);
            }

            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.sodexo))) {


                if (!allJobsBeanList.get(position).getJob_image().equals(""))
                    ImageLoader.loadJobImageCallback(activity, allJobsBeanList.get(position).getJob_image(), ((MyViewHolder) holder).imageViewCompany, ((MyViewHolder) holder).progress_bar);
                else
                    ((MyViewHolder) holder).imageViewCompany.setImageResource(R.drawable.placeholder_sodexo);
            }


            ((MyViewHolder) holder).textViewMoreInformation.setPaintFlags(((MyViewHolder) holder)
                    .textViewMoreInformation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            if (allJobsBeanList.get(position).getApply_on().equals("")) {
                ((MyViewHolder) holder).btnApply.setText(activity.getResources().getString(R.string.apply));
                ((MyViewHolder) holder).btnApply.setBackground(ContextCompat.getDrawable(activity, R.drawable.round_button_blue));

            } else {
                ((MyViewHolder) holder).btnApply.setText(activity.getResources().getString(R.string.already_apply));
                ((MyViewHolder) holder).btnApply.setBackground(ContextCompat.getDrawable(activity, R.drawable.round_button_pink));
            }

            ((MyViewHolder) holder).tvJobTitle.setText(allJobsBeanList.get(position).getJob_title());

            String contract = allJobsBeanList.get(position).getContract_type();
            if (contract != null && !contract.equals("")) {


                for (int i = 0; i < GetAllDropDownResponce.getContractTypes().size(); i++) {
                    if (GetAllDropDownResponce.getContractTypes().get(i)
                            .getContract_id().equalsIgnoreCase(contract)) {
                        ((MyViewHolder) holder).linearContract.setVisibility(View.VISIBLE);
                        ((MyViewHolder) holder).tvContract.setText(GetAllDropDownResponce.getContractTypes().get(i)
                                .getContract_title());

                        break;
                    } else {
                    }
                }


            } else {
                ((MyViewHolder) holder).linearContract.setVisibility(View.GONE);
            }

           /* String experience = allJobsBeanList.get(position).getExperience();
            if (experience != null && !experience.equals("")) {
                ((MyViewHolder) holder).linearExperience.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvExperience.setText(experience);
            } else {
                ((MyViewHolder) holder).linearExperience.setVisibility(View.GONE);
            }
            String hours = allJobsBeanList.get(position).getNum_of_hours();
            if (hours != null && !hours.equals("")) {
                ((MyViewHolder) holder).linearNumHours.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvNoHours.setText(hours);
            } else {
                ((MyViewHolder) holder).linearNumHours.setVisibility(View.GONE);
            }

            String start_date = allJobsBeanList.get(position).getStart_date();
            if (start_date != null && !start_date.equals("")) {
                ((MyViewHolder) holder).linearStartDate.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvStartdate.setText(start_date);
            } else {
                ((MyViewHolder) holder).linearStartDate.setVisibility(View.GONE);
            }
            String desc = allJobsBeanList.get(position).getJob_description();
            if (desc != null && !desc.equals("")) {
                ((MyViewHolder) holder).tvDesc.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvDesc.setText(desc);
            } else {
                ((MyViewHolder) holder).tvDesc.setVisibility(View.GONE);
            }*/


//Enabling scrolling on TextView.

/*

            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.pole_emploi))) {
                ((MyViewHolder) holder).tvContact.
                        setText(activity.getResources().getString(R.string.pole_emploi));
            }
            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.sodexo)))
            {
                ((MyViewHolder) holder).tvContact.setText(activity.getResources().getString(R.string.sodexo));
            }
            if (allJobsBeanList.get(position).getOrigin().
                    equalsIgnoreCase(activity.getResources().getString(R.string.app_name)))
            {
                String contact = allJobsBeanList.get(position).getContact_mode();
                if (contact != null && !contact.equals("")) {
                    String[] contactArray = contact.split(":", 2);
                    if (contact.length() > 1)
                    {
                        try {
                            ((MyViewHolder) holder).tvContact.setText(contactArray[1]);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                        }
                    }
                }
            }
            String education = allJobsBeanList.get(position).getEducation_level();
            if (education != null && !education.equals("")) {
                ((MyViewHolder) holder).linearEducation.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvEducationLevel.setText(education);
            } else {
                ((MyViewHolder) holder).linearEducation.setVisibility(View.GONE);
            }

            String language = allJobsBeanList.get(position).getLang();
            if (language != null && !language.equals("")) {
                ((MyViewHolder) holder).linearLanguage.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvLanguage.setText(language);
            } else {
                ((MyViewHolder) holder).linearLanguage.setVisibility(View.GONE);
            }

            String salary = allJobsBeanList.get(position).getSalarie();
            if (salary != null && !salary.equals("")) {
                ((MyViewHolder) holder).linearSalaries.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvSalaries.setText(salary);
            } else {
                ((MyViewHolder) holder).linearSalaries.setVisibility(View.GONE);
            }*/

            ((MyViewHolder) holder).btnApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyViewHolder) holder).btnApply.getText().toString().equals(activity.getResources().getString(R.string.apply))) {
                        if (allJobsBeanList.get(position).getOrigin().
                                equalsIgnoreCase(activity.getResources().getString(R.string.pole_emploi)) || allJobsBeanList.get(position).getOrigin().
                                equalsIgnoreCase(activity.getResources().getString(R.string.sodexo))) {
                            Intent intent = new Intent(activity, PoleEmploiRedirectUrl.class);
                            intent.putExtra("redirect_url", allJobsBeanList.get(position).getRedirect_url());
                            activity.startActivity(intent);
                        } else {
                            serviceForApplyJobs(position);
                        }
                    }
                }
            });

            ((MyViewHolder) holder).imageLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    InputMethodManager imm = (InputMethodManager) ((MyViewHolder) holder).imageLayout.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(((MyViewHolder) holder).imageLayout.getWindowToken(), 0);
                    return false;
                }
            });

            ((MyViewHolder) holder).textViewMoreInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (allJobsBeanList.get(position).getOrigin().
                            equalsIgnoreCase(activity.getResources().getString(R.string.pole_emploi)) || allJobsBeanList.get(position).getOrigin().
                            equalsIgnoreCase(activity.getResources().getString(R.string.sodexo))) {
                        Intent intent = new Intent(activity, PoleEmploiRedirectUrl.class);
                        intent.putExtra("redirect_url", allJobsBeanList.get(position).getRedirect_url());
                        activity.startActivity(intent);
                        return;
                    }
                    JobDetailsFragment jobDetailsFragment = new JobDetailsFragment(searchJobFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.JOB_ID, allJobsBeanList.get(position).getJob_id());
                    bundle.putInt(Keys.POSITION, (holder).getAdapterPosition());
                    ((HomeSeekerActivity) activity).addInnerFragment(jobDetailsFragment, bundle, Keys.JOB_DETAILS, false, true);
                }
            });
         /*   ((MyViewHolder) holder).tvDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allJobsBeanList.get(position).getOrigin().
                            equalsIgnoreCase(activity.getResources().getString(R.string.pole_emploi)) || allJobsBeanList.get(position).getOrigin().
                            equalsIgnoreCase(activity.getResources().getString(R.string.sodexo))) {
                        Intent intent = new Intent(activity, PoleEmploiRedirectUrl.class);
                        intent.putExtra("redirect_url", allJobsBeanList.get(position).getRedirect_url());
                        activity.startActivity(intent);
                        return;
                    }
                    JobDetailsFragment jobDetailsFragment = new JobDetailsFragment(searchJobFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.JOB_ID, allJobsBeanList.get(position).getJob_id());
                    bundle.putInt(Keys.POSITION, (holder).getAdapterPosition());
                    ((HomeSeekerActivity) activity).addInnerFragment(jobDetailsFragment, bundle, Keys.JOB_DETAILS, false, true);
                }
            });*/
            ((MyViewHolder) holder).relativeCompanyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.EMPLOYER_ID, allJobsBeanList.get(position).getUser_id());
                    bundle.putInt(Keys.POSITION, (holder).getAdapterPosition());
                    ((HomeSeekerActivity) activity).addInnerFragment(companyDetailsFragment, bundle, Keys.COMPANY_DETAILS, false, true);
                }
            });

        }
        if (holder instanceof ProgressViewHolder) {
            //((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
        }

       /* if (holder instanceof AdvertisementViewHolder) {

            if (allJobsBeanList.get(position).getFile_type().
                    equalsIgnoreCase("image")) {
                if (allJobsBeanList.get(position).getAdvertisement_type().
                        equalsIgnoreCase("square")) {

                    ((AdvertisementViewHolder) holder).product_image.getLayoutParams().height =
                            (int) context.getResources().getDimension(R.dimen.image_Size_big);

                } else {
                    ((AdvertisementViewHolder) holder).product_image.getLayoutParams().height =
                            (int) context.getResources().getDimension(R.dimen.margin_160);
                }
                ((AdvertisementViewHolder) holder).imageViewFull.setVisibility(View.GONE);

                ((AdvertisementViewHolder) holder).video.setVisibility(View.GONE);

                ((AdvertisementViewHolder) holder).product_image.setVisibility(View.VISIBLE);
                if (!allJobsBeanList.get(position).getAdvertisement_image().equals(""))
                    ImageLoader.loadJobImageCallback(activity, allJobsBeanList.get(position).getAdvertisement_image(), ((AdvertisementViewHolder) holder).product_image, ((AdvertisementViewHolder) holder).progress_bar);
                else
                    ((AdvertisementViewHolder) holder).product_image.setImageResource(R.drawable.pole_employ_placeholder);
                // ((AdvertisementViewHolder) holder).title.setText(allJobsBeanList.get(position).getApp_advertisement_id());
                ((AdvertisementViewHolder) holder).product_image.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(activity, PoleEmploiRedirectUrl.class);
                        intent.putExtra("redirect_url", allJobsBeanList.get(position).getUrl());
                        activity.startActivity(intent);
                    }
                });

            }*/
/*
            else
                {

                ((AdvertisementViewHolder) holder).video.setVisibility(View.VISIBLE);
                ((AdvertisementViewHolder) holder).product_image.setVisibility(View.GONE);
                Uri uri = Uri.parse(allJobsBeanList.get(position).getAdvertisement_image());

                ((AdvertisementViewHolder) holder).progress_bar.setVisibility(View.VISIBLE);

                ((AdvertisementViewHolder) holder).video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp)
                    {
                        mp.setLooping(true);
                        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                            @Override
                            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                              mp.isLooping();
                                     if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                                    //first frame was bufered - do your stuff here
                                    ((AdvertisementViewHolder) holder).progress_bar.setVisibility(View.GONE);
                                }
                                return false;
                            }
                        });

                    }
                });*/

               /* try {

                    ((AdvertisementViewHolder) holder).video.setVideoURI(uri);

                    mediaController.setAnchorView(((AdvertisementViewHolder) holder).video);
                    ((AdvertisementViewHolder) holder).video.setMediaController(mediaController);
                    ((AdvertisementViewHolder) holder).video.start();
                    // set the media controller in the VideoView

                    ((AdvertisementViewHolder) holder).imageViewFull.setVisibility(View.VISIBLE);
                    ((AdvertisementViewHolder) holder).imageViewFull.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,FullScreenVideoActivity.class);
                            intent.putExtra(Keys.URL,allJobsBeanList.get(position).getAdvertisement_image());
                            ((Activity)context).startActivity(intent);
                        }
                    });





                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }*/
    }


    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public void addData(ArrayList<GetSearchJobResponse.DataBean.AllJobsBean> strings) {
        allJobsBeanList.addAll(strings);
        Logger.e("LoadMore Adapter :" + strings.size());
        // notifyItemInserted(strings.size());

    }

    @Override
    public int getItemCount() {
        //  Logger.e("LoadMore Adapter getItemCount  :" + stringArrayList.size());
        return allJobsBeanList == null ? 0 : allJobsBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            if (null != allJobsBeanList.get(position).getAdvertisement_image()) {
                return ADVERTISEMENT;
            }
            if (allJobsBeanList.get(position) != null) {
                return VIEW_ITEM;
            } else {
                return VIEW_PROG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW_PROG;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewCompany)
        ImageView imageViewCompany;
        @BindView(R.id.textViewMoreInformation)
        CustomsTextView textViewMoreInformation;
        @BindView(R.id.tvcompanyName)
        CustomsTextView tvcompanyName;
        @BindView(R.id.textViewLocation)
        CustomsTextView textViewLocation;
        @BindView(R.id.btnApply)
        CustomsButton btnApply;

        @BindView(R.id.imageLayout)
        LinearLayout imageLayout;

        @BindView(R.id.relativeCompanyName)
        RelativeLayout relativeCompanyName;
        @BindView(R.id.tvJobTitle)
        CustomsTextViewBold tvJobTitle;
        @BindView(R.id.linearJobTitle)
        LinearLayout linearJobTitle;
        @BindView(R.id.tvContract)
        CustomsTextViewBold tvContract;
        @BindView(R.id.linearContract)
        LinearLayout linearContract;
        /*@BindView(R.id.tvExperience)
        CustomsTextViewBold tvExperience;
        @BindView(R.id.linearExperience)
        LinearLayout linearExperience;
        @BindView(R.id.tvNoHours)
        CustomsTextViewBold tvNoHours;
        @BindView(R.id.linearNumHours)
        LinearLayout linearNumHours;
        @BindView(R.id.tvStartdate)
        CustomsTextViewBold tvStartdate;
        @BindView(R.id.linearStartDate)
        LinearLayout linearStartDate;
        @BindView(R.id.tvDesc)
        TextView tvDesc;*/


        /*@BindView(R.id.tvContact)
        CustomsTextViewBold tvContact;
        @BindView(R.id.linearEducation)
        LinearLayout linearEducation;
        @BindView(R.id.tvEducationLevel)
        CustomsTextViewBold tvEducationLevel;
        @BindView(R.id.linearLanguage)
        LinearLayout linearLanguage;
        @BindView(R.id.tvLanguage)
        CustomsTextViewBold tvLanguage;
        @BindView(R.id.linearSalaries)
        LinearLayout linearSalaries;
        @BindView(R.id.tvSalaries)
        CustomsTextViewBold tvSalaries;*/
        @BindView(R.id.progress_bar)
        ProgressBar progress_bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            // btnApply.setTextAppearance(activity, R.style.boldText);
           /* tvDesc.setOnTouchListener((v, event) -> {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            });

//Enabling scrolling on TextView.

            tvDesc.setMovementMethod(new ScrollingMovementMethod());*/
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


  /*  public class AdvertisementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_bar)
        ProgressBar progress_bar;
        @BindView(R.id.imageView)
        ImageView product_image;
        @BindView(R.id.video)
        VideoView video;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.imageViewFull)
        ImageView imageViewFull;


        public AdvertisementViewHolder(View v)
        {
            super(v);
            ButterKnife.bind(this, v);
        }
    }*/


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public boolean isJobIdAvailable(int pos, String job_id) {
        if (allJobsBeanList.get(pos).getJob_id().contains(job_id)) {
            return true;
        }
        return false;
    }

    public void applyJob(int position, String apply_on) {
        //  if (isJobIdAvailable(position,job_id)){
        allJobsBeanList.get(position).setApply_on(apply_on);
        notifyDataSetChanged();
        //   }

    }


    /**
     * call service for apply job
     *
     * @param position
     */
    private void serviceForApplyJobs(int position) {
        final SpotsDialog spotsDialog = new SpotsDialog(activity, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.JOB_ID, allJobsBeanList.get(position).getJob_id());
        Call<GetApplyJobResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getApplyJobResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new Callback<GetApplyJobResponse>() {
            @Override
            public void onResponse(Response<GetApplyJobResponse> response, Retrofit retrofit) {
                spotsDialog.dismiss();
                GetApplyJobResponse getApplyJobResponse = response.body();
                if (getApplyJobResponse.isSuccess()) {

                    addUserOnFireBase(getApplyJobResponse);
                    // add bubble on activity
                    int count = Singleton.getUserInfoInstance().getSeekerActivityCount();
                    Singleton.getUserInfoInstance().setSeekerActivityCount(count + 1);
                    activity.sendBroadcast(new Intent(Keys.ACTIVITY_COUNT));

                    allJobsBeanList.get(position).setApply_on(getApplyJobResponse.getData().get(0).getApply_on());
                    notifyDataSetChanged();
                    Intent intent = new Intent(activity, ApplyActivity.class);
                    intent.putExtra(Keys.FROM, Keys.SEARCH_JOB);
                    activity.startActivityForResult(intent, Keys.REQ_CODE_APPLY);

                } else {
                    CommonUtils.showSimpleMessageBottom(activity, getApplyJobResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(activity, t.getMessage());
            }
        });
    }


    void addUserOnFireBase(GetApplyJobResponse getApplyJobResponse) {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/" +getApplyJobResponse.getEmployerInfo().getId())
                .
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();


                                final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();

                                //send msg to user
                                Message newMessage = new Message();
                               // newMessage.setText("Job appilied by candidate");
                                newMessage.setFromId(getApplyJobResponse.getSeekerInfo().getId());
                                newMessage.setToId(getApplyJobResponse.getEmployerInfo().getId());
                                newMessage.setFcmToken((String) mapUserInfo.get("fcmToken"));
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);
                                newMessage.setJobImage(getApplyJobResponse.getData().get(0).getJob_image());
                                newMessage.setText(activity.getString(R.string.msg_apply)+" "+
                                        getApplyJobResponse.getData().get(0).getJob_title());
                                newMessage.setMsgType(activity.getString(R.string.msg_types));


                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName(getApplyJobResponse.getSeekerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getSeekerInfo().getLast_name());
                                from.setType("seeker");
                                from.setProfilePic(Singleton.getUserInfoInstance().getuser_pic());

                                Message.setUserType to = new Message.setUserType();
                                to.setName(getApplyJobResponse.getEmployerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getEmployerInfo().getLast_name());
                                to.setType("recruiter");
                                to.setProfilePic((String) mapUserInfo.get("profilePic"));

                                newMessage.setFrom(from);
                                newMessage.setTo(to);

                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                final String key = mss.child("Messages").push().getKey();
                                newMessage.setMessageID(key);
                                mss.child("Messages/" + key).setValue(newMessage).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(getApplyJobResponse.getEmployerInfo().getId())
                                                        .child(getApplyJobResponse.getSeekerInfo().getId()
                                                                + "/unreadMessages")
                                                        .child(key).setValue(1);

                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getSeekerInfo().getId() + "/"
                                                                + getApplyJobResponse.getEmployerInfo().getId() + "/" + key).setValue(1);
                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getEmployerInfo().getId() + "/"
                                                                + getApplyJobResponse.getSeekerInfo().getId() + "/" + key).setValue(1);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Write failed
                                                // ...
                                            }
                                        });
                                ;


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });
    }
}

