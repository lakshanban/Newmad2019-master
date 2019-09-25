//package com.smartL.mad.commonUtilDevice;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.smartL.mad.AlarmManager;
//
//public class SheduleAlarmControll extends BroadcastReceiver {
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent();
//        String did= intent.getStringExtra("DiviceID");
//        AlarmManager alarmManager = new AlarmManager();
//        alarmManager.offAlarm(did);
//
//        Log.d("error", "Alarm wadinawa");
//    }
//}