package com.example.da_app;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    private Button btnRegister;

    private TextView txtLogin;

    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);


        // Ánh xạ
        edtFullName =
                findViewById(R.id.edtFullName);

        edtEmail =
                findViewById(R.id.edtEmail);

        edtPassword =
                findViewById(R.id.edtPassword);

        edtConfirmPassword =
                findViewById(R.id.edtConfirmPassword);

        btnRegister =
                findViewById(R.id.btnRegister);

        txtLogin =
                findViewById(R.id.txtLogin);


        // Đăng ký
        btnRegister.setOnClickListener(v ->
                dangKy()
        );


        // Quay lại đăng nhập
        txtLogin.setOnClickListener(v ->
                finish()
        );
    }


    private void dangKy() {

        String hoTen =
                edtFullName.getText()
                        .toString()
                        .trim();

        String email =
                edtEmail.getText()
                        .toString()
                        .trim();

        String matKhau =
                edtPassword.getText()
                        .toString();

        String nhapLai =
                edtConfirmPassword.getText()
                        .toString();


        // =================================================
        // KIỂM TRA HỌ TÊN
        // =================================================

        if (hoTen.isEmpty()) {

            edtFullName.setError(
                    "Vui lòng nhập họ tên"
            );

            edtFullName.requestFocus();

            return;
        }


        // =================================================
        // KIỂM TRA EMAIL
        // =================================================

        if (email.isEmpty()) {

            edtEmail.setError(
                    "Vui lòng nhập email"
            );

            edtEmail.requestFocus();

            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            edtEmail.setError(
                    "Email không hợp lệ"
            );

            edtEmail.requestFocus();

            return;
        }


        // =================================================
        // KIỂM TRA MẬT KHẨU
        // =================================================

        if (matKhau.length() < 6) {

            edtPassword.setError(
                    "Mật khẩu phải có ít nhất 6 ký tự"
            );

            edtPassword.requestFocus();

            return;
        }


        if (!matKhau.equals(nhapLai)) {

            edtConfirmPassword.setError(
                    "Mật khẩu nhập lại không khớp"
            );

            edtConfirmPassword.requestFocus();

            return;
        }


        // =================================================
        // KIỂM TRA EMAIL TRÙNG
        // =================================================

        if (databaseHelper.emailDaTonTai(email)) {

            edtEmail.setError(
                    "Email đã được sử dụng"
            );

            edtEmail.requestFocus();

            return;
        }


        // =================================================
        // THÊM USER VÀO SQLITE
        // =================================================

        boolean thanhCong =
                databaseHelper.dangKy(
                        hoTen,
                        email,
                        matKhau
                );


        if (thanhCong) {

            Toast.makeText(
                    this,
                    "Đăng ký thành công",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Đăng ký thất bại",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}