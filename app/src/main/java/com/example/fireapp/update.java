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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update extends AppCompatActivity implements View.OnClickListener {
    TextView t1, t2, t3;
    CheckBox c1, c2, c3, c4, c5, c6;
    Firebase fb;
    private String name;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), update.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), WorkSpaceStudent.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        Toast.makeText(this,
                "Caution: If you want to update your elective history, please select all electives again.", Toast.LENGTH_LONG).show();
        sp = getSharedPreferences
                ("mycredentials", Context.MODE_PRIVATE);
        t1 = (TextView) findViewById(R.id.pid1);
        t2 = (TextView) findViewById(R.id.pid2);
        t3 = (TextView) findViewById(R.id.pid3);
        t1.setText(sp.getString("Stid", "Error"));
        t2.setText(sp.getString("Email", "Error"));
        t3.setText(sp.getString("Name", "Error"));
        c1 = (CheckBox) findViewById(R.id.checkid1);
        c2 = (CheckBox) findViewById(R.id.checkid2);
        c3 = (CheckBox) findViewById(R.id.checkid3);
        c4 = (CheckBox) findViewById(R.id.checkid4);
        c5 = (CheckBox) findViewById(R.id.checkid5);
        c6 = (CheckBox) findViewById(R.id.checkid6);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
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

            Intent myintent = new Intent(update.this, HomeActivity.class);
            startActivity(myintent);
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onclickbuttonMethod(View view) {
        fb = new Firebase("https://fireapp-ea7aa.firebaseio.com/Users/" + t1.getText().toString().substring(8));
        name = sp.getString("Name", "Error").substring(0, sp.getString("Name", "Error").indexOf('{') + 1);
        if (c1.isChecked()) {
            name += c1.getText().toString().trim() + ",";
        }
        if (c2.isChecked()) {
            name += c2.getText().toString().trim() + ",";
        }
        if (c3.isChecked()) {
            name += c3.getText().toString().trim() + ",";
        }
        if (c4.isChecked()) {
            name += c4.getText().toString().trim() + ",";
        }
        if (c5.isChecked()) {
            name += c5.getText().toString().trim() + ",";
        }
        if (c6.isChecked()) {
            name += c6.getText().toString().trim() + ",";
        }
        if (name.equals(t3.getText().toString().substring(0, t3.getText().toString().indexOf('{') + 1))) {
            Toast.makeText(this,
                    "Please select atleast one feild to get updated", Toast.LENGTH_SHORT).show();
            return;
        }
        name = name.substring(0, name.length() - 1);
        name += "}";
        t3.setText(name);
        Firebase fbchild = fb.child("name");
        fbchild.setValue(name);
        Toast.makeText(this,
                "Student updated successfully", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("Name", name);
        edit.commit();
    }

    public void onBackPressed() {
        Intent start = new Intent(update.this, WorkSpaceStudent.class);
        startActivity(start);
    }

    @Override
    public void onClick(View view) {

    }
}