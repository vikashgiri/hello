package com.infoicon.bonjob.main;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.Key;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.ChatActivity;
import com.infoicon.bonjob.chat.ChatListActivity;
import com.infoicon.bonjob.chat.StaticConfig;
import com.infoicon.bonjob.chatModule.LocalBinder;
import com.infoicon.bonjob.chatModule.MyService;
import com.infoicon.bonjob.chatModule.chatList.ChatListFragment;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.dialogs.SubscribePlanDialogFragment;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.recruiters.myOffer.OfferTabFragment;
import com.infoicon.bonjob.recruiters.post.PostJobOfferFragment;
import com.infoicon.bonjob.recruiters.profile.ProfileRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.LookForRecruiterFragment;
import com.infoicon.bonjob.recruiters.search.SearchForCandidateFragment;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewRecruiter;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class HomeRecruiterActivity extends AppCompatActivity
        implements FragmentChanger, View.OnClickListener
{

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.rel_rbLookFor) public RelativeLayout rel_rbLookFor;
    @BindView(R.id.rel_rbFolder) public RelativeLayout rel_rbFolder;

    @BindView(R.id.rel_rbChat) public RelativeLayout rel_rbChat;
    @BindView(R.id.rel_rbActivity) public RelativeLayout rel_rbActivity;

    @BindView(R.id.rel_rbProfile) public RelativeLayout rel_rbProfile;
    @BindView(R.id.rbLookFor) public CustomsTextView rbLookFor;

    @BindView(R.id.rbFolder) public CustomsTextView rbFolder;
    @BindView(R.id.rbChat) public CustomsTextView rbChat;

    @BindView(R.id.rbActivity) public CustomsTextView rbActivity;
    @BindView(R.id.rbProfile) public CustomsTextView rbProfile;

    @BindView(R.id.textViewChatCount) public CustomsTextViewBold textViewChatCount;
    @BindView(R.id.textVieOfferCount) public CustomsTextViewBold textVieOfferCount;

    private MyService mService;
    private boolean mBounded;

    private ArrayList<String> arrayList;
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recruiter);
        ButterKnife.bind(this);
        initialize();
        bindService();

        if(Singleton.getUserInfoInstance().getLoginAdmin())
        {
            rel_rbLookFor.setVisibility(View.GONE);
           rel_rbFolder.setVisibility(View.GONE);;

           rel_rbActivity.setVisibility(View.GONE);;

            addFragmentFirstTime();

            return;
        }

            addFragmentFirstTime();
            callServiceForSubscriptioPlan(true);
        setChatCount();

    }
    /** get chat count when app launch */
    private void getChatCountForTab()
    {
        int count = 0;
        if (arrayList != null && arrayList.size() > 0)
        {
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).contains(Singleton.getUserInfoInstance().getChatId() + "b"))
                {
                    Logger.e(TAG + " Chat List user :" + arrayList.get(i));
                    count = count + Singleton.getDbHelper().getUnreadCount2(arrayList.get(i));
                }
            }
            setChatCount();
        }
    }

    /** initialization and listener */
    private void initialize()
    {
        arrayList = Singleton.getDbHelper().getTables();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        rbLookFor.setOnClickListener(this);
        rbFolder.setOnClickListener(this);
        rbChat.setOnClickListener(this);
        rbActivity.setOnClickListener(this);
        rbProfile.setOnClickListener(this);
    }

    /**
     * load fragment at first time.
     */
    private void addFragmentFirstTime()
    {
        if(Singleton.getUserInfoInstance().getLoginAdmin())
        {
            addFragment(new ChatListActivity()
                    , new Bundle(), Keys.CHAT_LIST, false, false);
return;
        }
        Intent intent = getIntent();
        String from = intent.getStringExtra(Keys.FROM);
        if (from.equals(Keys.NOTIFICATION)) {
            //String json = intent.getStringExtra(Keys.NOTIFICATION_JSON);
            String fore = intent.getStringExtra(Keys.FOR);
            if (fore.equals(Keys.CHAT)) {
                addFragment(new ChatListFragment(), new Bundle(), Keys.CHAT_LIST, false, false);
            } else {
                addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, false);
            }
        }
        else
            {
                boolean bb=Singleton.getUserInfoInstance().isFirstTimeHomePopUpRecruiter();
            if (Singleton.getUserInfoInstance().isFirstTimeHomePopUpRecruiter()) {
                addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, true, false);
            } else {
                Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(true);
                addFragment(new LookForRecruiterFragment(), new Bundle(), Keys.LOOK_FOR, true, false);
            }
        /*    if (Singleton.getSubscriptionListAndMyPlan().getCurrentPlan() != null
                    && !Singleton.getSubscriptionListAndMyPlan().
                    getCurrentPlan().getExpiredOn().equals("")) {*/
               // addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, false, false);
           // } else {
                //Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(true);
               // addFragment(new LookForRecruiterFragment(), new Bundle(), Keys.LOOK_FOR, false, false);
           // }
        }
    }


    /**
     * add fragment to back stack
     * @param fragment          fragment which want to add into back stack
     * @param bundle            send data to another fragment class
     * @param isRemoveBackStack if true remove all the back stack fragment
     */
    @Override
    public void addFragment(Fragment fragment, Bundle bundle,
                            String tag, boolean isRemoveBackStack,
                            boolean isAddToBackStack)
    {
        FragmentManager manager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        boolean fragmentPopped=false;
        try {
             fragmentPopped = manager.popBackStackImmediate(tag, 0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if (!fragmentPopped &&
                manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(R.id.frame_container, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        }
    }

    /*
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack)
        {
            //fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }
        fragment.setArguments(bundle);

        if (getIndex(tag) >= 0) {
            if (isAddToBackStack)
            {
                fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            } else {
                Logger.e(TAG + " Replace  InBackStack Not");
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            }
        } else {
            if (isAddToBackStack) {
                Logger.e(TAG + " Add  InBackStack");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            } else {
                Logger.e(TAG + " Add  InBackStack Not");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            }
        }
    }
*/
    /**
     * replace fragment to back stack
     * @param fragment          fragment which want to add into back stack
     * @param bundle            send data to another fragment class
     * @param isRemoveBackStack if true remove all the back stack fragment
     */
    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (isRemoveBackStack) {
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
        }

        fragment.setArguments(bundle);
        //  fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);
        if (isAddToBackStack) {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(null).commit();
        } else {
            fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
        }
    }

    /**
     * remove fragment from backStack
     */
    @Override
    public void removeFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }

    /**
     * manage permission result for passing to fragment class.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (uploadType != null) {
            uploadType.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * manage activity result for passing to fragment class.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Keys.VERIFY_PHONE)
        {
            if (resultCode == RESULT_OK)
            {
                String loadPage = data.getStringExtra(Keys.VIEW_FRAGMENT);
                if (loadPage.equals(Keys.PROFILE)) {//load profile
                    new Thread(() -> addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();

                } else {//load offer
                    new Thread(() -> addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true)).start();
                }

            }
        } else {
            Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (uploadType != null) {
                uploadType.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Going back from app.
     */
    @Override
    public void onBackPressed()
    {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count >1) {
            Logger.e("Count : " + count);
            if (count > 1) {
                FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(count - 2);
                String tag = backEntry.getName();
                Fragment fragment = fm.findFragmentByTag(tag);
                if (fragment != null)
                    fragment.onResume();
            } else {
                setCheckedToBottom(Keys.LOOK_FOR);
            }
            fm.popBackStack();
        } else {
            showExitDialog(getResources().getString(R.string.exit_from_app));
        }
    }

    public void showExitDialog(String message)
    {
        final Dialog dialog = new Dialog(this);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCancelable(false);
        CustomsTextView textViewTitle = (CustomsTextView) dialog.findViewById(R.id.textViewTitle);
        CustomsTextView textViewMessage = (CustomsTextView) dialog.findViewById(R.id.textViewMessage);
        CustomsButton buttonYes = (CustomsButton) dialog.findViewById(R.id.buttonYes);
        CustomsButton buttonNo = (CustomsButton) dialog.findViewById(R.id.buttonNo);
        textViewTitle.setText(getResources().getString(R.string.app_name));
        textViewMessage.setText(message);
        buttonYes.setOnClickListener(view ->
        {
            dialog.dismiss();
            finish();
        });
        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void setCheckedToBottom(String search)
    {
        TabBarChangeViewRecruiter.changeView(this, search);
    }


    /** check the current fragment is visible */
    private boolean isItCurrentFragment(String tag)
    {
        Fragment hm = getSupportFragmentManager().findFragmentByTag(tag);
        if (hm != null && hm.isVisible()) {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            Logger.e("isItCurrentFragment count : " + count);
            String tagTop = "";
            if (count > 0) {
                FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(count - 1);
                tagTop = backEntry.getName();
            }
            if (tagTop.equals(tag)) {
                return true;
            } else return false;
        } else {
            return false;
        }
    }

    /** get index of the fragment by tag */
    private int getIndex(String tagname)
    {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            if (manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagname))
            {
                return i;
            }
        }
        return -1;
    }

    /** check the fragment is in back stack true = yes , false = no */
    private boolean isFragmentInBackStack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        return fragmentPopped;
    }


    @Override
    public void onClick(View v) {
        UtilsMethods.getDefaultLanguage();
        switch (v.getId()) {
            case R.id.rbLookFor:
            //    if (UtilsMethods.isValidForSearch(this)) {
                    TabBarChangeViewRecruiter.changeView(this, Keys.LOOK_FOR);
                    if (Singleton.getUserInfoInstance().isFirstTimeHomePopUpRecruiter()) {
                        addFragment(new SearchForCandidateFragment(), new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, true, false);
                    } else {
                        Singleton.getUserInfoInstance().setFirstTimeHomePopUpRecruiter(true);
                        addFragment(new LookForRecruiterFragment(), new Bundle(), Keys.LOOK_FOR, true, false);
                    }


             //   }

                // replaceFragment(new ProfileVisitorViewerFragment(),new Bundle(),Keys.LOOK_FOR,true,false);
                break;
            case R.id.rbFolder:
                if (UtilsMethods.isPlanActive(this)) {
                    if (!isItCurrentFragment(Keys.MY_OFFERS)) {
                        Singleton.getUserInfoInstance().setRecruiterOfferCount(0);
                        myOfferCount();
                        TabBarChangeViewRecruiter.changeView(this, Keys.MY_OFFERS);
                        addFragment(new OfferTabFragment(), new Bundle(), Keys.MY_OFFERS, false, true);
                    }
                }


                break;
            case R.id.rbChat:

                if(Singleton.getUserInfoInstance().getLoginAdmin())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("admin/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
                            .setValue(0);

                    if (!isItCurrentFragment(Keys.CHAT_LIST)) {
                        TabBarChangeViewRecruiter.changeView(this, Keys.CHAT);
                        addFragment(new ChatListActivity(), new Bundle(), Keys.CHAT_LIST, false, true);
                    }
                }
                else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
                            .setValue(0);

                    if (UtilsMethods.isPlanActive(this)) {
                        if (!isItCurrentFragment(Keys.CHAT_LIST)) {
                            TabBarChangeViewRecruiter.changeView(this, Keys.CHAT);
                            addFragment(new ChatListActivity(), new Bundle(), Keys.CHAT_LIST, false, true);
                        }
                    }
                }


                break;
            case R.id.rbActivity:

              //  if (UtilsMethods.isValidForPostJob(this)) {
                    if (!isItCurrentFragment(Keys.POST_JOB)) {
                        if (Singleton.getUserInfoInstance().getEnterprise_name().equalsIgnoreCase("")) {
                           UtilsMethods.postCallback(this, new UtilsMethods.Callback() {
                                @Override
                                public void onYes() {
                                    if (!isItCurrentFragment(Keys.PROFILE)) {
                                        TabBarChangeViewRecruiter.changeView(HomeRecruiterActivity.this, Keys.PROFILE);
                                        addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                                    }
                                }
                            });

                        } else {
                            TabBarChangeViewRecruiter.changeView(this, Keys.POST_JOB);
                            addFragment(new PostJobOfferFragment(), new Bundle(), Keys.POST_JOB, false, true);
                        }
                    }
              //  }


                //}
                break;
            case R.id.rbProfile:
                if (!isItCurrentFragment(Keys.PROFILE))
                {
                    TabBarChangeViewRecruiter.changeView(this, Keys.PROFILE);
                    addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                }
                //set user offline
                if (mBounded && mService != null) {
                    mService.goOffline();
                }
                break;
        }
    }

    /** set chat count */
    public void setChatCount() {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()+"/totalUnreadCount")
                .
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                /* HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();*/
                                int count=Integer.parseInt(dataSnapshot.getValue().toString());
                                ChatActivity test = (ChatActivity) getSupportFragmentManager()
                                        .findFragmentByTag(Keys.CHAT);
                                if (test != null && test.isVisible()) {
                                    textViewChatCount.setVisibility(View.GONE);
                                }
                                else {

                                    if (count > 0) {
                                        textViewChatCount.setVisibility(View.VISIBLE);
                                        textViewChatCount.setText(String.valueOf(count));
                                    } else {
                                        textViewChatCount.setVisibility(View.GONE);
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });
    }


    /** method to bind the service */
    public void bindService() {
        bindService(new Intent(this, MyService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //set user offline
        if (mBounded && mService != null)
        {
            mService.goOffline();
        }
        if (isApplicationSentToBackground(this)){
            // Do what you want to do on detecting Home Key being Pressed
            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                    .setValue("0");
            long timestamp = System.currentTimeMillis() / 1000;
            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
                    .setValue(timestamp);
        }
    }
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
 /*   @Override
    protected void onStop() {
        super.onStop();
     if (Singleton.getUserInfoInstance().isLoginRecriter()) {

            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                    .setValue("0");
            long timestamp = System.currentTimeMillis() / 1000;
            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/" + Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
                    .setValue(timestamp);

        }
        }*/
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().
                child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                .setValue("1");
        long timestamp = System.currentTimeMillis()/1000;
        FirebaseDatabase.getInstance().getReference().
                child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
                .setValue(timestamp);
        /*FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()+"/totalUnreadCount")
                .setValue(0);*/
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()+"/logOutStatus")
                .setValue(0);
        myOfferCount();
        //network state
        registerReceiver(broadcastReceiver, intentFilter);
        //reconnection with service xmpp
        registerReceiver(broadcastReceiverReconnection, new IntentFilter(Keys.RECONNECTION));
        //my offer count
        registerReceiver(broadcastReceiverMyOffer, new IntentFilter(Keys.MY_OFFER_COUNT));
        // add new table to arraylist for bubble
        registerReceiver(broadcastReceiverAddTable, new IntentFilter(Keys.ADD_TABLE));
        //after payment redirect to screens
        registerReceiver(broadcastReceiverAfterPayment, new IntentFilter(Keys.SUBSCRIPTION));
        //set user online
        if (mBounded && mService != null) {
            mService.goOnline();
        }

        try
        {
            if(getIntent().hasExtra(Keys.FROM_CANDIDATE)) {
                TabBarChangeViewRecruiter.changeView(HomeRecruiterActivity.this, Keys.PROFILE);
                addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    @Override
    protected void onDestroy() {

        //unregister for getting internet connection status
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);


        //unregister to reconnection with xmpp server
        if (broadcastReceiverReconnection != null)
            unregisterReceiver(broadcastReceiverReconnection);

        //my offer count
        if (broadcastReceiverMyOffer != null)
            unregisterReceiver(broadcastReceiverMyOffer);

        //unregister to refresh table
        if (broadcastReceiverAddTable != null)
            unregisterReceiver(broadcastReceiverAddTable);

        //unregister payment
        if (broadcastReceiverAfterPayment != null)
            unregisterReceiver(broadcastReceiverAfterPayment);

        //unbind service
        if (mBounded && mService != null) {
            mService.onDestroy();
            unbindService(mConnection);
            mBounded = false;
            mService = null;
        }
        super.onDestroy();
    }


    /** broadcast to add table for showing count in bubble */
    BroadcastReceiver broadcastReceiverAddTable = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.clear();
            arrayList = Singleton.getDbHelper().getTables();
        }
    };

    /** get bind result from service */
    private final ServiceConnection mConnection = new ServiceConnection() {
        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            mService = ((LocalBinder<MyService>) service).getService();
            mBounded = true;
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mService = null;
            mBounded = false;
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    /** broadcast receiver for internet connection */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("broadcastReceiver call");

            if (UtilsMethods.isInternetConnected(getApplicationContext())) {
                Logger.e("broadcastReceiver internet connected");
                bindServiceAgain();

                //check the subscription list is loaded
                if (Singleton.getSubscriptionListAndMyPlan().getSubscriptionList() == null) {
                    callServiceForSubscriptioPlan(false);
                }

            } else {
                Logger.e("broadcastReceiver internet disconnected");
                unBindService();
            }
        }
    };

    /** method for bind service second time */
    public void bindServiceAgain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mBounded && mService == null) {
                    Logger.e("broadcastReceiver bind service");
                    bindService();
                }
            }
        }, 10000);
    }

    /** method for unbind service */
    public void unBindService() {
        if (mBounded && mService != null) {
            Logger.e("broadcastReceiver unbind service");
            mService.onDestroy();
            unbindService(mConnection);
            mBounded = false;
            mService = null;
        }
    }



    /** broadcast receiver to get offer message count */
    BroadcastReceiver broadcastReceiverMyOffer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            myOfferCount();
        }
    };

    /** manage to count the new recent offer */
    public void myOfferCount() {
        if (Singleton.getUserInfoInstance().getRecruiterOfferCount() > 0) {
            textVieOfferCount.setVisibility(View.VISIBLE);
            textVieOfferCount.setText(String.valueOf(Singleton.getUserInfoInstance().getRecruiterOfferCount()));
        } else {
            textVieOfferCount.setVisibility(View.GONE);
        }
    }

    /** broadcast for bind service again */
    BroadcastReceiver broadcastReceiverReconnection = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!UtilsMethods.isInternetConnected(getApplicationContext())) {
                return;
            }
            unBindService();
            //service bind again
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mBounded && mService == null) {
                        Logger.e("broadcastReceiver bind service");
                        bindService();
                    }
                }
            }, 2000);
        }
    };

    /** event perform after payment got success or failure. */
    BroadcastReceiver broadcastReceiverAfterPayment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getStringExtra(Keys.FOR)) {
                    case Keys.POST_JOB:
                        if (!isItCurrentFragment(Keys.POST_JOB)) {
                            new Thread(() -> addFragment(new PostJobOfferFragment(), new Bundle(),
                                    Keys.POST_JOB,
                                    false, true)).start();
                        }
                        break;
                    case Keys.VIEW_ONLINE_CANDIDATE:
                        if (!isItCurrentFragment(Keys.VIEW_ONLINE_CANDIDATE)) {
                            new Thread(() -> addFragment(new SearchForCandidateFragment(),
                                    new Bundle(), Keys.VIEW_ONLINE_CANDIDATE, true, false)).start();
                        }
                        break;
                    case Keys.SUBSCRIPTION:
                        FragmentManager fm = getSupportFragmentManager();
                        SubscribePlanDialogFragment experienceDialogFragment = new SubscribePlanDialogFragment();
                        experienceDialogFragment.setTargetFragment(experienceDialogFragment, Keys.REQUEST_CODE_SUBSCRIBE_PLAN);
                        experienceDialogFragment.show(fm, Keys.TAG_SUBSCRIBE);
                        break;
                    case Keys.EDIT_PROFILE:
                        if (!isItCurrentFragment(Keys.PROFILE)) {
                            new Thread(() -> addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true)).start();
                        }
                        break;
                }
            }
        }
    };

    /** get the subscription list and your current plan */
    private void callServiceForSubscriptioPlan(boolean isLoaderShow)
    {
        final SpotsDialog spotsDialog = new SpotsDialog(this, R.style.Custom);
        spotsDialog.setCancelable(false);
        if (isLoaderShow)
            spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        retrofit.Call<GetSubscriptionListResponse> call = AppRetrofit.getAppRetrofitInstance().getApiServices().getSubscriptionListResponse(jsonObject);
        ServiceCreator.enqueueCall(call, new retrofit.Callback<GetSubscriptionListResponse>() {
            @Override
            public void onResponse(retrofit.Response<GetSubscriptionListResponse> response, retrofit.Retrofit retrofit) {
                spotsDialog.dismiss();

                GetSubscriptionListResponse getSubscriptionListResponse = response.body();

                if (getSubscriptionListResponse.isSuccess()) {
                    Singleton.getSubscriptionListAndMyPlan().setSubscriptionList(getSubscriptionListResponse.getData());
                    Singleton.getSubscriptionListAndMyPlan().setCurrentPlan(getSubscriptionListResponse.getCurrentPlan());
                } else {
                    if (getSubscriptionListResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(HomeRecruiterActivity.this, getSubscriptionListResponse.getMsg());
                    } //else
                    // CommonUtils.showSimpleMessageBottom(HomeRecruiterActivity.this, getSubscriptionListResponse.getMsg());
                }
               // addFragmentFirstTime();
                try
                {
                    if(getIntent().hasExtra(Keys.FROM_CANDIDATE)) {
                        TabBarChangeViewRecruiter.changeView(HomeRecruiterActivity.this, Keys.PROFILE);
                        addFragment(new ProfileRecruiterFragment(), new Bundle(), Keys.PROFILE, false, true);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
               // addFragmentFirstTime();
                // CommonUtils.showSimpleMessageBottom(HomeRecruiterActivity.this, t.getMessage());
            }
        });
    }
}

