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
import com.infoicon.bonjob.preferences.SharePref_User;
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
public class EducationLevelFragment extends Fragment {


    View rootView;
    @BindView(R.id.listView) ListView listView;
    private String[] array_list_title;
    int pos = 0;
    EditProfileFragment editProfileFragment;

    public EducationLevelFragment(EditProfileFragment editProfileFragment) {
        this.editProfileFragment = editProfileFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_education_level, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        //serviceForGetEducationData();
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
            editProfileFragment.tvEducationLevel.setText(selected_item);
            editProfileFragment.tvEducationLevelId=getEducationDataResponce.getEducationLevels()
                    .get(pos).getEducation_id();
        }
        (getActivity()).onBackPressed();
    }

    /** init view */
    private void initView()
    {
        getEducationDataResponce=Singleton.getUserInfoInstance().getAllDropdowns();
      //  array_list_title = getResources().getStringArray(R.array.education_level);
        array_list_title=  new String[getEducationDataResponce.getEducationLevels()
                .size()];
        for(int i=0;i< getEducationDataResponce.getEducationLevels().size();i++)
        {
            array_list_title[i]=getEducationDataResponce.getEducationLevels()
                    .get(i).getEducation_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, array_list_title);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(0);
        listView.setOnItemClickListener((parent, view, position, id) -> pos = position);
    }


    GetAllDropDownResponce getEducationDataResponce;

   /* private void serviceForGetEducationData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetEducationDataResponce> call = AppRetrofit.getAppRetrofitInstance().
                getApiServices().getGetEducationDataResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetEducationDataResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetEducationDataResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                 getEducationDataResponce = response.body();

                if (getEducationDataResponce.isSuccess())
                {


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
