package com.example.bkap_sm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bkap_sm.R;
import com.example.bkap_sm.adapter.StudentAdapter;
import com.example.bkap_sm.dao.IStudentDAO;
import com.example.bkap_sm.dao.ImplStudentDAO;
import com.example.bkap_sm.dialog.DialogSearch;
import com.example.bkap_sm.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogSearch.ICallback{

    private List<Student> mLst = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadStudent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuHome:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuAddStudent:
                intent = new Intent(this, ManageStudentActivity.class);
                startActivity(intent);
                break;
            case R.id.menuClass:

                break;
            case R.id.menuSearch:
                DialogSearch ds = new DialogSearch(MainActivity.this);
                ds.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadStudent(){
        IStudentDAO stDAO = new ImplStudentDAO(this);
        mLst = stDAO.selectAll();
        StudentAdapter adapter = new StudentAdapter(this, mLst);
        ListView listView = findViewById(R.id.lstViewStudent);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idSt = mLst.get(position).getId();
                Intent intent = new Intent(MainActivity.this, ManageStudentActivity.class);
                intent.putExtra("idSt", idSt);
                startActivity(intent);
            }
        });
    }

    private void searchStudent(int id, String name){
        IStudentDAO stDAO = new ImplStudentDAO(this);
        mLst = stDAO.search(id, name);
        if(mLst.isEmpty()){
            Toast.makeText(this, "Thông tin sinh viên không đúng, xin mời nhập lại", Toast.LENGTH_SHORT).show();
            loadStudent();
        }else{
            StudentAdapter adapter = new StudentAdapter(this, mLst);
            ListView listView = findViewById(R.id.lstViewStudent);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int idSt = mLst.get(position).getId();
                    Intent intent = new Intent(MainActivity.this, ManageStudentActivity.class);
                    intent.putExtra("idSt", idSt);
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public void search(int id, String name) {
        searchStudent(id, name);

    }
}