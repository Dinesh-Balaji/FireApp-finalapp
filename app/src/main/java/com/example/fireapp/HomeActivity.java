package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.fireapp.SignUp.isValidF;
import static com.example.fireapp.SignUp.isValidR;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    EditText studentID,pin;
    private DatabaseReference ref,reff;
    TextView mTextViewRegister;
    Button b1;
    SharedPreferences sp,rsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        studentID = (EditText) findViewById(R.id.studentID);
        pin = (EditText) findViewById(R.id.pin);
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        reff = FirebaseDatabase.getInstance().getReference().child("Fac");
        b1=findViewById(R.id.loginid);
        b1.setOnClickListener(this);
        mTextViewRegister=(TextView)findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(this);
        sp = getSharedPreferences
                ("mycredentials", Context.MODE_PRIVATE);
        String username=sp.getString("Unamehome","na");
        if(!username.equals("na"))
            studentID.setText(username);
        rsp=getSharedPreferences("ratecheck",Context.MODE_PRIVATE);
        ratecheck();
    }

    String PIN;
    public void onClick(View view)
    {
        if(view.getId()==mTextViewRegister.getId())
        {
            Intent  registerIntent=new Intent(HomeActivity.this,SignUp.class);
            startActivity(registerIntent);
            return;
        }
        if(studentID.getText().toString().isEmpty()||pin.getText().toString().isEmpty())
        {
            Toast.makeText(this,
                    "Please fill all entries", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isValidR(studentID.getText().toString().toLowerCase()))
        {
            PIN = pin.getText().toString();

            ref = ref.child(studentID.getText().toString().toLowerCase());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        Users user = dataSnapshot.getValue(Users.class);
                        if (PIN.equals(user.getPassword())) {
                            Toast.makeText(HomeActivity.this,
                                    "Login Successfull", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences
                                    ("mycredentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("Unamehome",studentID.getText().toString());
                            edit.putString("Stid",user.getStid());
                            edit.putString("Email",user.getEmail());
                            edit.putString("Name",user.getName());
                            edit.commit();

                            Intent start = new Intent(HomeActivity.this, WorkSpaceStudent.class);
                            startActivity(start);
                        }
                        else {
                            Toast.makeText(HomeActivity.this,
                                    "Enter Correct Pin...!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(HomeActivity.this,
                                "Error logging in...!", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }
        else
        {
            if(isValidF(studentID.getText().toString()))
            {
                PIN = pin.getText().toString();

                reff = reff.child(studentID.getText().toString());

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            Fac fac = dataSnapshot.getValue(Fac.class);
                            if (PIN.equals(fac.getPassword())) {
                                Toast.makeText(HomeActivity.this,
                                        "Login Successfull", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences
                                        ("mycredentials", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("Facid",fac.getFacid());
                                edit.putString("Unamehome",fac.getFacid());
                                edit.putString("Fname",fac.getName());
                                edit.commit();

                                Intent start = new Intent(HomeActivity.this, WorkSpaceFaculty.class);
                                startActivity(start);
                            }
                            else {
                                Toast.makeText(HomeActivity.this,
                                        "Enter Correct Pin...!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(HomeActivity.this,
                                    "Error logging in...!", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
            else
            {
                Toast.makeText(this,
                        "Please enter a valid college ID", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void ratecheck()
    {
        SharedPreferences.Editor edit = rsp.edit();
        if(rsp.getInt("android",-1)==1)
        {

        }
        else
        {
            edit.putInt("android",0);
        }
        if(rsp.getInt("biometrics",-1)==1)
        {

        }
        else
        {
            edit.putInt("biometrics",0);
        }
        if(rsp.getInt("cloud_computing",-1)==1)
        {

        }
        else
        {
            edit.putInt("cloud_computing",0);
        }
        if(rsp.getInt("neural_networks",-1)==1)
        {

        }
        else
        {
            edit.putInt("neural_networks",0);
        }
        if(rsp.getInt("game_theory",-1)==1)
        {

        }
        else
        {
            edit.putInt("game_theory",0);
        }
        if(rsp.getInt("pattern_recognition",-1)==1)
        {

        }
        else
        {
            edit.putInt("pattern_recognition",0);
        }
        edit.commit();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
