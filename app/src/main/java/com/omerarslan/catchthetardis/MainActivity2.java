package com.omerarslan.catchthetardis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        level = 0;
    }

    public void selectLevel(View view){
        switch (view.getId()){
            case R.id.btnEasy:
                level = 1000;
                break;
             case R.id.btnNormal:
                level = 500;
                break;
             case R.id.btnHard:
                level = 250;
                break;
        }
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        intent.putExtra("difficultyLevel",level);
        startActivity(intent);
    }
}