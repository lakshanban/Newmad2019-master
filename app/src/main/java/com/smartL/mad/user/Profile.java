package com.smartL.mad.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.smartL.mad.DbHandler;
import com.smartL.mad.R;
import com.smartL.mad.user.Register;

public class Profile extends AppCompatActivity {


    TextView tid, tfname, tlname, tmail, password, rpassword;
    ImageView imageView;
    Intent intentregister;
    GoogleSignInClient mGoogleSignInClient;
    Button button;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbHandler = new DbHandler(this);
        intentregister = new Intent(this, Register.class);

        tid = findViewById(R.id.pid);
        tfname = findViewById(R.id.pfirstname);
        tlname = findViewById(R.id.plastname);
        tmail = findViewById(R.id.pemail);
        password = findViewById(R.id.ppassword);
        rpassword = findViewById(R.id.prpassword);
        button = findViewById(R.id.Logout);
        imageView = findViewById(R.id.imageView);
        Cursor resultSet = dbHandler.getOneRaw("Select * from USERDETAILS ");

       try {
           resultSet.moveToFirst();
           String personId = resultSet.getString(0);
           String personEmail = resultSet.getString(3);
           String personGivenName = resultSet.getString(4);
           String personFamilyName = resultSet.getString(1);
           tid.setText(personId);
           tfname.setText(personGivenName);
           tlname.setText(personFamilyName);
           tmail.setText(personEmail);
       }catch (Exception e){
           Log.d("error","profile cursor"+e);
       }




        // imageView.setBackground(personPhoto);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signOut();
                    }
                }
        );


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(intentregister);

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signOut();
    }
}
