package com.example.fireapp;
import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import android.app.Application;

public class FireApp extends Application {
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);

        if(!FirebaseApp.getApps(this).isEmpty())
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
