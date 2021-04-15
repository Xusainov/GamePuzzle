package com.example.gamepuzzle.soundplayer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.gamepuzzle.R;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    private final int SOUND_POOL_MAX = 3;

    private static SoundPool soundPool;
    private static int buttonSound;
    private static int refreshSound;
    private static int winSound;
    private static int stopSound;

    public SoundPlayer(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
        buttonSound = soundPool.load(context, R.raw.bosilish, 1);
        refreshSound = soundPool.load(context, R.raw.refresh, 2);
        winSound = soundPool.load(context, R.raw.win, 3);
        stopSound = soundPool.load(context, R.raw.stop, 1);
    }

    public void playButtonSound() {
        soundPool.play(buttonSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playStopSound() {
        soundPool.play(stopSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playWinSound() {
        soundPool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playRefreshSound() {
        soundPool.play(refreshSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
