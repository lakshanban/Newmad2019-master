package com.smartL.mad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.smartL.mad.commonUtilDevice.DevicesCatogory;
import com.smartL.mad.laky.FirebaseHandler;
import com.smartL.mad.recyclerView.MainDevicesHome;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceContoll extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private DrawerLayout drawerLayout;
    private Button button;
    private TextView textView;
    private String[] words;
    //

    private DbHandler dbHandler;
    private FirebaseHandler fire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_contoll);
        textView= (TextView) findViewById(R.id.resp);
        dbHandler = new DbHandler(this);

        fire = new FirebaseHandler(this);

        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window= getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
        button=findViewById(R.id.voicebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voiceautomation();
            }
        });
        //    trigerAction("office bulb","off");
    }
    private  void voiceautomation(){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Tell Me");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK&&data!=null){
            ArrayList<String> arrayList =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //  textView.setText(arrayList.get(0));
            // Log.d("errorvoice","speak "+arrayList.get(0));
            try {



                if (arrayList.get(0).toString().startsWith("turn on")){
                    //     Log.d("errorvoice","turn on");
                    words=arrayList.get(0).toString().split("turn on");
                    trigerAction(words[1] ,"on");
                    //   Log.d("errorvoice",words[1]);
                }else if (arrayList.get(0).toString().startsWith("turn off")){
                    //    Log.d("errorvoice","turn off");
                    words=arrayList.get(0).toString().split("turn off");
                    trigerAction(words[1] ,"off");
                    //     Log.d("errorvoice",words[1]);
                }else if (arrayList.get(0).toString().endsWith("on")){
                    //    Log.d("errorvoice","on");
                    words=arrayList.get(0).toString().split("on");
                    trigerAction(words[0] ,"on");
                    //     Log.d("errorvoice",words[0]);
                }else if (arrayList.get(0).toString().endsWith("off")){
                    //     Log.d("errorvoice","off");
                    words=arrayList.get(0).toString().split("off");
                    trigerAction(words[0] ,"off");
                    ///    Log.d("errorvoice",words[0]);
                }else if (arrayList.get(0).toString().endsWith("turn on")){
                    ///  Log.d("errorvoice","on");
                    words=arrayList.get(0).toString().split("turn on");
                    trigerAction(words[0] ,"on");
                    Log.d("errorvoice",words[0]);
                }else if (arrayList.get(0).toString().endsWith("turn off")){
                    //     Log.d("errorvoice","off");
                    words=arrayList.get(0).toString().split("turn off");
                    trigerAction(words[0] ,"off");
                    //    Log.d("errorvoice",words[0]);
                }else if (arrayList.get(0).toString().startsWith("on")){
                    //      Log.d("errorvoice","on");
                    words=arrayList.get(0).toString().split("on");
                    trigerAction(words[1] ,"on");
                    //    Log.d("errorvoice",words[1]);
                }else if (arrayList.get(0).toString().startsWith("off")){
                    Log.d("errorvoice","off");
                    words=arrayList.get(0).toString().split("off");
                    trigerAction(words[1] ,"off");
                    //      Log.d("errorvoice",words[1]);
                }






            }catch (Exception e){
                //   Log.d("errorvoice","voice "+e);
            }



        }
    }
    private void trigerAction(String dvice ,String actn){

        DevicesCatogory devicesCatogory = new DevicesCatogory();
        String did="";
        String query2;
        Cursor cursor;

        try {
            query2="SELECT * From `Devices` where `nickname` LIKE '%"+dvice.toString().trim()+"%'";//WHERE  deviceName like '"+dvice.trim()+"'
            //   Log.d("errorvoice","speak "+query2);
            cursor =dbHandler.getOneRaw(query2);
            if (cursor.moveToFirst()){




                did = cursor.getString(1);
                Log.d("errorvoice","Device id "+did);

                String query;
                if (actn.trim().equals("on")) {
                    if (devicesCatogory.isRgb(did)) {
                        //    Log.d("errorvoice","Rgb id "+did);
                        query = "UPDATE SmartBulbs SET Bulbstate=1 where BulbID='" + did + "'";
                        dbHandler.iudFuntion(query);
                        fire.updateStatus(did, 1);
                        textView.setText("Okey Turning on "+dvice);
                    } else {
                        //    Log.d("errorvoice","ndevice id "+did);
                        query = "UPDATE SmartDevice SET Devicestate=1 where DeviceID='" + did + "'";
                        dbHandler.iudFuntion(query);
                        fire.updateStatus(did, 1);
                        textView.setText("Okey Turning on "+dvice);
                    }


                } else if (actn.trim().equals("off")) {
                    if (devicesCatogory.isRgb(did)) {
                        // Log.d("errorvoice","Rgb id "+did);
                        query = "UPDATE SmartBulbs SET Bulbstate=0 where BulbID='" + did + "'";
                        dbHandler.iudFuntion(query);
                        fire.updateStatus(did, 0);
                        textView.setText("Okey Turning off "+dvice);
                    } else {
                        //    Log.d("errorvoice","n device id "+did);
                        query = "UPDATE SmartDevice SET Devicestate=0 where DeviceID='" + did + "'";
                        dbHandler.iudFuntion(query);
                        fire.updateStatus(did, 0);
                        textView.setText("Okey Turning off "+dvice);
                    }

                }
            }
        }catch (Exception e){
            // Log.d("errorvoice","query ex "+e);
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, MainDevicesHome.class));
    }
}
