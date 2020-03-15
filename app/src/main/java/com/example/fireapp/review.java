package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class review extends AppCompatActivity implements View.OnClickListener{
    EditText e1;
    Button submit;
    Firebase fb;
    SQLiteDatabase db;
    SharedPreferences sp,rsp;
    private String elective;
    DatabaseReference ref;
    FirebaseDatabase database;
    DatabaseReference rateref;
    int x,val,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        e1=findViewById(R.id.comment_id);
        submit=findViewById(R.id.submit_id);
        submit.setOnClickListener(this);
        sp = getSharedPreferences
                ("mycomments", Context.MODE_PRIVATE);
        elective=sp.getString("elective","na").toLowerCase().trim();
        String[] temp=elective.split("\\W+");
        if(temp.length>1)
            elective=temp[0]+"_"+temp[1];
        else
            elective=temp[0];
        //
        dialogbox();
        //
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child(elective);
        ref=ref.child("reviews");
        //
        db = openOrCreateDatabase("commentdb", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS comment(gaali VARCHAR);");
        SharedPreferences sp1 = getSharedPreferences
                ("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = sp1.edit();
        int x=sp1.getInt("df",-1);
        if(x==-1)
        {
//            Toast.makeText(this, Integer.toString(sp.getInt("df",-1)), Toast.LENGTH_SHORT).show();
            edit1.putInt("df", 0);
            edit1.commit();
        }
        x=sp1.getInt("df",-1);
        if(x==0)
        {
//            Toast.makeText(this, Integer.toString(sp.getInt("df",-1)), Toast.LENGTH_SHORT).show();
            gali g=new gali();
            String xyz=g.getGalistr();
            String str[]=xyz.split("\\W+");
            for(int i=0;i<str.length;i++)
            {
                db.execSQL("INSERT INTO comment VALUES('" + str[i] + "');");
            }
            edit1.putInt("df",1);
            edit1.commit();
        }
    }

    @Override
    public void onClick(View view)
    {
        String str=e1.getText().toString().trim();
        str=str.toLowerCase();
        if(str.contains("hello")||str.contains("tity"))
        {
            str=str.replaceAll("hello", " ");
            str=str.replaceAll("tity", " ");
        }
        String xyz="";
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)=='_')
            {
                xyz+=" ";
                continue;
            }
            xyz+=str.charAt(i);
        }
        str=xyz;
        if(str.length()<1)
        {
            Toast.makeText(this,"Empty field!",Toast.LENGTH_SHORT).show();
            return;
        }


        String str1[]=str.split("\\W+");
        for(int i=0;i<str1.length;i++)
        {
            if(str1[i].length()<1)
                str1[i]="god";
        }
        String repf="";
        for(int i=0;i<str1.length;i++)
        {
            str1[i]=str1[i].trim();
            Cursor c = db.rawQuery("SELECT * FROM comment WHERE gaali='" + str1[i] + "'", null);
            if(c.moveToFirst())
            {
                showMessage("Caution", "Words may have been used which may offend someone");
                return;
            }
            int x=1;
            String rep1="";
            String rep2="";
            for(int j=0;j<str1[i].length()-1;j++)
            {
                if(str1[i].charAt(j)==str1[i].charAt(j+1))
                    x++;
                else
                    x=1;
                if(x<=2)
                    rep1+=Character.toString(str1[i].charAt(j));
            }
            if(x<=2)
                rep1+=Character.toString(str1[i].charAt(str1[i].length()-1));
            if(!str1[i].equals(rep1))
            {
                repf += rep1 + " ";
                Cursor d = db.rawQuery("SELECT * FROM comment WHERE gaali='" + rep1 + "'", null);
                if(d.moveToFirst())
                {
                    showMessage("Caution", "Words may have been used which may offend someone");
                    return;
                }
            }
            for(int k=0;k<rep1.length()-1;k++)
            {
                if(rep1.charAt(k)==rep1.charAt(k+1))
                {
                    rep2+=Character.toString(rep1.charAt(++k));
                }
                else
                    rep2+=Character.toString(rep1.charAt(k));
            }
            if(rep1.length()>1)
                if(rep1.charAt(rep1.length()-1)!=rep1.charAt(rep1.length()-2))
                    rep2+=rep1.charAt(rep1.length()-1);
            if(!rep1.equals(rep2))
            {
                repf += rep2 + " ";
                Cursor e = db.rawQuery("SELECT * FROM comment WHERE gaali='" + rep2 + "'", null);
                if(e.moveToFirst())
                {
                    showMessage("Caution", "Words may have been used which may offend someone");
                    return;
                }
            }
        }


