package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.expensetracker.TabFragments.Analysis;
import com.example.expensetracker.TabFragments.List;
import com.example.expensetracker.TabFragments.Profile;
import com.example.expensetracker.TabFragments.Transaction;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements Transaction.TransactionListener{

    private static final String TAG = "HomeActivity";

    ViewPager viewPager;
    TabLayout tabLayout;

    Transaction transaction;
    Analysis analysis;
    List list;
    Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        transaction = new Transaction();
        analysis = new Analysis();
        list = new List();
        profile = new Profile();

        HomeActivity.MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(myPagerAdapter);

        // db = new DatabaseHandler(this);
        // db.getWritableDatabase();
        // db.clear();
    }

    @Override
    public void onAddButtonClicked() {
        analysis.display();
        analysis.addDataToBarChar();
        analysis.addDataToBarChar1();
        analysis.addDataToPieChar1();
        analysis.addDataToPieChar();
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        String fragmentNames[] = {"Track", "Stats", "List", "Profile"};


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position)    {
                case 0: return transaction;
                case 1: return analysis;
                case 2: return list;
                case 3: return profile;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames[position];
        }
    }


}