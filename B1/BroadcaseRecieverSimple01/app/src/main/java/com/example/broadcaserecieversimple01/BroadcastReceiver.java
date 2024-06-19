package com.example.broadcaserecieversimple01;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.broadcaserecieversimple01.databinding.ActivityBroadcastReceiverBinding;
import com.example.broadcaserecieversimple01.databinding.ActivityMainBinding;

public class BroadcastReceiver extends AppCompatActivity {

    ActivityBroadcastReceiverBinding binding;

    android.content.BroadcastReceiver receiver = new android.content.BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()) {
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    binding.imvState.setImageResource(R.drawable.round_wifi_24);
                    binding.tvState.setText("Connected with WIFI");
                }
                else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    binding.imvState.setImageResource(R.drawable.round_4g_plus_mobiledata_24);
                    binding.tvState.setText("Connected with Mobile Data");
                }
            } else {
                binding.imvState.setImageResource(R.drawable.round_do_disturb_alt_24);
                binding.tvState.setText("No internet connection");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_broadcast_receiver);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityBroadcastReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}