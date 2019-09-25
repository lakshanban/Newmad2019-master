package com.smartL.mad;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.smartL.mad.commonUtilDevice.AlarmReciver;

public class AlarmManager extends AppCompatActivity {


    private PendingIntent pendingIntent;
    private android.app.AlarmManager alarmManager;
    private Intent intentalarm;
    private   DbHandler dbHandler ;;
    public AlarmManager(){



    }

    public  Boolean setAlarm(String id,long time){

        alarmManager = (android.app.AlarmManager) getSystemService(ALARM_SERVICE);

        intentalarm = new Intent(AlarmManager.this, AlarmReciver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmManager.this, 1, intentalarm, 0);
        intentalarm.putExtra("DiviceID",id);
        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, time, pendingIntent);
        return true;
    }

    public  Boolean offAlarm(String id){
        dbHandler = new DbHandler(this);
        String query=null;

        if (id.startsWith("r")){
            query ="UPDATE SmartBulbs SET Bulbstate=1 where BulbID='"+id+"'";
        }else if(id.startsWith("n")){
            query ="UPDATE SmartBulbs SET Bulbstate=1 where BulbID='"+id+"'";
        }else if (id.startsWith("p")){
            query ="UPDATE SmartBulbs SET Bulbstate=1 where BulbID='"+id+"'";
        }

        dbHandler.iudFuntion(query);
        return true;
    }

}
