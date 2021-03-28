package com.omerarslan.catchthetardis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    TextView scoreText;
    TextView bestScoreText;
    int score;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    int level;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        bestScoreText = findViewById(R.id.bestScoreText);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray = new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        hideImages();

        score = 0 ;

        Intent intent = getIntent();
        level = intent.getIntExtra("difficultyLevel",500);

        sharedPreferences = this.getSharedPreferences("com.omerarslan.catchthetardis", Context.MODE_PRIVATE);
        int bestScoreEasy = sharedPreferences.getInt("bestScoreEasy",0);
        int bestScoreNormal = sharedPreferences.getInt("bestScoreNormal",0);
        int bestScoreHard = sharedPreferences.getInt("bestScoreHard",0);

        switch (level){
            case 1000:
                bestScoreText.setText("Best score on easy mode: " + bestScoreEasy);
                break;
            case 500:
                bestScoreText.setText("Best score on normal mode: " + bestScoreNormal);
                break;
            case 250:
                bestScoreText.setText("Best score on hard mode: " + bestScoreHard);
                break;
        }

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time's Up!");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Tekrar oynamak ister misiniz?");
                alert.setMessage("Emin misiniz?");
                alert.setCancelable(false);
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //restart
                        Intent intent = getIntent();
                        finish();    //güncel aktiviteyi bitirecek
                        startActivity(intent);  //aynı aktiviteyi yeniden başlatacak
                    }
                });

                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent1);
                    }
                });
                alert.show();

                if (level == 1000 && bestScoreEasy < score){
                    sharedPreferences.edit().putInt("bestScoreEasy",score).apply();
                }else if(level == 500 && bestScoreNormal < score){
                    sharedPreferences.edit().putInt("bestScoreNormal",score).apply();
                }else if(level == 250 && bestScoreHard < score){

                    sharedPreferences.edit().putInt("bestScoreHard",score).apply();
                }
            }
        }.start();
    }

    public void increaseScore(View view){

        score++;

        scoreText.setText("Score: " + score);
    }

    public void hideImages(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this,level);
            }
        };
        handler.post(runnable);
    }
}