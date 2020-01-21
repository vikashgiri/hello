package com.infoicon.bonjob.chat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chatModule.chatList.ChatListFragment;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.seeker.profile.PickLocationActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TabBarChangeViewSeeker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatListActivity extends Fragment implements FragmentChanger
{

    private RecyclerView recyclerChat;
    private LinearLayoutManager linearLayoutManager;
    private ListMessageAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edtsearch;
    ArrayList<String>allFriendListId;
    ArrayList<String>allFriendMessageId;
    ArrayList<Message>allFriendMessage;
    ArrayList<Message>storedFriendMessage;
boolean refress=false;


    @Override
    public void onResume() {
        super.onResume();
        if (Singleton.getUserInfoInstance().isLoginRecriter()
                || Singleton.getUserInfoInstance().getLoginAdmin()) {
            ((HomeRecruiterActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        } else {
            ((HomeSeekerActivity) getActivity()).setCheckedToBottom(Keys.CHAT);
        }

        if (Singleton.getUserInfoInstance().isLoginRecriter()
                || Singleton.getUserInfoInstance().getLoginAdmin()) {

            if (Singleton.getUserInfoInstance().getLoginAdmin()) {

                FirebaseDatabase.getInstance().getReference()
                        .child("admin/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
                        .setValue(0);

            } else {
                FirebaseDatabase.getInstance().getReference()
                        .child("recruiter/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
                        .setValue(0);

            }
        }
     else {

    FirebaseDatabase.getInstance().getReference().
            child("seeker/"+Singleton.getUserInfoInstance().getUser_id()).child("totalUnreadCount")
            .setValue(0);
}
    }

    void deleteMessage(int position)
{
    FirebaseDatabase.
            getInstance().
            getReference().
            child("User-List").
            child(StaticConfig.UID)
            .child(allFriendListId.get(position))
           .removeValue();

    allFriendMessage.remove(position);
    allFriendListId.remove(position);
    allFriendMessageId.remove(position);
    swipeRefreshLayout.setRefreshing(true);
   // adapter.notifyDataSetChanged();

}

    @Override
    public void addFragment(Fragment fragment, Bundle bundle,
                            String tag, boolean isRemoveBackStack,
                            boolean isAddToBackStack) {
        FragmentManager manager =getActivity().getSupportFragmentManager();
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

    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack) {

    }

    @Override
    public void removeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chats, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StaticConfig.UID= Singleton.getUserInfoInstance().getUser_id();

//getUserMessage();
        //((TextView) findViewById(R.id.textViewHeading)).setText(getResources().getString(R.string.chat));
        edtsearch=(EditText)rootView.findViewById(R.id.search) ;
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        recyclerChat = (RecyclerView)rootView.findViewById(R.id.recyclerViewChatList);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                edtsearch.setText("");
                if(refress==true)
                {
                    return;
                }

                refress=true;

                allFriendMessage.clear();
                storedFriendMessage.clear();
                allFriendListId.clear();
                allFriendMessageId.clear();
                getUserMessage();

            }
        });
        recyclerChat.setLayoutManager(linearLayoutManager);

        allFriendListId = new ArrayList<>();

        allFriendMessageId = new ArrayList<>();
        allFriendMessage = new ArrayList<>();
        storedFriendMessage = new ArrayList<>();
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerChat.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerChat ,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position)
                            {

                               /* StaticConfig.BID=allFriendListId.get(position);
                                Intent intent = new Intent(getActivity(), VideoActivity.class);
                                startActivity(intent);*/

                              if(allFriendMessage.get(position).fromId.equalsIgnoreCase(StaticConfig.UID))
                                     {
                                         StaticConfig.type_from=allFriendMessage.get(position).from.type;
                                         StaticConfig.type_to=allFriendMessage.get(position).to.type;
                                         StaticConfig.to_name=allFriendMessage.get(position).to.name;
                                         StaticConfig.to_image=allFriendMessage.get(position).to.profilePic;
                                         StaticConfig.BID=allFriendMessage.get(position).toId;

                                     }
                                     else {
                                         StaticConfig.type_from = allFriendMessage.get(position).to.type;
                                         StaticConfig.type_to = allFriendMessage.get(position).from.type;
                                         StaticConfig.to_name=allFriendMessage.get(position).from.name;
                                         StaticConfig.to_image=allFriendMessage.get(position).from.profilePic;
                                  StaticConfig.BID=allFriendMessage.get(position).fromId;

                              }



                                     //remove read message

                                FirebaseDatabase.getInstance().getReference().
                                        child("read-Messages/" + StaticConfig.BID).
                                        child(StaticConfig.UID)
                                        .removeValue();

                                FirebaseDatabase.
                                        getInstance().
                                        getReference()
                                       .child(StaticConfig.type_to+"/"+StaticConfig.BID)
                                        .
                                                addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        try {
                                                            HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                                                            StaticConfig.to_profile_pick = (String) mapUserInfo.get("profilePic");

                                                            StaticConfig.fcm_type_to = (String) mapUserInfo.get("fcmToken");
                                                        }catch (Exception e)
                                                        {
                                                            e.printStackTrace();
                                                        }
                                                        addFragment(new ChatActivity(), new Bundle(), Keys.CHAT,
                                                                false,
                                                                true);
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError error) {
                                                        // Failed to read value

                                                    }
                                                });
                                getUserMessage();

                           }

                            @Override public void onLongItemClick(View view, int position)
                            {
                                // do whatever
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                                        getActivity());
                                // Setting Dialog Title
                                alertDialog.setTitle(getActivity().getString(R.string.delete));
                                // Setting Dialog Message
                                alertDialog.setMessage(R.string.delete_msg);
                                // On pressing Settings button
                                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        deleteMessage(position);

                                    }
                                });
                                // on pressing cancel button
                                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                // Showing Alert Message
                                alertDialog.show();
                            }
                        })
        );

        FirebaseDatabase.getInstance().getReference().child("User-List")
                .child(StaticConfig.UID)
                .addValueEventListener(
                        new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                    getUserMessage();


                            }

                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
        return rootView;
    }

    private View rootView;
    private Unbinder unbinder;

