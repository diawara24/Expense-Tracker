package com.example.expensetracker.TabFragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.expensetracker.DatabaseHandler;
import com.example.expensetracker.Expense;
import com.example.expensetracker.MapsActivity;
import com.example.expensetracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Transaction extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final int RESULT_OK = -1;
    private TransactionListener listener;
    public Spinner categorySpinner;
    public ArrayAdapter<String> arrayAdapter;
    Button incomeButton, expenseButton, addButton, adress;
    EditText amountEditText;
    private int transactionType;
    private DatabaseHandler db;
    private String TAG= "Listing data";
    int LAUNCH_MAPS_ACTIVITY = 1;
    public String location;

    TextView tv;
    public Transaction() {
        // Required empty public constructor
        transactionType = 0;
    }

    public interface TransactionListener {
        void onAddButtonClicked();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction, container, false);

        incomeButton = v.findViewById(R.id.incomeToggleButton);
        expenseButton = v.findViewById(R.id.expenseToggleButton);
        addButton = v.findViewById(R.id.addButton);
        adress = v.findViewById(R.id.adress);
        amountEditText = v.findViewById(R.id.amountEditText);
        tv = v.findViewById(R.id.readTextView);

        db = new DatabaseHandler(getContext());

        incomeButton.setOnClickListener(this);
        expenseButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        adress.setOnClickListener(this);

        categorySpinner = v.findViewById(R.id.spinner);

        String[] arraySpinner = new String[] {};

        arrayAdapter = new ArrayAdapter(this.getActivity(),R.layout.spinner_text, arraySpinner);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        categorySpinner.setAdapter(arrayAdapter);
        categorySpinner.setOnItemSelectedListener(this);



        return v;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TransactionListener) {
            listener = (TransactionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " needs to implement TransactionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    // when we return to activity set the text of the button
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_MAPS_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                location =data.getStringExtra("result");
                // makeToast(result);
                adress.setText(location);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                makeToast("no data return");
            }
        }

    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked

        String arraySpinner[];

        switch (v.getId()) {
            case R.id.incomeToggleButton:
                transactionType = 1;

                incomeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                incomeButton.setTextColor(getResources().getColor(R.color.white));

                expenseButton.setBackgroundColor(getResources().getColor(R.color.white));
//                expenseButton.setTextColor(getResources().getColor(R.color.failure));


                arraySpinner = new String[] {
                        "Select Category",
                        "Pocket Money",
                        "Salary",
                        "Refund",
                };

                arrayAdapter = new ArrayAdapter(this.getActivity(),R.layout.spinner_text, arraySpinner);
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                categorySpinner.setPrompt("Income");
                categorySpinner.setAdapter(arrayAdapter);
                break;
            case R.id.expenseToggleButton:

                transactionType = -1;

                expenseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                expenseButton.setTextColor(getResources().getColor(R.color.white));

                incomeButton.setBackgroundColor(getResources().getColor(R.color.white));
//                incomeButton.setTextColor(getResources().getColor(R.color.success));

                arraySpinner = new String[] {
                        "Select Category",
                        "Food",
                        "Entertainment",
                        "Shopping",
                };

                arrayAdapter = new ArrayAdapter(this.getActivity(),R.layout.spinner_text, arraySpinner);
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                categorySpinner.setAdapter(arrayAdapter);
                break;
            case R.id.addButton:

                if(categorySpinner.getCount() == 0 )    {
                    makeToast("Select Income/Expense!");
                    return;
                }

                final String category = categorySpinner.getSelectedItem().toString();
                if(category.equalsIgnoreCase("Select Category")) {
                    makeToast("Select Category!");
                    return;
                }
//                try {
                String amountText = amountEditText.getText().toString();
                if(amountText.equals(""))  {
                    makeToast("Enter amount!");
                    return;
                }
                if(!amountText.matches("[0-9\\.]+"))  {
                    makeToast("Amount in digits only!");
                    return;
                }
                final double amount = Double.parseDouble(amountText);
                makeToast(category + " - " + amount);

                Date date = new Date();
                final double amt = transactionType * amount;
                try {
                    makeToast(location);
                    db.addRow(new Expense(db.getCurrentId() + 1, getCurrentDate(date), getCurrentTime(date),
                            category, amt, location));
                }catch (Exception e){
                    e.printStackTrace();
                    makeToast("Could not add to table!");
                }
                printEvents();

                amountEditText.setText("");
                categorySpinner.setSelection(0,true);
                incomeButton.setSelected(false);
                expenseButton.setSelected(false);
                incomeButton.setBackgroundColor(getResources().getColor(R.color.white));
                expenseButton.setBackgroundColor(getResources().getColor(R.color.white));

                String[] def = new String[] {};

                arrayAdapter = new ArrayAdapter(this.getActivity(),android.R.layout.simple_spinner_item, def);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                categorySpinner.setAdapter(arrayAdapter);

                listener.onAddButtonClicked();
                makeToast("Transaction saved!");
                adress.setText("");
                adress.setHint("Tap to add Change add location");
                break;
            case R.id.adress:
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(intent, LAUNCH_MAPS_ACTIVITY);
                break;
        }
    }

    private void makeToast(String msg)  {
        Toast.makeText(this.getContext(), msg , Toast.LENGTH_LONG).show();
    }

    private String getCurrentDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
        return formatter.format(date);
    }

    private String getCurrentTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(date);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id) {
//        Toast.makeText(this.getContext(), "Selected: " + categorySpinner.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }

    private void printEvents() {
        Log.d(TAG, "Printing...");
        List<Expense> ex = db.getAllRows();
        for (Expense e : ex) {
            String log = "ID: " + e.getId() + ", date: " + e.getDate() + ", time:"+
                    e.getTime() + " Type: " + e.getType() + ", Montant: " + e.getMontant()  + ", Adress: " + e.getAddress() ;
            Log.d(TAG, log);
        }
        //  String total= Double.toString(db.getTotal());
        //   Log.d(TAG, total);

    }

}