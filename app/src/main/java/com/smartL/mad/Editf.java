package com.smartL.mad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartL.mad.recyclerView.MainDevicesHome;
import com.smartL.mad.recyclerView.QrReader;


public class Editf extends Fragment {

    private String id=null, nickName;
    Button btnChangeNn, btnDeleteDevice;
    DbHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editf, container, false);
        final Intent intentHome = new Intent(getActivity(), MainDevicesHome.class);
        dbHandler = new DbHandler(getActivity());
        try {
            Intent intent = getActivity().getIntent();
            id=intent.getStringExtra("id_ReserveByDeviceAdapter").trim();
            nickName=intent.getStringExtra("nickName_ReserveByDeviceAdapter").trim();
            Log.d("error",id);
        }catch (Exception e){
            Log.d("error","Intetn eke awulk "+e);
        }

        btnChangeNn = view.findViewById(R.id.updateNn);
        btnDeleteDevice = view.findViewById(R.id.deletedevice);

        btnChangeNn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Change Nickname:");
                //builder.setMessage(scanResults);
                final EditText nickname = new EditText(getActivity());
                nickname.setHint("Nickname:"+nickName);
                nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(nickname);

                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String query;
                        String newNn = nickname.getText().toString();
                        if (newNn.equals("")) {
                            Toast.makeText(getActivity(), "Please enter your new nickname.", Toast.LENGTH_SHORT).show();
                        }else {
                            query = "UPDATE Devices SET nickname='" + newNn + "' where deviceId='" + id + "'";
                            dbHandler.iudFuntion(query);
                            startActivity(intentHome);
                            getActivity().finish();
                            Toast.makeText(getActivity(), "Nickname Changed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnDeleteDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Device Remove");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteDevice(id,getActivity());
                        startActivity(intentHome);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
