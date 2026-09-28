package com.example.da_app.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da_app.AddEditPlaceActivity;
import com.example.da_app.R;
import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

import java.util.ArrayList;

public class DiaDiemAdminAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<DiaDiem> danhSach;
    private final DatabaseHelper databaseHelper;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public DiaDiemAdminAdapter(
            Context context,
            ArrayList<DiaDiem> danhSach,
            DatabaseHelper databaseHelper
    ) {

        this.context = context;
        this.danhSach = danhSach;
        this.databaseHelper = databaseHelper;
    }


    // =====================================================
    // SỐ LƯỢNG ĐỊA ĐIỂM
    // =====================================================

    @Override
    public int getCount() {

        return danhSach.size();
    }


    // =====================================================
    // LẤY ĐỊA ĐIỂM THEO VỊ TRÍ
    // =====================================================

    @Override
    public Object getItem(int position) {

        return danhSach.get(position);
    }


    // =====================================================
    // LẤY ID
    // =====================================================

    @Override
    public long getItemId(int position) {

        return danhSach
                .get(position)
                .getMaDiaDiem();
    }


    // =====================================================
    // HIỂN THỊ MỘT ITEM
    // =====================================================

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        // =================================================
        // TẠO VIEW
        // =================================================

        if (convertView == null) {

            convertView = LayoutInflater
                    .from(context)
                    .inflate(
                            R.layout.item_admin_place,
                            parent,
                            false
                    );
        }


        // =================================================
        // ÁNH XẠ CONTROL
        // =================================================

        ImageView imgPlace =
                convertView.findViewById(
                        R.id.imgPlace
                );


        TextView txtPlaceName =
                convertView.findViewById(
                        R.id.txtPlaceName
                );


        TextView txtPlaceAddress =
                convertView.findViewById(
                        R.id.txtPlaceAddress
                );


        TextView txtPlaceInfo =
                convertView.findViewById(
                        R.id.txtPlaceInfo
                );


        Button btnEdit =
                convertView.findViewById(
                        R.id.btnEdit
                );


        Button btnDelete =
                convertView.findViewById(
                        R.id.btnDelete
                );


        // =================================================
        // LẤY ĐỊA ĐIỂM
        // =================================================

        DiaDiem diaDiem =
                danhSach.get(position);


        // =================================================
        // HIỂN THỊ TÊN
        // =================================================

        txtPlaceName.setText(
                diaDiem.getTenDiaDiem()
        );


        // =================================================
        // HIỂN THỊ ĐỊA CHỈ
        // =================================================

        txtPlaceAddress.setText(
                "📍 " + diaDiem.getDiaChi()
        );


        // =================================================
        // HIỂN THỊ KHU VỰC - DANH MỤC - ĐÁNH GIÁ
        // =================================================

        txtPlaceInfo.setText(
                diaDiem.getKhuVuc()
                        + " • "
                        + diaDiem.getDanhMuc()
                        + " • ⭐ "
                        + diaDiem.getDanhGia()
        );


        // =================================================
        // HIỂN THỊ ẢNH
        // =================================================

        String hinhAnh =
                diaDiem.getHinhAnh();


        // Nếu không có ảnh
        if (hinhAnh == null
                || hinhAnh.trim().isEmpty()) {

            imgPlace.setImageResource(
                    R.drawable.halong_bay
            );

        }

        // =================================================
        // ẢNH ADMIN CHỌN TỪ ĐIỆN THOẠI
        // =================================================

        else if (
                hinhAnh.startsWith("content://")
                        || hinhAnh.startsWith("file://")
        ) {

            try {

                Uri imageUri =
                        Uri.parse(hinhAnh);

                imgPlace.setImageURI(
                        imageUri
                );

            } catch (Exception e) {

                imgPlace.setImageResource(
                        R.drawable.halong_bay
                );
            }

        }

        // =================================================
        // ẢNH CÓ SẴN TRONG DRAWABLE
        // =================================================

        else {

            int resourceId =
                    context
                            .getResources()
                            .getIdentifier(
                                    hinhAnh,
                                    "drawable",
                                    context.getPackageName()
                            );


            if (resourceId != 0) {

                imgPlace.setImageResource(
                        resourceId
                );

            } else {

                // Không tìm thấy ảnh
                // dùng ảnh mặc định

                imgPlace.setImageResource(
                        R.drawable.halong_bay
                );
            }
        }


        // =================================================
        // NÚT SỬA
        // =================================================

        btnEdit.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            context,
                            AddEditPlaceActivity.class
                    );


            // Gửi mã địa điểm sang màn hình sửa

            intent.putExtra(
                    "MA_DIA_DIEM",
                    diaDiem.getMaDiaDiem()
            );


            context.startActivity(intent);
        });


        // =================================================
        // NÚT XÓA
        // =================================================

        btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)

                    .setTitle(
                            "Xóa địa điểm"
                    )

                    .setMessage(
                            "Bạn có chắc muốn xóa "
                                    + diaDiem.getTenDiaDiem()
                                    + "?"
                    )

                    // =====================================
                    // HỦY
                    // =====================================

                    .setNegativeButton(
                            "Hủy",
                            null
                    )

                    // =====================================
                    // XÓA
                    // =====================================

                    .setPositiveButton(
                            "Xóa",
                            (dialog, which) -> {

                                boolean ketQua =
                                        databaseHelper
                                                .xoaDiaDiem(
                                                        diaDiem.getMaDiaDiem()
                                                );


                                // =========================
                                // XÓA THÀNH CÔNG
                                // =========================

                                if (ketQua) {

                                    danhSach.remove(
                                            diaDiem
                                    );


                                    notifyDataSetChanged();


                                    Toast.makeText(
                                            context,
                                            "Xóa địa điểm thành công",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                }

                                // =========================
                                // XÓA THẤT BẠI
                                // =========================

                                else {

                                    Toast.makeText(
                                            context,
                                            "Không thể xóa địa điểm",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }
                    )

                    .show();
        });


        return convertView;
    }
}