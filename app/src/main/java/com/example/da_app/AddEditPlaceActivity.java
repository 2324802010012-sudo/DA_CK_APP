package com.example.da_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da_app.database.DatabaseHelper;
import com.example.da_app.model.DiaDiem;

public class AddEditPlaceActivity extends AppCompatActivity {

    private TextView btnBack;
    private TextView txtTitle;

    private EditText edtPlaceName;
    private EditText edtAddress;
    private EditText edtRegion;
    private EditText edtCategory;
    private EditText edtRating;
    private EditText edtDescription;
    private EditText edtBestTime;

    private ImageView imgPreview;

    private Button btnChooseImage;
    private Button btnSave;

    private DatabaseHelper databaseHelper;

    private int maDiaDiem = -1;

    // URI ảnh đang được chọn
    private String imageUriString = "";


    // =====================================================
    // CHỌN ẢNH
    // =====================================================

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {

                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {

                            Uri uri = result.getData().getData();

                            if (uri != null) {

                                // Cố gắng giữ quyền đọc ảnh lâu dài
                                try {

                                    getContentResolver()
                                            .takePersistableUriPermission(
                                                    uri,
                                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                            );

                                } catch (Exception ignored) {
                                }

                                imageUriString = uri.toString();

                                imgPreview.setImageURI(uri);
                            }
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_place);


        // =================================================
        // ÁNH XẠ
        // =================================================

        btnBack = findViewById(R.id.btnBack);
        txtTitle = findViewById(R.id.txtTitle);

        edtPlaceName = findViewById(R.id.edtPlaceName);
        edtAddress = findViewById(R.id.edtAddress);
        edtRegion = findViewById(R.id.edtRegion);
        edtCategory = findViewById(R.id.edtCategory);
        edtRating = findViewById(R.id.edtRating);
        edtDescription = findViewById(R.id.edtDescription);
        edtBestTime = findViewById(R.id.edtBestTime);

        imgPreview = findViewById(R.id.imgPreview);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);


        databaseHelper =
                new DatabaseHelper(this);


        // =================================================
        // KIỂM TRA THÊM / SỬA
        // =================================================

        maDiaDiem = getIntent().getIntExtra(
                "MA_DIA_DIEM",
                -1
        );


        if (maDiaDiem == -1) {

            txtTitle.setText("Thêm địa điểm");

            btnSave.setText(
                    "THÊM ĐỊA ĐIỂM"
            );

        } else {

            txtTitle.setText(
                    "Sửa địa điểm"
            );

            btnSave.setText(
                    "LƯU THAY ĐỔI"
            );

            loadDiaDiem();
        }


        // =================================================
        // QUAY LẠI
        // =================================================

        btnBack.setOnClickListener(
                v -> finish()
        );


        // =================================================
        // CHỌN ẢNH
        // =================================================

        btnChooseImage.setOnClickListener(
                v -> moThuVienAnh()
        );


        // =================================================
        // LƯU
        // =================================================

