package com.example.finalyearprojects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Semester_List extends AppCompatActivity {

    private ListView listView;
    private String semester1[] = {"Semester-1", "Semester-2", "Semester-3", "Semester-4","Semester-5", "Semester-6"};
    private String semester2[] = {"Semester-1", "Semester-2", "Semester-3", "Semester-4","Semester-5", "Semester-6","Semester-7", "Semester-8"};
    private ArrayAdapter<String> adapter;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester__list);
        str = getIntent().getStringExtra("course");
        listView = findViewById(R.id.listView);

        if(str.equalsIgnoreCase("btech"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester2);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("mca"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }

        else if(str.equalsIgnoreCase("bca"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("msc"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("bsc"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("mcom"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("bcom"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("ma"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
        else if(str.equalsIgnoreCase("ba"))
        {
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.semester_list_view,R.id.txtView,semester1);
            listView.setAdapter(adapter);
            return;
        }
    }
}
