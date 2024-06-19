package com.nguyentrananhtan.ontapthi;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyentrananhtan.ontapthi.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
//    ListView listView;
    private List<String> list;
    private ArrayAdapter adap;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PrepareDATABASE();
        loadDB();
        addEvent();

    }
    private  void PrepareDATABASE(){
        database = openOrCreateDatabase("SinhVienDB", Context.MODE_PRIVATE,null);
//       database.execSQL(" DELETE FROM sinhvien");
        database.execSQL(" CREATE TABLE IF NOT EXISTS sinhvien(id INTERGER PRIMARY KEY NOT NULL, name NVARCHAR(150) NOT NULL, lop NVARCHAR(150) NOT NULL) ");
    }
    private void Insert(String id, String name, String lop){

        try {
            database = openOrCreateDatabase("SinhVienDB", Context.MODE_PRIVATE,null);
            database.execSQL("INSERT INTO sinhvien(id,name,lop) VALUES (" + id + ",'" + name +"','" + lop +"')");
//            SQLiteStatement sta = database.compileStatement("INSERT INTO sinhvien(id,name,lop) VALUES (?,?,?)");
//            sta.bindString(1,id);
//            sta.bindString(2,name);
//            sta.bindString(3,lop);
//            sta.execute();
            Toast.makeText(MainActivity2.this,"saved",Toast.LENGTH_LONG).show();
            loadDB();
        }
        catch (Exception e){
            Toast.makeText(MainActivity2.this,"Trung id sinh vien",Toast.LENGTH_LONG).show();
        }

    }
    private void loadDB(){
        database = openOrCreateDatabase("SinhVienDB", Context.MODE_PRIVATE,null);
        Cursor cur = database.rawQuery("SELECT * FROM sinhvien",null);

        list = new ArrayList<>();

        while (cur.moveToNext()){
            list.add(  " Họ và tên: "+ cur.getString(1) + "\n" + " Lớp: " + cur.getString(2)+ "\n" + " Mã sinh viên: " + cur.getString(0));
        }
        adap = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        if(list.size() != 0){
            binding.list.setAdapter(adap);
        }
        else {
            list.add(" Họ và tên: "+ "nameDefault \n" + " Lớp: " + "LopDefault \n" + " Mã sinh viên: " + "IDDefault\n");
            binding.list.setAdapter(adap);
        }

    }

    private void addEvent(){
        binding.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Dialog a = new Dialog(MainActivity2.this);
                a.setContentView(R.layout.custom_dialog);
                a.setCancelable(false);
                a.show();

                Button btnSave = a.findViewById(R.id.btnsave);
                Button btnBack = a.findViewById(R.id.btnback);
                ImageButton btnTurnBack = a.findViewById(R.id.txtTurnBack);
                EditText namesv = a.findViewById(R.id.name);
                EditText idsv = a.findViewById(R.id.id);
                EditText lopsv = a.findViewById(R.id.lop);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Insert(idsv.getText().toString(),namesv.getText().toString(),lopsv.getText().toString());
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.dismiss();
                    }
                });
                btnTurnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.dismiss();
                    }
                });
                return true;
            }
        });
    }

}