//package com.smartL.mad.commonUtilDevice;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.TimePicker;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import com.smartL.mad.DbHandler;
//import com.smartL.mad.R;
//
//import java.util.Calendar;
//
//public class Shedule extends AppCompatActivity {
//
//    Intent intent;
//    Button buttonCancel;
//    Button buttonOk;
//    private TimePicker alarmTimePickerFrom;
//    private TimePicker alarmTimePickerTo;
//    private PendingIntent pendingIntentAlarm;
//    private AlarmManager alarmManagerFrom;
//    private AlarmManager alarmManagerTo;
//    private Intent intSheduleAControlle;
//    private ToggleButton t1,t2,t3,t4,t5,t6,t7;
//    private DbHandler dbHandler;
//    private    int s,m,t,w,th,f,sa;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        setContentView(R.layout.activity_shedule);
//
//        getSupportActionBar().hide();
//        getWindow().setStatusBarColor(getResources().getColor(R.color.newActionBar));
//
//        dbHandler = new DbHandler(this);
//        intent = new Intent(this, Controller.class);
//        buttonCancel = findViewById(R.id.sheduleCancel);
//        buttonOk=findViewById(R.id.seduleOk);
//
//        t1=findViewById(R.id.su);
//        t2=findViewById(R.id.mo);
//        t3=findViewById(R.id.tu);
//        t4=findViewById(R.id.we);
//        t5=findViewById(R.id.th);
//        t6=findViewById(R.id.fr);
//        t7=findViewById(R.id.sa);
//
//
//        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    s=1;
//                    t1.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t1.setBackgroundColor(R.drawable.btnsheduledisable);
//                    s=0;
//                }
//            }
//        });
//
//        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    m=1;
//                    t2.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t2.setBackgroundColor(R.drawable.btnsheduledisable);
//                    m=0;
//                }
//            }
//        });
//
//        t3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    t=1;
//                    t3.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t3.setBackgroundColor(R.drawable.btnsheduledisable);
//                    t=0;
//                }
//            }
//        });
//
//        t4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    w=1;
//                    t4.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t4.setBackgroundColor(R.drawable.btnsheduledisable);
//                    w=0;
//                }
//            }
//        });
//
//        t5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    th=1;
//                    t5.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t5.setBackgroundColor(R.drawable.btnsheduledisable);
//                    th=0;
//                }
//            }
//        });
//
//        t6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    f=1;
//                    t6.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t6.setBackgroundColor(R.drawable.btnsheduledisable);
//                    f=0;
//                }
//            }
//        });
//
//        t7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    sa=1;
//                    t7.setBackgroundColor(R.drawable.mbtnpattern);
//                }else{
//                    t7.setBackgroundColor(R.drawable.btnsheduledisable);
//                    sa=0;
//                }
//            }
//        });
//
//
//        alarmTimePickerFrom = (TimePicker) findViewById(R.id.timePickerFrom);
//        alarmTimePickerTo = (TimePicker) findViewById(R.id.timePickerTo);
//        alarmManagerFrom = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManagerTo = (AlarmManager) getSystemService(ALARM_SERVICE);
//        intSheduleAControlle = new Intent(Shedule.this, SheduleAlarmControll.class);
//
//
//
//        pendingIntentAlarm = PendingIntent.getBroadcast(Shedule.this, 1, intSheduleAControlle, 0);
//
//
//
//
//
//
//
//
//
//        buttonOk.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        long time;
//                        Toast.makeText(Shedule.this, "Shedule Set", Toast.LENGTH_SHORT).show();
//
//                        Calendar calendarFrom = Calendar.getInstance();
//                        calendarFrom.set(Calendar.HOUR_OF_DAY, alarmTimePickerFrom.getCurrentHour());
//                        calendarFrom.set(Calendar.MINUTE, alarmTimePickerFrom.getCurrentMinute());
//
//                        Calendar calendarTo = Calendar.getInstance();
//                        calendarTo.set(Calendar.HOUR_OF_DAY, alarmTimePickerTo.getCurrentHour());
//                        calendarTo.set(Calendar.MINUTE, alarmTimePickerTo.getCurrentMinute());
//
//
//                        time = (calendarFrom.getTimeInMillis() - (calendarFrom.getTimeInMillis() % 60000));
//                        Log.d("error", (String.valueOf(time).toString()));
//                        if (System.currentTimeMillis() > time) {
//                            if (calendarFrom.AM_PM == 0) {
//                                time = time + (1000 * 60 * 60 * 12);
//                            } else {
//                                time = time + (1000 * 60 * 60 * 24);
//                            }
//                        }
//
//String query="";
//
//                        alarmManagerFrom.setRepeating(AlarmManager.RTC_WAKEUP, time,24*60*60*1000 ,pendingIntentAlarm);
//                        alarmManagerTo.setRepeating(AlarmManager.RTC_WAKEUP, time,24*60*60*1000 ,pendingIntentAlarm);
//
//
//
//                    }
//                });
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
//                        startActivity(intent);
//                    }
//                }
//        );
//
//
//    }
//
//    public void onBackPressed() {
//        startActivity(intent);
//    }
//}
