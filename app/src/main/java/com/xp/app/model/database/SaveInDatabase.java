package com.xp.app.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


import com.xp.app.model.helper.Constants;
import com.xp.app.pojo.ChannelList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




public class SaveInDatabase {
    private static final String TAG = SaveInDatabase.class.getSimpleName();

    private static SaveInDatabase mInstance = null;
    private Context context;

    private SQLiteDatabase db;

    public static SaveInDatabase getInstance(Context ctx) {

         if (mInstance == null) {
            mInstance = new SaveInDatabase(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private SaveInDatabase(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();    }



    public void addFlower(ChannelList vchannel) {

        try {
            Log.d(TAG, "Values Got " + vchannel.getClass().getSimpleName()+ vchannel.toString());

//        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
         //   values.put(Constants.DATABASE.CHANNEL_TITLE, vchannel.getChanneltitle());
            values.put(Constants.DATABASE.TITLE, vchannel.getTitle());
            values.put(Constants.DATABASE.DESCRIPTION, vchannel.getDescription());
            values.put(Constants.DATABASE.PUBLIHED_AT, vchannel.getDatetime());
       //     values.put(Constants.DATABASE.THUMBNAIL_URL,Utils.getPictureByteOfArray(Utils.StringToBitMap(vchannel.getThumbnailurl())));
            values.put(Constants.DATABASE.THUMBNAIL_URL,vchannel.getThumbnailurl());



            db.insert(Constants.DATABASE.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        db.close();
    }


    public List<ChannelList> getallData() {
//        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Constants.DATABASE.GET_VCHANNEL_QUERY, null);
        final List<ChannelList> vcList = new ArrayList<>();

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
//                do {
                    ChannelList vchannel = new ChannelList();
                    // vchannel.setFromDatabase(true);
            //    vchannel.setTitle(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.CHANNEL_TITLE)));
                    vchannel.setTitle(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.TITLE)));
                    vchannel.setDescription(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.DESCRIPTION)));
                    vchannel.setDatetime(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PUBLIHED_AT)));
                //    vchannel.setThumbnailurl(Utils.BitMapToString(Utils.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Constants.DATABASE.THUMBNAIL_URL)))));

                    vchannel.setThumbnailurl(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.THUMBNAIL_URL)));

                    vcList.add(vchannel);
                    // publishFlower(flower);

//                } while (cursor.moveToNext());
            }

        }
        return vcList;
    }

    public void deleteData() {
        db.execSQL(Constants.DATABASE.DELET_VCHANNEL_QUERY );
       // db.close();
    }
    public int numberOfRows() {
       // SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(Constants.DATABASE.GET_VCHANNEL_QUERY, null);
        int nrows =  cursor.getCount();
      //  int numRows = DatabaseUtils.queryNumEntries(Constants.DATABASE.TABLE_NAME);
        Log.d("nrowsd",""+nrows);
        return nrows;
    }


    /**
     * The Class OpenHelper.
     */
    private static class OpenHelper extends SQLiteOpenHelper {


        OpenHelper(Context context) {
            super(context, Environment.getExternalStorageDirectory()+ File.separator+Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
//			super(context, Environment.getExternalStorageDirectory()
//					+ File.separator + "/DataBase/" + File.separator
//					+ DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(Constants.DATABASE.CREATE_TABLE_QUERY);
                Log.d(TAG, "oncreate "+ Constants.DATABASE.TABLE_NAME);
            } catch (SQLException ex) {
                Log.d(TAG, ex.getMessage());
            }

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Constants.DATABASE.DROP_QUERY);
            this.onCreate(db);
        }
    }
}
