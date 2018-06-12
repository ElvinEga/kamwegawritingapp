package com.twisac.kamwegawritings.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.jsonpojo.comments.Comment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Elvin Ega on 11/4/2015.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> commentList = new ArrayList<>();
    Context context;

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public CommentAdapter(Context context, List<Comment> commentList){
        //super(context, resource, objects);
        this.context = context;
        this.commentList = commentList;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txt_from;
        private TextView txt_url;
        private TextView txt_content;
        private TextView txt_timestamp;
        private ImageView iv_avatar;


        public ViewHolder(View v) {
            super(v);

            txt_from = (TextView) v.findViewById(R.id.txt_from);
            txt_url= (TextView) v.findViewById(R.id.txt_url);
            txt_content= (TextView) v.findViewById(R.id.txt_content);
            txt_timestamp= (TextView) v.findViewById(R.id.txt_timestamp);
            iv_avatar= (ImageView) v.findViewById(R.id.iv_avatar);



        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.txt_from.setText(comment.getAuthorName());
        holder.txt_url.setText(comment.getAuthorUrl());
        holder.txt_timestamp.setText(getDate(comment.getDate()));
      //  holder.txt_content.setText(comment.getContent().getRendered());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txt_content.setText(trimTrailingWhitespace(Html.fromHtml(comment.getContent().getRendered(), Html.FROM_HTML_MODE_COMPACT)));
        }else {
            holder.txt_content.setText(trimTrailingWhitespace(Html.fromHtml(comment.getContent().getRendered())));
        }
      //  holder.txt_content.setText(Html.fromHtml(comment.getContent().getRendered()));
        Picasso.with(context)
                .load(comment.getAuthorAvatarUrls().get48())
                .placeholder(R.drawable.kwback)// optional
                .error(R.drawable.kwback)     // optional
                //  .resize(600, 150)                        // optional
                // .rotate(90)
                // optional
                .fit().centerCrop().into(holder.iv_avatar);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
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
    public static String getStyledFont(String html) {
        boolean addBodyStart = !html.toLowerCase().contains("<body>");
        boolean addBodyEnd = !html.toLowerCase().contains("</body");
        return "<style type=\"text/css\">@font-face {font-family: CustomFont;" +
                "src: url(\"file:///android_asset/fonts/Aller_Rg.ttf\")}" +
                "body {font-family: CustomFont;font-size: medium;text-align: justify;}</style>" +
                (addBodyStart ? "<body>" : "") + html + (addBodyEnd ? "</body>" : "");
    }
    public static CharSequence trimTrailingWhitespace(CharSequence source) {

        if(source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        while(--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i+1);
    }
}
