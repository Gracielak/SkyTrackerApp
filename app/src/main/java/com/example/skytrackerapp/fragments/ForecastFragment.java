package com.example.skytrackerapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultLauncher;

import com.example.skytrackerapp.R;
import com.example.skytrackerapp.WeatherApi;
import com.example.skytrackerapp.WeatherResponse;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastFragment extends Fragment {

    private static final String API_KEY = "598db062";
    private static final String BASE_URL = "https://api.hgbrasil.com/";

    private WeatherApi api;

    private final List<MaterialCardView> cardViews = new ArrayList<>();
    private final List<TextView> tempViews = new ArrayList<>();
    private final List<TextView> cityViews = new ArrayList<>();

    private int selectedCardIndex = -1; // Nenhum card selecionado inicialmente

    public ForecastFragment() {}

    private final ActivityResultLauncher<ScanOptions> qrLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    if (selectedCardIndex == -1) {
                        Toast.makeText(getContext(), "Selecione um card antes de escanear", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String novaCidade = result.getContents();
                    Toast.makeText(getContext(), "Cidade escaneada: " + novaCidade, Toast.LENGTH_SHORT).show();
                    updateCard(selectedCardIndex, novaCidade);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Inicializa cards e TextViews
        cardViews.add(view.findViewById(R.id.card1));
        tempViews.add(view.findViewById(R.id.temp1TextView));
        cityViews.add(view.findViewById(R.id.city1TextView));

        cardViews.add(view.findViewById(R.id.card2));
        tempViews.add(view.findViewById(R.id.temp2TextView));
        cityViews.add(view.findViewById(R.id.city2TextView));

        cardViews.add(view.findViewById(R.id.card3));
        tempViews.add(view.findViewById(R.id.temp3TextView));
        cityViews.add(view.findViewById(R.id.city3TextView));

        // Adiciona clique em cada card para selecionar
        for (int i = 0; i < cardViews.size(); i++) {
            final int index = i;
            cardViews.get(i).setOnClickListener(v -> selectCard(index));
        }

        // FAB QR Code
        FloatingActionButton fabScan = view.findViewById(R.id.fabScan);
        fabScan.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Escaneie o QR Code da cidade");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            qrLauncher.launch(options);
        });

        // Inicializa Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WeatherApi.class);

        // Carrega cidades iniciais
        updateCard(0, "Toledo,PR");
        updateCard(1, "Cascavel,PR");
        updateCard(2, "Curitiba,PR");

        return view;
    }

    // Seleciona um card visualmente
    private void selectCard(int index) {
        for (int i = 0; i < cardViews.size(); i++) {
            if (i == index) {
                cardViews.get(i).setStrokeColor(Color.BLUE);
                cardViews.get(i).setStrokeWidth(6);
                selectedCardIndex = index;
            } else {
                cardViews.get(i).setStrokeWidth(0);
            }
        }
    }

    // Atualiza o card pelo índice
    private void updateCard(int index, String cidade) {
        if (index < 0 || index >= cardViews.size()) return;

        Call<WeatherResponse> call = api.getWeather(API_KEY, cidade);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse.Results results = response.body().results;
                    tempViews.get(index).setText(results.temp + "°C");
                    cityViews.get(index).setText(results.description + "\n" + results.city);
                } else {
                    cityViews.get(index).setText("Erro ao carregar");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                cityViews.get(index).setText("Falha na conexão");
                Toast.makeText(getContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
