package com.smartL.mad.laky;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseHandler {


private String User;


    public FirebaseHandler(Context context){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
        if (acct != null) {
            User = acct.getId().trim();
        }
    }

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

    public void addUser(String uid,String ufname, String ulname, String ugivenname,String uemail){
        firebaseDatabase.getReference().child("users").child(uid).child("UID").setValue(uid);
        firebaseDatabase.getReference().child("users").child(uid).child("UFname").setValue(ufname);
        firebaseDatabase.getReference().child("users").child(uid).child("ULname").setValue(ulname);
        firebaseDatabase.getReference().child("users").child(uid).child("UGivenName").setValue(ugivenname);
        firebaseDatabase.getReference().child("users").child(uid).child("UEmail").setValue(uemail);
    }
//    public void deleteUser(String uid){
//        firebaseDatabase.getReference().child("users").child(uid).removeValue();
//    }

    public void AddDevice(String did){
        String name = null;
        String x = did.substring(3,6);
        if(x.equals("PLG")){
            firebaseDatabase.getReference().child("PLG").child(did).child("status").setValue(0);
        }else if(x.equals("BLB")){
            firebaseDatabase.getReference().child("BLB").child(did).child("status").setValue(0);
        }else if(x.equals("RGB")){
            firebaseDatabase.getReference().child("RGB").child(did).child("status").setValue(0);
            firebaseDatabase.getReference().child("RGB").child(did).child("C_Red").setValue("null");
            firebaseDatabase.getReference().child("RGB").child(did).child("C_Green").setValue("null");
            firebaseDatabase.getReference().child("RGB").child(did).child("C_Blue").setValue("null");
            firebaseDatabase.getReference().child("RGB").child(did).child("BulbFunction").setValue(0);
            firebaseDatabase.getReference().child("RGB").child(did).child("BulbcFunction").setValue(0);
        }
    }
    public void updateStatus(String did,int status){
        String x = did.substring(3,6);
        if(x.equals("PLG")) {
            firebaseDatabase.getReference().child("PLG").child(did).child("status").setValue(status);
        }else if(x.equals("BLB")){
            firebaseDatabase.getReference().child("BLB").child(did).child("status").setValue(status);
        }else if(x.equals("RGB")){
            firebaseDatabase.getReference().child("RGB").child(did).child("status").setValue(status);
        }
    }
    public void SetColour(String did,int Red,int Green,int Blue){
        firebaseDatabase.getReference().child("RGB").child(did).child("C_Red").setValue(Red);
        firebaseDatabase.getReference().child("RGB").child(did).child("C_Green").setValue(Green);
        firebaseDatabase.getReference().child("RGB").child(did).child("C_Blue").setValue(Blue);
    }
    public void updateFunction(String did,int val,int function_type){
        // 0 = Bulb Function
        // 1 = Bulbc Function
        if(did.substring(3,6).equals("RGB")) {
            if (function_type == 0) {
                firebaseDatabase.getReference().child("RGB").child(did).child("BulbFunction").setValue(val);
            } else if (function_type == 1) {
                firebaseDatabase.getReference().child("RGB").child(did).child("BulbcFunction").setValue(val);
            }
        }
    }
    public void deleteDevice(String did){
        String x = did.substring(3,6);
        if(x.equals("PLG")){
            firebaseDatabase.getReference().child("PLG").child(did).removeValue();
        }else if(x.equals("BLB")){
            firebaseDatabase.getReference().child("BLB").child(did).removeValue();
        }else if(x.equals("RGB")){
            firebaseDatabase.getReference().child("RGB").child(did).removeValue();
        }
    }




}