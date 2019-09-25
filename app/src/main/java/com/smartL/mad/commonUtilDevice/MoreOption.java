//package com.smartL.mad.commonUtilDevice;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.smartL.mad.R;
//import com.smartL.mad.commonUtilDevice.Controller;
//
//public class MoreOption extends AppCompatActivity {
//
//    Intent intent;
//
//    Button buttonRead;
//    Button buttonSleep;
//    Button buttonParty;
//    Button buttonDance;
//    TextView textView;
//String Dis;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            Window window= getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//        }
//       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //getWindow().statusBarColor = ContextCompat.getColor(this, android.R.color.transparent);
//        //getWindow().navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent);
//        getWindow().setBackgroundDrawableResource(R.drawable.gradientcolor);
//        setContentView(R.layout.activity_more_option);
//
//       getSupportActionBar().hide();
//        getWindow().setStatusBarColor(getResources().getColor(R.color.newActionBar));
//final ConstraintLayout constraintLayout =findViewById(R.id.conbg);
//
//buttonParty=findViewById(R.id.party);
//buttonDance=findViewById(R.id.dance);
//buttonSleep=findViewById(R.id.sleep);
//buttonRead=findViewById(R.id.read);
//textView=findViewById(R.id.dis);
//        Dis="We Give You Best Party Dance";
//        textView.setText(Dis);
//buttonRead.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        constraintLayout.setBackgroundResource(R.drawable.morebgread);
//        Dis="We Provide Sutable Enviornment for Redading";
//        textView.setText(Dis);
//    }
//});
//        buttonParty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                constraintLayout.setBackgroundResource(R.drawable.morebgparty);
//                Dis="We Give You Best Party Experience";
//                textView.setText(Dis);
//            }
//        });
//        buttonDance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                constraintLayout.setBackgroundResource(R.drawable.morebgdance);
//                Dis="We Give You Best Party Dance";
//                textView.setText(Dis);
//            }
//        });
//        buttonSleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                constraintLayout.setBackgroundResource(R.drawable.morebgsleep);
//                Dis="We Provide Sutable Enviornment for Sleeping";
//                textView.setText(Dis);
//            }
//        });
//        intent = new Intent(this, Controller.class);
//
//
//
//    }
//
//    public void onBackPressed() {
//        startActivity(intent);
//    }
//
//}
