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

public class AhpThreeCriteriaActivity extends AppCompatActivity {

    EditText oneTwo, oneThree, twoThree;
    TextView oneOne, twoOne, twoTwo, threeOne, threeTwo, threeThree, explanationText, threeText;
    Button tableCompleteButton, calculateButton;
    Double one_one, two_two, three_three, one_two, one_three, two_three, two_one, three_one, three_two;

    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_three_criteria);

        oneOne = findViewById(R.id.oneOneT);
        oneTwo = findViewById(R.id.oneTwoT);
        oneThree = findViewById(R.id.oneThreeT);
        twoOne = findViewById(R.id.twoOneT);
        twoTwo = findViewById(R.id.twoTwoT);
        twoThree = findViewById(R.id.twoThreeT);
        threeOne = findViewById(R.id.threeOneT);
        threeTwo = findViewById(R.id.threeTwoT);
        threeThree = findViewById(R.id.threeThreeT);
        threeText = findViewById(R.id.threeText);
        explanationText = findViewById(R.id.explanationTextT);
        tableCompleteButton = findViewById(R.id.tableCompleteButtonT);
        calculateButton = findViewById(R.id.calculateButtonT);
    }

    @SuppressLint("SetTextI18n")
    public void calculateButtonT(View view){

        if (a == 2) {
            if (!oneTwo.getText().toString().equals("") && !oneThree.getText().toString().equals("") && !twoThree.getText().toString().equals("")) {

                Double firstColumn = one_one + two_one + three_one;
                Double secondColumn = one_two + two_two + three_two;
                Double thirdColumn = one_three + two_three + three_three;

                Double d_one_one = one_one / firstColumn;
                Double d_one_two = one_two / secondColumn;
                Double d_one_three = one_three / thirdColumn;

                Double d_two_one = two_one / firstColumn;
                Double d_two_two = two_two / secondColumn;
                Double d_two_three = two_three / thirdColumn;

                Double d_three_one = three_one / firstColumn;
                Double d_three_two = three_two / secondColumn;
                Double d_three_three = three_three / thirdColumn;

                Double firstRow = (d_one_one + d_one_two + d_one_three ) / 3;
                Double secondRow = (d_two_one + d_two_two + d_two_three ) / 3;
                Double thirdRow = (d_three_one + d_three_two + d_three_three ) / 3;

                Double dd_one_one = one_one * firstRow;
                Double dd_two_one = two_one * firstRow;
                Double dd_three_one = three_one * firstRow;

                Double dd_one_two = one_two * secondRow;
                Double dd_two_two = two_two * secondRow;
                Double dd_three_two = three_two * secondRow;

                Double dd_one_three = one_three * thirdRow;
                Double dd_two_three = two_three * thirdRow;
                Double dd_three_three = three_three * thirdRow;

                Double d_firstRow = dd_one_one + dd_one_two + dd_one_three ;
                Double d_secondRow = dd_two_one + dd_two_two + dd_two_three;
                Double d_thirdRow = dd_three_one + dd_three_two + dd_three_three ;

                Double dd_firstRow = d_firstRow / firstRow;
                Double dd_secondRow = d_secondRow / secondRow;
                Double dd_thirdRow = d_thirdRow / thirdRow;

                double mean = (dd_firstRow + dd_secondRow + dd_thirdRow) / 3;
                double consistency = (mean - 3) / 2;
                double result = consistency / 0.58;

                if (result < 0.10) {
                    threeText.setTextColor(Color.parseColor("#32CD32"));
                    threeText.setText("Feasible");
                    explanationText.setText("The " + result + " found according to the values you entered is less than 0.10.");
                } else {
                    threeText.setTextColor(Color.parseColor("#CC0000"));
                    threeText.setText("Unfeasible");
                    explanationText.setText("The " + result +" found according to the values you entered is greater than 0.10.");

                }
            }else{
                Toast.makeText(this, "Please be sure to enter all data!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "After entering the values, click the Complete Table button.", Toast.LENGTH_SHORT).show();
        }
    }

    public void tableCompleteT(View view) {

        if (!oneTwo.getText().toString().equals("") && !oneThree.getText().toString().equals("") && !twoThree.getText().toString().equals("")) {
            a = 2;
            DecimalFormat df = new DecimalFormat("#.#####");

            one_one = Double.parseDouble(oneOne.getText().toString());
            two_two = Double.parseDouble(twoTwo.getText().toString());
            three_three = Double.parseDouble(threeThree.getText().toString());

            one_two = Double.parseDouble(oneTwo.getText().toString());
            one_three = Double.parseDouble(oneThree.getText().toString());
            two_three = Double.parseDouble(twoThree.getText().toString());

            two_one = 1 / one_two;
            three_one = 1 / one_three;
            three_two = 1 / two_three;

            twoOne.setText(df.format(two_one));
            threeOne.setText(df.format(three_one));
            threeTwo.setText(df.format(three_two));
        }else{
            Toast.makeText(this, "Please be sure to enter all data!", Toast.LENGTH_SHORT).show();
        }
    }
}