package uk.flood.noise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify);
        findViewById(android.R.id.text1).setVisibility(View.GONE);
        findViewById(android.R.id.button1).setOnClickListener(this);
        findViewById(android.R.id.button2).setOnClickListener(this);
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
