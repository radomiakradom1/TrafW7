
package com.example.trafw7;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.zstradom.daniel.trafw7.R;

public class MainActivity extends AppCompatActivity {

    private TextView licznik;
    private TextView komunikat;
    private TextView trafieniaText;

    private Button start;
    private Button stop;

    private Handler handler = new Handler();

    private int cyfra = 0;
    private int trafienia = 0;

    private int czas = 500;
    private boolean czyDziala = false;

    private final Runnable zmianaCyfry = new Runnable() {
        @Override
        public void run() {

            cyfra++;

            if (cyfra > 9) {
                cyfra = 0;
            }

            licznik.setText(String.valueOf(cyfra));

            handler.postDelayed(this, czas);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        licznik = findViewById(R.id.licznik);
        komunikat = findViewById(R.id.komunikat);
        trafieniaText = findViewById(R.id.trafienia);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        start.setOnClickListener(v -> rozpocznijGre());

        stop.setOnClickListener(v -> zatrzymajGre());
    }

    private void rozpocznijGre() {

        cyfra = 0;

        licznik.setText("0");
        komunikat.setText("");

        czyDziala = true;

        start.setEnabled(false);
        stop.setEnabled(true);

        handler.postDelayed(zmianaCyfry, czas);
    }

    private void zatrzymajGre() {

        if (!czyDziala) {
            return;
        }

        handler.removeCallbacks(zmianaCyfry);

        czyDziala = false;

        start.setEnabled(true);
        stop.setEnabled(false);

        if (cyfra == 7) {

            komunikat.setText("Trafienie!");
            komunikat.setTextColor(0xFF16A34A);

            trafienia++;

            // Przyspieszenie po każdym trafieniu
            czas = Math.max(200, czas - 50);

        } else {

            komunikat.setText("Pudło! Zatrzymano na " + cyfra);
            komunikat.setTextColor(0xFFDC2626);
        }

        trafieniaText.setText("Trafienia: " + trafienia);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(zmianaCyfry);
        super.onDestroy();
    }
}
