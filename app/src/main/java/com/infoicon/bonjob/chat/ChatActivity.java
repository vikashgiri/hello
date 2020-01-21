package com.infoicon.bonjob.chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.fcm.MyFcmTokenModel;
import com.infoicon.bonjob.interfaces.FragmentChanger;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.login.MainActivity;
import com.infoicon.bonjob.permission.MarshmallowPermission;
import com.infoicon.bonjob.permission.PermissionKeys;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.TakePhoto;
import com.infoicon.bonjob.utils.TakeVideo;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ChatActivity extends Fragment implements View.OnClickListener, FragmentChanger {

    private RecyclerView recyclerChat;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private String roomId;
    private ArrayList<CharSequence> idFriend;
    private ImageView btnSend;
    private TextView imageViewattach;
    private EditText editWriteMessage;
    private LinearLayoutManager linearLayoutManager;
    public static HashMap<String, Bitmap> bitmapAvataFriend;
    public Bitmap bitmapAvataUser;
    public static ChatActivity activity;

    private ListMessageAdapter adapter;
    ArrayList<Message> allMessagesList;
    ArrayList<String> messageId;
boolean online_status;
    boolean block_status = false;
    boolean block_status_user = false;
  TextView block;
  TextView btnChat;
    String total_unread_count;
    String last_online_time;
    private View rootView;
    private Unbinder unbinder;
    CircleImageView imgViewProfile;
    TextView videoCall;
/*
     FirebaseDatabase.getInstance().getReference().
    child("User_blocked/" + StaticConfig.UID).
    child(StaticConfig.BID)
                                .setValue("0");*/


    @Override
    public void addInnerFragment(Fragment fragment, Bundle bundle, String tag, boolean isRemoveBackStack, boolean isAddToBackStack) {

    }

    @Override
    public void removeFragment() {

    }
    @Override
    public void addFragment(Fragment fragment, Bundle bundle,
                            String tag, boolean isRemoveBackStack,
                            boolean isAddToBackStack)
    {
        FragmentManager manager =getActivity().getSupportFragmentManager();
        fragment.setArguments(bundle);
        boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);

        if (!fragmentPopped && manager.findFragmentByTag(tag) == null)
        { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.setReorderingAllowed(true);
            ft.add(R.id.frame_container, fragment, tag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(tag);
            ft.commitAllowingStateLoss();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_conversation, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        videoCall=(TextView)rootView.findViewById(R.id.video_call);
videoCall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if (block_status) {
            // do whatever
            android.support.v7.app.AlertDialog.Builder alertDialog =
                    new android.support.v7.app.AlertDialog.Builder(getActivity());
            // Setting Dialog Title
            alertDialog.setTitle(getString(R.string.blocked_title));
            // Setting Dialog Message
            alertDialog.setMessage(R.string.blocked_msg);
            // On pressing Settings button
            alertDialog.setPositiveButton(getString(R.string.ub_block), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().
                            child("Blocked-Users/" + StaticConfig.UID).
                            child(StaticConfig.BID)
                            .child("blocked")
                            .setValue("No");

                }
            });
            // on pressing cancel button
            alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // Showing Alert Message
            alertDialog.show();
            return;
        }
        if (block_status_user) {
            CommonUtils.showSimpleMessageTop(getActivity(), getActivity().getString(R.string.block_user));

            // Toast.makeText(getApplicationContext(),"Blocked",Toast.LENGTH_SHORT).show();
            return;
        }


            Intent intent = new Intent(getActivity(), VideoActivity.class);
        StaticConfig.to_name_chatter= ((TextView)rootView.findViewById(R.id.name))
                .getText().toString();
            startActivity(intent);


    }
});
        marshmallowPermission = new MarshmallowPermission(getActivity());
        recyclerChat = (RecyclerView)rootView. findViewById(R.id.recyclerView);
        btnChat = (TextView) rootView. findViewById(R.id.btnChat);
        recyclerChat.setLayoutManager(linearLayoutManager);
        block= ((TextView) rootView.findViewById(R.id.block));
        imgViewProfile= ((CircleImageView) rootView.findViewById(R.id.imgViewJob));
        try {
            ImageLoader.loadImageWithCircle(getActivity(),  StaticConfig.to_image,
                    imgViewProfile);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        block.setVisibility(View.VISIBLE);
        ((TextView)rootView.findViewById(R.id.name))
                .setText(StaticConfig.to_name);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(!block_status)
                {
                    // do whatever
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    // Setting Dialog Title
                    alertDialog.setTitle(getString(R.string.block_title));
                    // Setting Dialog Message
                    alertDialog.setMessage(R.string.un_blocked_msg);
                    // On pressing Settings button
                    alertDialog.setPositiveButton(getString(R.string.block), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            FirebaseDatabase.getInstance().getReference().
                                    child("Blocked-Users/" + StaticConfig.UID).
                                    child(StaticConfig.BID)
                                    .child("blocked")
                                    .setValue("Yes");
                        }
                    });
                    // on pressing cancel button
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                    return;


                }
                else {

                    // do whatever
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    // Setting Dialog Title
                    alertDialog.setTitle(getString(R.string.blocked_title));
                    // Setting Dialog Message
                    alertDialog.setMessage(R.string.blocked_msg);
                    // On pressing Settings button
                    alertDialog.setPositiveButton(getString(R.string.ub_block), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().
                                    child("Blocked-Users/" + StaticConfig.UID).
                                    child(StaticConfig.BID)
                                    .child("blocked")
                                    .setValue("No");
                        }
                    });
                    // on pressing cancel button
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           // pickFile();
                           // takeVideoOption();
                     // takePhotoOption("");
                          dialog.cancel();
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();

                }

            }
        });
        //  -----------------------------------------------------------------------------------------------------------------
        recyclerChat.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerChat, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        if(
                                allMessagesList.get(position).text.equalsIgnoreCase("video")) {
                            ShowDialogs("video",allMessagesList.get(position).videoUrl);
                            Intent i=new Intent(getActivity(),ShowUrl.class)
                                    ;
                            i.putExtra("url",allMessagesList.get(position).videoUrl);
                            i.putExtra("type","video");
                            getActivity().startActivity(i);
                            /*addFragment(new PlayVideo(), new Bundle(), Keys.TAG_LANGUAGE,
                                    false,
                                    true);
                            StaticConfig.BID_TOKEN=allMessagesList.get(position).videoUrl;*/

                        }
                        if(allMessagesList.get(position).text.equalsIgnoreCase("image") ) {
                            Intent i=new Intent(getActivity(),ShowUrl.class)
                                    ;
                            i.putExtra("url",allMessagesList.get(position).imageUrl);
                            i.putExtra("type","image");
                            getActivity().startActivity(i);
                           // ShowDialogs("image",allMessagesList.get(position).imageUrl);
                        }
                        if(allMessagesList.get(position).text.equalsIgnoreCase("file") ) {
                            Uri uri =  Uri.parse(allMessagesList.get(position).fileUrl);
                            try {
                                Intent i=new Intent(getActivity(),ShowUrl.class)
                                        ;
                                i.putExtra("url",allMessagesList.get(position).fileUrl);
                                i.putExtra("type","file");
                                getActivity().startActivity(i);
                               // ShowDialogs("file",allMessagesList.get(position).fileUrl);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                           // ShowDialogs("image",);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        // Setting Dialog Title
                        alertDialog.setTitle(getString(R.string.delete));
                        // Setting Dialog Message
                        alertDialog.setMessage(R.string.delete_msg);
                        // On pressing Settings button
                        alertDialog.setPositiveButton(getString(R.string.message_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("User-List")
                                        .child(StaticConfig.UID
                                                + "/" + StaticConfig.BID
                                                + "/" + messageId.get(position)).removeValue();
                                messageId.remove(position);
                                allMessagesList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        // on pressing cancel button
                        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        // Showing Alert Message
                        alertDialog.show();


                        ((TextView)rootView. findViewById(R.id.typing)).
                                setVisibility(View.VISIBLE);
                    }
                })
        );

