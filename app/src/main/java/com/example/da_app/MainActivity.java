package com.example.da_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // =====================================================
    // MENU
    // =====================================================
    private View menuLocation;
    private View menuJourney;
    private View menuFavorite;
    private View menuTips;

    private View btnMenu;


    // =====================================================
    // CARD
    // =====================================================

    private View cardHaLong;


    // =====================================================
    // BOTTOM NAVIGATION
    // =====================================================
    private View navHome;
    private View navExplore;
    private View navFavorite;
    private View navProfile;
    private View navSuggest;


    // =====================================================
    // DATABASE
    // =====================================================

    private DatabaseHelper databaseHelper;


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);


        // =================================================
        // STATUS BAR
        // =================================================

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );


        // =================================================
        // DATABASE
        // =================================================

        databaseHelper =
                new DatabaseHelper(this);


        // =================================================
        // ÁNH XẠ MENU
        // =================================================

        menuLocation =
                findViewById(R.id.menuLocation);

        menuJourney =
                findViewById(R.id.menuJourney);

        menuFavorite =
                findViewById(R.id.menuFavorite);

        menuTips =
                findViewById(R.id.menuTips);
        btnMenu =
                findViewById(R.id.btnMenu);

        // =================================================
        // CARD
        // =================================================

        cardHaLong =
                findViewById(R.id.cardHaLong);


        // =================================================
        // BOTTOM NAVIGATION
        // =================================================

        navHome =
                findViewById(R.id.navHome);

        navExplore =
                findViewById(R.id.navExplore);

        navFavorite =
                findViewById(R.id.navFavorite);

        navProfile =
                findViewById(R.id.navProfile);

        navSuggest =
                findViewById(R.id.navSuggest);
        btnMenu.setOnClickListener(v -> {

            xuLyTaiKhoan();

        });
        // =================================================
        // ĐỊA ĐIỂM
        // KHÔNG CẦN ĐĂNG NHẬP
        // =================================================

        menuLocation.setOnClickListener(v -> {

            moKhamPha();

        });


        // =================================================
        // HÀNH TRÌNH
        // Hiện tại chưa bắt đăng nhập
        // =================================================

        menuJourney.setOnClickListener(v -> {

            // Sẽ làm JourneyActivity sau

        });


        // =================================================
        // YÊU THÍCH
        // BẮT BUỘC ĐĂNG NHẬP
        // =================================================

        menuFavorite.setOnClickListener(v -> {

            xuLyMoYeuThich();

        });


        // =================================================
        // MẸO DU LỊCH
        // KHÔNG CẦN ĐĂNG NHẬP
        // =================================================

        menuTips.setOnClickListener(v -> {

            // Sẽ làm TipsActivity sau

        });


        // =================================================
        // CARD VỊNH HẠ LONG
        // =================================================

        cardHaLong.setOnClickListener(v -> {

            moChiTietHaLong();

        });


        // =================================================
        // NAV HOME
        // =================================================

        navHome.setOnClickListener(v -> {

            // Đang ở trang chủ

        });


        // =================================================
        // NAV KHÁM PHÁ
        // =================================================

        navExplore.setOnClickListener(v -> {

            moKhamPha();

        });


        // =================================================
        // NAV YÊU THÍCH
        // =================================================

        navFavorite.setOnClickListener(v -> {

            xuLyMoYeuThich();

        });


        // =================================================
        // NAV CÁ NHÂN
        // =================================================

        navProfile.setOnClickListener(v -> {

            xuLyTaiKhoan();

        });
        // =================================================
        // NAV GỢI Ý
        // =================================================

        navSuggest.setOnClickListener(v -> {

            hienThiGoiYDuLich();

        });
    }


    // =====================================================
    // MỞ KHÁM PHÁ
    // =====================================================

    private void moKhamPha() {

        Intent intent = new Intent(
                MainActivity.this,
                ExploreActivity.class
        );

        startActivity(intent);
    }


    // =====================================================
    // MỞ CHI TIẾT HẠ LONG
    // =====================================================

    private void moChiTietHaLong() {

        ArrayList<DiaDiem> danhSach =
                databaseHelper.layTatCaDiaDiem();


        DiaDiem haLong = null;


        for (DiaDiem diaDiem : danhSach) {

            if (diaDiem
                    .getTenDiaDiem()
                    .equalsIgnoreCase("Vịnh Hạ Long")) {

                haLong = diaDiem;

                break;
            }
        }


        if (haLong == null) {

            return;
        }


        Intent intent = new Intent(
                MainActivity.this,
                PlaceDetailActivity.class
        );


        intent.putExtra(
                "MA_DIA_DIEM",
                haLong.getMaDiaDiem()
        );


        intent.putExtra(
                "PLACE_NAME",
                haLong.getTenDiaDiem()
        );


        startActivity(intent);
    }
// =====================================================
// MỞ DANH SÁCH YÊU THÍCH
// =====================================================

    private void xuLyMoYeuThich() {

        // =================================================
        // CHƯA ĐĂNG NHẬP
        // =================================================

        if (!daDangNhap()) {

            hienThiYeuCauDangNhap(
                    "Bạn cần đăng nhập để xem danh sách yêu thích."
            );

            return;
        }


        // =================================================
        // ĐÃ ĐĂNG NHẬP
        // =================================================

        Intent intent =
                new Intent(
                        MainActivity.this,
                        FavoriteActivity.class
                );

        startActivity(intent);
    }
// =====================================================
// CÁ NHÂN / PROFILE
// =====================================================

    private void xuLyTaiKhoan() {

        // =============================================
        // CHƯA ĐĂNG NHẬP
        // =============================================

        if (!daDangNhap()) {

            hienThiYeuCauDangNhap(
                    "Bạn cần đăng nhập để xem hồ sơ cá nhân."
            );

            return;
        }


        // =============================================
        // ĐÃ ĐĂNG NHẬP
        // MỞ MÀN HÌNH PROFILE
        // =============================================

        Intent intent =
                new Intent(
                        MainActivity.this,
                        ProfileActivity.class
                );

        startActivity(intent);
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
// YÊU CẦU ĐĂNG NHẬP
// =====================================================

    private void hienThiYeuCauDangNhap(String noiDung) {

        new AlertDialog.Builder(this)

                .setTitle("Yêu cầu đăng nhập")

                .setMessage(noiDung)

                .setNegativeButton(
                        "HỦY",
                        null
                )

                .setPositiveButton(
                        "ĐĂNG NHẬP",
                        (dialog, which) -> {

                            Intent intent =
                                    new Intent(
                                            MainActivity.this,
                                            LoginActivity.class
                                    );

                            startActivity(intent);
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

        // App vẫn cho khách sử dụng
        recreate();
    }
    // =====================================================
// GỢI Ý DU LỊCH
// =====================================================

    private void hienThiGoiYDuLich() {

        new AlertDialog.Builder(this)

                .setTitle("Gợi ý du lịch")

                .setMessage(
                        "Chức năng này sẽ giúp bạn tìm địa điểm " +
                                "du lịch phù hợp với sở thích."
                )

                .setPositiveButton(
                        "OK",
                        null
                )

                .show();
    }
}