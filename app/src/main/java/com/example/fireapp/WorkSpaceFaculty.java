package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WorkSpaceFaculty extends AppCompatActivity implements View.OnClickListener{

    private boolean doubleBackToExitPressedOnce;
    Button b1,b2,b3,b4,b5,b6;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_space_faculty);
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);
        b4=findViewById(R.id.button4);
        b5=findViewById(R.id.button5);
        b6=findViewById(R.id.button6);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), WorkSpaceFaculty.class));//DASHBOARD
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.mail:
                        startActivity(new Intent(getApplicationContext(), info_activity.class));//DASHBOARD
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        sp = getSharedPreferences
                ("mycomments", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.logout){

            Intent myintent = new Intent(WorkSpaceFaculty.this, HomeActivity.class);
            startActivity(myintent);
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view)
    {
        if(view.getId()==b1.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b1.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
        if(view.getId()==b2.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b2.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
        if(view.getId()==b3.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b3.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
        if(view.getId()==b4.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b4.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
        if(view.getId()==b5.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b5.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
        if(view.getId()==b6.getId())
        {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("elective",b6.getText().toString());
            edit.commit();
            startActivity(new Intent(getApplicationContext(), FacCom.class));
        }
    }

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent start = new Intent(WorkSpaceFaculty.this, HomeActivity.class);
            startActivity(start);
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to Log Out", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
