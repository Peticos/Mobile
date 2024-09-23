package com.mobile.peticos.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import com.mobile.peticos.Home.AdapterCuriosidadesDiarias;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Configuração do RecyclerView para dicas
        RecyclerView recyclerViewDicas = view.findViewById(R.id.RecyclerViewDicas);
        recyclerViewDicas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dados de exemplo para o RecyclerViewDicas
        List<String> dicasItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");

        // Configuração do Adapter para o RecyclerViewDicas
        AdapterCuriosidadesDiarias dicasAdapter = new AdapterCuriosidadesDiarias(dicasItems);
        recyclerViewDicas.setAdapter(dicasAdapter);

        // Configuração do RecyclerView para o feed de pets
        RecyclerView recyclerViewFeedPets = view.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));

        // Exemplo de dados para o feed de pets
        List<FeedPet> feedPets = Arrays.asList(
                new FeedPet("geogeo43", "nutela", "Há 2 dias", "curtido por João", "descrição da imagem", R.drawable.user1, R.drawable.publicacao1),
                new FeedPet("amanda_pet", "lola", "Há 1 dia", "curtido por Maria", "uma foto da lola", R.drawable.user1, R.drawable.publicacao1)
        );

        // Configuração do Adapter para o RecyclerViewFeedPets
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(feedPets, feedPet -> {
            // Tratar clique no item (feedPet)
        });
        recyclerViewFeedPets.setAdapter(feedPetsAdapter);

        return view;
    }
}
