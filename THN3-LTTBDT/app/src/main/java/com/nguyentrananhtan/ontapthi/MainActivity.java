package com.nguyentrananhtan.ontapthi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyentrananhtan.ontapthi.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
//    private Button btnDraw;
//    private EditText editText;
//    private LinearLayout container;
    private Handler handler;
    private static final int DRAWBUTTON = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
//                switch (msg.what) {
//                    case DRAWBUTTON:
//                        drawButtons(Integer.parseInt(msg.obj.toString()));
//                        break;
//                }
                if(msg.what == 1) drawButtons(Integer.parseInt(msg.obj.toString()));
                return true;
            }
        });

        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numRows = Integer.parseInt(binding.editText.getText().toString());
                Message msg = new Message();
                msg.what = DRAWBUTTON;
                msg.obj = numRows;
                handler.sendMessage(msg);
            }
        });


    }

    private void drawButtons(int numRows) {
        binding.container.removeAllViews();

        for (int i = 0; i < numRows; i++) {
            LinearLayout rowLayout = createNewLinearLayout();

            int numButtons = 2;
            float button1Ratio;
            float button2Ratio;
            if (i % 2 == 0 ) {
                button1Ratio = 2.0f;
                button2Ratio = 1.0f;
            } else {
                button1Ratio = 1.0f;
                button2Ratio = 2.0f;
            }

            Button button1 = new Button(MainActivity.this);
            int randomNumber1 = new Random().nextInt(10);
            button1.setText(String.valueOf(randomNumber1));
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams1.weight = button1Ratio;
            layoutParams1.setMargins(15,15,15,15);
            button1.setLayoutParams(layoutParams1);
            if (randomNumber1 % 2 == 0) {
                button1.setBackgroundColor(Color.GREEN);
            } else {
                button1.setBackgroundColor(Color.GRAY);
            }
            rowLayout.addView(button1);

            Button button2 = new Button(MainActivity.this);
            int randomNumber2 = new Random().nextInt(10);
            button2.setText(String.valueOf(randomNumber2));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.weight = button2Ratio;
            layoutParams2.setMargins(15,15,15,15);
            button2.setLayoutParams(layoutParams2);
            if (randomNumber2 % 2 == 0) {
                button2.setBackgroundColor(Color.GREEN);
            } else {
                button2.setBackgroundColor(Color.GRAY);
            }
            rowLayout.addView(button2);

            binding.container.addView(rowLayout);
        }
    }

    private LinearLayout createNewLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

}