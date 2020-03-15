package com.example.fireapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Users>
{
    private static final String TAG="CustomListAdapter";
    private Context mContext;
    int mResource;

    public CustomListAdapter(Context context, int resource, ArrayList<Users> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        //get the persons information
        String id = getItem(position).getStid();
        String name = getItem(position).getName();
        String email = getItem(position).getEmail();

        //Create the person object with the information
        Users person = new Users(id, name, email);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvstid=convertView.findViewById(R.id.stid);
        TextView tvname=convertView.findViewById(R.id.nameid);
        TextView tvemail=convertView.findViewById(R.id.emailid);
        tvstid.setText(id);
        tvname.setText(name);
        tvemail.setText(email);
        return convertView;
    }
    }
