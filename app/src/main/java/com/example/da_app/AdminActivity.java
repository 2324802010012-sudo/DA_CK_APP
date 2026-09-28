package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    // ==========================================
    // KHAI BÁO CONTROL
    // ==========================================

    private TextView menuManagePlaces;
    private TextView menuManageUsers;
    private TextView menuManageTips;
    private TextView menuViewApp;

    private Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ==========================================
        // GẮN GIAO DIỆN
        // ==========================================

        setContentView(R.layout.activity_admin);


        // ==========================================
        // ÁNH XẠ CONTROL
        // ==========================================

        menuManagePlaces =
                findViewById(R.id.menuManagePlaces);

        menuManageUsers =
                findViewById(R.id.menuManageUsers);

        menuManageTips =
                findViewById(R.id.menuManageTips);

        menuViewApp =
                findViewById(R.id.menuViewApp);

        btnLogout =
                findViewById(R.id.btnLogout);


        // ==========================================
        // KIỂM TRA TÀI KHOẢN ADMIN
        // ==========================================

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

        String vaiTro =
                preferences.getString(
                        "VAI_TRO",
                        ""
                );


        // Nếu chưa đăng nhập
        // hoặc không phải ADMIN
        if (!daDangNhap ||
                !vaiTro.equalsIgnoreCase("ADMIN")) {

            Intent intent = new Intent(
                    AdminActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

            return;
        }


        // ==========================================
        // QUẢN LÝ ĐỊA ĐIỂM
        // ==========================================

        menuManagePlaces.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminActivity.this,
                    AdminPlaceActivity.class
            );

            startActivity(intent);
        });


        // ==========================================
        // QUẢN LÝ NGƯỜI DÙNG
        // ==========================================

        menuManageUsers.setOnClickListener(v -> {

            Toast.makeText(
                    AdminActivity.this,
                    "Quản lý người dùng",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ==========================================
        // QUẢN LÝ MẸO DU LỊCH
        // ==========================================

        menuManageTips.setOnClickListener(v -> {

            Toast.makeText(
                    AdminActivity.this,
                    "Quản lý mẹo du lịch",
                    Toast.LENGTH_SHORT
            ).show();

        });


        // ==========================================
        // XEM TRANG NGƯỜI DÙNG
        // ==========================================

        menuViewApp.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminActivity.this,
                    MainActivity.class
            );

            startActivity(intent);

        });


        // ==========================================
        // ĐĂNG XUẤT
        // ==========================================

        btnLogout.setOnClickListener(v -> {

            // Xóa phiên đăng nhập
            SharedPreferences.Editor editor =
                    preferences.edit();

            editor.clear();

            editor.apply();


            // Quay về Login
            Intent intent = new Intent(
                    AdminActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

        });
    }
}