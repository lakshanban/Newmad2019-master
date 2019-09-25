package com.smartL.mad;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartL.mad.commonUtilDevice.DevicesCatogory;
import com.smartL.mad.laky.FirebaseHandler;


public class Moreoptionf extends Fragment {

    private Button buttonRead;
    private Button buttonSleep;
    private Button buttonParty;
    private Button buttonDance;
    private TextView textView;
    private TextView textView1;
    private  DbHandler dbHandler;
    private String id,update;
    private int f;
    private Cursor cursor;
    private FirebaseHandler fire;

    private  int state;



    ConstraintLayout constraintLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_moreoptionf, container, false);
        buttonParty=view.findViewById(R.id.party);
        buttonDance=view.findViewById(R.id.dance);
        buttonSleep= view.findViewById(R.id.sleep);
        buttonRead=view.findViewById(R.id.read);
        textView=view.findViewById(R.id.dis);
        textView1=view.findViewById(R.id.eventname);
        constraintLayout =view.findViewById(R.id.conbg);

       dbHandler= new DbHandler(getActivity());
       fire = new FirebaseHandler(getActivity());
        try {
            Intent intent = getActivity().getIntent();
           id=intent.getStringExtra("id_ReserveByDeviceAdapter").trim();
            Log.d("error",id);
        }catch (Exception e){
            Log.d("error","Intetn eke awulk "+e);
        }
        update="UPDATE SmartBulbs SET BuLBfuntion=1 where BulbID='"+id+"'";
      dbHandler.iudFuntion(update);
      fire.updateFunction(id,1,0);

      try{
          String query1="Select * from SmartBulbs where BulbID='"+id+"'";
          cursor = dbHandler.getOneRaw(query1);
          cursor.moveToFirst();
          state=Integer.parseInt(cursor.getString(3));
          if (Integer.parseInt(cursor.getString(9))==0 ){
             // constraintLayout.setBackgroundResource(R.drawable.morebgdance);
              textView1.setText("Dance");
              textView.setText("We Give You Best Party Dance");
              constraintLayout.setBackgroundResource(R.drawable.morebgdance);

          }else  if (Integer.parseInt(cursor.getString(9))==1 ){
             // constraintLayout.setBackgroundResource(R.drawable.morebgparty);
              textView1.setText("Party");
              textView.setText("We Give You Best Party Experience");
              constraintLayout.setBackgroundResource(R.drawable.morebgparty);

          }else if (Integer.parseInt(cursor.getString(9))==2 ){
             // constraintLayout.setBackgroundResource(R.drawable.morebgread);
              textView1.setText("Reading");
              textView.setText("We Provide Sutable Enviornment for Redading");
              constraintLayout.setBackgroundResource(R.drawable.morebgread);

          }else  if (Integer.parseInt(cursor.getString(9))==3 ){
              //constraintLayout.setBackgroundResource(R.drawable.morebgsleep);
              textView1.setText("Sleep");
              textView.setText("We Provide Sutable Enviornment for Sleeping");
              constraintLayout.setBackgroundResource(R.drawable.morebgsleep);

          }
      }catch (Exception e){
          Log.d("error","db more case"+e);
      }






        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state != 0) {
                    constraintLayout.setBackgroundResource(R.drawable.morebgread);
                    update = "UPDATE SmartBulbs SET BuLBcfunction=2 where BulbID='" + id + "'";
                    dbHandler.iudFuntion(update);
                    fire.updateFunction(id,2,1);
                    textView1.setText("Reading");
                    textView.setText("We Provide Sutable Enviornment for Redading");
                } else {
                    Toast.makeText(getActivity(), "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state != 0) {
                constraintLayout.setBackgroundResource(R.drawable.morebgparty);
                update="UPDATE SmartBulbs SET BuLBcfunction=1 where BulbID='"+id+"'";
                dbHandler.iudFuntion(update);
                fire.updateFunction(id,1,1);
                textView1.setText("Party");
            } else {
                Toast.makeText(getActivity(), "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
            }
            }
        });
        buttonDance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state != 0) {
                constraintLayout.setBackgroundResource(R.drawable.morebgdance);
                update="UPDATE SmartBulbs SET BuLBcfunction=0 where BulbID='"+id+"'";
                dbHandler.iudFuntion(update);
                fire.updateFunction(id,0,1);
                textView1.setText("Dance");
                textView.setText("We Give You Best Party Dance");
        } else {
            Toast.makeText(getActivity(), "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
        }
            }
        });
        buttonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state != 0) {
                    constraintLayout.setBackgroundResource(R.drawable.morebgsleep);
                    update = "UPDATE SmartBulbs SET BuLBcfunction=3 where BulbID='" + id + "'";
                    dbHandler.iudFuntion(update);
                    fire.updateFunction(id,3,1);
                    textView1.setText("Sleep");
                    textView.setText("We Provide Sutable Enviornment for Sleeping");
                } else {
                    Toast.makeText(getActivity(), "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }



}
