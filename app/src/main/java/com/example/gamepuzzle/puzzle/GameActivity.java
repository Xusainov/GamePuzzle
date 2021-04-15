package com.example.gamepuzzle.puzzle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamepuzzle.R;

public class GameActivity extends AppCompatActivity {
    private Button uch, turt, besh;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        uch = findViewById(R.id.uch);
        turt = findViewById(R.id.turt);
        besh = findViewById(R.id.besh);

        imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        uch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameActivity.this, UchActivity.class));
                Log.i("SHG", "3X3 Bosildi");
            }
        });

        turt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameActivity.this, TurtActivity.class));
                Log.i("SHG", "4X4 Bosildi");
            }
        });

        besh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameActivity.this,BeshActivity.class));
                Log.i("SHG", "5X5 Bosildi");
            }
        });

    }
}
