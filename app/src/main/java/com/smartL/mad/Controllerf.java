package com.smartL.mad;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.internal.Objects;
import com.smartL.mad.laky.FirebaseHandler;

import java.util.Locale;

import top.defaults.colorpicker.ColorObserver;
import top.defaults.colorpicker.ColorPickerView;


public class Controllerf extends Fragment {

  private TextView textViewNickname;

  private ToggleButton buttonOnOff;
  private GradientDrawable gradientDrawable;
  private ConstraintLayout constraintLayout;
  private String id=null;
  private String name,hex=null;
  private DbHandler dbHandler;
  private int state;
  private String r,g,b;
  private FirebaseHandler fire;
  private ColorPickerView colorPickerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_controllerf, container, false);
        dbHandler = new DbHandler(getActivity());
        fire = new FirebaseHandler(getActivity());

        try {
            Intent intent = getActivity().getIntent();
             name = intent.getStringExtra("nickName_ReserveByDeviceAdapter");
             id=intent.getStringExtra("id_ReserveByDeviceAdapter").trim();

        }catch (Exception e){
            Log.d("error","Intetn eke awulk "+e);
        }

        String query2="UPDATE SmartBulbs SET BuLBfuntion=0 where BulbID='"+id+"' ";
         dbHandler.iudFuntion(query2);
         fire.updateFunction(id,0,0);



        textViewNickname =  view.findViewById(R.id.rgbConName);

        buttonOnOff = view.findViewById(R.id.Onoff);
        constraintLayout=view.findViewById(R.id.controllerbg);
        colorPickerView=view.findViewById(R.id.colorPicker);
        textViewNickname.setText(name);


        String query3="SELECT * FROM SmartBulbs where BulbID='"+id+"' ";

        try {
            Cursor cursor= dbHandler.getOneRaw(query3);
            cursor.moveToFirst();

        r= cursor.getString(4);
        g= cursor.getString(5);
        b= cursor.getString(6);
            state= Integer.parseInt( cursor.getString(3));

            hex= cursor.getString(7);
         colorPickerView.setInitialColor(Integer.valueOf(hex));
            Log.d("error","r "+r);
            Log.d("error","g "+g);
            Log.d("error","b "+b);

            if (state==0){
                Log.d("error","switchstate"+state);
                buttonOnOff.setBackgroundResource(R.drawable.offbutton);
                buttonOnOff.setChecked(false);
                Log.d("error","button is check "+buttonOnOff.isChecked());
            }else{
                buttonOnOff.setBackgroundResource(R.drawable.onbutton);
                Log.d("error","switchstate"+state);
                buttonOnOff.setChecked(true);
                Log.d("error","button is check "+buttonOnOff.isChecked());
            }
            int colorg[]={Color.parseColor("#223553"),Color.parseColor("#223553"),Color.parseColor(hex)};
            Log.d("error","color read"+hex);

            gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,colorg);
            gradientDrawable.setCornerRadius(0f);
            constraintLayout.setBackground(gradientDrawable);
        }catch (Exception e){
            Log.d("error","get db row"+e);
        }



        colorPickerView.subscribe(new ColorObserver() {
            @Override
            public void onColor(int color, boolean fromUser) {
            if (state!=0) {
                Log.d("error", "r" + Color.red(color));
                Log.d("error", "g" + Color.green(color));
                Log.d("error", "b" + Color.blue(color));
                String hex = colorHex(color);
                Log.d("error", "meka okakda" + hex);

                constraintLayout.setBackgroundColor(color);
                textViewNickname.setTextColor(color);

                int colorg[] = {Color.parseColor("#223553"), Color.parseColor("#223553"), Color.parseColor(hex)};
                gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorg);
                gradientDrawable.setCornerRadius(0f);
                constraintLayout.setBackground(gradientDrawable);
                int r, g, b;
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
                String query = "UPDATE SmartBulbs SET BuLBR=" + r + ", BuLBG=" + g + ", BuLBB=" + b + ", BuLBColor=" + color + " where BulbID='" + id + "' ";
                dbHandler.iudFuntion(query);
                fire.SetColour(id, r, g, b);
            }else{
                Toast.makeText(getActivity(), "Plase Turn On Switch", Toast.LENGTH_SHORT).show();
            }
            }
        });

buttonOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b){
            String query1="UPDATE SmartBulbs SET Bulbstate=0 where BulbID='"+id+"'";
           dbHandler.iudFuntion(query1);
           fire.updateStatus(id,0);
            buttonOnOff.setBackgroundResource(R.drawable.offbutton);
          Log.d("error","off");
            state=0;
        }else{
         String query1="UPDATE SmartBulbs SET Bulbstate=1 where BulbID='"+id+"'";
            dbHandler.iudFuntion(query1);
            fire.updateStatus(id,1);
            buttonOnOff.setBackgroundResource(R.drawable.onbutton);
            Log.d("error","on");
            state=1;
        }
    }
});


return  view;

    }
    private String colorHex(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "#%02X%02X%02X",  r, g, b);
    }

}
