package com.example.skytrackerapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.skytrackerapp.fragments.ForecastFragment;
import com.example.skytrackerapp.fragments.MapFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ForecastFragment(); // Página 1
        } else {
            return new MapFragment(); // Página 2
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Duas páginas
    }
}