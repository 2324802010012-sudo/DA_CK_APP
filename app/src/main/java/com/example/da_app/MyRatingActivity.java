package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DanhGiaCuaToi;

import java.util.ArrayList;

public class MyRatingActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView txtEmpty;

    private LinearLayout layoutRatingList;

    private DatabaseHelper databaseHelper;

    private int maNguoiDung = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_rating);


        // =========================================
        // ÁNH XẠ
        // =========================================

        btnBack =
                findViewById(R.id.btnBack);

        txtEmpty =
                findViewById(R.id.txtEmpty);

        layoutRatingList =
                findViewById(R.id.layoutRatingList);


        databaseHelper =
                new DatabaseHelper(this);


        // =========================================
        // LẤY USER
        // =========================================

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );

        maNguoiDung =
                preferences.getInt(
                        "MA_NGUOI_DUNG",
                        -1
                );


        if (maNguoiDung == -1) {

            finish();

            return;
        }


        // =========================================
        // BACK
        // =========================================

        btnBack.setOnClickListener(
                v -> finish()
        );
    }


    // =====================================================
    // ON RESUME
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();

        loadDanhGia();
    }


    // =====================================================
    // LOAD DANH SÁCH
    // =====================================================

    private void loadDanhGia() {

        layoutRatingList.removeAllViews();


        ArrayList<DanhGiaCuaToi> danhSach =
                databaseHelper.layDanhGiaCuaToi(
                        maNguoiDung
                );


        if (danhSach.isEmpty()) {

            txtEmpty.setVisibility(
                    View.VISIBLE
            );

            return;
        }


        txtEmpty.setVisibility(
                View.GONE
        );


        for (DanhGiaCuaToi danhGia : danhSach) {

            themItemDanhGia(danhGia);
        }
    }


    // =====================================================
    // THÊM ITEM
    // =====================================================

    private void themItemDanhGia(
            DanhGiaCuaToi danhGia
    ) {

        View item =
                LayoutInflater
                        .from(this)
                        .inflate(
                                R.layout.item_my_rating,
                                layoutRatingList,
                                false
                        );


        ImageView imgPlace =
                item.findViewById(
                        R.id.imgPlace
                );

        TextView txtPlaceName =
                item.findViewById(
                        R.id.txtPlaceName
                );

        TextView txtAddress =
                item.findViewById(
                        R.id.txtAddress
                );

        TextView txtMyRating =
                item.findViewById(
                        R.id.txtMyRating
                );


        // =========================================
        // DỮ LIỆU
        // =========================================

        txtPlaceName.setText(
                danhGia.getTenDiaDiem()
        );


        txtAddress.setText(
                danhGia.getDiaChi()
        );


        txtMyRating.setText(
                taoChuoiSao(
                        danhGia.getSoSao()
                )
                        + "  "
                        + danhGia.getSoSao()
                        + "/5"
        );


        // =========================================
        // ẢNH
        // =========================================

        loadImage(
                imgPlace,
                danhGia.getHinhAnh()
        );


        // =========================================
        // MỞ CHI TIẾT
        // =========================================

        item.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MyRatingActivity.this,
                            PlaceDetailActivity.class
                    );

            intent.putExtra(
                    "MA_DIA_DIEM",
                    danhGia.getMaDiaDiem()
            );

            intent.putExtra(
                    "PLACE_NAME",
                    danhGia.getTenDiaDiem()
            );

            startActivity(intent);

        });


        layoutRatingList.addView(item);
    }


    // =====================================================
    // TẠO CHUỖI SAO
    // =====================================================

    private String taoChuoiSao(int soSao) {

        StringBuilder ketQua =
                new StringBuilder();

        for (int i = 1; i <= 5; i++) {

            if (i <= soSao) {

                ketQua.append("★");

            } else {

                ketQua.append("☆");
            }
        }

        return ketQua.toString();
    }


    // =====================================================
    // LOAD IMAGE
    // =====================================================

    private void loadImage(
            ImageView imageView,
            String hinhAnh
    ) {

        if (hinhAnh == null
                || hinhAnh.trim().isEmpty()) {

            return;
        }


        // URI từ bộ nhớ máy
        if (hinhAnh.startsWith("content://")
                || hinhAnh.startsWith("file://")) {

            try {

                imageView.setImageURI(
                        Uri.parse(hinhAnh)
                );

            } catch (Exception ignored) {
            }

            return;
        }


        // Drawable
        int resourceId =
                getResources()
                        .getIdentifier(
                                hinhAnh,
                                "drawable",
                                getPackageName()
                        );


        if (resourceId != 0) {

            imageView.setImageResource(
                    resourceId
            );
        }
    }
}