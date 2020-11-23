package com.deu.multisolvermko.problemler.gsmaps;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.deu.multisolvermko.MapsActivity;
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
                    Toast.makeText(GsFly.this, "Lütfen şehir sayısı giriniz.", Toast.LENGTH_SHORT).show();
                }else{
                    Integer city = Integer.parseInt(editTextFly.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("sehir",city);
                    startActivity(intent);

                }
            }
        });
    }
}