package com.example.expensetracker.TabFragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetracker.DatabaseHandler;
import com.example.expensetracker.Expense;
import com.example.expensetracker.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Analysis extends Fragment {


    public TextView balanceNumberView;
    private DatabaseHandler db;
    private BarChart barChart;
    private BarChart barChart1;
    private PieChart piechart;
    private PieChart piechart1;

    public Analysis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_analysis, container, false);
        balanceNumberView = v.findViewById(R.id.balanceNumberView);

        db = new DatabaseHandler(getContext());
       // db.clear();
        display();

        barChart = (BarChart) v.findViewById(R.id.barChart);
        barChart1 = (BarChart) v.findViewById(R.id.barChart1);
        piechart = (PieChart) v.findViewById(R.id.piechar);
        piechart1 = (PieChart) v.findViewById(R.id.piechar1);

        // BarChar Expense
        addDataToBarChar();
        barChart.invalidate();

        // BarChar Income
        addDataToBarChar1();
        barChart1.invalidate();

        // PieChar Expense
        addDataToPieChar();
        piechart.invalidate();
        // PieChar Income
        addDataToPieChar1();
        piechart1.invalidate();


        return v;
    }


    public void display()   {
        String total= Double.toString(db.getTotal());
        db = new DatabaseHandler(getContext());
        ArrayList<Expense> expenses = db.getAllRows();
        if (expenses.size() > 0) {
            balanceNumberView.setText(String.valueOf(total));
        }
        else {
            makeToast("Your list of expenses is empty");
        }
    }
    public void addDataToBarChar(){

        db = new DatabaseHandler(getContext());

        final ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        final ArrayList<String> yData = new ArrayList<String>();

        for (int i = 0; i < db.queryYData().size(); i++) {
            BarEntry newBarEntry = new BarEntry(i,Math.abs(Float.parseFloat(db.queryYData().get(i))));
            yVals.add(newBarEntry);
        }

        final ArrayList<String> xVals = new ArrayList<String>();
        final ArrayList<String> xData = db.queryXData();

        for (int i = 0; i < db.queryXData().size() ; i++) {
            xVals.add(xData.get(i));
        }

        BarDataSet dataSet = new BarDataSet(yVals,"Total Expense per Day");
        ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(dataSet);

        BarData data = new BarData(dataSets1);
        data.setValueTextColor(Color.WHITE);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawLabels(true);
        xAxis.isCenterAxisLabelsEnabled();
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(15f);
        xAxis.setEnabled(false);


        YAxis yAxis = barChart.getAxisRight();
        yAxis.setEnabled(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(15f);

        barChart.setMaxVisibleValueCount(5);
        barChart.setFitBars(true);
        barChart.animateY(200);

        Legend l = barChart.getLegend();
        l.setTextSize(15f);
        l.setTextColor(Color.BLACK);
        l.setForm(Legend.LegendForm.CIRCLE);


    }

    public void addDataToBarChar1(){

        db = new DatabaseHandler(getContext());
        final ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        final ArrayList<String> yData = new ArrayList<String>();

        for (int i = 0; i < db.queryYData1().size(); i++) {
            BarEntry newBarEntry = new BarEntry(i,Float.parseFloat(db.queryYData1().get(i)));
            yVals.add(newBarEntry);
        }

        final ArrayList<String> xVals = new ArrayList<String>();
        final ArrayList<String> xData = db.queryXData1();

        for (int i = 0; i < db.queryXData1().size() ; i++) {
            xVals.add(xData.get(i));
        }

        BarDataSet dataSet = new BarDataSet(yVals,"Total Income per Day");
        ArrayList<IBarDataSet> dataSets1 = new ArrayList<>();
        dataSets1.add(dataSet);
        dataSet.setValueTextSize(20f);
        dataSet.setValueTextColor(Color.BLACK);
        BarData data = new BarData(dataSets1);
        barChart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
        barChart1.setData(data);

        XAxis xAxis = barChart1.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawLabels(true);
        xAxis.isCenterAxisLabelsEnabled();
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(15f);
        xAxis.setEnabled(false);

        YAxis yAxis = barChart1.getAxisLeft();
        yAxis.setEnabled(true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(15f);


        barChart1.setMaxVisibleValueCount(5);
        barChart1.setFitBars(true);
        barChart1.animateY(200);

        Legend l = barChart1.getLegend();
        l.setTextSize(15f);
        l.setTextColor(Color.BLACK);
        l.setForm(Legend.LegendForm.CIRCLE);

    }

    public void addDataToPieChar(){

        db = new DatabaseHandler(getContext());



        final ArrayList<PieEntry> vectors = new ArrayList<PieEntry>();
        final ArrayList<Expense> expenses = db.pieDataExpense();



        for (Expense e: expenses){
            PieEntry pieEntry = new PieEntry((float) Math.abs(e.getMontant()),e.getType());
            vectors.add(pieEntry);
        }


        PieDataSet pieDataSet = new PieDataSet(vectors,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20f);


        PieData pieData = new  PieData(pieDataSet);
        piechart.setData(pieData);
        piechart.getDescription().setEnabled(false);
        piechart.setCenterText("Total Expense");
        piechart.animate();
        Legend l = piechart.getLegend();
        l.setTextSize(15f);
        l.setTextColor(Color.BLACK);
        l.setForm(Legend.LegendForm.CIRCLE);


    }

    public void addDataToPieChar1(){

        db = new DatabaseHandler(getContext());



        final ArrayList<PieEntry> vectors = new ArrayList<PieEntry>();
        final ArrayList<Expense> expenses = db.pieDataIncome();



        for (Expense e: expenses){
            PieEntry pieEntry1 = new PieEntry((float) Math.abs(e.getMontant()),e.getType());
            vectors.add(pieEntry1);
        }


        PieDataSet pieDataSet = new PieDataSet(vectors,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData1 = new  PieData(pieDataSet);
        piechart1.setData(pieData1);
        piechart1.getDescription().setEnabled(false);
        piechart1.setCenterText("Total Income");
        piechart1.animate();

        Legend l = piechart1.getLegend();
        l.setTextSize(15f);
        l.setTextColor(Color.BLACK);
        l.setForm(Legend.LegendForm.CIRCLE);


    }

    private void makeToast(String msg)  {
        Toast.makeText(this.getContext(), msg , Toast.LENGTH_LONG).show();
    }

}
