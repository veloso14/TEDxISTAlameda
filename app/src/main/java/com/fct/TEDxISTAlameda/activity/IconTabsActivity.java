package com.fct.TEDxISTAlameda.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.fct.TEDxISTAlameda.Feedback;
import com.fct.TEDxISTAlameda.Privacy;
import com.fct.TEDxISTAlameda.QrcodeTab;
import com.fct.TEDxISTAlameda.R;
import com.fct.TEDxISTAlameda.Settings;
import com.fct.TEDxISTAlameda.fragments.FiveFragment;
import com.fct.TEDxISTAlameda.fragments.FourFragment;
import com.fct.TEDxISTAlameda.fragments.OneFragment;
import com.fct.TEDxISTAlameda.fragments.ThreeFragment;
import com.fct.TEDxISTAlameda.fragments.TwoFragment;
import com.linkedin.TEDxISTAlameda.LISessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class IconTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int last = 0;

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        //listeners do menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {



                if(item.getTitle().toString().equals("Send Feedback") ){


                    Intent myIntent = new Intent(IconTabsActivity.this, Feedback.class);

                    IconTabsActivity.this.startActivity(myIntent);
                }


                if(item.getTitle().toString().equals("Privacy policy") ){


                    Intent myIntent = new Intent(IconTabsActivity.this, Privacy.class);

                    IconTabsActivity.this.startActivity(myIntent);
                }



                if(item.getTitle().toString().equals("Settings") ){


                    Intent myIntent = new Intent(IconTabsActivity.this, Settings.class);

                    IconTabsActivity.this.startActivity(myIntent);
                }



                if(item.getTitle().toString().equals("Logout") ){


                    File f = getFileStreamPath("data");
                    f.delete();
                    LISessionManager.getInstance(getApplicationContext()).clearSession();
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                return true;
            }
        });


        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.defenicoes, popup.getMenu());

        popup.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        final ImageView qrcode = (ImageView) findViewById(R.id.qrcamera);
        final ImageView setting = (ImageView) findViewById(R.id.settings);


        //abrir o qr code
        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), QrcodeTab.class);
                startActivity(i);
            }
        });

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //abir o menu das defenicoes

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showPopup(v);
            }
        });



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_feed_black,
                R.drawable.ic_explore_black,
                R.drawable.ic_event_black,
                R.drawable.ic_chat_black,
                R.drawable.ic_profile_black

        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(0).setText(null);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setText(null);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setText(null);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(3).setText(null);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(4).setText(null);

        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.navigationBarColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.navigationBarColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.navigationBarColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(4).getIcon().setColorFilter(getResources().getColor(R.color.navigationBarColor), PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                viewPager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(viewPager.getAdapter().getPageTitle(tab.getPosition()));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.navigationBarColor), PorterDuff.Mode.SRC_IN);;
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Feed");
        adapter.addFrag(new TwoFragment(), "Explore");
        adapter.addFrag(new ThreeFragment(), "Event");
        adapter.addFrag(new FourFragment(), "Chat");
        adapter.addFrag(new FiveFragment(), "Profile");
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
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


    }
}
