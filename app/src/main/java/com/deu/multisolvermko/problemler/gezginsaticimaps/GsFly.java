package com.deu.multisolvermko.problemler.gezginsaticimaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deu.multisolvermko.R;

public class GsFly extends AppCompatActivity {

    EditText editTextFly;
    Button buttonFly, buttonRoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gs_fly);

        editTextFly = findViewById(R.id.editTextFly);
        buttonFly = findViewById(R.id.buttonfly);
        buttonRoad = findViewById(R.id.buttonroad);


        buttonFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFly.getText().toString().equals("")){
                    fazlaDeger();
                }else{
                    Integer city = Integer.parseInt(editTextFly.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("sehir",city);
                    startActivity(intent);

                }
            }
        });

        buttonRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFly.getText().toString().equals("")){
                    fazlaDeger();
                }else{
                    Integer city = Integer.parseInt(editTextFly.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), RoadActivity.class);
                    intent.putExtra("sehir",city);
                    startActivity(intent);

                }
            }
        });
    }


    public void fazlaDeger() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_gezgin_satici_maps_fly_bos_deger, (ViewGroup) findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}