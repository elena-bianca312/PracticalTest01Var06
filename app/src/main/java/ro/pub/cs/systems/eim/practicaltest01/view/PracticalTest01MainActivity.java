package ro.pub.cs.systems.eim.practicaltest01.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest01.R;
import ro.pub.cs.systems.eim.practicaltest01.service.PracticalTest01Service;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText editText1, editText2, editText3;
    private CheckBox checkBox1, checkBox2, checkBox3;
    private Button playButton;

    private Random random = new Random();

    private Integer score;

    private IntentFilter intentFilter = new IntentFilter();

    private int serviceStatus = Constants.SERVICE_STOPPED;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
        @Override
        public void onClick(View view) {

            switch(view.getId()) {
                case R.id.play_button:
                    int int_random1 = random.nextInt(3) + 1;
                    int int_random2 = random.nextInt(3) + 1;
                    int int_random3 = random.nextInt(3) + 1;
                    int noOfCheckedBoxes = 0;
                    if (checkBox1.isChecked()) {
                        editText1.setText("*");
                        noOfCheckedBoxes++;
                    } else {
                        editText1.setText(String.valueOf(int_random1));
                    }
                    if (checkBox2.isChecked()) {
                        editText2.setText("*");
                        noOfCheckedBoxes++;
                    } else {
                        editText2.setText(String.valueOf(int_random2));
                    }
                    if (checkBox3.isChecked()) {
                        editText3.setText("*");
                        noOfCheckedBoxes++;
                    } else {
                        editText3.setText(String.valueOf(int_random3));
                    }
                    String message = int_random1 + " " + int_random2 + " " + int_random3;
                    Toast.makeText(PracticalTest01MainActivity.this, message , Toast.LENGTH_LONG).show();

                    // Create intent for secondary activity
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra(Constants.FIRST_NUMBER, int_random1);
                    intent.putExtra(Constants.SECOND_NUMBER, int_random2);
                    intent.putExtra(Constants.THIRD_NUMBER, int_random3);
                    intent.putExtra(Constants.NUMBER_OF_CHECKED_BOXES, noOfCheckedBoxes);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);

                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
//            Toast.makeText(context, (Constants.BROADCAST_RECEIVER_TAG + intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        score = 300;

        // Set view as the main xml we created
        setContentView(R.layout.activity_practical_test01_main);

        // Bind text and button variables with identifiers
        editText1 = (EditText)findViewById(R.id.edit_text_1);
        editText2 = (EditText)findViewById(R.id.edit_text_2);
        editText3 = (EditText)findViewById(R.id.edit_text_3);

        // Initialize checkboxes
        checkBox1 = (CheckBox)findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox)findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox)findViewById(R.id.checkbox_3);

        // Initialize text field with 0
        editText1.setText(String.valueOf(0));
        editText2.setText(String.valueOf(0));
        editText3.setText(String.valueOf(0));

        // Add listeners to buttons so we can change/increment them according to UI updates
        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.SCORE, score.toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.SCORE)) {
            score = Integer.valueOf(Constants.SCORE);
        } else {
            score = 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
//            String text = intent.getStringExtra(Constants.TOAST_VALUE);
            Toast.makeText(this, "Gained: " + resultCode, Toast.LENGTH_LONG).show();
            score += resultCode;
            Toast.makeText(this, "Scor: " + score, Toast.LENGTH_LONG).show();
            serviceStatus = Constants.SERVICE_STOPPED;
            if (score != null) {
                Intent intent2 = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent2.putExtra(Constants.TOAST_VALUE, String.valueOf(score));
                getApplicationContext().startService(intent2);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}