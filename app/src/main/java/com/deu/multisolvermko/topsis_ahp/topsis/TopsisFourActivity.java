package com.deu.multisolvermko.topsis_ahp.topsis;

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
import com.deu.multisolvermko.topsis_ahp.topsis.adapters.PaymentAdapterFour;
import com.deu.multisolvermko.topsis_ahp.topsis.models.PaymentModelFour;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopsisFourActivity extends AppCompatActivity {

    RecyclerView fourrecycler_view;
    Button fourbuttonT,fourbuttonHesapla;
    EditText foureditText1,foureditText2,foureditText3,foureditText4,foureditTextTopsis1,foureditTextTopsis2,foureditTextTopsis3,foureditTextTopsis4;
    TextView fourtextViewTopsis;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    SwitchCompat fourswitch1,fourswitch2,fourswitch3,fourswitch4;
    Integer sonuc;
    ArrayList<BarEntry> visitors;
    List<PaymentModelFour> fourpayment_list;
    PaymentAdapterFour fouradapter;
    List<Double> foursutun1,foursutun2 ,foursutun3 ,foursutun4,fouragirliklisutun1,fouragirliklisutun2,fouragirliklisutun3,fouragirliklisutun4,fourvarti,fourveksi,foursiarti,foursieksi,fourpidegeri;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsis_four);

        fourbuttonT = findViewById(R.id.fourbuttonT);
        fourbuttonHesapla = findViewById(R.id.buttonTopsisHesapla);
        foureditText1 = findViewById(R.id.foureditText1);
        foureditText2 = findViewById(R.id.foureditText2);
        foureditText3 = findViewById(R.id.foureditText3);
        foureditText4 = findViewById(R.id.foureditText4);
        foureditTextTopsis1 = findViewById(R.id.foureditTextTopsis1);
        foureditTextTopsis2 = findViewById(R.id.foureditTextTopsis2);
        foureditTextTopsis3 = findViewById(R.id.foureditTextTopsis3);
        foureditTextTopsis4 = findViewById(R.id.foureditTextTopsis4);
        fourtextViewTopsis = findViewById(R.id.fourtextViewTopsis);
        fourrecycler_view = findViewById(R.id.fourrecycler_view);
        fourswitch1 = findViewById(R.id.fourswitch1);
        fourswitch2 = findViewById(R.id.fourswitch2);
        fourswitch3 = findViewById(R.id.fourswitch3);
        fourswitch4 = findViewById(R.id.fourswitch4);
        fourpayment_list = new ArrayList<>();

        fourRecyclerView();

        fourswitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    fourswitch1.setText("Max");
                }else{
                    fourswitch1.setText("Min");
                }
            }
        });

        fourswitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    fourswitch2.setText("Max");
                }else{
                    fourswitch2.setText("Min");
                }
            }
        });

        fourswitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    fourswitch3.setText("Max");
                }else{
                    fourswitch3.setText("Min");
                }
            }
        });

        fourswitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    fourswitch4.setText("Max");
                }else{
                    fourswitch4.setText("Min");
                }
            }
        });
    }

    public void fourhesapla(View view){
        double bir,iki,uc,dort,sonuc;
        double newSonuc = 0.0;

        if (!foureditTextTopsis1.getText().toString().equals("") && !foureditTextTopsis2.getText().toString().equals("") && !foureditTextTopsis3.getText().toString().equals("") && !foureditTextTopsis4.getText().toString().equals("")) {
            bir = Double.parseDouble(foureditTextTopsis1.getText().toString());
            iki = Double.parseDouble(foureditTextTopsis2.getText().toString());
            uc = Double.parseDouble(foureditTextTopsis3.getText().toString());
            dort = Double.parseDouble(foureditTextTopsis4.getText().toString());
            sonuc = bir + iki + uc + dort;
            DecimalFormat df = new DecimalFormat("#.########");
            newSonuc = Double.parseDouble(df.format(sonuc));
        }else{
            Toast.makeText(this, "Lütfen Ağırlıkları Giriniz!!", Toast.LENGTH_SHORT).show();
        }

        if (a==0){
            Toast.makeText(TopsisFourActivity.this, "Lütfen geçerli değer giriniz", Toast.LENGTH_SHORT).show();
        }else if (newSonuc != 1.00){
            Toast.makeText(TopsisFourActivity.this, "Lütfen ağırlıkların toplamı 1 olacak şekilde değerler giriniz.", Toast.LENGTH_SHORT).show();

        }else {
            fournormalKararMatrisi();
            fouragirlikCarpma();
            fourminmaks();
            foursiHesaplama();
            fourpiHesaplama();
            foursonucHesaplama();
            alertDialogFunction();

        }
    }

    private void fourRecyclerView() {
        fourrecycler_view.setHasFixedSize(true);
        fourrecycler_view.setLayoutManager(new LinearLayoutManager(this));
        fouradapter= new PaymentAdapterFour(this,getList());
        fourrecycler_view.setAdapter(fouradapter);

    }

    private List<PaymentModelFour> getList(){

        fourpayment_list = new ArrayList<>();

        fourbuttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = 2;

                if (foureditText1.getText().toString().equals("")|| foureditText2.getText().toString().equals("")|| foureditText3.getText().toString().equals("") || foureditText4.getText().toString().equals("")){
                    Toast.makeText(TopsisFourActivity.this, "Lütfen geçerli değer giriniz", Toast.LENGTH_SHORT).show();
                    foureditText1.setText("");
                    foureditText2.setText("");
                    foureditText3.setText("");
                    foureditText4.setText("");
                }else {
                    fourpayment_list.add(new PaymentModelFour(foureditText1.getText().toString(),foureditText2.getText().toString(),foureditText3.getText().toString(),foureditText4.getText().toString()));
                    foureditText1.setText("");
                    foureditText2.setText("");
                    foureditText3.setText("");
                    foureditText4.setText("");
                }

            }
        });

        return fourpayment_list;
    }

    public void fournormalKararMatrisi(){

        foursutun1 = new ArrayList<>();
        foursutun2 = new ArrayList<>();
        foursutun3 = new ArrayList<>();
        foursutun4 = new ArrayList<>();

        double toplamsutun1 = 0;
        double toplamsutun2 = 0;
        double toplamsutun3 = 0;
        double toplamsutun4 = 0;

        for (int i = 0; i < fourpayment_list.size(); i++){
            toplamsutun1 = Math.pow(Double.parseDouble(fourpayment_list.get(i).getId()),2) + toplamsutun1;
            toplamsutun2 = Math.pow(Double.parseDouble(fourpayment_list.get(i).getName()),2) + toplamsutun2;
            toplamsutun3 = Math.pow(Double.parseDouble(fourpayment_list.get(i).getPayment()),2) + toplamsutun3;
            toplamsutun4 = Math.pow(Double.parseDouble(fourpayment_list.get(i).getPayment1()),2) + toplamsutun4;

        }

        toplamsutun1 = Math.pow(toplamsutun1,0.5);
        toplamsutun2 = Math.pow(toplamsutun2,0.5);
        toplamsutun3 = Math.pow(toplamsutun3,0.5);
        toplamsutun4 = Math.pow(toplamsutun4,0.5);

        for (int i = 0; i < fourpayment_list.size(); i++){
            foursutun1.add(Double.parseDouble(fourpayment_list.get(i).getId())/toplamsutun1);
            foursutun2.add(Double.parseDouble(fourpayment_list.get(i).getName())/toplamsutun2);
            foursutun3.add(Double.parseDouble(fourpayment_list.get(i).getPayment())/toplamsutun3);
            foursutun4.add(Double.parseDouble(fourpayment_list.get(i).getPayment1())/toplamsutun4);
        }
    }

    public void fouragirlikCarpma(){
        fouragirliklisutun1 = new ArrayList<>();
        fouragirliklisutun2 = new ArrayList<>();
        fouragirliklisutun3 = new ArrayList<>();
        fouragirliklisutun4 = new ArrayList<>();

        for (int i = 0; i < fourpayment_list.size(); i++){

            fouragirliklisutun1.add(foursutun1.get(i)*Double.parseDouble(foureditTextTopsis1.getText().toString()));
            fouragirliklisutun2.add(foursutun2.get(i)*Double.parseDouble(foureditTextTopsis2.getText().toString()));
            fouragirliklisutun3.add(foursutun3.get(i)*Double.parseDouble(foureditTextTopsis3.getText().toString()));
            fouragirliklisutun4.add(foursutun4.get(i)*Double.parseDouble(foureditTextTopsis4.getText().toString()));

        }
    }

    public void fourminmaks(){

        fourvarti = new ArrayList<>();
        fourveksi = new ArrayList<>();

        if (fourswitch1.getText().toString().equals("Min")) {
            fourvarti.add(Collections.min(fouragirliklisutun1));
            fourveksi.add(Collections.max(fouragirliklisutun1));
        }else if(fourswitch1.getText().toString().equals("Max")){
            fourveksi.add(Collections.min(fouragirliklisutun1));
            fourvarti.add(Collections.max(fouragirliklisutun1));
        }

        if (fourswitch2.getText().toString().equals("Min")) {
            fourvarti.add(Collections.min(fouragirliklisutun2));
            fourveksi.add(Collections.max(fouragirliklisutun2));
        }else if(fourswitch2.getText().toString().equals("Max")){
            fourveksi.add(Collections.min(fouragirliklisutun2));
            fourvarti.add(Collections.max(fouragirliklisutun2));
        }

        if (fourswitch3.getText().toString().equals("Min")) {
            fourvarti.add(Collections.min(fouragirliklisutun3));
            fourveksi.add(Collections.max(fouragirliklisutun3));
        }else if(fourswitch3.getText().toString().equals("Max")){
            fourveksi.add(Collections.min(fouragirliklisutun3));
            fourvarti.add(Collections.max(fouragirliklisutun3));
        }

        if (fourswitch4.getText().toString().equals("Min")) {
            fourvarti.add(Collections.min(fouragirliklisutun4));
            fourveksi.add(Collections.max(fouragirliklisutun4));
        }else if(fourswitch4.getText().toString().equals("Max")){
            fourveksi.add(Collections.min(fouragirliklisutun4));
            fourvarti.add(Collections.max(fouragirliklisutun4));
        }
    }

    public void foursiHesaplama(){

        foursiarti = new ArrayList<>();
        foursieksi = new ArrayList<>();

        for (int i = 0; i < fourpayment_list.size(); i++) {
            foursiarti.add(Math.pow((Math.pow((fouragirliklisutun1.get(i) - fourvarti.get(0)), 2) + Math.pow((fouragirliklisutun2.get(i) - fourvarti.get(1)), 2) + Math.pow((fouragirliklisutun3.get(i) - fourvarti.get(2)), 2) + Math.pow((fouragirliklisutun4.get(i) - fourvarti.get(3)), 2)),0.5));
            foursieksi.add(Math.pow((Math.pow((fouragirliklisutun1.get(i) - fourveksi.get(0)), 2) + Math.pow((fouragirliklisutun2.get(i) - fourveksi.get(1)), 2) + Math.pow((fouragirliklisutun3.get(i) - fourveksi.get(2)), 2) + Math.pow((fouragirliklisutun4.get(i) - fourveksi.get(3)),2)), 0.5));
        }
    }

    public void fourpiHesaplama(){

        fourpidegeri = new ArrayList<>();

        for (int i = 0; i < fourpayment_list.size(); i++) {
            fourpidegeri.add(foursieksi.get(i) / (foursieksi.get(i) + foursiarti.get(i)));
            System.out.println(fourpidegeri.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    public void foursonucHesaplama(){

        int idx = fourpidegeri.indexOf(Collections.max(fourpidegeri));
        sonuc = idx + 1;

        fourtextViewTopsis.setText("Seçmeniz gereken alternatif " + sonuc);

    }

    @SuppressLint("SetTextI18n")
    public void alertDialogFunction(){

        AlertDialog.Builder builder = new AlertDialog.Builder(TopsisFourActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(TopsisFourActivity.this).inflate(
                R.layout.layout_success_dialog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText("Seçmeniz gereken alternatif " + sonuc);

        visitors = new ArrayList<>();
        ((BarChart) view.findViewById(R.id.barChart)).setData(null);
        ArrayList<BarEntry> visitors = new ArrayList<>();

        for (int i = 0; i < fourpidegeri.size(); i++){
            visitors.add(new BarEntry(i+1, Float.parseFloat(fourpidegeri.get(i).toString())));
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
