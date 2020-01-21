package com.infoicon.bonjob.chatModule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.infoicon.bonjob.chatModule.database.DbConstants;
import com.infoicon.bonjob.chatModule.model.ChatMessage;
import com.infoicon.bonjob.chatModule.model.ChatModel;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.AppConstants;
import com.infoicon.bonjob.utils.Keys;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.ping.PingFailedListener;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.ping.android.ServerPingWithAlarmManager;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by infoicona on 26/5/17.
 */

public class MyXMPP {
    private Chat Mychat;
    private boolean connected = false;
    private boolean loggedin = false;
    private boolean isconnecting = false;
    private boolean isToasted = true;
    private boolean chat_created = false;
    private String serverAddress;
    private XMPPTCPConnection connection;
    private Gson gson;
    private MyService context;
    public static MyXMPP instance = null;
    public static boolean instanceCreated = false;
    private Roster roster;
    private ChatManagerListenerImpl mChatManagerListener;
    private MMessageListener mMessageListener;

    public MyXMPP(MyService context, String serverAdress) {
        this.serverAddress = serverAdress;
        this.context = context;
        init();
    }

    public static MyXMPP getInstance(MyService context, String server) {
        if (instance == null) {
            instance = new MyXMPP(context, server);
            instanceCreated = true;
        }
        return instance;
    }

    static {
        try {
            Class.forName("org.jivesoftware.smack.ReconnectionManager");
        } catch (ClassNotFoundException ex) {
            // problem loading reconnection manager
        }
    }

    public void init() {
        gson = new Gson();
        mMessageListener = new MMessageListener(context);
        mChatManagerListener = new ChatManagerListenerImpl();
        initialiseConnection();
    }

    /** initialize connection */
    public void initialiseConnection() {
        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration
                .builder();
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        //  config.setServiceName("localhost");
        config.setServiceName(serverAddress);
        config.setHost(serverAddress);
        config.setPort(5222);
        // config.setUsernameAndPassword("kollyde102@localhost", "kollyde102");
        config.setDebuggerEnabled(true);

        XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true);
        XMPPTCPConnection.setUseStreamManagementDefault(true);

        connection = new XMPPTCPConnection(config.build());
        XMPPConnectionListener connectionListener = new XMPPConnectionListener();
        connection.addConnectionListener(connectionListener);
        connection.setPacketReplyTimeout(15000);

        // Add reconnect manager
        ReconnectionManager.getInstanceFor(connection).enableAutomaticReconnection();

