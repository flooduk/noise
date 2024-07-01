package uk.flood.noise;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.io.IOException;

public class NoiseService extends Service {
    private MediaPlayer mp = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private PendingIntent build(boolean value) {
        return PendingIntent.getForegroundService(
                this,
                value ? -1 : 0,
                new Intent(this, NoiseService.class).putExtra("n", value),
                PendingIntent.FLAG_MUTABLE
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager nm = getSystemService(NotificationManager.class);
        NotificationChannel nc = nm.getNotificationChannel("x");
        if (nc == null) {
            nc = new NotificationChannel("x", "x", NotificationManager.IMPORTANCE_DEFAULT);
            nc.setLightColor(Color.WHITE);
            nc.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            nm.createNotificationChannel(nc);
        }
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.notify);
        view.setOnClickPendingIntent(android.R.id.button1, build(true));
        view.setOnClickPendingIntent(android.R.id.button2, build(false));

        Notification n = new Notification.Builder(this, "x")
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setCustomContentView(view)
                .setContentIntent(
                        PendingIntent.getActivity(
                                this,
                                0,
                                new Intent(this, MainActivity.class),
                                PendingIntent.FLAG_MUTABLE)
                )
                .build();
        startForeground((int) System.currentTimeMillis(), n);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }
        boolean destroy = intent.getBooleanExtra("d", false);
        if (destroy) {
            if (mp != null) {
                return START_STICKY;
            } else {
                stopForeground(STOP_FOREGROUND_REMOVE);
                stopSelf();
                return START_NOT_STICKY;
            }
        }
        boolean noise = intent.getBooleanExtra("n", false);
        if (noise) {
            if (mp == null) {
                mp = new MediaPlayer();
                mp.reset();
                mp.setDataSource(new NoiseDataSource());
                mp.setLooping(true);
                try {
                    mp.prepare();
                    mp.start();
                } catch (IOException ignore) {
                }
            }
            return START_STICKY;
        } else {
            MediaPlayer lmp = mp;
            if (lmp != null) {
                lmp.stop();
                lmp.release();
            }
            mp = null;
            return START_NOT_STICKY;
        }
    }
}
