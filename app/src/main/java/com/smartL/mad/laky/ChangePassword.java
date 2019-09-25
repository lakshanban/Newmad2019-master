package com.smartL.mad.laky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smartL.mad.R;
import com.smartL.mad.recyclerView.MainDevicesHome;

public class ChangePassword extends AppCompatActivity {

    private TextInputEditText currentp,newp,confirmp;
    private Button reset;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        user = FirebaseAuth.getInstance().getCurrentUser();

        currentp=findViewById(R.id.currentPsw);
        newp=findViewById(R.id.newPsw);
        confirmp=findViewById(R.id.confirmPsw);
        reset=findViewById(R.id.resetbtn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentpass=currentp.getText().toString();
                String newpass=newp.getText().toString();
                String confirmpass=confirmp.getText().toString();

                resetpassword(currentpass,newpass,confirmpass);

            }
        });
    }

    private void resetpassword(final String currentpass, final String newpass, final String confirmpass) {


        if (!(currentpass.length() == 0 || newpass.length() == 0 || confirmpass.length() == 0)) {
            if (newpass.equals(confirmpass)) {
                String email=user.getEmail();
// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, currentpass);
// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("donebro", "User re-authenticated.");
                                user.updatePassword(confirmpass)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("donebro", "User password updated.");
                                                    Toast.makeText(ChangePassword.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    startActivity(new Intent(ChangePassword.this,LoginLaky.class));
                                                    Toast.makeText(ChangePassword.this, "Login to the system", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }else {
                                                    Toast.makeText(ChangePassword.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
            }  else {
                Toast.makeText(ChangePassword.this,"new password and confirm password do not match", Toast.LENGTH_LONG).show();
            }
        } else {
            if (currentpass.isEmpty())
                currentp.setError("field cannot be empty");
            if (newpass.isEmpty())
                newp.setError("field cannot be empty");
            if (confirmpass.isEmpty())
                confirmp.setError("field cannot be empty");
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(ChangePassword.this, MainDevicesHome.class));
        finish();
    }
}
