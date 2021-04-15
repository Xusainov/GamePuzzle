package com.example.gamepuzzle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gamepuzzle.puzzle.AboutActivity;
import com.example.gamepuzzle.puzzle.GameActivity;

public class MainActivity extends AppCompatActivity {
    private Button aloqa, startGame, about;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        aloqa = findViewById(R.id.aloqa);
        startGame = findViewById(R.id.start_game);
        about = findViewById(R.id.ebout);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
        aloqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SHG", "Dasturdan chiqildi");
                finish();
            }
        });
    }
}
