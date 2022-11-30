package com.example.buscaminas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements EventClickCelda{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void ClickCelda(Celda celda) {
        Toast.makeText(getApplicationContext(), "Celda clickada", Toast.LENGTH_SHORT).show();
    }
}