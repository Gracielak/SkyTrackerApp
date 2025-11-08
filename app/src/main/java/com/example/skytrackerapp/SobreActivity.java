package com.example.skytrackerapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        // Botão voltar da segunda barra
        ImageView botaoVoltar = findViewById(R.id.botaoVoltarSobre);
        botaoVoltar.setOnClickListener(v -> finish());
    }
}
