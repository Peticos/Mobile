package com.mobile.peticos.Perdidos.Achar;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mobile.peticos.R;

public class PetFoundDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    private Button publicar;
    private Button sair;

    // Método para criar uma nova instância do DialogFragment com argumentos
    public static PetFoundDialogFragment newInstance(String title, String message) {
        PetFoundDialogFragment fragment = new PetFoundDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alert_pet_encontrado, container, false);



        new Handler().postDelayed(() -> {
            dismiss();
        }, 3000);



        return view;
    }
}