//        for(int i=0;i<str1.length;i++)
//        {
//            Cursor c = db.rawQuery("SELECT * FROM comment WHERE gaali='" + str1[i] + "'", null);
//            if(c.moveToFirst())
//            {
//                showMessage("Caution", "Words may have used which may offend someone");
//                return;
//            }
//        }


        String str2="";
        for(int i=0;i<str1.length;i++)
        {
            if(str1[i].contains("ass")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("bra")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("nig")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("nip")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("pud")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("tit")&&str1[i].length()>3)
                continue;
            if(str1[i].contains("wab")&&str1[i].length()>3)
                continue;
            str2+=str1[i];
        }
        str2+=repf;
        if(str2.length()>1)
            str2=slash(str2);
        Cursor d = db.rawQuery("SELECT * FROM comment", null);
        while (d.moveToNext())
        {
            if(str2.contains(d.getString(0)))
            {
                showMessage("Caution", "Words may have been used which may offend someone");
                return;
            }
        }
//        Toast.makeText(this, elective+"", Toast.LENGTH_SHORT).show();
//        user.setStid(str);
//        user.setEmail(email.getText().toString());
//        user.setPassword(password.getText().toString());
//        user.setName(name.getText().toString()+" {}");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateOnly = new SimpleDateFormat("dd-MM-yyyy");
        String day=dateOnly.format(cal.getTime());
        dateOnly = new SimpleDateFormat("HH:mm:ss");
        String time=dateOnly.format(cal.getTime());
        String ts=day+"_"+time;
        reviews sent=new reviews();
        sent.setComment(e1.getText().toString());
        sent.setTime(ts);

        ref.child(sent.getTime()).setValue(sent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(review.this,
                                    "Review added Successfully", Toast.LENGTH_SHORT).show();
                            Intent start = new Intent(review.this, WorkSpaceComment.class);
                            startActivity(start);
                        }else{
                            Toast.makeText(review.this,
                                    "failed...!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public String slash(String str)
    {
        String repf="";
        int x=1;
        String rep1="";
        String rep2="";
        for(int j=0;j<str.length()-1;j++)
        {
            if(str.charAt(j)==str.charAt(j+1))
                x++;
            else
                x=1;
            if(x<=2)
                rep1+=Character.toString(str.charAt(j));
        }
        if(x<=2)
            rep1+=Character.toString(str.charAt(str.length()-1));
        if(!str.equals(rep1))
            repf += rep1 + " ";
        else
            repf += str + " ";
        //
        for(int k=0;k<rep1.length()-1;k++)
        {
            if(rep1.charAt(k)==rep1.charAt(k+1))
            {
                rep2+=Character.toString(rep1.charAt(++k));
            }
            else
                rep2+=Character.toString(rep1.charAt(k));
        }
        if(rep1.length()>1)
            if(rep1.charAt(rep1.length()-1)!=rep1.charAt(rep1.length()-2))
                rep2+=rep1.charAt(rep1.length()-1);
        if(!rep1.equals(rep2))
            repf += rep2 + " ";
        else
            repf += rep1 + " ";
        return repf;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == R.id.logout){

            Intent myintent = new Intent(review.this, WorkSpaceStudent.class);
            startActivity(myintent);
            Toast.makeText(this, "Home Page", Toast.LENGTH_SHORT).show();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogbox()
    {
        rsp=getSharedPreferences("ratecheck",Context.MODE_PRIVATE);
        if(rsp.getInt(elective,-1)==0)
        {
            String[] items= {" 1", " 2", " 3", " 4", " 5"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Rate "+elective+" :");
            builder.setSingleChoiceItems(items,-1,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    x=i+1;
                }
            });
            builder.setPositiveButton("Done!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                                fb = new Firebase("https://fireapp-ea7aa.firebaseio.com/"+elective+"/rating");
                                Firebase fbchild1 = fb.child("avg");
                                Firebase fbchild2 = fb.child("num");
                                Bundle b=getIntent().getExtras();
                                val=b.getInt("val")+x;
                                num=b.getInt("num")+1;
                                fbchild1.setValue(Integer.toString(val));
                                fbchild2.setValue(Integer.toString(num));
                                Toast.makeText(getApplicationContext(),
                                        elective+" rated successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor edit = rsp.edit();
                                edit.putInt(elective,1);
                                edit.commit();
                }
            });
            builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    SharedPreferences.Editor edit = rsp.edit();
                    edit.putInt(elective,2);
                    edit.commit();
                }
            });
            builder.show();

        }
    }

}
