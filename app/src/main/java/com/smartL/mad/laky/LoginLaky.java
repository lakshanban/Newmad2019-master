package com.smartL.mad.laky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smartL.mad.R;
import com.smartL.mad.recyclerView.MainDevicesHome;
import com.smartL.mad.user.HandleUser;
import com.smartL.mad.user.Register;

public class LoginLaky extends AppCompatActivity {

    private Button logintbtn;
    SignInButton signInButton;
    private EditText emailET,passwordET;
    private TextView registerTV;
    public FirebaseAuth mAuth=FirebaseAuth.getInstance();

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        logintbtn=findViewById(R.id.loginbtn);
        signInButton = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        emailET=findViewById(R.id.emaildET);
        passwordET=findViewById(R.id.passwordET);
        registerTV=findViewById(R.id.registetTV);


///////////////////////////////////////////////////////////////////////////////////////////////////
        logintbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=emailET.getText().toString();
                String password=passwordET.getText().toString();
                isEmpty(email,password);
                validate(email,password);




            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginLaky.this, Register.class);
//                startActivity(intent);
               // finish();
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginLaky.this,RegisterLaky.class);
                startActivity(registerIntent);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////



    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //yanna ona thana hari nam
            Intent intent = new Intent(this, HandleUser.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }
    private boolean isEmpty(String email,String password){
        if(email.length()==0){
            emailET.setError("Enter email address");
            return true;
        }
        if(password.length()==0){
            passwordET.setError("Enter password");
            return true;
        }

        return false;

    }

    private void validate(String email,String password)
    {

        if(!isEmpty(email,password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("firebasexxx", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(LoginLaky.this, MainDevicesHome.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("firebasexxx", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginLaky.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (currentUser!=null){
            Intent intent = new Intent(LoginLaky.this,MainDevicesHome.class);
            startActivity(intent);
            finish();
        } else if (account != null) {
            startActivity(new Intent(this, MainDevicesHome.class));
            finish();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
