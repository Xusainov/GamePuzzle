package com.example.gamepuzzle.puzzle;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamepuzzle.R;
import com.example.gamepuzzle.soundplayer.SoundPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class UchActivity extends AppCompatActivity {
    private TextView timeShow, stepShow;
    private ViewGroup btnGroup;
    private ArrayList<Integer> numbers = new ArrayList<>();
    private Button stop;
    private ImageView refresh;
    private ImageView uchhhh;
    private Button[][] btn = new Button[3][3];
    int step;
    private int emptyI = 2, emptyJ = 2;
    private int time = 0;
    private Timer timer;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uch);


        soundPlayer = new SoundPlayer(this);
        loadView();
        loadDataToView();
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                soundPlayer.playStopSound();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart();
                soundPlayer.playRefreshSound();
            }
        });
        uchhhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void Restart() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        step = 0;
        time = 0;
        stepShow.setText(String.valueOf(step));
        timeShow.setText(String.valueOf(time));
        loadView();
        loadDataToView();
    }

    public void loadNumber() {
        numbers.clear();
        for (int i = 1; i < 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }

    public void getTags() {
        loadNumber();
        for (int i = 0; i < btnGroup.getChildCount(); i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 3][i % 3] = (Button) btnGroup.getChildAt(i);
                btn[i / 3][i % 3].setTag(i / 3 + "" + i % 3);
            }
        }
    }

    public void loadView() {
        loadNumber();

        stop = findViewById(R.id.stop);
        refresh = findViewById(R.id.restart);
        btnGroup = findViewById(R.id.relative);
        timeShow = findViewById(R.id.timeshow);
        stepShow = findViewById(R.id.stepshow);
        uchhhh = findViewById(R.id.uch_back);


        for (int i = 0; i < btnGroup.getChildCount(); i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 3][i % 3] = (Button) btnGroup.getChildAt(i);
                btn[i / 3][i % 3].setTag(i / 3 + "" + i % 3);
            }
        }
    }

    public void loadDataToView() {
        emptyI = 2;
        emptyJ = 2;
        for (int i = 0; i < btnGroup.getChildCount() - 1; i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 3][i % 3].setText(String.valueOf(numbers.get(i)));
                btn[i / 3][i % 3].setBackgroundColor(Color.parseColor("#00BCD4"));
            }
        }
        btn[emptyI][emptyJ].setText("");
        btn[emptyI][emptyJ].setBackgroundColor(Color.parseColor("#E8DFDF"));

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTime(time);
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    public void setTime(int time) {
        int sekund = time % 60;
        int hour = time / 3600;
        int minute = (time - hour * 3600) / 60;
        String currentTime = String.format("%02d:%02d:%02d", hour, minute, sekund);
        timeShow.setText(currentTime);
    }

    public void ButtonClick(View view) {

        Button button = (Button) view;
        int i = Integer.parseInt(String.valueOf(String.valueOf(button.getTag()).charAt(0)));
        int j = Integer.parseInt(String.valueOf(String.valueOf(button.getTag()).charAt(1)));

        if ((i == emptyI && Math.abs(j - emptyJ) == 1) || (j == emptyJ && Math.abs(i - emptyI) == 1)) {
            btn[emptyI][emptyJ].setText(button.getText());
            btn[emptyI][emptyJ].setBackgroundColor(Color.parseColor("#00BCD4"));


            button.setText("");
            button.setBackgroundColor(Color.parseColor("#E8DFDF"));

            emptyI = i;
            emptyJ = j;
            step++;

            stepShow.setText("Moves : " + String.valueOf(step));
            soundPlayer.playButtonSound();
            checkWin();
        }
    }

    @SuppressLint("SetTextI18n")
    public void checkWin() {
        boolean t = true;
        if (emptyI == 2 && emptyJ == 2) {
            for (int i = 0; i < 8; i++) {
                t = t && btn[i / 3][i % 3].getText().equals(String.valueOf(i + 1));
            }
        } else {
            t = false;
        }
        if (t) {
            Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
            soundPlayer.playWinSound();
            Restart();
        }
    }
}