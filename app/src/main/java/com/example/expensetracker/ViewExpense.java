package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.TabFragments.List;

public class ViewExpense extends AppCompatActivity {
    private Expense expense;
    public static String TAG = "ViewExpense";
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        expense =  (Expense)  getIntent().getSerializableExtra(List.EXPENSE_EXTRA);
        TextView date = findViewById(R.id.date);
        date.setText(expense.getDate());

        TextView time = findViewById(R.id.time);
        time.setText(expense.getTime());

        TextView type = findViewById(R.id.type);
        type.setText(expense.getType());

        TextView montant = findViewById(R.id.montant);
        String montantS = "€" + expense.getMontant();
        montant.setText(montantS);

        TextView addresse = findViewById(R.id.address);
        addresse.setText(String.valueOf(expense.getAddress()));

        ImageView img = findViewById(R.id.background);
        if(expense.type.equals("Refund")){
            img.setImageResource(R.drawable.refund);
        }else if(expense.getType().equals("Salary")){
            img.setImageResource(R.drawable.salary);
        }
        else if(expense.type.equals("Pocket Money")){
            img.setImageResource(R.drawable.pocketmoney);
        }else if(expense.type.equals("Food")){
            img.setImageResource(R.drawable.food);
        }else if(expense.type.equals("Shopping")){
            img.setImageResource(R.drawable.groceries);
        }else if(expense.type.equals("Entertainment")){
            img.setImageResource(R.drawable.entertainment);
        }else{
            img.setImageResource(R.drawable.ic_launcher_background);
        }

//        Button deleteEx = findViewById(R.id.deleteExpense);

    }

    public void delete(View view){
        db = new DatabaseHandler(this);
        String id = String.valueOf(expense.getId());
        makeToast("Deleting Expense...");
        db.deleteItem(id);
      Intent intent = new Intent(this, List .class);
//       startActivity(intent);
        setResult(Activity.RESULT_OK,intent);
        finish();


    }
    private void makeToast(String msg)  {
        Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
    }
}