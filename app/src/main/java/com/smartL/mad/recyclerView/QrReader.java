package com.smartL.mad.recyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.smartL.mad.DbHandler;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class QrReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                Toast.makeText(QrReader.this,"Permission is Granted!", Toast.LENGTH_LONG).show();
            }else {
                requestPermission();
            }
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(QrReader.this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResults(int requestCode, String permission[], int grantResults[]){
        switch (requestCode){
            case REQUEST_CAMERA :
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(QrReader.this,"Permission granted", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(QrReader.this,"Permission denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                if (scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
        }
    }

    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }
    public void onStop(){
        super.onStop();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(QrReader.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResults = result.getText();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        final EditText nickname = new EditText(QrReader.this);

            if (scanResults.startsWith("S!")) {
                if(checkavailability(scanResults.toString())==0) {
                    vibrator.vibrate(100);
                    builder.setTitle("Enter device name:");
                    //builder.setMessage(scanResults);
                    nickname.setHint("Nickname:");
                    nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(nickname);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(QrReader.this, AddDeviceActivity.class);
                            String deviceId = scanResults.toString();
                            String nn = nickname.getText().toString();
                            if (nn.equals("")) {
                                nn = "My Device";
                            }
                            intent.putExtra("deviceIdQr", deviceId);
                            intent.putExtra("nicknameQr", nn);
                            onStop();
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            scannerView.resumeCameraPreview(QrReader.this);
                        }
                    });
                }else {
                    vibrator.vibrate(500);
                    builder.setTitle("Error!..");
                    builder.setMessage("This device already have in your device list!");
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            scannerView.resumeCameraPreview(QrReader.this);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(QrReader.this, MainDevicesHome.class);
                            onStop();
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            } else {
                vibrator.vibrate(500);
                builder.setTitle("Error!..");
                builder.setMessage("This is not a Smart Device. Please scan correct smart device!!.");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(QrReader.this);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(QrReader.this, MainDevicesHome.class);
                        onStop();
                        startActivity(intent);
                        finish();
                    }
                });

            }
        AlertDialog alert = builder.create();
        alert.show();
    }
    public int checkavailability(String did){
        int status = 0;
        DbHandler dbHandler = new DbHandler(this);
        String count= null;
        String query3="SELECT count(*) FROM Devices where deviceId='"+did.toString()+"' ";
        try {
            Cursor cursor = dbHandler.getOneRaw(query3);
            cursor.moveToFirst();
            count = cursor.getString(0);
            Log.d("error","QrScanning Id : "+count);
        }catch (Exception e) {
            Log.d("error", count + " " + e.toString());
        }
        if (count.equals("1")) {
            status = 1;
        }else {
            status = 0;
        }
        return status;

    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(QrReader.this,MainDevicesHome.class);
        onStop();
        startActivity(intent);
        finish();
    }

}


//        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResults));
//                startActivity(intent);
//            }
//        });
