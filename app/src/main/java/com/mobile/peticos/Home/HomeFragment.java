package com.mobile.peticos.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import com.mobile.peticos.Home.AdapterCuriosidadesDiarias; // Certifique-se de que o import está correto
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Configurar o RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewDicas); // Verifique se o ID corresponde ao layout

        // Configurar o LayoutManager para orientação horizontal
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dados de exemplo
        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");

        // Configurar o Adapter
        AdapterCuriosidadesDiarias adapter = new AdapterCuriosidadesDiarias(items);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
