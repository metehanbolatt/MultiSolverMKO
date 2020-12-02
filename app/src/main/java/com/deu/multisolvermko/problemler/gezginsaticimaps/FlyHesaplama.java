package com.deu.multisolvermko.problemler.gezginsaticimaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.R;
import java.util.ArrayList;

public class FlyHesaplama extends AppCompatActivity {

    ArrayList<Double> latdouble,londouble;
    ArrayList<String> latstr, lonstr;
    ArrayList<Float> goToPy;
    Location loc1,loc2;
    Integer size ;
    int[] distances;

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

        distances = new int[size*size];

        for (int i=0; i < latstr.size();i++){
            latdouble.add(Double.parseDouble(latstr.get(i)));
            londouble.add(Double.parseDouble(lonstr.get(i)));
        }

        hesapla();
        arrayToDizi();

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("gezginfly");

        flyhesaplabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PyObject obj = pyobj.callAttr("main123", distances);
                textViewfly.setText(obj.toString());
            }
        });
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

    public void arrayToDizi(){
        for (int i =0; i<size*size;i++){
            distances[i]=Math.round(goToPy.get(i));
        }
    }
}