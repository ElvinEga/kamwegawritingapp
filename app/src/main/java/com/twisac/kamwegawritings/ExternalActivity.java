package com.twisac.kamwegawritings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.twisac.kamwegawritings.wizard.WizardActivity;

import java.util.ArrayList;
import java.util.List;


public class ExternalActivity extends AppCompatActivity {
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String sUrl;
    Bundle bundle;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
  //  NavigationView mNavigationView;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_tab_star
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  TypefaceHelper.typeface(this);
         bundle = new Bundle();
        bundle.putString("edttext", getpostUrl());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

sUrl= getpostUrl();
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        mNavigationView =(NavigationView)findViewById(R.id.navigation);
      //  tabLayout = (TabLayout) findViewById(R.id.tabs);
  //      tabLayout.setupWithViewPager(viewPager);
        setupNavigationView();
      //  setupTabIcons();
     // setupCustomTabIcons();
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
                 /*   case R.id.navigation_bible:
                        Intent intent4 = new Intent(getApplicationContext(), com.egaficsoftwares.kamwegawritings.bible.MainActivity.class);
                        startActivity(intent4);
                        // return true;
                        break;*/
                    case R.id.navigation_offline:
                        Intent intent3 = new Intent(getApplicationContext(), OfflineActivity.class);
                        startActivity(intent3);
                        // return true;
                        break;
                    case R.id.navigation_home:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return true;
            }
        });

    }
    public String getpostUrl(){
        Intent intentUrl = getIntent();

//intent.get
        Uri uri    =intentUrl.getData();
        String pointUrl = uri.toString();
        pointUrl= pointUrl.replaceAll(ENDPOINT,"");
        pointUrl=  pointUrl.replaceAll("/", "");
        return pointUrl;
    };
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LinkFragment(), "Link");
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
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}