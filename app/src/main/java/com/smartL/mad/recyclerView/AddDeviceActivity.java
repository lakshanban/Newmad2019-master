package com.smartL.mad.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartL.mad.DbHandler;

public class AddDeviceActivity extends AppCompatActivity {


    private DbHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.add_device);
        //////////////////////////////
        Intent intent1 = getIntent();
        String deviceid = intent1.getStringExtra("deviceIdQr");
        String nickname = intent1.getStringExtra("nicknameQr");
        if(deviceid!=null) {
            saveDevice(deviceid,nickname);
            Toast.makeText(this, "Geting intent", Toast.LENGTH_LONG).show();
        }
        /////////////////////////////
    }

    public void saveDevice(String deviceid, String nickName){


        String id = deviceid;
        //String name = checkDeviceType(Integer.parseInt(id));
        //int rid = Integer.parseInt(id);
        String name = checkDeviceType(id);
        String nickname = nickName;
        String image = "wert";
        dbHelper = new DbHandler(this);


        //create new device
        Device device = new Device(id, name, nickname, image);
        dbHelper.addNewDevice(device);



        goBackHome();
    }

    private String checkDeviceType(String did){
        String name = null;
        String x = did.substring(3,6);
        if(x.equals("PLG")){
            name = "Plug";
        }else if(x.equals("BLB")){
            name = "Bulb";
        }else if(x.equals("RGB")){
            name = "RGB Bulb";
        }




        return name;
    }


    private void goBackHome(){
        startActivity(new Intent(AddDeviceActivity.this, MainDevicesHome.class));
        finish();
    }

}
