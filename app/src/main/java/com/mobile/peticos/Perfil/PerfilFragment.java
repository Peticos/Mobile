package com.mobile.peticos.Perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobile.peticos.Perfil.AdapterLembretes.CarouselAdapter;
import com.mobile.peticos.Perfil.AdapterLembretes.Lembrete;
import com.mobile.peticos.Perfil.Calendario.CalendarioFragment;
import com.mobile.peticos.Perfil.Graficos.GraficoFragment;
import com.mobile.peticos.Perfil.Posts.FeedDoPet;
import com.mobile.peticos.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class PerfilFragment extends Fragment {


    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        return view;
    }





}