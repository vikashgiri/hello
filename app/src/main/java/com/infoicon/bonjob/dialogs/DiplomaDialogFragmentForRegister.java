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
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;
import com.infoicon.bonjob.utils.Keys;


/**
 * Created by infoicon on 15/7/17.
 * this class is for getting salary list  and showing it in dialog.
 */

public class DiplomaDialogFragmentForRegister extends Activity {
    JsonArray array;
    GetAllDropDownResponce jobSearchDropDownResponce;


    String pos;
    ArrayAdapter<String> arrayAdapter;

   /* @Nullable
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
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_type_of_contract);
        getBundleData();
        init();
    }

    /**
     * get intents data
     */
    private void getBundleData() {


            jobSearchDropDownResponce = Singleton.getUserInfoInstance().getAllDropdowns();
        }



    /**
     * init view
     */
    private void init() {
        ListView listView = (ListView) findViewById(R.id.listView);
        Button ok = (Button) findViewById(R.id.buttonOk);

            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ok.setVisibility(View.GONE);

        ImageView imageViewClose = (ImageView) findViewById(R.id.imageViewClose);
        CustomsTextViewBold tvTitle = (CustomsTextViewBold) findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.diploma));
       // String[] mTestArray = getResources().getStringArray(R.array.education_level);

        //  String[] mTestArray = getResources().getStringArray(R.array.array_experience);
        String[] mTestArray=  new String[jobSearchDropDownResponce.getEducationLevels()
                .size()];
        for(int i=0;i< jobSearchDropDownResponce.getEducationLevels().size();i++)
        {
            mTestArray[i]=jobSearchDropDownResponce.getEducationLevels().get(i).getEducation_title();

            // String[] mTestArray = getResources().getStringArray(R.array.array_type_of_contract);
        }
        arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.list_item_single_choice, mTestArray);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener((parent, views, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra(Keys.DIPLOMA_ID,
                    jobSearchDropDownResponce.getEducationLevels().get(position).getEducation_id());
            intent.putExtra(Keys.DIPLOMA, mTestArray[position]);
            setResult(Activity.RESULT_OK,intent);
            finish();
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

    }


}
