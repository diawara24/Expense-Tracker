package com.example.expensetracker.TabFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.MyNotification;
import com.example.expensetracker.R;
import com.example.expensetracker.WeeklyNotification;

public class Profile extends Fragment implements View.OnClickListener {
    SharedPreferences myPrefs;
    EditText name;
    EditText amount;
    TextView daily;
    TextView weakly;
    Button btnSave;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        name = v.findViewById(R.id.username);
        amount = v.findViewById(R.id.total);
        daily = v.findViewById(R.id.dailyReminder);
        weakly = v.findViewById(R.id.weeklyReminder);
        myPrefs = getActivity().getSharedPreferences("prefID", Context.MODE_PRIVATE);
        String namePref = myPrefs.getString("nameKey", "name");
        String amountPref = myPrefs.getString("amountkey", "amount");

        name.setText(namePref);
        amount.setText(amountPref);
        btnSave = v.findViewById(R.id.saveInfo);
        btnSave.setOnClickListener(this);
        daily.setOnClickListener(this);
        weakly.setOnClickListener(this);

        return  v;
    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.saveInfo:
                myPrefs.edit().remove("nameKey").commit();
                myPrefs.edit().remove("amountkey").commit();
                SharedPreferences.Editor editor = myPrefs.edit();;
                editor.putString("nameKey", name.getText().toString());
                editor.putString("amountkey",amount.getText().toString());
                editor.apply();
                Toast.makeText(getActivity().getBaseContext(), "Information SAVE", Toast.LENGTH_LONG).show();
                break;
            case R.id.dailyReminder:
                intent = new Intent(getActivity(), MyNotification.class);
                startActivity(intent);
                break;
            case R.id.weeklyReminder:
                 intent = new Intent(getActivity(), WeeklyNotification.class);
                 startActivity(intent);
                break;
        }



    }

}