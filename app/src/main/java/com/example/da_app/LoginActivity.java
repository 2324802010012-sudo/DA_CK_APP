package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;

    private Button btnLogin;
    private TextView txtRegister;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // =============================================
        // DATABASE
        // =============================================

        databaseHelper = new DatabaseHelper(this);

        databaseHelper.getWritableDatabase();


        // =============================================
        // ÁNH XẠ
        // =============================================

        edtEmail = findViewById(R.id.edtEmail);

        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtRegister = findViewById(R.id.txtRegister);


        // =============================================
        // ĐĂNG NHẬP
        // =============================================

        btnLogin.setOnClickListener(v -> dangNhap());


        // =============================================
        // ĐĂNG KÝ
        // =============================================

        txtRegister.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });
    }


    // =====================================================
    // ĐĂNG NHẬP
    // =====================================================

    private void dangNhap() {

        String email =
                edtEmail
                        .getText()
                        .toString()
                        .trim();

        String matKhau =
                edtPassword
                        .getText()
                        .toString()
                        .trim();


        // =============================================
        // KIỂM TRA EMAIL
        // =============================================

        if (email.isEmpty()) {

            edtEmail.setError(
                    "Vui lòng nhập email"
            );

            edtEmail.requestFocus();

            return;
        }


        // =============================================
        // KIỂM TRA MẬT KHẨU
        // =============================================

        if (matKhau.isEmpty()) {

            edtPassword.setError(
                    "Vui lòng nhập mật khẩu"
            );

            edtPassword.requestFocus();

            return;
        }


        // =============================================
        // KIỂM TRA SQLITE
        // =============================================

        String vaiTro =
                databaseHelper.dangNhap(
                        email,
                        matKhau
                );


        if (vaiTro == null) {

            Toast.makeText(
                    LoginActivity.this,
                    "Email hoặc mật khẩu không đúng",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =============================================
        // LẤY THÔNG TIN NGƯỜI DÙNG
        // =============================================

        int maNguoiDung =
                databaseHelper.layMaNguoiDung(
                        email
                );

        String hoTen =
                databaseHelper.layHoTen(
                        email
                );


        // =============================================
        // LƯU PHIÊN ĐĂNG NHẬP
        // =============================================

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putBoolean(
                "DA_DANG_NHAP",
                true
        );

        editor.putInt(
                "MA_NGUOI_DUNG",
                maNguoiDung
        );

        editor.putString(
                "HO_TEN",
                hoTen
        );

        editor.putString(
                "EMAIL",
                email
        );

        editor.putString(
                "VAI_TRO",
                vaiTro
        );

        editor.apply();


        // =============================================
        // ADMIN
        // =============================================

        if (vaiTro.equalsIgnoreCase("ADMIN")) {

            Toast.makeText(
                    LoginActivity.this,
                    "Đăng nhập Admin thành công",
                    Toast.LENGTH_SHORT
            ).show();


            Intent intent = new Intent(
                    LoginActivity.this,
                    AdminActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();

        }

        // =============================================
        // USER
        // =============================================

        else {

            Toast.makeText(
                    LoginActivity.this,
                    "Đăng nhập thành công",
                    Toast.LENGTH_SHORT
            ).show();


            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        }
    }
}