void getUserMessage()
{
    FirebaseDatabase.getInstance()
            .getReference()
            .child("User-List")
            .child(StaticConfig.UID)
            .orderByKey()
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allFriendMessage.clear();
                    allFriendListId.clear();
                    allFriendMessageId.clear();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        allFriendListId.add(child.getKey());
                    }
                    getAllFriendMessageId(0);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Handle possible errors.
                }
            });
}




    private void getAllFriendMessageId(final int index) {
     /*   if (index == allFriendListId.size()) {
            //save list friend
            getAllFriendMessage(0);
        } else {*/
     int size=allFriendListId.size();
    /* if(allFriendListId.size()<200)
     {
         size=allFriendListId.size();
     }*/
     for(int i=0;i<size;i++)
     {
         final String id = allFriendListId.get(i);
         int finalSize = size;
         FirebaseDatabase.getInstance()
                 .getReference()
                 .child("User-List")
                 .child(StaticConfig.UID + "/" + id)
                 .limitToLast(1)
                 .addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         for (DataSnapshot child : dataSnapshot.getChildren()) {

                             if (child.getKey().charAt(0) == '-') {
                                 allFriendMessageId.add(child.getKey());
                             } else {
                                 allFriendMessageId.add("-" + child.getKey());
                             }
                         }
                         if(allFriendMessageId.size()== finalSize) {
                             getAllFriendMessage(0);
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {
                         //Handle possible errors.
                     }
                 });
     }
   // }
    }


    private void getAllFriendMessage(final int index)
    {



               int size=allFriendListId.size();

                /*if(allFriendListId.size()<200)
                {
                    size=allFriendListId.size();
                }*/

                for(int i=0;i<size;i++) {
                    int finalSize = size;

            final String id = allFriendMessageId.get(i);

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("Messages")
                    .child(id)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null)
                            {
                                Message message = new Message();
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                                if(mapUserInfo!=null) {
                                    message.fromId = (String) mapUserInfo.get("fromId");
                                    message.toId = (String) mapUserInfo.get("toId");
                                    message.fcmToken = (String) mapUserInfo.get("fcmToken");
                                    message.messageID = (String) mapUserInfo.get("messageID");
                                    message.text = (String) mapUserInfo.get("text");
                                    message.readStatus = (String) mapUserInfo.get("readStatus");
                                    try {
                                        message.timeStamp = Integer.parseInt("" + mapUserInfo.get("timeStamp"));

                                    HashMap mapUserInfo1 = (HashMap) mapUserInfo.get("from");
                                    message.from.name =(String) mapUserInfo1.get("name");
                                    message.from.type =(String) mapUserInfo1.get("type");
                                    message.from.profilePic =(String) mapUserInfo1.get("profilePic");
                                    HashMap mapUserInfos = (HashMap) mapUserInfo.get("to");
                                    message.to.name =(String) mapUserInfos.get("name");
                                    message.to.type =(String) mapUserInfos.get("type");
                                    message.to.profilePic =(String) mapUserInfos.get("profilePic");
                               //     message.timestamp = (long) mapUserInfo.get("timestamp");
                                    allFriendMessage.add(message);
                                    storedFriendMessage.add(message);
                                    }catch (Exception e)

                                    {
                                        e.printStackTrace();
                                    }
                                }
                                if(allFriendMessageId.size()== finalSize) {

                                    // Ascending Order
                                    Collections.sort(allFriendMessage, new Comparator<Message>() {

                                        @Override
                                        public int compare(Message o1, Message o2) {
                                            return (int)(o2.timeStamp-o1.timeStamp);
                                        }
                                    });

                                    adapter = new ListMessageAdapter(getActivity());
                                recyclerChat.setAdapter(adapter);
                                // linearLayoutManager.scrollToPosition(allFriendListId.size() - 1);
                                swipeRefreshLayout.setRefreshing(false);
                                refress=false;
                            }
                            }
                           // getAllFriendMessage(index+1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Handle possible errors.
                        }
                    });
        }

    }



    public  String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private Context context;
