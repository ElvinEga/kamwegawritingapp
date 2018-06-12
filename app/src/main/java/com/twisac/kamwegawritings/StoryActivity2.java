package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 11/1/2015.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.twisac.kamwegawritings.adapter.StoryAdapter;
import com.twisac.kamwegawritings.kamwegadb.AppDataStore;
import com.squareup.picasso.Picasso;


public class StoryActivity2 extends AppCompatActivity {

    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    private  String POST_FEATURED = "featured";
    private   String POST_CONTENT = "content";
    private   String POST_CATEGORY = "category";
    private  String POST_TITLE = "title";
    private  String POST_DATE = "date";
    private  String POST_EXCERPT = "excerpt";
    private  String POST_AUTHOR = "author";
    private String POST_LINK = "link";
ImageView toolbarImage;
    //  NavigationView mNavigationView;
    private RecyclerView rv;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabStar;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
ImageView backdrop=(ImageView)findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        fabShare =(FloatingActionButton)findViewById(R.id.fabshare);

        fabStar =(FloatingActionButton)findViewById(R.id.fabstar);


        Intent i =getIntent();
        POST_TITLE=  i.getStringExtra(POST_TITLE);
        POST_FEATURED = i.getStringExtra(POST_FEATURED);
        POST_CONTENT=i.getStringExtra(POST_CONTENT);
        POST_CATEGORY=i.getStringExtra(POST_CATEGORY);
        POST_DATE=  i.getStringExtra(POST_DATE);
        POST_EXCERPT=  i.getStringExtra(POST_EXCERPT);
        POST_LINK=  i.getStringExtra(POST_LINK);
        POST_AUTHOR=  i.getStringExtra(POST_AUTHOR);
        toolbar.setTitle(POST_TITLE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
fabShare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Spanned htmlAsSpanned = Html.fromHtml(POST_EXCERPT);
        String sExcerpt = htmlAsSpanned.toString();

        String sSend = POST_TITLE + "\n" + sExcerpt + "\n" + POST_LINK + "\n" + "sent Via KamwegaWritings App";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, POST_TITLE);
        shareIntent.putExtra(Intent.EXTRA_TEXT, sSend);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }
});
        fabStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDataStore.savePostData(POST_TITLE,POST_FEATURED,POST_EXCERPT,POST_CONTENT,POST_DATE,POST_AUTHOR,POST_CATEGORY);


            }
        });

           Picasso.with(this)
                .load(POST_FEATURED)
                .placeholder(R.drawable.kwback)// optional
                .error(R.drawable.kwback)     // optional
                        //  .resize(600, 150)                        // optional
                        // .rotate(90)
                        // optional
                .fit().centerCrop().into(backdrop);

       // collapsingToolbar.setContentScrimColor(R.color.colorPrimary);
        StoryAdapter adapter= new StoryAdapter(this,POST_CONTENT,POST_TITLE,POST_DATE,POST_CATEGORY);
        rv = (RecyclerView)findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(adapter);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    }
