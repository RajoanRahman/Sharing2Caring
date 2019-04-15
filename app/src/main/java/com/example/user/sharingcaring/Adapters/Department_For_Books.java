package com.example.user.sharingcaring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sharingcaring.R;

public class Department_For_Books extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    String[] departments_name;


    public Department_For_Books(Context context, String[] departments_name) {
        this.context = context;
        this.departments_name = departments_name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] getDepartments_name() {
        return departments_name;
    }

    public void setDepartments_name(String[] departments_name) {
        this.departments_name = departments_name;
    }

    @Override
    public int getCount() {
        return departments_name.length;
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
            convertView=inflater.inflate(R.layout.department_book_category,parent,false);
        }

        //ImageView imageView=(convertView).findViewById(R.id.allworkout_imageId);
        //imageView.setImageResource(workoutImages[position]);

        TextView textView=(convertView ).findViewById(R.id.department_wise_book_text);
        textView.setText(departments_name[position]);

        return convertView;
    }

}
