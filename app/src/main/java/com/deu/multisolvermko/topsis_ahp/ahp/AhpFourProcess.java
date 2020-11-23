package com.deu.multisolvermko.topsis_ahp.ahp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deu.multisolvermko.R;

import java.text.DecimalFormat;

public class AhpFourProcess extends AppCompatActivity {

    EditText fourbiriki,fourbiruc,fourbirdort,fourikiuc,fourikidort,fourucdort;
    TextView fourbirbir,fourikibir,fourikiiki,fourucbir,fouruciki,fourucuc,fourdortbir,fourdortiki,fourdortuc,fourdortdort,fournot,fouraciklamatext;
    Button fourtablotamamlabutton,fourhesaplabutton;

    Double birbir;
    Double ikiiki;
    Double ucuc ;
    Double dortdort ;

    Double biriki;
    Double biruc;
    Double birdort;
    Double ikiuc;
    Double ikidort;
    Double ucdort;

    Double ikibir;
    Double ucbir;
    Double uciki;
    Double dortbir;
    Double dortiki;
    Double dortuc;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_four_process);

        fourbirbir = findViewById(R.id.fourbirbir);
        fourbiriki = findViewById(R.id.fourbiriki);
        fourbiruc = findViewById(R.id.fourbiruc);
        fourbirdort = findViewById(R.id.fourbirdort);
        fourikibir = findViewById(R.id.fourikibir);
        fourikiiki = findViewById(R.id.fourikiiki);
        fourikiuc = findViewById(R.id.fourikiuc);
        fourikidort = findViewById(R.id.fourikidort);
        fourucbir = findViewById(R.id.fourucbir);
        fouruciki = findViewById(R.id.fouruciki);
        fourucuc = findViewById(R.id.fourucuc);
        fourucdort = findViewById(R.id.fourucdort);
        fourdortbir = findViewById(R.id.fourdortbir);
        fourdortiki = findViewById(R.id.fourdortiki);
        fourdortuc = findViewById(R.id.fourdortuc);
        fourdortdort = findViewById(R.id.fourdortdort);

        fournot = findViewById(R.id.fournot);
        fouraciklamatext = findViewById(R.id.fouraciklamatext);

        fourtablotamamlabutton = findViewById(R.id.fourtablotamamlabutton);
        fourhesaplabutton = findViewById(R.id.fourhesaplabutton);

    }


    public void tablotamamla(View view){

        if (!fourbiriki.getText().toString().equals("") && !fourbiruc.getText().toString().equals("") && !fourbirdort.getText().toString().equals("")
                && !fourikiuc.getText().toString().equals("") && !fourikidort.getText().toString().equals("") && !fourucdort.getText().toString().equals("")){
            a = 2;
            DecimalFormat df = new DecimalFormat("#.#####");

            birbir = Double.parseDouble(fourbirbir.getText().toString());
            ikiiki = Double.parseDouble(fourikiiki.getText().toString());
            ucuc = Double.parseDouble(fourucuc.getText().toString());
            dortdort = Double.parseDouble(fourdortdort.getText().toString());

            biriki = Double.parseDouble(fourbiriki.getText().toString());
            biruc = Double.parseDouble(fourbiruc.getText().toString());
            birdort = Double.parseDouble(fourbirdort.getText().toString());
            ikiuc = Double.parseDouble(fourikiuc.getText().toString());
            ikidort = Double.parseDouble(fourikidort.getText().toString());
            ucdort = Double.parseDouble(fourucdort.getText().toString());

            ikibir = 1 / biriki;
            ucbir = 1 / biruc;
            uciki = 1 / ikiuc;
            dortbir = 1 / birdort;
            dortiki = 1 / ikidort;
            dortuc = 1 /ucdort;

            fourikibir.setText(df.format(ikibir));
            fourucbir.setText(df.format(ucbir));
            fouruciki.setText(df.format(uciki));
            fourdortbir.setText(df.format(dortbir));
            fourdortiki.setText(df.format(dortiki));
            fourdortuc.setText(df.format(dortuc));

        }else{
            Toast.makeText(this, "Lütfen Boş Değer Bırakmayınız.", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("ResourceAsColor")
    public void hesaplaButton(View view){

        if (a == 2) {
            if (!fourbiriki.getText().toString().equals("") && !fourbiruc.getText().toString().equals("") && !fourbirdort.getText().toString().equals("")
                    && !fourikiuc.getText().toString().equals("") && !fourikidort.getText().toString().equals("") && !fourucdort.getText().toString().equals("")) {

                Double birincisutun = birbir + ikibir + ucbir + dortbir;
                Double ikincisutun = biriki + ikiiki + uciki + dortiki;
                Double ucuncusutun = biruc + ikiuc + ucuc + dortuc;
                Double dorduncusutun = birdort + ikidort + ucdort + dortdort;

                Double abirbir = birbir / birincisutun;
                Double abiriki = biriki / ikincisutun;
                Double abiruc = biruc / ucuncusutun;
                Double abirdort = birdort / dorduncusutun;

                Double aikibir = ikibir / birincisutun;
                Double aikiiki = ikiiki / ikincisutun;
                Double aikiuc = ikiuc / ucuncusutun;
                Double aikidort = ikidort / dorduncusutun;

                Double aucbir = ucbir / birincisutun;
                Double auciki = uciki / ikincisutun;
                Double aucuc = ucuc / ucuncusutun;
                Double aucdort = ucdort / dorduncusutun;

                Double adortbir = dortbir / birincisutun;
                Double adortiki = dortiki / ikincisutun;
                Double adortuc = dortuc / ucuncusutun;
                Double adortdort = dortdort / dorduncusutun;

                Double abirincisatir = (abirbir + abiriki + abiruc + abirdort) / 4;
                Double aikincisatir = (aikibir + aikiiki + aikiuc + aikidort) / 4;
                Double aucuncusatir = (aucbir + auciki + aucuc + aucdort) / 4;
                Double adorduncusatir = (adortbir + adortiki + adortuc + adortdort) / 4;

                Double kbirbir = birbir * abirincisatir;
                Double kikibir = ikibir * abirincisatir;
                Double kucbir = ucbir * abirincisatir;
                Double kdortbir = dortbir * abirincisatir;

                Double kbiriki = biriki * aikincisatir;
                Double kikiiki = ikiiki * aikincisatir;
                Double kuciki = uciki * aikincisatir;
                Double kdortiki = dortiki * aikincisatir;

                Double kbiruc = biruc * aucuncusatir;
                Double kikiuc = ikiuc * aucuncusatir;
                Double kucuc = ucuc * aucuncusatir;
                Double kdortuc = dortuc * aucuncusatir;

                Double kbirdort = birdort * adorduncusatir;
                Double kikidort = ikidort * adorduncusatir;
                Double kucdort = ucdort * adorduncusatir;
                Double kdortdort = dortdort * adorduncusatir;

                Double kbirincisatir = kbirbir + kbiriki + kbiruc + kbirdort;
                Double kikincisatir = kikibir + kikiiki + kikiuc + kikidort;
                Double kucuncusatir = kucbir + kuciki + kucuc + kucdort;
                Double kdorduncusatir = kdortbir + kdortiki + kdortuc + kdortdort;

                Double tcbirincisatir = kbirincisatir / abirincisatir;
                Double tcikincisatir = kikincisatir / aikincisatir;
                Double tcucuncusatir = kucuncusatir / aucuncusatir;
                Double tcdorduncusatir = kdorduncusatir / adorduncusatir;

                Double tcortalama = (tcbirincisatir + tcikincisatir + tcucuncusatir + tcdorduncusatir) / 4;

                Double consistency = (tcortalama - 4) / 3;

                Double sonuc = consistency / 0.90;

                if (sonuc < 0.10) {
                    fournot.setTextColor(Color.parseColor("#32CD32"));
                    fournot.setText("Uygulanabilir");
                    fouraciklamatext.setText("Girdiğiniz değerlere göre bulunan sonuç: " + sonuc + "\n" + " 0.10'dan küçüktür. ");
                } else {
                    fournot.setTextColor(Color.parseColor("#CC0000"));
                    fournot.setText("Uygulanamaz");
                    fouraciklamatext.setText("Girdiğiniz değerlere göre bulunan sonuç: " + sonuc + "\n" + " 0.10'dan büyüktür. ");

                }
            }else{
                Toast.makeText(this, "Lütfen Boş Değer Bırakmayınız!!!", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Değerleri Girdikten Sonra Tabloyu Tamamla Butonuna Tıklamanız Gerekmektedir.", Toast.LENGTH_SHORT).show();
        }


    }
}