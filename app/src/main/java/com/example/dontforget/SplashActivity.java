package com.example.dontforget;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView splashText;
    private int Back = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashText = findViewById(R.id.Splash_Text);

        //Methods
        Animate();
    }

    private void Animate() {
        ObjectAnimator.ofFloat(splashText, View.ALPHA, 0f, 1f).setDuration(3000).start();
        final Handler Going_to_next = new Handler();
        Going_to_next.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Back >= 2) {
                    finish();
                } else {
                    NextActivity();
                }
            }
        }, 3000);
    }

    private void NextActivity() {
        Intent Next = new Intent(getApplicationContext(), Navigation.class);
        Next.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(Next);
        finish();
    }

    @Override
    public void onBackPressed() {
        Handler BackFunctions = new Handler();
        if (Back == 0) {
            Toast.makeText(getApplicationContext(), "Press Again to Exit", Toast.LENGTH_SHORT).show();
            Back++;
            BackFunctions.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Back >= 0)
                        Back = 0;
                }
            }, 3000);
        }
        else if (Back >= 1){
            Back++;
            super.onBackPressed();
        }
    }
}
