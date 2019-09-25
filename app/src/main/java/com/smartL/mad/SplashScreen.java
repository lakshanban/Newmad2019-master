package com.smartL.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.smartL.mad.laky.LoginLaky;
import com.smartL.mad.user.Register;

public class SplashScreen extends AppCompatActivity {

    public static int welcome_time_out=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window= getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent welcome = new Intent(SplashScreen.this, Register.class);
                Intent welcome = new Intent(SplashScreen.this, LoginLaky.class);
                startActivity(welcome);
                finish();
            }
        },welcome_time_out);


    }
}
