package com.example.user.sharingcaring.Category;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.user.sharingcaring.Adapters.Blood_Category_Adapter;
import com.example.user.sharingcaring.BloodCategories.ABpositive;
import com.example.user.sharingcaring.BloodCategories.Apositive;
import com.example.user.sharingcaring.BloodCategories.Bnegative;
import com.example.user.sharingcaring.BloodCategories.Bpositive;
import com.example.user.sharingcaring.BloodCategories.Onegative;
import com.example.user.sharingcaring.BloodCategories.Opositive;
import com.example.user.sharingcaring.MainPostsActivities.Blood;
import com.example.user.sharingcaring.R;

public class Blood_Category extends AppCompatActivity {

    GridView gridView;

    String[] groups_name={"A+","AB+","O+","O-","B+","B-"};
    Toolbar toolbar;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood__category);

        toolbar=findViewById(R.id.blood_category_appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gridView=findViewById(R.id.blood_category_gridView);

        Animation animation=AnimationUtils.loadAnimation(this,R.anim.animforbuttontwo);
        GridLayoutAnimationController controller=new GridLayoutAnimationController(animation,.2f,.2f);
        gridView.setLayoutAnimation(controller);

        Blood_Category_Adapter blood_category_adapter=new Blood_Category_Adapter(groups_name,this);

        gridView.setAdapter(blood_category_adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(Blood_Category.this,Apositive.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(Blood_Category.this,ABpositive.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(Blood_Category.this,Opositive.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(Blood_Category.this,Onegative.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(Blood_Category.this,Bpositive.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(Blood_Category.this,Bnegative.class);
                        startActivity(intent5);
                        break;

                }
            }
        });

    }

    public void gotoBlood(){

        Intent intent=new Intent(Blood_Category.this,Blood.class);
        startActivity(intent);
    }
}
