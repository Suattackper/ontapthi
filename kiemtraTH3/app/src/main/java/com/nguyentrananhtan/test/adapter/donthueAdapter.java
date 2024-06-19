package com.nguyentrananhtan.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyentrananhtan.test.MainActivity;
import com.nguyentrananhtan.test.model.DonThue;
import com.nguyentrananhtan.test.R;

import java.util.List;

public class donthueAdapter extends BaseAdapter {
    MainActivity context;
    int item_layout;
    List<DonThue> donThues;

    //constructor


    public donthueAdapter(MainActivity context, int item_layout, List<DonThue> donThues) {
        this.context = context;
        this.item_layout = item_layout;
        this.donThues = donThues;
    }

    //done
    @Override
    public int getCount() {
        return donThues.size();
    }

    //done
    @Override
    public Object getItem(int position) {
        return donThues.get(position);
    }
    //done
    @Override
    public long getItemId(int position) {
        return position;
    }
    //done
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodel hodel;
        if (convertView == null) {
            hodel = new ViewHodel();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            //linking view
            hodel.tvMaDon = convertView.findViewById(R.id.tvMaDon);
            hodel.tvHangXe = convertView.findViewById(R.id.tvHangXe);
            hodel.tvGiaThue = convertView.findViewById(R.id.tvGiaThue);
            hodel.tvDiaDiem = convertView.findViewById(R.id.tvDiaDiem);
            hodel.tvLoaiXe = convertView.findViewById(R.id.tvLoaiXe);
            hodel.imvEdit = convertView.findViewById(R.id.imvEdit);
            hodel.imvDelete = convertView.findViewById(R.id.imvDelete);

            convertView.setTag(hodel);


        } else {
            hodel = (ViewHodel) convertView.getTag();
        }

        // binding data
        DonThue donThue = donThues.get(position);
//        hodel.txtInfo.setText(food.getFoodName() + " - " + String.format("%.0fvnđ", food.getFoodPrice()));
        hodel.tvMaDon.setText("Mã đơn: " + String.valueOf(donThue.getMaDon()));
        hodel.tvHangXe.setText("Hãng xe: " + donThue.getHangXe());
        hodel.tvGiaThue.setText("Giá thuê: " + String.valueOf(donThue.getGiaThue()));
        hodel.tvDiaDiem.setText("Dịa điểm: " + donThue.getDiaDiem());
        hodel.tvLoaiXe.setText("Loại xe: " + donThue.getLoaiXe());

        hodel.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openEditDialog(donThue);
            }
        });
        hodel.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.openDeleteConfirmDialog(donThue);
            }
        });

        return convertView;
    }

    //done
    public static class ViewHodel {
        TextView tvMaDon;
        TextView tvHangXe;
        TextView tvGiaThue;
        TextView tvDiaDiem;
        TextView tvLoaiXe;
        ImageView imvEdit, imvDelete;
    }
}
