package com.deu.multisolvermko.animations;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private final ProgressBar progressBar;
    private final ProgressBar progressBar2;
    private final TextView textView;
    private final float from;
    private final float to;

    public ProgressBarAnimation(ProgressBar progressBar,ProgressBar progressBar2, TextView textView, float from, float to){

        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
        this.progressBar2 = progressBar2;

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value =  from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int) value + " %");

        if (textView.getText().toString().equals("100 %")){
            textView.setText("Çözüm bulundu. Rota hesaplanıyor...");
            progressBar.setVisibility(View.INVISIBLE);
            progressBar2.setVisibility(View.VISIBLE);
        }
    }
}
