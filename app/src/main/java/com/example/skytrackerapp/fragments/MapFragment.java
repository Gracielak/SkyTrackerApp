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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private double latitude = -23.55052; // Valor padrão (São Paulo)
    private double longitude = -46.633308;
    private String cidadeNome = "Cidade selecionada"; // ✅ ADICIONADO

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

        // ✅ Recupera dados enviados pelo Bundle
        if (getArguments() != null) {
            latitude = getArguments().getDouble("latitude", latitude);
            longitude = getArguments().getDouble("longitude", longitude);
            cidadeNome = getArguments().getString("cidadeNome", cidadeNome); // ✅ Agora funciona
        }

        // ✅ Carrega o fragment do XML
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.mapFragment);

        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                LatLng cidadeLatLng = new LatLng(latitude, longitude);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(cidadeLatLng).title(cidadeNome));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cidadeLatLng, 12f));
            });
        }
    }
}
