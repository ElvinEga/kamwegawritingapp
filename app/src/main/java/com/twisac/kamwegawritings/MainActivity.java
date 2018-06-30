package com.twisac.kamwegawritings;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.twisac.kamwegawritings.adapter.HeaderAdapter;
import com.twisac.kamwegawritings.components.Constant;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.twisac.kamwegawritings.retrofitdata.NewsApi;
import com.twisac.kamwegawritings.wizard.WizardActivity;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    HeaderAdapter adapter2;
    RestAdapter restadapter2;
    OkHttpClient okHttpClient;
    RecyclerView rv2;
    List<Posts> postsList2 = new ArrayList<>();
    Cache cache;
    private static long SIZE_OF_CACHE =10 * 1024 * 1024;// 10MB
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";
    int ty=0;
    ConnectivityManager cn;
    NetworkInfo nf;
    AppBarLayout appBarLayout;
    //  NavigationView mNavigationView;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_tab_star
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Aller_Rg.ttf")
                // .setDefaultFontPath("fonts/Museo300-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        //  TypefaceHelper.typeface(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
         appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("KamwegaWritings");

                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    isShow = true;
                } else if(isShow) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.transparentColor));
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        appBarLayout.setExpanded(false);



        rv2 = (RecyclerView)findViewById(R.id.rv2);
        rv2.setHasFixedSize(true);
        rv2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        //rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        //   AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        //   alphaAdapter.setDuration(1000);

        rv2.setAdapter(adapter2);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_slide_from_right);
        rv2.setLayoutAnimation(animationController);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
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

        cn = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);


        try{
            cache = new Cache(new File(MainActivity.this.getCacheDir(),"http"),SIZE_OF_CACHE);
        }catch (IOException e){

        }

        okHttpClient= new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);


        okHttpClient.networkInterceptors().add(mCacheInterceptor);
        //  executor = Executors.newCachedThreadPool();


        restadapter2 = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                // .setExecutors(executor, executor)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        headerUpdate();
    }
    private void setupCustomTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("HOME");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("FAVOURITE");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_star, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

    }

    /* private void setupTabIcons() {
         tabLayout.getTabAt(0).setIcon(tabIcons[0]);
         tabLayout.getTabAt(1).setIcon(tabIcons[1]);
         tabLayout.getTabAt(2).setIcon(tabIcons[2]);
         tabLayout.getTabAt(3).setIcon(tabIcons[3]);
     }*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "HOME");
        viewPager.setAdapter(adapter);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent =new Intent(this,StoryActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    public void headerUpdate() {


        NewsApi newsApi = restadapter2.create(NewsApi.class);
        newsApi.getHeader(new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> Posts, Response response) {


                postsList2 = Posts;

                adapter2 = new HeaderAdapter(MainActivity.this, postsList2);

                adapter2.notifyDataSetChanged();


                rv2.setAdapter(adapter2);
                rv2.scheduleLayoutAnimation();
                appBarLayout.setExpanded(true);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        int items = adapter2.getItemCount() - 1;

                        if (ty < adapter2.getItemCount()) {
                            ty++;
                            rv2.smoothScrollToPosition(ty);

                            if (ty == items) {
                                ty = 0;
                            }
                        }
                    }
                };
                Timer timer = new Timer();
                long delay = 0;
                long intevalPeriod = 1 * 4000;
                timer.scheduleAtFixedRate(task, delay,intevalPeriod);







            }

            @Override
            public void failure(RetrofitError error) {

                appBarLayout.setExpanded(false);
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
    //This is for the font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}