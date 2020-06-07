package com.example.atilaversionbeta.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atilaversionbeta.Interfaces.MainActivity;
import com.example.atilaversionbeta.R;

public class MainFragment extends Fragment {
    TextView textoBuscado;
    String municipioBuscado = MainActivity.municipioBuscado;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        textoBuscado = view.findViewById(R.id.municipioBuscado);
        textoBuscado.setText(municipioBuscado);
        return view;
    }
}
