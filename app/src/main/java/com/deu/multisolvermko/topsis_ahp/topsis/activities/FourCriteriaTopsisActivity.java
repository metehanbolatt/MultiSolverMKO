package com.deu.multisolvermko.topsis_ahp.topsis.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.adapters.FourCriteriaTopsisAdapter;
import com.deu.multisolvermko.topsis_ahp.topsis.models.FourCriteriaTopsisModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourCriteriaTopsisActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button buttonF, buttonCalculate;
    EditText editText, editText1, editText2, editText3, editText4, editText5, editText6, editText7;
    TextView topsisTextF;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    SwitchCompat switch1, switch2, switch3, switch4;
    Integer result;
    ArrayList<BarEntry> alternates;
    List<FourCriteriaTopsisModel> alternateList;
    FourCriteriaTopsisAdapter fourCriteriaTopsisAdapter;
    List<Double> column1, column2, column3, column4, wColumn1, wColumn2, wColumn3, wColumn4, vPlus, vMinus, siPlus, siMinus, piValue;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_criteria_topsis);

        buttonF = findViewById(R.id.fourButtonT);
        buttonCalculate = findViewById(R.id.buttonTopsisCalculate);
        editText = findViewById(R.id.fourEditText1);
        editText1 = findViewById(R.id.fourEditText2);
        editText2 = findViewById(R.id.fourEditText3);
        editText3 = findViewById(R.id.fourEditText4);
        editText4 = findViewById(R.id.fourEditTextTopsis1);
        editText5 = findViewById(R.id.fourEditTextTopsis2);
        editText6 = findViewById(R.id.fourEditTextTopsis3);
        editText7 = findViewById(R.id.fourEditTextTopsis4);
        topsisTextF = findViewById(R.id.fourTextViewTopsis);
        recyclerView = findViewById(R.id.four_Recycler_View);
        switch1 = findViewById(R.id.fourSwitch1);
        switch2 = findViewById(R.id.fourSwitch2);
        switch3 = findViewById(R.id.fourSwitch3);
        switch4 = findViewById(R.id.fourSwitch4);
        alternateList = new ArrayList<>();

        fourRecyclerView();

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    switch1.setText("Max");
                }else{
                    switch1.setText("Min");
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    switch2.setText("Max");
                }else{
                    switch2.setText("Min");
                }
            }
        });

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    switch3.setText("Max");
                }else{
                    switch3.setText("Min");
                }
            }
        });

        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    switch4.setText("Max");
                }else{
                    switch4.setText("Min");
                }
            }
        });
    }

    public void calculateFourTopsis(View view){
        double one, two, three, four, result;
        double newResult = 0.0;

        if (!editText4.getText().toString().equals("") && !editText5.getText().toString().equals("") && !editText6.getText().toString().equals("") && !editText7.getText().toString().equals("")) {
            one = Double.parseDouble(editText4.getText().toString());
            two = Double.parseDouble(editText5.getText().toString());
            three = Double.parseDouble(editText6.getText().toString());
            four = Double.parseDouble(editText7.getText().toString());
            result = one + two + three + four;
            DecimalFormat df = new DecimalFormat("#.########");
            newResult = Double.parseDouble(df.format(result));
        }else{
            Toast.makeText(this, "Please enter the weights.", Toast.LENGTH_SHORT).show();
        }

        if (a==0){
            Toast.makeText(FourCriteriaTopsisActivity.this, "Please enter the correct value.", Toast.LENGTH_SHORT).show();
        }else if (newResult != 1.00){
            Toast.makeText(FourCriteriaTopsisActivity.this, "Please enter values so that the sum of the weights is 1.", Toast.LENGTH_SHORT).show();

        }else {
            normalDecisionMatrix();
            weightMultiply();
            minMax();
            siCalculate();
            piCalculate();
            resultCalculate();
            alertDialogFunction();

        }
    }

    private void fourRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fourCriteriaTopsisAdapter = new FourCriteriaTopsisAdapter(this,getList());
        recyclerView.setAdapter(fourCriteriaTopsisAdapter);

    }

    private List<FourCriteriaTopsisModel> getList(){

        alternateList = new ArrayList<>();

        buttonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 2;

                if (editText.getText().toString().equals("")|| editText1.getText().toString().equals("")|| editText2.getText().toString().equals("") || editText3.getText().toString().equals("")){
                    Toast.makeText(FourCriteriaTopsisActivity.this, "Please enter the correct value.", Toast.LENGTH_SHORT).show();
                }else {
                    alternateList.add(new FourCriteriaTopsisModel(editText.getText().toString(), editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString()));
                }
                editText.setText("");
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");

            }
        });

        return alternateList;
    }

    public void normalDecisionMatrix(){

        column1 = new ArrayList<>();
        column2 = new ArrayList<>();
        column3 = new ArrayList<>();
        column4 = new ArrayList<>();

        double totalColumn1 = 0;
        double totalColumn2 = 0;
        double totalColumn3 = 0;
        double totalColumn4 = 0;

        for (int i = 0; i < alternateList.size(); i++){
            totalColumn1 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate1()),2) + totalColumn1;
            totalColumn2 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate2()),2) + totalColumn2;
            totalColumn3 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate3()),2) + totalColumn3;
            totalColumn4 = Math.pow(Double.parseDouble(alternateList.get(i).getAlternate4()),2) + totalColumn4;

        }

        totalColumn1 = Math.pow(totalColumn1,0.5);
        totalColumn2 = Math.pow(totalColumn2,0.5);
        totalColumn3 = Math.pow(totalColumn3,0.5);
        totalColumn4 = Math.pow(totalColumn4,0.5);

        for (int i = 0; i < alternateList.size(); i++){
            column1.add(Double.parseDouble(alternateList.get(i).getAlternate1())/totalColumn1);
            column2.add(Double.parseDouble(alternateList.get(i).getAlternate2())/totalColumn2);
            column3.add(Double.parseDouble(alternateList.get(i).getAlternate3())/totalColumn3);
            column4.add(Double.parseDouble(alternateList.get(i).getAlternate4())/totalColumn4);
        }
    }

    public void weightMultiply(){
        wColumn1 = new ArrayList<>();
        wColumn2 = new ArrayList<>();
        wColumn3 = new ArrayList<>();
        wColumn4 = new ArrayList<>();

        for (int i = 0; i < alternateList.size(); i++){

            wColumn1.add(column1.get(i)*Double.parseDouble(editText4.getText().toString()));
            wColumn2.add(column2.get(i)*Double.parseDouble(editText5.getText().toString()));
            wColumn3.add(column3.get(i)*Double.parseDouble(editText6.getText().toString()));
            wColumn4.add(column4.get(i)*Double.parseDouble(editText7.getText().toString()));

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

        if (switch4.getText().toString().equals("Min")) {
            vPlus.add(Collections.min(wColumn4));
            vMinus.add(Collections.max(wColumn4));
        }else if(switch4.getText().toString().equals("Max")){
            vMinus.add(Collections.min(wColumn4));
            vPlus.add(Collections.max(wColumn4));
        }
    }

    public void siCalculate(){

        siPlus = new ArrayList<>();
        siMinus = new ArrayList<>();

        for (int i = 0; i < alternateList.size(); i++) {
            siPlus.add(Math.pow((Math.pow((wColumn1.get(i) - vPlus.get(0)), 2) + Math.pow((wColumn2.get(i) - vPlus.get(1)), 2) + Math.pow((wColumn3.get(i) - vPlus.get(2)), 2) + Math.pow((wColumn4.get(i) - vPlus.get(3)), 2)),0.5));
            siMinus.add(Math.pow((Math.pow((wColumn1.get(i) - vMinus.get(0)), 2) + Math.pow((wColumn2.get(i) - vMinus.get(1)), 2) + Math.pow((wColumn3.get(i) - vMinus.get(2)), 2) + Math.pow((wColumn4.get(i) - vMinus.get(3)),2)), 0.5));
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

        topsisTextF.setText("You should choose: " + result);

    }

    @SuppressLint("SetTextI18n")
    public void alertDialogFunction(){

        AlertDialog.Builder builder = new AlertDialog.Builder(FourCriteriaTopsisActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(FourCriteriaTopsisActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("You should choose: " + result);

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

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
