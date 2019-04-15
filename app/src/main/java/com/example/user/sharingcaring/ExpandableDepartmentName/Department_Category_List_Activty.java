package com.example.user.sharingcaring.ExpandableDepartmentName;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.user.sharingcaring.BookCategoriesActivity.ElectricalComputerActivity;
import com.example.user.sharingcaring.BookCategoriesActivity.MarketingEconomicsActivity;
import com.example.user.sharingcaring.BookCategoriesActivity.PharmacyActivity;
import com.example.user.sharingcaring.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Department_Category_List_Activty extends AppCompatActivity {

    Toolbar toolbar;
    private ExpandableListView listView;
    private Expandable_list_Adapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department__category__list__activty);

        toolbar=findViewById(R.id.department_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Departments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ExpandableListView)findViewById(R.id.lvExp);
        initData();
        listAdapter = new Expandable_list_Adapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               final String selected=(String) listAdapter.getChild(
                       groupPosition, childPosition);

               switch (selected){
                   case "Department of Marketing & Economics":
                       Intent intent=new Intent(Department_Category_List_Activty.this,MarketingEconomicsActivity.class);
                       startActivity(intent);
                       break;
                   case "Department of Electronic and Computer Engineering":
                       Intent intent1=new Intent(Department_Category_List_Activty.this,ElectricalComputerActivity.class);
                       startActivity(intent1);
                       break;
                   case "Department of Pharmacy":
                       Intent intent2=new Intent(Department_Category_List_Activty.this,PharmacyActivity.class);
                       startActivity(intent2);
                       break;
               }
                return true;
            }
        });

    }

    private void initData() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Departments");


        List<String> departmentDev = new ArrayList<>();
        departmentDev.add("Department of Marketing & Economics");
        departmentDev.add("Department of Electronic and Computer Engineering");
        departmentDev.add("Department of Pharmacy");



        listHash.put(listDataHeader.get(0),departmentDev);

    }
}