//blocked-----------------------------------------------------------------------
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference()
                .child("Blocked-Users").child(StaticConfig.UID).child(StaticConfig.BID).child("blocked");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                block_status = false;
              //  block.setText(R.string.block);
                try {
                    if (snapshot.getValue().toString().equalsIgnoreCase("Yes"))
                    {
                    //    block.setText(getString(R.string.ub_block));
                        block_status = true;
                        ((TextView) rootView.findViewById(R.id.typing)).
                                setText("");
                        ((TextView) rootView.findViewById(R.id.image_online)).
                                setBackgroundResource(R.drawable.circle_white);
                    }
                    else {
                        if (block_status_user)
                        {

                        }
                        else
                            {
                        if (online_status) {
                            ((TextView) rootView.findViewById(R.id.typing)).
                                    setText("En ligne");
                            ((TextView) rootView.findViewById(R.id.image_online)).
                                  setBackgroundResource(R.drawable.circle_red);
                        } else {
                            ((TextView) rootView.findViewById(R.id.typing)).
                                    setText(getDate(
                                            Long.valueOf(last_online_time) * 1000, "hh:mm a dd/MM/yyyy"));

                            ((TextView) rootView.findViewById(R.id.image_online)).
                                    setBackgroundResource(R.drawable.circle_gray);
                        }
                    }

                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference rootRefs = FirebaseDatabase.getInstance().getReference()
                .child("Blocked-Users").child(StaticConfig.BID).child(StaticConfig.UID).child("blocked");
        rootRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                block_status_user = false;

                try {
                    if (snapshot.getValue().toString().equalsIgnoreCase("Yes"))
                    {
                        ((TextView) rootView.findViewById(R.id.typing)).
                                setText("");
                        ((TextView) rootView.findViewById(R.id.image_online)).
                                setBackgroundResource(R.drawable.circle_white);
                        block_status_user = true;
                    }
                    else {
                        if (block_status) {

                        } else {
                            if (online_status) {
                            ((TextView) rootView.findViewById(R.id.typing)).
                                    setText("En ligne");
                                ((TextView) rootView.findViewById(R.id.image_online)).
                                        setBackgroundResource(R.drawable.circle_red);
                        } else {
                            ((TextView) rootView.findViewById(R.id.typing)).
                                    setText(getDate(
                                            Long.valueOf(last_online_time) * 1000, "hh:mm a dd/MM/yyyy"));
                                ((TextView) rootView.findViewById(R.id.image_online)).
                                        setBackgroundResource(R.drawable.circle_gray);}
                    }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSend = (ImageView) rootView.findViewById(R.id.imageViewSend);
        imageViewattach = (TextView) rootView.findViewById(R.id.imageViewattach);
        btnSend.setOnClickListener(this);
        imageViewattach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMultimedia();
            }
        });
        allMessagesList = new ArrayList<>();
        messageId = new ArrayList<>();
        editWriteMessage = (EditText) rootView.findViewById(R.id.btnMessage);
    /*    ((TextView) findViewById(R.id.textViewHeading))
                .setText(getIntent()
                .getStringExtra("name"));*/

        FirebaseDatabase.
                getInstance().
                getReference().
                child("User-List").
                child(StaticConfig.UID).child(StaticConfig.BID).
                addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot == null)
                            return;
                        collectMessageIdList(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });
        ((TextView)rootView. findViewById(R.id.typing)).
                setVisibility(View.VISIBLE);


        editWriteMessage.addTextChangedListener(new TextWatcher() {

            boolean isTyping = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            private Timer timer = new Timer();
            private final long DELAY = 2000; // milliseconds

            @Override
            public void afterTextChanged(final Editable s) {
                Log.d("", "");
                if (!isTyping) {
                    // Send notification for start typing event
                    isTyping = true;
                    FirebaseDatabase.
                            getInstance().
                            getReference().
                            child("Typing").
                            child(StaticConfig.BID).child(StaticConfig.UID + "/value").setValue("1");
                }

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                isTyping = false;
                                FirebaseDatabase.
                                        getInstance().
                                        getReference().
                                        child("Typing").
                                        child(StaticConfig.BID).child(StaticConfig.UID + "/value").setValue("0");
                            }
                        },
                        DELAY
                );
            }
        });




