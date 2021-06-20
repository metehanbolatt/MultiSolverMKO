package com.deu.multisolvermko.problems.travellingsalesmanmanuel;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.animations.ProgressBarAnimation;
import com.deu.multisolvermko.R;
import static java.lang.Math.floor;

public class TravellingSalesmanManuel extends AppCompatActivity{

    EditText editText;
    Button button, buttonCalculate, buttonReset;
    TextView textView, textViewCalculate;
    ProgressBar progressBar,progressBar2;
    int a = 0;
    int size;
    int column;
    int row;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travelling_salesman_manuel);

        editText = findViewById(R.id.editTextGD);
        button = findViewById(R.id.buttonGD);
        buttonCalculate = findViewById(R.id.buttonGDone);
        buttonReset = findViewById(R.id.buttonGDReset);
        textView = findViewById(R.id.textViewGD);
        textViewCalculate = findViewById(R.id.textViewGDone);
        progressBar = findViewById(R.id.progress_bar);
        progressBar2 = findViewById(R.id.progress_Bar2);

        buttonCalculate.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("manualSalesman");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equals("")) {
                    showEnterData();
                } else {
                    if (a == 0) {
                        size = Integer.parseInt(editText.getText().toString());
                        PyObject obj = pyobj.callAttr("manual3", editText.getText().toString());
                        textView.setText(obj.toString());
                        editText.setHint("1. row 1. column");
                        a = a + 1;
                    } else if (a <= size * size) {

                        column =(a% size)+1;
                        row = (int) (floor(a/size)+1);
                        editText.setHint(row+" .row"+ " " + column + " .column");

                        PyObject obj = pyobj.callAttr("manual3", editText.getText().toString());
                        textView.setText(obj.toString());
                        if (a == size * size) {
                            buttonCalculate.setVisibility(View.VISIBLE);
                        }
                        a = a + 1;

                    } else {
                        showTooSize();
                    }
                }
            }


        });

        buttonReset.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(TravellingSalesmanManuel.this, R.style.AlertDialogTheme);
            View view = LayoutInflater.from(TravellingSalesmanManuel.this).inflate(
                    R.layout.layout_error_dialog, findViewById(R.id.layoutDialogContainer)
            );
            builder.setView(view);
            ((TextView) view.findViewById(R.id.textTitle)).setText("Emin misiniz?");
            ((TextView) view.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.error_title));
            ((Button) view.findViewById(R.id.buttonYes)).setText("Evet");
            ((Button) view.findViewById(R.id.buttonNo)).setText("Hayır");
            ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warningg);

            final AlertDialog alertDialog = builder.create();

            view.findViewById(R.id.buttonYes).setOnClickListener(v1 -> {
                a = 0;
                textView.setVisibility(View.VISIBLE);
                buttonCalculate.setVisibility(View.INVISIBLE);
                editText.setHint("Matris boyutunu giriniz..");
                PyObject obj = pyobj.callAttr("manual4");
                textView.setText(obj.toString());
                alertDialog.dismiss();
            });

            view.findViewById(R.id.buttonNo).setOnClickListener(v12 -> alertDialog.dismiss());

            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }

            alertDialog.show();
        });

        buttonCalculate.setOnClickListener(v -> {
            buttonCalculate.setClickable(false);
            buttonReset.setClickable(false);
            button.setClickable(false);

            textView.setText("0 %");
            progressBar.setMax(100);
            progressBar.setScaleY(3.f);
            progressAnimation();

            ExampleThread thread = new ExampleThread();
            thread.start();

            textViewCalculate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    buttonCalculate.setClickable(true);
                    buttonReset.setClickable(true);
                    button.setClickable(true);
                }
            });
        });
    }

    public void showTooSize() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_salesman_extra_value, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showEnterData() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_salesman_enter_value, findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void progressAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar,progressBar2,textView,0,100f);
        anim.setDuration(2000);
        progressBar.setAnimation(anim);
    }

    class ExampleThread extends Thread{

        @Override
        public void run() {
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("manualSalesman");
            PyObject obj = pyobj.callAttr("manual2");
            textViewCalculate.setText(obj.toString());

        }
    }
}