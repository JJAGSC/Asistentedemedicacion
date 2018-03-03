package com.medicacion.juanjose.asistentedemedicacion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Juanjo on 28/02/2018.
 */

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();

        // Una vez iniciada, la alarma sonará unos segundos y se apagará
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }, 15000);

    }
}
