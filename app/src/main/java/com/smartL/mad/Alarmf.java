package com.smartL.mad;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.smartL.mad.commonUtilDevice.AlarmReciver;
import com.smartL.mad.commonUtilDevice.DevicesCatogory;

import java.util.Calendar;


public class Alarmf extends Fragment {

    private Intent intent;
    private Button buttonOk;
    private Button buttonCancel;
    private TimePicker alarmTimePicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Intent intentalarm;
    private TextView textView;
    private String id,query;
    private DbHandler dbHandler;
    private Cursor cursor;
    private DevicesCatogory devicesCatogory;
    private int h,m;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_alarmf, container, false);
        dbHandler = new DbHandler(getActivity());
        devicesCatogory = new DevicesCatogory();
        buttonCancel = view.findViewById(R.id.AlarmCancel);
        buttonOk = view.findViewById(R.id.AlarmOk);

        textView = view.findViewById(R.id.alarmset);
        alarmTimePicker = (TimePicker) view.findViewById(R.id.altimePicker);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);




        try {
            Intent intent = getActivity().getIntent();
            id=intent.getStringExtra("id_ReserveByDeviceAdapter").trim();
            Log.d("error",id);
        }catch (Exception e){
            Log.d("error","Intetn eke awulk "+e);
        }
        intentalarm = new Intent(getActivity().getApplication(), AlarmReciver.class);
        intentalarm.putExtra("did",id);
        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplication(), 1, intentalarm, 0);

try {
    if (!devicesCatogory.isRgb(id)){
        Log.d("error","is nomal  ");
        query ="Select * from SmartDevice where DeviceID='"+id+"'";

        cursor = dbHandler.getOneRaw(query);
        cursor.moveToFirst();
        if (cursor.getString(4).equals("false")){
            cancelAlarm();
        }else{
            setAlarm();
        }
    }else{
        Log.d("error","is rgb ");
        query="Select * from SmartBulbs where BulbID='"+id+"'";
        cursor = dbHandler.getOneRaw(query);
        cursor.moveToFirst();
        if (cursor.getString(10).equals("false")){
          cancelAlarm();
            Log.d("error","cancel"+cursor.getString(10));
        }else{
            setAlarm();
            String t[]= cursor.getString(10).split("/");
            h=Integer.parseInt(t[0]);
            m=Integer.parseInt(t[1]);
            Log.d("error","set alarm"+cursor.getString(10));
            Log.d("error","Intetn eke awulk ");
        }

    }

}catch (Exception e){
    Log.d("error","alarm awul "+e);
}














        buttonOk.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long time;
                        Toast.makeText(getActivity(), "ALARM ON", Toast.LENGTH_SHORT).show();

                        Calendar calendar = Calendar.getInstance();
                         h=alarmTimePicker.getCurrentHour();
                         m=alarmTimePicker.getCurrentMinute();
                        calendar.set(Calendar.HOUR_OF_DAY,h );
                        calendar.set(Calendar.MINUTE,m );
                        Log.d("error", alarmTimePicker.getCurrentHour().toString());
                        Log.d("error", alarmTimePicker.getCurrentMinute().toString());
                        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                        Log.d("error", h+" "+m);
                        if (System.currentTimeMillis() > time) {
                            if (calendar.AM_PM == 0) {
                                time = time + (1000 * 60 * 60 * 12);
                            } else {
                                time = time + (1000 * 60 * 60 * 24);
                            }
                        }
                        String colock= String.valueOf(h)+"/"+String.valueOf(m);
                        updatedb(colock,id);

                        setAlarm();
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);


                    }
                });

        buttonCancel.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatedb("false",id);
                        alarmManager.cancel(pendingIntent);
                        Toast.makeText(getActivity(), "ALARM Cancel", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "Alarm cancel");
                        cancelAlarm();

                    }
                });



        return  view;
    }
private void setAlarm(){
    buttonOk.setEnabled(false);
    buttonOk.setBackgroundResource(R.drawable.btnsheduledisable);
    buttonCancel.setEnabled(true);
    buttonCancel.setBackgroundResource(R.drawable.btnpattern);
    if(h>12){
        textView.setText("Alarm is Set "+(h-12)+" : "+m+" PM");
    }else{
        textView.setText("Alarm is Set "+(h)+" : "+m+" AM");
    }
    alarmTimePicker.setEnabled(false);
}
private void cancelAlarm(){

    alarmTimePicker.setEnabled(true);
    buttonOk.setEnabled(true);
    buttonOk.setBackgroundResource(R.drawable.btnpattern);
    buttonCancel.setEnabled(false);
    buttonCancel.setBackgroundResource(R.drawable.btnsheduledisable);

    textView.setText("Alarm is not Set");
    alarmTimePicker.setEnabled(true);
}
private void updatedb(String value,String id){
        String query;
        if (devicesCatogory.isRgb(id)){
            query="UPDATE SmartBulbs SET alarmSet='"+value+"' where BulbID='"+id+"'";
        }else{
            query="UPDATE SmartDevice SET alarmSet='"+value+"' where DeviceID='"+id+"' ";
        }
        dbHandler.iudFuntion(query);


}
}