        // Add ping manager here
        PingManager.setDefaultPingInterval(60); //Ping every 1 minutes
        PingManager.getInstanceFor(connection).registerPingFailedListener(pingFailedListener);

    }

    /** ping to manager for get status of server */
    private PingFailedListener pingFailedListener = new PingFailedListener() {
        @Override
        public void pingFailed() {
            Logger.e("pingFailed");
            disconnect();
            initialiseConnection();
        }
    };

    /** disconnect from connection */
    public void disconnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (connection != null) {
                        ReconnectionManager.getInstanceFor(connection).disableAutomaticReconnection();
                        PingManager.getInstanceFor(connection).unregisterPingFailedListener(pingFailedListener);
                        connection.disconnect();
                        Logger.e("connection disconnected");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /** connect to xmpp server */
    public void connect(final String caller) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Boolean> connectionThread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected synchronized Boolean doInBackground(Void... arg0) {
                if (connection.isConnected())
                    return false;
                isconnecting = true;
                if (isToasted)
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            //   CommonUtils.showToast(context, caller + "=>connecting....");
                        }
                    });
                Log.d("Connect() Function", caller + "=>connecting....");

                try {
                    connection.connect();
                    connected = true;
                } catch (IOException e) {
                    if (isToasted)
                        new Handler(Looper.getMainLooper())
                                .post(new Runnable() {

                                    @Override
                                    public void run() {
                                        //     CommonUtils.showToast(context, "(" + caller + ")" + "IOException: ");
                                    }
                                });

                    Log.e("(" + caller + ")", "IOException: " + e.getMessage());
                } catch (SmackException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            // CommonUtils.showToast(context, "(" + caller + ")" + "SMACKException: ");

                            disconnect();
                            initialiseConnection();
                            //reconnection service
                            context.sendBroadcast(new Intent(Keys.RECONNECTION));

                        }
                    });
                    Log.e("(" + caller + ")", "SMACKException: " + e.getMessage());

                    //reconnection service
                    // context.sendBroadcast(new Intent(Keys.RECONNECTION));
                } catch (XMPPException e) {
                    if (isToasted)
                        new Handler(Looper.getMainLooper())
                                .post(new Runnable() {

                                    @Override
                                    public void run() {

                                        //      CommonUtils.showToast(context, "(" + caller + ")" + "XMPPException: ");
                                    }
                                });
                    Log.e("connect(" + caller + ")", "XMPPException: " + e.getMessage());
                }
                return isconnecting = false;
            }
        };
        connectionThread.execute();
    }

    /** login from xpp server */
    public void login() {
        try {
            Logger.e("chat username : " + Singleton.getUserInfoInstance().getChatId() + " : chat password : " + Singleton.getUserInfoInstance().getPassword());
            connection.login(Singleton.getUserInfoInstance().getChatId(), Singleton.getUserInfoInstance().getPassword());
            // Set the status to available
            Presence presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.available);
            connection.sendPacket(presence);
            Log.i("LOGIN", "Yey! We're connected to the Xmpp server!");

        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** chat listener for receive messages from roaster */
    private class ChatManagerListenerImpl implements ChatManagerListener {
        @Override
        public void chatCreated(final Chat chat, final boolean createdLocally) {
            if (!createdLocally)
                chat.addMessageListener(mMessageListener);
        }
    }

    /** send message to other end */
    public void sendMessage(ChatMessage chatMessage) {
        String body = chatMessage.getContent();
        Mychat = ChatManager.getInstanceFor(connection).createChat(
                chatMessage.receiver + "@" + serverAddress,
                mMessageListener);
        chat_created = true;

        final Message message = new Message();

        message.setBody(body);

        message.setStanzaId(chatMessage.msgid);

        message.setType(Message.Type.chat);

        try {
            if (connection.isAuthenticated()) {
                if (Mychat != null) {
                    Mychat.sendMessage(message);
                } else {
                    Logger.e("Chat is null");
                }
            }
            else
                {
                initialiseConnection();
                login();
            }
        } catch (SmackException.NotConnectedException e) {
            Log.e("xmpp.SendMessage()", "msg Not sent!-Not Connected!");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("", "msg Not sent!" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** check the connection is authenticated */
    public boolean isAuthenticated() {
        if (connection != null)
            return connection.isAuthenticated();
        else return false;
    }

    /** xmpp connection listener class */
    public class XMPPConnectionListener implements ConnectionListener
    {
        @Override
        public void connected(final XMPPConnection connection) {
            connection.getStreamId();
            Log.d("xmpp", "Connected!");
            connected = true;
            if (!connection.isAuthenticated()) {
                login();
            }
        }

        @Override
        public void connectionClosed() {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // Toast.makeText(context, "ConnectionCLosed!", Toast.LENGTH_SHORT).show();
                    }
                });
            Log.d("xmpp", "ConnectionCLosed!");
            connected = false;
            chat_created = false;
            loggedin = false;
        }

        @Override
        public void connectionClosedOnError(Exception arg0) {
            if (isToasted)
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run() {
                    }
                });
            Log.d("xmpp", "ConnectionClosedOn Error!");
            connected = false;
            chat_created = false;
            loggedin = false;
        }

        @Override
        public void reconnectingIn(int arg0) {

            Log.d("xmpp", "Reconnectingin " + arg0);

            loggedin = false;
            disconnect();
            initialiseConnection();
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {

                    if (!connection.isAuthenticated())
                        login();
                }
            });
        }

        @Override
        public void reconnectionFailed(Exception arg0) {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        //  CommonUtils.showToast(context, "ReconnectionFailed!");
                    }
                });
            Log.d("xmpp", "ReconnectionFailed!");
            connected = false;
            chat_created = false;
            loggedin = false;
        }

        @Override
        public void reconnectionSuccessful() {
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                    }
                });
            Log.d("xmpp", "ReconnectionSuccessful");
            connected = true;
            chat_created = false;
            loggedin = false;
        }

        @Override
        public void authenticated(XMPPConnection arg0, boolean arg1) {
            Log.d("xmpp", "Authenticated!");
            loggedin = true;

            ChatManager.getInstanceFor(connection).addChatListener(mChatManagerListener);

            chat_created = false;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
            if (isToasted)

                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        // TODO Auto-generated method stub
                        // Set the status to available
                        Presence presence = new Presence(Presence.Type.available);
                        presence.setMode(Presence.Mode.available);
                        try {
                            connection.sendPacket(presence);
                        } catch (SmackException.NotConnectedException e) {
                            e.printStackTrace();
                        }

                        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
                        roster = Roster.getInstanceFor(connection);
                        //listener for getting real time online status
                        roster.addRosterListener(rosterListener);

                    }
                });
        }
    }

    private class MMessageListener implements ChatMessageListener {

        public MMessageListener(Context contxt) {
        }

        @Override
        public void processMessage(final Chat chat, final Message message) {
            if (message.getType() == Message.Type.chat && message.getBody() != null) {
                String from = message.getFrom();
                from = from.substring(0, from.lastIndexOf('@'));
                processMessage(from, message);
            }
        }

        private void processMessage(String from, final Message message) {

            //  int message_id = new Random().nextInt(1000);
            String message_id = message.getStanzaId();
            String date = CommonMethods.getCurrentDate();
            String time = CommonMethods.getCurrentTime();

            //mode to save db
            ChatModel chatModel = new ChatModel();
            chatModel.setSender_id(Singleton.getUserInfoInstance().getChatId());
            chatModel.setReceiver_id(from);
            chatModel.setMessage(message.getBody());
            chatModel.setMsg_id(message_id);
            chatModel.setMine(false);
            chatModel.setDate(date);
            chatModel.setTime(time);

            // send broadcast receiver
            Intent intent = new Intent(Keys.CHAT);
            intent.putExtra(Keys.IS_MINE, false);
            intent.putExtra(Keys.DATE, date);
            intent.putExtra(Keys.TIME, time);
            intent.putExtra(Keys.CONTENT, message.getBody());
            intent.putExtra(Keys.SENDER, Singleton.getUserInfoInstance().getChatId());
            intent.putExtra(Keys.RECEIVER, from);
            intent.putExtra(Keys.MSG_ID, message_id);

            //check table exist
            if (Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + "" + from)) {
                //check message id exist not
                if (!Singleton.getDbHelper().isMessageIdExist(message_id, Singleton.getUserInfoInstance().getChatId(), from)) {

                    //check message unread
                    if (AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(from)) {
                        chatModel.setUnread(0);
                        Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), from);
                    } else {
                        // int count = Singleton.getDbHelper().getUnreadCount(Singleton.getUserInfoInstance().getChatId(), from);
                        chatModel.setUnread(1);
                        Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), from);
                        context.sendBroadcast(new Intent(Keys.UNREAD_STATUS));
                        context.sendBroadcast(new Intent(Keys.UNREAD_STATUS_HOME));
                    }
                    context.sendBroadcast(intent);

                } else {
                    Logger.e("Message id exist : " + message_id);
                }
            } else {
                Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), from);

                //check message unread
                if (AppConstants.IS_CHAT_VISIBLE) {
                    chatModel.setUnread(0);
                    Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), from);
                } else {
                    // int count = Singleton.getDbHelper().getUnreadCount(Singleton.getUserInfoInstance().getChatId(), from);
                    chatModel.setUnread(1);
                    Singleton.getDbHelper().storeChatToDb(chatModel, Singleton.getUserInfoInstance().getChatId(), from);
                    context.sendBroadcast(new Intent(Keys.UNREAD_STATUS));
                }
                context.sendBroadcast(intent);
            }
        }
    }

    /** get the online list user */
    RosterListener rosterListener = new RosterListener() {
        @Override
        public void entriesAdded(Collection<String> addresses) {
            //Called when the presence of a roster entry is changed
            Logger.e("entriesAdded " + addresses.size());

            //Get all rosters
            Collection<RosterEntry> entries = roster.getEntries();
            //loop through
            for (RosterEntry entry : entries) {
                //example: get presence, type, mode, status
                Presence entryPresence = roster.getPresence(entry.getUser());
                Presence.Type userType = entryPresence.getType();
                Presence.Mode mode = entryPresence.getMode();

                String from = entryPresence.getFrom();
                from = from.substring(0, from.lastIndexOf('@'));
                Singleton.getOnlineList().put(from, userType.toString());
           //     Logger.e("status of user"+userType.toString());

                if (AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(from)) {
                    Intent intent = new Intent(Keys.ONLINE_STATUS);
                    context.sendBroadcast(intent);
                }/*else if(!AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(from)){
                    Logger.e("presenceChangedEntries " + userType.toString());
                    Presence pres = new Presence(Presence.Type.unavailable);
                    try {
                        connection.sendPacket(pres);
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }*/
            }

        }

        @Override
        public void entriesUpdated(Collection<String> addresses) {
            // Called when a roster entries are updated.
            Logger.e("entriesUpdated " + addresses.size());
        }

        @Override
        public void entriesDeleted(Collection<String> addresses) {
            // Called when a roster entries are removed.
            Logger.e("entriesDeleted " + addresses.size());
        }

        @Override
        public void presenceChanged(Presence presence) {
            // Called when a roster entries are added.
            Logger.e("presenceChanged " + presence.getType());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String from = presence.getFrom();
                    from = from.substring(0, from.lastIndexOf('@'));
                    Singleton.getOnlineList().put(from, presence.getType().toString());
                    if (AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(from)) {
                        Intent intent = new Intent(Keys.ONLINE_STATUS);
                        context.sendBroadcast(intent);
                    }/*else if(!AppConstants.IS_CHAT_VISIBLE && AppConstants.CURRENT_ROSTER.equals(from)){
                        Logger.e("presenceChangedUser " + presence.getType());
                        presence.setType(Presence.Type.unavailable);
                        try {
                            connection.sendPacket(presence);
                        } catch (SmackException.NotConnectedException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }).start();


        }
    };
           public  void setUserPresenceWhenOffline(){
               Logger.e("setPresence " + "setPresence ");
               Presence pres = new Presence(Presence.Type.unavailable);
               try {
                   connection.sendPacket(pres);
               } catch (SmackException.NotConnectedException e) {
                   e.printStackTrace();
               }
           }
    public  void setUserPresenceWhenOnline(){
        Logger.e("setPresence " + "setPresence ");
        Presence pres = new Presence(Presence.Type.available);
        try {
            connection.sendPacket(pres);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

}
