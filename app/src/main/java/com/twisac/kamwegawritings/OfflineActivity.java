package com.twisac.kamwegawritings;

import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.twisac.kamwegawritings.components.Constant;
import com.twisac.kamwegawritings.wizard.WizardActivity;

import java.util.ArrayList;
import java.util.List;


public class OfflineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
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
        setContentView(R.layout.activity_offline);
      //  TypefaceHelper.typeface(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                  /*  case R.id.navigation_bible:
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

                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        return true;
                    case R.id.nav_share_app:
                        String share =  "Download the Kamwega Writings App" + "\n" + "sent Via Kamwega Writings App "+ "\n\n"+ new Constant().PLAYSTORE_LINK;
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Download the kamwega app");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, share);
                        shareIntent.setType("text/plain");
                        startActivity(Intent.createChooser(shareIntent, "Share Via"));
                        break;
                }
                return true;
            }
        });
    }
    private void setupCustomTabIcons() {

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
        adapter.addFragment(new TwoFragment(), "OFFLINE");
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
            toolbar.setTitle("Offline");
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
}