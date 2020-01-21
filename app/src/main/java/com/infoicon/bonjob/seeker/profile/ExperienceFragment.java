package com.infoicon.bonjob.seeker.profile;


import android.animation.LayoutTransition;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.login.LoginFacebookSignUpSeekerActivity;
import com.infoicon.bonjob.login.LoginSignUpRecruiterActivity;
import com.infoicon.bonjob.login.LoginSignUpSeekerActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetDeleteExperience;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.seeker.searchJob.JobSearchDropDownResponce;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import static com.infoicon.bonjob.utils.UtilsMethods.showErrorAlert;


public class ExperienceFragment extends Fragment {

    private static final String TAG = PackageManager.class.getName();
    private View rootView;

    @BindView(R.id.textViewExpIndi) CustomsTextViewBold textViewExpIndi;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.linearLayoutAddExperinec) LinearLayout linearLayoutAddExperinec;
    @BindView(R.id.scrollView) ScrollView scrollView;
    private CustomsTextView tvCountDescription;
    private List<GetSeekerProfileResponse.DataBean.ExperienceBean> dataBean;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_experience, container, false);
        ButterKnife.bind(this, rootView);
        initialize();
        setTextStyle();
        getBundleData();
        // serviceForGetpostHeldData();
        return rootView;
    }

    /** getting bundle data */
    private void getBundleData() {
        getPositionDataResponce=Singleton.getUserInfoInstance().getAllDropdowns();
        Bundle extras = getArguments();
        if (extras != null) {
            dataBean = extras.getParcelableArrayList(Keys.EXPERIENCE);

            if (dataBean != null && dataBean.size() > 0) {

           for (int i = 0; i < dataBean.size(); i++) {
                    for (int j = 0; j < getPositionDataResponce.getIndustryTypes().size();
                         j++) {

                        if (dataBean.get(i).getIndustry_type()
                                .equalsIgnoreCase(getPositionDataResponce
                                .getIndustryTypes().get(j).getIndustry_type_id())) {
                           dataBean.get(i).setIndustry_type(getPositionDataResponce
                                    .getIndustryTypes().get(j).getIndustry_type_name());


                            for (int k = 0; k < getPositionDataResponce
                                    .getIndustryTypes().get(j).
                                            getPositions().size(); k++) {
                                if (getPositionDataResponce
                                        .getIndustryTypes().get(j).getPositions()
                                        .get(k).
                                                getPosition_id().equalsIgnoreCase(
                                                dataBean.get(i).getPosition_held())) {
                                    dataBean.get(i).setPosition_held(
                                    getPositionDataResponce
                                            .getIndustryTypes().get(j).getPositions().get(k).
                                                    getPosition_name());
       /* System.out.println("dfsfeffe"+getPositionDataResponce
                .getPositions().get(j).getArea_of_activities().get(k).
                        getPosition_id());*/
                                }
                            }

                        }
                    }
                }
                for (int i = 0; i < dataBean.size(); i++) {
                    addViewExperience(true, i);
                }



            } else {
                addViewExperience(false, 0);
            }
        }
    }

    /** initialization */
    private void initialize() {

    }

    /** set style to text */
    private void setTextStyle() {
        //  textViewExpIndi.setTextAppearance(getActivity(), R.style.boldText);
        // tvExp.setTextAppearance(getActivity(), R.style.boldText);
    }


    /** event for go back screen */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }


    /** event for save data */
    @OnClick(R.id.tvSave)
    void save() {
        getExperienceArray();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.PROFILE);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * call service for update experience
     * @param jArry get the array of all added experience
     */
    private void callWebServiceForMyEditExperience(JSONArray jArry) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER_ID, Integer.parseInt(
                    Singleton.getUserInfoInstance().getUser_id()));
            if(jArry.length()<=0)
            {
                JSONObject jsonObjects = new JSONObject();

                jsonObjects.put("position_held", "");
                jsonObjects.put("industry_type", "");
                jsonObjects.put("company_name", "");
                jsonObjects.put("description", "");
                jsonObjects.put("experience","1");
                jsonObjects.put("position_held_name", "");
                jsonObjects.put("industry_type_name","");
                jArry.put(jsonObjects);
            }
            jsonObject.put(Keys.EXPERIENCE, jArry);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebServiceCall webServiceCall = new WebServiceCall(getActivity(),
                new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean(Keys.SUCCESS);
                    String message = jsonObject.getString(Keys.MESSAGE);
                    if (success) {
                        openAlert(message);
                        Intent intentVolume = new Intent(Keys.PROFILE);
                        getActivity().sendBroadcast(intentVolume);
                    } else {
                        if (jsonObject.getString(Keys.ACTIVE_USER).equals(Keys.AUTH_CODE)) {
                            UtilsMethods.unAuthorizeAlert(getActivity(), message);
                        } else {
                            CommonUtils.showSimpleMessageBottom(getActivity(), message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                CommonUtils.showSimpleMessageBottom(getActivity(), error);
            }
        });
        webServiceCall.execute(jsonObject, ServiceUrls.EDIT_EXPERIENCE);
    }


    /**
     * call service for delete the experience
     * @param addView       view which have delete
     * @param experience_id
     */
    private void callWebServiceForDeleteExperience(View addView,
                                                   String experience_id) {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(Keys.EXPERIENCE_ID, experience_id);
        retrofit.Call<GetDeleteExperience> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getDeleteExperienceResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetDeleteExperience>() {
            @Override
            public void onResponse(retrofit.Response<GetDeleteExperience> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetDeleteExperience getDeleteExperience = response.body();
                if (getDeleteExperience.isSuccess()) {
                    ((LinearLayout) addView.getParent()).removeView(addView);
                    CommonUtils.showSimpleMessageBottom(getActivity(), getDeleteExperience.getMsg());
                    Intent intentVolume = new Intent(Keys.PROFILE);
                    getActivity().sendBroadcast(intentVolume);
                } else {
                    if (getDeleteExperience.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getDeleteExperience.getMsg());
                    } else {
                        CommonUtils.showSimpleMessageBottom(getActivity(), getDeleteExperience.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });
    }


    /** show message on alert */
    public void openAlert(String message) {
        final Dialog dialog = new Dialog(getActivity());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert_ok);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonOk = dialog.findViewById(R.id.buttonOk);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonOk.setOnClickListener(view -> {
            dialog.dismiss();
            (getActivity()).onBackPressed();
        });
        dialog.show();
    }


    /**
     * add dynamic view for add multiple language
     * @param isAddBundleData if data is coming from bundle then data will autofill in the field.
     * @param pos             if isAddedBundle is true then pos is for getting the data from the list.
     *                        http://android-er.blogspot.in/2013/05/add-and-remove-view-dynamically.html
     */
    private void addViewExperience(boolean isAddBundleData, int pos) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.layout_add_experience, null);
        LinearLayout linearWithExp = addView.findViewById(R.id.linearWithExp);
        CustomsButton btnAdd = addView.findViewById(R.id.btnAdd);
        CustomsButton btnRemove = addView.findViewById(R.id.btnRemove);
        CustomsTextView textViewPostHeld = addView.findViewById(R.id.textViewPostHeld);
        CustomsTextView textViewPostHeldCategory = addView.findViewById(R.id.textViewPostHeldCategory);
        tvCountDescription = addView.findViewById(R.id.tvCountDescription);
        CustomRadioButton rbWithout = addView.findViewById(R.id.rbWithout);
        CustomRadioButton rbOne = addView.findViewById(R.id.rbOne);
        CustomRadioButton rbOneTwo = addView.findViewById(R.id.rbOneTwo);
        CustomRadioButton rbThreeFour = addView.findViewById(R.id.rbThreeFour);
        CustomRadioButton rbFiveMore = addView.findViewById(R.id.rbFiveMore);
        CustomsTextView textViewExpIndi = addView.findViewById(R.id.textViewExpIndi);
        EditText edCompanyName = addView.findViewById(R.id.edCompanyName);
        edCompanyName.setSingleLine(true);
        EditText edDesc = addView.findViewById(R.id.edDesc);

        // make first radio view checked
       /* rbWithout.setChecked(true);
        changeTextColor(rbWithout, rbOne, rbOneTwo, rbThreeFour, rbFiveMore);*/

        rbOne.setChecked(true);
        linearWithExp.setVisibility(View.VISIBLE);
        changeTextColor(rbOne, rbWithout, rbOneTwo, rbThreeFour, rbFiveMore);


        //add bundle data
        if (isAddBundleData) {
            textViewPostHeld.setText(dataBean.get(pos).getPosition_held());
            textViewPostHeldCategory.setText(dataBean.get(pos).getIndustry_type());
            edCompanyName.setText(dataBean.get(pos).getCompany_name());
            edDesc.setText(dataBean.get(pos).getDescription());
            tvCountDescription.setText(dataBean.get(pos).getDescription().length() + "/200");
            switch (dataBean.get(pos).getExperience()) {
                case "1":
                    rbWithout.setChecked(true);
                    linearWithExp.setVisibility(View.GONE);
                    changeTextColor(rbWithout, rbOne, rbOneTwo, rbThreeFour, rbFiveMore);
                    break;
                case "2":
                    rbOne.setChecked(true);
                    linearWithExp.setVisibility(View.VISIBLE);
                    changeTextColor(rbOne, rbWithout, rbOneTwo, rbThreeFour, rbFiveMore);
                    break;
                case "3":
                    rbOneTwo.setChecked(true);
                    linearWithExp.setVisibility(View.VISIBLE);
                    changeTextColor(rbOneTwo, rbOne, rbWithout, rbThreeFour, rbFiveMore);
                    break;
                case "4":
                    rbThreeFour.setChecked(true);
                    linearWithExp.setVisibility(View.VISIBLE);
                    changeTextColor(rbThreeFour, rbOne, rbOneTwo, rbWithout, rbFiveMore);
                    break;
                case "5":
                    rbFiveMore.setChecked(true);
                    linearWithExp.setVisibility(View.VISIBLE);
                    changeTextColor(rbFiveMore, rbOne, rbOneTwo, rbThreeFour, rbWithout);
                    break;
            }
        }

        // set typeface to editText
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "HelveticaNeueLTCom-LtEx.ttf");
        edCompanyName.setTypeface(font);
        edDesc.setTypeface(font);

        // listener
        edDesc.addTextChangedListener(new MyTextWatcher(tvCountDescription));

        if (linearLayoutAddExperinec.getChildCount() > 0) {
            btnRemove.setVisibility(View.VISIBLE);
           /* View childView = linearLayoutAddExperinec.getChildAt(1);
            CustomsButton btnRemove2= (CustomsButton) childView.findViewById(R.id.btnRemove);
            btnRemove2.setVisibility(View.VISIBLE);*/
        } else {
            btnRemove.setVisibility(View.INVISIBLE);
        }

        btnRemove.setOnClickListener(v -> {
            if (getArguments().containsKey(Keys.TYPE))
            {
                ((LinearLayout) addView.getParent()).removeView(addView);

                return;
            }
            if (isAddBundleData) {
                callWebServiceForDeleteExperience(addView, dataBean.get(pos).getExperience_id());
            } else {
                ((LinearLayout) addView.getParent()).removeView(addView);
                if (linearLayoutAddExperinec.getChildCount() > 0) {
                    btnRemove.setVisibility(View.VISIBLE);
                } else {
                    btnRemove.setVisibility(View.INVISIBLE);
                }
            }
        });
        // btnRemove.setOnClickListener(v -> ((LinearLayout) addView.getParent()).removeView(addView));
        btnAdd.setOnClickListener(v -> {
            addViewExperience(false, 0);
            new Handler().postDelayed(() -> {
                scrollView.smoothScrollTo(0, linearLayoutAddExperinec.getBottom());
            }, 500);

        });
        textViewPostHeld.setOnClickListener(v -> activityAreaDialog(textViewPostHeld, textViewPostHeldCategory));

        rbWithout.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked) {
                linearWithExp.setVisibility(View.GONE);
                changeTextColor(rbWithout, rbOne, rbOneTwo, rbThreeFour, rbFiveMore);
            }
        });

        rbOne.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                linearWithExp.setVisibility(View.VISIBLE);
                changeTextColor(rbOne, rbWithout, rbOneTwo, rbThreeFour, rbFiveMore);
            }
        });

        rbOneTwo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                linearWithExp.setVisibility(View.VISIBLE);
                changeTextColor(rbOneTwo, rbOne, rbWithout, rbThreeFour, rbFiveMore);
            }
        });

        rbThreeFour.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                linearWithExp.setVisibility(View.VISIBLE);
                changeTextColor(rbThreeFour, rbOne, rbWithout, rbOneTwo, rbFiveMore);
            }
        });

        rbFiveMore.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                linearWithExp.setVisibility(View.VISIBLE);
                changeTextColor(rbFiveMore, rbOne, rbWithout, rbOneTwo, rbThreeFour);
            }
        });

        linearLayoutAddExperinec.addView(addView);
        LayoutTransition transition = new LayoutTransition();
        linearLayoutAddExperinec.setLayoutTransition(transition);
    }


    /** get experience from fields */
    private void getExperienceArray()
    {
        String[] arrayPositionHeld = new String[linearLayoutAddExperinec.getChildCount()];
        String[] arrayPositionHeldCategory = new String[linearLayoutAddExperinec.getChildCount()];
        String[] arrayCompanyName = new String[linearLayoutAddExperinec.getChildCount()];
        String[] arrayDesc = new String[linearLayoutAddExperinec.getChildCount()];
        String[] arrayExperience = new String[linearLayoutAddExperinec.getChildCount()];

        String[] position_held_name = new String[linearLayoutAddExperinec.getChildCount()];
        String[] industry_type_name = new String[linearLayoutAddExperinec.getChildCount()];

        boolean isCallService = false;

        JSONArray jArry = new JSONArray();
        for (int i = 0; i < linearLayoutAddExperinec.getChildCount(); i++) {
            View childView = linearLayoutAddExperinec.getChildAt(i);

            EditText edCompanyName = childView.findViewById(R.id.edCompanyName);
            arrayCompanyName[i] = edCompanyName.getText().toString().trim();

            EditText edDesc = childView.findViewById(R.id.edDesc);
            arrayDesc[i] = edDesc.getText().toString().trim();

            CustomsTextView textViewPostHeld = childView.findViewById(R.id.textViewPostHeld);
            arrayPositionHeld[i] = textViewPostHeld.getText().toString().trim();
            position_held_name[i] = textViewPostHeld.getText().toString().trim();

            CustomsTextView textViewPostHeldCategory = childView.findViewById(R.id.textViewPostHeldCategory);
            arrayPositionHeldCategory[i] = textViewPostHeldCategory.getText().toString().trim();
            industry_type_name[i] = textViewPostHeldCategory.getText().toString().trim();

            for (int j = 0; j < getPositionDataResponce.getIndustryTypes().size(); j++) {

                if (arrayPositionHeldCategory[i].equalsIgnoreCase(getPositionDataResponce
                        .getIndustryTypes().get(j).getIndustry_type_name())) {
                    arrayPositionHeldCategory[i] = getPositionDataResponce
                            .getIndustryTypes().get(j).getIndustry_type_id();
                    /*System.out.println("dfsfeffe"+getPositionDataResponce
                            .getPositions().get(j).getPosition_id());*/

                    for (int k = 0; k < getPositionDataResponce
                            .getIndustryTypes().get(j).getPositions().size(); k++) {
                        if (getPositionDataResponce
                                .getIndustryTypes().get(j).getPositions().get(k).
                                        getPosition_name().equalsIgnoreCase(arrayPositionHeld[i])) {
                            arrayPositionHeld[i] = getPositionDataResponce
                                    .getIndustryTypes().get(j).getPositions().get(k).
                                            getPosition_id();
       /* System.out.println("dfsfeffe"+getPositionDataResponce
                .getPositions().get(j).getArea_of_activities().get(k).
                        getPosition_id());*/
                            break;
                        }
                    }
                    break;

                }
            }
            CustomRadioButton rbWithout = childView.findViewById(R.id.rbWithout);
            CustomRadioButton rbOne = childView.findViewById(R.id.rbOne);
            CustomRadioButton rbOneTwo = childView.findViewById(R.id.rbOneTwo);
            CustomRadioButton rbThreeFour = childView.findViewById(R.id.rbThreeFour);
            CustomRadioButton rbFiveMore = childView.findViewById(R.id.rbFiveMore);


            String expr = "";
            if (rbWithout.isChecked()) {
                expr = "1";
            } else if (rbOne.isChecked()) {
                expr = "2";
            } else if (rbOneTwo.isChecked()) {
                expr = "3";
            } else if (rbThreeFour.isChecked()) {
                expr = "4";
            } else if (rbFiveMore.isChecked()) {
                expr = "5";
            }
            arrayExperience[i] = expr;

            // no validation for non experience
            if (!expr.equals("1")) {
                if (validateForm(textViewPostHeld, edCompanyName, edDesc)) {
                    isCallService = true;
                } else {
                    isCallService = false;
                    break;
                }
            } else {
                isCallService = true;
            }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("position_held", Integer.parseInt(arrayPositionHeld[i]));
                    jsonObject.put("industry_type", Integer.parseInt(arrayPositionHeldCategory[i]));
                    jsonObject.put("company_name", arrayCompanyName[i]);
                    jsonObject.put("description", arrayDesc[i]);
                    jsonObject.put("experience", Integer.parseInt(arrayExperience[i]));
                    jsonObject.put("position_held_name", position_held_name[i]);
                    jsonObject.put("industry_type_name", industry_type_name[i]);
                   if (!expr.equals("1"))
                        jArry.put(jsonObject);
                } catch (Exception e) {

                    e.printStackTrace();
                }

        }
        if (getArguments().containsKey(Keys.TYPE))
        {
            if(getArguments().getString(Keys.TYPE).equalsIgnoreCase("facebook_register"))
            {
                ((LoginFacebookSignUpSeekerActivity) getActivity()).jArry = jArry;
                ((LoginFacebookSignUpSeekerActivity) getActivity()).setExperiance();

                getActivity().getSupportFragmentManager().popBackStack();
            }
            else {
                ((LoginSignUpSeekerActivity) getActivity()).jArry = jArry;
                ((LoginSignUpSeekerActivity) getActivity()).setExperiance();

                getActivity().getSupportFragmentManager().popBackStack();
            }

            return;
        }
        if (isCallService)
            callWebServiceForMyEditExperience(jArry);
    }

    /** validate from */
    private boolean validateForm(CustomsTextView position_held, EditText company_name, EditText desc) {
        if (UtilsMethods.isEmpty(position_held)) {
            position_held.setHint(getResources().getString(R.string.select_pos_held));
            position_held.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.red_color));
            requestFocus(position_held);
            return false;
        }
        if (UtilsMethods.isEmpty(company_name)) {
            showErrorAlert(getActivity(),
                    getResources().getString(R.string.company_name_err));

                    requestFocus(company_name);
            return false;
        }
        /*if (UtilsMethods.isEmpty(desc)) {
            desc.setError(getResources().getString(R.string.desc_err));
            requestFocus(desc);
            return false;
        }*/
        return true;
    }

    /**
     * focus to view which view have invalid.
     * @param view
     */
    private void requestFocus(View view)
    {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /** text count for discription */
    public static class MyTextWatcher implements TextWatcher
    {
        private CustomsTextView mEditText;

        public MyTextWatcher(CustomsTextView editText)
        {
            mEditText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            mEditText.setText(s.length() + "/200");
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

   /* *//** text count for discription *//*
    private final TextWatcher textWatcherDesc = new TextWatcher(EditText editText) {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            View childView = linearLayoutAddExperinec.getChildAt(i);
            CustomRadioButton customRadioButton=(CustomRadioButton) buttonView.findViewById(R.id.rbWithout);
            EditText edDesc = (EditText) addView.findViewById(R.id.edDesc);
            tvCountDescription.setText(s.length() + "/200");
        }

        public void afterTextChanged(Editable s) {
        }
    };*/


    /**
     * method for open dialog post held
     * @param textViewPostHelds
     * @param textViewPostHeldCategory
     */
    public void activityAreaDialog(CustomsTextView textViewPostHelds, CustomsTextView textViewPostHeldCategory) {
        final Dialog dialogActivityArea = new Dialog(getActivity());
        dialogActivityArea.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogActivityArea.getWindow() != null)
            dialogActivityArea.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogActivityArea.setContentView(R.layout.dialog_activity_area);
        ListView listView = (ListView) dialogActivityArea.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) dialogActivityArea.findViewById(R.id.imageViewClose);
      //  String[] mTestArray = getResources().getStringArray(R.array.array_active_area);
        String[] mTestArray=  new String[getPositionDataResponce.getIndustryTypes()
                .size()];
        for(int i=0;i< getPositionDataResponce.getIndustryTypes().size();i++)
        {
            mTestArray[i]=getPositionDataResponce.getIndustryTypes().get(i).getIndustry_type_name();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.single_item_list, mTestArray);
        listView.setAdapter(itemsAdapter);
        dialogActivityArea.show();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            postHeldDialog(textViewPostHelds, textViewPostHeldCategory, mTestArray[position], position);
            dialogActivityArea.dismiss();
        });
        imageViewClose.setOnClickListener(v -> dialogActivityArea.dismiss());
    }

    /**
     * method for open dialog post held
     * @param textViewPostHelds
     * @param textViewPostHeldCategory
     * @param title
     * @param pos
     */
    public void postHeldDialog(CustomsTextView textViewPostHelds, CustomsTextView textViewPostHeldCategory, String title, int pos) {
        final Dialog dialogpostHeld = new Dialog(getActivity());
        dialogpostHeld.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogpostHeld.getWindow() != null)
            dialogpostHeld.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogpostHeld.setContentView(R.layout.dialog_position_held);
        ListView listView = (ListView) dialogpostHeld.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) dialogpostHeld.findViewById(R.id.imageViewClose);
        ImageView imageViewBack = (ImageView) dialogpostHeld.findViewById(R.id.imageViewBack);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) dialogpostHeld.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        String[] mTestArray;
      //  if (pos == 0) {
          mTestArray = new String[getPositionDataResponce.getIndustryTypes()
                    .get(pos).getPositions().size()];
            for (int i = 0; i < getPositionDataResponce.getIndustryTypes()
                    .get(pos).getPositions().size(); i++)
            {
                mTestArray[i] =
                        getPositionDataResponce.getIndustryTypes()
                                .get(pos).getPositions().get(i).getPosition_name();

                // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
            }
            // mTestArray = getResources().getStringArray(R.array.array_restaurant);
       /* } else if (pos == 1) {
            mTestArray = getResources().getStringArray(R.array.array_sale_service);
        }
        else {
            mTestArray = getResources().getStringArray(R.array.array_hotel);
        }
*/

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.single_item_list_post_held, mTestArray);
        listView.setAdapter(itemsAdapter);
        dialogpostHeld.show();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //  CustomsTextView textViewPostHeld = (CustomsTextView) parent.findViewById(R.id.textViewPostHeld);
            textViewPostHeldCategory.setText(title);
            textViewPostHelds.setText(mTestArray[position]);
            textViewPostHelds.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            textViewPostHelds.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            dialogpostHeld.dismiss();
        });
        imageViewClose.setOnClickListener(v -> {
            dialogpostHeld.dismiss();
            activityAreaDialog(textViewPostHelds, textViewPostHeldCategory);
        });
        imageViewBack.setOnClickListener(v -> {
            dialogpostHeld.dismiss();
            activityAreaDialog(textViewPostHelds, textViewPostHeldCategory);
        });
    }

    /** change the textcolor for tab bar */
    private void changeTextColor(CustomRadioButton r1, CustomRadioButton r2, CustomRadioButton r3, CustomRadioButton r4, CustomRadioButton r5) {
        r1.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        r2.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        r3.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        r4.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        r5.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
    }




    GetAllDropDownResponce getPositionDataResponce;

  /*  private void serviceForGetpostHeldData()
    {
        final SpotsDialog spotsDialog = new
                SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();

        retrofit.Call<GetPositionDataResponce> call = AppRetrofit.getAppRetrofitInstance().
                getApiServices().getGetPositionDataResponce
                (jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetPositionDataResponce>() {
            @Override
            public void onResponse(retrofit.Response<GetPositionDataResponce> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                 getPositionDataResponce = response.body();

                if (getPositionDataResponce.isSuccess())
                {

                    getBundleData();
                } else {
                    if (getPositionDataResponce.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getPositionDataResponce.getMsg());
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
