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
import android.webkit.WebView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;

import com.twisac.kamwegawritings.components.PostContentHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Elvin Ega on 11/4/2015.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{
    private String POST_FEATURED = "featured";
    private String POST_CONTENT = "content";
    private String POST_CATEGORY = "category";
    private  String POST_TITLE = "title";
    private String POST_DATE = "date";
    Context context;

    @Override
    public int getItemCount() {
        return 1;
    }

    public StoryAdapter(Context context,String content,String title,String date,String category){
        //super(context, resource, objects);
        this.context = context;
        this.POST_TITLE=title;
        this.POST_DATE=date;
        this.POST_CONTENT=content;
        this.POST_CATEGORY=category;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView textTitle;

        public  TextView textContent;
        public TextView textDate;
        public WebView webView;
        public TextView textCat;
        public TextView txt_web;
        LinearLayout ll_content;
        public ViewHolder(View v) {
            super(v);

            textTitle = (TextView) v.findViewById(R.id.textTitle);
            webView=(WebView) v.findViewById(R.id.webView);

            textCat =(TextView) v.findViewById(R.id.textcat);
            textContent= (TextView) v.findViewById(R.id.textInfo);
            textDate =(TextView) v.findViewById(R.id.textDate);
            txt_web =(TextView) v.findViewById(R.id.txt_web);
            ll_content =(LinearLayout) v.findViewById(R.id.ll_content);


        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        TextView txtTitle=holder.textTitle;
        txtTitle.setText(POST_TITLE);


        //webView
        WebView webView=holder.webView;
        webView.loadData("<html><body>"+POST_CONTENT+"</body></html>",
                "text/html;charset=utf-8", "UTF-8");

        String webContent = "<html><body>"+POST_CONTENT+"</body></html>";

//        webView.loadData(getStyledFont(webContent),
//                "text/html;charset=utf-8", "UTF-8");
//        //TextDate
        TextView txtCat=holder.textCat;
        txtCat.setText(POST_CATEGORY);

        TextView textDate=holder.textDate;
        String mDate = getDate(POST_DATE);


        textDate.setText(mDate);
    //    holder.txt_web.setText();

        PostContentHandler postContentHandler = new PostContentHandler(context,POST_CONTENT,holder.ll_content);
        postContentHandler.setContentToView();

        Document document = Jsoup.parse(POST_CONTENT);
        document.select("img").remove();
        POST_CONTENT = document.toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txt_web.setText(trimTrailingWhitespace(Html.fromHtml(POST_CONTENT, Html.FROM_HTML_MODE_COMPACT)));
        }else {
           holder.txt_web.setText(trimTrailingWhitespace(Html.fromHtml(POST_CONTENT)));
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.storycard, parent, false);
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
