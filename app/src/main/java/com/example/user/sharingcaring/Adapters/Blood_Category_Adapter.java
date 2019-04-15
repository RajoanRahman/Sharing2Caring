package com.example.user.sharingcaring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.sharingcaring.R;

public class Blood_Category_Adapter extends BaseAdapter {

    String[] groups_name;
    Context context;
    LayoutInflater inflater;


    public Blood_Category_Adapter(String[] groups_name, Context context) {
        this.groups_name = groups_name;
        this.context = context;
    }


    public String[] getGroups_name() {
        return groups_name;
    }

    public void setGroups_name(String[] groups_name) {
        this.groups_name = groups_name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return groups_name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.blood_category,parent,false);
        }

        //ImageView imageView=(convertView).findViewById(R.id.allworkout_imageId);
        //imageView.setImageResource(workoutImages[position]);

        TextView textView=(convertView ).findViewById(R.id.blood_grp_text_id);
        textView.setText(groups_name[position]);

        return convertView;

        //return null;
    }
}
