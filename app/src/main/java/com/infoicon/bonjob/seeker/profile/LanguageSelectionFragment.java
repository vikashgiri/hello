package com.infoicon.bonjob.seeker.profile;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.main.HomeSeekerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class LanguageSelectionFragment extends Fragment {


    View rootView;
    @BindView(R.id.listView) ListView listView;
    private String[] array_list_title;
    int pos = 0;
    CustomsTextView tvLanguage;
    public LanguageSelectionFragment(CustomsTextView tvLanguage) {
        this.tvLanguage=tvLanguage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_language_selection, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    @OnClick(R.id.textViewBack)
    void Goback() {
        ((HomeSeekerActivity) getActivity()).onBackPressed();
    }

    @OnClick(R.id.tvSave)
    void save() {
        String selected_item = array_list_title[pos];
        if (!selected_item.equals("")) {
            tvLanguage.setText(selected_item);
        }
        ((HomeSeekerActivity) getActivity()).onBackPressed();
    }

    private void initView() {

       // array_list_title = getResources().getStringArray(R.array.education_level);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_single_choice, array_list_title);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(arrayAdapter);
        listView.setSelection(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
        });

    }
}
