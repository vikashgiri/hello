package com.infoicon.bonjob.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.recruiters.search.GetTargetSearchResponce;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;


/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class LanguageDialogRecruiterFragment extends DialogFragment {


    private View rootView;
    String pos ;
    private JsonArray array;
    GetAllDropDownResponce jobSearchDropDownResponce;

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
            pos = bundle.getString(Keys.SELECTED_POSITION);
            jobSearchDropDownResponce = bundle.getParcelable("CompanyDetails");

        }
        }

    /** init view */
    private void init() {
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) rootView.findViewById(R.id.imageViewClose);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.profilefirstcell_language));

        Button ok = (Button) rootView.findViewById(R.id.buttonOk);
        Bundle bundle = getArguments();

        if (bundle.containsKey(Keys.TYPE)) {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ok.setVisibility(View.VISIBLE);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ok.setVisibility(View.GONE);
        }


     //   String[] mTestArray = getResources().getStringArray(R.array.array_language);
        String[] mTestArray=  new String[jobSearchDropDownResponce.getJobLanguages()
                .size()];
        for(int i=0;i< jobSearchDropDownResponce.getJobLanguages().size();i++)
        {
            mTestArray[i]=jobSearchDropDownResponce.getJobLanguages().get(i).getJob_language_title();
            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, mTestArray) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                try {


                if (pos != null) {
                    if (bundle.containsKey(Keys.TYPE)) {

                        String posi[] = pos.split("#");
                        for (int i = 0; i < posi.length; i++) {
                            if (position == Integer.parseInt(posi[i])) {
                                listView.setItemChecked(position, true);
                            }
                        }
                    }else{
                        if(Integer.parseInt(pos)==position){
                            listView.setItemChecked(position, true);
                        }
                    }}}catch (Exception e)
                {
                    e.printStackTrace();
                }
                return v;
            }
        };
        listView.setAdapter(arrayAdapter);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                if (checked.size() > 0) {
                    array = new JsonArray();
                    String str = null;
                    String value=null;
                    String valueId=null;
                    for (int i = 0; i < checked.size(); i++) {
                        // Item position in adapter
                        int position = checked.keyAt(i);
                        // Add sport if it is checked i.e.) == TRUE!
                        if (checked.valueAt(i)) {
                            Gson gson = new Gson();
                            array.add(arrayAdapter.getItem(position));
                            if (str == null) {
                                str = "" + position;
                                value=arrayAdapter.getItem(position);
                                valueId=jobSearchDropDownResponce.getJobLanguages().get(position)
                                        .getJob_language_id();
                            } else {
                                str = str + "#" + position;
                                value=value + ","+arrayAdapter.getItem(position);
                                valueId=valueId+ ","+jobSearchDropDownResponce.
                                        getJobLanguages().get(position)
                                        .getJob_language_id();
                            }
                        }
                    }
                  //  if (array.size() > 0) {
                        Intent intent = new Intent();
                        intent.putExtra(Keys.POSITION, str);
                    intent.putExtra(Keys.LANGUAGE_ID, valueId);

                    intent.putExtra(Keys.LANGUAGE,value);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        getDialog().dismiss();
                   // } else {
                        getDialog().dismiss();
                   // }
                }
            }

        });

        if (!bundle.containsKey(Keys.TYPE)) {
            //get data from list
            listView.setOnItemClickListener((parent, views, position, id) -> {
                Intent intent = new Intent();
                intent.putExtra(Keys.POSITION, position + "");
                intent.putExtra(Keys.LANGUAGE, mTestArray[position]);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            });
        }
        //dismiss dialog
        imageViewClose.setOnClickListener(v -> getDialog().dismiss());
    }


}