//last online
       /* if(StaticConfig.Type.
                equalsIgnoreCase("seeker"))
        {*/
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child(StaticConfig.type_to+"/"+StaticConfig.BID).child("lastOnlineTime")
                    .
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    try {
                                        last_online_time = dataSnapshot.getValue().toString();
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value

                                }
                            });

     /*   }
        else
        {
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("seeker/"+StaticConfig.BID).child("lastOnlineTime").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {
                                last_online_time = dataSnapshot.getValue().toString();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });

        }*/





//show when user typing
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("Typing").child(StaticConfig.UID).child(StaticConfig.BID)
                .
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    if (!block_status_user) {
                                        if (child.getValue().toString().
                                                equalsIgnoreCase("1")) {
                                            if (!block_status_user && !block_status) {
                                                ((TextView) rootView.findViewById(R.id.typing)).
                                                        setText(R.string.typing);
                                                ((TextView) rootView.findViewById(R.id.image_online)).
                                                        setBackgroundResource(R.drawable.circle_red);
                                            }
                                        } else {
                                            if (!block_status_user &&  !block_status) {
                                                ((TextView) rootView.findViewById(R.id.typing)).
                                                        setText(R.string.online);

                                                ((TextView) rootView.findViewById(R.id.image_online)).
                                                        setBackgroundResource(R.drawable.circle_red);
                                            }
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });


//check read unread msg
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("Messages").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        try {
                            for (DataSnapshot child : dataSnapshot.getChildren())
                            {
                                if (allMessagesList.size() > 0)
                                {
                                    for (int i = 0; i < messageList.size(); i++)
                                    {
                                        //iterate through each user, ignoring their UID
                                        if (messageList.get(i).equalsIgnoreCase(child.getKey()))
                                        {
                                            Message name = child.getValue(Message.class);
                                            allMessagesList.get(i).readStatus = name.readStatus;

                                        }
                                    }
                                }
                            }
                            if (allMessagesList.size() > 0)
                            {
                                adapter.notifyDataSetChanged();
                                linearLayoutManager.scrollToPosition(allMessagesList.size() - 1);
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });

/*
        //unreadcount
        if(StaticConfig.Type.
                equalsIgnoreCase("seeker"))
        {
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("seeker/"+StaticConfig.BID).child("totalUnreadCount")
                    .
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue() != null) {

                                        total_unread_count = dataSnapshot.getValue().toString();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value

                                }
                            });

        }
        else
        {
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("recruiter/"+StaticConfig.BID).child("totalUnreadCount")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {
                                total_unread_count = dataSnapshot.getValue().toString();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });

        }*/


        //show online/onlinetime
      /*  if(StaticConfig.Type.
                equalsIgnoreCase("seeker"))

        {*/
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child(StaticConfig.type_to+"/"+StaticConfig.BID).child("online_status")

                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {

                                if (dataSnapshot.getValue().toString().equalsIgnoreCase("0")) {
                                    if (!block_status_user &&  !block_status) {
                                        ((TextView) rootView.findViewById(R.id.typing)).
                                                setText(getDate(
                                                        Long.valueOf(last_online_time) * 1000, "hh:mm a dd/MM/yyyy"));
                                        ((TextView) rootView.findViewById(R.id.image_online)).
                                                setBackgroundResource(R.drawable.circle_gray);
                                    }
                                    online_status = false;
                                }else {
                                    if (!block_status_user &&  !block_status)
                                    {
    ((TextView) rootView.findViewById(R.id.typing)).
            setText("En ligne");

         ((TextView) rootView.findViewById(R.id.image_online)).
                 setBackgroundResource(R.drawable.circle_red);

}
                                    else
                                    {
                                        ((TextView) rootView.findViewById(R.id.image_online)).
                                                setBackgroundResource(R.drawable.circle_white);
                                    }

                                    online_status = true;
                                }
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });


        //}
      /*  else
        {
            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("seeker/"+StaticConfig.BID).child("onlineStatus")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) {

                                if (dataSnapshot.getValue().toString().equalsIgnoreCase("no")) {

                                    ((TextView)rootView. findViewById(R.id.typing)).
                                            setText(getDate(Long.valueOf(last_online_time) * 1000));
                                    online_status = false;
                                } else {
                                    ((TextView)rootView. findViewById(R.id.typing)).
                                            setText("Online");
                                    online_status = true;
                                }
                            }
                        }



                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });

        }*/


        return  rootView;
    }
