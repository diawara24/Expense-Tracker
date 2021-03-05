package com.example.expensetracker.TabFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.expensetracker.DatabaseHandler;
import com.example.expensetracker.Expense;
import com.example.expensetracker.R;
import com.example.expensetracker.ViewExpense;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class List extends Fragment {

   public String TAG = "Listing Expenses";
    public static String EXPENSE_EXTRA = "Expense";
   private DatabaseHandler db;

    //int[] images = {R.drawable.pocketmoney, R.drawable.salary,R.drawable.refund};


    ListView lView;

    ListAdapter lAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public List() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = v.findViewById(R.id.androidList);

        Log.d(TAG, "Listing...");
        lView = (ListView) v.findViewById(R.id.androidList);
        db = new DatabaseHandler(getContext());
        final java.util.List<Expense> expenses = db.getAllRows();
        java.util.List<String> types = new ArrayList<>();
        java.util.List<String> dates = new ArrayList<>();
        java.util.List<String> images = new ArrayList<>();
        for (Expense e : expenses) {
            types.add(e.getType());
            dates.add(e.getDate());
            if(e.getType().equals("Salary")){
                images.add(String.valueOf(R.drawable.salary));
            }else if(e.getType().equals("Pocket Money")){
                images.add(String.valueOf(R.drawable.pocketmoney));
            }else if(e.getType().equals("Refund")){
                images.add(String.valueOf(R.drawable.refund));
            }else if(e.getType().equals("Food")){
                images.add(String.valueOf(R.drawable.food));
            }else if(e.getType().equals("Shopping")){
                images.add(String.valueOf(R.drawable.groceries));
            }else if(e.getType().equals("Entertainment")){
                images.add(String.valueOf(R.drawable.entertainment));
            }else{
                images.add(String.valueOf(R.drawable.ic_launcher_background));
            }

        }
        


        lAdapter = new ListAdapter(getActivity(), types, dates, images);

        lView.setAdapter(lAdapter);



        // Handle onClick event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getActivity(), ViewExpense.class);
                intent.putExtra(EXPENSE_EXTRA, expenses.get(arg2));
                startActivity(intent);
            }
        });

        return v;

    }

    public void listEvents() {

//        ArrayAdapter.notifyDataSetChanged();
//        listView.invalidateViews();
//        listView.refreshDrawableState();

    }
}