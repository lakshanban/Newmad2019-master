package com.smartL.mad.recyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.smartL.mad.ConnectWifi;
import com.smartL.mad.DbHandler;
import com.smartL.mad.R;
import com.smartL.mad.VoiceContoll;
import com.smartL.mad.laky.ChangePassword;
import com.smartL.mad.laky.FirebaseHandler;
import com.smartL.mad.laky.LoginLaky;
import com.smartL.mad.user.Register;

public class MainDevicesHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DbHandler dbHelper;
    private DeviceAdapter adapter;
    private String filter = "";
    private LinearLayout linearLayout;
    private DrawerLayout drawerLayout;
    private AnimationDrawable animationDrawable;
    private Vibrator vibrator;
    private ImageView imageView1;
    private TextView uName,navmail;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseHandler fire;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    int backpress = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_devices_home);
        getSupportActionBar().hide();

        drawerLayout=findViewById(R.id.dhome);

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);

        View navView = navigationView.getHeaderView(0);
        uName = (TextView) navView.findViewById(R.id.userName);
        navmail = (TextView) navView.findViewById(R.id.navmail);
        imageView1=(ImageView) navView.findViewById(R.id.imageView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // danata sign in welada inne kiyala ballna meka damme
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (acct != null) {
            try {
                 nav_Menu.findItem(R.id.nav_changePsw).setVisible(false);
                 uName.setText(acct.getGivenName().toString());
                 navmail.setText(acct.getEmail().toString());
                 String img_uri = acct.getPhotoUrl().toString();
                 Glide.with(this).load(img_uri).into(imageView1);

                Log.d("error","drawercase "+acct.getEmail());
            }catch (Exception e){
                Log.d("error","drawercase "+e);
            }
        }else if (currentUser!=null){
            navmail.setText(currentUser.getEmail().toString());
        }




        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // danata sign in welada inne kiyala ballna meka damme
        if (account != null) {
            //startActivity(new Intent(this, Register.class));

        }

        animationDrawable = (AnimationDrawable) drawerLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getDrawable(R.drawable.moreic));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:

        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageDrawable(getDrawable(R.drawable.plus));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageDrawable(getDrawable(R.drawable.smartplug));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageDrawable(getDrawable(R.drawable.nomalbulb));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();

        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageDrawable(getDrawable(R.drawable.smartbulb));
        SubActionButton button4 = itemBuilder.setContentView(itemIcon4).build();

        ImageView itemIcon5 = new ImageView(this);
        itemIcon5.setImageDrawable(getDrawable(R.drawable.alli));
        SubActionButton button5 = itemBuilder.setContentView(itemIcon5).build();

        ImageView itemIcon6 = new ImageView(this);
        itemIcon6.setImageDrawable(getDrawable(R.drawable.wifi));
        SubActionButton button6 = itemBuilder.setContentView(itemIcon6).build();

        ImageView itemIcon7 = new ImageView(this);
        itemIcon7.setImageDrawable(getDrawable(R.drawable.googlemic));
        SubActionButton button7 = itemBuilder.setContentView(itemIcon7).build();

        itemIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                Toast.makeText(MainDevicesHome.this, "Open QR Reader", Toast.LENGTH_SHORT).show();
                startQrReader();
            }
        });
        itemIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                populaterecyclerView("Plug");

            }
        });
        itemIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                populaterecyclerView("Bulb");
            }
        });
        itemIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                populaterecyclerView("RGB Bulb");
            }
        });
        itemIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                populaterecyclerView("");
            }
        });
        itemIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);
                startActivity(new Intent(MainDevicesHome.this, ConnectWifi.class));
                finish();
            }
        });

        itemIcon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);
                startActivity(new Intent(MainDevicesHome.this, VoiceContoll.class));
                finish();
            }
        });

        SubActionButton.LayoutParams layoutParams1 = (SubActionButton.LayoutParams) button3.getLayoutParams();
        layoutParams1.width = 160;
        layoutParams1.height = 160;
        layoutParams1.bottomMargin = 40;
        layoutParams1.rightMargin = 100;


        FloatingActionButton.LayoutParams layoutParams2 = (FloatingActionButton.LayoutParams) actionButton.getLayoutParams();
        layoutParams2.width = 200;
        layoutParams2.rightMargin = 75;
        layoutParams2.bottomMargin = 75;
        layoutParams2.height = 200;

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).setRadius(520)
                .addSubActionView(button1)
                .addSubActionView(button6)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)
                .addSubActionView(button5)
                .addSubActionView(button7)
                .attachTo(actionButton)
                .build();

        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //populate recyclerview
        populaterecyclerView(filter);

    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            populaterecyclerView("");
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_changePsw) {
            startActivity(new Intent(MainDevicesHome.this, ChangePassword.class));
            finish();
        } else if (id == R.id.nav_singout) {
            FirebaseAuth.getInstance().signOut();
            signOut();
            startActivity(new Intent(MainDevicesHome.this, LoginLaky.class));
            finish();
        }
