package com.twisac.kamwegawritings.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.kamwegadb.Post;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Elvin Ega on 10/30/2015.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private List<Post> mPosts;
    Context context;
    public FavouriteAdapter(Context context, List<Post> objects){
        //super(context, resource, objects);
        this.context = context;
        this.mPosts= objects;

    }
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView headerImage;
        public TextView textTopic;
        public TextView textHeader;
        public  TextView textInfo;
        public TextView textTime;
        public TextView textCat;

        public ViewHolder(View v) {
            super(v);
            headerImage=(ImageView) v.findViewById(R.id.header_image);
             textTopic = (TextView) v.findViewById(R.id.textTopic);
             textHeader= (TextView) v.findViewById(R.id.textHeader);
             textInfo= (TextView) v.findViewById(R.id.textInfo);
             textTime= (TextView) v.findViewById(R.id.textTime);
textCat= (TextView) v.findViewById(R.id.textcat);

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
   Post posts=mPosts.get(position);

        //imge
        ImageView headerImage=holder.headerImage;
        String imageurl=posts.getFeature();
        Picasso.with(context)
                .load(posts.getFeature())
                .placeholder(R.drawable.kwback)// optional
                .error(R.drawable.kwback)     // optional
                        //  .resize(600, 150)                        // optional
                        // .rotate(90)
                        // optional
                .fit().centerCrop().into(headerImage);
        TextView txtTitle=holder.textTopic;
        txtTitle.setText(posts.getTitle());

        TextView txtExerpt=holder.textHeader;
        String excerpt=posts.getExcerpt();
        Spanned htmlAsSpanned= Html.fromHtml(excerpt);
        txtExerpt.setMaxLines(4);
        txtExerpt.setText(htmlAsSpanned);

        //Textinfo
        TextView textInfo=holder.textInfo;
        String nameAuthor="By: "+posts.getLink();
        // nameAuthor.replaceAll("<","&lt;");
        String comments="22";
        textInfo.setText(nameAuthor);

        //TextTime
        TextView textTime=holder.textTime;
        String mDate=posts.getDate();
        mDate=getDate(mDate);

        TextView txtCat=holder.textCat;
        txtCat.setText(posts.getCategory());
        textTime.setText(mDate);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.homecard, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);

        return viewHolder;
    }
    @Override
    public int getItemCount() {
        return mPosts.size();
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
