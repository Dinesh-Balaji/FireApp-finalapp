package com.example.fireapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Cstmlstadptr extends ArrayAdapter<reviews>
{
    private static final String TAG="Cstmlstadptr";
    private Context mContext;
    int mResource;
    public Cstmlstadptr(Context context, int resource, ArrayList<reviews> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource=resource;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //get the persons information
        String ts = getItem(position).getTime();
        String cmt = getItem(position).getComment();

        //Create the person object with the information
        reviews person = new reviews(ts, cmt);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvts=convertView.findViewById(R.id.tsid);
        TextView tvcmt=convertView.findViewById(R.id.cmtid);
        tvts.setText(ts);
        tvcmt.setText(cmt);
        return convertView;
    }
}
