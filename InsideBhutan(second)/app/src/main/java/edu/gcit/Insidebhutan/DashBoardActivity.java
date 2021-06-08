 package edu.gcit.Insidebhutan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashBoardActivity extends Activity {
    private CardView mimage_1,mimage_2,mimage_3,mimage_4,mimage_5 ;
    private FloatingActionButton mfloatingaction,mfloatingaction1;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        mimage_1 = findViewById(R.id.image_1);
        mimage_2 = findViewById(R.id.image_2);
        mimage_3 = findViewById(R.id.image_3);
        mimage_4 = findViewById(R.id.image_4);
        mimage_5 = findViewById(R.id.image_5);
        mfloatingaction = findViewById(R.id.floatingActionButton);
        mfloatingaction1 = findViewById(R.id.floatingActionButton1);

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(intent);
                        break;

//                    case R.id.nav_about:
//                        Intent intent1 = new Intent(getApplicationContext(), About.class);
//                        startActivity(intent1);
//                        break;
//
//                    case R.id.nav_profile:
//                        Intent intent2 = getIntent();
//                        String user_id = intent2.getStringExtra("id");
//                        String user_name = intent2.getStringExtra("name");
//                        String user_email = intent2.getStringExtra("email");
//                        String user_password = intent2.getStringExtra("password");
//                        Intent obj = new Intent(getApplicationContext(), User_Profile.class);
//                        obj.putExtra("name", user_name);
//                        obj.putExtra("email", user_email);
//                        obj.putExtra("id", user_id);
//                        obj.putExtra("password", user_password);
//                        startActivity(obj);
//                        break;
//                    case R.id.nav_logout:
//                        Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent3);
//                        break;

                    case  R.id.nav_share:{
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }
                    break;
                }
                return false;
            }
        });

        mimage_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardActivity.this,image_1.class);
                startActivity(intent);

            }
        });

        mimage_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DashBoardActivity.this,image_2.class);
                startActivity(intent1);

            }
        });

        mimage_3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(DashBoardActivity.this,image_3.class);
                startActivity(intent2);

            }
        });

        mimage_4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(DashBoardActivity.this,image_4.class);
                startActivity(intent3);

            }
        });

        mimage_5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(DashBoardActivity.this,image_5.class);
                startActivity(intent4);

            }
        });

        mfloatingaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(DashBoardActivity.this,postphoto.class);
                startActivity(intent5);
            }
        });

        mfloatingaction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(DashBoardActivity.this,view.class);
                startActivity(intent6);
            }
        });
    }


    public void View(View view) {
        startActivity(new Intent(getApplicationContext(),view.class));
        finish();
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
