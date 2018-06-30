package com.twisac.kamwegawritings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.twisac.kamwegawritings.adapter.CategoryAdapter;
import com.twisac.kamwegawritings.components.AlertPopup;
import com.twisac.kamwegawritings.components.Constant;
import com.twisac.kamwegawritings.egaview.RecyclerItemClickListener;
import com.twisac.kamwegawritings.jsonpojo.category.Category;
import com.twisac.kamwegawritings.retrofitdata.NewsApi;
import com.twisac.kamwegawritings.wizard.WizardActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class CategoriesActivity extends AppCompatActivity {
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";
    private static long SIZE_OF_CACHE =10 * 1024 * 1024;// 10MB
    TextView textView;
    ImageView imageLoading;
    Executor executor;
    OkHttpClient okHttpClient;
    RestAdapter restadapter;
    private ProgressDialog pDialog;
    CategoryAdapter adapter;
    //  HeaderAdapter adapter2;
    RecyclerView rv;
    FloatingActionButton fab;
    Cache cache;
    int ty=0;
    NewsApi newsApi;
    private RecyclerView.Adapter mAdapter;
    List<Category> categoryList;
    ConnectivityManager cn;
    NetworkInfo nf;
    boolean success = true;
    int anim = 1;
    Button btn_try;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        success = true;


        try{
            cache = new Cache(new File(getApplicationContext().getCacheDir(),"http"),SIZE_OF_CACHE);
        }catch (IOException e){

        }

        okHttpClient= new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);


        okHttpClient.networkInterceptors().add(mCacheInterceptor);

        restadapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                // .setExecutors(executor, executor)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        imageLoading =(ImageView) findViewById(R.id.imageLoading);
        textView = (TextView)findViewById(R.id.textLoading);
        btn_try = (Button) findViewById(R.id.btn_try);
        btn_try.setVisibility(View.GONE);
        callAsynchronousTask();
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        //   rv.setLayoutManager(new VegaLayoutManager());
        rv.setLayoutManager(new LinearLayoutManager(this));

        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_slide_from_bottom);
        rv.setLayoutAnimation(animationController);


        rv.setAdapter(adapter);

        listUpdate();



        cn = (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        btn_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listUpdate();
            }
        });

        mNavigationView =(NavigationView)findViewById(R.id.navigation);
        //  tabLayout = (TabLayout) findViewById(R.id.tabs);
        //      tabLayout.setupWithViewPager(viewPager);
        setupNavigationView();
        //  setupTabIcons();
        // setupCustomTabIcons();
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {

                    case R.id.navigation_info:
                        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent);
                        // return true;
                        break;
                    case R.id.navigation_pref:
                        Intent intent2 = new Intent(getApplicationContext(), WizardActivity.class);
                        startActivity(intent2);
                        // return true;
                        break;
                    case R.id.navigation_categories:
                        Intent intentCat = new Intent(getApplicationContext(), CategoriesActivity.class);
                        startActivity(intentCat);
                        // return true;
                        break;
                    case R.id.navigation_offline:
                        Intent intent3 = new Intent(getApplicationContext(), OfflineActivity.class);
                        startActivity(intent3);
                        // return true;
                        break;
                    case R.id.nav_share_app:
                        String share =  "Download the Kamwega Writings App" + "\n" + "sent Via Kamwega Writings App "+ "\n\n"+ new Constant().PLAYSTORE_LINK;
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download the kamwega app");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, share);
                        shareIntent.setType("text/plain");
                        startActivity(Intent.createChooser(shareIntent, "Share Via"));
                        break;
                    case R.id.navigation_home:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return true;
            }
        });

    }
    private void setupNavigationView(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (toolbar != null) {

            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }
    public void listUpdate() {
        if(adapter==null) {
            textView.setVisibility(View.VISIBLE);
            imageLoading.setVisibility(View.VISIBLE);
            //   imageLoading.setImageResource(R.drawable.ic_happy);
            textView.setText("Loading...");
            success = true;
            btn_try.setVisibility(View.GONE);
        }
        if(adapter!=null){
            // adapter.clear();
            // adapter.notifyDataSetChanged();
            textView.setVisibility(View.GONE);
            imageLoading.setVisibility(View.GONE);
        }


        newsApi = restadapter.create(NewsApi.class);
        newsApi.getCategories(new Callback<List<Category>>() {
            @Override
            public void success(List<Category> categories, Response response) {

                adapter = new CategoryAdapter(getApplicationContext(), categories);



                rv.setAdapter(adapter);
                if (adapter != null) {
                    // adapter.clear();
                    // adapter.notifyDataSetChanged();
                    textView.setVisibility(View.GONE);
                    imageLoading.setVisibility(View.GONE);
                }
                rv.scheduleLayoutAnimation();

                rv.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                            }
                        })
                );
            }

            @Override
            public void failure(RetrofitError error) {
                if (adapter == null) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Opps! Please Check Your Internet Connection");
                    imageLoading.setVisibility(View.VISIBLE);
                    //  imageLoading.setImageResource(R.drawable.ic_sad);
                    btn_try.setVisibility(View.VISIBLE);
                    success = false;
                }

            }
        });


    }
    private Interceptor mCacheInterceptor = new Interceptor() {
        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

            nf = cn.getActiveNetworkInfo();
            Request request = chain.request();

            //Add Cache control Only for GET methods
            if(request.method().equals("GET")){
                if(nf != null && nf.isConnected() == true){
                    //1 day
                    request.newBuilder()
                            .header("Cache-Control","only-if-cached")
                            .build();
                }else {
                    //4 week stale
                    request.newBuilder()
                            .header("Cache-Control", "public, max-stale=2419200")
                            .build();
                }
            }
            com.squareup.okhttp.Response response = chain.proceed(request);

            return response.newBuilder()
                    .header("Cache-Control", "public, max-stale=86400")//1 day
                    .build();
        }
    };

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(success){

                            if(anim==1){
                                imageLoading.setImageResource(R.drawable.ic_happy);
                                anim = 0;
                            }else{
                                imageLoading.setImageResource(R.drawable.ic_happy2);
                                anim = 1;
                            }

                        }else{

                            if(anim==1){
                                imageLoading.setImageResource(R.drawable.ic_sad);
                                anim = 0;
                            }else{
                                imageLoading.setImageResource(R.drawable.ic_angry2);
                                anim = 1;
                            }

                        }


                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 500); //execute in every 50000 ms
    }


}
