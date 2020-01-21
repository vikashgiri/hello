package com.infoicon.bonjob.chatModule.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.infoicon.bonjob.chatModule.model.ChatModel;
import com.infoicon.bonjob.logger.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by infoicon on 14/4/17.
 * this is the main class of database.
 */
public class DbHelper extends SQLiteOpenHelper {

    private String TAG = PackageManager.class.getName();
    private Context context;

    /**
     * Given database name and version code.
     */
    public DbHelper(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.VERSION_CODE);
        this.context = context;
    }

    /**
     * create table.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //  createTable(db);
    }

    /**
     * dropping table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbConstants.DROP_TABLE_IF_EXOSTS + DbConstants.TABLE_USERS);
        onCreate(db);
    }

    /**
     * create table
     */
    public void createTable(SQLiteDatabase db) {
        String cartTable = DbConstants.CREATE_TABLE + DbConstants.TABLE_USERS + "(" +
                DbConstants.COLUMN_SENDER_ID + DbConstants.TEXT + "," +
                DbConstants.COLUMN_RECEIVER_ID + DbConstants.TEXT +
                ")";
        Log.e(TAG, "TABLE CREATED " + DbConstants.TABLE_USERS);
        db.execSQL(cartTable);
    }

    /**
     * create table
     */
    public void createTableUser(String sender_id, String receiver) {
        //SQLiteDatabase db = context.openOrCreateDatabase(DbConstants.DATABASE_NAME,Context.MODE_WORLD_WRITEABLE, null);
        SQLiteDatabase db = getWritableDatabase();
        String cartTable = DbConstants.CREATE_TABLE + sender_id + "" + receiver + "(" +
                DbConstants.COLUMN_SENDER_ID + DbConstants.TEXT + "," +
                DbConstants.COLUMN_RECEIVER_ID + DbConstants.TEXT + "," +
                DbConstants.COLUMN_MESSSAE + DbConstants.TEXT + "," +
                DbConstants.COLUMN_MESSAGE_ID + DbConstants.TEXT + "," +
                DbConstants.COLUMN_DATE + DbConstants.TEXT + "," +
                DbConstants.COLUMN_TIME + DbConstants.TEXT + "," +
                DbConstants.COLUMN_IS_MINE + DbConstants.BOOLEAN + "," +
                DbConstants.UNREAD + DbConstants.INTEGER +
                ")";
        Log.e(TAG, "TABLE CREATED " + sender_id + "" + receiver);
        db.execSQL(cartTable);
        db.close();
    }

    /** store user to db */
    public void storeUserToDB(String sender_id, String receiver_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.COLUMN_SENDER_ID, sender_id);
        contentValues.put(DbConstants.COLUMN_RECEIVER_ID, receiver_id);
        sqLiteDatabase.insert(DbConstants.TABLE_USERS, null, contentValues);
        sqLiteDatabase.close();
        Log.e(TAG, "Data Inserted " + DbConstants.TABLE_USERS);
    }

    /**
     * adding item on chat
     */
    public void storeChatToDb(ChatModel chatModel, String sender, String receiver) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbConstants.COLUMN_SENDER_ID, chatModel.getSender_id());
        contentValues.put(DbConstants.COLUMN_RECEIVER_ID, chatModel.getReceiver_id());
        contentValues.put(DbConstants.COLUMN_MESSSAE, chatModel.getMessage());
        contentValues.put(DbConstants.COLUMN_MESSAGE_ID, chatModel.getMsg_id());
        contentValues.put(DbConstants.COLUMN_DATE, chatModel.getDate());
        contentValues.put(DbConstants.COLUMN_TIME, chatModel.getTime());
        contentValues.put(DbConstants.COLUMN_IS_MINE, chatModel.isMine());
        contentValues.put(DbConstants.UNREAD, chatModel.getUnread());
        try {
            sqLiteDatabase.insert(sender + "" + receiver, null, contentValues);
            Logger.e(TAG + "Data Inserted " + sender + "" + receiver);
        } catch (SQLiteConstraintException exception) {
            Logger.e(exception.getMessage());
        }
        sqLiteDatabase.close();
    }

    /**
     * adding chat history
     */
    public void storeChatHistoryToDb(ArrayList<ChatModel> chatModelArrayList, String sender, String receiver) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (ChatModel chatModel : chatModelArrayList) {
                contentValues.put(DbConstants.COLUMN_SENDER_ID, chatModel.getSender_id());
                contentValues.put(DbConstants.COLUMN_RECEIVER_ID, chatModel.getReceiver_id());
                contentValues.put(DbConstants.COLUMN_MESSSAE, chatModel.getMessage());
                contentValues.put(DbConstants.COLUMN_MESSAGE_ID, chatModel.getMsg_id());
                contentValues.put(DbConstants.COLUMN_DATE, chatModel.getDate());
                contentValues.put(DbConstants.COLUMN_TIME, chatModel.getTime());
                contentValues.put(DbConstants.COLUMN_IS_MINE, chatModel.isMine());
                contentValues.put(DbConstants.UNREAD, chatModel.getUnread());
                sqLiteDatabase.insert(sender + "" + receiver, null, contentValues);
            }
            Logger.e(TAG + "Chat History Inserted " + sender + "" + " ---Items :" + receiver + chatModelArrayList.size());
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLiteConstraintException exception) {
            Logger.e(exception.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

    /** check is message id exist in chat history */
    public boolean isMessageIdExist(String message_id, String sender, String receiver) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = null;
        String sql = "SELECT " + DbConstants.COLUMN_MESSAGE_ID + " FROM " + sender + "" + receiver + "  WHERE " + DbConstants.COLUMN_MESSAGE_ID + "=" + "'" + message_id + "' LIMIT 10";
        Logger.e("isMessageIdExist query : " + sql);
        cursor = sqLiteDatabase.rawQuery(sql, null);
        Logger.e("Cursor Count : " + cursor.getCount());

        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();
            sqLiteDatabase.close();
            return true;
        } else {
            cursor.close();
            sqLiteDatabase.close();
            return false;
            //PID Not Found
        }
    }

    /** get row count from table */
    public int getTableCount(String sender, String receiver) {
        String countQuery = "SELECT  * FROM " + sender + "" + receiver;
        SQLiteDatabase db = this.getReadableDatabase();
        int cnt = 0;
        try {
            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor != null && !cursor.isClosed()) {
                cnt = cursor.getCount();
                cursor.close();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return cnt;
    }

    /** get last date of chat */
    public String getLastDate(String sender, String receiver) {
        String countQuery = "SELECT  " + DbConstants.COLUMN_DATE + " FROM " + sender + "" + receiver + " ORDER BY " + DbConstants.COLUMN_DATE + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String str = "";
        try {
            cursor = db.rawQuery(countQuery, null);
            if (cursor != null && !cursor.isClosed()) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst())
                        str = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATE));
                }
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        if (cursor != null)
            cursor.close();
        return str;
    }

    /** get last time of chat */
    public String getLastTime(String sender, String receiver) {
        String countQuery = "SELECT  " + DbConstants.COLUMN_TIME + " FROM " + sender + "" + receiver + " ORDER BY " + DbConstants.COLUMN_TIME + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String str = "";
        try {
            cursor = db.rawQuery(countQuery, null);
            if (cursor != null && !cursor.isClosed()) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst())
                        str = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_TIME));
                }
            }
        } catch (IllegalStateException ex) {
            Logger.e(TAG + " : " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.e(TAG + " : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return str;
    }

    /** get the chat data for specific user */
    public List<ChatModel> getChatData(String sender_id, String receiver_id) {

        List<ChatModel> chatModels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selectQuery = DbConstants.SELECT_STAR_FROM + sender_id + "" + receiver_id + " where " + DbConstants.COLUMN_SENDER_ID + "=" + "'" + sender_id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        //looping through all rows and adding to list.
        if (cursor.moveToFirst()) {
            do {
                ChatModel myChatModel = new ChatModel();
                myChatModel.setSender_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_SENDER_ID)));
                myChatModel.setReceiver_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_RECEIVER_ID)));
                myChatModel.setMessage(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_MESSSAE)));
                myChatModel.setMsg_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_MESSAGE_ID)));
                myChatModel.setMine(cursor.getInt(cursor.getColumnIndex(DbConstants.COLUMN_IS_MINE)) > 0);
                myChatModel.setDate(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATE)));
                myChatModel.setTime(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_TIME)));
                myChatModel.setUnread(cursor.getInt(cursor.getColumnIndex(DbConstants.UNREAD)));
                chatModels.add(myChatModel);
                sqLiteDatabase.close();

            } while (cursor.moveToNext());
        }
        cursor.close();
        return chatModels;
    }


    /** get the undelieverchat data for specific user */
    public List<ChatModel> getUndelieverChatData(String table, String sender_id) {

        List<ChatModel> chatModels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selectQuery = DbConstants.SELECT_STAR_FROM + table + " where " + DbConstants.COLUMN_SENDER_ID + "=" + "'" + sender_id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        //looping through all rows and adding to list.
        if (cursor.moveToFirst()) {
            do {
                ChatModel myChatModel = new ChatModel();
                myChatModel.setSender_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_SENDER_ID)));
                myChatModel.setReceiver_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_RECEIVER_ID)));
                myChatModel.setMessage(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_MESSSAE)));
                myChatModel.setMsg_id(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_MESSAGE_ID)));
                myChatModel.setMine(cursor.getInt(cursor.getColumnIndex(DbConstants.COLUMN_IS_MINE)) > 0);
                myChatModel.setDate(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_DATE)));
                myChatModel.setTime(cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_TIME)));
                myChatModel.setUnread(cursor.getInt(cursor.getColumnIndex(DbConstants.UNREAD)));
                chatModels.add(myChatModel);
                sqLiteDatabase.close();

            } while (cursor.moveToNext());
        }
        cursor.close();
        return chatModels;
    }

    /** check id table is already exist. */
    public boolean isTableExists(String tableName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /** get unread message count */
    public int getUnreadCount(String sender_id, String receiver_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + DbConstants.UNREAD + ") FROM " + sender_id + "" + receiver_id;

        //  Logger.e(TAG + "  unread query " + query);

        Cursor cursor = null;

        //Add in the movetofirst etc here? see SO

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && !cursor.isClosed()) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int i = cursor.getInt(0);
                    //   Logger.e(TAG + "  unread count " + i);
                    return i;
                }
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }


        return 0;
    }

    /** get unread message count */
    public int getUnreadCount2(String ids) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + DbConstants.UNREAD + ") FROM " + ids;

        // Logger.e(TAG + "  unread query " + query);


        //Add in the movetofirst etc here? see SO
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && !cursor.isClosed()) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int i = cursor.getInt(0);
                    // Logger.e(TAG + "  unread count " + i);
                    return i;
                }
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        if (cursor != null)
            cursor.close();
        return 0;
    }

    /** update all row of a column of a table */
    public void updateColumn(String sender_id, String receiver_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConstants.UNREAD, 0);
        sqLiteDatabase.update(sender_id + "" + receiver_id, values, null, null);
        sqLiteDatabase.close();
    }

    /** get All Tables from db */
    public ArrayList<String> getTables() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                // Logger.e(TAG+ " Table Name : " +c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }
        return arrTblNames;
    }

    /** delete row data after deliever message */
    public void deleteUnDelieverMessage(String messageid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(DbConstants.TABLE_UNDELEIVERED, DbConstants.COLUMN_MESSAGE_ID + " = ?", new String[]{messageid});
        sqLiteDatabase.close();
    }

    /** drop table */
    public void dropTable() {
        context.deleteDatabase(DbConstants.DATABASE_NAME);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        onCreate(sqLiteDatabase);
    }
}
