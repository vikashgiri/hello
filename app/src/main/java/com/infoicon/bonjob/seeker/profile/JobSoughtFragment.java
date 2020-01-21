package com.infoicon.bonjob.seeker.profile;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class JobSoughtFragment extends Fragment {


    View rootView;
    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.title)
    CustomsTextView title;

    private String[] array_list_title;
    int pos = 0;
    EditProfileFragment editProfileFragment;

    public JobSoughtFragment(EditProfileFragment editProfileFragment) {
        this.editProfileFragment = editProfileFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_education_level, container, false);
        ButterKnife.bind(this, rootView);
        title.setText(getString(R.string.job_sought));
      //  serviceForGetEducationData();
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /** go back event */
    @OnClick(R.id.textViewBack)
    void Goback() {
        (getActivity()).onBackPressed();
    }

    @OnClick(R.id.tvSave)
    void save()
    {
        String selected_item = array_list_title[pos];
        if (!selected_item.equals(""))
        {
            editProfileFragment.tvJobSought.setText(selected_item);
            editProfileFragment.tvJobSoughtId=getEducationDataResponce.
                    getCandidateSeeks()
                    .get(pos).getCandidate_seek_id();
        }
        (getActivity()).onBackPressed();
    }

    /** init view */
    private void initView()
    {
        getEducationDataResponce=Singleton.getUserInfoInstance().getAllDropdowns();
      //  array_list_title = getResources().getStringArray(R.array.education_level);
        array_list_title=  new String[getEducationDataResponce.getCandidateSeeks()
                .size()];
        for(int i=0;i< getEducationDataResponce.getCandidateSeeks().size();i++)
        {
            array_list_title[i]=getEducationDataResponce.getCandidateSeeks()
                    .get(i).getCandidate_seek_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, array_list_title);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(0);
        listView.setOnItemClickListener((parent, view, position, id) -> pos = position);
    }


    GetAllDropDownResponce getEducationDataResponce;
/*
    private void serviceForGetEducationData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetJobSoughtFragmentResponce> call = AppRetrofit.getAppRetrofitInstance().
                getApiServices().getGetJobSoughtFragmentResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetJobSoughtFragmentResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetJobSoughtFragmentResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                  getEducationDataResponce = response.body();

                if (getEducationDataResponce.isSuccess())
                {
                    initView();

                } else {
                    if (getEducationDataResponce.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getEducationDataResponce.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }*/
}
