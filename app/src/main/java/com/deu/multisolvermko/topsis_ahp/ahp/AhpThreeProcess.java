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

public class AhpThreeProcess extends AppCompatActivity {

    EditText threebiriki,threebiruc,threeikiuc;
    TextView threebirbir,threeikibir,threeikiiki,threeucbir,threeuciki,threeucuc,threeaciklamatext, threenot;
    Button threetablotamamlabutton,threehesaplabutton;

    Double birbir;
    Double ikiiki;
    Double ucuc ;

    Double biriki;
    Double biruc;
    Double ikiuc;

    Double ikibir;
    Double ucbir;
    Double uciki;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_three_process);

        threebirbir = findViewById(R.id.threebirbir);
        threebiriki = findViewById(R.id.threebiriki);
        threebiruc = findViewById(R.id.threebiruc);
        threeikibir = findViewById(R.id.threeikibir);
        threeikiiki = findViewById(R.id.threeikiiki);
        threeikiuc = findViewById(R.id.threeikiuc);
        threeucbir = findViewById(R.id.threeucbir);
        threeuciki = findViewById(R.id.threeuciki);
        threeucuc = findViewById(R.id.threeucuc);
        threenot = findViewById(R.id.threenot);
        threeaciklamatext = findViewById(R.id.threeaciklamatext);
        threetablotamamlabutton = findViewById(R.id.threetablotamamlabutton);
        threehesaplabutton = findViewById(R.id.threehesaplabutton);
    }

    @SuppressLint("SetTextI18n")
    public void hesaplaButton(View view){

        if (a == 2) {
            if (!threebiriki.getText().toString().equals("") && !threebiruc.getText().toString().equals("") && !threeikiuc.getText().toString().equals("")) {

                Double birincisutun = birbir + ikibir + ucbir;
                Double ikincisutun = biriki + ikiiki + uciki ;
                Double ucuncusutun = biruc + ikiuc + ucuc ;

                Double abirbir = birbir / birincisutun;
                Double abiriki = biriki / ikincisutun;
                Double abiruc = biruc / ucuncusutun;

                Double aikibir = ikibir / birincisutun;
                Double aikiiki = ikiiki / ikincisutun;
                Double aikiuc = ikiuc / ucuncusutun;

                Double aucbir = ucbir / birincisutun;
                Double auciki = uciki / ikincisutun;
                Double aucuc = ucuc / ucuncusutun;

                Double abirincisatir = (abirbir + abiriki + abiruc ) / 3;
                Double aikincisatir = (aikibir + aikiiki + aikiuc ) / 3;
                Double aucuncusatir = (aucbir + auciki + aucuc ) / 3;

                Double kbirbir = birbir * abirincisatir;
                Double kikibir = ikibir * abirincisatir;
                Double kucbir = ucbir * abirincisatir;

                Double kbiriki = biriki * aikincisatir;
                Double kikiiki = ikiiki * aikincisatir;
                Double kuciki = uciki * aikincisatir;

                Double kbiruc = biruc * aucuncusatir;
                Double kikiuc = ikiuc * aucuncusatir;
                Double kucuc = ucuc * aucuncusatir;

                Double kbirincisatir = kbirbir + kbiriki + kbiruc ;
                Double kikincisatir = kikibir + kikiiki + kikiuc;
                Double kucuncusatir = kucbir + kuciki + kucuc ;

                Double tcbirincisatir = kbirincisatir / abirincisatir;
                Double tcikincisatir = kikincisatir / aikincisatir;
                Double tcucuncusatir = kucuncusatir / aucuncusatir;

                double tcortalama = (tcbirincisatir + tcikincisatir + tcucuncusatir) / 3;
                double consistency = (tcortalama - 3) / 2;
                double sonuc = consistency / 0.58;

                if (sonuc < 0.10) {
                    threenot.setTextColor(Color.parseColor("#32CD32"));
                    threenot.setText("Uygulanabilir");
                    threeaciklamatext.setText("Girdiğiniz değerlere göre bulunan sonuç: " + sonuc + "\n" + " 0.10'dan küçüktür. ");
                } else {
                    threenot.setTextColor(Color.parseColor("#CC0000"));
                    threenot.setText("Uygulanamaz");
                    threeaciklamatext.setText("Girdiğiniz değerlere göre bulunan sonuç: " + sonuc + "\n" + " 0.10'dan büyüktür. ");

                }
            }else{
                Toast.makeText(this, "Lütfen Boş Değer Bırakmayınız!!!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Değerleri Girdikten Sonra Tabloyu Tamamla Butonuna Tıklamanız Gerekmektedir.", Toast.LENGTH_SHORT).show();
        }
    }

    public void tablotamamla(View view) {

        if (!threebiriki.getText().toString().equals("") && !threebiruc.getText().toString().equals("") && !threeikiuc.getText().toString().equals("")) {
            a = 2;
            DecimalFormat df = new DecimalFormat("#.#####");

            birbir = Double.parseDouble(threebirbir.getText().toString());
            ikiiki = Double.parseDouble(threeikiiki.getText().toString());
            ucuc = Double.parseDouble(threeucuc.getText().toString());

            biriki = Double.parseDouble(threebiriki.getText().toString());
            biruc = Double.parseDouble(threebiruc.getText().toString());
            ikiuc = Double.parseDouble(threeikiuc.getText().toString());

            ikibir = 1 / biriki;
            ucbir = 1 / biruc;
            uciki = 1 / ikiuc;

            threeikibir.setText(df.format(ikibir));
            threeucbir.setText(df.format(ucbir));
            threeuciki.setText(df.format(uciki));
        }else{
            Toast.makeText(this, "Lütfen Boş Değer Bırakmayınız.", Toast.LENGTH_SHORT).show();
        }
    }
}