package com.smartL.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.smartL.mad.commonUtilDevice.Controller;
import com.smartL.mad.laky.LoginLaky;
import com.smartL.mad.recyclerView.MainDevicesHome;
import com.smartL.mad.user.Register;


public class MainActivity extends AppCompatActivity {

    Intent intentContoller;
    Intent intentPlug;
    Intent register;
    Intent devicesHome;
    Intent splashScreen;
    Intent lakyLogin;

    Button buttonContoller;
    Button buttonPlug;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intentContoller = new Intent(this, Controller.class);
        //intentPlug = new Intent(this, Plug.class);
        //register = new Intent(this, Controller.class);
        buttonPlug = findViewById(R.id.buttonMainPlug);
        buttonContoller = findViewById(R.id.buttonMainBulb);
        //startActivity(register);

        //devicesHome = new Intent(this, MainDevicesHome.class);
        //lakyLogin = new Intent(this, LoginLaky.class);
        splashScreen = new Intent(this, SplashScreen.class);

        startActivity(splashScreen);
        finish();


//        buttonContoller.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        startActivity(intentContoller);
//                    }
//                });
//
//        buttonPlug.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        startActivity(intentPlug);
//                    }
//                });




    }

}
