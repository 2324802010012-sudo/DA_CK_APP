package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

public class PlaceDetailActivity extends AppCompatActivity {

    // =====================================================
    // VIEW
    // =====================================================

    private TextView btnBack;
    private TextView btnFavorite;
    private TextView btnJourney;

    private TextView txtPlaceName;
    private TextView txtLocation;
    private TextView txtRating;
    private TextView txtDescription;
    private TextView txtBestTime;

    private ImageView imgPlace;


    // =====================================================
    // DATABASE
    // =====================================================

    private DatabaseHelper databaseHelper;


    // =====================================================
    // DỮ LIỆU ĐỊA ĐIỂM
    // =====================================================

    private DiaDiem diaDiem;

    private int maDiaDiem = -1;

    private String placeName = "";

    private boolean isFavorite = false;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_detail);


        // =================================================
        // ÁNH XẠ VIEW
        // =================================================

        btnBack =
                findViewById(R.id.btnBack);

        btnFavorite =
                findViewById(R.id.btnFavorite);

        btnJourney =
                findViewById(R.id.btnJourney);

        txtPlaceName =
                findViewById(R.id.txtPlaceName);

        txtLocation =
                findViewById(R.id.txtLocation);

        txtRating =
                findViewById(R.id.txtRating);

        txtDescription =
                findViewById(R.id.txtDescription);

        txtBestTime =
                findViewById(R.id.txtBestTime);

        imgPlace =
                findViewById(R.id.imgPlace);


        // =================================================
        // DATABASE
        // =================================================

        databaseHelper =
                new DatabaseHelper(this);


        // =================================================
        // NHẬN MÃ ĐỊA ĐIỂM
        // =================================================

        maDiaDiem =
                getIntent().getIntExtra(
                        "MA_DIA_DIEM",
                        -1
                );


        placeName =
                getIntent().getStringExtra(
                        "PLACE_NAME"
                );


        // =================================================
        // KIỂM TRA MÃ ĐỊA ĐIỂM
        // =================================================

        if (maDiaDiem == -1) {

            Toast.makeText(
                    this,
                    "Không tìm thấy mã địa điểm",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }


        // =================================================
        // LOAD ĐỊA ĐIỂM
        // =================================================

        loadDiaDiemTheoMa();


        // =================================================
        // QUAY LẠI
        // =================================================

        btnBack.setOnClickListener(v -> {

            finish();

        });


        // =================================================
        // YÊU THÍCH
        // =================================================

        btnFavorite.setOnClickListener(v -> {

            xuLyYeuThich();

        });


        // =================================================
        // GỢI Ý HÀNH TRÌNH
        // Không bắt buộc đăng nhập ở thời điểm hiện tại
        // =================================================

        btnJourney.setOnClickListener(v -> {

            Toast.makeText(
                    PlaceDetailActivity.this,
                    "Gợi ý hành trình " + placeName,
                    Toast.LENGTH_SHORT
            ).show();

        });
    }


    // =====================================================
    // KHI QUAY LẠI MÀN HÌNH
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();

        /*
         * Ví dụ:
         * Khách đang xem địa điểm
         * → bấm yêu thích
         * → sang Login
         * → đăng nhập
         *
         * Khi quay lại màn hình này,
         * trạng thái yêu thích sẽ được kiểm tra lại.
         */

        if (databaseHelper != null
                && maDiaDiem != -1) {

            capNhatTrangThaiYeuThich();
        }
    }


    // =====================================================
    // LOAD ĐỊA ĐIỂM THEO MÃ
    // =====================================================

    private void loadDiaDiemTheoMa() {

        diaDiem =
                databaseHelper.layDiaDiemTheoMa(
                        maDiaDiem
                );


        // =================================================
        // KHÔNG TÌM THẤY
        // =================================================

        if (diaDiem == null) {

            Toast.makeText(
                    this,
                    "Không tìm thấy địa điểm",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }


        // =================================================
        // TÊN
        // =================================================

        placeName =
                diaDiem.getTenDiaDiem();


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

            txtLocation.setText(
                    "📍 Chưa cập nhật địa chỉ"
            );

        } else {

            txtLocation.setText(
                    "📍 " + diaChi + ", Việt Nam"
            );
        }


        // =================================================
        // DANH MỤC
        // =================================================

        String danhMuc =
                diaDiem.getDanhMuc();


        if (danhMuc == null
                || danhMuc.trim().isEmpty()) {

            danhMuc = "Du lịch";
        }


        // =================================================
        // ĐÁNH GIÁ HIỆN TẠI
        // =================================================

        txtRating.setText(
                "★ "
                        + diaDiem.getDanhGia()
                        + "   •   "
                        + danhMuc
        );


        // =================================================
        // MÔ TẢ
        // =================================================

        String moTa =
                diaDiem.getMoTa();


        if (moTa == null
                || moTa.trim().isEmpty()) {

            txtDescription.setText(
                    "Chưa có mô tả cho địa điểm này."
            );

        } else {

            txtDescription.setText(
                    moTa
            );
        }


        // =================================================
        // THỜI ĐIỂM THÍCH HỢP
        // =================================================

        String thoiDiem =
                diaDiem.getThoiDiemThichHop();


        if (thoiDiem == null
                || thoiDiem.trim().isEmpty()) {

            txtBestTime.setText(
                    "Chưa cập nhật"
            );

        } else {

            txtBestTime.setText(
                    thoiDiem
            );
        }


        // =================================================
        // ẢNH
        // =================================================

        hienThiAnh(
                diaDiem.getHinhAnh()
        );


        // =================================================
        // KIỂM TRA YÊU THÍCH
        // =================================================

        capNhatTrangThaiYeuThich();
    }


    // =====================================================
    // KIỂM TRA ĐÃ ĐĂNG NHẬP CHƯA
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
    // LẤY MÃ NGƯỜI DÙNG ĐANG ĐĂNG NHẬP
    // =====================================================

    private int layMaNguoiDung() {

        SharedPreferences preferences =
                getSharedPreferences(
                        "TAI_KHOAN",
                        MODE_PRIVATE
                );


        return preferences.getInt(
                "MA_NGUOI_DUNG",
                -1
        );
    }


    // =====================================================
    // CẬP NHẬT ICON YÊU THÍCH
    // =====================================================

    private void capNhatTrangThaiYeuThich() {

        // =================================================
        // KHÁCH
        // =================================================

        if (!daDangNhap()) {

            isFavorite = false;

            btnFavorite.setText("♡");

            return;
        }


        // =================================================
        // USER
        // =================================================

        int maNguoiDung =
                layMaNguoiDung();


        if (maNguoiDung == -1
                || maDiaDiem == -1) {

            isFavorite = false;

            btnFavorite.setText("♡");

            return;
        }


        isFavorite =
                databaseHelper.daYeuThich(
                        maNguoiDung,
                        maDiaDiem
                );


        if (isFavorite) {

            btnFavorite.setText("♥");

        } else {

            btnFavorite.setText("♡");
        }
    }


    // =====================================================
    // XỬ LÝ KHI BẤM YÊU THÍCH
    // =====================================================

    private void xuLyYeuThich() {

        // =================================================
        // CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            hienThiYeuCauDangNhap();

            return;
        }


        // =================================================
        // ĐÃ ĐĂNG NHẬP
        // =================================================

        int maNguoiDung =
                layMaNguoiDung();


        if (maNguoiDung == -1) {

            Toast.makeText(
                    this,
                    "Không tìm thấy thông tin người dùng",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // =================================================
        // ĐÃ YÊU THÍCH → XÓA
        // =================================================

        if (databaseHelper.daYeuThich(
                maNguoiDung,
                maDiaDiem
        )) {

            boolean ketQua =
                    databaseHelper.xoaYeuThich(
                            maNguoiDung,
                            maDiaDiem
                    );


            if (ketQua) {

                isFavorite = false;

                btnFavorite.setText("♡");


                Toast.makeText(
                        this,
                        "Đã bỏ "
                                + placeName
                                + " khỏi yêu thích",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Không thể bỏ yêu thích",
                        Toast.LENGTH_SHORT
                ).show();
            }

        }

        // =================================================
        // CHƯA YÊU THÍCH → THÊM
        // =================================================

        else {

            boolean ketQua =
                    databaseHelper.themYeuThich(
                            maNguoiDung,
                            maDiaDiem
                    );


            if (ketQua) {

                isFavorite = true;

                btnFavorite.setText("♥");


                Toast.makeText(
                        this,
                        "Đã thêm "
                                + placeName
                                + " vào yêu thích",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Không thể thêm vào yêu thích",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }


    // =====================================================
    // HỘP THOẠI YÊU CẦU ĐĂNG NHẬP
    // =====================================================

    private void hienThiYeuCauDangNhap() {

        new AlertDialog.Builder(this)

                .setTitle(
                        "Yêu cầu đăng nhập"
                )

                .setMessage(
                        "Bạn cần đăng nhập để thêm địa điểm vào yêu thích."
                )

                .setNegativeButton(
                        "HỦY",
                        null
                )

                .setPositiveButton(
                        "ĐĂNG NHẬP",
                        (dialog, which) -> {

                            Intent intent =
                                    new Intent(
                                            PlaceDetailActivity.this,
                                            LoginActivity.class
                                    );

                            startActivity(intent);
                        }
                )

                .show();
    }


    // =====================================================
    // HIỂN THỊ ẢNH
    // =====================================================

    private void hienThiAnh(String hinhAnh) {

        // =================================================
        // KHÔNG CÓ ẢNH
        // =================================================

        if (hinhAnh == null
                || hinhAnh.trim().isEmpty()) {

            imgPlace.setImageResource(
                    R.drawable.halong_bay
            );

            return;
        }


        // =================================================
        // ẢNH ADMIN CHỌN TỪ MÁY
        // =================================================

        if (hinhAnh.startsWith("content://")
                || hinhAnh.startsWith("file://")) {

            try {

                Uri uri =
                        Uri.parse(
                                hinhAnh
                        );


                imgPlace.setImageURI(
                        uri
                );

            } catch (Exception e) {

                imgPlace.setImageResource(
                        R.drawable.halong_bay
                );
            }

            return;
        }


        // =================================================
        // ẢNH TRONG DRAWABLE
        // =================================================

        int resourceId =
                getResources()
                        .getIdentifier(
                                hinhAnh,
                                "drawable",
                                getPackageName()
                        );


        if (resourceId != 0) {

            imgPlace.setImageResource(
                    resourceId
            );

        } else {

            imgPlace.setImageResource(
                    R.drawable.halong_bay
            );
        }
    }
}