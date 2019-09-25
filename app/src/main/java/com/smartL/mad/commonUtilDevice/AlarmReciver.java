package com.smartL.mad.commonUtilDevice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.smartL.mad.AlarmManager;
import com.smartL.mad.DbHandler;
import com.smartL.mad.R;
import com.smartL.mad.ShowNotification;
import com.smartL.mad.laky.FirebaseHandler;

public class AlarmReciver extends BroadcastReceiver  {


    @Override
    public void onReceive(Context context, Intent intent) {


        String did= intent.getStringExtra("did");
        String name= intent.getStringExtra("nickName_ReserveByDeviceAdapter");

        Log.d("error", "Intent value"+did+name);
        Log.d("error", "Alarm wadinawa"+did);


        DbHandler dbHandler= new DbHandler(context);
        FirebaseHandler fire = new FirebaseHandler(context);
        DevicesCatogory devicesCatogory= new DevicesCatogory();
try{
    String query;
    if (devicesCatogory.isRgb(did)){
        query="UPDATE SmartBulbs SET alarmSet= 'false' where BulbID='"+did+"'";
    }else{
        query="UPDATE SmartDevice SET alarmSet= 'false' where DeviceID='"+did+"' ";
    }
    dbHandler.iudFuntion(query);
    fire.updateStatus(did,0);
}catch (Exception e){
    Log.d("error", "Alarm wadinawa awl"+e);
}


       /* Intent intent1= new Intent(context, ShowNotification.class);
        intent1.putExtra("id_ReserveByDeviceAdapter",did);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =PendingIntent.getActivity(context,0,intent1 ,PendingIntent.FLAG_UPDATE_CURRENT );


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context
        ).setSmallIcon(R.drawable.iconnew)
                .setContentText(name)
                .setContentTitle("Device Off")
                .setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);




        NotificationManager notificationManager =(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

*/

    }
}


