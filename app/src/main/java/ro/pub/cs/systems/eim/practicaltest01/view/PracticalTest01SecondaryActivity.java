package ro.pub.cs.systems.eim.practicaltest01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView scor;
    private Button okButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK, null);
                    break;
            }
            finish();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

//        scor = (TextView) findViewById(R.id.scor);
//        scor.setText("Gained: 0");
//        okButton = (Button) findViewById(R.id.ok_button);
//        okButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();

        if (intent != null && intent.getExtras().containsKey(Constants.FIRST_NUMBER)
                           && intent.getExtras().containsKey(Constants.SECOND_NUMBER)
                           && intent.getExtras().containsKey(Constants.THIRD_NUMBER)
                           && intent.getExtras().containsKey(Constants.NUMBER_OF_CHECKED_BOXES)) {
            int firstNumber = intent.getIntExtra(Constants.FIRST_NUMBER, 0);
            int secondNumber = intent.getIntExtra(Constants.SECOND_NUMBER, 0);
            int thirdNumber = intent.getIntExtra(Constants.THIRD_NUMBER, 0);
            int noOfCheckedBoxes = intent.getIntExtra(Constants.NUMBER_OF_CHECKED_BOXES, 0);
            int noPoints = 100;
            switch (noOfCheckedBoxes) {
                case 0:
                    noPoints = 100;
                    break;
                case 1:
                    noPoints = 50;
                    break;
                case 2:
                    noPoints = 10;
                    break;
            }

            if (firstNumber == secondNumber && firstNumber == thirdNumber) {
                Intent intent2 = new Intent();
                intent2.putExtra(Constants.TOAST_VALUE, "Gained");
                setResult(noPoints, intent2);
//                scor.setText("Gained: " + noPoints);

            }
        }
        finish();
    }
}