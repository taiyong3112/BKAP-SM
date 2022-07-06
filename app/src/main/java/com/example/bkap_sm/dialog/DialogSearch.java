package com.example.bkap_sm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bkap_sm.R;

public class DialogSearch extends Dialog {
    private Context mCtx;

    private ICallback callback;

    public interface ICallback {
        public void search(int id, String name);
    }

    public DialogSearch(@NonNull Context context) {
        super(context);
        this.mCtx = context;
        this.callback = (ICallback) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srId = ((EditText) findViewById(R.id.edtSearchId)).getText().toString();
                String searchName = ((EditText) findViewById(R.id.edtSearchName)).getText().toString();
                if(srId.matches("") || searchName.matches("")){
                    Toast.makeText(mCtx, "Xin mời nhập đầy đủ thông tin để tìm kiếm", Toast.LENGTH_SHORT).show();
                }else {
                    int searchId = Integer.parseInt(srId);
                    callback.search(searchId, searchName);
                    dismiss();
                }
            }
        });
    }
}