/*
    public static String getDate(long milliSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String dateString = sdf.format(new Date(milliSeconds));
        return dateString;
    }*/
    @Override
    public void onClick(View view) {
            String content = editWriteMessage.getText().toString().trim();
            if (content.length() > 0) {

                if (block_status) {
                    // do whatever
                    android.support.v7.app.AlertDialog.Builder alertDialog =
                            new android.support.v7.app.AlertDialog.Builder(getActivity());
                    // Setting Dialog Title
                    alertDialog.setTitle(getString(R.string.blocked_title));
                    // Setting Dialog Message
                    alertDialog.setMessage(R.string.blocked_msg);
                    // On pressing Settings button
                    alertDialog.setPositiveButton(getString(R.string.ub_block), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().
                                    child("Blocked-Users/" + StaticConfig.UID).
                                    child(StaticConfig.BID)
                                    .child("blocked")
                                    .setValue("No");

                        }
                    });
                    // on pressing cancel button
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                    return;
                }
                if (block_status_user) {
                    CommonUtils.showSimpleMessageTop(getActivity(), getActivity().getString(R.string.block_user));

                    // Toast.makeText(getApplicationContext(),"Blocked",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    editWriteMessage.setText("");


//increasee unread count
                    FirebaseDatabase.
                            getInstance().
                            getReference().
                            child(StaticConfig.type_to+"/"+StaticConfig.BID).child("totalUnreadCount")

                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue() != null) {
                                        String count=dataSnapshot.getValue().toString();

                                        int counts=Integer.parseInt(count)+1;
                                        FirebaseDatabase.getInstance().getReference()
                                                .child(StaticConfig.type_to+"/"+StaticConfig.BID)
                                                .child("totalUnreadCount")
                                                .setValue(counts);



                                        Message newMessage = new Message();
                                        newMessage.text = content;
                                        newMessage.fromId = StaticConfig.UID;
                                        newMessage.toId = StaticConfig.BID;
                                        newMessage.fcmToken =StaticConfig.fcm_type_to ;
                                        newMessage.timeStamp =System.currentTimeMillis() / 1000;
                                        newMessage.from.type = StaticConfig.type_from;
                                        newMessage.to.type = StaticConfig.type_to;
                                        newMessage.to.profilePic = StaticConfig.to_profile_pick;
                                        newMessage.from.profilePic = Singleton.getUserInfoInstance().getuser_pic();
                                        newMessage.totalUnreadCount =""+counts;


                                        newMessage.from.name =Singleton.getUserInfoInstance().getFirst_name()
                                                + " " + Singleton.getUserInfoInstance().getLast_name();
                                        newMessage.to.name = StaticConfig.to_name;

                                        final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                        final String key = mss.child("Messages").push().getKey();
                                        newMessage.messageID = key;
                                        mss.child("Messages/" + key).setValue(newMessage).
                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mss.child("User-List/" + StaticConfig.UID + "/" +
                                                                StaticConfig.BID + "/" + key).setValue(1);
                                                        //create user_msg for reciver
                                                        mss.child("User-List/" + StaticConfig.BID + "/" +
                                                                StaticConfig.UID + "/" + key).setValue(1);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Write failed
                                                        // ...
                                                    }
                                                });
                                        ;

                                        FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                child(StaticConfig.UID).child(StaticConfig.BID + "/unreadMessages")
                                                .child(key).setValue(1);

                                    }
                                }


                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value

                                }
                            });

                }
            }
}



    private void collectMessage(DataSnapshot users)
    {
       allMessagesList.clear();
        messageId.clear();
        for (DataSnapshot child : users.getChildren()) {
            for(int i=0;i<messageList.size();i++){
                //iterate through each user, ignoring their UID
                if(messageList.get(i).equalsIgnoreCase(child.getKey()))
                {
                  Message name = child.getValue(Message.class);
                  allMessagesList.add(name);
                  messageId.add(child.getKey());
                }
        }
    }
        adapter = new ListMessageAdapter(getActivity(),allMessagesList);
        recyclerChat.setAdapter(adapter);
      linearLayoutManager.scrollToPosition(allMessagesList.size()-1);
    }
    ArrayList<String> messageList;
    private void collectMessageIdList(DataSnapshot users)
    {
        messageList = new ArrayList<>();
        messageList.clear();
        if (users.getValue() == null) {
            return;
        }

        for (DataSnapshot child : users.getChildren()) {
            if(child.getKey().charAt(0)=='-') {
                messageList.add(child.getKey());
            }
            else {
                messageList.add("-"+child.getKey());
            }
        }
        getMessage();
   }

    @Override
    public void onPause() {
        super.onPause();
        long timestamp = System.currentTimeMillis()/1000;
   /*     if(StaticConfig.Type.
                equalsIgnoreCase("seeker"))
        {*/
           /* FirebaseDatabase.getInstance().getReference().
                    child(StaticConfig.type_from+"/"+StaticConfig.UID).child("online_status")
                    .setValue("0");

            FirebaseDatabase.getInstance().getReference().
                    child(StaticConfig.type_from+"/"+StaticConfig.UID).child("lastOnlineTime")
                    .setValue(timestamp);
*/
        /*}
        else
        {
            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/"+StaticConfig.UID).child("onlineStatus")
                    .setValue("No");

            FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("recruiter/"+StaticConfig.UID).child("lastOnlineTime")
                    .setValue(timestamp);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();

       /* if(StaticConfig.Type.
                equalsIgnoreCase("seeker"))
        {*/
            /*FirebaseDatabase.getInstance().getReference().
                    child(StaticConfig.type_from+"/"+StaticConfig.UID).child("online_status")
                    .setValue("1");*/
        /*}
        else
        {
            FirebaseDatabase.getInstance().getReference().
                    child("recruiter/"+StaticConfig.UID).child("onlineStatus")
                    .setValue("Yes");
        }*/
    }


