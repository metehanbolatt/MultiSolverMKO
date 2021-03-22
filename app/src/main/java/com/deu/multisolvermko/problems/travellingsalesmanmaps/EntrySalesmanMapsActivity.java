package com.deu.multisolvermko.problems.travellingsalesmanmaps;

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

public class EntrySalesmanMapsActivity extends AppCompatActivity {

    EditText editTextFly;
    Button buttonFly, buttonRoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_salesman_maps);

        editTextFly = findViewById(R.id.editTextFly);
        buttonFly = findViewById(R.id.buttonFly);
        buttonRoad = findViewById(R.id.buttonRoad);


        buttonFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFly.getText().toString().equals("")){
                    extraValue();
                }else{
                    Integer city = Integer.parseInt(editTextFly.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), FlySalesmanMapsActivity.class);
                    intent.putExtra("city",city);
                    startActivity(intent);

                }
            }
        });

        buttonRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextFly.getText().toString().equals("")){
                    extraValue();
                }else{
                    Integer city = Integer.parseInt(editTextFly.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), RoadSalesmanMapsActivity.class);
                    intent.putExtra("city",city);
                    startActivity(intent);

                }
            }
        });
    }

    public void extraValue() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_selasman_maps_fly_empty_value, (ViewGroup) findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}