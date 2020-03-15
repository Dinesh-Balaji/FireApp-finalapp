package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class info_activity extends AppCompatActivity implements View.OnClickListener{
    EditText e1;
    ListView lv;
    TextView t1;
    Button b1,b2;
    FirebaseListAdapter adapter;
    ArrayList<String> idal,nameal,emailal,etext;
    boolean doubleBackToExitPressedOnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_activity);

        doubleBackToExitPressedOnce=false;
        lv=findViewById(R.id.listid);
        e1=findViewById(R.id.extramailid);
        t1=findViewById(R.id.mailboxid);
        b1=findViewById(R.id.addid);
        b1.setOnClickListener(this);
        b2=findViewById(R.id.submitid);
        b2.setOnClickListener(this);
        idal=new ArrayList<>();
        nameal=new ArrayList<>();
        emailal=new ArrayList<>();
        etext=new ArrayList<>();
        Query query=FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseListOptions<Users> options=new FirebaseListOptions.Builder<Users>()
                .setLayout(R.layout.student)
                .setQuery(query,Users.class)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position)
            {
                TextView stid=v.findViewById(R.id.stid);
                TextView name=v.findViewById(R.id.nameid);
                TextView email=v.findViewById(R.id.emailid);
                Users user=(Users) model;
                stid.setText(user.getStid());
                name.setText(user.getName());
                email.setText(user.getEmail());
                idal.add(user.getStid());
                nameal.add(user.getName().toLowerCase());
                emailal.add(user.getEmail());
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
               Users u= (Users) lv.getItemAtPosition(i);
                if(t1.getText().toString().contains(u.getEmail()))
                {
                    updateE(u.getEmail());
                    return;
                }
                if(t1.getText().toString().equals("Email-IDs here..."))
                    t1.setText("");
                etext.add(u.getEmail());
                t1.setText(t1.getText().toString()+u.getEmail()+",");
               //Toast.makeText(getApplicationContext(),""+u.getEmail(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchMenu);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search student (Don't use caps)");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                ArrayList<String> idr,namer,emailr;
                idr=new ArrayList<>();
                namer=new ArrayList<>();
                emailr=new ArrayList<>();
               for(int i=0;i<idal.size();i++)
               {
                   if(idal.get(i).contains(s))
                   {
                       idr.add(idal.get(i));
                       namer.add(nameal.get(i));
                       emailr.add(emailal.get(i));
                   }
               }
                for(int i=0;i<nameal.size();i++)
                {
                    if(nameal.get(i).contains(s))
                    {
                        idr.add(idal.get(i));
                        namer.add(nameal.get(i));
                        emailr.add(emailal.get(i));
                    }
                }
                update(idr,namer,emailr);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void update(ArrayList<String> idr, ArrayList<String> namer, ArrayList<String> emailr)
    {
        idr=removeDuplicates(idr);
        namer=removeDuplicates(namer);
        emailr=removeDuplicates(emailr);
        Users[] user=new Users[idr.size()];
        for(int i=0;i<idr.size();i++)
        {
            user[i]=new Users(idr.get(i),namer.get(i),emailr.get(i));
        }
        ArrayList<Users> ual=new ArrayList<>();
        for(int i=0;i<idr.size();i++)
        {
            ual.add(user[i]);
        }
        CustomListAdapter adapter1=new CustomListAdapter(this,R.layout.student,ual);
        lv.setAdapter(adapter1);
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        Set<T> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==b1.getId())
        {
            if (e1.getText().toString().isEmpty())
                return;
            if (!isValid(e1.getText().toString().toLowerCase())) {
                Toast.makeText(this, "Please enter a valid email_ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkcheck(e1.getText().toString()))
                return;
            if (t1.getText().toString().equals("Email-IDs here..."))
                t1.setText("");
            t1.setText(t1.getText().toString() + e1.getText().toString() + ",");
            etext.add(e1.getText().toString());
        }
        if(view.getId()==b2.getId())
        {
            String fstr=t1.getText().toString().trim();
            fstr=fstr.substring(0,fstr.length()-1);
            if(t1.getText().toString().equals("Email-IDs here..."))
            {
                Toast.makeText(this,"Enter atleast one mail-id.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent mailintent=new Intent(this,feedback_activity.class);
            mailintent.putExtra("mails",fstr);
            startActivity(mailintent);
        }
    }

    static boolean isValid(String email)
    {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean checkcheck(String s)
    {
        String[] str=t1.getText().toString().split(",");
        for (int i=0;i<str.length;i++)
        {
            if(s.equals(str[i]))
                return true;
        }
        return false;
    }

    public void updateE(String s)
    {
        etext.remove(s);
        if(etext.isEmpty())
        {
            t1.setText("Email-IDs here...");
            return;
        }
        t1.setText("");
        for(int i=0;i<etext.size();i++)
        {
            t1.setText(t1.getText()+etext.get(i)+",");
        }
    }

    public void onBackPressed() {
        Intent start = new Intent(info_activity.this, WorkSpaceFaculty.class);
        startActivity(start);
    }
}
