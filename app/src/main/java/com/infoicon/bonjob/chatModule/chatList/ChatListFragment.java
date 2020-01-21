package com.infoicon.bonjob.chatModule.chatList;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.recruiters.myOffer.OfferTabFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetChatListResponse;
import com.infoicon.bonjob.seeker.searchJob.SearchJobFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ChatListFragment extends Fragment {

    private View rootView;
    private Unbinder unbinder;
    private String TAG = this.getClass().getSimpleName();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.rlDefaultChatSeeker) RelativeLayout rlDefaultChatSeeker;
    @BindView(R.id.rlDefaultChatRecruiter) RelativeLayout rlDefaultChatRecruiter;
    @BindView(R.id.tvTitle) CustomsTextViewBold tvTitle;
    @BindView(R.id.rlRetry) RelativeLayout rlRetry;
    @BindView(R.id.tvErrorMessage) CustomsTextViewBold tvErrorMessage;
    @BindView(R.id.btnRetry) CustomsButton btnRetry;
    @BindView(R.id.etSearch) CustomEditText etSearch;
    @BindView(R.id.relativeSearch) RelativeLayout relativeSearch;
    private ChatListAdapter chatListAdapter, searchResultAdapter;

    private int page_no = 1;
    private boolean isAdmin = false;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 20;
    private boolean loading;

    private boolean isSearchActive = false;
    private int searchPageNo = 0;
   private int totalChatUserCount,totalChatListSize,totalSearchUserCount,totalSearchListSize;
  // private boolean hasMoreSearchData=true,hasMoreUser=true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceGetChatList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if (Singleton.getUserInfoInstance().isLoginRecriter()
                || Singleton.getUserInfoInstance().getLoginAdmin()) {
            ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        } else {
            ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        }
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Keys.UNREAD_STATUS));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (broadcastReceiver != null)
            getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
    }
