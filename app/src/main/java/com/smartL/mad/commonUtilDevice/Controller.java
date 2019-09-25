package com.smartL.mad.commonUtilDevice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smartL.mad.Alarmf;
import com.smartL.mad.Controllerf;
import com.smartL.mad.DbHandler;
import com.smartL.mad.Editf;
import com.smartL.mad.MainActivity;
import com.smartL.mad.Moreoptionf;
import com.smartL.mad.NoteAvailable;
import com.smartL.mad.Plugf;
import com.smartL.mad.R;
import com.smartL.mad.recyclerView.MainDevicesHome;

public class Controller extends AppCompatActivity {

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private String id;
    private String update;
    private DbHandler dbHandler;
    private DevicesCatogory devicesCatogory;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
dbHandler = new DbHandler(this);

        try {
            Intent intent =getIntent();
           id=intent.getStringExtra("id_ReserveByDeviceAdapter").trim();
         //   id="S!-RGB15555";
        }catch (Exception e){
            Log.d("error",""+e);
        }




        devicesCatogory =new DevicesCatogory();

        if (!devicesCatogory.isRgb(id)){
            Fragment fragment;
            fragment= new Plugf();
            FragmentManager fragmentManager =getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fgmntDefault,fragment);
            fragmentTransaction.commit();
        }



        getSupportActionBar().hide();
        getWindow().setBackgroundDrawableResource(R.drawable.gradientcolor);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window= getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }



        getWindow().setStatusBarColor(getResources().getColor(R.color.newActionBar));

        Button button;



        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);

        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changefun();
                        b1.setBackgroundResource(R.drawable.homew);
                        b2.setBackgroundResource(R.drawable.alarm);
                        b3.setBackgroundResource(R.drawable.more);
                        b4.setBackgroundResource(R.drawable.setting);
                        Fragment fragment1;

                        if (!devicesCatogory.isRgb(id)){
                            fragment1= new Plugf();
                        }else{
                            fragment1= new Controllerf();
                        }
                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fgmntDefault,fragment1);
                        fragmentTransaction.commit();
                    }
                }
        );
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changefun();
                        Fragment fragment;
                        fragment= new Alarmf();
                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fgmntDefault,fragment);
                        fragmentTransaction.commit();
                        b1.setBackgroundResource (R.drawable.home);
                        b2.setBackgroundResource (R.drawable.alarmw);
                        b3.setBackgroundResource (R.drawable.more);
                        b4.setBackgroundResource (R.drawable.setting);
                    }
                }
        );

        b3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment;
                        if (devicesCatogory.isRgb(id)){

                            fragment= new Moreoptionf();
                        }else {
                            fragment= new NoteAvailable();
                        }

                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fgmntDefault,fragment);
                        fragmentTransaction.commit();
                        b1.setBackgroundResource (R.drawable.home);
                        b3.setBackgroundResource (R.drawable.morew);
                        b2.setBackgroundResource (R.drawable.alarm);
                        b4.setBackgroundResource (R.drawable.setting);
                    }
                }
        );

        b4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changefun();
                        Fragment fragment;
                        fragment= new Editf();
                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fgmntDefault,fragment);
                        fragmentTransaction.commit();
                        b4.setBackgroundResource (R.drawable.settingw);
                        b2.setBackgroundResource (R.drawable.alarm);
                        b3.setBackgroundResource (R.drawable.more);
                        b1.setBackgroundResource (R.drawable.home);
                    }
                }
        );














    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainDevicesHome.class));
        finish();
        changefun();

    }

public void changefun(){
    update="UPDATE SmartBulbs SET BuLBfuntion=0 where BulbID='"+id+"'";
    dbHandler.iudFuntion(update);
}
}
