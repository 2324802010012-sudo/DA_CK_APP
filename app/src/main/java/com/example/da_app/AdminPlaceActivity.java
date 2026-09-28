package com.example.da_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.adapter.DiaDiemAdminAdapter;
import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

import java.util.ArrayList;

public class AdminPlaceActivity extends AppCompatActivity {

    private TextView btnBack;
    private Button btnAddPlace;
    private ListView listPlace;

    private DatabaseHelper databaseHelper;
    private ArrayList<DiaDiem> danhSachDiaDiem;
    private DiaDiemAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_place);

        // ==========================================
        // ÁNH XẠ
        // ==========================================

        btnBack = findViewById(R.id.btnBack);
        btnAddPlace = findViewById(R.id.btnAddPlace);
        listPlace = findViewById(R.id.listPlace);

        // ==========================================
        // DATABASE
        // ==========================================

        databaseHelper =
                new DatabaseHelper(this);

        // ==========================================
        // NÚT QUAY LẠI
        // ==========================================

        btnBack.setOnClickListener(v -> finish());

        // ==========================================
        // NÚT THÊM
        // ==========================================

        btnAddPlace.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AdminPlaceActivity.this,
                    AddEditPlaceActivity.class
            );

            startActivity(intent);
        });
    }


    // ==========================================
    // LOAD DANH SÁCH
    // ==========================================

    private void loadDanhSach() {

        danhSachDiaDiem =
                databaseHelper.layTatCaDiaDiem();

        adapter = new DiaDiemAdminAdapter(
                AdminPlaceActivity.this,
                danhSachDiaDiem,
                databaseHelper
        );

        listPlace.setAdapter(adapter);
    }


    // ==========================================
    // KHI QUAY LẠI ACTIVITY
    // LOAD LẠI DỮ LIỆU
    // ==========================================

    @Override
    protected void onResume() {
        super.onResume();

        loadDanhSach();
    }
}