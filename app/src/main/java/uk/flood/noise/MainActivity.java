package uk.flood.noise;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_POST_NOTIFICATIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        findViewById(android.R.id.text1).setVisibility(View.INVISIBLE);
        findViewById(android.R.id.button1).setOnClickListener(this);
        findViewById(android.R.id.button2).setOnClickListener(this);
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_POST_NOTIFICATIONS);
        }
    }

    @Override
    protected void onDestroy() {
        onClick(null);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        startForegroundService(
                new Intent(MainActivity.this, NoiseService.class)
                        .putExtra("n", v != null && v.getId() == android.R.id.button1)
                        .putExtra("d", v == null)
        );
    }
}
