package com.deu.multisolvermko.problemler;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.deu.multisolvermko.R;
import com.deu.multisolvermko.problemler.gezginsaticimaps.GezginSaticiMapsEntry;
import com.deu.multisolvermko.problemler.gezginsaticimanuel.GezginSatici;

public class ProblemActivity extends AppCompatActivity {

    CardView manuelCard, otomatikCard;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        manuelCard = findViewById(R.id.manuelCard);
        otomatikCard = findViewById(R.id.otomatikCard);
        dialog = new Dialog(this);


        manuelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),GezginSatici.class);
                startActivity(intent);

            }
        });

        manuelCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openManuelDialog();
                return false;
            }
        });

        otomatikCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GezginSaticiMapsEntry.class);
                startActivity(intent);
            }
        });

        otomatikCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openOtomatikDialog();
                return false;
            }
        });


    }

    private void openManuelDialog(){
        dialog.setContentView(R.layout.manuel_card_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView  = dialog.findViewById(R.id.imageViewClose);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void openOtomatikDialog(){

        dialog.setContentView(R.layout.otomatik_card_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageView  = dialog.findViewById(R.id.imageViewClose);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}