
package com.mobile.peticos.Perfil.Profissional;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.Login;
import com.mobile.peticos.Perfil.Profissional.Graficos.GraficoFragment;
import com.mobile.peticos.Perfil.Tutor.EditarPerfil;
import com.mobile.peticos.R;

public class PerfilProfissional extends Fragment {






    public PerfilProfissional() {
        // Required empty public constructor
    }

    public static PerfilProfissional newInstance() {
        PerfilProfissional fragment = new PerfilProfissional();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    ImageView fotoPerfil;
    TextView nome;
    TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_perfil_profissional, container, false);

        Button graphic = view.findViewById(R.id.area_restrita_button);
        Button novoProduto = view.findViewById(R.id.novo_produto_button);
        LinearLayout editar;
        editar = view.findViewById(R.id.layoutEditar);
        Button btnLogout = view.findViewById(R.id.btnSair);

        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nome = view.findViewById(R.id.NickName);
        email = view.findViewById(R.id.email);


        FirebaseAuth autenticator = FirebaseAuth.getInstance();
        if(autenticator.getCurrentUser().getPhotoUrl() != null){
            RequestOptions options = new RequestOptions()
                    .centerCrop() // Garante que a imagem preencha o espaço
                    .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

            Glide.with(this)
                    .load(autenticator.getCurrentUser().getPhotoUrl())
                    .apply(options)
                    .into(fotoPerfil);
        }
        if(autenticator.getCurrentUser().getDisplayName() != null){

            nome.setText(autenticator.getCurrentUser().getDisplayName());
        }
        if(autenticator.getCurrentUser().getEmail() != null){

            email.setText(autenticator.getCurrentUser().getEmail());
        }









        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfile(view);
            }
        });
        graphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGraphic(view);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        novoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewProduct(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void goToEditProfile(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfilProfissional.class);
        startActivity(intent);
    }

    private void goToNewProduct(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, AdicionarProduto.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void goToGraphic(View view) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, GraficoFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void logout(View view) {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        autenticator.signOut();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);

    }
}
