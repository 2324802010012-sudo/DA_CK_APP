package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    // =====================================================
    // VIEW
    // =====================================================

    private TextView btnBack;

    private TextView txtAvatar;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtRole;

    private TextView menuInfo;
    private TextView menuFavorite;
    private TextView menuRating;
    private TextView menuEditProfile;

    private TextView btnLogout;


    // =====================================================
    // DỮ LIỆU USER
    // =====================================================

    private int maNguoiDung = -1;

    private String hoTen = "";
    private String email = "";
    private String vaiTro = "";


    // =====================================================
    // ON CREATE
    // =====================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);


        // =================================================
        // ÁNH XẠ
        // =================================================

        btnBack =
                findViewById(R.id.btnBack);

        txtAvatar =
                findViewById(R.id.txtAvatar);

        txtName =
                findViewById(R.id.txtName);

        txtEmail =
                findViewById(R.id.txtEmail);

        txtRole =
                findViewById(R.id.txtRole);

        menuInfo =
                findViewById(R.id.menuInfo);

        menuFavorite =
                findViewById(R.id.menuFavorite);

        menuRating =
                findViewById(R.id.menuRating);

        menuEditProfile =
                findViewById(R.id.menuEditProfile);

        btnLogout =
                findViewById(R.id.btnLogout);


        // =================================================
        // KIỂM TRA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            moDangNhap();

            finish();

            return;
        }


        // =================================================
        // LOAD USER
        // =================================================

        loadThongTinNguoiDung();


        // =================================================
        // QUAY LẠI
        // =================================================

        btnBack.setOnClickListener(v -> {

            finish();

        });


        // =================================================
        // THÔNG TIN CÁ NHÂN
        // =================================================

        menuInfo.setOnClickListener(v -> {

            hienThiThongTin();

        });


        // =================================================
        // YÊU THÍCH
        // =================================================

        menuFavorite.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            FavoriteActivity.class
                    );

            startActivity(intent);

        });


        // =================================================
        // ĐÁNH GIÁ CỦA TÔI
        // =================================================

        menuRating.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            MyRatingActivity.class
                    );

            startActivity(intent);

        });

        // =================================================
        // CHỈNH SỬA HỒ SƠ
        // =================================================
        menuEditProfile.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            ProfileActivity.this,
                            EditProfileActivity.class
                    );

            startActivity(intent);

        });


        // =================================================
        // ĐĂNG XUẤT
        // =================================================

        btnLogout.setOnClickListener(v -> {

            xacNhanDangXuat();

        });
    }


    // =====================================================
    // ON RESUME
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();

        if (daDangNhap()) {

            loadThongTinNguoiDung();

        }
    }


    // =====================================================
    // LOAD THÔNG TIN USER
    // =====================================================

    private void loadThongTinNguoiDung() {

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


        hoTen =
                preferences.getString(
                        "HO_TEN",
                        "Người dùng"
                );


        email =
                preferences.getString(
                        "EMAIL",
                        ""
                );


        vaiTro =
                preferences.getString(
                        "VAI_TRO",
                        "USER"
                );


        // =================================================
        // HIỂN THỊ
        // =================================================

        txtName.setText(
                hoTen
        );


        txtEmail.setText(
                email
        );


        if ("ADMIN".equalsIgnoreCase(vaiTro)) {

            txtRole.setText(
                    "Quản trị viên"
            );

        } else {

            txtRole.setText(
                    "Thành viên"
            );
        }


        // =================================================
        // CHỮ ĐẦU AVATAR
        // =================================================

        if (hoTen != null
                && !hoTen.trim().isEmpty()) {

            String chuCai =
                    hoTen
                            .trim()
                            .substring(0, 1)
                            .toUpperCase();

            txtAvatar.setText(
                    chuCai
            );

        } else {

            txtAvatar.setText(
                    "U"
            );
        }
    }


    // =====================================================
    // THÔNG TIN CÁ NHÂN
    // =====================================================

    private void hienThiThongTin() {

        new AlertDialog.Builder(this)

                .setTitle(
                        "Thông tin cá nhân"
                )

                .setMessage(
                        "Mã người dùng: "
                                + maNguoiDung

                                + "\n\nHọ tên: "
                                + hoTen

                                + "\n\nEmail: "
                                + email

                                + "\n\nVai trò: "
                                + vaiTro
                )

                .setPositiveButton(
                        "ĐÓNG",
                        null
                )

                .show();
    }


    // =====================================================
    // KIỂM TRA ĐĂNG NHẬP
    // =====================================================

    private boolean daDangNhap() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );


        return preferences.getBoolean(
                "DA_DANG_NHAP",
                false
        );
    }


    // =====================================================
    // MỞ LOGIN
    // =====================================================

    private void moDangNhap() {

        Intent intent =
                new Intent(
                        ProfileActivity.this,
                        LoginActivity.class
                );

        startActivity(intent);
    }


    // =====================================================
    // XÁC NHẬN ĐĂNG XUẤT
    // =====================================================

    private void xacNhanDangXuat() {

        new AlertDialog.Builder(this)

                .setTitle(
                        "Đăng xuất"
                )

                .setMessage(
                        "Bạn có chắc muốn đăng xuất khỏi tài khoản?"
                )

                .setNegativeButton(
                        "HỦY",
                        null
                )

                .setPositiveButton(
                        "ĐĂNG XUẤT",
                        (dialog, which) -> {

                            dangXuat();

                        }
                )

                .show();
    }


    // =====================================================
    // ĐĂNG XUẤT
    // =====================================================

    private void dangXuat() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );


        preferences
                .edit()
                .clear()
                .apply();


        Toast.makeText(
                this,
                "Đã đăng xuất",
                Toast.LENGTH_SHORT
        ).show();


        Intent intent =
                new Intent(
                        ProfileActivity.this,
                        MainActivity.class
                );


        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
        );


        startActivity(intent);

        finish();
    }
}