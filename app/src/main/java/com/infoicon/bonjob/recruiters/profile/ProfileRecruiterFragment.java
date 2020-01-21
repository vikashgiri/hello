package com.infoicon.bonjob.recruiters.profile;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsItalicTextView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetRecruiterProfileResponse;
import com.infoicon.bonjob.setting.SettingRecruiterFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class ProfileRecruiterFragment extends Fragment {

    private View rootView;
    @BindView(R.id.linearLayoutMain) LinearLayout linearLayoutMain;
    @BindView(R.id.tvLocationName) CustomsTextView tvLocationName;
    @BindView(R.id.imageViewEnterprise) public ImageView imageViewEnterprise;
    @BindView(R.id.imgViewJob) public CircleImageView imgViewRecruiter;
    @BindView(R.id.textViewRecruiterName) CustomsTextViewBold textViewRecruiterName;
    @BindView(R.id.tvCompanyName) CustomsItalicTextView tvCompanyName;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;

    public GetRecruiterProfileResponse.DataBean dataBeanProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callWebServiceForMyProfile(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile_recruiter, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        if(Singleton.getUserInfoInstance().getLoginAdmin())
        {
            Button log_out=(Button)rootView.findViewById(R.id.btnEditProfile);
            ImageView setting=(ImageView) rootView.findViewById(R.id.imageViewSetting);
setting.setVisibility(View.GONE);
            log_out.setText(getString(R.string.sign_out));
        }
        return rootView;
    }

    private void initView() {
        imgViewRecruiter.setBorderWidth(2);
        imgViewRecruiter.setBorderColor(ContextCompat.getColor(getActivity(), R.color.border_grey));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.PROFILE);
        setResponseData();
    }

    @OnClick(R.id.imageViewSetting)
    void proceedToSettingPage() {
        ((HomeRecruiterActivity) getActivity()).addFragment(new SettingRecruiterFragment(), new Bundle(), Keys.SETTINGS, false, true);
    }

    @OnClick(R.id.btnEditProfile)
    void editProfile() {
        if(Singleton.getUserInfoInstance().getLoginAdmin())
        {
            UtilsMethods.logoutAlert(getActivity());
            return;
        }
        ((HomeRecruiterActivity) getActivity()).addFragment(new EditProfileRecruiterFragment
                (ProfileRecruiterFragment.this), new Bundle(), Keys.EDIT_PROFILE,
                false, true);
    }


    @OnClick(R.id.btnRetry)
    void retry() {
        callWebServiceForMyProfile(true);
    }

    /** call service for get profile */
    private void callWebServiceForMyProfile(boolean isLoaderShow) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        if (isLoaderShow)
            spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.USER_ID, Singleton.
                getUserInfoInstance().getUser_id());
        retrofit.Call<GetRecruiterProfileResponse> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().
                getRecruirerProfileResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetRecruiterProfileResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetRecruiterProfileResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.GONE);
                GetRecruiterProfileResponse getRecruiterProfileResponse = response.body();
                if (getRecruiterProfileResponse.isSuccess()) {
                    linearLayoutMain.setVisibility(View.VISIBLE);
                    dataBeanProfile = getRecruiterProfileResponse.getData();
                    setResponseData();
                } else {
                    linearLayoutMain.setVisibility(View.GONE);
                    if (getRecruiterProfileResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getRecruiterProfileResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                linearLayoutMain.setVisibility(View.GONE);
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
                //  CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }


    /** set response data after getting profile service response */
    private void setResponseData() {
        if (dataBeanProfile != null) {
            if (dataBeanProfile.getCity() == null || dataBeanProfile.getCity().equals("")) {
                tvLocationName.setVisibility(View.GONE);
            } else {
                tvLocationName.setVisibility(View.VISIBLE);
                tvLocationName.setText(dataBeanProfile.getCity());
            }

            // this condition is if profile currently updated then load local image otherwise url.
            if (dataBeanProfile.isUserPicChange()) {
                if (!dataBeanProfile.getUser_pic().equals(""))
                    imgViewRecruiter.setImageBitmap(BitmapFactory.decodeFile(dataBeanProfile.getUser_pic()));
                    // ImageLoader.loadStorageImage(getActivity(), dataBeanProfile.getUser_pic(), imgViewRecruiter);
                else {
                    imgViewRecruiter.setImageResource(R.drawable.default_photo_deactive);
                }
            } else {
                if (!dataBeanProfile.getUser_pic().equals(""))
                    ImageLoader.loadImageWithCircle(getActivity(), dataBeanProfile.getUser_pic(), imgViewRecruiter);
                else {
                    imgViewRecruiter.setImageResource(R.drawable.default_photo_deactive);
                }
            }

            if (dataBeanProfile.isEnterPrisePicChange()) {
                if (!dataBeanProfile.getEnterprise_pic().equals("")) {
                    //   imageViewEnterprise.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageViewEnterprise.setImageBitmap(BitmapFactory.decodeFile(dataBeanProfile.getEnterprise_pic()));
                } else {
                    //  imageViewEnterprise.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageViewEnterprise.setImageResource(R.mipmap.ic_launcher_round);
                }
            } else {
                if (!dataBeanProfile.getEnterprise_pic().equals("")) {
                    ImageLoader.loadImage2(getActivity(), dataBeanProfile.getEnterprise_pic(), imageViewEnterprise);
                } else {
                    //  imageViewEnterprise.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageViewEnterprise.setImageResource(R.mipmap.ic_launcher_round);
                }
            }
            textViewRecruiterName.setText(dataBeanProfile.getFirst_name() + " " + dataBeanProfile.getLast_name());
            tvCompanyName.setText(dataBeanProfile.getEnterprise_name());
        }
    }

    @Override
    public void onDestroy() {
        //delete file from storage after upload
        UtilsMethods.deleteAllBonjobCreatedFile();
        super.onDestroy();
    }
}
