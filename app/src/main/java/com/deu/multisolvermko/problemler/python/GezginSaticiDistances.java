package com.deu.multisolvermko.problemler.python;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.R;

public class GezginSaticiDistances extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gezgin_satici_distances);

        editText = findViewById(R.id.editTextG);
        textView = findViewById(R.id.textViewG);
        button = findViewById(R.id.buttonG);

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("gezgin1");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Lütfen değer giriniz", Toast.LENGTH_LONG).show();
                }else {
                    PyObject obj = pyobj.callAttr("main1",editText.getText().toString());
                    textView.setText(obj.toString());
                }
            }
        });
    }
}