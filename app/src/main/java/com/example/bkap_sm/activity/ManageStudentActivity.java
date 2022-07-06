package com.example.bkap_sm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bkap_sm.R;
import com.example.bkap_sm.dao.IClassDAO;
import com.example.bkap_sm.dao.IStudentDAO;
import com.example.bkap_sm.dao.ImplClassDAO;
import com.example.bkap_sm.dao.ImplStudentDAO;
import com.example.bkap_sm.entity.Classes;
import com.example.bkap_sm.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManageStudentActivity extends AppCompatActivity {

    private int currStId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);

        // bật Dialog chọn ngày tháng
        EditText txtBirthday = findViewById(R.id.txtBirthday);
        txtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog((EditText) v);
            }
        });

        //set danh sách lớp lên spinner
        IClassDAO cDAO = new ImplClassDAO(this);
        List<Classes> lstClasses = cDAO.selectAll();
        ArrayAdapter<Classes> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lstClasses);
        Spinner spClass = findViewById(R.id.spnClass);
        spClass.setAdapter(adapter);

        //khai báo các button
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);

        //set trường hợp có truyền id từ listview không
        if (getIntent().getExtras() != null) {
            btnAdd.setEnabled(false);
            currStId = getIntent().getExtras().getInt("idSt");

            IStudentDAO stDAO = new ImplStudentDAO(ManageStudentActivity.this);
            Student st = stDAO.detail(currStId);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

            EditText txtStudentName = findViewById(R.id.txtStudentName);
            EditText txtEmail = findViewById(R.id.txtEmail);
            EditText txtPhone = findViewById(R.id.txtPhone);
            RadioButton rdoMale = findViewById(R.id.rdoMale);
            RadioButton rdoFemale = findViewById(R.id.rdoFemale);

            int pos = 0;
            for (int i = 0; i < lstClasses.size(); i++) {
                if (lstClasses.get(i).getId() == st.getClassId()) {
                    pos = i;
                    break;
                }
            }
            spClass.setSelection(pos);
            txtStudentName.setText(st.getName());
            if (st.isGender() == true) {
                rdoMale.setChecked(true);
            } else if (st.isGender() == false) {
                rdoFemale.setChecked(true);
            }
            txtEmail.setText(st.getEmail());
            txtBirthday.setText(fmt.format(st.getBirthday()));
            txtPhone.setText(st.getPhone());

            btnUpdate.setOnClickListener(listenerUpdate);
            btnDelete.setOnClickListener(listenerDelete);
        } else {
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
            btnAdd.setOnClickListener(listenerAdd);
        }


    }

    private void openDialog(EditText v) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                v.setText(fmt.format(c.getTime()));
            }
        };
        DatePickerDialog pickerDialog = new DatePickerDialog(this, listener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    private View.OnClickListener listenerAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            IStudentDAO stDAO = new ImplStudentDAO(ManageStudentActivity.this);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

            Spinner spnClass = findViewById(R.id.spnClass);
            Classes c = (Classes) spnClass.getSelectedItem();
            try {
                String studentName = ((EditText) findViewById(R.id.txtStudentName)).getText().toString();
                RadioButton rdoMale = findViewById(R.id.rdoMale);
                RadioButton rdoFemale = findViewById(R.id.rdoFemale);
                Boolean gender = false;
                if (rdoMale.isChecked()) {
                    gender = true;
                } else if (rdoFemale.isChecked()) {
                    gender = false;
                }
                String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
                Date birthday = fmt.parse(((EditText) findViewById(R.id.txtBirthday)).getText().toString());
                String phone = ((EditText) findViewById(R.id.txtPhone)).getText().toString();
                Student st = new Student(c.getId(), studentName, gender, email, birthday, phone);
                boolean isOK = stDAO.insert(st);
                if (isOK) {
                    Toast.makeText(ManageStudentActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ManageStudentActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManageStudentActivity.this, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener listenerUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IStudentDAO stDAO = new ImplStudentDAO(ManageStudentActivity.this);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String stName = ((EditText) findViewById(R.id.txtStudentName)).getText().toString();
                Spinner spClass = findViewById(R.id.spnClass);
                Classes cl = (Classes) spClass.getSelectedItem();
                RadioButton rdoMale = findViewById(R.id.rdoMale);
                RadioButton rdoFemale = findViewById(R.id.rdoFemale);
                boolean gender = false;
                if (rdoMale.isChecked()) {
                    gender = true;
                } else if (rdoFemale.isChecked()) {
                    gender = false;
                }
                String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
                Date birthday = fmt.parse(((EditText) findViewById(R.id.txtBirthday)).getText().toString());
                String phone = ((EditText) findViewById(R.id.txtPhone)).getText().toString();
                Student std = new Student(currStId, cl.getId(), stName, gender, email, birthday, phone);
                boolean isOk = stDAO.update(std);
                if (isOk) {
                    Toast.makeText(ManageStudentActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ManageStudentActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManageStudentActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener listenerDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            IStudentDAO stDAO = new ImplStudentDAO(ManageStudentActivity.this);
            Student st = stDAO.detail(currStId);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageStudentActivity.this);
            alertDialog.setTitle("Quản lý sinh viên");
            alertDialog.setMessage("Bạn có chắc chắn xóa dữ liệu sinh viên " + st.getName() + " MS là " + st.getId() + "?");

            alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    stDAO.delete(currStId);
                    Intent intent = new Intent(ManageStudentActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }
    };
}