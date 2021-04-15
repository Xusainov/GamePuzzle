package com.example.gamepuzzle.puzzle;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class BeshActivity extends AppCompatActivity {
    private ViewGroup btnGroup;
    private Button stop;
    private ImageView restart;
    private ImageView back;
    private Button[][] btn = new Button[5][5];
    private Timer timer;
    private ArrayList<Integer> numbers = new ArrayList<>();
    private TextView timeShow, stepShow;
    private int emptyI = 4, emptyJ = 4;
    private int step = 0;
    private int time;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_besh);
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
        stepShow.setText(String.valueOf(step));
        timeShow.setText(String.valueOf(time));
        loadView();
        loadDataToView();
    }

    public void getTags() {
        loadNumber();
        for (int i = 0; i < btnGroup.getChildCount(); i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 5][i % 5] = (Button) btnGroup.getChildAt(i);
                btn[i / 5][i % 5].setTag(i / 5 + "" + i % 5);
            }
        }

    }

    public void loadNumber() {
        numbers.clear();
        for (int i = 1; i < 25; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
    }

    public void loadView() {
        loadNumber();

        stop = findViewById(R.id.stopcha);
        restart = findViewById(R.id.res);
        btnGroup = findViewById(R.id.reletivcha);
        timeShow = findViewById(R.id.soat);
        stepShow = findViewById(R.id.sanagich);
        back = findViewById(R.id.besh_back);

        for (int i = 0; i < btnGroup.getChildCount(); i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 5][i % 5] = (Button) btnGroup.getChildAt(i);
                btn[i / 5][i % 5].setTag(i / 5 + "" + i % 5);
            }
        }
    }

    public void loadDataToView() {
        emptyI = 4;
        emptyJ = 4;
        for (int i = 0; i < btnGroup.getChildCount() - 1; i++) {
            if (btnGroup.getChildAt(i) instanceof Button) {
                btn[i / 5][i % 5].setText(String.valueOf(numbers.get(i)));
                btn[i / 5][i % 5].setBackgroundColor(Color.parseColor("#00BCD4"));
                Log.i("SHG", "Bosildi");
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
        int minute = (time - hour * 3600) / 60;
        String currenttime = String.format("%02d:%02d:%02d", hour, minute, secund);
        timeShow.setText(String.valueOf(currenttime));
    }

    public void OnClick(View view) {
        Button button = (Button) view;
        int i = Integer.parseInt(String.valueOf(String.valueOf(button.getTag()).charAt(0)));
        int j = Integer.parseInt(String.valueOf(String.valueOf(button.getTag()).charAt(1)));

        if ((i == emptyI && Math.abs(j - emptyJ) == 1) || (j == emptyJ && Math.abs(i - emptyI) == 1)) {
            btn[emptyI][emptyJ].setText(button.getText());
            btn[emptyI][emptyJ].setBackgroundColor(Color.parseColor("#00BCD4"));
            Log.i("SHG", "iSHLADI");

            button.setText("");
            button.setBackgroundColor(Color.parseColor("#E8DFDF"));
            Log.i("SHG", "Ishlaydi");
            emptyI = i;
            emptyJ = j;
            step++;
            stepShow.setText("Moves : " + String.valueOf(step));
            soundPlayer.playButtonSound();
            cheskWin();
        }
    }

    public void cheskWin() {
        boolean t = true;
        if (emptyI == 4 && emptyJ == 4) {
            for (int i = 0; i < 24; i++) {
                t = t && btn[emptyI][emptyJ].getText().equals(String.valueOf(i + 1));
            }
        } else {
            t = false;
        }
        if (t) {
            Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show();
            Refresh();
            soundPlayer.playWinSound();
        }
    }
}
