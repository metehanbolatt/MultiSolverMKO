package com.deu.multisolvermko;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progressBar, progressBar2;
    private TextView textView;
    private float from;
    private float to;



    public ProgressBarAnimation(Context context, ProgressBar progressBar,ProgressBar progressBar2, TextView textView, float from, float to){

        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
        this.progressBar2 = progressBar2;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value =  from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int) value + " %");

        if (textView.getText().toString().equals("100 %")){
            textView.setText("Sonuç bulundu. Rota Hesaplanıyor..");
            progressBar.setVisibility(View.INVISIBLE);
            progressBar2.setVisibility(View.VISIBLE);

        }


    }
}
