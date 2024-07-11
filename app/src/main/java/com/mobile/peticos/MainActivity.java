package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import com.mobile.peticos.R;

import com.mobile.peticos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//
//        binding.bottomNavigationView.setOnItemSelectedListener(
//                item -> {
//                    switch (item.getItemId()) {
//                        case R.id.navHome:
//                            // Handle navHome action
//                            break;
//                        case R.id.navVakinhas:
//                            // Handle navVakinhas action
//                            break;
//                        case R.id.navPerdidos:
//                            // Handle navPerdidos action
//                            break;
//                        case R.id.navLocal:
//                            // Handle navLocal action
//                            break;
//                        case R.id.navPerfil:
//                            // Handle navPerfil action
//                            break;
//                        default:
//                            return false;
//                    }
//                    return true;
//                }
//        );

    }
}