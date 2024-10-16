package com.mobile.peticos.Local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import java.util.Arrays;
import java.util.List;

public class LocalFragment extends Fragment {

    public LocalFragment() {
        // Required empty public constructor
    }

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewLocais);  // Certifique-se de que o ID está correto
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Exemplo de dados para os locais
        List<Local> locaisList = Arrays.asList(
                new Local("Local 1", "Descrição do Local 1", R.drawable.local_image1),
                new Local("Local 2", "Descrição do Local 2", R.drawable.local_image1)
        );

        LocaisAdapter adapter = new LocaisAdapter(locaisList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