        btnSave.setOnClickListener(
                v -> luuDiaDiem()
        );
    }


    // =====================================================
    // MỞ THƯ VIỆN ẢNH
    // =====================================================

    private void moThuVienAnh() {

        Intent intent = new Intent(
                Intent.ACTION_OPEN_DOCUMENT
        );

        intent.setType("image/*");

        intent.addCategory(
                Intent.CATEGORY_OPENABLE
        );

        intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        );

        imagePickerLauncher.launch(intent);
    }


    // =====================================================
    // LOAD DỮ LIỆU KHI SỬA
    // =====================================================

    private void loadDiaDiem() {

        DiaDiem diaDiem =
                databaseHelper.layDiaDiemTheoMa(
                        maDiaDiem
                );


        if (diaDiem == null) {

            Toast.makeText(
                    this,
                    "Không tìm thấy địa điểm",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }


        edtPlaceName.setText(
                diaDiem.getTenDiaDiem()
        );

        edtAddress.setText(
                diaDiem.getDiaChi()
        );

        edtRegion.setText(
                diaDiem.getKhuVuc()
        );

        edtCategory.setText(
                diaDiem.getDanhMuc()
        );

        edtRating.setText(
                String.valueOf(
                        diaDiem.getDanhGia()
                )
        );

        edtDescription.setText(
                diaDiem.getMoTa()
        );

        edtBestTime.setText(
                diaDiem.getThoiDiemThichHop()
        );


        // =================================================
        // LOAD ẢNH CŨ
        // =================================================

        imageUriString =
                diaDiem.getHinhAnh();

        hienThiAnh(imageUriString);
    }


    // =====================================================
    // HIỂN THỊ ẢNH
    // =====================================================

    private void hienThiAnh(String imageValue) {

        if (imageValue == null
                || imageValue.trim().isEmpty()) {

            imgPreview.setImageResource(
                    R.drawable.halong_bay
            );

            return;
        }


        // Ảnh được Admin chọn từ máy
        if (imageValue.startsWith("content://")
                || imageValue.startsWith("file://")) {

            try {

                imgPreview.setImageURI(
                        Uri.parse(imageValue)
                );

            } catch (Exception e) {

                imgPreview.setImageResource(
                        R.drawable.halong_bay
                );
            }

            return;
        }


        // Ảnh có sẵn trong drawable
        int resourceId =
                getResources().getIdentifier(
                        imageValue,
                        "drawable",
                        getPackageName()
                );


        if (resourceId != 0) {

            imgPreview.setImageResource(
                    resourceId
            );

        } else {

            imgPreview.setImageResource(
                    R.drawable.halong_bay
            );
        }
    }


    // =====================================================
    // LƯU
    // =====================================================

    private void luuDiaDiem() {

        String tenDiaDiem =
                edtPlaceName.getText()
                        .toString()
                        .trim();

        String diaChi =
                edtAddress.getText()
                        .toString()
                        .trim();

        String khuVuc =
                edtRegion.getText()
                        .toString()
                        .trim();

        String danhMuc =
                edtCategory.getText()
                        .toString()
                        .trim();

        String danhGiaText =
                edtRating.getText()
                        .toString()
                        .trim();

        String moTa =
                edtDescription.getText()
                        .toString()
                        .trim();

        String thoiDiem =
                edtBestTime.getText()
                        .toString()
                        .trim();


        // =================================================
        // KIỂM TRA TÊN
        // =================================================

        if (TextUtils.isEmpty(tenDiaDiem)) {

            edtPlaceName.setError(
                    "Vui lòng nhập tên địa điểm"
            );

            edtPlaceName.requestFocus();

            return;
        }


        if (TextUtils.isEmpty(diaChi)) {

            edtAddress.setError(
                    "Vui lòng nhập địa chỉ"
            );

            edtAddress.requestFocus();

            return;
        }


        if (TextUtils.isEmpty(khuVuc)) {

            edtRegion.setError(
                    "Vui lòng nhập khu vực"
            );

            edtRegion.requestFocus();

            return;
        }


        // =================================================
        // ĐÁNH GIÁ
        // =================================================

        double danhGia = 0;

        if (!TextUtils.isEmpty(danhGiaText)) {

            try {

                danhGia =
                        Double.parseDouble(
                                danhGiaText
                        );

            } catch (NumberFormatException e) {

                edtRating.setError(
                        "Đánh giá phải là số"
                );

                return;
            }
        }


        if (danhGia < 0 || danhGia > 5) {

            edtRating.setError(
                    "Đánh giá phải từ 0 đến 5"
            );

            return;
        }


        // =================================================
        // THÊM
        // =================================================

        if (maDiaDiem == -1) {

            DiaDiem diaDiem =
                    new DiaDiem(
                            tenDiaDiem,
                            diaChi,
                            khuVuc,
                            danhMuc,
                            danhGia,
                            moTa,
                            thoiDiem,
                            imageUriString
                    );


            boolean thanhCong =
                    databaseHelper.themDiaDiem(
                            diaDiem
                    );


            if (thanhCong) {

                Toast.makeText(
                        this,
                        "Thêm địa điểm thành công",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Thêm địa điểm thất bại",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            // =================================================
            // SỬA
            // =================================================

            DiaDiem diaDiem =
                    new DiaDiem(
                            maDiaDiem,
                            tenDiaDiem,
                            diaChi,
                            khuVuc,
                            danhMuc,
                            danhGia,
                            moTa,
                            thoiDiem,
                            imageUriString
                    );


            boolean thanhCong =
                    databaseHelper.suaDiaDiem(
                            diaDiem
                    );


            if (thanhCong) {

                Toast.makeText(
                        this,
                        "Cập nhật địa điểm thành công",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Cập nhật thất bại",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}