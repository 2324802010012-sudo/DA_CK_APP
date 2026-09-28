package com.example.da_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

public class ExploreActivity extends AppCompatActivity {

    // =====================================================
    // VIEW
    // =====================================================

    private TextView btnBack;

    private TextView filterAll;
    private TextView filterNorth;
    private TextView filterCentral;
    private TextView filterSouth;

    private EditText edtSearchPlace;

    private LinearLayout layoutPlaceList;


    // =====================================================
    // DATABASE
    // =====================================================

    private DatabaseHelper databaseHelper;


    // =====================================================
    // DANH SÁCH
    // =====================================================

    private ArrayList<DiaDiem> danhSachGoc;

    private String khuVucDangChon = "Tất cả";


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explore);


        // =================================================
        // ÁNH XẠ VIEW
        // =================================================

        btnBack =
                findViewById(R.id.btnBack);

        filterAll =
                findViewById(R.id.filterAll);

        filterNorth =
                findViewById(R.id.filterNorth);

        filterCentral =
                findViewById(R.id.filterCentral);

        filterSouth =
                findViewById(R.id.filterSouth);

        edtSearchPlace =
                findViewById(R.id.edtSearchPlace);

        layoutPlaceList =
                findViewById(R.id.layoutPlaceList);


        // =================================================
        // DATABASE
        // =================================================

        databaseHelper =
                new DatabaseHelper(this);


        // =================================================
        // QUAY LẠI
        // =================================================

        btnBack.setOnClickListener(v -> {

            finish();

        });


        // =================================================
        // FILTER TẤT CẢ
        // =================================================

        filterAll.setOnClickListener(v -> {

            khuVucDangChon = "Tất cả";

            capNhatGiaoDienFilter();

            locDanhSach();

        });


        // =================================================
        // FILTER MIỀN BẮC
        // =================================================

        filterNorth.setOnClickListener(v -> {

            khuVucDangChon = "Miền Bắc";

            capNhatGiaoDienFilter();

            locDanhSach();

        });


        // =================================================
        // FILTER MIỀN TRUNG
        // =================================================

        filterCentral.setOnClickListener(v -> {

            khuVucDangChon = "Miền Trung";

            capNhatGiaoDienFilter();

            locDanhSach();

        });


        // =================================================
        // FILTER MIỀN NAM
        // =================================================

        filterSouth.setOnClickListener(v -> {

            khuVucDangChon = "Miền Nam";

            capNhatGiaoDienFilter();

            locDanhSach();

        });


        // =================================================
        // TÌM KIẾM
        // =================================================

        edtSearchPlace.addTextChangedListener(

                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {

                    }


                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        locDanhSach();

                    }


                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {

                    }
                }
        );


        // =================================================
        // FILTER BAN ĐẦU
        // =================================================

        capNhatGiaoDienFilter();
    }


    // =====================================================
    // ON RESUME
    // =====================================================

    @Override
    protected void onResume() {

        super.onResume();

        /*
         * Khi từ PlaceDetailActivity quay lại,
         * danh sách sẽ được tạo lại.
         *
         * Vì trạng thái yêu thích được đọc từ SQLite
         * nên icon tim cũng tự đồng bộ.
         */

        loadDuLieu();
    }


    // =====================================================
    // LOAD SQLITE
    // =====================================================

    private void loadDuLieu() {

        danhSachGoc =
                databaseHelper.layTatCaDiaDiem();


        if (danhSachGoc == null) {

            danhSachGoc =
                    new ArrayList<>();
        }


        locDanhSach();
    }


    // =====================================================
    // LỌC DANH SÁCH
    // =====================================================

    private void locDanhSach() {

        if (danhSachGoc == null) {

            return;
        }


        ArrayList<DiaDiem> ketQua =
                new ArrayList<>();


        String tuKhoa =
                edtSearchPlace
                        .getText()
                        .toString()
                        .trim();


        String tuKhoaChuanHoa =
                chuanHoaChuoi(
                        tuKhoa
                );


        for (DiaDiem diaDiem : danhSachGoc) {

            // =============================================
            // KHU VỰC
            // =============================================

            boolean dungKhuVuc;


            if (khuVucDangChon.equals("Tất cả")) {

                dungKhuVuc = true;

            } else {

                String khuVucDiaDiem =
                        chuanHoaChuoi(
                                diaDiem.getKhuVuc()
                        );


                String khuVucCanTim =
                        chuanHoaChuoi(
                                khuVucDangChon
                        );


                dungKhuVuc =
                        khuVucDiaDiem.equals(
                                khuVucCanTim
                        );
            }


            // =============================================
            // TÌM KIẾM
            // =============================================

            String tenDiaDiem =
                    chuanHoaChuoi(
                            diaDiem.getTenDiaDiem()
                    );


            String diaChi =
                    chuanHoaChuoi(
                            diaDiem.getDiaChi()
                    );


            boolean dungTimKiem =
                    tuKhoaChuanHoa.isEmpty()

                            || tenDiaDiem.contains(
                            tuKhoaChuanHoa
                    )

                            || diaChi.contains(
                            tuKhoaChuanHoa
                    );


            // =============================================
            // KẾT QUẢ
            // =============================================

            if (dungKhuVuc
                    && dungTimKiem) {

                ketQua.add(
                        diaDiem
                );
            }
        }


        hienThiDanhSach(
                ketQua
        );
    }


    // =====================================================
    // HIỂN THỊ DANH SÁCH
    // =====================================================

    private void hienThiDanhSach(
            ArrayList<DiaDiem> danhSach
    ) {

        layoutPlaceList.removeAllViews();


        // =================================================
        // DANH SÁCH RỖNG
        // =================================================

        if (danhSach.isEmpty()) {

            TextView txtEmpty =
                    new TextView(this);


            txtEmpty.setText(
                    "Không có địa điểm phù hợp"
            );


            txtEmpty.setTextSize(
                    15
            );


            txtEmpty.setTextColor(
                    Color.parseColor(
                            "#8493A8"
                    )
            );


            txtEmpty.setGravity(
                    Gravity.CENTER
            );


            txtEmpty.setPadding(
                    20,
                    70,
                    20,
                    70
            );


            layoutPlaceList.addView(
                    txtEmpty
            );


            return;
        }


        // =================================================
        // TẠO CARD
        // =================================================

        for (DiaDiem diaDiem : danhSach) {

            View itemView =
                    LayoutInflater
                            .from(this)
                            .inflate(
                                    R.layout.item_user_place,
                                    layoutPlaceList,
                                    false
                            );


            // =============================================
            // ÁNH XẠ
            // =============================================

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


            TextView btnFavorite =
                    itemView.findViewById(
                            R.id.btnFavorite
                    );


            // =============================================
            // TÊN
            // =============================================

            txtPlaceName.setText(
                    diaDiem.getTenDiaDiem()
            );


            // =============================================
            // ĐỊA CHỈ
            // =============================================

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


            // =============================================
            // ĐÁNH GIÁ + DANH MỤC
            // =============================================

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


            // =============================================
            // ẢNH
            // =============================================

            hienThiAnh(
                    imgPlace,
                    diaDiem.getHinhAnh()
            );


            // =============================================
            // ĐỒNG BỘ TIM TỪ SQLITE
            // =============================================

            capNhatIconYeuThich(
                    btnFavorite,
                    diaDiem.getMaDiaDiem()
            );


            // =============================================
            // CLICK CARD
            // =============================================

            itemView.setOnClickListener(v -> {

                moChiTietDiaDiem(
                        diaDiem
                );

            });


            // =============================================
            // CLICK YÊU THÍCH
            // =============================================

            btnFavorite.setOnClickListener(v -> {

                xuLyYeuThich(
                        btnFavorite,
                        diaDiem
                );

            });


            // =============================================
            // THÊM CARD
            // =============================================

            layoutPlaceList.addView(
                    itemView
            );
        }
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
    // LẤY MÃ USER
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
    // ĐỒNG BỘ ICON YÊU THÍCH
    // =====================================================

    private void capNhatIconYeuThich(
            TextView btnFavorite,
            int maDiaDiem
    ) {

        // =================================================
        // KHÁCH CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            btnFavorite.setText(
                    "♡"
            );

            return;
        }


        // =================================================
        // USER
        // =================================================

        int maNguoiDung =
                layMaNguoiDung();


        if (maNguoiDung == -1) {

            btnFavorite.setText(
                    "♡"
            );

            return;
        }


        boolean daYeuThich =
                databaseHelper.daYeuThich(
                        maNguoiDung,
                        maDiaDiem
                );


        if (daYeuThich) {

            btnFavorite.setText(
                    "♥"
            );

        } else {

            btnFavorite.setText(
                    "♡"
            );
        }
    }


    // =====================================================
    // XỬ LÝ YÊU THÍCH
    // =====================================================

    private void xuLyYeuThich(
            TextView btnFavorite,
            DiaDiem diaDiem
    ) {

        // =================================================
        // CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            hienThiYeuCauDangNhap();

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


        int maDiaDiem =
                diaDiem.getMaDiaDiem();


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

                btnFavorite.setText(
                        "♡"
                );


                Toast.makeText(
                        this,
                        "Đã bỏ "
                                + diaDiem.getTenDiaDiem()
                                + " khỏi yêu thích",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Không thể bỏ khỏi yêu thích",
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

                btnFavorite.setText(
                        "♥"
                );


                Toast.makeText(
                        this,
                        "Đã thêm "
                                + diaDiem.getTenDiaDiem()
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
    // HỘP THOẠI ĐĂNG NHẬP
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
                                            ExploreActivity.this,
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

    private void hienThiAnh(
            ImageView imageView,
            String hinhAnh
    ) {

        // =================================================
        // KHÔNG CÓ ẢNH
        // =================================================

        if (hinhAnh == null
                || hinhAnh.trim().isEmpty()) {

            imageView.setImageResource(
                    R.drawable.halong_bay
            );

            return;
        }


        // =================================================
        // URI
        // =================================================

        if (hinhAnh.startsWith("content://")
                || hinhAnh.startsWith("file://")) {

            try {

                Uri uri =
                        Uri.parse(
                                hinhAnh
                        );


                imageView.setImageURI(
                        uri
                );

            } catch (Exception e) {

                imageView.setImageResource(
                        R.drawable.halong_bay
                );
            }


            return;
        }


        // =================================================
        // DRAWABLE
        // =================================================

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


    // =====================================================
    // MỞ CHI TIẾT
    // =====================================================

    private void moChiTietDiaDiem(
            DiaDiem diaDiem
    ) {

        Intent intent =
                new Intent(
                        ExploreActivity.this,
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


        startActivity(
                intent
        );
    }


    // =====================================================
    // CẬP NHẬT FILTER
    // =====================================================

    private void capNhatGiaoDienFilter() {

        datFilterChuaChon(
                filterAll
        );

        datFilterChuaChon(
                filterNorth
        );

        datFilterChuaChon(
                filterCentral
        );

        datFilterChuaChon(
                filterSouth
        );


        if (khuVucDangChon.equals("Tất cả")) {

            datFilterDangChon(
                    filterAll
            );

        } else if (
                khuVucDangChon.equals("Miền Bắc")
        ) {

            datFilterDangChon(
                    filterNorth
            );

        } else if (
                khuVucDangChon.equals("Miền Trung")
        ) {

            datFilterDangChon(
                    filterCentral
            );

        } else if (
                khuVucDangChon.equals("Miền Nam")
        ) {

            datFilterDangChon(
                    filterSouth
            );
        }
    }


    // =====================================================
    // FILTER ĐANG CHỌN
    // =====================================================

    private void datFilterDangChon(
            TextView textView
    ) {

        textView.setBackgroundResource(
                R.drawable.bg_filter_selected
        );


        textView.setTextColor(
                Color.WHITE
        );


        textView.setTypeface(
                null,
                Typeface.BOLD
        );
    }


    // =====================================================
    // FILTER CHƯA CHỌN
    // =====================================================

    private void datFilterChuaChon(
            TextView textView
    ) {

        textView.setBackgroundResource(
                R.drawable.bg_filter
        );


        textView.setTextColor(
                Color.parseColor(
                        "#526780"
                )
        );


        textView.setTypeface(
                null,
                Typeface.NORMAL
        );
    }


    // =====================================================
    // CHUẨN HÓA CHUỖI
    // =====================================================

    private String chuanHoaChuoi(
            String text
    ) {

        if (text == null) {

            return "";
        }


        String normalized =
                Normalizer.normalize(
                        text,
                        Normalizer.Form.NFD
                );


        normalized =
                normalized.replaceAll(
                        "\\p{InCombiningDiacriticalMarks}+",
                        ""
                );


        normalized =
                normalized
                        .replace(
                                "Đ",
                                "D"
                        )
                        .replace(
                                "đ",
                                "d"
                        );


        return normalized
                .toLowerCase(
                        Locale.ROOT
                )
                .trim();
    }
}