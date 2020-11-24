package com.deu.multisolvermko.problemler.python;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.deu.multisolvermko.animations.ProgressBarAnimation;
import com.deu.multisolvermko.R;
import static java.lang.Math.floor;

public class GezginSatici extends AppCompatActivity{

    EditText editText;
    Button button, buttonHesapla, buttonSifirla;
    TextView textView, textViewHesapla, text_progress;

    ProgressBar progressBar,progressBar2;
    int a = 0;
    int boyut;
    int sutun;
    int satir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gezgin_satici);

        editText = findViewById(R.id.editTextGD);
        button = findViewById(R.id.buttonGD);
        buttonHesapla = findViewById(R.id.buttonGDone);
        buttonSifirla = findViewById(R.id.buttonGDsıfırla);
        textView = findViewById(R.id.textViewGD);
        textViewHesapla = findViewById(R.id.textViewGDone);
        text_progress = findViewById(R.id.text_progress);
        progressBar = findViewById(R.id.progress_bar);
        progressBar2 = findViewById(R.id.progress_Bar2);

        buttonHesapla.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("gezgin2");



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equals("")) {
                    showDegerGiriniz();
                } else {
                    if (a == 0) {
                        boyut = Integer.parseInt(editText.getText().toString());
                        PyObject obj = pyobj.callAttr("main3", editText.getText().toString());
                        textView.setText(obj.toString());
                        editText.setHint("1. satır 1. sutun değerini giriniz.");
                        a = a + 1;
                    } else if (a <= boyut * boyut) {

                        sutun=(a%boyut)+1;
                        satir= (int) (floor(a/boyut)+1);
                        editText.setHint(satir + ". satır " + sutun + ". sütun değerini giriniz");

                        PyObject obj = pyobj.callAttr("main3", editText.getText().toString());
                        textView.setText(obj.toString());
                        if (a == boyut * boyut) {
                            buttonHesapla.setVisibility(View.VISIBLE);
                        }
                        a = a + 1;

                    } else {
                        showBoyutFazla();
                    }
                }
            }


        });

        buttonSifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GezginSatici.this, R.style.AlertDialogTheme);
                View view = LayoutInflater.from(GezginSatici.this).inflate(
                        R.layout.layout_error_dialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
                );
                builder.setView(view);
                ((TextView) view.findViewById(R.id.textTitle)).setText("Emin misiniz?");
                ((TextView) view.findViewById(R.id.textMessage)).setText(getResources().getString(R.string.error_title));
                ((Button) view.findViewById(R.id.buttonYes)).setText("Evet");
                ((Button) view.findViewById(R.id.buttonNo)).setText("Hayır");
                ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);

                final AlertDialog alertDialog = builder.create();

                view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a = 0;
                        textView.setVisibility(View.VISIBLE);
                        buttonHesapla.setVisibility(View.INVISIBLE);
                        PyObject obj = pyobj.callAttr("main4");
                        textView.setText(obj.toString());
                        alertDialog.dismiss();
                    }
                });

                view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });

                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
            }
        });

        buttonHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHesapla.setClickable(false);
                buttonSifirla.setClickable(false);
                button.setClickable(false);

                textView.setText("0 %");
                progressBar.setMax(100);
                progressBar.setScaleY(3.f);
                progressAnimation();

                ExampleThread thread = new ExampleThread();
                thread.start();

                textViewHesapla.addTextChangedListener(new TextWatcher() {
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
                        buttonHesapla.setClickable(true);
                        buttonSifirla.setClickable(true);
                        button.setClickable(true);
                    }
                });
            }
        });
    }

    public void showBoyutFazla() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_gezgin_satici_fazla_deger, (ViewGroup) findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showDegerGiriniz() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout_gezgin_satici_deger_gir, (ViewGroup) findViewById(R.id.toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void progressAnimation(){
        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar,progressBar2,textView,0,100f);
        anim.setDuration(4000);
        progressBar.setAnimation(anim);
    }

    class ExampleThread extends Thread{

        @Override
        public void run() {
            Python py = Python.getInstance();
            final PyObject pyobj = py.getModule("gezgin2");
            PyObject obj = pyobj.callAttr("main2");
            textViewHesapla.setText(obj.toString());

        }
    }

}
