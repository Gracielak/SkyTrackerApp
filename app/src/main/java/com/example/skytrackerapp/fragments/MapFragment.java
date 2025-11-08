package com.example.skytrackerapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skytrackerapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    public MapFragment() {
        // Construtor vazio obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ✅ Carrega o fragment do XML
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapFragment);

        // ✅ Inicializa o mapa de forma segura
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {

                // ✅ Exemplo simples: marcador em São Paulo
                LatLng sp = new LatLng(-23.55052, -46.633308);
                googleMap.addMarker(new MarkerOptions().position(sp).title("São Paulo"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sp, 12f));

            });
        }
    }
}
