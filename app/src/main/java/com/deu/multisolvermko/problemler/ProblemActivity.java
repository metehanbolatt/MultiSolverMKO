package com.deu.multisolvermko.problemler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.problemler.gezginsaticimaps.GsFly;
import com.deu.multisolvermko.problemler.python.GezginSatici;
import com.deu.multisolvermko.problemler.python.GezginSaticiDistances;

public class ProblemActivity extends AppCompatActivity {

    Button button1,button2,button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProblemActivity.this, GezginSaticiDistances.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProblemActivity.this, GezginSatici.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProblemActivity.this, GsFly.class);
                startActivity(intent);
            }
        });




    }
}