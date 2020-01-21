package com.infoicon.bonjob.chatModule.chatMain;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chatModule.CommonMethods;
import com.infoicon.bonjob.chatModule.MyService;
import com.infoicon.bonjob.chatModule.database.DbConstants;
import com.infoicon.bonjob.chatModule.model.ChatMessage;
import com.infoicon.bonjob.chatModule.model.ChatModel;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomEditText;
import com.infoicon.bonjob.customview.CustomRadioButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.recruiters.candidateProfile.CandidateProfileActivity;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.ServiceUrls;
import com.infoicon.bonjob.retrofit.response.GetAddToChatListResponse;
import com.infoicon.bonjob.retrofit.response.GetChatHistoryResponse;
import com.infoicon.bonjob.seeker.searchJob.CompanyDetailsFragment;
import com.infoicon.bonjob.servicesConnection.INetworkResponse;
import com.infoicon.bonjob.servicesConnection.WebServiceCall;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    View rootView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.btn_send) CustomsTextView mButtonSend;
    @BindView(R.id.tvTitle) CustomsTextView tvTitle;
    @BindView(R.id.rbOnlineStatus) CustomRadioButton rbOnlineStatus;
    @BindView(R.id.et_message) CustomEditText mEditTextMessage;
    @BindView(R.id.iv_image) ImageView mImageView;
    @BindView(R.id.imageViewProfile) CircleImageView imageViewProfile;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    private ChatMessageAdapter mAdapter;
    private Random random;
    private String receiver;
    private LinearLayoutManager linearLayoutManager;
    private String job_title;
    private String user_id;
    private String user_pic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void hideProgress() {
        progress_bar.setVisibility(View.GONE);
    }

    private void showProgress() {
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        getIntents();
        getChatDataFromDB();
    }

    /** get intents */
    private void getIntents() {
        receiver = getArguments().getString(Keys.RECEIVER_ID);
        user_id = getArguments().getString(Keys.USER_ID);
        String name = getArguments().getString(Keys.NAME);
        job_title = getArguments().getString(Keys.JOB_TITLE);
        user_pic = getArguments().getString(Keys.USER_PIC);
        String job_image = getArguments().getString(Keys.JOB_IMAGE);
        String job_desc = getArguments().getString(Keys.DESCRIPTION);
        tvTitle.setText(name);

        imageViewProfile.setBorderWidth(2);
        imageViewProfile.setBorderColor(ContextCompat.getColor(getActivity(), R.color.border_grey));
        if (!user_pic.equals("") &&
                !user_pic.equalsIgnoreCase(
                        "https://bonjob.co//public/uploads/user_pic/")) {
            ImageLoader.loadImageWithCircle(getActivity(), user_pic, imageViewProfile);
        }
        else imageViewProfile.setImageResource(R.drawable.default_photo_deactive);


        Logger.e("Receiver : " + receiver);
        checkUserOnlineStatus();

        if (mAdapter != null) {
            mAdapter.addJobDetails(job_image, job_title, job_desc);
        }
        Singleton.getDbHelper().updateColumn(Singleton.getUserInfoInstance().getChatId(), receiver);
        getActivity().sendBroadcast(new Intent(Keys.UNREAD_STATUS));
        getActivity().sendBroadcast(new Intent(Keys.UNREAD_STATUS_HOME));
    }

    /** broadcast for get user online status */
    BroadcastReceiver broadcastReceiverOnlineStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                checkUserOnlineStatus();
            }
        }
    };

    /** check user is online or not */
    private void checkUserOnlineStatus() {
        if (Singleton.getOnlineList().containsKey(receiver)) {
            String status = Singleton.getOnlineList().get(receiver);
            if (status.equals(Keys.AVAILABLE)) {
                rbOnlineStatus.setChecked(true);
                rbOnlineStatus.setText(getResources().getString(R.string.online));
            } else {
                rbOnlineStatus.setChecked(false);
                rbOnlineStatus.setText(getResources().getString(R.string.offline));
            }
        }
    }

    /** listener implementation */
    @OnClick(R.id.imageViewProfile)
    void profile() {
        if (Singleton.getUserInfoInstance().isLoginRecriter()||Singleton.getUserInfoInstance().getLoginAdmin()) {
            Intent intent = new Intent(getActivity(), CandidateProfileActivity.class);
            intent.putExtra(Keys.CANDIDATE_ID, user_id);
            intent.putExtra(Keys.APPLIED_ID, "");
            intent.putExtra(Keys.JOB_TITLE, job_title);
            intent.putExtra(Keys.FROM, Keys.FROM_CHAT);
            startActivity(intent);


        } else {

            CompanyDetailsFragment companyDetailsFragment = new CompanyDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Keys.EMPLOYER_ID, user_id);
            bundle.putInt(Keys.POSITION, 0);
            ((HomeSeekerActivity) getActivity()).addFragment(companyDetailsFragment, bundle, Keys.COMPANY_DETAILS, false, true);

        }

    }

    /** event for go back */
    @OnClick(R.id.textViewBack)
    void GoBack() {
        (getActivity()).onBackPressed();
    }

    /** initialization here */
    private void initialize() {
        random = new Random();
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ChatMessageAdapter(getActivity(), new ArrayList<ChatMessage>());
        mRecyclerView.setAdapter(mAdapter);

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mEditTextMessage.getText().toString();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                sendMessage(message);
                mEditTextMessage.setText("");
                UtilsMethods.hideSoftKeyboard(getActivity());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("onResume");
        AppConstants.IS_CHAT_VISIBLE = true;
        AppConstants.CURRENT_ROSTER = receiver;
        UtilsMethods.clearNotification(getActivity());
        if (Singleton.getUserInfoInstance().isLoginRecriter() ||  Singleton.getUserInfoInstance().getLoginAdmin()) {
            ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        } else {
            ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        }

        getActivity().registerReceiver(broadcastReceiverOtherMessage, new IntentFilter(Keys.CHAT));
        getActivity().registerReceiver(broadcastReceiverOnlineStatus, new IntentFilter(Keys.ONLINE_STATUS));
    }

    @Override
    public void onPause() {
        super.onPause();
        AppConstants.IS_CHAT_VISIBLE = false;
        AppConstants.CURRENT_ROSTER = "";
        Logger.e("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.e("onStop");
    }

    /** send message to recruiter */
    private void sendMessage(String message)
    {
        if (Singleton.getUserInfoInstance().isLoginRecriter()) {
            if (Singleton.getDbHelper().getTableCount(Singleton.getUserInfoInstance().getChatId(), receiver) < 1)
                serviceAddToChatList();
        }

        int random_id = random.nextInt(1000);
        String message_id = UtilsMethods.getSaltString() + "" + random_id;

        String date = CommonMethods.getCurrentDate();
        String time = CommonMethods.getCurrentTime();

        ChatMessage chatMessage = new ChatMessage(
                Singleton.getUserInfoInstance().getChatId(),
                receiver, message, "" + message_id, true, date, time);
        mAdapter.add(chatMessage);

        new Handler().postDelayed(() -> mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1), 500);
        int count = mAdapter.getItemCount() - 1;
        Logger.e("Adapter count : " + count);
        //   linearLayoutManager.scrollToPosition(count);
        //  mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());

        ChatModel chatModel = new ChatModel();
        chatModel.setSender_id(Singleton.getUserInfoInstance().getChatId());
        chatModel.setReceiver_id(receiver);
        chatModel.setMessage(message);
        chatModel.setMsg_id("" + message_id);
        chatModel.setMine(true);
        chatModel.setDate(date);
        chatModel.setTime(time);
        chatModel.setUnread(0);
        Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), receiver);

        if (!MyService.xmpp.isAuthenticated()) {
            if (!Singleton.getDbHelper().isTableExists(DbConstants.TABLE_UNDELEIVERED)) {
                Singleton.getDbHelper().createTableUser(DbConstants.TABLE_UNDELEIVERED, "");
                Singleton.getDbHelper().storeChatToDb(chatModel, DbConstants.TABLE_UNDELEIVERED, "");
            } else {
                Singleton.getDbHelper().storeChatToDb(chatModel, DbConstants.TABLE_UNDELEIVERED, "");
            }
            //reconnection service
            getActivity().sendBroadcast(new Intent(Keys.RECONNECTION));
        } else {
            MyService.xmpp.sendMessage(chatMessage);
        }
    }

    /** receive message from recruiter */
    BroadcastReceiver broadcastReceiverOtherMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null && isAdded()) {
                String rec = intent.getStringExtra(Keys.RECEIVER);
                if (rec.equals(receiver)) {

                    boolean isMine = intent.getBooleanExtra(Keys.IS_MINE, false);
                    String date = intent.getStringExtra(Keys.DATE);
                    String time = intent.getStringExtra(Keys.TIME);
                    String message = intent.getStringExtra(Keys.CONTENT);
                    String sender = intent.getStringExtra(Keys.SENDER);
                    String receiver = intent.getStringExtra(Keys.RECEIVER);
                    String message_id = intent.getStringExtra(Keys.MSG_ID);

                    ChatMessage chatMessage = new ChatMessage(sender, receiver, message, message_id, isMine, date, time);
                    chatMessage.setMsgID();
                    mAdapter.add(chatMessage);
                    new Handler().postDelayed(() -> mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1), 200);
                    int count = mAdapter.getItemCount() - 1;
                    Logger.e("Adapter count : " + count);
                    //   linearLayoutManager.scrollToPosition(count);
                }
            }
        }
    };

    /**
     * check table is already exist if not then create it.
     */
    private void getChatDataFromDB() {
        if (Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + "" + receiver)) {
            List<ChatModel> chatModels = Singleton.getDbHelper().getChatData(Singleton.getUserInfoInstance().getChatId(), receiver);
            if (chatModels.size() > 0) {
                for (int i = 0; i < chatModels.size(); i++) {
                    final ChatMessage chatMessage = new ChatMessage(Singleton.getUserInfoInstance().getChatId(),
                            receiver,
                            chatModels.get(i).getMessage(),
                            chatModels.get(i).getMsg_id(),
                            chatModels.get(i).isMine(),
                            chatModels.get(i).getDate(),
                            chatModels.get(i).getTime());
                    chatMessage.setMsgID();
                    mAdapter.add(chatMessage);
                }
            } else {
                getChatHistory();
            }
        } else {
            Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), receiver);
            getChatHistory();
        }
    }

    @Override
    public void onDestroy() {
        Logger.e("onDestroy");
        AppConstants.IS_CHAT_VISIBLE = false;
        if (broadcastReceiverOtherMessage != null)
            getActivity().unregisterReceiver(broadcastReceiverOtherMessage);

        if (broadcastReceiverOnlineStatus != null) {
            getActivity().unregisterReceiver(broadcastReceiverOnlineStatus);
        }
        super.onDestroy();
    }

    /** call service for hired candidate first time only for add the user in list */
    private void serviceAddToChatList() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.EMPLOYER_ID, Singleton.getUserInfoInstance().getUser_id());
        jsonObject.addProperty(Keys.USER_ID, user_id);
        retrofit.Call<GetAddToChatListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getAddToChatResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetAddToChatListResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetAddToChatListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();
                GetAddToChatListResponse addToChatListResponse = response.body();
                if (addToChatListResponse.isSuccess()) {

                } else {
                    if (addToChatListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), addToChatListResponse.getMsg());
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

    /** service for get chat history */
    private void getChatHistory() {
       // showProgress();
        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                hideProgress();

                GetChatHistoryResponse historyResponse = new Gson().fromJson(response, GetChatHistoryResponse.class);

                if (historyResponse == null)
                    return;

                if (historyResponse.isStatus()) {

                    String sender;
                    String receiverSide;
                    ArrayList<ChatModel> chatModelList = new ArrayList<>();
                    ArrayList<ChatMessage> chatMessageList = new ArrayList<>();
                    for (GetChatHistoryResponse.DataBean dataBean : historyResponse.getData()) {

                        String from = dataBean.getFromJID();
                        String to = dataBean.getToJID();
                        boolean isMine;

                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(dataBean.getSentDate());
                        TimeZone tz = TimeZone.getDefault();
                        sdf.setTimeZone(tz);
                        sdf.format(calendar.getTime());

                        String date = CommonMethods.getConvertedDate(calendar.getTime());
                        String time = CommonMethods.getConvertedTime(calendar.getTime());
                        //  System.out.println(date);
                        //  System.out.println(time);

                        if (from.equals(Singleton.getUserInfoInstance().getUser_id())) {
                            sender = Keys.BONJOB_ + from;
                            receiverSide = Keys.BONJOB_ + to;
                            isMine = true;
                        } else {
                            sender = Keys.BONJOB_ + to;
                            receiverSide = Keys.BONJOB_ + from;
                            isMine = false;
                        }

                        String message = dataBean.getBody();
                        String message_id = String.valueOf(dataBean.getMessageID());

                        ChatMessage chatMessage = new ChatMessage(
                                sender,
                                receiverSide,
                                message,
                                message_id,
                                isMine,
                                date,
                                time);
                        chatMessage.setMsgID();
                        //  mAdapter.add(chatMessage);

                        //mode to save db
                        ChatModel chatModel = new ChatModel();
                        chatModel.setSender_id(Singleton.getUserInfoInstance().getChatId());
                        chatModel.setReceiver_id(receiver);
                        chatModel.setMessage(message);
                        chatModel.setMsg_id(message_id);
                        chatModel.setMine(isMine);
                        chatModel.setDate(date);
                        chatModel.setTime(time);
                        chatModel.setUnread(0);

                        chatMessageList.add(chatMessage);
                        chatModelList.add(chatModel);

                        /*if (Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + "" + receiver)) {
                            if (!Singleton.getDbHelper().isMessageIdExist(message_id, Singleton.getUserInfoInstance().getChatId(), receiver)) {
                                Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), receiver);
                            }
                        } else {
                            Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), receiver);
                            Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), receiver);
                        }*/
                    }
                    mAdapter.addAll(chatMessageList);
                    new Handler().postDelayed(() -> mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1), 200);

                    if (Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + "" + receiver)) {
                        Singleton.getDbHelper().storeChatHistoryToDb(chatModelList, Singleton.getUserInfoInstance().getChatId(), receiver);
                    } else {
                        Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), receiver);
                        Singleton.getDbHelper().storeChatHistoryToDb(chatModelList, Singleton.getUserInfoInstance().getChatId(), receiver);
                    }

                } else {
                    if (historyResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(getActivity(), historyResponse.getMsg());
                    }
                }
            }

            @Override
            public void onError(String error) {
                hideProgress();
            }
        }, false, true);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.USER, Singleton.getUserInfoInstance().getUser_id());
            jsonObject.put(Keys.ROSTER, user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        webServiceCall.execute(jsonObject, ServiceUrls.CHAT_HISTORY);
    }
}
