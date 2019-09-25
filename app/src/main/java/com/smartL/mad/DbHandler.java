package com.smartL.mad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.smartL.mad.laky.FirebaseHandler;
import com.smartL.mad.recyclerView.Device;

import java.util.LinkedList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static String dbname = "SmartHome5";
//-----------------recyclerView--------------------------------------
    public static final String TABLE_NAME = "Devices";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DEVICE_ID = "deviceId";
    public static final String COLUMN_DEVICE_NAME = "deviceName";
    public static final String COLUMN_DEVICE_NICKNAME = "nickname";
    public static final String COLUMN_DEVICE_IMAGE = "image";
    public static final String COLUMN_DEVICE_SWITCH_STATE = "switchState";
    private  String personId=null;
    FirebaseHandler fire;
//---------------end recuclervie-------------------------------------------

    public DbHandler(Context context) {
        super(context, dbname, null, 1);

        Log.d("error","Db Connecterd");
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            personId = acct.getId().trim();
        }
        fire = new FirebaseHandler(context);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //============to recyclerView====================================
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +

                COLUMN_ID + " INTEGER , " +
                COLUMN_DEVICE_ID + " TEXT PRIMARY KEY NOT NULL, " +
                COLUMN_DEVICE_NAME + " TEXT NOT NULL, " +
                COLUMN_DEVICE_NICKNAME + " TEXT NOT NULL, " +
                COLUMN_DEVICE_IMAGE + " BLOB NOT NULL, " +
                COLUMN_DEVICE_SWITCH_STATE + "BOOLEAN);"
        );
        //================================================
        //SLight.USERDETAILS
        String query = "CREATE TABLE IF NOT EXISTS USERDETAILS(" +
                "    Uid text PRIMARY KEY," +
                "    Ufname text," +
                "    Ulame text," +
                "    Uemail text," +
                "    Ugivenname text," +
                "    Uimage text" +
                ")";
        //SLight.SmartDevice
        String query2= " CREATE TABLE IF NOT EXISTS SmartDevice(" +
                "    DeviceID text PRIMARY KEY," +
                "    Uid text ," +
                "    Devicename text," +
                "    Devicestate text DEFAULT 0," +
                        // "    Devicestate text INTEGER DEFAULT 0," +
                "    alarmSet text  DEFAULT 'false'," +
                "    CONSTRAINT plugforign FOREIGN KEY (Uid) REFERENCES USERDETAILS (Uid)" +
                ")";
        //SLight.SmartBulbs
        String query3= "CREATE TABLE IF NOT EXISTS SmartBulbs(" +
                "    BulbID text PRIMARY KEY," +
                "    Uid text," +
                "    Bulbname text DEFAULT 'SmartBulb'," +
                "    Bulbstate INTEGER DEFAULT 0," +
                "    BuLBR INTEGER DEFAULT 0," +
                "    BuLBG INTEGER DEFAULT 0," +
                "    BuLBB INTEGER DEFAULT 0," +
                "    BuLBColor INTEGER DEFAULT 0," +
                "    BuLBfuntion INTEGER DEFAULT 0," +
                "    BuLBcfunction INTEGER DEFAULT 0," +
                "    alarmSet text  DEFAULT 'false'," +
                "    CONSTRAINT bulbforiegn FOREIGN KEY (Uid) REFERENCES USERDETAILS (Uid)" +
                "    );" ;

        try {
            sqLiteDatabase.execSQL(query);
            sqLiteDatabase.execSQL(query2);
            sqLiteDatabase.execSQL(query3);
            Log.d("Error", "db create kala");
        } catch (Exception e) {
            Log.d("Error", "Db Create une na"+ e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USERDETAILS ");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SmartDevice");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SmartBulbs");

            Log.d("error", "THibba table drop kala");
            this.onCreate(sqLiteDatabase);
        } catch (Exception e) {
            Log.d("error", e.toString());
        }


    }

    public  boolean insertUser(String id,String name,String familyname,String email,String givienname){
//,String familyname,String email,String givienname
    SQLiteDatabase db= this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Uid",id);
        contentValues.put("Ufname",name);
        contentValues.put("Ulame",familyname);
        contentValues.put("Uemail",email);
        contentValues.put("Ugivenname",givienname);
        //contentValues.put("Uimage",photo);

        try {
            db.insert("USERDETAILS",null,contentValues);
            Log.d("Error","Insert Successful");
            db.close();
            return true;
        }catch (Exception e){
            Log.d("Error","Insert Error"+e.toString());
            return false;
        }

    }

    public boolean iudFuntion(String query) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.execSQL(query);
            Log.d("Error", "Update una");
            return true;
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return false;
        }

    }
    public Cursor getOneRaw(String query) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            return cursor;
        } catch (Exception e) {
            Log.d("Error","getone row"+ e.toString());
            return null;
        }
    }
    //=========================================================================
    /**create new record*/
    public void addNewDevice(Device device) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEVICE_ID, device.getDeviceId());
        values.put(COLUMN_DEVICE_NAME, device.getDeviceName());
        values.put(COLUMN_DEVICE_NICKNAME, device.getNickName());
        values.put(COLUMN_DEVICE_IMAGE, device.getImage());
        // insert new device
        db.insert(TABLE_NAME,null, values);

        //--To asign data to SmartPlugs or SmartBulbs--//
        if (device.getDeviceName().equals("Plug") || device.getDeviceName().equals("Bulb")) { //plugs and normalbulbs are same table
            ContentValues values1 = new ContentValues();
            values1.put("DeviceID", device.getDeviceId());
            values1.put("Devicename", device.getDeviceName());
            values1.put("Uid", personId);
            db.insert("SmartDevice",null,values1);

        }else if (device.getDeviceName().equals("RGB Bulb")) {
            ContentValues values2 = new ContentValues();
            values2.put("BulbID", device.getDeviceId());
            values2.put("Bulbname", device.getDeviceName());
            values2.put("Uid", personId);
            db.insert("SmartBulbs",null,values2);
        }

        fire.AddDevice(device.getDeviceId().toString());
        db.close();
    }
    /**Query records, give options to filter results**/
    public List<Device> deviceList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " where deviceName ='"+filter+"'";
        }

        List<Device> deviceLinkedList = new LinkedList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            Device device;

            if (cursor.moveToFirst()) {
                do {
                    device = new Device();
                    device.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                    device.setDeviceId(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_ID)));
                    device.setDeviceName(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NAME)));
                    device.setNickName(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NICKNAME)));
                    device.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_IMAGE)));
                    deviceLinkedList.add(device);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return deviceLinkedList;
    }
    /**Query only 1 record**/
    public Device getDevice(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Device receivedDevice = new Device();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedDevice.setDeviceId(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_ID)));
            receivedDevice.setDeviceName(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NAME)));
            receivedDevice.setNickName(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_NICKNAME)));
            receivedDevice.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_IMAGE)));
        }
        return receivedDevice;
    }
    /**delete device**/
    public void deleteDevice(String deviceId, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE deviceId='"+deviceId+"'");
        try{
            db.execSQL("DELETE FROM SmartDevice WHERE DeviceID='"+deviceId+"'");
        }catch (Exception e){
        }
        try{
            db.execSQL("DELETE FROM SmartBulbs WHERE BulbID='"+deviceId+"'");
        }catch (Exception e){
        }
        fire.deleteDevice(deviceId);
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
    //=========================================================================

    //----------------------------------------------
    //-----tempory code---------------------//
    public Cursor getAllData(String tableName){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from "+tableName, null);
            return res;
        }catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }
    }
    //---------------------------------------------
public String getId(){
        return personId;
}

}
