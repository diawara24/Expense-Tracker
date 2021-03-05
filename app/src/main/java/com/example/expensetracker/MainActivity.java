package com.example.expensetracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.expensetracker.TabFragments.Analysis;
import com.example.expensetracker.TabFragments.List;
import com.example.expensetracker.TabFragments.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";
    SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPrefs = this.getSharedPreferences("prefID", Context.MODE_PRIVATE);
        String name = myPrefs.getString("nameKey", "name");
        String amount = myPrefs.getString("amountkey", "amount");
        if (name != null && amount != null )
        {
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }


    }


    public void Login(View view){

        EditText nameText = findViewById(R.id.name);
        EditText amountText =  findViewById(R.id.expenseAmount);
        String name = nameText.getText().toString();
        String amount = amountText.getText().toString();
        Intent intent = new Intent(this, HomeActivity.class);

        if(name.trim().equals("")){
            nameText.setError("No name was Provided");
            amountText.setHint("Please Enter your Name");
        }else if(amount.trim().equals("")){
            amountText.setError("No email was provided");
            amountText.setHint("Please Enter your Email");
        }else {

            myPrefs.edit().remove("nameKey").commit();
            myPrefs.edit().remove("emailKey").commit();
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString("nameKey", nameText.getText().toString());
            editor.putString("amountkey", amountText.getText().toString());

            editor.apply();
            startActivity(intent);
        }

    }
}
