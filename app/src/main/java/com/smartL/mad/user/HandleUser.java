package com.smartL.mad.user;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.smartL.mad.DbHandler;
import com.smartL.mad.R;
import com.smartL.mad.laky.FirebaseHandler;
import com.smartL.mad.recyclerView.MainDevicesHome;

public class HandleUser extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    private String personId;
    private Uri personPhoto=null;
    private DbHandler dbHandler;
    private Button button;
    public static String currentUserId = null;

    public HandleUser() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_user);

        button = findViewById(R.id.accountContinue);
        dbHandler = new DbHandler(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personGivenName = acct.getGivenName();
            personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            personId = acct.getId().trim();
            currentUserId = personId;
            personPhoto = acct.getPhotoUrl();

            try {
                Log.d("Error count", currentUserId);
                String q1 = "SELECT count(*) FROM USERDETAILS WHERE Uid='"+currentUserId+"'";

                Cursor resultSet = dbHandler.getOneRaw(q1);

              resultSet.moveToFirst();
              String count = resultSet.getString(0);


                if (count.equals("0") ) {


                    String query = "INSERT INTO USERDETAILS (Uid,Ufname,Ulame,Uemail,Ugivenname ) VALUES ('"+personId+"','"+personName+"','"+personFamilyName+"','"+personEmail+"','"+personGivenName+"')";
                  Boolean b= dbHandler.iudFuntion(query);

                    FirebaseHandler fire=new FirebaseHandler(getApplicationContext());
                    fire.addUser(personId,personName,personFamilyName,personGivenName,personEmail);
                  //  Toast.makeText(this, "add user to firebase", Toast.LENGTH_SHORT).show();


               //   dbHandler.insertUser(personId,personName,personFamilyName,personEmail,personGivenName);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HandleUser.this, MainDevicesHome.class));
                            finish();
                        }
                    });
                } else {

                    startActivity(new Intent(this, MainDevicesHome.class));
                    finish();
                }

            } catch (Exception e) {
                Log.d("Error", "except"+e.toString());
                startActivity(new Intent(this, Register.class));
                finish();
            }


        }
        else {
            Toast.makeText(this, "Account Null", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Register.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quitapp();
        startActivity(new Intent(HandleUser.this, Register.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        //Toast.makeText(this, "on start Method Calling...", Toast.LENGTH_SHORT).show();
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // danata sign in welada inne kiyala ballna meka damme
        if (account == null) {
            startActivity(new Intent(this, Register.class));
            finish();
        }
    }

    public void quitapp() {
        //Toast.makeText(this, "App Quit Method Calling...", Toast.LENGTH_SHORT).show();
        try {
           String query = "DELETE FROM USERDETAILS WHERE Uid='"+personId+"'";
           dbHandler.iudFuntion(query);
            signOut();

        } catch (Exception e) {
            Log.d("Error", "user delete"+e.toString());
        }
    }

    public void signOut() {
        Toast.makeText(this, "Sing out Method Calling...", Toast.LENGTH_SHORT).show();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
    }

}
