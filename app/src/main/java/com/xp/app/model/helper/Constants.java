package com.xp.app.model.helper;




public class Constants {


    public static final class YOUTUBEURL{
        public static final String DEVELOPER_KEY = "AIzaSyBRLPDbLkFnmUv13B-Hq9rmf0y7q8HOaVs";
    }

    public static final class DATABASE {

        public static final String DB_NAME = "YoutubeVChannel";
        public static final int DB_VERSION = 2;
        public static final String TABLE_NAME = "vchannel";

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        public static final String GET_VCHANNEL_QUERY = "SELECT * FROM " + TABLE_NAME;
    public static final String DELET_VCHANNEL_QUERY = "DELETE  FROM " + TABLE_NAME;

        public static final String PRODUCT_ID = "productId";

        public static final String DESCRIPTION = "description";
        public static final String TITLE = "Title";
       // public static final String CHANNEL_TITLE ="channelTitle";
        public static final String THUMBNAIL_URL = "thumbnail_url";
        public static final String PUBLIHED_AT = "datetime";
   /* String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_PH_NO + " TEXT" + ")";*/

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
                "(" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT not null," +
                DESCRIPTION + " TEXT not null," +
                PUBLIHED_AT + " TEXT not null," +
                THUMBNAIL_URL + " TEXT not null)";
    }


}
