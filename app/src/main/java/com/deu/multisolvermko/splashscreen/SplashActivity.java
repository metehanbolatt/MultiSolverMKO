package com.deu.multisolvermko.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.deu.multisolvermko.R;
import com.deu.multisolvermko.authentication.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView2 = findViewById(R.id.imagePremiumLogo);

        int SPLASH_TIME_OUT = 600;
        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(SplashActivity.this, SignInActivity.class);
            startActivity(homeIntent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}