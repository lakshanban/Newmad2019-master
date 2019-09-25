package com.smartL.mad;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartL.mad.laky.FirebaseHandler;


public class Plugf extends Fragment {

    private String name,id;
    private TextView textView;
    private  DbHandler dbHandler;
    private ToggleButton toggleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_plugf, container, false);

        toggleButton=view.findViewById(R.id.plugOnOff);
        dbHandler= new DbHandler(getActivity());
        textView=view.findViewById(R.id.plugname);
        try {
            Intent intent = getActivity().getIntent();
            name = intent.getStringExtra("nickName_ReserveByDeviceAdapter");
            id=intent.getStringExtra("id_ReserveByDeviceAdapter");
            Log.d("error",name);
            Log.d("error",id);
        }catch (Exception e){
            Log.d("error","Intetn eke awulk "+e);
        }

        textView.setText(name);
        String query3="SELECT * FROM SmartDevice where DeviceID='"+id+"' ";
        try {
           Cursor cursor= dbHandler.getOneRaw(query3);
            cursor.moveToFirst();
           String state= cursor.getString(3);
           Log.d("error","State"+state);
            if (Integer.parseInt(state)==0){
              toggleButton.setBackgroundResource(R.drawable.offbutton);
                toggleButton.setChecked(false);
            }else{
               toggleButton.setBackgroundResource(R.drawable.onbutton);
                toggleButton.setChecked(true);
           }
        }catch (Exception e){
            Log.d("error","db connect eke error"+e);
        }




        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FirebaseHandler fire=new FirebaseHandler(getActivity());
                if (!b){
                    String query1="UPDATE SmartDevice SET Devicestate=0 where DeviceID='"+id+"'";
                    dbHandler.iudFuntion(query1);
                    fire.updateStatus(id,0);
                    Toast.makeText(getActivity(), "update added", Toast.LENGTH_SHORT).show();
                    toggleButton.setBackgroundResource(R.drawable.offbutton);

                    Log.d("error","off");
                }else{
                    String query1="UPDATE SmartDevice SET Devicestate=1 where DeviceID='"+id+"'";
                    dbHandler.iudFuntion(query1);
                    fire.updateStatus(id,1);
                    toggleButton.setBackgroundResource(R.drawable.onbutton);
                    Log.d("error","on");
                }
            }
        });
        return view;
    }

}
