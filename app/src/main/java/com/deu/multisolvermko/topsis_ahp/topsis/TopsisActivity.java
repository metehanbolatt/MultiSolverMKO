package com.deu.multisolvermko.topsis_ahp.topsis;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.topsis_ahp.topsis.adapters.PaymentAdapter;
import com.deu.multisolvermko.topsis_ahp.topsis.models.PaymentModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopsisActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    PaymentAdapter adapter;
    Button buttonT,buttonHesapla;
    EditText editText1,editText2,editText3,editTextTopsis1,editTextTopsis2,editTextTopsis3;
    TextView textViewTopsis;
    List<PaymentModel> payment_list;
    ArrayList<Double> sutun1;
    ArrayList<Double> sutun2;
    ArrayList<Double> sutun3;
    ArrayList<Double> agirliklisutun1;
    ArrayList<Double> agirliklisutun2;
    ArrayList<Double> agirliklisutun3;
    ArrayList<Double> varti;
    ArrayList<Double> veksi;
    ArrayList<Double> siarti;
    ArrayList<Double> sieksi;
    ArrayList<Double> pidegeri;
    Integer sonuc;
    ArrayList<BarEntry> visitors;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    SwitchCompat switch1,switch2,switch3;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsis);

        buttonT = findViewById(R.id.buttonT);
        buttonHesapla = findViewById(R.id.buttonTopsisHesapla);
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
    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<PaymentModel> getList(){
        payment_list = new ArrayList<>();

        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 2;
                if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("") || editText3.getText().toString().equals("")){
                    Toast.makeText(TopsisActivity.this, "Lütfen geçerli değer giriniz", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                }else {
                    payment_list.add(new PaymentModel(editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString()));
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                }

            }
        });

        return payment_list;
    }

    public void buttonTopsisHesapla(View view){

        double bir,iki,uc,sonuc;
        double newSonuc = 0.0;

        if (!editTextTopsis1.getText().toString().equals("") && !editTextTopsis2.getText().toString().equals("") && !editTextTopsis3.getText().toString().equals("") ) {
            bir = Double.parseDouble(editTextTopsis1.getText().toString());
            iki = Double.parseDouble(editTextTopsis2.getText().toString());
            uc = Double.parseDouble(editTextTopsis3.getText().toString());
            sonuc = bir + iki + uc;
            DecimalFormat df = new DecimalFormat("#.########");
            newSonuc = Double.parseDouble(df.format(sonuc));
        }else{
            Toast.makeText(this, "Lütfen Ağırlıkları Giriniz!!", Toast.LENGTH_SHORT).show();
        }

        if (a==0){
            Toast.makeText(TopsisActivity.this, "Lütfen geçerli değer giriniz", Toast.LENGTH_SHORT).show();
        }else if ( newSonuc != 1.0){
            Toast.makeText(TopsisActivity.this, "Lütfen ağırlıkların toplamı 1 olacak şekilde değerler giriniz.", Toast.LENGTH_SHORT).show();
        }
        else {
            normalKararMatrisi();
            agirlikCarpma();
            minmaks();
            siHesaplama();
            piHesaplama();
            sonucHesaplama();
            alertDialogFunction();
        }
    }

    @SuppressLint("SetTextI18n")
    public void normalKararMatrisi(){

        sutun1 = new ArrayList<>();
        sutun2 = new ArrayList<>();
        sutun3 = new ArrayList<>();

        double toplamsutun1 = 0;
        double toplamsutun2 = 0;
        double toplamsutun3 = 0;

        for (int i = 0; i < payment_list.size(); i++){
            toplamsutun1 = Math.pow(Double.parseDouble(payment_list.get(i).getId()),2) + toplamsutun1;
            toplamsutun2 = Math.pow(Double.parseDouble(payment_list.get(i).getName()),2) + toplamsutun2;
            toplamsutun3 = Math.pow(Double.parseDouble(payment_list.get(i).getPayment()),2) + toplamsutun3;

        }

        toplamsutun1 = Math.pow(toplamsutun1,0.5);
        toplamsutun2 = Math.pow(toplamsutun2,0.5);
        toplamsutun3 = Math.pow(toplamsutun3,0.5);

        for (int i = 0; i < payment_list.size(); i++){
            sutun1.add(Double.parseDouble(payment_list.get(i).getId())/toplamsutun1);
            sutun2.add(Double.parseDouble(payment_list.get(i).getName())/toplamsutun2);
            sutun3.add(Double.parseDouble(payment_list.get(i).getPayment())/toplamsutun3);
        }
    }

    public void agirlikCarpma(){
        agirliklisutun1 = new ArrayList<>();
        agirliklisutun2 = new ArrayList<>();
        agirliklisutun3 = new ArrayList<>();

        for (int i = 0; i < payment_list.size(); i++){

            agirliklisutun1.add(sutun1.get(i)*Double.parseDouble(editTextTopsis1.getText().toString()));
            agirliklisutun2.add(sutun2.get(i)*Double.parseDouble(editTextTopsis2.getText().toString()));
            agirliklisutun3.add(sutun3.get(i)*Double.parseDouble(editTextTopsis3.getText().toString()));

        }
    }

    public void minmaks(){

        varti = new ArrayList<>();
        veksi = new ArrayList<>();

        if (switch1.getText().toString().equals("Min")) {
            varti.add(Collections.min(agirliklisutun1));
            veksi.add(Collections.max(agirliklisutun1));
        }else if(switch1.getText().toString().equals("Max")){
            veksi.add(Collections.min(agirliklisutun1));
            varti.add(Collections.max(agirliklisutun1));
        }

        if (switch2.getText().toString().equals("Min")) {
            varti.add(Collections.min(agirliklisutun2));
            veksi.add(Collections.max(agirliklisutun2));
        }else if(switch2.getText().toString().equals("Max")){
            veksi.add(Collections.min(agirliklisutun2));
            varti.add(Collections.max(agirliklisutun2));
        }

        if (switch3.getText().toString().equals("Min")) {
            varti.add(Collections.min(agirliklisutun3));
            veksi.add(Collections.max(agirliklisutun3));
        }else if(switch3.getText().toString().equals("Max")){
            veksi.add(Collections.min(agirliklisutun3));
            varti.add(Collections.max(agirliklisutun3));
        }
    }


    public void siHesaplama(){

        siarti = new ArrayList<>();
        sieksi = new ArrayList<>();

        for (int i = 0; i < payment_list.size(); i++) {
            siarti.add(Math.pow((Math.pow((agirliklisutun1.get(i) - varti.get(0)), 2) + Math.pow((agirliklisutun2.get(i) - varti.get(1)), 2) + Math.pow((agirliklisutun3.get(i) - varti.get(2)), 2)),0.5));
            sieksi.add(Math.pow((Math.pow((agirliklisutun1.get(i) - veksi.get(0)), 2) + Math.pow((agirliklisutun2.get(i) - veksi.get(1)), 2) + Math.pow((agirliklisutun3.get(i) - veksi.get(2)), 2)), 0.5));
        }
    }
    public void piHesaplama(){

        pidegeri = new ArrayList<>();

        for (int i = 0; i < payment_list.size(); i++) {
            pidegeri.add(sieksi.get(i) / (sieksi.get(i) + siarti.get(i)));
            System.out.println(pidegeri.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    public void sonucHesaplama(){

        int idx = pidegeri.indexOf(Collections.max(pidegeri));
        sonuc = idx + 1;

        textViewTopsis.setText("Seçmeniz gereken alternatif " + sonuc);

    }

    @SuppressLint("SetTextI18n")
    public void alertDialogFunction(){

        AlertDialog.Builder builder = new AlertDialog.Builder(TopsisActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(TopsisActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Seçmeniz gereken alternatif " + sonuc);

        visitors = new ArrayList<>();
        ((BarChart) view.findViewById(R.id.barChart)).setData(null);
        ArrayList<BarEntry> visitors = new ArrayList<>();

        for (int i = 0; i < pidegeri.size(); i++){
            visitors.add(new BarEntry(i+1, Float.parseFloat(pidegeri.get(i).toString())));
        }

        BarDataSet barDataSet = new BarDataSet(visitors,"Alternatifler");
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