//        else if (id == R.id.nav_rgbdb) {
//            showData("SmartBulbs");
//        } else if (id == R.id.nav_smartdevicedb) {
//            showData("SmartDevice");
//        } else if (id == R.id.nav_userdb) {
//            showData("USERDETAILS");
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void populaterecyclerView(String filter){
        dbHelper = new DbHandler(this);
        adapter = new DeviceAdapter(dbHelper.deviceList(filter), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        //Toast.makeText(this, dbHelper.getId(), Toast.LENGTH_SHORT).show();
        new ItemTouchHelper(itemTouchhelperCallback).attachToRecyclerView(mRecyclerView);
    }
    ItemTouchHelper.SimpleCallback itemTouchhelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            dbHelper = new DbHandler(MainDevicesHome.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainDevicesHome.this);
            builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbHelper.deleteDevice(viewHolder.itemView.getTag().toString(),MainDevicesHome.this);
                    refresh();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    refresh();
                }
            });
            builder.setIcon(R.drawable.delete);
            builder.setTitle("Device Remove");
            builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
            AlertDialog alert = builder.create();
            alert.show();
            vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
            if (dX<=-10) {
                ((DeviceAdapter.ViewHolder) viewHolder).viewBackground.setVisibility(View.VISIBLE);
            }else {
                ((DeviceAdapter.ViewHolder) viewHolder).viewBackground.setVisibility(View.GONE);
            }
            //Log.d("error","x"+dX+" y"+dY+"Action "+actionState );
            final View foregroundView = ((DeviceAdapter.ViewHolder) viewHolder).viewForeground;
                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                            actionState, isCurrentlyActive);


        }

    };

    private void startQrReader(){
        Intent intent = new Intent(MainDevicesHome.this,QrReader.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    //===========================================================
    //-------tempory code---------------------------------------//
    public void showData(String tablename){
        try {
            Cursor res = dbHelper.getAllData(tablename);
            if (res.getCount() == 0) {
                showMessage("Error", "No data found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            if (tablename.equals("SmartBulbs")) {
                while (res.moveToNext()) {
                    buffer.append("BulbID: " + res.getString(0) + "\n");
                    buffer.append("Uid: " + res.getString(1) + "\n");
                    buffer.append("Bulbname: " + res.getString(2) + "\n");
                    buffer.append("Bulbstate: " + res.getString(3) + "\n");
                    buffer.append("BuLBR: " + res.getString(4) + "\n");
                    buffer.append("BuLBG: " + res.getString(5) + "\n");
                    buffer.append("BuLBB: " + res.getString(6) + "\n");
                    buffer.append("Hex: " + res.getString(7) + "\n");
                    buffer.append("BuLBfuntion: " + res.getString(8) + "\n");
                    buffer.append("BuLBcfunction: " + res.getString(9) + "\n");
                    buffer.append("alarmSet: " + res.getString(10) + "\n\n");
                }
            } else if (tablename.equals("SmartDevice")) {
                while (res.moveToNext()) {
                    buffer.append("DeviceID: " + res.getString(0) + "\n");
                    buffer.append("Uid: " + res.getString(1) + "\n");
                    buffer.append("Devicename: " + res.getString(2) + "\n");
                    buffer.append("Devicestate: " + res.getString(3) + "\n");
                    buffer.append("alarmSet: " + res.getString(4) + "\n\n");
                }
            } else if (tablename.equals("USERDETAILS")) {
                while (res.moveToNext()) {
                    buffer.append("Uid: " + res.getString(0) + "\n");
                    buffer.append("Ufname: " + res.getString(1) + "\n");
                    buffer.append("Ulame: " + res.getString(2) + "\n");
                    buffer.append("Uemail: " + res.getString(3) + "\n");
                    buffer.append("Ugivenname: " + res.getString(4) + "\n");
                    buffer.append("Uimage: " + res.getString(5) + "\n\n");
                }
            }
            //show all data
            showMessage("Data", buffer.toString());
        }catch (Exception e){
            showMessage("Error", "Table not created! or aOther error");
        }
    }
    public void showMessage(String title, String Messages){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messages);
        builder.show();
    }

    //=====================end===================================

    public void signOut() {
        Toast.makeText(this, "Sing out", Toast.LENGTH_SHORT).show();
        fire = new FirebaseHandler(this);
        //dbHelper = new DbHandler(this);
        //String queary = "DELETE FROM USERDETAILS WHERE Uid='"+uid+"'";
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
    }

    public void refresh(){
        startActivity(getIntent());
        finish();
    }


//    public void buildMessage(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainDevicesHome.this);
//        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                dbHelper.deleteDevice(viewHolder.itemView.getTag().toString(),MainDevicesHome.this);
////                refresh();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                refresh();
//            }
//        });
//        builder.setIcon(R.drawable.delete);
//        builder.setTitle("Device Remove");
//        builder.setMessage("If you want to really delete your device. All settings will be removed can not undo.!");
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

}

