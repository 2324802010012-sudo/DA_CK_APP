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
    // =====================================================
// VIEW ĐÁNH GIÁ
// =====================================================

    private TextView txtRatingInfo;
    private TextView txtYourRating;

    private TextView star1;
    private TextView star2;
    private TextView star3;
    private TextView star4;
    private TextView star5;

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
// =================================================
// ÁNH XẠ ĐÁNH GIÁ
// =================================================

        txtRatingInfo =
                findViewById(R.id.txtRatingInfo);

        txtYourRating =
                findViewById(R.id.txtYourRating);

        star1 =
                findViewById(R.id.star1);

        star2 =
                findViewById(R.id.star2);

        star3 =
                findViewById(R.id.star3);

        star4 =
                findViewById(R.id.star4);

        star5 =
                findViewById(R.id.star5);
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
// ĐÁNH GIÁ 1 → 5 SAO
// =================================================

        star1.setOnClickListener(v -> {
            xuLyDanhGia(1);
        });

        star2.setOnClickListener(v -> {
            xuLyDanhGia(2);
        });

        star3.setOnClickListener(v -> {
            xuLyDanhGia(3);
        });

        star4.setOnClickListener(v -> {
            xuLyDanhGia(4);
        });

        star5.setOnClickListener(v -> {
            xuLyDanhGia(5);
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

            // Đồng bộ yêu thích
            capNhatTrangThaiYeuThich();

            // Đọc lại địa điểm vì điểm có thể đã thay đổi
            diaDiem =
                    databaseHelper.layDiaDiemTheoMa(
                            maDiaDiem
                    );

            // Đồng bộ đánh giá
            capNhatGiaoDienDanhGia();
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

// Hiển thị điểm + số lượt + đánh giá của user
        capNhatGiaoDienDanhGia();
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
// XỬ LÝ KHI USER CHỌN SAO
// =====================================================

    private void xuLyDanhGia(int soSao) {

        // =================================================
        // CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            hienThiYeuCauDangNhapDanhGia();

            return;
        }


        // =================================================
        // LẤY USER
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
        // LƯU SQLITE
        // =================================================

        boolean ketQua =
                databaseHelper.themHoacSuaDanhGia(
                        maNguoiDung,
                        maDiaDiem,
                        soSao
                );


        if (ketQua) {

            Toast.makeText(
                    this,
                    "Bạn đã đánh giá "
                            + soSao
                            + " sao",
                    Toast.LENGTH_SHORT
            ).show();


            // Đọc lại địa điểm vì điểm trung bình đã thay đổi
            diaDiem =
                    databaseHelper.layDiaDiemTheoMa(
                            maDiaDiem
                    );


            // Cập nhật giao diện
            capNhatGiaoDienDanhGia();

        } else {

            Toast.makeText(
                    this,
                    "Không thể lưu đánh giá",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


// =====================================================
// CẬP NHẬT GIAO DIỆN ĐÁNH GIÁ
// =====================================================

    private void capNhatGiaoDienDanhGia() {

        if (diaDiem == null
                || maDiaDiem == -1) {

            return;
        }


        // =================================================
        // ĐIỂM TRUNG BÌNH
        // =================================================

        double diem =
                diaDiem.getDanhGia();


        int soLuot =
                databaseHelper.laySoLuotDanhGia(
                        maDiaDiem
                );


        txtRatingInfo.setText(
                "Điểm hiện tại: "
                        + String.format(
                        java.util.Locale.getDefault(),
                        "%.1f",
                        diem
                )
                        + " / 5"
                        + "   •   "
                        + soLuot
                        + " lượt đánh giá"
        );


        // =================================================
        // CẬP NHẬT ĐIỂM Ở PHÍA TRÊN
        // =================================================

        String danhMuc =
                diaDiem.getDanhMuc();


        if (danhMuc == null
                || danhMuc.trim().isEmpty()) {

            danhMuc = "Du lịch";
        }


        txtRating.setText(
                "★ "
                        + String.format(
                        java.util.Locale.getDefault(),
                        "%.1f",
                        diem
                )
                        + "   •   "
                        + danhMuc
        );


        // =================================================
        // KHÁCH CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            hienThiSoSaoDaChon(0);

            txtYourRating.setText(
                    "Đăng nhập để đánh giá địa điểm"
            );

            return;
        }


        // =================================================
        // LẤY ĐÁNH GIÁ CỦA USER
        // =================================================

        int maNguoiDung =
                layMaNguoiDung();


        if (maNguoiDung == -1) {

            hienThiSoSaoDaChon(0);

            return;
        }


        int soSao =
                databaseHelper.layDanhGiaCuaUser(
                        maNguoiDung,
                        maDiaDiem
                );


        hienThiSoSaoDaChon(
                soSao
        );


        if (soSao == 0) {

            txtYourRating.setText(
                    "Chạm vào ngôi sao để đánh giá"
            );

        } else {

            txtYourRating.setText(
                    "Đánh giá của bạn: "
                            + soSao
                            + " / 5 sao"
            );
        }
    }


// =====================================================
// HIỂN THỊ SỐ SAO USER ĐÃ CHỌN
// =====================================================

    private void hienThiSoSaoDaChon(
            int soSao
    ) {

        star1.setText(
                soSao >= 1 ? "★" : "☆"
        );

        star2.setText(
                soSao >= 2 ? "★" : "☆"
        );

        star3.setText(
                soSao >= 3 ? "★" : "☆"
        );

        star4.setText(
                soSao >= 4 ? "★" : "☆"
        );

        star5.setText(
                soSao >= 5 ? "★" : "☆"
        );
    }


// =====================================================
// YÊU CẦU ĐĂNG NHẬP ĐỂ ĐÁNH GIÁ
// =====================================================

    private void hienThiYeuCauDangNhapDanhGia() {

        new AlertDialog.Builder(this)

                .setTitle(
                        "Yêu cầu đăng nhập"
                )

                .setMessage(
                        "Bạn cần đăng nhập để đánh giá địa điểm."
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

                            startActivity(
                                    intent
                            );
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