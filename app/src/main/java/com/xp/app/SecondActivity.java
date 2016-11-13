package com.xp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView thumbnail;
    private TextView title,rating,year;
    public static String vid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        thumbnail = (ImageView)findViewById(R.id.Dthumbnail);
        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.des);
        year = (TextView) findViewById(R.id.releaseYear);
        updateData();
        thumbnail.setOnClickListener(this);



    }

    private void updateData() {
        Bundle in = getIntent().getExtras();
        title.setText(in.getString("title"));
        rating.setText(in.getString("desc"));
        year.setText(in.getString("pubat"));
        String turl =in.getString("url");
        vid = in.getString("videoid");
        Toast.makeText(getApplicationContext(),"vidv seconActivity "+vid,Toast.LENGTH_LONG).show();
        Glide.with(getApplicationContext())
                .load(turl)
                .placeholder(R.mipmap.ic_launcher)
                .into(thumbnail);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SecondActivity.this, Youtube_Activity.class);
        intent.putExtra("videoid",vid);
        startActivity(intent);
    }
}
