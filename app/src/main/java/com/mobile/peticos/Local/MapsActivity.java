package com.mobile.peticos.Local;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile.peticos.R;
import com.mobile.peticos.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ImageView btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            finish();
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        // Endereço específico a ser convertido em coordenadas
        //Intent intent = getIntent();
        //String location = intent.getStringExtra("local");
        String location = "Av. Raimundo Pereira de Magalhães, 1465 - Jardim Iris, São Paulo - SP, 05145-000";

        if (location != null && !location.isEmpty()) {
            // Aqui você pode usar o Geocoder para converter o endereço em coordenadas e exibir no mapa
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;

            try {
                addresses = geocoder.getFromLocationName(location, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // Adiciona um marcador com um ícone personalizado
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(location));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                } else {
                    Toast.makeText(this, "Localização não encontrada", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Erro ao buscar localização", Toast.LENGTH_SHORT).show();
            }
        }}


    }
