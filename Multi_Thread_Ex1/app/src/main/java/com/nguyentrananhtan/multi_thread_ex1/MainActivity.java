package com.nguyentrananhtan.multi_thread_ex1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyentrananhtan.multi_thread_ex1.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Random random = new Random();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            int percent = msg.arg1;
            int randNumb = (int) msg.obj;
            binding.txtPercent.setText(percent + "%"); // so + chuoi = chuoi
            binding.pbPercent.setProgress(percent);

            ImageView imageView = new ImageView(MainActivity.this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200,ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            //imageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
            //binding.containerLayout.addView(imageView);

//            if(randNumb %2 == 0){
//                imageView.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
//            }
//            else imageView.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
//            binding.containerLayout.addView(imageView);

//            LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    4f
//            );


            Button button = new Button(MainActivity.this);
            button.setLayoutParams(params);
            button.setTextSize(24);
            button.setText(String.valueOf(randNumb));
            if(randNumb %2 == 0){
                button.setBackgroundColor(Color.BLUE);
            }
            else{
                button.setBackgroundColor(Color.GRAY);
            }
            binding.containerLayout.addView(button);

            if(percent == 100){
                binding.txtPercent.setText("DONE!!");
            }

            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw();
            }
        });
    }

    private void draw() {
        // Background/Worker - Thread
        int numOdView = Integer.parseInt(binding.idtNumOfView.getText().toString());
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=numOdView;i++){
//                    Message message = handler.obtainMessage();
//
//                    message.arg1 = i*100/numOdView; //percent
//                    message.obj = random.nextInt(100); // random number
//
//                    handler.sendMessage(message);
                    binding.txtPercent.setText(i*100/numOdView + "%"); // so + chuoi = chuoi
                    binding.pbPercent.setProgress(i*100/numOdView);

                    SystemClock.sleep(100);
                    if(i*100/numOdView == 100){
                        binding.txtPercent.setText("DONE!!");
                    }
                }
            }
        });
//        Thread backgroundThread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int i=1;i<=numOdView;i++){
////                    Message message = handler.obtainMessage();
////
////                    message.arg1 = i*100/numOdView; //percent
////                    message.obj = random.nextInt(100); // random number
////
////                    handler.sendMessage(message);
//                    binding.txtPercent1.setText(i*100/numOdView + "%"); // so + chuoi = chuoi
//                    binding.pbPercent1.setProgress(i*100/numOdView);
//
//                    SystemClock.sleep(100);
//                    if(i*100/numOdView == 100){
//                        binding.txtPercent1.setText("DONE!!");
//                    }
//                }
//            }
//        });
        backgroundThread.start();
//        backgroundThread1.start();
    }
}