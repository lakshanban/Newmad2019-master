//package com.smartL.mad.commonUtilDevice;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.smartL.mad.R;
//
//import java.util.Calendar;
//
//public class Alarm extends AppCompatActivity {
//
//    private Intent intent;
//    private Button buttonOk;
//    private Button buttonCancel;
//    private TimePicker alarmTimePicker;
//    private PendingIntent pendingIntent;
//    private AlarmManager alarmManager;
//    private Intent intentalarm;
//    private TextView textView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm);
//
//        getSupportActionBar().hide();
//        getWindow().setStatusBarColor(getResources().getColor(R.color.newActionBar));
//
//        intent = new Intent(this, Controller.class);
//        buttonCancel = findViewById(R.id.AlarmCancel);
//        buttonOk = findViewById(R.id.AlarmOk);
//        textView = findViewById(R.id.alarmset);
//        alarmTimePicker = (TimePicker) findViewById(R.id.altimePicker);
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        intentalarm = new Intent(Alarm.this, AlarmReciver.class);
//        pendingIntent = PendingIntent.getBroadcast(Alarm.this, 1, intentalarm, 0);
//
//
//
//
//
//
//
//
//        buttonCancel.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(Alarm.this, "ALARM Cancel", Toast.LENGTH_SHORT).show();
//                        alarmTimePicker.setEnabled(true);
//                        buttonOk.setEnabled(true);
//                        buttonOk.setBackgroundColor(getResources().getColor(R.color.btn_color));
//                        buttonCancel.setEnabled(false);
//                        buttonCancel.setBackgroundColor(getResources().getColor(R.color.btn_disable));
//                        alarmManager.cancel(pendingIntent);
//                        textView.setText("Alarm is not Set");
//                        Log.d("Error", "Alarm cancel");
//
//                    }
//                });
//
//        buttonOk.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        long time;
//                        Toast.makeText(Alarm.this, "ALARM ON", Toast.LENGTH_SHORT).show();
//
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//                        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//                        Log.d("error", alarmTimePicker.getCurrentHour().toString());
//                        Log.d("error", alarmTimePicker.getCurrentMinute().toString());
//                        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
//                        Log.d("error", (String.valueOf(time).toString()));
//                        if (System.currentTimeMillis() > time) {
//                            if (calendar.AM_PM == 0) {
//                                time = time + (1000 * 60 * 60 * 12);
//                            } else {
//                                time = time + (1000 * 60 * 60 * 24);
//                            }
//                        }
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
//
//                        buttonOk.setEnabled(false);
//                        buttonOk.setBackgroundColor(getResources().getColor(R.color.btn_disable));
//                        buttonCancel.setEnabled(true);
//                        buttonCancel.setBackgroundColor(getResources().getColor(R.color.btn_color));
//                        textView.setText("Alarm is Set");
//
//
//                    }
//                });
//    }
//
//    public void onBackPressed() {
//        startActivity(intent);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (true) {//alarm set karal nm danata
//            alarmTimePicker.setEnabled(false);
//            buttonOk.setEnabled(false);
//            buttonCancel.setEnabled(true);
//            buttonCancel.setBackgroundColor(getResources().getColor(R.color.btn_color));
//            buttonOk.setBackgroundColor(getResources().getColor(R.color.btn_disable));
//            textView.setText("Alarm is Set");
//        } else {
//
//            buttonCancel.setEnabled(false);
//            buttonOk.setEnabled(true);
//            buttonOk.setBackgroundColor(getResources().getColor(R.color.btn_color));
//            buttonCancel.setBackgroundColor(getResources().getColor(R.color.btn_disable));
//            textView.setText("Alarm is not Set");
//        }
//
//
//    }
//}
