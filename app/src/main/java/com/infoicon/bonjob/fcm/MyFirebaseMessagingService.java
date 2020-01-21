package com.infoicon.bonjob.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.StaticConfig;
import com.infoicon.bonjob.chat.VideoActivity;
import com.infoicon.bonjob.chatModule.CommonMethods;
import com.infoicon.bonjob.chatModule.model.ChatModel;
import com.infoicon.bonjob.login.LoginSignUpRecruiterActivity;
import com.infoicon.bonjob.login.LoginSignUpSeekerActivity;
import com.infoicon.bonjob.login.LoginSignupOptionActivity;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.splash.SplashActivity;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.Keys;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";
    private String notificationType = "";
    private String message = "";

    private void playSound() {
    /*    val alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + this.packageName + "/raw/loud_alert")*/


    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println(TAG + " From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println(TAG + " Message data payload: " + remoteMessage.getData());
        //    {"candidate_id":"","aplied_id":"","job_id":"","message":"Déconnecter un appel vidéo de Henry","type":"disconnectcall","badgeCount":3,"employer_id":""}
        //{"room_name":"37863863","grant_access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4LTE1NjY1NjIyNDgiLCJpc3MiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4Iiwic3ViIjoiQUNkZmNlZjE2MzM1MThhZGMyNjA5ZWMyM2RkODVmMjg1OSIsImV4cCI6MTU2NjU2NTg0OCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiVmlrYXNoIFZpa2FzaCBHaXJpIiwidmlkZW8iOnsicm9vbSI6IjM3ODYzODYzIn19fQ.Qt5CljV3c36-oye2uicumKTohstfzOxrm99KNRF2KLI","user_pic":"http:\/\/172.104.8.51\/bonjob\/public\/images\/default.jpeg","user_id":"3785","user_name":"Henry Henry","sound":"loud_alert.caf","message":"Incoming Video Call from Henry","type":"call","badgeCount":"1"}
        //Bundle[{google.delivered_priority=normal, google.sent_time=1566562334684, google.ttl=2419200, google.original_priority=normal, from=1082064392881, google.message_id=0:1566562334691139%8733999df9fd7ecd, message={"room_name":"37863863","grant_access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4LTE1NjY1NjIzMzQiLCJpc3MiOiJTS2ViZjUzYTc5YjMwYjFmMTA4NTFlODIxZmVkNjA4ZTI4Iiwic3ViIjoiQUNkZmNlZjE2MzM1MThhZGMyNjA5ZWMyM2RkODVmMjg1OSIsImV4cCI6MTU2NjU2NTkzNCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiVmlrYXNoIFZpa2FzaCBHaXJpIiwidmlkZW8iOnsicm9vbSI6IjM3ODYzODYzIn19fQ.e5NAM1KdQKONNxgn0DwDMTKok3vlDkJUpkksNZJJhx8","user_pic":"http:\/\/172.104.8.51\/bonjob\/public\/images\/default.jpeg","user_id":"3785","user_name":"Henry Henry","sound":"loud_alert.caf","message":"Incoming Video Call from Henry","type":"call","badgeCount":"1"}}]

            JSONObject json = null;
            JSONObject object = null;

            try {
                json = new JSONObject(remoteMessage.getData().toString());
                object = json.getJSONObject("message");
                message = object.getString("message");
                notificationType = object.getString(Keys.NOTIFICATION_TYPE);

                if (notificationType.equals("chat")) {

                    String date = CommonMethods.getCurrentDate();
                    String time = CommonMethods.getCurrentTime();
                    String message_id = object.getString("message_id");
                    String from = Keys.BONJOB_ + object.getString("from");
                    String to = Keys.BONJOB_ + object.getString("to");

                    //mode to save db
                    ChatModel chatModel = new ChatModel();
                    chatModel.setSender_id(to);
                    chatModel.setReceiver_id(from);
                    chatModel.setMessage(object.getString("message"));
                    chatModel.setMsg_id(message_id);
                    chatModel.setMine(false);
                    chatModel.setDate(date);
                    chatModel.setTime(time);
                    if (AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(Keys.BONJOB_ + object.getString("from"))) {
                       //0 read,1 unread
                        chatModel.setUnread(0);
                    } else {
                        chatModel.setUnread(1);
                        sendBroadcast(new Intent(Keys.UNREAD_STATUS));
                        sendBroadcast(new Intent(Keys.UNREAD_STATUS_HOME));
                    }

                    // send broadcast receiver
                    Intent intent = new Intent(Keys.CHAT);
                    intent.putExtra(Keys.IS_MINE, false);
                    intent.putExtra(Keys.DATE, date);
                    intent.putExtra(Keys.TIME, time);
                    intent.putExtra(Keys.CONTENT, object.getString("message"));
                    intent.putExtra(Keys.SENDER, to);
                    intent.putExtra(Keys.RECEIVER, from);
                    intent.putExtra(Keys.MSG_ID, message_id);

                    if (Singleton.getDbHelper().isTableExists(to + "" + from)) {
                        if (!Singleton.getDbHelper().isMessageIdExist(message_id, to, from)) {
                            Singleton.getDbHelper().storeChatToDb(chatModel, to, from);
                            sendBroadcast(intent);
                        }
                    } else {
                        Singleton.getDbHelper().createTableUser(to, from);
                        Singleton.getDbHelper().storeChatToDb(chatModel, to, from);
                        sendBroadcast(intent);
                    }


                } else {
                    /** activity count manage from notification. */
                    if (!Singleton.getUserInfoInstance().isLoginRecriter()) {
                        int count = Singleton.getUserInfoInstance().getSeekerActivityCount();
                        Singleton.getUserInfoInstance().setSeekerActivityCount(count + 1);
                        sendBroadcast(new Intent(Keys.ACTIVITY_COUNT));
                    } else {
                        if (notificationType.equals("Apply Job")) {
                            sendBroadcast(new Intent(Keys.APPLY_CANDIDATE_NOTIFY_TO_RECRUITER));
                        } else if (notificationType.equals("paymentSuccess")
                                || notificationType.equals("paymentFailed")) {

                        } else {
                            int count = Singleton.getUserInfoInstance().getRecruiterOfferCount();
                            Singleton.getUserInfoInstance().setRecruiterOfferCount(count + 1);
                            sendBroadcast(new Intent(Keys.MY_OFFER_COUNT));
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (object != null)
                sendNotification(object.toString());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            System.out.println(TAG + " Message Notification Body: " + remoteMessage.getNotification().getBody());
            // sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = null;
        if (notificationType.equals("selectCandidate")) {
            if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("jobexpired")) {
            if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("notSelectCandidate")) {
            if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("hireCandidate")) {
            if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        }
        //after search by recuter
        else if (notificationType.equals("SelectedByRecruiter")) {
            if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpSeekerActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("Apply Job")) {
            if (Singleton.getUserInfoInstance().isLogin() && Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("acceptRequest")) {
            if (Singleton.getUserInfoInstance().isLogin() && Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("rejectRequest")) {
            if (Singleton.getUserInfoInstance().isLogin() && Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.FOR, "");
            } else {
                intent = new Intent(this, LoginSignUpRecruiterActivity.class);
                intent.putExtra(Keys.NOTIFICATION_JSON, messageBody);
                intent.putExtra(Keys.CLICKED_EVENT, Keys.CLICKED_EVENT_LOGIN);
            }
        } else if (notificationType.equals("chat")) {
            if (Singleton.getUserInfoInstance().isLogin() && Singleton.getUserInfoInstance().isLoginRecriter() || Singleton.getUserInfoInstance().getLoginAdmin()) {
                intent = new Intent(this, HomeRecruiterActivity.class);
            } else if (Singleton.getUserInfoInstance().isLogin() && !Singleton.getUserInfoInstance().isLoginRecriter()) {
                intent = new Intent(this, HomeSeekerActivity.class);
            } else {
                intent = new Intent(this, LoginSignupOptionActivity.class);
            }
            JSONObject object = null;
            String date = CommonMethods.getCurrentDate();
            String time = CommonMethods.getCurrentTime();
            try {
                object = new JSONObject(messageBody);
                String from = Keys.BONJOB_ + object.getString("from");
                intent.putExtra(Keys.IS_MINE, false);
                intent.putExtra(Keys.DATE, date);
                intent.putExtra(Keys.TIME, time);
                intent.putExtra(Keys.CONTENT, object.getString("message"));
                intent.putExtra(Keys.SENDER, Singleton.getUserInfoInstance().getChatId());
                intent.putExtra(Keys.RECEIVER, from);
                intent.putExtra(Keys.MSG_ID, object.getString("message_id"));
                intent.putExtra(Keys.FOR, Keys.CHAT);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (notificationType.equals("call"))
        {
          //  if (isAppIsInBackground(this)) {

                intent = new Intent(this, VideoActivity.class);
                JSONObject object = null;

                try {
                    object = new JSONObject(messageBody);
                    String from = object.getString("room_name");
                    String grant_access_token = object.getString("grant_access_token");
                    StaticConfig.to_name_chatter = object.getString("user_name");
                    intent.putExtra(Keys.NAME, from);
                    intent.putExtra(Keys.DEVICE_TOKEN, grant_access_token);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

           /* intent = new Intent(this, VideoActivity.class);
            JSONObject object = null;

            try {
                object = new JSONObject(messageBody);
                String from = object.getString("room_name");
                String grant_access_token = object.getString("grant_access_token");
                intent.putExtra(Keys.NAME, from);
                intent.putExtra(Keys.DEVICE_TOKEN, grant_access_token);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

        } else if (notificationType.equals("disconnectcall")) {
            intent = new Intent();
            JSONObject object = null;

            try {
                VideoActivity.VideoActivity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (notificationType.equals("rejectcall")) {
            intent = new Intent();
            JSONObject object = null;

            try {
                VideoActivity.VideoActivity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            intent = new Intent(this, SplashActivity.class);
        }
        intent.putExtra(Keys.FROM, Keys.NOTIFICATION);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
        showSmallNotification(R.mipmap.ic_launcher_round, getResources().getString(R.string.app_name),
                message, pendingIntent);
       /* Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(this, R.color.pink_color))
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Don't forget to set the ChannelID!!
           notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(this, R.color.pink_color))
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

          //  notificationManager.notify(0, notificationBuilder.build());

        }

        showSmallNotification(R.mipmap.app_icon,getResources().getString(R.string.app_name),
             message, pendingIntent);
        // I removed one of the semi-colons in the next line of code
    }*/
    }
    private void showSmallNotification( int icon, String title, String message, PendingIntent resultPendingIntent) {


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       /* NotificationCompat.Builder builder =
                new  NotificationCompat.bui.Builder(context, channelId);
   */
        NotificationCompat.Builder mBuilder = new NotificationCompat.
                Builder(this, channelId);
        Notification notification = mBuilder.setContentTitle(message)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher_round))
                .setContentIntent(resultPendingIntent).build();

        Random random = new Random();
        int id = random.nextInt(10000);
        Log.e("notification_id", id + "");
        notificationManager.notify(id, notification);
    }


    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}