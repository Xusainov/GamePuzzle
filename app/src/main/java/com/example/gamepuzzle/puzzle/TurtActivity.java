package com.example.gamepuzzle.puzzle;

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

public class TurtActivity extends AppCompatActivity {

    private TextView timeshow, stepshow;
    private ViewGroup btngroup;
    private ArrayList<Integer> numbers = new ArrayList<>();
    private Button[][] btn = new Button[4][4];
    private int emptyI = 3, emptyJ = 3;
    private int step = 0;
    private int time;
    private Timer timer;
    private Button stop;
    private ImageView restart;
    private ImageView back;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turt);

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
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh();
                soundPlayer.playRefreshSound();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void Refresh() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        step = 0;
        time = 0;
        stepshow.setText(String.valueOf(step));
        timeshow.setText(String.valueOf(time));
        loadView();
        loadDataToView();
    }

    public void getTags() {
        loadNumber();
        for (int i = 0; i < btngroup.getChildCount(); i++) {
            if (btngroup.getChildAt(i) instanceof Button) {
                btn[i / 4][i % 4] = (Button) btngroup.getChildAt(i);
                btn[i / 4][i % 4].setTag(i / 4 + "" + i % 4);
            }
        }
    }

    public void loadNumber() {
        numbers.clear();
        for (int i = 1; i < 16; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }

    public void loadView() {
        loadNumber();

        stop = findViewById(R.id.stopped);
        restart = findViewById(R.id.restarted);
        btngroup = findViewById(R.id.btns_group);
        timeshow = findViewById(R.id.time5);
        stepshow = findViewById(R.id.step5);
        back = findViewById(R.id.turt_back);

        for (int i = 0; i < btngroup.getChildCount(); i++) {
            if (btngroup.getChildAt(i) instanceof Button) {
                btn[i / 4][i % 4] = (Button) btngroup.getChildAt(i);
                btn[i / 4][i % 4].setTag(i / 4 + "" + i % 4);
            }
        }
    }

    public void loadDataToView() {
        emptyI = 3;
        emptyJ = 3;
        for (int i = 0; i < btngroup.getChildCount() - 1; i++) {
            if (btngroup.getChildAt(i) instanceof Button) {
                btn[i / 4][i % 4].setText(String.valueOf(numbers.get(i)));
                btn[i / 4][i % 4].setBackgroundColor(Color.parseColor("#00BCD4"));
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
        int secund = time % 60;
        int hour = time / 3600;
        int minute = (time = hour * 3600) / 60;
        String currenttime = String.format("%02d:%02d:%02d", hour, minute, secund);
        timeshow.setText(currenttime);
    }

    public void TurtClick(View view) {
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
            stepshow.setText("Moves : " + String.valueOf(step));
            soundPlayer.playButtonSound();
            checkWin();
        }
    }

    public void checkWin() {
        boolean t = true;
        if (emptyI == 2 && emptyJ == 2) {
            for (int i = 0; i < 16; i++) {
                t = t && btn[emptyI][emptyJ].getText().equals(String.valueOf(i + 1));
            }
        } else {
            t = false;
        }
        if (t) {
            Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show();
            soundPlayer.playWinSound();
            Refresh();
        }
    }
}
