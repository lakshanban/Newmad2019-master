//package com.smartL.mad.commonUtilDevice;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
//import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
//import com.smartL.mad.DbHandler;
//import com.smartL.mad.MainActivity;
//import com.smartL.mad.R;
//import com.smartL.mad.commonUtilDevice.Alarm;
//import com.smartL.mad.commonUtilDevice.Shedule;
//import com.smartL.mad.recyclerView.MainDevicesHome;
//
//public class Plug extends AppCompatActivity {
//
//    Intent intentAlarm;
//    Intent intentShedule;
//    Intent devicesHome;
//
//    TextView detailstxt;
//    Intent intentgetDetails;
//
//    Button buttonOnOff;
//
//    Boolean isOn;
//String id;
//DbHandler dbHandler;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        setContentView(R.layout.activity_plug);
//        dbHandler = new DbHandler(this);
//        getSupportActionBar().hide();
//        getWindow().setStatusBarColor(getResources().getColor(R.color.newActionBar));
//        intentAlarm = new Intent(this, Alarm.class);
//        devicesHome = new Intent(this, MainDevicesHome.class);
//        intentShedule = new Intent(this, Shedule.class);
//
//
//        buttonOnOff = findViewById(R.id.plugOnOff);
//
//        detailstxt = (TextView) findViewById(R.id.textView3);
//        intentgetDetails = getIntent();
//        detailstxt.setText(" "+intentgetDetails.getStringExtra("nickName_ReserveByDeviceAdapter"));
//        //textviewId.setText("DeviceId: "+intent.getStringExtra("id_ReserveByDeviceAdapter"));
//
//        ImageView icon = new ImageView(this); // Create an icon
//        icon.setImageDrawable(getDrawable(R.drawable.moreic));
//
//        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
//                .setContentView(icon)
//
//                .build();
//
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//// repeat many times:
//
//        ImageView itemIcon1 = new ImageView(this);
//        itemIcon1.setImageDrawable(getDrawable(R.drawable.alarmon));
//        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
//
//        ImageView itemIcon2 = new ImageView(this);
//        itemIcon2.setImageDrawable(getDrawable(R.drawable.scheduleoff));
//        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
//
//
//        ImageView itemIcon3 = new ImageView(this);
//        itemIcon3.setImageDrawable(getDrawable(R.drawable.back));
//        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
//
//
//        SubActionButton.LayoutParams layoutParams1 = (SubActionButton.LayoutParams) button3.getLayoutParams();
//        layoutParams1.width = 225;
//        layoutParams1.height = 225;
//        layoutParams1.bottomMargin = 40;
//        layoutParams1.rightMargin = 100;
//
//
//        FloatingActionButton.LayoutParams layoutParams2 = (FloatingActionButton.LayoutParams) actionButton.getLayoutParams();
//        layoutParams2.width = 250;
//        layoutParams2.rightMargin = 75;
//
//        layoutParams2.bottomMargin = 75;
//        layoutParams2.height = 250;
//
//        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).setRadius(460)
//                .addSubActionView(button1)
//                .addSubActionView(button2)
//                .addSubActionView(button3)
//
//                .attachTo(actionButton)
//
//                .build();
//        itemIcon1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("error", "button1click");
//                if (isOn) {
//                    startActivity(intentAlarm);
//                }
//                return false;
//            }
//        });
//        itemIcon2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("error", "button2click");
//
//
//                startActivity(intentShedule);
//
//                return false;
//            }
//        });
//
//        itemIcon3.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("error", "button3click");
//
//                startActivity(devicesHome);
//
//                return false;
//            }
//        });
//
//
//        isOn = true;
//        if (isOn) {
//            buttonOnOff.setBackgroundResource(R.drawable.onbutton);
//        } else {
//            buttonOnOff.setBackgroundResource(R.drawable.offbutton);
//        }
//
//
//
//
//        buttonOnOff.setOnClickListener(
//                new Button.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isOn) {
//                            String query1="UPDATE SmartDevice SET Devicestate=0 where DeviceID='"+id+"'";
//                            dbHandler.iudFuntion(query1);
//                            buttonOnOff.setBackgroundResource(R.drawable.offbutton);
//                            isOn = false;
//                        } else {
//                            String query1="UPDATE SmartDevice SET Devicestate=1 where DeviceID='"+id+"'";
//                            dbHandler.iudFuntion(query1);
//                            buttonOnOff.setBackgroundResource(R.drawable.onbutton);
//                            isOn = true;
//                        }
//                    }
//                });
//
//
//    }
//
//    public void onBackPressed() {
//        startActivity(devicesHome);
//    }
//}
