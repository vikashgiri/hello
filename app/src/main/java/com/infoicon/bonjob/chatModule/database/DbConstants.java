package com.infoicon.bonjob.chatModule.database;

/**
 * Created by infoicon on 29/8/16.
 * all constants related to database are implements in this class.
 */
public class DbConstants {



    /**
     * Database name and version code
     */

    public static String DATABASE_NAME = "mydb";
    public static int VERSION_CODE = 2;

    /**
     * query related key
     */
    public static String CREATE_TABLE = "create table ";
    public static String SELECT_STAR_FROM = "select * from ";
    public static String DELETE_TABLE = "delete table ";
    public static String DROP_TABLE_IF_EXOSTS = "DROP TABLE IF EXISTS ";




    /**
     * Table Name
     */

    public static String TABLE_USERS = "table_users";
    public static String TABLE_UNDELEIVERED = "table_undeliverd";

    /**
     * Column name
     */
    public static String COLUMN_SENDER_ID = "sender_id";
    public static String COLUMN_RECEIVER_ID = "receiver_id";

    public static String COLUMN_MESSSAE = "messages";
    public static String COLUMN_MESSAGE_ID = "messages_id";
    public static String COLUMN_IS_MINE = "is_mine";
    public static String COLUMN_DATE = "dates";
    public static String COLUMN_TIME = "times";
    public static String UNREAD = "unread";



    /**
     * data type
     */
    public static String TEXT = " TEXT";
    public static String INTEGER = " INTEGER";
    public static String FLOAT = " FLOAT";
    public static String DOUBLE = " DOUBLE";
    public static String DATE = " DATE";
    public static String BLOB = " BLOB";
    public static String BOOLEAN = " BOOLEAN";


}