void getMessage()
{
    DatabaseReference aa = FirebaseDatabase.getInstance().getReference().child("Messages");
    Query query = aa.orderByKey();
    query.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                collectMessage(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
    }




    class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;

        List<Message>allMessagesList;
        public ListMessageAdapter(Context context,List<Message>allMessagesList) {
            this.context = context;
            this.allMessagesList=allMessagesList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            if (viewType == ChatActivity.VIEW_TYPE_FRIEND_MESSAGE) {
                View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
                return new ItemMessageFriendHolder(view);
            } else if (viewType == ChatActivity.VIEW_TYPE_USER_MESSAGE) {
                View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
                return new ItemMessageUserHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            holder.setIsRecyclable(false);
            if (holder instanceof ItemMessageFriendHolder)
            {
                ((ItemMessageFriendHolder) holder).txtContent.setVisibility(View.VISIBLE);
                ((ItemMessageFriendHolder) holder).txtContent.setText(allMessagesList.get(position).text);
                ((ItemMessageFriendHolder) holder).image_message.setVisibility(View.GONE);
                ((ItemMessageFriendHolder) holder).rel1.setBackground
                        (getResources().getDrawable(R.drawable.round_grey_rec_msg));
                ((ItemMessageFriendHolder) holder).videoView.setVisibility(View.GONE);
                ((ItemMessageFriendHolder) holder).image_job.setVisibility(View.GONE);

                if(allMessagesList.get(position).getMsgType().equalsIgnoreCase("ApplyJob")) {
                    try {

                        ((ItemMessageFriendHolder) holder).image_job.setVisibility(View.VISIBLE);
                        ImageLoader.loadImageWithCircle(getActivity(), allMessagesList.get(position).getJobImage(),
                                ((ItemMessageFriendHolder) holder).image_job);
                        ((ItemMessageFriendHolder) holder).txtContent.setText(allMessagesList.get(position).text);

                        String substr=allMessagesList.get(position).text
                                .substring(getResources().getString(R.string.msg_apply)
                                        .length(),allMessagesList.get(position).text.length());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            ((ItemMessageFriendHolder) holder).txtContent.setText(Html.
                                    fromHtml(getResources().getString(R.string.msg_apply)+"<h6>"+substr+"</h6>", Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            ((ItemMessageFriendHolder) holder).txtContent.setText(Html.
                                    fromHtml(getResources().getString(R.string.msg_apply)+"<h6>"+substr+"</h6>"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(allMessagesList.get(position).text.equalsIgnoreCase("file"))
                {
                    ((ItemMessageFriendHolder) holder).image_job.setVisibility(View.VISIBLE);
                    ((ItemMessageFriendHolder) holder).image_job.setImageDrawable
                            (getResources().getDrawable(R.drawable.ic_insert_drive_file_black_24dp));
                    ((ItemMessageFriendHolder) holder).txtContent.setText(allMessagesList.get(position).fileName);


                }
                if(allMessagesList.get(position).text.equalsIgnoreCase("image") ||
                        allMessagesList.get(position).text.equalsIgnoreCase("video"))
                {
                    ((ItemMessageFriendHolder) holder).image_message.setVisibility(View.VISIBLE);
                    ((ItemMessageFriendHolder) holder).rel1.setBackgroundColor(Color.TRANSPARENT);

                    Glide.with(getActivity()).load(allMessagesList.get(position).getImageUrl())
                            .into(((ItemMessageFriendHolder) holder).image_message);
                    ((ItemMessageFriendHolder) holder).txtContent.setVisibility(View.GONE);

                }
                if(allMessagesList.get(position).text.equalsIgnoreCase("video"))
                {
                    ((ItemMessageFriendHolder) holder).videoView.setBackground
                            (getResources().getDrawable(R.drawable.play_pink));
                    ((ItemMessageFriendHolder) holder).videoView.setVisibility(View.VISIBLE);

                }


                ((ItemMessageFriendHolder) holder).time.setText(
                        getDate(allMessagesList.get(position)
                                .timeStamp*1000,"hh:mm a dd/MM/yyyy"));
                if(!allMessagesList.get(position).readStatus.equalsIgnoreCase("1"))
                {
                    FirebaseDatabase.getInstance().getReference().
                            child("Messages/"+messageId.get(position)).child("readStatus")
                            .setValue("1");
                }

            }
            else if (holder instanceof ItemMessageUserHolder)
            {
                ((ItemMessageUserHolder) holder).image_message.setVisibility(View.GONE);
                ((ItemMessageUserHolder) holder).txtContent.setVisibility(View.VISIBLE);
                ((ItemMessageUserHolder) holder).txtContent.setText(allMessagesList.get(position).text);
                ((ItemMessageUserHolder) holder).rel1.setBackground
                        (getResources().getDrawable(R.drawable.round_grey_send_msg));
                ((ItemMessageUserHolder) holder).
                        time.setTextColor(getResources().getColor(R.color.white));
                ((ItemMessageUserHolder) holder).videoView.setVisibility(View.GONE);
                ((ItemMessageUserHolder) holder).image_job.setVisibility(View.GONE);

                if(allMessagesList.get(position).getMsgType().equalsIgnoreCase("ApplyJob"))
                {
                    try {
                    ((ItemMessageUserHolder) holder).image_job.setVisibility(View.VISIBLE);
                        ImageLoader.loadImageWithCircle(getActivity(), allMessagesList.get(position).getJobImage(),
                                ((ItemMessageUserHolder) holder).image_job);

                        String substr=allMessagesList.get(position).text
                                .substring(getResources().getString(R.string.msg_apply)
                                        .length(),allMessagesList.get(position).text.length());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            ((ItemMessageUserHolder) holder).txtContent.setText(Html.
                                    fromHtml(getResources().getString(R.string.msg_apply)+"<b>"+substr+"</b>", Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            ((ItemMessageUserHolder) holder).txtContent.setText(Html.
                                    fromHtml(getResources().getString(R.string.msg_apply)+"<b>"+substr+"</b>"));
                        }




                    }catch (Exception e)
                {
                    e.printStackTrace();
                }
                }
                if(allMessagesList.get(position).text.equalsIgnoreCase("file"))
                {
                    try {
                        ((ItemMessageUserHolder) holder).image_job.setVisibility(View.VISIBLE);
                        ((ItemMessageUserHolder) holder).txtContent.setText(allMessagesList.get(position).fileName);

                        ((ItemMessageUserHolder) holder).image_job.setImageDrawable(getResources().getDrawable(R.drawable.ic_insert_drive_file_white_24dp));




                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if(allMessagesList.get(position).text.equalsIgnoreCase("image") ||
                        allMessagesList.get(position).text.equalsIgnoreCase("video"))
                {
                    ((ItemMessageUserHolder) holder).rel1.setBackgroundColor(Color.TRANSPARENT);
                    Glide.with(getActivity()).load(allMessagesList.get(position).getImageUrl())
                            .into(((ItemMessageUserHolder) holder).image_message);
                    ((ItemMessageUserHolder) holder).txtContent.setVisibility(View.GONE);
                    ((ItemMessageUserHolder) holder).image_message.setVisibility(View.VISIBLE);
                    ((ItemMessageUserHolder) holder).
                            time.setTextColor(getResources().getColor(R.color.black));

                }
                if(allMessagesList.get(position).text.equalsIgnoreCase("video"))
                {
                    ((ItemMessageUserHolder) holder).videoView.setBackground
                            (getResources().getDrawable(R.drawable.play_pink));

                    ((ItemMessageUserHolder) holder).videoView.setVisibility(View.VISIBLE);

                }


                /*Date date = new Date(allMessagesList.get(position).timestamp);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);*/
                ((ItemMessageUserHolder) holder).
                        time.setText(
                        getDate(allMessagesList.get(position)
                                .timeStamp*1000,"hh:mm a dd/MM/yyyy"));
            //    getDateCurrentTimeZone(allMessagesList.get(position).timeStamp));
                if(allMessagesList.get(position).readStatus.equalsIgnoreCase("1"))
                {
                    ((ItemMessageUserHolder) holder).read_status.setImageDrawable(getResources()
                            .getDrawable(R.drawable.read_msg_icon));
                }
                else {

                        ((ItemMessageUserHolder) holder).read_status.setImageDrawable(getResources()
                                .getDrawable(R.drawable.unread_msg_icon));
                }
            }
        }

        @Override
        public int getItemViewType(int position)
        {
            return allMessagesList.get(position).fromId.equals(StaticConfig.UID)
                    ? ChatActivity.VIEW_TYPE_USER_MESSAGE : ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
        }

        @Override
        public int getItemCount() {
            return allMessagesList.size();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

    }

    class ItemMessageUserHolder extends RecyclerView.ViewHolder {
        public TextView txtContent,time;
        ImageView read_status,image_message,videoView;
        CircleImageView image_job;
        LinearLayout rel1;

        public ItemMessageUserHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.textContentUser);
            time = (TextView) itemView.findViewById(R.id.time);
            read_status=(ImageView)itemView.findViewById(R.id.read_status);
            image_message=(ImageView)itemView.findViewById(R.id.image_message);
            videoView=(ImageView)itemView.findViewById(R.id.videoView);
            image_job=(CircleImageView) itemView.findViewById(R.id.image_job);
            rel1=(LinearLayout)itemView.findViewById(R.id.rel1);


        }

    }

    class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
        public TextView txtContent,time;
        ImageView image_message,videoView;
        LinearLayout rel1;
        CircleImageView image_job;


        public ItemMessageFriendHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.textContentFriend);
            time = (TextView) itemView.findViewById(R.id.time);
            image_message=(ImageView)itemView.findViewById(R.id.image_message);
            rel1=(LinearLayout)itemView.findViewById(R.id.rel1);
            videoView=(ImageView)itemView.findViewById(R.id.videoView);
            image_job=(CircleImageView) itemView.findViewById(R.id.image_job);

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


    private static final int RESULT_LOAD_IMG = 1;
    private static final int RESULT_LOAD_VID = 2;
    private static final int RESULT_LOAD_FILE = 3;

    /**
     * load image from gallery
     * Create intent to Open Image applications like Gallery, Google Photos
     */
    private void loadImagefromGallery() {
        if (marshmallowPermission.isPermissionGranted(PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE,
                PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1))
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        }
    }


    public void openAlert(int requestCode, String message) {
        final Dialog dialog = new Dialog(getActivity());
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
            switch (requestCode) {
                case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                    loadImagefromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                  //  loadVideofromGallery();
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                    takePhotoOption(fromPage);
                    break;
                case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO:
                   // takeVideoOption();
                    break;
                default:
                    break;
            }
        });
        buttonNo.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    /** get result back if permission is granted or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImagefromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }
                break;
            case PermissionKeys.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  //  loadVideofromGallery();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.external_storage_perm_mess));
                }

                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoOption(fromPage);
                } else {
                    openAlert(requestCode, getResources().getString(R.string.camera_external));
                }
                break;
            case PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                   // takeVideoOption();
                } else {
                    openAlert(requestCode, getResources().getString(R.string.camera_external));
                }
                break;
            default:
                break;
        }
    }

    private String mCurrentVideoPath;
    private String imgDecodeString;
    private String imgDecodeStringForGallery;

    private MarshmallowPermission marshmallowPermission;

    /** method for taking photo */
    public void takePhotoOption(String from) {
        fromPage = from;

      //  fromPage = from;
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_PHOTO, permissions)) {
            new TakePhoto(getActivity(), new TakePhoto.CallbackTakePhoto() {
                @Override
                public void getPath(String imgDecodableStrings, String mCurrentPhotoPaths) {
                    if (from.equals(Keys.FROM_GALLERY_ADAPTER)) {
                        imgDecodeStringForGallery = imgDecodableStrings;
                    } else {
                      //onActivityUserPhotoCall = false;
                       imgDecodeString = imgDecodableStrings;
                    }
                }
            }).dialogTakePic();
        }
    }
    private String pitchVideoThumbNail;
    private String fromPage;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    /** get intent data from other application. */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMG:
                if (resultCode == RESULT_OK && data != null)
                {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    imgDecodeStringForGallery = UtilsMethods.getImageFromUri(getActivity(), selectedImage);
                    imgDecodeStringForGallery = UtilsMethods.compressImage(getActivity(), imgDecodeStringForGallery);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                    uploadImage(selectedImage,"image","");
                }
                break;
            case Keys.TAKE_VIDEO_CODE:
                if (resultCode == RESULT_OK)
                {
                    Uri videoUri = Uri.parse(mCurrentVideoPath);
                    Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(videoUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
                    String   pitchVideoThumbNail = UtilsMethods.saveImageToInternalStorage(getActivity(), bmThumbnail);
                    uploadVideoThumNail(videoUri,Uri.fromFile(new File(pitchVideoThumbNail)));
                }
                break;

            case Keys.TAKE_PHOTO_CODE:
        if (resultCode == RESULT_OK)
        {

            if (resultCode == RESULT_OK)
            {
                if (fromPage.equals(Keys.FROM_GALLERY_ADAPTER))
                {

                    imgDecodeStringForGallery = UtilsMethods.compressImage(getActivity(), imgDecodeStringForGallery);
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                   // Crop.of(Uri.fromFile(new File(imgDecodeStringForGallery)), destination).asSquare().start(getActivity());
                    uploadImage(Uri.fromFile(new File(imgDecodeStringForGallery)),"image","");                }
                else
                {
                    imgDecodeString = UtilsMethods.compressImage(getActivity(), imgDecodeString);
                    //  imageViewPhotoView.setImageBitmap(BitmapFactory.decodeFile(imgDecodeString));
                    Uri destination = Uri.fromFile(new File(UtilsMethods.getCropedPath()));
                  //  Crop.of(Uri.fromFile(new File(imgDecodeString)), destination).asSquare().start(getActivity());
                    uploadImage(Uri.fromFile(new File(imgDecodeString)),"image","");
                }
            }


        }
        break;

            case RESULT_LOAD_VID:
                if (resultCode == RESULT_OK && data != null)
                {
                    if (resultCode == RESULT_OK && data != null) {
                        // Get the Image from data
                        Uri selectedVideo = data.getData();
                        String   vidDecodeString = UtilsMethods.getVideoFromUri(getActivity(), selectedVideo);

                        ;
                        String  pitchVideoThumbNail = UtilsMethods.getThumbnailPathForLocalFile(getActivity(), selectedVideo);

                        uploadVideoThumNail(selectedVideo,Uri.fromFile(new File(pitchVideoThumbNail)));


                    }
                }
                        break;


                case RESULT_LOAD_FILE:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri PathHolder = data.getData();
                    String uriString = PathHolder.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getActivity().getContentResolver().query(PathHolder, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }

                    uploadFile(PathHolder,"file",displayName);
                }

        }
    }
    private void uploadImage( Uri filePath,String type,String thummNail)
    {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Envoi en cours...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref=null;
            StorageMetadata metadata=null;
            if (type.equalsIgnoreCase("image")) {
                metadata = new StorageMetadata.Builder()
                        .setContentType("image/png")
                        .build();

                ref = storageReference.child("message_images/"+ UUID.randomUUID().toString());
            }
            if (type.equalsIgnoreCase("video")) {
                metadata = new StorageMetadata.Builder()
                        .setContentType("video/mp4")
                        .build();
                 ref = storageReference.child("message_videos/"+ UUID.randomUUID().toString());
            }
            if (type.equalsIgnoreCase("file")) {
                 ref = storageReference.child("message_files/"+ UUID.randomUUID().toString());
            }

            ref.putFile(filePath,metadata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String str = taskSnapshot.getDownloadUrl().toString();
                            progressDialog.dismiss();
//increasee unread count
                            FirebaseDatabase.
                                    getInstance().
                                    getReference().
                                    child(StaticConfig.type_to+"/"+StaticConfig.BID).child("totalUnreadCount")

                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue() != null) {
                                                String count=dataSnapshot.getValue().toString();

                                                int counts=Integer.parseInt(count)+1;

                                                FirebaseDatabase.getInstance().getReference()
                                                        .child(StaticConfig.type_to+"/"+StaticConfig.BID)
                                                        .child("totalUnreadCount")
                                                        .setValue(counts);
                            Message newMessage = new Message();
                            if (type.equalsIgnoreCase("image")) {
                                newMessage.text = "image";
                                newMessage.imageUrl =str;
                            }
                            if (type.equalsIgnoreCase("video")) {

                                newMessage.imageUrl =thummNail;
                                newMessage.text = "video";
                                newMessage.videoUrl =str;
                            }
                            if (type.equalsIgnoreCase("file")) {
                                newMessage.text = "File";
                                newMessage.fileUrl =str;
                            }

                            newMessage.fromId = StaticConfig.UID;
                            newMessage.toId = StaticConfig.BID;

                            newMessage.fcmToken =StaticConfig.fcm_type_to;
                            newMessage.timeStamp =System.currentTimeMillis() / 1000;
                            newMessage.totalUnreadCount =""+counts;
                           // if(online_status) {
                                newMessage.readStatus = "0";
                           // }
                            /*if(StaticConfig.Type=="seeker")
                            {*/
                                newMessage.from.type =StaticConfig.type_from;
                                newMessage.to.type =StaticConfig.type_to;
                            newMessage.to.profilePic = StaticConfig.to_profile_pick;
                            newMessage.from.profilePic = Singleton.getUserInfoInstance().getuser_pic();
                           /* }
                            else
                            {
                                newMessage.from.type = "recruiter";
                                newMessage.to.type = "seeker";

                            }*/

                            newMessage.from.name =Singleton.getUserInfoInstance().getFirst_name()
                                    + " " + Singleton.getUserInfoInstance().getLast_name();
                            newMessage.to.name = StaticConfig.to_name;


                            final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                            final String key = mss.child("Messages").push().getKey();
                            newMessage.messageID = key;
                            mss.child("Messages/" + key).setValue(newMessage).
                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mss.child("User-List/" + StaticConfig.UID + "/" +
                                                    StaticConfig.BID + "/" + key).setValue(1);
                                            //create user_msg for reciver
                                            mss.child("User-List/" + StaticConfig.BID + "/" +
                                                    StaticConfig.UID + "/" + key).setValue(1);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Write failed
                                            // ...
                                        }
                                    });
                            ;

                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                        child(StaticConfig.UID).child(StaticConfig.BID + "/unreadMessages")
                                        .child(key).setValue(1);

                                            }
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value

                                        }
                                    });




                            //Toast.makeText(getActivity(), "Uploaded"+str, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }


    private void uploadFile( Uri filePath,String type,String thummNail)
    {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Envoi en cours...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref=null;

                ref = storageReference.child("message_files/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String str = taskSnapshot.getDownloadUrl().toString();
                            progressDialog.dismiss();



//increasee unread count
                            FirebaseDatabase.
                                    getInstance().
                                    getReference().
                                    child(StaticConfig.type_to+"/"+StaticConfig.BID).child("totalUnreadCount")

                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.getValue() != null) {
                                                String count=dataSnapshot.getValue().toString();

                                                int counts=Integer.parseInt(count)+1;
                                                FirebaseDatabase.getInstance().getReference()
                                                        .child(StaticConfig.type_to+"/"+StaticConfig.BID)
                                                        .child("totalUnreadCount")
                                                        .setValue(counts);
                                                Message newMessage = new Message();

                                                    newMessage.text = "File";
                                                    newMessage.fileUrl =str;

                                                newMessage.fromId = StaticConfig.UID;
                                                newMessage.toId = StaticConfig.BID;

                                                newMessage.fcmToken =StaticConfig.fcm_type_to;
                                                newMessage.timeStamp =System.currentTimeMillis() / 1000;
                                                newMessage.totalUnreadCount =""+counts;
                                                // if(online_status) {
                                                newMessage.readStatus = "0";
                                                // }
                            /*if(StaticConfig.Type=="seeker")
                            {*/
                                                newMessage.from.type =StaticConfig.type_from;
                                                newMessage.to.type =StaticConfig.type_to;
                                                newMessage.to.profilePic = StaticConfig.to_profile_pick;
                                                newMessage.from.profilePic = Singleton.getUserInfoInstance().getuser_pic();



                                                newMessage.fileName =thummNail;


                           /* }
                            else
                            {
                                newMessage.from.type = "recruiter";
                                newMessage.to.type = "seeker";

                            }*/

                                                newMessage.from.name =Singleton.getUserInfoInstance().getFirst_name()
                                                        + " " + Singleton.getUserInfoInstance().getLast_name();
                                                newMessage.to.name = StaticConfig.to_name;


                                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                                final String key = mss.child("Messages").push().getKey();
                                                newMessage.messageID = key;
                                                mss.child("Messages/" + key).setValue(newMessage).
                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                mss.child("User-List/" + StaticConfig.UID + "/" +
                                                                        StaticConfig.BID + "/" + key).setValue(1);
                                                                //create user_msg for reciver
                                                                mss.child("User-List/" + StaticConfig.BID + "/" +
                                                                        StaticConfig.UID + "/" + key).setValue(1);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                // Write failed
                                                                // ...
                                                            }
                                                        });
                                                ;

                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(StaticConfig.UID).child(StaticConfig.BID + "/unreadMessages")
                                                        .child(key).setValue(1);

                                            }
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value

                                        }
                                    });




                            //Toast.makeText(getActivity(), "Uploaded"+str, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }




    private void uploadVideoThumNail( Uri filePath,Uri thumNail)
    {

           storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref= storageReference.child("message_images/"+ UUID.randomUUID().toString());

            ref.putFile(thumNail)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                              @Override
                                              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                  String str = taskSnapshot.getDownloadUrl().toString();
uploadImage(filePath,"video",str);

                                              }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            //progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

    }
    private String vidDecodeString;
    private boolean onActivityPitchVideoCall;
    /** method for get video from camera/gallery */
    private void takeVideoOption() {
        String[] permissions = {PermissionKeys.PERMISSION_CAMERA, PermissionKeys.PERMISSION_WRITE_EXTERNAL_STORAGE};
        if (marshmallowPermission.isPermissionGrantedAll(PermissionKeys.REQUEST_CODE_PERMISSION_ALL_VIDEO, permissions)) {
            new TakeVideo(getActivity(), new TakeVideo.CallbackTakeVideo() {
                @Override
                public void getPath(String vidDecodableStrings, String mCurrentVideoPaths) {
                    vidDecodeString = vidDecodableStrings;
                    mCurrentVideoPath = mCurrentVideoPaths;
                    onActivityPitchVideoCall = false;
                }
            }).dialogTakeVideo();
        }
    }

    private void pickFile()
    {

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), RESULT_LOAD_FILE);
    }



    /** method for open dialog for ask to take a photo from camera and from gallery */
    public void dialogMultimedia()
    {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_multi_media_photo);
        LinearLayout linearCancel = (LinearLayout) dialog.findViewById(R.id.linearCancel);
        CustomsTextView textViewPicture = (CustomsTextView) dialog.findViewById(R.id.image);
        CustomsTextView textViewVideo = (CustomsTextView) dialog.findViewById(R.id.video);
        CustomsTextView video_call = (CustomsTextView) dialog.findViewById(R.id.video_call);
        CustomsTextView file = (CustomsTextView) dialog.findViewById(R.id.file);
        // pickFile();
        //
        //

        textViewPicture.setOnClickListener(v -> {
            dialog.dismiss();
            takePhotoOption("");
        });


        textViewVideo.setOnClickListener(v -> {
            dialog.dismiss();
            takeVideoOption();
        });

        video_call.setOnClickListener(v -> {
            dialog.dismiss();

            Intent intent = new Intent(getActivity(), VideoActivity.class);
            StaticConfig.to_name_chatter= ((TextView)rootView.findViewById(R.id.name))
                    .getText().toString();
            startActivity(intent);
        });
        file.setOnClickListener(v -> {
            dialog.dismiss();
            pickFile();

        });


        linearCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }



    void ShowDialogs(String type,String url) {
        // custom dialog
        //final Dialog dialog = new Dialog(getActivity());
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.chat_video_image);


    }
}
