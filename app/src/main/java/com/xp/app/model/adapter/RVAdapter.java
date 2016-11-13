package com.xp.app.model.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xp.app.R;
import com.xp.app.SecondActivity;
import com.xp.app.Youtube_Activity;
import com.xp.app.pojo.ChannelList;


import java.util.ArrayList;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder>{
    Activity mcontext;
    List<ChannelList> mchannelList = new ArrayList<>();


    public RVAdapter(Activity mainActivity, List<ChannelList> rlist) {
       this. mcontext = mainActivity;
        this. mchannelList = rlist;

    }

    @Override
    public RVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rview = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null,false);
        return new MyViewHolder(rview);
    }

    @Override
    public void onBindViewHolder(final RVAdapter.MyViewHolder holder, final int position) {
        ChannelList currchannel = mchannelList.get(position);
        holder.channeltitle.setText(currchannel.getChanneltitle());
        holder.title.setText(currchannel.getTitle());
        holder.rating.setText(currchannel.getDescription());
        holder.genre.setText(currchannel.getVideoId());
        holder.year.setText(currchannel.getDatetime());

       // Picasso.with(mcontext).load(Utils.BitMapToString(currchannel.getThumbnailurl())).into(holder.thumbnail);
//        Log.d("picasso",Utils.BitMapToString(currchannel.getThumbnailurl()));

        Glide.with(mcontext)
                .load(currchannel.getThumbnailurl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, Youtube_Activity.class);
                intent.putExtra("url", mchannelList.get(position).getThumbnailurl());
                intent.putExtra("videoid",mchannelList.get(position).getVideoId());
                mcontext.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, SecondActivity.class);
                intent.putExtra("channeltitle", mchannelList.get(position).getChanneltitle());

                intent.putExtra("title", mchannelList.get(position).getTitle());
                intent.putExtra("desc", mchannelList.get(position).getDescription());
                intent.putExtra("pubat", mchannelList.get(position).getDatetime());
                intent.putExtra("videoid", mchannelList.get(position).getVideoId());
                intent.putExtra("url", mchannelList.get(position).getThumbnailurl());
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mchannelList.size();
    }

       public class MyViewHolder  extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView channeltitle,title,rating,year,genre;


        public MyViewHolder(View itemView) {
            super(itemView);


            thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
             title = (TextView) itemView.findViewById(R.id.title);
             channeltitle = (TextView) itemView.findViewById(R.id.channeltitle);
            rating = (TextView) itemView.findViewById(R.id.des);
             genre = (TextView) itemView.findViewById(R.id.genre);
            year = (TextView) itemView.findViewById(R.id.releaseYear);
        }


    }
    }

