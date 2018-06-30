package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 11/1/2015.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

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
import android.widget.LinearLayout;


import com.twisac.kamwegawritings.adapter.CommentAdapter;
import com.twisac.kamwegawritings.adapter.StoryAdapter;
import com.twisac.kamwegawritings.components.AlertPopup;
import com.twisac.kamwegawritings.components.Constant;
import com.twisac.kamwegawritings.jsonpojo.comment.Comment;
import com.twisac.kamwegawritings.kamwegadb.AppDataStore;
import com.twisac.kamwegawritings.kamwegadb.Post;
import com.twisac.kamwegawritings.retrofitdata.NewsApi;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class StoryActivity extends AppCompatActivity {

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
    private String POST_ID = "id";

    private int post_id;
ImageView toolbarImage;
    //  NavigationView mNavigationView;
    private RecyclerView rv,rv_comment;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabStar;
    private AlertDialog.Builder alertDialog2;
    Bitmap bitmap;
    NewsApi newsApi;
    RestAdapter restadapter;
    List<Comment> commentList = new ArrayList<>();
    CommentAdapter commentAdapter;
    Cache cache;
    OkHttpClient okHttpClient;
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";


    LinearLayout layoutBottomSheet,ll_text;
    ImageView iv_comment;
    BottomSheetBehavior sheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Aller_Rg.ttf")
                //.setDefaultFontPath("fonts/FiraSans-Regular.otf")
                //.setDefaultFontPath("fonts/Museo300-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

ImageView backdrop=(ImageView)findViewById(R.id.backdrop);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        fabShare =(FloatingActionButton)findViewById(R.id.fabshare);

        fabStar =(FloatingActionButton)findViewById(R.id.fabstar);
        alertDialog2 = new AlertDialog.Builder(this);

        Intent i =getIntent();
        POST_TITLE=  i.getStringExtra(POST_TITLE);
        POST_FEATURED = i.getStringExtra(POST_FEATURED);
        POST_CATEGORY=i.getStringExtra(POST_CATEGORY);
        POST_CONTENT=i.getStringExtra(POST_CONTENT);
        POST_DATE=  i.getStringExtra(POST_DATE);
        POST_EXCERPT=  i.getStringExtra(POST_EXCERPT);
        POST_LINK=  i.getStringExtra(POST_LINK);
        POST_AUTHOR=  i.getStringExtra(POST_AUTHOR);

        post_id=  i.getIntExtra(POST_ID,0);
        toolbar.setTitle(POST_TITLE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
fabShare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Spanned htmlAsSpanned = Html.fromHtml(POST_EXCERPT);
        String sExcerpt = htmlAsSpanned.toString();

        String sSend = POST_TITLE + "\n" + sExcerpt + "\n" + POST_LINK + "\n" + "sent Via KamwegaWritings App"+ "\n"+new Constant().PLAYSTORE_LINK;

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
                alertDialog2.setTitle("Save");

                // Setting Dialog Message
                alertDialog2.setMessage("Are you sure you want to save for offline reading?");
                // On pressing Settings button

                alertDialog2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        List<Post> post2= AppDataStore.getAllPosts(POST_TITLE);
                        if(post2.isEmpty()){
                            AppDataStore.savePostData(POST_TITLE,POST_FEATURED,POST_EXCERPT,POST_CONTENT,POST_DATE,POST_AUTHOR,POST_CATEGORY);
                            showWarning("Post Saved");
                        }else{
                            showWarning("Already Exists") ;
                        }
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

        layoutBottomSheet = (LinearLayout)findViewById(R.id.bottom_sheet);
        ll_text = (LinearLayout)findViewById(R.id.ll_text);
        iv_comment = (ImageView)findViewById(R.id.iv_comment);


        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                      //  btnBottomSheet.setText("Close Sheet");
                        iv_comment.setImageResource(R.drawable.ic_close);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                      //  btnBottomSheet.setText("Expand Sheet");
                        iv_comment.setImageResource(R.drawable.ic_comments);
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        ll_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });

        iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBottomSheet();
            }
        });




    // collapsingToolbar.setContentScrimColor(R.color.colorPrimary);
        StoryAdapter adapter= new StoryAdapter(this,POST_CONTENT,POST_TITLE,POST_DATE,POST_CATEGORY);
        rv = (RecyclerView)findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(adapter);


        restadapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                // .setExecutors(executor, executor)
               // .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();


        commentAdapter = new CommentAdapter(this,commentList);

        rv_comment = (RecyclerView)findViewById(R.id.rv_comment);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));

        rv_comment.setAdapter(commentAdapter);

        commentUpdate();


    }
private void showWarning(String note){
    Snackbar.make(getCurrentFocus(),
            note,
            Snackbar.LENGTH_SHORT).show();
};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void commentUpdate() {

        newsApi = restadapter.create(NewsApi.class);
        newsApi.getComments(post_id,new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                commentList = comments;
                commentAdapter = new CommentAdapter(getApplicationContext(),commentList);
                rv_comment.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {

                new AlertPopup().alertConnectError(getApplicationContext());

            }
        });
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            iv_comment.setImageResource(R.drawable.ic_close);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            iv_comment.setImageResource(R.drawable.ic_comments);
        }
    }

}
