package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FacCom extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sp;
    ListView lv;
    DatabaseReference ref;
    FirebaseListAdapter adapter;
    ArrayList<String> tsal,cmtal;
    Button b1;
    private String elective,val,num;
    private DatabaseReference rateref;
    Float rateitup;
    RatingBar r;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_com);
        sp = getSharedPreferences
                ("mycomments", Context.MODE_PRIVATE);
        elective=sp.getString("elective","na");
        String[] temp=elective.split("\\W+");
        if(temp.length>1)
            elective=temp[0]+"_"+temp[1];
        else
            elective=temp[0];
        elective=elective.toLowerCase();
        tsal=new ArrayList<>();
        cmtal=new ArrayList<>();
        lv=findViewById(R.id.commentlisid);
        ref=FirebaseDatabase.getInstance().getReference().child(elective);
        ref=ref.child("reviews");
        Query query=ref;
        FirebaseListOptions<reviews> options=new FirebaseListOptions.Builder<reviews>()
                .setLayout(R.layout.comment)
                .setQuery(query,reviews.class)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position)
            {
                TextView ts=v.findViewById(R.id.tsid);
                TextView cmt=v.findViewById(R.id.cmtid);
                reviews user=(reviews) model;
                ts.setText(user.getTime());
                cmt.setText(user.getComment());
                tsal.add(user.getTime());
                cmtal.add(user.getComment()); }
        };
        lv.setAdapter(adapter);
        b1=findViewById(R.id.addcomid);
        b1.setOnClickListener(this);
        lv.setAdapter(adapter);
        r=(RatingBar)findViewById(R.id.starbarid);
        t1=findViewById(R.id.starvalueid);
        t2=findViewById(R.id.ratedbyid);
        rateref = FirebaseDatabase.getInstance().getReference().child(elective);
        rateref=rateref.child("rating");
        rateref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    rating rr = dataSnapshot.getValue(rating.class);
                    val=rr.getAvg();
                    num=rr.getNum();
                    if(Integer.parseInt(num)>0)
                    {
                        rateitup = (Float.parseFloat(val)) / (Float.parseFloat(num));
                        r.setRating(rateitup);
                        String q = rateitup.toString();
                        t1.setText(q.substring(0,3));
                        t2.setText("- by "+num+" Users");
                    }
                    else
                    {

                    }
                }
                else
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                ArrayList<String> idr,namer;
                idr=new ArrayList<>();
                namer=new ArrayList<>();
                for(int i=0;i<tsal.size();i++)
                {
                    if(tsal.get(i).contains(s))
                    {
                        idr.add(tsal.get(i));
                        namer.add(cmtal.get(i));
                    }
                }
                for(int i=0;i<cmtal.size();i++)
                {
                    if(cmtal.get(i).contains(s))
                    {
                        idr.add(tsal.get(i));
                        namer.add(cmtal.get(i));
                    }
                }
                update(idr,namer);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void update(ArrayList<String> idr, ArrayList<String> namer)
    {
        idr=removeDuplicates(idr);
        namer=removeDuplicates(namer);
        reviews[] user=new reviews[idr.size()];
        for(int i=0;i<idr.size();i++)
        {
            user[i]=new reviews(idr.get(i),namer.get(i));
        }
        ArrayList<reviews> ual=new ArrayList<>();
        for(int i=0;i<idr.size();i++)
        {
            ual.add(user[i]);
        }
        Cstmlstadptr adapter1=new Cstmlstadptr(this,R.layout.comment,ual);
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), WorkSpaceFaculty.class));
        this.finish();
    }

    @Override
    public void onClick(View view)
    {
        startActivity(new Intent(getApplicationContext(), facaddcom.class));
    }
}