/*

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (broadcastReceiver != null)
            getActivity().unregisterReceiver(broadcastReceiver);
    }
*/

    /** initialize view */
    private void initialization() {
        isAdmin = Singleton.getUserInfoInstance().getLoginAdmin();
        if (isAdmin) {
            relativeSearch.setVisibility(View.VISIBLE);
        } else {
            relativeSearch.setVisibility(View.GONE);
        }

        tvTitle.setText(getResources().getString(R.string.chat));
        tvTitle.setTextAppearance(getActivity(), R.style.boldText);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        chatListAdapter = new ChatListAdapter(getActivity());
        recyclerView.setAdapter(chatListAdapter);


        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        isSearchActive = false;
                        etSearch.setText("");
                        recyclerView.setAdapter(chatListAdapter);
                        return true;
                    }
                }
                return false;
            }
        });

        //handling search in case of admin login
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!etSearch.getText().toString().isEmpty()) {
                        totalSearchListSize=0;
                        totalSearchUserCount=0;
                        isSearchActive = true;
                        searchPageNo = 0;
                        callSearchUserApi(etSearch.getText().toString());
                    }
                    UtilsMethods.hideSoftKeyboard(getActivity());
                    return true;
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if(s.toString().isEmpty()){
                       recyclerView.setAdapter(chatListAdapter);
                       isSearchActive=false;
                     }else{
                 isSearchActive=true;
             }
            }
        });

        // handling pagination in case of admin login
        if (isAdmin) {

           recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            if (isSearchActive && (totalSearchListSize<totalSearchUserCount)) {
                                loading = true;
                                searchPageNo = searchPageNo + 1;
                                callSearchUserApi(etSearch.getText().toString());
                            } else {
                                if(!isSearchActive && (totalChatListSize<totalChatUserCount)){
                                    loading = true;
                                    page_no = page_no + 1;
                                    serviceGetChatList();
                                }
                            }

                        }
                    }
                }
            });
        }
    }

    /** redirect to the search jon screen */
    @OnClick(R.id.btnJobSearchSeeker)
    void searchJob() {
        ((HomeSeekerActivity) getActivity())
                .addFragment(new SearchJobFragment(), new Bundle(), Keys.SEARCH_JOB, false, true);
    }

    /** redirect to the search candidate screen */
    @OnClick(R.id.btnJobSearchRecruiter)
    void searchCandidate() {
        if (UtilsMethods.isValidForSearch(getActivity()))
            ((HomeRecruiterActivity) getActivity()).addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, true);
    }

    @OnClick(R.id.btnRetry)
    void retry() {
        serviceGetChatList();
    }


    /** call service for hired candidate */
    private void serviceGetChatList() {

        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        if (isAdmin) {
            jsonObject.addProperty(Keys.PAGE, page_no);
        }
        retrofit.Call<GetChatListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSearchChatUserResponse(jsonObject);

        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetChatListResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetChatListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                if (isAdmin) {
                    loading = false;
                }
                rlRetry.setVisibility(View.GONE);
                GetChatListResponse getChatListResponse = response.body();
                   totalChatUserCount=getChatListResponse.getTotalUsers();
                if (getChatListResponse.isSuccess()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    // rlDefaultChatSeeker.setVisibility(View.GONE);
                    showDefaultScreen(false);
                    for (GetChatListResponse.DataBean dataBean : getChatListResponse.getData()) {
                        chatListAdapter.addChatUser(dataBean);
                    }
                   totalChatListSize=totalChatListSize+getChatListResponse.getData().size();

                } else {
                    //rlDefaultChatSeeker.setVisibility(View.VISIBLE);
                    if (page_no == 1) {
                        showDefaultScreen(true);
                        recyclerView.setVisibility(View.GONE);
                    }
                    if (getChatListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getChatListResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(t.getMessage());
                recyclerView.setVisibility(View.GONE);
                // rlDefaultChatSeeker.setVisibility(View.GONE);
                showDefaultScreen(false);
                if (isAdmin && page_no > 1) {
                    page_no = page_no - 1;
                    loading = false;
                }
                //   CommonUtils.showSimpleMessageBottom(getActivity(), t.getMessage());
            }
        });

    }

    /** show default screen */
    private void showDefaultScreen(boolean status) {
        if (status) {
            if (Singleton.getUserInfoInstance().isLoginRecriter()) {
                rlDefaultChatSeeker.setVisibility(View.GONE);
                rlDefaultChatRecruiter.setVisibility(View.VISIBLE);
            } else {
                rlDefaultChatSeeker.setVisibility(View.VISIBLE);
                rlDefaultChatRecruiter.setVisibility(View.GONE);
            }
        } else {
            rlDefaultChatSeeker.setVisibility(View.GONE);
            rlDefaultChatRecruiter.setVisibility(View.GONE);
        }
    }


    /** broadcast receiver to get messages read status */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                if (chatListAdapter != null)
                    chatListAdapter.notifyDataSetChanged();

              /*  //set chat count on chat icon
                String myChatId = Singleton.getUserInfoInstance().getChatId();
                int count = 0;
                if (chatListAdapter.getChatIdList().size() > 0) {
                    for (int i = 0; i < chatListAdapter.getChatIdList().size(); i++) {
                        String rosterChatId = chatListAdapter.getChatIdList().get(i).getUser_id();
                        count = count + Singleton.getDbHelper().getUnreadCount(myChatId, Keys.BONJOB_ + rosterChatId);
                    }
                    if (Singleton.getUserInfoInstance().isLoginRecriter()) {
                        ((HomeRecruiterActivity) getActivity()).setChatCount(count);
                    } else {
                        ((HomeSeekerActivity) getActivity()).setChatCount(count);
                    }
                }*/
            }
        }
    };


    //this feature is only for admin

    /**
     * @param search_name
     */
    private void callSearchUserApi(String search_name) {

        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.USER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.PAGE, searchPageNo + "");
        jsonObject.addProperty(Keys.SEARCH_NAME, search_name);
        retrofit.Call<GetChatListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getChatListResponse(jsonObject);

        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetChatListResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetChatListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                rlRetry.setVisibility(View.GONE);
                loading = false;
                GetChatListResponse getChatListResponse = response.body();
                   totalSearchUserCount=getChatListResponse.getTotalUsers();
                if (getChatListResponse.isSuccess()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    if (searchPageNo == 0) {
                        searchResultAdapter = new ChatListAdapter(getActivity());
                      //  searchResultAdapter.clearArray();
                        recyclerView.setAdapter(searchResultAdapter);
                    }
                    for (GetChatListResponse.DataBean dataBean : getChatListResponse.getData()) {
                        searchResultAdapter.addChatUser(dataBean);
                    }
                    totalSearchListSize=totalSearchListSize+getChatListResponse.getData().size();
                     if(getChatListResponse.getData().size()==0){
                         UtilsMethods.showToastS(getActivity(),getString(R.string.message_no_search_result_found));
                     }
                } else {

                    if (getChatListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), getChatListResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
                loading = false;
                if (searchPageNo > 0) {
                    searchPageNo = searchPageNo - 1;
                }

            }
        });

    }


}
