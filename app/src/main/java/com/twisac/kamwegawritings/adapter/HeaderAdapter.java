package com.twisac.kamwegawritings.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Elvin Ega on 10/30/2015.
 */
public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {
    private List<Posts> mPostss;
    Context context;
    public HeaderAdapter(Context context, List<Posts> objects){
        //super(context, resource, objects);
        this.context = context;
        this.mPostss= objects;

    }
    public void clear() {
        mPostss.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView headerImage;
        public TextView textTopic;

        public ViewHolder(View v) {
            super(v);
            headerImage=(ImageView) v.findViewById(R.id.header_image);
             textTopic = (TextView) v.findViewById(R.id.textTopic);



            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    headerImage=(ImageView) v.findViewById(R.id.header_image);
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
   Posts Postss=mPostss.get(position);
        //imge
        ImageView headerImage=holder.headerImage;
        String imageurl=Postss.getBetterFeaturedImage().getSourceUrl();
        Picasso.with(context)
                .load(Postss.getBetterFeaturedImage().getSourceUrl())
                .placeholder(R.drawable.kwback)// optional
                .error(R.drawable.kwback)     // optional
                        //  .resize(600, 150)                        // optional
                        // .rotate(90)
                        // optional
                .fit().centerCrop().into(headerImage);
        TextView txtTitle=holder.textTopic;
        txtTitle.setText(Postss.getTitle().getRendered());




    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.headercard, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public int getItemCount() {
        return mPostss.size();
    }
    private String getDate(String timeStamp) {
//2015-11-09T21:53:42
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            //sdf.parse("2015-11-09T21:53:42");
            Date netDate = sdf2.parse(timeStamp);
        //   return sdf.format(netDate);
            long timet=netDate.getTime();
            long now = System.currentTimeMillis();
          return  DateUtils.getRelativeTimeSpanString( timet, now, DateUtils.SECOND_IN_MILLIS).toString();
          //return  DateUtils.getRelativeDateTimeString(context, timet, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (Exception ex) {
            return "xxx";
        }

    }
}
