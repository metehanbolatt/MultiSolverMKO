package com.deu.multisolvermko.problemler.gsmaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.deu.multisolvermko.R;
import java.util.ArrayList;


public class FlyHesaplama extends AppCompatActivity {

    ArrayList<Double> latdouble,londouble;
    ArrayList<String> latstr, lonstr;
    ArrayList<Float> goToPy;
    Location loc1,loc2;
    Integer size ;

    TextView textViewfly;
    Button flyhesaplabutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_hesaplama);

        flyhesaplabutton = findViewById(R.id.flyhesaplabutton);
        textViewfly = findViewById(R.id.textView_fly);

        latdouble = new ArrayList<>();
        londouble = new ArrayList<>();
        latstr = new ArrayList<>();
        lonstr = new ArrayList<>();
        goToPy = new ArrayList<>();

        Intent intent = getIntent();
        latstr = intent.getStringArrayListExtra("latstr");
        lonstr = intent.getStringArrayListExtra("lonstr");

        size = lonstr.size();

        for (int i=0; i < latstr.size();i++){

            latdouble.add(Double.parseDouble(latstr.get(i)));
            londouble.add(Double.parseDouble(lonstr.get(i)));

        }

        hesapla();

    }

    public void hesapla(){
        for (int c =1 ; c<=size; c++){
            for (int d =1; d<=size ; d++){

                loc1 = new Location("");
                loc1.setLatitude(latdouble.get(c-1));
                loc1.setLongitude(londouble.get(c-1));

                loc2 = new Location("");
                loc2.setLatitude(latdouble.get(d-1));
                loc2.setLongitude(londouble.get(d-1));

                float distance = loc1.distanceTo(loc2);

                goToPy.add(distance);

            }
        }
    }
}