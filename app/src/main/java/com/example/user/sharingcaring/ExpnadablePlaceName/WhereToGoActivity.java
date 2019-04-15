package com.example.user.sharingcaring.ExpnadablePlaceName;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.user.sharingcaring.ExpandableDepartmentName.Expandable_list_Adapter;
import com.example.user.sharingcaring.MainPostsActivities.DhanmondiRidePosts;
import com.example.user.sharingcaring.MainPostsActivities.JatrabariRidePosts;
import com.example.user.sharingcaring.MainPostsActivities.MirpurRidePosts;
import com.example.user.sharingcaring.MainPostsActivities.OldDhakaRidePost;
import com.example.user.sharingcaring.MainPostsActivities.UttoraRidePosts;
import com.example.user.sharingcaring.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WhereToGoActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ExpandableListView listView;
    private Expandable_list_Adapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_to_go);

        toolbar=findViewById(R.id.place_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Where to go");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ExpandableListView)findViewById(R.id.lvExp1);
        initData();
        listAdapter = new Expandable_list_Adapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                final String selected = (String) listAdapter.getChild(
                        groupPosition, childPosition);

                Intent intent;
                switch (selected){
                    case "Dhanmondi Area":
                        Intent intent1=new Intent(WhereToGoActivity.this,DhanmondiRidePosts.class);
                        startActivity(intent1);
                        break;
                    case "Mirpur Area":
                        Intent intent2=new Intent(WhereToGoActivity.this,MirpurRidePosts.class);
                        startActivity(intent2);
                        break;
                    case "Old Dhaka Area":
                        Intent intent3=new Intent(WhereToGoActivity.this,OldDhakaRidePost.class);
                        startActivity(intent3);
                        break;
                    case "Uttora Area":
                        Intent intent5=new Intent(WhereToGoActivity.this,UttoraRidePosts.class);
                        startActivity(intent5);
                        break;
                    case "Jatrabari Area":
                        Intent intent6=new Intent(WhereToGoActivity.this,JatrabariRidePosts.class);
                        startActivity(intent6);
                        break;
                }
                return true;
            }
        });
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Destination");

        List<String> placeDev = new ArrayList<>();
        placeDev.add("Mirpur Area");
        placeDev.add("Uttora Area");
        placeDev.add("Old Dhaka Area");
        placeDev.add("Jatrabari Area");
        placeDev.add("Dhanmondi Area");


        listHash.put(listDataHeader.get(0),placeDev);

    }
}