String b_id;
        public ListMessageAdapter(Context context)
        {
            this.context = context;

        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
        View view = LayoutInflater.from(context).inflate(R.layout.itme_chat_list, parent, false);
        return new ItemMessageFriendHolder(view);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }




        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            try {
                if (allFriendMessage.get(position).fromId.equalsIgnoreCase(StaticConfig.UID)) {
                    ((ItemMessageFriendHolder) holder).txtContent.setText(allFriendMessage.get(position).to.name);
                    b_id=allFriendMessage.get(position).toId;
                } else {
                    ((ItemMessageFriendHolder) holder).txtContent.setText(allFriendMessage.get(position).from.name);
                    b_id=allFriendMessage.get(position).fromId;
                }
                if(allFriendMessage.get(position).fromId.equalsIgnoreCase(StaticConfig.UID) )
                {
if(!allFriendMessage.get(position).getTo().profilePic.isEmpty()) {
    ImageLoader.loadImageWithCircle(context, allFriendMessage.get(position).getTo().profilePic,
            ((ItemMessageFriendHolder) holder).profile_pic);
}

                }
                else {
                    if(!allFriendMessage.get(position).getFrom().profilePic.isEmpty())
                    {
                        ImageLoader.loadImageWithCircle(context, allFriendMessage.get(position).getFrom().profilePic,
                                ((ItemMessageFriendHolder) holder).profile_pic);
                    }

                 //   StaticConfig.type_to = allFriendMessage.get(position).from.type;

                }
                ((ItemMessageFriendHolder) holder).time.setText(
                        getDate(allFriendMessage.get(position)
                                .timeStamp*1000,"hh:mm a dd/MM/yyyy"));
                ((ItemMessageFriendHolder) holder).textMessage.setText(allFriendMessage.get(position).text);


                FirebaseDatabase.getInstance().getReference().child("read-Messages").child(b_id).
                        child(StaticConfig.UID).child("unreadMessages")
                        .addValueEventListener(
                                new ValueEventListener() {
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getChildrenCount() > 0)
                                        {
                                            ((ItemMessageFriendHolder) holder).msgCounts.
                                                    setVisibility(View.VISIBLE);
                                            ((ItemMessageFriendHolder) holder).msgCounts.
                                                    setText("" + dataSnapshot.getChildrenCount());
                                        }
                                    }
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount()
        {
            return allFriendMessage.size();
        }
    }


    class ItemMessageFriendHolder extends RecyclerView.ViewHolder
    {
        public TextView txtContent,textMessage,msgCounts,time;

        public ImageView profile_pic;


        public ItemMessageFriendHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.textFriendName);
            textMessage = (TextView) itemView.findViewById(R.id.textMessage);

            profile_pic=(ImageView)itemView.findViewById(R.id.imgViewJob);
            msgCounts=(TextView)itemView.findViewById(R.id.msgCount);
            time=(TextView)itemView.findViewById(R.id.time);
        }
    }

    void filter(String text){
        allFriendMessage.clear();
    if(text.length()>0){
        List<Message> temp = new ArrayList();
        temp.clear();

        for(Message d: storedFriendMessage){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            String toname="";

            if (d.fromId.equalsIgnoreCase(StaticConfig.UID)) {
                toname=d.to.name;
            } else {
                toname=d.from.name;
            }
            if(toname.toLowerCase().contains(text.toLowerCase())){
                allFriendMessage.add(d);
            }
        }
    }
    else {
        allFriendMessage.addAll(storedFriendMessage);
    }
        // Ascending Order
        Collections.sort(allFriendMessage, new Comparator<Message>() {

            @Override
            public int compare(Message o1, Message o2) {
                return (int)(o2.timeStamp-o1.timeStamp);
            }
        });
        //update recyclerview
        adapter = new ListMessageAdapter(getActivity());
        recyclerChat.setAdapter(adapter);
    }


}
