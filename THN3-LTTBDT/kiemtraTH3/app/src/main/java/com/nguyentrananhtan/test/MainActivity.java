package com.nguyentrananhtan.test;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyentrananhtan.test.adapter.donthueAdapter;
import com.nguyentrananhtan.test.databinding.ActivityMainBinding;
import com.nguyentrananhtan.test.model.DonThue;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    donthueAdapter adapter;
    ArrayList<DonThue> donthues;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
        prepareDb();
        loadData();

    }

    private void addEvents() {
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hangXe = binding.editHangXe.getText().toString().trim();
                String giaThue = binding.editGiaThue.getText().toString().trim();
                String loaiXe = binding.editLoaiXe.getText().toString().trim();

//                if (hangXe.isEmpty() && giaThue.isEmpty() && loaiXe.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Chưa nhập gì cả!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (!hangXe.isEmpty() && (!giaThue.isEmpty() || !loaiXe.isEmpty())) {
                    Toast.makeText(MainActivity.this, "Chỉ được nhập 1 trong 3 ô tìm kiếm!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!giaThue.isEmpty() && (!hangXe.isEmpty() || !loaiXe.isEmpty())) {
                    Toast.makeText(MainActivity.this, "Chỉ được nhập 1 trong 3 ô tìm kiếm!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!loaiXe.isEmpty() && (!giaThue.isEmpty() || !hangXe.isEmpty())) {
                    Toast.makeText(MainActivity.this, "Chỉ được nhập 1 trong 3 ô tìm kiếm!", Toast.LENGTH_SHORT).show();
                    return;
                }
                donthues = new ArrayList<>();

                if(!hangXe.isEmpty()){
                    Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME + " WHERE " + Database.COL_HANGXE + "='" + hangXe + "'");
                    while (cursor.moveToNext()) {
                        donthues.add(new DonThue(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                    }
                    cursor.close();
                }
                if(!loaiXe.isEmpty()){
                    Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME + " WHERE " + Database.COL_LOAIXE + "='" + loaiXe + "'");
                    while (cursor.moveToNext()) {
                        donthues.add(new DonThue(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                    }
                    cursor.close();
                }
                if(!giaThue.isEmpty()){
                    Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME + " WHERE " + Database.COL_GIATHUE + "=" + giaThue );
                    while (cursor.moveToNext()) {
                        donthues.add(new DonThue(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                    }
                    cursor.close();
                }
                if(giaThue.isEmpty()&&hangXe.isEmpty()&&loaiXe.isEmpty()){
                    Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME);
                    while (cursor.moveToNext()) {
                        donthues.add(new DonThue(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                    }
                    cursor.close();
                }
                adapter = new donthueAdapter(MainActivity.this, R.layout.list_item, donthues);
                binding.lvDonThue.setAdapter(adapter);
            }
        });
    }

    public void openEditDialog(DonThue donThue) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);

        EditText editHangXe = dialog.findViewById(R.id.editHangXe);
        editHangXe.setText(donThue.getHangXe());

        EditText editDiaDiem = dialog.findViewById(R.id.editDiaDiem);
        editDiaDiem.setText(donThue.getDiaDiem());

        EditText editLoaiXe = dialog.findViewById(R.id.editLoaiXe);
        editLoaiXe.setText(donThue.getLoaiXe());

        EditText editGiaThue = dialog.findViewById(R.id.editGiaThue);
        editGiaThue.setText(String.valueOf(donThue.getGiaThue()));

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hangxe = editHangXe.getText().toString();
                String diadiem = editDiaDiem.getText().toString();
                String loaixe = editLoaiXe.getText().toString();
                String hinhanh = "";
                double giathue = Double.parseDouble(editGiaThue.getText().toString());

                db.execSql("UPDATE " + Database.TBL_NAME + " SET " + Database.COL_HANGXE + "='" + hangxe + "'," + Database.COL_GIATHUE + "=" + giathue + "," + Database.COL_HINHANH + "='" + hinhanh + "'," + Database.COL_DIADIEM + "='" + diadiem + "'," + Database.COL_LOAIXE + "='" + loaixe + "'" + " WHERE " + Database.COL_MADON + "=" + donThue.getMaDon());

                loadData();
                dialog.dismiss();
            }
        });

        Button btnCancle = dialog.findViewById(R.id.btnCancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    public void openDeleteConfirmDialog(DonThue donThue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Bạn có chắc muốn xóa đơn thuê: " + donThue.getMaDon() + " ?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.execSql("DELETE FROM " + Database.TBL_NAME + " WHERE " + Database.COL_MADON + " = " + donThue.getMaDon());
                loadData();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void loadData() {
        adapter = new donthueAdapter(MainActivity.this, R.layout.list_item, getDataFromDb());
        binding.lvDonThue.setAdapter(adapter);
    }

    private List<DonThue> getDataFromDb() {
        donthues = new ArrayList<>();
        Cursor cursor = db.queryData("SELECT * FROM " + db.TBL_NAME);
        while (cursor.moveToNext()) {
            donthues.add(new DonThue(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
        }
        cursor.close();
        return donthues;
    }

    private void prepareDb() {
        db = new Database(this);
        db.createSampleData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_add);

            EditText editHangxe = dialog.findViewById(R.id.editHangXe);
            EditText editGiathue = dialog.findViewById(R.id.editGiaThue);
            EditText editDiadiem = dialog.findViewById(R.id.editDiaDiem);
            EditText editLoaixe = dialog.findViewById(R.id.editLoaiXe);

//            if(editHangxe.getText().equals("") || editGiathue.getText().equals("") || editDiadiem.getText().equals("") || editLoaixe.getText().equals("")){
//                Toast.makeText(MainActivity.this,"Vui lòng nhập đầy đủ",Toast.LENGTH_SHORT);
//                return;
//            }

            Button btnSave = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Insert data
                    String hangxe = editHangxe.getText().toString();
                    String diadiem = editDiadiem.getText().toString();
                    String loaixe = editLoaixe.getText().toString();
                    String hinhanh = "";
                    double giathue = Double.parseDouble(editGiathue.getText().toString());

                    db.execSql("INSERT INTO " + Database.TBL_NAME + " VALUES(null, '" +
                            hangxe + "', " + giathue + ", " + " '" +
                            hinhanh + "', " + " '" +
                            diadiem + "', ' " + loaixe + " ')");
                    loadData();

                    // dong hop thoai
                    dialog.dismiss();
                }
            });

            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}