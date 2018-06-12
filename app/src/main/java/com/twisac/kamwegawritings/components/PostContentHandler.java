package com.twisac.kamwegawritings.components;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twisac.kamwegawritings.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class PostContentHandler {
Context context;
String content;
LinearLayout linearLayout;

public PostContentHandler(Context context, String content, LinearLayout linearLayout) {
    this.context = context;
    this.content = content;
    this.linearLayout = linearLayout;

}

public void setContentToView() {


    List<String> p = new ArrayList<>();
    List<String> src = new ArrayList<>();
    List<String> li = new ArrayList<>();
    Document doc = Jsoup.parse(content);

    Elements elements = doc.getAllElements();

    for (Element element : elements) {
        Tag tag = element.tag();
        if (tag.getName().equalsIgnoreCase("img")) {
            String url = element.select("img").attr("src");
            final ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.getLayoutParams().height = 400;
                    imageView.setAdjustViewBounds(true);
                    imageView.requestLayout();


            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.kwback)// optional
                    .error(R.drawable.kwback)     // optional
                    //  .resize(600, 150)                        // optional
                    // .rotate(90)
                    // optional
                    .fit().centerInside().into(imageView);
            linearLayout.addView(imageView);
            src.add(url);
        }




    }
}

public static int getScreenWidth() {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
}
}