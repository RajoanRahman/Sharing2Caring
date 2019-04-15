package com.example.user.sharingcaring.Category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.user.sharingcaring.Adapters.Department_For_Books;
import com.example.user.sharingcaring.BookCategoriesActivity.ElectricalComputerActivity;
import com.example.user.sharingcaring.BookCategoriesActivity.MarketingEconomicsActivity;
import com.example.user.sharingcaring.BookCategoriesActivity.PharmacyActivity;
import com.example.user.sharingcaring.MainPostsActivities.Book;
import com.example.user.sharingcaring.R;

public class Department_Book_categorry extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    String[] departments_name={"Department of Marketing & Economics","Department of Electronic and Computer Engineering","Department of Pharmacy"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department__book_categorry);

        toolbar=findViewById(R.id.department_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Departments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView=findViewById(R.id.book_department_id);


        //Department_Book_categorry department_book_categorry=new Department_Book_categorry(this,departments_name);

        Department_For_Books department_for_books=new Department_For_Books(this,departments_name);
        listView.setAdapter(department_for_books);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        gotoMarketingAndEco();
                        break;
                    case 1:
                        gotoECSE();
                        break;
                    case 2:
                        gotoPharmacy();
                        break;


                }
            }
        });
    }

    public void gotoPharmacy() {
        Intent bookIntent=new Intent(Department_Book_categorry.this,PharmacyActivity.class);
        startActivity(bookIntent);
    }

    public void gotoECSE() {
        Intent bookIntent=new Intent(Department_Book_categorry.this,ElectricalComputerActivity.class);
        startActivity(bookIntent);
    }

    public void gotoMarketingAndEco(){
        Intent bookIntent=new Intent(Department_Book_categorry.this,MarketingEconomicsActivity.class);
        startActivity(bookIntent);
    }
}
