package com.example.skytrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    // ✅ Coordenadas padrão da cidade selecionada
    private double latitude = -24.7135;   // Toledo
    private double longitude = -53.7433;  // Toledo
    private String cidadeNome = "Toledo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Clique no ícone do menu para abrir a aba "Sobre"
        ImageView iconeMenu = findViewById(R.id.iconeMenu);
        iconeMenu.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SobreActivity.class))
        );

        // ✅ Configurar ViewPager2
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // ✅ Enviar parâmetros para o Adapter (correto!)
        ViewPagerAdapter adapter =
                new ViewPagerAdapter(this, latitude, longitude, cidadeNome);

        viewPager.setAdapter(adapter);

        // ✅ Configurar TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Previsão");
            } else {
                tab.setText("Mapa");
            }
        }).attach();
    }
}
