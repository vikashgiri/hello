package com.infoicon.bonjob.recruiters.myOffer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.ChatListActivity;
import com.infoicon.bonjob.chatModule.chatMain.ChatFragment;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.utils.Keys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class OfferTabFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();
    private View rootView;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    private CustomRadioButton tabOne, tabTwo, tabThree, tabFour;
    private Context context;
    private Unbinder unbinder;
    private CustomsTextView tvItemsCountOne;
    private CustomsTextView tvItemsCountTwo;
    private CustomsTextView tvItemsCountThree;
    private CustomsTextView tvItemsCountFour;

    private int pos=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_offer_tab, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getActivity();  // <--- add this line here
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        if(getArguments().containsKey(Keys.POSITION)){
            pos=getArguments().getInt(Keys.POSITION);
            createTabIcons(pos);
            initListeners();
            viewPager.setCurrentItem(pos);
            changeTab(pos);
        }else{
            createTabIcons(pos);
            initListeners();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.MY_OFFERS);
        getActivity().registerReceiver(broadcastReceiverProfileUpdate, new IntentFilter(Keys.MY_OFFERS));
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiverProfileUpdate != null)
            getActivity().unregisterReceiver(broadcastReceiverProfileUpdate);
        super.onDestroy();
    }

    /**
     * setup view pager
     * add fragment to viewpager
     * @param viewPager instance
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        adapter.addFrag(new CandidatesFragment(), "");
        adapter.addFrag(new SelectedFragment(), "");
        adapter.addFrag(new HiredFragment(), "");
        adapter.addFrag(new MyOfferRecruiterFragment(), "");
        viewPager.setAdapter(adapter);


    }

    /**
     * select tab
     * @param tabIndex index of the tag which one will select
     * @param position position to remove from candidate list if user have selected
     */
    public void selectTab(int tabIndex, int position) {
        if (tabLayout != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(tabIndex);
            assert tab != null;
            tab.select();
            viewPager.setCurrentItem(tabIndex);
            if (tabIndex == 1) {
                getActivity().sendBroadcast(new Intent(Keys.APPLIED_CANDIDATE).putExtra(Keys.POSITION, position));
                getActivity().sendBroadcast(new Intent(Keys.SELECTED_CANDIDATE));
            } else if (tabIndex == 2) {
                getActivity().sendBroadcast(new Intent(Keys.HIRE));
                getActivity().sendBroadcast(new Intent(Keys.SELECTED_CANDIDATE));
            }
        }

    }

    /** listener for tab change */
    public void initListeners() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                changeTab(tab.getPosition());
                Logger.e(TAG + " onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Logger.e(TAG + " onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Logger.e(TAG + " onTabReselected");
            }
        });
    }


    /**
     * receive status if any changes in profile update
     */
    BroadcastReceiver broadcastReceiverProfileUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO: 1/2/17 Get music related information.
            if (getActivity() != null && isAdded()) {
                int pos = intent.getIntExtra(Keys.POSITION, 0);
                int val = intent.getIntExtra(Keys.VALUE, 0);
                setCount(pos, val);
            }

        }
    };

    /** change tab layout */
    private void changeTab(int desiredPosition) {
        switch (desiredPosition) {
            case 0:
                change(tabOne, tabTwo, tabThree, tabFour);
                changeTv(tvItemsCountOne, tvItemsCountTwo, tvItemsCountThree, tvItemsCountFour);
                break;
            case 1:

                change(tabTwo, tabOne, tabThree, tabFour);
                changeTv(tvItemsCountTwo, tvItemsCountOne, tvItemsCountThree, tvItemsCountFour);
                break;
            case 2:

                change(tabThree, tabTwo, tabOne, tabFour);
                changeTv(tvItemsCountThree, tvItemsCountTwo, tvItemsCountOne, tvItemsCountFour);
                break;
            case 3:

                change(tabFour, tabTwo, tabThree, tabOne);
                changeTv(tvItemsCountFour, tvItemsCountTwo, tvItemsCountThree, tvItemsCountOne);
                break;
        }
    }

    /** view for change the effect of selection */
    private void change(CustomRadioButton tabOne, CustomRadioButton tabTwo, CustomRadioButton tabThree, CustomRadioButton tabFour) {
        tabOne.setChecked(true);
        tabOne.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        tabTwo.setChecked(false);
        tabTwo.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        tabThree.setChecked(false);
        tabThree.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        tabFour.setChecked(false);
        tabFour.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
    }

    /** view for change the effect of selection */
    private void changeTv(CustomsTextView tabOne, CustomsTextView tabTwo, CustomsTextView tabThree, CustomsTextView tabFour) {
        tabOne.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        tabTwo.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        tabThree.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
        tabFour.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
    }

    /**
     * set the count for index
     * @param tabPos index of the tab
     * @param value  count value for the tab
     */
    public void setCount(int tabPos, int value) {
        //if (getActivity() != null  && isAdded()) {
        switch (tabPos) {
            case 0:
                //   countTabOne=value;
                tvItemsCountOne.setText(String.valueOf(value));
                break;
            case 1:
                //  countTabTwo=value;
                tvItemsCountTwo.setText(String.valueOf(value));
                break;
            case 2:
                //  countTabThree=value;
                tvItemsCountThree.setText(String.valueOf(value));
                break;
            case 3:
                //  countTabFour=value;
                tvItemsCountFour.setText(String.valueOf(value));
                break;
        }
        // }
    }

    /**
     * create custom icon for tab layout
     * @param pos pos for selecting the tab indicator
     */
    private void createTabIcons(int pos) {
        //add view for candidate
        View convertView1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabs, null);
        tabOne = (CustomRadioButton) convertView1.findViewById(R.id.rbSelected);
        tvItemsCountOne = (CustomsTextView) convertView1.findViewById(R.id.tvItemsCount);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.radio_candidate, 0, 0);
        tabOne.setText(getResources().getString(R.string.candidate));
        if (pos == 0) {
            tabOne.setChecked(true);
            tabOne.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            tvItemsCountOne.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        } else tabOne.setChecked(false);
        TabLayout.Tab tab0 = tabLayout.getTabAt(0);
        assert tab0 != null;
        tab0.setCustomView(convertView1);

        //add view for selected candidate
        View convertView2 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabs, null);
        tabTwo = (CustomRadioButton) convertView2.findViewById(R.id.rbSelected);
        tvItemsCountTwo = (CustomsTextView) convertView2.findViewById(R.id.tvItemsCount);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.radio_selected, 0, 0);
        tabTwo.setText(getResources().getString(R.string.selected));
        if (pos == 1) {
            tabTwo.setChecked(true);
            tabTwo.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            tvItemsCountTwo.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        } else tabTwo.setChecked(false);
        TabLayout.Tab tab1 = tabLayout.getTabAt(1);
        assert tab1 != null;
        tab1.setCustomView(convertView2);

        //add view for hired candidate
        View convertView3 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabs, null);
        tabThree = (CustomRadioButton) convertView3.findViewById(R.id.rbSelected);
        tvItemsCountThree = (CustomsTextView) convertView3.findViewById(R.id.tvItemsCount);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.radio_hired, 0, 0);
        tabThree.setText(getResources().getString(R.string.hired));
        if (pos == 2) {
            tabThree.setChecked(true);
            tabThree.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            tvItemsCountThree.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        } else tabThree.setChecked(false);
        TabLayout.Tab tab2 = tabLayout.getTabAt(2);
        assert tab2 != null;
        tab2.setCustomView(convertView3);

        //add view for my offers
        View convertView4 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabs, null);
        tabFour = (CustomRadioButton) convertView4.findViewById(R.id.rbSelected);
        tvItemsCountFour = (CustomsTextView) convertView4.findViewById(R.id.tvItemsCount);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.radio_my_offer, 0, 0);
        tabFour.setText(getResources().getString(R.string.my_offers));
        if (pos == 3) {
            tabFour.setChecked(true);
            tabFour.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
            tvItemsCountFour.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_color));
        } else tabFour.setChecked(false);
        TabLayout.Tab tab3 = tabLayout.getTabAt(3);
        assert tab3 != null;
        tab3.setCustomView(convertView4);
    }

    /** view pager adapter to change the tab */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.CANDIDATE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra(Keys.POSITION, 0);
                selectTab(1, position);
            }
        }
        if (requestCode == Keys.CANDIDATE_HIRED_CODE) {
            if (resultCode == RESULT_OK) {
                String forData = data.getStringExtra(Keys.FOR);
                if (forData.equals(Keys.HIRE)) {
                    selectTab(2, 0);
                } else if (forData.equals(Keys.CHAT)) {
                   // new Thread(() -> {
                /*        ChatFragment chatFragment = new ChatFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.RECEIVER_ID, Keys.BONJOB_ + data.getStringExtra(Keys.CANDIDATE_ID));
                        bundle.putString(Keys.USER_ID, data.getStringExtra(Keys.CANDIDATE_ID));
                        bundle.putString(Keys.NAME, data.getStringExtra(Keys.FIRST_NAME) + " " + data.getStringExtra(Keys.LAST_NAME));
                        bundle.putString(Keys.USER_PIC, data.getStringExtra(Keys.USER_PIC));
                        bundle.putString(Keys.JOB_TITLE, data.getStringExtra(Keys.JOB_TITLE));
                        bundle.putString(Keys.JOB_IMAGE, data.getStringExtra(Keys.JOB_IMAGE));
                        bundle.putString(Keys.DESCRIPTION, data.getStringExtra(Keys.DESCRIPTION));
                    addFragment(new ChatListActivity(), new Bundle(), Keys.CHAT_LIST, false, true);*/

                    ((HomeRecruiterActivity) getActivity()).addFragment(new ChatListActivity(),
                            new Bundle(), Keys.DEFAULT_CHAT, false, true);

                   // }).start();
                }
            }
        }

        /*if (requestCode == Keys.VERIFY_PHONE) {
            if (resultCode == RESULT_OK) {
                String loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                if (loadPage.equals(Keys.PROFILE)) {//load profile
                    new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();

                } else {//load offer
                    new Thread(() -> ((HomeRecruiterActivity) getActivity()).addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true)).start();
                }

            }
        }*/
    }
}
