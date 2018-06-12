package com.twisac.kamwegawritings.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Elvin Ega on 10/23/2015.
 */
public class NewsAdapter extends ArrayAdapter<Posts> {
    private Context context;
    private List<Posts> postsList;
    public NewsAdapter(Context context, int resource, List<Posts> objects) {
        super(context, resource, objects);
        this.context = context;
        this.postsList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.homecard, parent, false);
        Posts posts = postsList.get(position);
        //header_image
        ImageView headerImage=(ImageView) view.findViewById(R.id.header_image);
      String imageurl=posts.getBetterFeaturedImage();
     //   String imageurl=posts.getFeaturedImage().getFeaturedImage().getAttachmentMeta().getFile();
      //  String imageurl="http://kamwegawritings.com/wp-content/uploads/2015/10/gabrielle-union-by-jeff-lipsky-for-glamour-february-2014-1.jpg";
        Picasso.with(view.getContext())
                .load(posts.getBetterFeaturedImage())
                .placeholder(R.drawable.ic_camera_grey)// optional
                .error(R.drawable.ic_camera_grey)     // optional
                      //  .resize(600, 150)                        // optional
                        // .rotate(90)
                        // optional
.fit().centerCrop().into(headerImage)
                ;
        //TextTopic
        TextView textTopic = (TextView) view.findViewById(R.id.textTopic);
        textTopic.setText(posts.getTitle().getRendered());

        //TextHeader
        TextView textHeader= (TextView) view.findViewById(R.id.textHeader);
        String excerpt=posts.getExcerpt().getRendered();
        Spanned htmlAsSpanned= Html.fromHtml(excerpt);
        textHeader.setText(htmlAsSpanned);

        //Textinfo
        TextView textInfo= (TextView) view.findViewById(R.id.textInfo);
        String nameAuthor="By: "+posts.getSlug();
       // nameAuthor.replaceAll("<","&lt;");
        String comments="22";
        textInfo.setText(nameAuthor+" | "+comments+" comments");

        //TextTime
        TextView textTime= (TextView) view.findViewById(R.id.textTime);
        textTime.setText(posts.getDate()+" "+imageurl);
        return view;
    }
}
