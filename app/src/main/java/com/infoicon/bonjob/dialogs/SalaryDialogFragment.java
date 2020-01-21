package com.infoicon.bonjob.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.recruiters.post.GetJobpostDropDown;
import com.infoicon.bonjob.seeker.searchJob.JobSearchDropDownResponce;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;


/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class SalaryDialogFragment extends DialogFragment {

    private View rootView;
    int pos = 0;
    GetAllDropDownResponce getJobpostDropDown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rootView = inflater.inflate(R.layout.dialog_type_of_contract, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        init();
    }

    /** get intents data */
    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            pos = bundle.getInt(Keys.SELECTED_POSITION);
            getJobpostDropDown = bundle.getParcelable("CompanyDetails");
        }
    }

    /** init view */
    private void init() {
        ListView listView = rootView.findViewById(R.id.listView);
        ImageView imageViewClose = rootView.findViewById(R.id.imageViewClose);
        CustomsTextViewBold tvTitle = rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.salary));
    //    String[] mTestArray = getResources().getStringArray(R.array.array_salary);

        String[] mTestArray=  new String[getJobpostDropDown.getSalaries()
                .size()];
        for(int i=0;i< getJobpostDropDown.getSalaries().size();i++)
        {
            mTestArray[i]=getJobpostDropDown.getSalaries().get(i).getSalary_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, mTestArray) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if(pos>=0&&pos==position){
                    listView.setItemChecked(position, true);
                }
                // listView.setItemChecked(position, position == pos);
                return v;
            }
        };
        Button ok = (Button) rootView.findViewById(R.id.buttonOk);
        Bundle bundle = getArguments();

        if (bundle.containsKey(Keys.TYPE)) {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ok.setVisibility(View.VISIBLE);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ok.setVisibility(View.GONE);
        }
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);

        //get data from list
        listView.setOnItemClickListener((parent, views, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra(Keys.POSITION, position);
            intent.putExtra(Keys.SALARY, mTestArray[position]);
            intent.putExtra(Keys.SALARY_ID, getJobpostDropDown.
                    getSalaries().get(position)
            .getSalary_id());
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            getDialog().dismiss();
        });

        //dismiss dialog
        imageViewClose.setOnClickListener(v -> getDialog().dismiss());
    }
}
