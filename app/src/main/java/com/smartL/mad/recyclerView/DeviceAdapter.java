package com.smartL.mad.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.smartL.mad.commonUtilDevice.Controller;
import com.smartL.mad.DbHandler;
import com.smartL.mad.R;
import com.smartL.mad.laky.FirebaseHandler;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    public List<Device> mDeviceList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private DbHandler dbHandler;
    private FirebaseHandler fire;
    Intent intentSetAct;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView deviceNametxt;
        public TextView deviceNicknametxt;
        public ImageView deviceImage, removeDevice;
        public ToggleButton bulbSwitch;
        public RelativeLayout viewBackground, viewForeground;
        //private String state="off";

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;

            deviceNametxt = (TextView) v.findViewById(R.id.devName);
            deviceNicknametxt = (TextView) v.findViewById(R.id.devNickName);
            deviceImage = (ImageView) v.findViewById(R.id.image);

            removeDevice = (ImageView) v.findViewById(R.id.removeDevice);
            bulbSwitch = (ToggleButton) v.findViewById(R.id.bulbSwitch);

            viewBackground = v.findViewById(R.id.view_background);
            viewBackground.setVisibility(View.GONE);
            viewForeground = v.findViewById(R.id.view_foreground);

        }
    }

    public void add(int position, Device device) {
        mDeviceList.add(position, device);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mDeviceList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceAdapter(List<Device> myDataset, Context context, RecyclerView recyclerView) {
        mDeviceList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Device device = mDeviceList.get(position);

        dbHandler = new DbHandler(mContext);
        String name1 = device.getDeviceName();
        String query2;
        if (name1.equals("RGB Bulb")){
            query2="SELECT * FROM SmartBulbs where BulbID='" + device.getDeviceId() + "' ";
        }else {
            query2="SELECT * FROM SmartDevice where DeviceID='" + device.getDeviceId() + "' ";
        }
        try {
            Cursor cursor;
                cursor = dbHandler.getOneRaw(query2);
                cursor.moveToFirst();

            String state = cursor.getString(3);
            Log.d("error", "State" + state);
            if (state.equals("0")) {
                holder.bulbSwitch.setBackgroundResource(R.drawable.offbutton);
                holder.bulbSwitch.setChecked(false);
            } else {
                holder.bulbSwitch.setBackgroundResource(R.drawable.onbutton);
                holder.bulbSwitch.setChecked(true);
            }
        }catch (Exception e){

        }

        holder.deviceNicknametxt.setText("Type: " + device.getDeviceName());
        holder.deviceNametxt.setText("Name: " + device.getNickName());
        holder.deviceImage.setImageResource(checkImageResourse(device.getDeviceName()));

        holder.itemView.setTag(device.getDeviceId());
        //Picasso.with(mContext).load(device.getImage()).placeholder(R.drawable.ic_lightbulb).into(holder.deviceImage);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(device.getDeviceName().equals("Bulb")||device.getDeviceName().equals("Plug")){
                    intentSetAct = new Intent(mContext, Controller.class);
                }else if (device.getDeviceName().equals("RGB Bulb")){
                    intentSetAct = new Intent(mContext, Controller.class);
                }
                intentSetAct.putExtra("id_ReserveByDeviceAdapter",device.getDeviceId());
                intentSetAct.putExtra("name_ReserveByDeviceAdapter",device.getDeviceName());
                intentSetAct.putExtra("nickName_ReserveByDeviceAdapter",device.getNickName());
                mContext.startActivity(intentSetAct);


            }
        });
        holder.removeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Device Remove");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbHandler dbHelper = new DbHandler(mContext);
                        dbHelper.deleteDevice(device.getDeviceId(), mContext);
                        mDeviceList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mDeviceList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        holder.bulbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                dbHandler = new DbHandler(mContext);
                fire = new FirebaseHandler(mContext);
                String name = device.getDeviceName();
                String query1;
                if (!isCheck){
                    if (name.equals("RGB Bulb")){
                        query1="UPDATE SmartBulbs SET Bulbstate=0 where BulbID='"+device.getDeviceId()+"'";
                    }else {
                        query1="UPDATE SmartDevice SET Devicestate=0 where DeviceID='"+device.getDeviceId()+"'";
                    }
                    Log.d("error","off");
                    fire.updateStatus(device.getDeviceId(),0);
                    compoundButton.setBackgroundResource(R.drawable.offbutton);
                }else {
                    if (name.equals("RGB Bulb")){
                        query1="UPDATE SmartBulbs SET Bulbstate=1 where BulbID='"+device.getDeviceId()+"'";
                    }else {
                        query1="UPDATE SmartDevice SET Devicestate=1 where DeviceID='"+device.getDeviceId()+"'";
                    }
                    Log.d("error","on");
                    fire.updateStatus(device.getDeviceId(),1);
                    compoundButton.setBackgroundResource(R.drawable.onbutton);
                }
                dbHandler.iudFuntion(query1);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    private int checkImageResourse(String name){
        if (name.equals("RGB Bulb")){
            return R.drawable.smartbulb;
        }else if (name.equals("Bulb")){
            return R.drawable.nomalbulb;
        }else if (name.equals("Plug")){
            return R.drawable.smartplug;
        }
        return 0;
    }





}
