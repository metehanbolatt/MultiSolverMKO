package com.deu.multisolvermko.topsis_ahp.topsis.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.adapters.ThreeCriteriaTopsisAdapter;
import com.deu.multisolvermko.topsis_ahp.topsis.models.ThreeCriteriaTopsisModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeCriteriaTopsisActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    ThreeCriteriaTopsisAdapter adapter;
    Button buttonT, buttonCalculate;
    EditText editText1,editText2,editText3,editTextTopsis1,editTextTopsis2,editTextTopsis3;
    TextView textViewTopsis;
    List<ThreeCriteriaTopsisModel> alternateList;
    ArrayList<Double> column1, column2, column3, wColumn1, wColumn2, wColumn3, vPlus, vMinus, siPlus, siMinus, piValue;
    Integer result;
    ArrayList<BarEntry> alternates;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    SwitchCompat switch1,switch2,switch3;

    int a = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_criteria_topsis);

        buttonT = findViewById(R.id.buttonT);
        buttonCalculate = findViewById(R.id.buttonTopsisCalculate);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editTextTopsis1 = findViewById(R.id.editTextTopsis1);
        editTextTopsis2 = findViewById(R.id.editTextTopsis2);
        editTextTopsis3 = findViewById(R.id.editTextTopsis3);
        textViewTopsis = findViewById(R.id.textViewTopsis);
        recycler_view = findViewById(R.id.recycler_view);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        setRecyclerView();

        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                switch1.setText("Max");
            }else{
                switch1.setText("Min");
            }
        });

        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                switch2.setText("Max");
            }else{
                switch2.setText("Min");
            }
        });

        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                switch3.setText("Max");
            }else{
                switch3.setText("Min");
            }
        });
    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ThreeCriteriaTopsisAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<ThreeCriteriaTopsisModel> getList(){
        alternateList = new ArrayList<>();

        buttonT.setOnClickListener(v -> {
            a = 2;
            if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("") || editText3.getText().toString().equals("")){
                Toast.makeText(ThreeCriteriaTopsisActivity.this, "Please enter the correct value.", Toast.LENGTH_SHORT).show();
            }else {
                alternateList.add(new ThreeCriteriaTopsisModel(editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString()));
            }
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");

        });

        return alternateList;
    }

    public void buttonTopsisCalculate(View view){

        double one, two, three, result;
        double newResult = 0.0;

        if (!editTextTopsis1.getText().toString().equals("") && !editTextTopsis2.getText().toString().equals("") && !editTextTopsis3.getText().toString().equals("") ) {
            one = Double.parseDouble(editTextTopsis1.getText().toString());
            two = Double.parseDouble(editTextTopsis2.getText().toString());
            three = Double.parseDouble(editTextTopsis3.getText().toString());
            result = one + two + three;
            DecimalFormat df = new DecimalFormat("#.########");
            newResult = Double.parseDouble(df.format(result));
        }else{
            Toast.makeText(this, "Lütfen ağırlıkları giriniz..", Toast.LENGTH_SHORT).show();
        }

        if (a==0){
            Toast.makeText(ThreeCriteriaTopsisActivity.this, "Lütfen geçerli değer giriniz.", Toast.LENGTH_SHORT).show();
        }else if ( newResult != 1.0){
            Toast.makeText(ThreeCriteriaTopsisActivity.this, "Lütfen ağırlıkları, toplamları 1 olacak şekilde giriniz.", Toast.LENGTH_SHORT).show();
        }
        else {
            normalDecisionMatrix();
            wMultiply();
            minMax();
            siCalculate();
            piCalculate();
            resultCalculate();
            alertDialogFunction();
        }
    }

    @SuppressLint("SetTextI18n")
    public void normalDecisionMatrix(){

        column1 = new ArrayList<>();
        column2 = new ArrayList<>();
        column3 = new ArrayList<>();

        double totalColumn1 = 0;
        double totalColumn2 = 0;
        double totalColumn3 = 0;

        for (int i = 0; i < alternateList.size(); i++){
            totalColumn1 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate1()),2) + totalColumn1;
            totalColumn2 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate2()),2) + totalColumn2;
            totalColumn3 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate3()),2) + totalColumn3;

        }

        totalColumn1 = Math.pow(totalColumn1,0.5);
        totalColumn2 = Math.pow(totalColumn2,0.5);
        totalColumn3 = Math.pow(totalColumn3,0.5);

        for (int i = 0; i < alternateList.size(); i++){
            column1.add(Double.parseDouble(alternateList.get(i).getAlternate1())/totalColumn1);
            column2.add(Double.parseDouble(alternateList.get(i).getAlternate2())/totalColumn2);
            column3.add(Double.parseDouble(alternateList.get(i).getAlternate3())/totalColumn3);
        }
    }

    public void wMultiply(){
        wColumn1 = new ArrayList<>();
        wColumn2 = new ArrayList<>();
        wColumn3 = new ArrayList<>();

        for (int i = 0; i < alternateList.size(); i++){

            wColumn1.add(column1.get(i)*Double.parseDouble(editTextTopsis1.getText().toString()));
            wColumn2.add(column2.get(i)*Double.parseDouble(editTextTopsis2.getText().toString()));
            wColumn3.add(column3.get(i)*Double.parseDouble(editTextTopsis3.getText().toString()));

        }
    }

    public void minMax(){

        vPlus = new ArrayList<>();
        vMinus = new ArrayList<>();

        if (switch1.getText().toString().equals("Min")) {
            vPlus.add(Collections.min(wColumn1));
            vMinus.add(Collections.max(wColumn1));
        }else if(switch1.getText().toString().equals("Max")){
            vMinus.add(Collections.min(wColumn1));
            vPlus.add(Collections.max(wColumn1));
        }

        if (switch2.getText().toString().equals("Min")) {
            vPlus.add(Collections.min(wColumn2));
            vMinus.add(Collections.max(wColumn2));
        }else if(switch2.getText().toString().equals("Max")){
            vMinus.add(Collections.min(wColumn2));
            vPlus.add(Collections.max(wColumn2));
        }

        if (switch3.getText().toString().equals("Min")) {
            vPlus.add(Collections.min(wColumn3));
            vMinus.add(Collections.max(wColumn3));
        }else if(switch3.getText().toString().equals("Max")){
            vMinus.add(Collections.min(wColumn3));
            vPlus.add(Collections.max(wColumn3));
        }
    }


    public void siCalculate(){

        siPlus = new ArrayList<>();
        siMinus = new ArrayList<>();

        for (int i = 0; i < alternateList.size(); i++) {
            siPlus.add(Math.pow((Math.pow((wColumn1.get(i) - vPlus.get(0)), 2) + Math.pow((wColumn2.get(i) - vPlus.get(1)), 2) + Math.pow((wColumn3.get(i) - vPlus.get(2)), 2)),0.5));
            siMinus.add(Math.pow((Math.pow((wColumn1.get(i) - vMinus.get(0)), 2) + Math.pow((wColumn2.get(i) - vMinus.get(1)), 2) + Math.pow((wColumn3.get(i) - vMinus.get(2)), 2)), 0.5));
        }
    }
    public void piCalculate(){

        piValue = new ArrayList<>();

        for (int i = 0; i < alternateList.size(); i++) {
            piValue.add(siMinus.get(i) / (siMinus.get(i) + siPlus.get(i)));

        }
    }

    @SuppressLint("SetTextI18n")
    public void resultCalculate(){

        int idx = piValue.indexOf(Collections.max(piValue));
        result = idx + 1;

        textViewTopsis.setText("Seçmeniz gereken: " + result);

    }

    @SuppressLint("SetTextI18n")
    public void alertDialogFunction(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ThreeCriteriaTopsisActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ThreeCriteriaTopsisActivity.this).inflate(
                R.layout.layout_success_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Seçmeniz gereken: " + result);

        alternates = new ArrayList<>();
        ((BarChart) view.findViewById(R.id.barChart)).setData(null);
        ArrayList<BarEntry> visitors = new ArrayList<>();

        for (int i = 0; i < piValue.size(); i++){
            visitors.add(new BarEntry(i+1, Float.parseFloat(piValue.get(i).toString())));
        }

        BarDataSet barDataSet = new BarDataSet(visitors,"Alternates");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        ((BarChart) view.findViewById(R.id.barChart)).setFitBars(true);
        ((BarChart) view.findViewById(R.id.barChart)).setData(barData);
        ((BarChart) view.findViewById(R.id.barChart)).animateY(2000);

        ((Button) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(v -> alertDialog.dismiss());
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}