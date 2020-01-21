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
import com.infoicon.bonjob.seeker.searchJob.JobSearchDropDownResponce;
import com.infoicon.bonjob.seeker.searchJob.MoreFilterFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;

import java.util.List;


/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class TypeOfContractDialogFragment extends DialogFragment {


    private View rootView;
    String pos ;
    private JsonArray array;
    private JsonArray arrayId;
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

            jobSearchDropDownResponce =Singleton.getUserInfoInstance().getAllDropdowns();
        }
    }

    /** init view */
    private void init() {
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        ImageView imageViewClose = (ImageView) rootView.findViewById(R.id.imageViewClose);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) rootView.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.type_of_contract));

        String[] mTestArray=  new String[jobSearchDropDownResponce.getContractTypes().size()];;
        for(int i=0;i< jobSearchDropDownResponce
                .getContractTypes().size();i++)
        {
            mTestArray[i]=jobSearchDropDownResponce.getContractTypes().get(i).getContract_title();

           // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }



        Button ok = (Button) rootView.findViewById(R.id.buttonOk);
        Bundle bundle = getArguments();

        if (bundle.containsKey(Keys.TYPE)) {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ok.setVisibility(View.VISIBLE);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ok.setVisibility(View.GONE);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_single_choice, mTestArray) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
              try{
                  if (pos != null)
                  {
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
                }
                  }
              }catch (Exception e)
              {
                  e.printStackTrace();
              }
                return v;
            }
        };

        listView.setAdapter(arrayAdapter);
        listView.setAdapter(arrayAdapter);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                if (checked.size() > 0) {
                    array = new JsonArray();
                    String str = null;
                    String valueId = null;
                    String value=null;
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
                                valueId=jobSearchDropDownResponce.getContractTypes().get(position)
                                        .getContract_id();


                            } else {
                                str = str + "#" + position;
                                value=value + ","+arrayAdapter.getItem(position);
                                valueId=valueId+ ","+jobSearchDropDownResponce.getContractTypes().get(position)
                                        .getContract_id();
                            }
                        }
                    }
                    //if (array.size() > 0) {
                        Intent intent = new Intent();
                        intent.putExtra(Keys.POSITION, str);
                        intent.putExtra(Keys.TYPE_OF_CONTRACT, value);
                        intent.putExtra(Keys.TYPE_OF_CONTRACT_ID, valueId);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        getDialog().dismiss();
                  //  } else {
                   //     getDialog().dismiss();
                   // }
                }
            }

        });

        if (!bundle.containsKey(Keys.TYPE)) {
            //get data from list
            listView.setOnItemClickListener((parent, views, position, id) ->

            {
                Intent intent = new Intent();
                intent.putExtra(Keys.POSITION, position+"");
                intent.putExtra(Keys.TYPE_OF_CONTRACT, mTestArray[position]);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            });
        }
        //dismiss dialog
        imageViewClose.setOnClickListener(v -> getDialog().dismiss());
    }


}
