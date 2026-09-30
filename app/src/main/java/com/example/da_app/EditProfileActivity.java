package com.example.da_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;

public class EditProfileActivity extends AppCompatActivity {

    private TextView btnBack;

    private EditText edtHoTen;
    private EditText edtEmail;

    private Button btnSave;

    private DatabaseHelper databaseHelper;

    private SharedPreferences preferences;

    private int maNguoiDung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);


        // =========================================
        // DATABASE
        // =========================================

        databaseHelper =
                new DatabaseHelper(this);


        // =========================================
        // SHARED PREFERENCES
        // =========================================

        preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );


        // =========================================
        // ÁNH XẠ
        // =========================================

        btnBack =
                findViewById(R.id.btnBack);

        edtHoTen =
                findViewById(R.id.edtHoTen);

        edtEmail =
                findViewById(R.id.edtEmail);

        btnSave =
                findViewById(R.id.btnSave);


        // =========================================
        // LẤY ID NGƯỜI DÙNG
        // =========================================

        maNguoiDung =
                preferences.getInt(
                        "MA_NGUOI_DUNG",
                        -1
                );


        if (maNguoiDung == -1) {

            Toast.makeText(
                    this,
                    "Không tìm thấy tài khoản",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }


        // =========================================
        // HIỂN THỊ THÔNG TIN HIỆN TẠI
        // =========================================

        String hoTen =
                preferences.getString(
                        "HO_TEN",
                        ""
                );

        String email =
                preferences.getString(
                        "EMAIL",
                        ""
                );


        edtHoTen.setText(hoTen);

        edtEmail.setText(email);


        // =========================================
        // QUAY LẠI
        // =========================================

        btnBack.setOnClickListener(v -> finish());


        // =========================================
        // LƯU
        // =========================================

        btnSave.setOnClickListener(v -> capNhatHoSo());
    }


    // =====================================================
    // CẬP NHẬT HỒ SƠ
    // =====================================================

    private void capNhatHoSo() {

        String hoTen =
                edtHoTen
                        .getText()
                        .toString()
                        .trim();

        String email =
                edtEmail
                        .getText()
                        .toString()
                        .trim();


        // =========================================
        // KIỂM TRA HỌ TÊN
        // =========================================

        if (hoTen.isEmpty()) {

            edtHoTen.setError(
                    "Vui lòng nhập họ tên"
            );

            edtHoTen.requestFocus();

            return;
        }


        // =========================================
        // KIỂM TRA EMAIL
        // =========================================

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


        // =========================================
        // KIỂM TRA EMAIL TRÙNG
        // =========================================

        if (databaseHelper.emailDaTonTaiKhiSua(
                email,
                maNguoiDung
        )) {

            edtEmail.setError(
                    "Email này đã được sử dụng"
            );

            edtEmail.requestFocus();

            return;
        }


        // =========================================
        // CẬP NHẬT SQLITE
        // =========================================

        boolean thanhCong =
                databaseHelper.capNhatHoSo(
                        maNguoiDung,
                        hoTen,
                        email
                );


        if (!thanhCong) {

            Toast.makeText(
                    this,
                    "Cập nhật hồ sơ thất bại",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =========================================
        // ĐỒNG BỘ SESSION
        // =========================================

        preferences
                .edit()
                .putString(
                        "HO_TEN",
                        hoTen
                )
                .putString(
                        "EMAIL",
                        email
                )
                .apply();


        Toast.makeText(
                this,
                "Cập nhật hồ sơ thành công",
                Toast.LENGTH_SHORT
        ).show();


        // Quay lại Profile
        finish();
    }
}