package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createBChunAnNiu, showBZhanShiAnNiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find ID/Cha Zhao ID
        createBChunAnNiu = (Button) findVBIDChaZhaoID(R.id.create_button);
        //find ID/Cha Zhao ID
        showBZhanShiAnNiu = (Button) findVBIDChaZhaoID(R.id.show_button);

        createBChunAnNiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to create a new ad
                Intent intentChangjianBaogao = new Intent(MainActivity.this, CreateAcAdShangBao.class);
                startActivity(intentChangjianBaogao);
            }
        });

        showBZhanShiAnNiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to show all lost and found items
                Intent intentZhanShiBaoGao = new Intent(MainActivity.this, ShowAcBaoGao.class);
                startActivity(intentZhanShiBaoGao);
            }
        });
    }

    View findVBIDChaZhaoID(int id) {
        View nView = findViewById(id);
        return nView;
    }
}