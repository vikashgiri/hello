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
import android.widget.ImageView;
import android.widget.ListView;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.seeker.profile.GetEmpProfileDropdownsResponce;
import com.infoicon.bonjob.utils.Keys;


/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class CompanyCategoryDialogFragment extends DialogFragment
{
    private View rootView;
    String pos;
    String[] mTestArray;
    GetEmpProfileDropdownsResponce getEmpProfileDropdownsResponce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rootView = inflater.inflate(R.layout.dialog_type_of_contract, container, false);
        
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        init();
    }

    /** get intents data */
    private void getBundleData()
    {
        Bundle bundle = getArguments();
        if (bundle != null) {
            pos = bundle.getString(Keys.SELECTED_POSITION);
            getEmpProfileDropdownsResponce = bundle.getParcelable(Keys.DATA);

        }}

    /** init view */
    private void init()
    {
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) rootView.findViewById(R.id.imageViewClose);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.company_category2));

        mTestArray=  new String[getEmpProfileDropdownsResponce.
                getCompanyCategories()
                .size()];
        for(int i=0;i< getEmpProfileDropdownsResponce.getCompanyCategories().size();i++)
        {
            mTestArray[i]=getEmpProfileDropdownsResponce.getCompanyCategories().get(i)
                    .getCompany_category_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
      //  String[] mTestArray = getResources().getStringArray(R.array.array_com_category);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, mTestArray) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
             //   listView.setItemChecked(position, position == pos);
                return v;
            }
        };
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);

        //get data from list
        listView.setOnItemClickListener((parent, views, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra(Keys.POSITION, position);
            intent.putExtra(Keys.COMPANY_CAEGORY, mTestArray[position]);
            intent.putExtra(Keys.COMPANY_CAEGORY_Id,
                    getEmpProfileDropdownsResponce.getCompanyCategories().get(position)
            .getCompany_category_id());
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            getDialog().dismiss();
        });

        //dismiss dialog
        imageViewClose.setOnClickListener(v -> getDialog().dismiss());
    }


}
