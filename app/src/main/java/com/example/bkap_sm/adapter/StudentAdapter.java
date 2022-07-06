package com.example.bkap_sm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bkap_sm.R;
import com.example.bkap_sm.dao.IClassDAO;
import com.example.bkap_sm.dao.ImplClassDAO;
import com.example.bkap_sm.entity.Classes;
import com.example.bkap_sm.entity.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context mCtx;
    private List<Student> mLst;
    public StudentAdapter(@NonNull Context context, @NonNull List<Student> objects) {
        super(context, R.layout.item_student, objects);
        this.mCtx = context;
        this.mLst = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if(item == null){
            item = LayoutInflater.from(mCtx).inflate(R.layout.item_student, null);
        }

        Student st = mLst.get(position);

        TextView txtClass = item.findViewById(R.id.itemClass);
        TextView txtName = item.findViewById(R.id.itemStudentName);
        TextView txtPhone = item.findViewById(R.id.itemPhone);
        TextView txtEmail = item.findViewById(R.id.itemEmail);
        ImageView imgGender = item.findViewById(R.id.itemGender);

        IClassDAO cDAO = new ImplClassDAO(mCtx);
        Classes c = cDAO.detail(st.getClassId());
        txtClass.setText(c.getName());
        txtName.setText(st.getName());
        txtPhone.setText(st.getPhone());
        txtEmail.setText(st.getEmail());

        if(st.isGender() == true){
            imgGender.setImageResource(R.drawable.male);
        }else{
            imgGender.setImageResource(R.drawable.female);
        }
        return item;
    }
}
