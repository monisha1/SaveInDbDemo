package com.xp.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xp.app.model.adapter.RVAdapter;
import com.xp.app.model.database.SaveInDatabase;
import com.xp.app.model.helper.Utils;
import com.xp.app.pojo.ChannelList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ProgressDialog pDialog;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url ="https://www.googleapis.com/youtube/v3/search?part=id,snippet&maxResults=20&channelId=UCCq1xDJMBRF61kiOgU90_kw&key=AIzaSyBRLPDbLkFnmUv13B-Hq9rmf0y7q8HOaVs";
    List<ChannelList> rlist = new ArrayList<>();
    List<ChannelList> dblist = new ArrayList<>();
    RVAdapter madapter;
    ChannelList mchannel = null;
    public SaveInDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mdatabase = SaveInDatabase.getInstance(this);

//        //rlist =  mdatabase.getallData();
        madapter = new RVAdapter(this,rlist);

        Log.d("rlist","inside onCReate()");

        mRecyclerView.setAdapter(madapter);
       sendRequest();
    }



    private void sendRequest() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        if (getNetworkAvailability()) {
            getFeed();
        } else {
            dblist =  mdatabase.getallData();
            Toast.makeText(getApplicationContext(),"testing with NO Network" +dblist.size() + mdatabase.numberOfRows(),Toast.LENGTH_LONG).show();

            if(dblist.size() > 0) {
                madapter = new RVAdapter(this, dblist);
                mRecyclerView.setAdapter(madapter);
            }else {
                Toast.makeText(getApplicationContext(), "NO Data Available in DB" + mdatabase.numberOfRows(), Toast.LENGTH_LONG).show();

            }
        }
    }
    private void getFeed(){

// Creating volley request obj
        JsonObjectRequest mchannelReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        // hidePDialog();
                        pDialog.dismiss();
//                        ChannelList mchannel = null;
//                        RunInBackground rback;
                        try {
                            JSONObject response1 = new JSONObject(response.toString());
                            JSONArray list = response1.getJSONArray("items");
                            if (list.length() > 0) {
                                if (mdatabase.numberOfRows() > 0){
                                    mdatabase.deleteData(); }

                                for (int i = 0; i < list.length(); i++) {
                                    try {
                                        JSONObject obj = list.getJSONObject(i);

                                        mchannel = new ChannelList();
                                        JSONObject vjb = obj.getJSONObject("id");
                                        mchannel.setVideoId(vjb.getString("videoId"));
                                        mchannel.setChanneltitle(obj.getJSONObject("snippet").getString("channelTitle"));
                                        mchannel.setTitle(obj.getJSONObject("snippet").getString("title"));

                                        JSONObject snippetJSONObject = obj.getJSONObject("snippet");
                                        mchannel.setDescription(snippetJSONObject.getString("description"));
                                     //   mchannel.setDatetime(snippetJSONObject.getString("publishedAt"));
                                        String date =snippetJSONObject.getString("publishedAt");
                                        String convertedDate = dateconvert(date);
                                        mchannel.setDatetime(convertedDate);
                                        String img = snippetJSONObject.getJSONObject("thumbnails").getJSONObject("medium").getString("url");

                                        mchannel.setThumbnailurl(img);

                                        rlist.add(mchannel);
                                        Log.d("vid",mchannel.getVideoId());
                                     //   mdatabase.addFlower(mchannel);

                                         mdatabase.addFlower(mchannel);
                                        Log.d("rlist", "" + rlist);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                         madapter.notifyDataSetChanged();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        });

        RequestQueue requestueue = Volley.newRequestQueue(this);
        requestueue.add(mchannelReq);
    }

    private String dateconvert(String date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        String dateString = date;
        String result = null;

        try {
            Date date1 = df.parse(dateString);
            df.applyPattern("yyyy/mm/dd hh:mm:ss");
            result = df.format(date1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

  }
