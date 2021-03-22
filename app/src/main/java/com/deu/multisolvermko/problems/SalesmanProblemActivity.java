package com.deu.multisolvermko.problems;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.problems.travellingsalesmanmaps.EntrySalesmanMapsActivity;
import com.deu.multisolvermko.problems.travellingsalesmanmanuel.TravellingSalesmanManuel;

public class SalesmanProblemActivity extends AppCompatActivity {

    CardView manuelCard, automaticCard;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salesman_problem);

        manuelCard = findViewById(R.id.manuelCard);
        automaticCard = findViewById(R.id.automaticCard);
        dialog = new Dialog(this);


        manuelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TravellingSalesmanManuel.class);
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

        automaticCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EntrySalesmanMapsActivity.class);
                startActivity(intent);
            }
        });

        automaticCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openAutomaticDialog();
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

    private void openAutomaticDialog(){

        dialog.setContentView(R.layout.automatic_card_dialog);
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