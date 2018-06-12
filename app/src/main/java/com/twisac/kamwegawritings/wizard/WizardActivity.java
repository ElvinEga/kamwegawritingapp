package com.twisac.kamwegawritings.wizard;

/**
 * Created by egafic on 2/15/2016.
 */

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.twisac.kamwegawritings.R;

import java.util.ArrayList;
import java.util.List;


public class WizardActivity extends AppCompatActivity {
    Button buttonNext1;
    FloatingActionButton fabNext;
    private ViewPager viewPager;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wizard);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        fabNext =(FloatingActionButton)findViewById(R.id.fabnext);

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                    fabNext.show();

                } else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(2);
                    fabNext.setImageResource(R.drawable.ic_action_check);
                }
                if (viewPager.getCurrentItem() == 2) {

                    fabNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Welcome ", Snackbar.LENGTH_SHORT);

                            snackbar.show();
                        }
                    });
                }

            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "ONE");
        adapter.addFragment(new Fragment2(), "TWO");
        adapter.addFragment(new Fragment3(), "THREE");
        viewPager.setAdapter(adapter);
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
