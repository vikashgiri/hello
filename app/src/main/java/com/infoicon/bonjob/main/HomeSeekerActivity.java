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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.seeker.activity.UsersActivityFragment;
import com.infoicon.bonjob.seeker.myOffers.MyOfferSeekerFragment;
import com.infoicon.bonjob.seeker.profile.ProfileFragment;
import com.infoicon.bonjob.seeker.searchJob.LookForFragment;
import com.infoicon.bonjob.seeker.searchJob.SearchJobFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewSeeker;
import com.infoicon.bonjob.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSeekerActivity extends AppCompatActivity implements FragmentChanger, View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.rbLookFor) public CustomsTextView rbLookFor;
    @BindView(R.id.rbFolder) public CustomsTextView rbFolder;
    @BindView(R.id.rbChat) public CustomsTextView rbChat;
    @BindView(R.id.rbActivity) public CustomsTextView rbActivity;
    @BindView(R.id.rbProfile) public CustomsTextView rbProfile;
    @BindView(R.id.textViewActivityCount) public CustomsTextViewBold textViewActivityCount;
    @BindView(R.id.textViewChatCount) public CustomsTextViewBold textViewChatCount;
    private MyService mService;
    private boolean mBounded;
    private String firstTag;// this for make tab bar checked on back pressed on last fragment
    private boolean isAppOpen;
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initialize();
        bindService();

        //getChatCountForTab();
        addFragmentFirstTime();
        setChatCount();
    }

    /** get chat count when app launch */
    private void getChatCountForTab()
    {
        int count = 0;
        Log.e(TAG, "My chat id : " + Singleton.getUserInfoInstance().getChatId());
        if (arrayList != null && arrayList.size() > 0)
        {
            for (int i = 0; i < arrayList.size(); i++)
            {
                if (arrayList.get(i).contains(Singleton.getUserInfoInstance().getChatId() + "b")) {
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
        Intent intent = getIntent();
        String from = intent.getStringExtra(Keys.FROM);
        if (from.equals(Keys.NOTIFICATION))
        {
           String fore = intent.getStringExtra(Keys.FOR);
           if (fore.equals(Keys.CHAT))
            {
                firstTag = Keys.CHAT_LIST;
                addFragment(new ChatListFragment(), new Bundle(), Keys.CHAT_LIST, false,
                        false);
            }
            else
                {
                firstTag = Keys.ACTIVITY;
                //String json = intent.getStringExtra(Keys.NOTIFICATION_JSON);
                addFragment(new UsersActivityFragment(), new Bundle(), Keys.ACTIVITY,
                        false, false);
            }

        }
        else
            {
            firstTag = Keys.LOOK_FOR;
            if (Singleton.getUserInfoInstance().isFirstTimeHomePopUpSeeker()) {
                addFragment(new SearchJobFragment(), new Bundle(), Keys.SEARCH_JOB, false,
                        false);
            } else {
                Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(true);
                addFragment(new LookForFragment(), new Bundle(), Keys.LOOK_FOR, false,
                        false);
            }
               // addFragment(new LookForFragment(), new Bundle(), Keys.LOOK_FOR, false, false);

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
                            boolean isAddToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);

        if (!fragmentPopped && manager.findFragmentByTag(tag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(R.id.frame_container, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        }
    }

      /*if (getIndex(tag) >= 0)
        {
            if (isAddToBackStack)
            {
                Logger.e(TAG + " Replace  InBackStack");
                fm.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            }
            else
                {
                Logger.e(TAG + " Replace  InBackStack Not");
                fragmentTransaction.replace(R.id.frame_container, fragment, tag).commit();
            }
        }
        else
            {
            if (isAddToBackStack)
            {
                Logger.e(TAG + " Add  InBackStack");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
                // fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            }
            else
                {
                Logger.e(TAG + " Add  InBackStack Not");
                fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
            }
        }
    }

    /**
     * replace fragment to back stack
     * @param fragment          fragment which want to add into back stack
     * @param bundle            send data to another fragment class
     * @param isRemoveBackStack if true remove all the back stack fragment
     */
    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle,
                                 String tag, boolean isRemoveBackStack,
                                 boolean isAddToBackStack)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (getFragmentByTag(tag))
        {
            fm.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            // removeFragmentByTag(tag);
        }

        if (isRemoveBackStack)
        {
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            {
                fm.popBackStack();
            }
        }
        fragment.setArguments(bundle);

        if (isAddToBackStack)
        {
            Logger.e(TAG + " Add  InBackStack");
            fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commit();
        }
        else
            {
            Logger.e(TAG + " Add  InBackStack Not");
            fragmentTransaction.add(R.id.frame_container, fragment, tag).commit();
        }
    }

    public void AddFragmnt(Fragment fragment, String tag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
    }

    public void removeFragmentByTag(String tag)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentManager.findFragmentByTag(tag)).commit();
    }

    private boolean getFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            return true;
        }
        return false;
    }

    /**
     * remove fragment from backStack
     */
    @Override
    public void removeFragment() {
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
        Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (uploadType != null) {
            uploadType.onActivityResult(requestCode, resultCode, data);
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
        if (count > 1) {
            Logger.e("Count : " + count);
            if (count > 1) {
                FragmentManager.BackStackEntry backEntry = fm.getBackStackEntryAt(count - 2);
                String tag = backEntry.getName();
                Fragment fragment = fm.findFragmentByTag(tag);
                if (fragment != null)
                    fragment.onResume();
            } else {
                setCheckedToBottom(firstTag);
            }
            fm.popBackStack();
        } else {
            showExitDialog(getResources().getString(R.string.exit_from_app));

        }
    }

    /**
     * dialog for exit from app
     * @param message exit message
     */
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
        buttonYes.setOnClickListener(view -> {
            dialog.dismiss();
            finish();
        });
        buttonNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /** make bottom tab selectable */
    public void setCheckedToBottom(String search)
    {
        TabBarChangeViewSeeker.changeView(this, search);
    }

    /** check the current fragment is visible */
    private boolean isItCurrentFragment(String tag) {
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

    public void fragmentRemoveByTag(Fragment tag) {
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentManager.findFragmentByTag(tag)).commit();*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(tag);
        trans.commit();
        manager.popBackStack();
    }

    /** get index of the fragment by tag */
    private int getIndex(String tagname) {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            if (manager.getBackStackEntryAt(i).getName().equalsIgnoreCase(tagname)) {
                return i;
            }
        }
        return -1;
    }

    /** check the fragment is in back stack true = yes , false = no */
    private boolean isFragmentInBackStack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean backstack = manager.popBackStackImmediate(backStateName, 0);
        return backstack;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbLookFor:
                TabBarChangeViewSeeker.changeView(this, Keys.LOOK_FOR);

                if (Singleton.getUserInfoInstance().isFirstTimeHomePopUpSeeker()) {
                    addFragment(new SearchJobFragment(), new Bundle(), Keys.SEARCH_JOB, false,
                            true);
                } else {
                    Singleton.getUserInfoInstance().setFirstTimeHomePopUpsSeeker(true);
                    addFragment(new LookForFragment(), new Bundle(), Keys.LOOK_FOR, false,
                            true);
                }
                if (mBounded && mService != null)
                {
                    mService.goOffline();
                }
                // replaceFragment(new ProfileVisitorViewerFragment(),new Bundle(),Keys.LOOK_FOR,true,false);
                break;
            case R.id.rbFolder:

               // if (!isItCurrentFragment(Keys.MY_OFFERS))
               // {
                    TabBarChangeViewSeeker.changeView(this, Keys.MY_OFFERS);
                    addFragment(new MyOfferSeekerFragment(), new Bundle(), Keys.MY_OFFERS, false,
                            true);
              //  }



                break;
            case R.id.rbChat:
                //if (!isItCurrentFragment(Keys.CHAT_LIST))
               // {
                FirebaseDatabase.getInstance().getReference().
                        child("seeker/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
                        .setValue(0);
                    TabBarChangeViewSeeker.changeView(this, Keys.CHAT);
                    addFragment(new ChatListActivity(), new Bundle(), Keys.CHAT_LIST, false,
                            true);
              //  }


                break;
            case R.id.rbActivity:
              //  if (!isItCurrentFragment(Keys.ACTIVITY))
              //  {
                    Singleton.getUserInfoInstance().setSeekerActivityCount(0);
                    activityCount();
                    TabBarChangeViewSeeker.changeView(this, Keys.ACTIVITY);
                    addFragment(new UsersActivityFragment(), new Bundle(), Keys.ACTIVITY, false,
                            true);
              //  }


                break;
            case R.id.rbProfile:
               /* if (!isItCurrentFragment(Keys.PROFILE))
                {*/
                    TabBarChangeViewSeeker.changeView(this, Keys.PROFILE);
                    addFragment(new ProfileFragment(), new Bundle(), Keys.PROFILE, false
                            , true);
               // }


                break;
        }
    }
    /** set chat count */
    public void setChatCount() {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("seeker/"+Singleton.getUserInfoInstance().getUser_id()+"/totalUnreadCount")
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
                                    }
                                    else
                                        {
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
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().
                child("seeker/"+Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                .setValue("1");
        long timestamp = System.currentTimeMillis()/1000;
        FirebaseDatabase.getInstance().getReference().
                child("seeker/"+Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
                .setValue(timestamp);
    /*    FirebaseDatabase.
                getInstance().
                getReference()
                .child("seeker/"+Singleton.getUserInfoInstance().getUser_id()+"/totalUnreadCount")
                .setValue(0);*/
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("seeker/"+Singleton.getUserInfoInstance().getUser_id()+"/logOutStatus")
                .setValue(0);

        isAppOpen = true;
        activityCount();
        //network state
        registerReceiver(broadcastReceiver, intentFilter);
        //unread activity
        registerReceiver(broadcastReceiverActivity, new IntentFilter(Keys.ACTIVITY_COUNT));
        //unread message
        registerReceiver(broadcastReceiverUnreadMessage, new IntentFilter(Keys.UNREAD_STATUS_HOME));
        //reconnection with service xmpp
        registerReceiver(broadcastReceiverReconnection, new IntentFilter(Keys.RECONNECTION));
        // add new table to arraylist for bubble
        registerReceiver(broadcastReceiverAddTable, new IntentFilter(Keys.ADD_TABLE));

        //set user online
       if (mBounded && mService != null) {
            mService.goOnline();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isApplicationSentToBackground(this)){
            // Do what you want to do on detecting Home Key being Pressed
            FirebaseDatabase.getInstance().getReference().
                    child("seeker/" + Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                    .setValue("0");
            long timestamp = System.currentTimeMillis() / 1000;
            FirebaseDatabase.getInstance().getReference().
                    child("seeker/" + Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
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
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onDestroy() {
        if (Singleton.getUserInfoInstance().isLogin()) {
            FirebaseDatabase.getInstance().getReference().
                    child("seeker/" + Singleton.getUserInfoInstance().getUser_id()).child("online_status")
                    .setValue("0");
            long timestamp = System.currentTimeMillis() / 1000;
            FirebaseDatabase.getInstance().getReference().
                    child("seeker/" + Singleton.getUserInfoInstance().getUser_id()).child("lastOnlineTime")
                    .setValue(timestamp);
        }
isAppOpen = false;
        //unregister for getting activity unread
        if (broadcastReceiverActivity != null) {
            unregisterReceiver(broadcastReceiverActivity);
        }

        //unregister for getting internet connection status
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);

        //unregister to receive unread messages
        if (broadcastReceiverUnreadMessage != null)
            unregisterReceiver(broadcastReceiverUnreadMessage);

        //unregister to reconnection with xmpp server
        if (broadcastReceiverReconnection != null)
            unregisterReceiver(broadcastReceiverReconnection);

        //unregister to refresh table
        if (broadcastReceiverAddTable != null)
            unregisterReceiver(broadcastReceiverAddTable);


        //unbind service
        if (mBounded && mService != null) {
            mService.onDestroy();
            unbindService(mConnection);
            mBounded = false;
            mService = null;
        }
        super.onDestroy();
    }


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

    /** broadcast receiver for internet connection. */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("broadcastReceiver call");

            //if (!isAppOpen)return;
            if (UtilsMethods.isInternetConnected(getApplicationContext())) {
                /*if(!MyService.xmpp.isAuthenticated()){
                    MyService.xmpp.login();
                }*/
            } else {
              /*  if (mBounded && mService != null) {
                    mService.onDestroy();
                    unbindService(mConnection);
                    mBounded = false;
                    mService = null;
                }*/
            }
        }
    };


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


    /** broadcast receiver to handle activity count */
    BroadcastReceiver broadcastReceiverActivity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isAppOpen) {
                activityCount();
            }
        }
    };

    /** broadcast receiver to get messages count */
    BroadcastReceiver broadcastReceiverUnreadMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (arrayList != null && arrayList.size() == 1){
                arrayList.clear();
                arrayList = Singleton.getDbHelper().getTables();
            }
            getChatCountForTab();
        }
    };

    /** manage to count the new recent activity by user */
    public void activityCount() {
        if (Singleton.getUserInfoInstance().getSeekerActivityCount() > 0) {
            textViewActivityCount.setVisibility(View.VISIBLE);
            textViewActivityCount.setText(String.valueOf(Singleton.getUserInfoInstance().getSeekerActivityCount()));
        } else {
            textViewActivityCount.setVisibility(View.GONE);
        }
    }

    /** broadcast receiver for internet connection */
    public class NetworkBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("broadcastReceiver call");
            if (UtilsMethods.isInternetConnected(getApplicationContext())) {
                Logger.e("broadcastReceiver internet connected");
                bindServiceAgain();
            } else {
                Logger.e("broadcastReceiver internet disconnected");
                unBindService();
            }
        }
    }

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
                        Logger.e("broadcastReceiver bind service" + " :  broadcastReceiverReconnection");
                        bindService();
                    }
                }
            }, 2000);
        }
    };

   /* public void test(){
        Map<String,String> map=new HashMap<>();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");

        map.forEach((String s, String s2) -> {
            Log.e(s, s2);
            System.out.println();
        });
    }*/

}
