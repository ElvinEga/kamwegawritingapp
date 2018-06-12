package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 11/1/2015.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.twisac.kamwegawritings.adapter.StoryAdapter;
import com.twisac.kamwegawritings.components.Constant;
import com.twisac.kamwegawritings.kamwegadb.AppDataStore;
import com.squareup.picasso.Picasso;


public class OfflineStoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    private  String POST_FEATURED = "featured";
    private   String POST_CONTENT = "content";
    private   String POST_CATEGORY = "category";
    private  String POST_TITLE = "title";
    private  String POST_DATE = "date";
    private  String POST_EXCERPT = "excerpt";
    private String POST_LINK = "link";
    private   String POST_ID = "id";
    private long postId;
ImageView toolbarImage;
    //  NavigationView mNavigationView;
    private RecyclerView rv;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabStar;
    private AlertDialog.Builder alertDialog2;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlinestory);
ImageView backdrop=(ImageView)findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        alertDialog2 = new AlertDialog.Builder(this);
        fabShare =(FloatingActionButton)findViewById(R.id.fabshare);

        fabStar =(FloatingActionButton)findViewById(R.id.fabstar);
long mId =0;

        Intent i =getIntent();
        POST_TITLE=  i.getStringExtra(POST_TITLE);
        POST_FEATURED = i.getStringExtra(POST_FEATURED);
        POST_CONTENT=i.getStringExtra(POST_CONTENT);
        POST_CATEGORY=i.getStringExtra(POST_CATEGORY);
        POST_DATE=  i.getStringExtra(POST_DATE);
        POST_EXCERPT=  i.getStringExtra(POST_EXCERPT);
        POST_LINK=  i.getStringExtra(POST_LINK);
        postId=  i.getLongExtra(POST_ID,mId);
        toolbar.setTitle(POST_TITLE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
fabShare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Spanned htmlAsSpanned = Html.fromHtml(POST_EXCERPT);
        String sExcerpt = htmlAsSpanned.toString();

        String sSend = POST_TITLE + "\n" + sExcerpt + "\n" + POST_LINK + "\n" + "sent Via KamwegaWritings App "+ "\n"+new Constant().PLAYSTORE_LINK;

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


                // Setting Dialog Title
                alertDialog2.setTitle("Delete");

                // Setting Dialog Message
                alertDialog2.setMessage("Are you sure you want to delete?");
                // On pressing Settings button

                alertDialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // Post post = Post.findById(Post.class, postId);
                     //   post.delete();
                        AppDataStore.deletePostData(postId);
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), OfflineActivity.class);
                        startActivity(intent3);
                        finish();
                    }
                });

                // on pressing cancel button
                alertDialog2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message

                alertDialog2.show();



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
