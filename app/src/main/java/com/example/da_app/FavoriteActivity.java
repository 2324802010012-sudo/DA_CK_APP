package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    // =====================================================
    // VIEW
    // =====================================================

    private TextView btnBack;
    private LinearLayout layoutFavoriteList;


    // =====================================================
    // DATABASE
    // =====================================================

    private DatabaseHelper databaseHelper;


    // =====================================================
    // USER
    // =====================================================

    private int maNguoiDung = -1;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite);


        // =================================================
        // ÁNH XẠ
        // =================================================

        btnBack =
                findViewById(R.id.btnBack);

        layoutFavoriteList =
                findViewById(R.id.layoutFavoriteList);


        // =================================================
        // DATABASE
        // =================================================

        databaseHelper =
                new DatabaseHelper(this);


        // =================================================
        // LẤY USER ĐANG ĐĂNG NHẬP
        // =================================================

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );


        boolean daDangNhap =
                preferences.getBoolean(
                        "DA_DANG_NHAP",
                        false
                );


        maNguoiDung =
                preferences.getInt(
                        "MA_NGUOI_DUNG",
                        -1
                );


        // =================================================
        // BẢO VỆ MÀN HÌNH
        // =================================================

        if (!daDangNhap || maNguoiDung == -1) {

            Toast.makeText(
                    this,
                    "Bạn cần đăng nhập để xem danh sách yêu thích",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }


        // =================================================
        // QUAY LẠI
        // =================================================

        btnBack.setOnClickListener(v -> finish());
    }


    // =====================================================
    // LOAD LẠI MỖI KHI QUAY VỀ MÀN HÌNH
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();

        if (maNguoiDung != -1) {

            loadDanhSachYeuThich();
        }
    }


    // =====================================================
    // LOAD DANH SÁCH YÊU THÍCH
    // =====================================================

    private void loadDanhSachYeuThich() {

        ArrayList<DiaDiem> danhSach =
                databaseHelper.layDanhSachYeuThich(
                        maNguoiDung
                );


        hienThiDanhSach(
                danhSach
        );
    }


    // =====================================================
    // HIỂN THỊ DANH SÁCH
    // =====================================================

    private void hienThiDanhSach(
            ArrayList<DiaDiem> danhSach
    ) {

        layoutFavoriteList.removeAllViews();


        // =================================================
        // KHÔNG CÓ YÊU THÍCH
        // =================================================

        if (danhSach == null
                || danhSach.isEmpty()) {

            TextView txtEmpty =
                    new TextView(this);


            txtEmpty.setText(
                    "Bạn chưa có địa điểm yêu thích nào.\n" +
                            "Hãy khám phá và nhấn ♡ để lưu địa điểm."
            );


            txtEmpty.setTextSize(15);

            txtEmpty.setTextColor(
                    Color.parseColor("#8493A8")
            );

            txtEmpty.setGravity(
                    Gravity.CENTER
            );

            txtEmpty.setPadding(
                    30,
                    100,
                    30,
                    100
            );


            layoutFavoriteList.addView(
                    txtEmpty
            );


            return;
        }


        // =================================================
        // HIỂN THỊ TỪNG ĐỊA ĐIỂM
        // =================================================

        for (DiaDiem diaDiem : danhSach) {

            View itemView =
                    LayoutInflater
                            .from(this)
                            .inflate(
                                    R.layout.item_favorite,
                                    layoutFavoriteList,
                                    false
                            );


            ImageView imgPlace =
                    itemView.findViewById(
                            R.id.imgPlace
                    );


            TextView txtPlaceName =
                    itemView.findViewById(
                            R.id.txtPlaceName
                    );


            TextView txtPlaceAddress =
                    itemView.findViewById(
                            R.id.txtPlaceAddress
                    );


            TextView txtPlaceInfo =
                    itemView.findViewById(
                            R.id.txtPlaceInfo
                    );


            TextView btnRemoveFavorite =
                    itemView.findViewById(
                            R.id.btnRemoveFavorite
                    );


            // =================================================
            // TÊN
            // =================================================

            txtPlaceName.setText(
                    diaDiem.getTenDiaDiem()
            );


            // =================================================
            // ĐỊA CHỈ
            // =================================================

            String diaChi =
                    diaDiem.getDiaChi();


            if (diaChi == null
                    || diaChi.trim().isEmpty()) {

                txtPlaceAddress.setText(
                        "📍 Chưa cập nhật địa chỉ"
                );

            } else {

                txtPlaceAddress.setText(
                        "📍 " + diaChi
                );
            }


            // =================================================
            // ĐÁNH GIÁ + DANH MỤC
            // =================================================

            String danhMuc =
                    diaDiem.getDanhMuc();


            if (danhMuc == null
                    || danhMuc.trim().isEmpty()) {

                danhMuc = "Du lịch";
            }


            txtPlaceInfo.setText(
                    "★ "
                            + diaDiem.getDanhGia()
                            + "   •   "
                            + danhMuc
            );


            // =================================================
            // ẢNH
            // =================================================

            hienThiAnh(
                    imgPlace,
                    diaDiem.getHinhAnh()
            );


            // =================================================
            // BẤM CARD → CHI TIẾT
            // =================================================

            itemView.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                FavoriteActivity.this,
                                PlaceDetailActivity.class
                        );


                intent.putExtra(
                        "MA_DIA_DIEM",
                        diaDiem.getMaDiaDiem()
                );


                intent.putExtra(
                        "PLACE_NAME",
                        diaDiem.getTenDiaDiem()
                );


                startActivity(intent);
            });


            // =================================================
            // ♥ → XÓA YÊU THÍCH
            // =================================================

            btnRemoveFavorite.setOnClickListener(v -> {

                boolean ketQua =
                        databaseHelper.xoaYeuThich(
                                maNguoiDung,
                                diaDiem.getMaDiaDiem()
                        );


                if (ketQua) {

                    Toast.makeText(
                            FavoriteActivity.this,
                            "Đã bỏ "
                                    + diaDiem.getTenDiaDiem()
                                    + " khỏi yêu thích",
                            Toast.LENGTH_SHORT
                    ).show();


                    // Load lại danh sách
                    loadDanhSachYeuThich();

                } else {

                    Toast.makeText(
                            FavoriteActivity.this,
                            "Không thể bỏ yêu thích",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });


            layoutFavoriteList.addView(
                    itemView
            );
        }
    }


    // =====================================================
    // HIỂN THỊ ẢNH
    // =====================================================

    private void hienThiAnh(
            ImageView imageView,
            String hinhAnh
    ) {

        // Không có ảnh
        if (hinhAnh == null
                || hinhAnh.trim().isEmpty()) {

            imageView.setImageResource(
                    R.drawable.halong_bay
            );

            return;
        }


        // Ảnh chọn từ thiết bị
        if (hinhAnh.startsWith("content://")
                || hinhAnh.startsWith("file://")) {

            try {

                imageView.setImageURI(
                        Uri.parse(hinhAnh)
                );

            } catch (Exception e) {

                imageView.setImageResource(
                        R.drawable.halong_bay
                );
            }

            return;
        }


        // Ảnh drawable
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

        } else {

            imageView.setImageResource(
                    R.drawable.halong_bay
            );
        }
    }
}