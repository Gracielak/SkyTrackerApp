package com.example.skytrackerapp;

import android.os.Bundle; // ✅ necessário para enviar dados para o MapFragment
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.skytrackerapp.fragments.ForecastFragment;
import com.example.skytrackerapp.fragments.MapFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private double latitude;
    private double longitude;
    private String cidadeNome;

    // ✅ Construtor com parâmetros de localização e nome da cidade
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                            double latitude,
                            double longitude,
                            String cidadeNome) {
        super(fragmentActivity);
        this.latitude = latitude;
        this.longitude = longitude;
        this.cidadeNome = cidadeNome;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            // ✅ MapFragment recebe os dados via Bundle
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString("cidadeNome", cidadeNome);
            mapFragment.setArguments(bundle);
            return mapFragment;
        }

        // Página 0: ForecastFragment
        return new ForecastFragment();
    }

    @Override
    public int getItemCount() {
        return 2; // Forecast + Map
    }
}
