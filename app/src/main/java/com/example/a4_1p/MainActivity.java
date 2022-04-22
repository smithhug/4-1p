package com.example.a4_1p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean timing = false;
    private EditText editTaskName;
    private TextView returntext;
    private CharSequence savedTimeStr;
    private TextView timeView;
    private CharSequence savedTaskName;
    static final String TIME_TXT = "savedTimeStr";
    static final String TASK_TXT = "savedTaskName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer();

        timeView = findViewById(R.id.timeView);

        if (savedInstanceState != null) { //restore values from last instance
            savedTimeStr = savedInstanceState.getCharSequence(TIME_TXT);
            savedTaskName = savedInstanceState.getCharSequence(TASK_TXT);

            editTaskName = findViewById(R.id.taskname);
            returntext =  findViewById(R.id.lastTIme);
            returntext.setText("You spent " + savedTimeStr + " working on " + savedTaskName + " last time");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { //save name of task and time spent
        savedTaskName = editTaskName.getText();
        savedInstanceState.putCharSequence(TIME_TXT, savedTimeStr);
        savedInstanceState.putCharSequence(TASK_TXT, savedTaskName);

        super.onSaveInstanceState(savedInstanceState);
    }
    public void onClickStart(View view)
    { //start timing
        timing = true;
    }
    public void onClickPause(View view)
    { //stop timer temporarily
        timing = false;
    }
    public void onClickStop(View view)
    { //stop timer, save values and reset to 0
        timing = false;
        seconds = 0;
        savedTimeStr = timeView.getText();
        editTaskName = findViewById(R.id.taskname);
        returntext =  findViewById(R.id.lastTIme);
        returntext.setText("You spent " + savedTimeStr + " working on " + editTaskName.getText() + " last time");
    }
    void timer()
    {
        Handler handler = new Handler(); //use handler in order to repeat timer actions every 1 second.
        handler.post(new Runnable() {
            @Override
            public void run() {
                int secs = seconds % 60; //show seconds up to 60
                int minutes = (seconds % 3600) / 60; //show minutes up to 60
                int hours = seconds / 3600; //show hours

                if (hours < 1){ //if no hours have passed only show mins and secs
                    timeView.setText(String.format("%02d", minutes) + "." + String.format("%02d", secs));
                }
                else //if hours passed show the amount of hours passed + mins and secs
                    timeView.setText(hours + "." + String.format("%02d", minutes) + "." + String.format("%02d", secs));

                if (timing == true) {
                    seconds++; //inc seconds...
                }

                handler.postDelayed(this, 1000); //... every second
            }
        });
    }
}