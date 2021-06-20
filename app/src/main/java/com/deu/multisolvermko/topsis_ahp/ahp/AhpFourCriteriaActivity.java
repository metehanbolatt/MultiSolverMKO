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

public class AhpFourCriteriaActivity extends AppCompatActivity {

    EditText oneTwo, oneThree, oneFour, twoThree, twoFour, threeFour;
    TextView oneOne, twoOne, twoTwo, threeOne, threeTwo, threeThree, fourOne, fourTwo, fourThree, fourFour, ahpText, explanationText;
    Button tableCompleteButton, calculateButton;
    Double one_one, two_two, three_three, four_four, one_two, one_three, one_four, two_three, two_four, three_four, two_one, three_one, three_two, four_one, four_two, four_three;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_four_criteria);

        oneOne = findViewById(R.id.oneOne);
        oneTwo = findViewById(R.id.oneTwo);
        oneThree = findViewById(R.id.oneThree);
        oneFour = findViewById(R.id.oneFour);
        twoOne = findViewById(R.id.twoOne);
        twoTwo = findViewById(R.id.twoTwo);
        twoThree = findViewById(R.id.twoThree);
        twoFour = findViewById(R.id.twoFour);
        threeOne = findViewById(R.id.threeOne);
        threeTwo = findViewById(R.id.threeTwo);
        threeThree = findViewById(R.id.threeThree);
        threeFour = findViewById(R.id.threeFour);
        fourOne = findViewById(R.id.fourOne);
        fourTwo = findViewById(R.id.fourTwo);
        fourThree = findViewById(R.id.fourThree);
        fourFour = findViewById(R.id.fourFour);

        ahpText = findViewById(R.id.ahpText);
        explanationText = findViewById(R.id.explanationText);

        tableCompleteButton = findViewById(R.id.tableCompleteButton);
        calculateButton = findViewById(R.id.calculationButton);

    }

    public void tableComplete(View view){

        if (!oneTwo.getText().toString().equals("") && !oneThree.getText().toString().equals("") && !oneFour.getText().toString().equals("")
                && !twoThree.getText().toString().equals("") && !twoFour.getText().toString().equals("") && !threeFour.getText().toString().equals("")){
            a = 2;
            DecimalFormat df = new DecimalFormat("#.#####");

            one_one = Double.parseDouble(oneOne.getText().toString());
            two_two = Double.parseDouble(twoTwo.getText().toString());
            three_three = Double.parseDouble(threeThree.getText().toString());
            four_four = Double.parseDouble(fourFour.getText().toString());

            one_two = Double.parseDouble(oneTwo.getText().toString());
            one_three = Double.parseDouble(oneThree.getText().toString());
            one_four = Double.parseDouble(oneFour.getText().toString());
            two_three = Double.parseDouble(twoThree.getText().toString());
            two_four = Double.parseDouble(twoFour.getText().toString());
            three_four = Double.parseDouble(threeFour.getText().toString());

            two_one = 1 / one_two;
            three_one = 1 / one_three;
            three_two = 1 / two_three;
            four_one = 1 / one_four;
            four_two = 1 / two_four;
            four_three = 1 / three_four;

            twoOne.setText(df.format(two_one));
            threeOne.setText(df.format(three_one));
            threeTwo.setText(df.format(three_two));
            fourOne.setText(df.format(four_one));
            fourTwo.setText(df.format(four_two));
            fourThree.setText(df.format(four_three));

        }else{
            Toast.makeText(this, "Lütfen tüm verileri girdiğinizden emin olun!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void calculationButton(View view){

        if (a == 2) {
            if (!oneTwo.getText().toString().equals("") && !oneThree.getText().toString().equals("") && !oneFour.getText().toString().equals("")
                    && !twoThree.getText().toString().equals("") && !twoFour.getText().toString().equals("") && !threeFour.getText().toString().equals("")) {

                Double firstColumn = one_one + two_one + three_one + four_one;
                Double secondColumn = one_two + two_two + three_two + four_two;
                Double thirdColumn = one_three + two_three + three_three + four_three;
                Double fourthColumn = one_four + two_four + three_four + four_four;

                Double d_one_one = one_one / firstColumn;
                Double d_one_two = one_two / secondColumn;
                Double d_one_three = one_three / thirdColumn;
                Double d_one_four = one_four / fourthColumn;

                Double d_two_one = two_one / firstColumn;
                Double d_two_two = two_two / secondColumn;
                Double d_two_three = two_three / thirdColumn;
                Double d_two_four = two_four / fourthColumn;

                Double d_three_one = three_one / firstColumn;
                Double d_three_two = three_two / secondColumn;
                Double d_three_three = three_three / thirdColumn;
                Double d_three_four = three_four / fourthColumn;

                Double d_four_one = four_one / firstColumn;
                Double d_four_two = four_two / secondColumn;
                Double d_four_three = four_three / thirdColumn;
                Double d_four_four = four_four / fourthColumn;

                Double firstRow = (d_one_one + d_one_two + d_one_three + d_one_four) / 4;
                Double secondRow = (d_two_one + d_two_two + d_two_three + d_two_four) / 4;
                Double thirdRow = (d_three_one + d_three_two + d_three_three + d_three_four) / 4;
                Double fourthRow = (d_four_one + d_four_two + d_four_three + d_four_four) / 4;

                Double dd_one_one = one_one * firstRow;
                Double dd_two_one = two_one * firstRow;
                Double dd_three_one = three_one * firstRow;
                Double dd_four_one = four_one * firstRow;

                Double dd_one_two = one_two * secondRow;
                Double dd_two_two = two_two * secondRow;
                Double dd_three_two = three_two * secondRow;
                Double dd_four_two = four_two * secondRow;

                Double dd_one_three = one_three * thirdRow;
                Double dd_two_three = two_three * thirdRow;
                Double dd_three_three = three_three * thirdRow;
                Double dd_four_three = four_three * thirdRow;

                Double dd_one_four = one_four * fourthRow;
                Double dd_two_four = two_four * fourthRow;
                Double dd_three_four = three_four * fourthRow;
                Double dd_four_four = four_four * fourthRow;

                Double d_first_row = dd_one_one + dd_one_two + dd_one_three + dd_one_four;
                Double d_second_row = dd_two_one + dd_two_two + dd_two_three + dd_two_four;
                Double d_third_row = dd_three_one + dd_three_two + dd_three_three + dd_three_four;
                Double d_fourth_row = dd_four_one + dd_four_two + dd_four_three + dd_four_four;

                Double dd_first_row = d_first_row / firstRow;
                Double dd_second_row = d_second_row / secondRow;
                Double dd_third_row = d_third_row / thirdRow;
                Double dd_fourth_row = d_fourth_row / fourthRow;

                double ddMean = (dd_first_row + dd_second_row + dd_third_row + dd_fourth_row) / 4;
                double consistency = (ddMean - 4) / 3;
                double result = consistency / 0.90;

                if (result < 0.10) {
                    ahpText.setTextColor(Color.parseColor("#32CD32"));
                    ahpText.setText("Olurlu");
                    explanationText.setText("Girdiğiniz değerlere göre bulunan sonuç: " + result + " değeri " + "0.10 değerinden küçüktür.");
                } else {
                    ahpText.setTextColor(Color.parseColor("#CC0000"));
                    ahpText.setText("Olursuz");
                    explanationText.setText("Girdiğiniz değerlere göre bulunan sonuç: " + result + " değeri " + "0.10 değerinden büyüktür.");
                }
            }else{
                Toast.makeText(this, "Lütfen tüm verileri girdiğinizden emin olun!", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Tüm verileri girdikten sonra, Tabloyu Tamamla butonuna dokununuz.", Toast.LENGTH_SHORT).show();
        }
    }
}