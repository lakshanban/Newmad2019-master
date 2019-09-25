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
import com.smartL.mad.user.HandleUser;

public class RegisterLaky extends AppCompatActivity {
    private TextView signin;
    private EditText usernameET,emailET,passwordET,confirmET;
    private Button Register;
    private SignInButton signInButton;
    LoginLaky loginLaky;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_laky);

        Register=findViewById(R.id.registerbtn);
        emailET=findViewById(R.id.email);
        usernameET=findViewById(R.id.username);
        passwordET=findViewById(R.id.password);
        confirmET=findViewById(R.id.confirm_password);

        signin=findViewById(R.id.signinTV);
//to google signin/////
        signInButton = findViewById(R.id.sign_in_button1);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button1:
                        signIn();
                        break;
                }

            }
        });
////////////////////


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(RegisterLaky.this, LoginLaky.class);
                startActivity(signin);
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=usernameET.getText().toString();
                String email=emailET.getText().toString();
                String password=passwordET.getText().toString();
                String confirm=confirmET.getText().toString();


                if(!empty(username,email,password,confirm)){

                    register(email,password);

                }


            }
        });


    }

    ///to google sign in
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
    ///end google signin///

    public boolean empty(String username,String email,String password, String confirm){

        if(username.length()==0){
            usernameET.setError("Enter valid Username");
            return true;
        }

        if(email.length()==0){
            emailET.setError("Enter Valid Email address");
            return true;
        }

        if(password.length()<6){
            passwordET.setError("Password must be 6 Characters long");
            return true;
        }
        if(!confirm.equals(password)){
            confirmET.setError("Dont match with the above password");
            return true;
        }

        return false;


    }


    public void register(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("registerxxx", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterLaky.this,"Account created Login",Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(RegisterLaky.this,LoginLaky.class);
                            Intent intent = new Intent(RegisterLaky.this,LoginLaky.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("registerxxx", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterLaky.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }
}
