package com.infoicon.bonjob.recruiters.post;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.recruiters.search.GetTargetSearchResponce;
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
public class JobOfferedFragment extends Fragment {


    View rootView;
    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.textViewBack) CustomsTextView textViewBack;
    @BindView(R.id.edSearch) CustomEditText edSearch;
    private String[] array_list_title;
    int pos = 0;
    PostJobOfferFragment postJobOfferFragment;
    private ArrayAdapter<String> arrayAdapter;

    @SuppressLint("ValidFragment")
    public JobOfferedFragment(PostJobOfferFragment postJobOfferFragment) {
        this.postJobOfferFragment = postJobOfferFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_job_offered, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        //serviceForGetJobTitle();
    }

    /**back to previous screen */
    @OnClick(R.id.textViewBack)
    void goBack()
    {
        (getActivity()).onBackPressed();
    }

    /** get the job offered from list */
    @OnClick(R.id.tvSave)
    void save() {
        //String selected_item = array_list_title[pos];
        String selected_item = null;
        if (arrayAdapter != null)
            selected_item = arrayAdapter.getItem(pos);
        if (selected_item!=null && !selected_item.equals("")) {
            postJobOfferFragment.edJobOffered.setText(selected_item);
            postJobOfferFragment.JobOfferedId=getCompanyDetailsResponse.getJobTitles()
                    .get(pos).getJob_title_id();

            postJobOfferFragment.edJobOffered.setError(null);
        }
        (getActivity()).onBackPressed();
    }

    /** initialization */
    private void initView()
    {
        getCompanyDetailsResponse = Singleton.getUserInfoInstance().getAllDropdowns();

        textViewBack.setText(getResources().getString(R.string.job_offer));
        edSearch.addTextChangedListener(textWatcher);

        array_list_title=  new String[getCompanyDetailsResponse
                .getJobTitles()
                .size()];
        for(int i=0;i< getCompanyDetailsResponse.getJobTitles().size();i++)
        {
            array_list_title[i]=getCompanyDetailsResponse.getJobTitles().get(i)
                    .getJob_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
       // array_list_title = getResources().getStringArray(R.array.array_job_offered);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, array_list_title);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(pos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
        });

    }

    /** TextWatcher for  text filtration*/
    TextWatcher textWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            arrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    GetAllDropDownResponce getCompanyDetailsResponse;
/*

    private void serviceForGetJobTitle()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetJobTitleResponce> call = AppRetrofit.
                getAppRetrofitInstance().getApiServices().getGetJobTitleResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetJobTitleResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetJobTitleResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                getCompanyDetailsResponse = response.body();

                if (getCompanyDetailsResponse.isSuccess())
                {
                    initView();

                } else {
                    if (getCompanyDetailsResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getCompanyDetailsResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();

            }
        });
    }
*/


}
