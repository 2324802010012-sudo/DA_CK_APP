package com.example.da_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.da_app.model.DiaDiem;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // =====================================================
    // DATABASE
    // =====================================================

    private static final String DATABASE_NAME = "TravelApp.db";
    private static final int DATABASE_VERSION = 2;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public DatabaseHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }


    // =====================================================
    // TẠO DATABASE
    // =====================================================

    @Override
    public void onCreate(SQLiteDatabase db) {

        // =================================================
        // 1. BẢNG NGƯỜI DÙNG
        // =================================================

        db.execSQL(
                "CREATE TABLE nguoi_dung (" +
                        "ma_nguoi_dung INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ho_ten TEXT NOT NULL, " +
                        "email TEXT NOT NULL UNIQUE, " +
                        "mat_khau TEXT NOT NULL, " +
                        "vai_tro TEXT NOT NULL DEFAULT 'USER', " +
                        "trang_thai INTEGER NOT NULL DEFAULT 1" +
                        ")"
        );


        // =================================================
        // 2. BẢNG ĐỊA ĐIỂM
        // =================================================

        db.execSQL(
                "CREATE TABLE dia_diem (" +
                        "ma_dia_diem INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ten_dia_diem TEXT NOT NULL, " +
                        "dia_chi TEXT, " +
                        "khu_vuc TEXT, " +
                        "danh_muc TEXT, " +
                        "danh_gia REAL DEFAULT 0, " +
                        "mo_ta TEXT, " +
                        "thoi_diem_thich_hop TEXT, " +
                        "hinh_anh TEXT" +
                        ")"
        );


        // =================================================
        // 3. BẢNG YÊU THÍCH
        // =================================================

        db.execSQL(
                "CREATE TABLE yeu_thich (" +
                        "ma_yeu_thich INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ma_nguoi_dung INTEGER NOT NULL, " +
                        "ma_dia_diem INTEGER NOT NULL, " +

                        "FOREIGN KEY(ma_nguoi_dung) " +
                        "REFERENCES nguoi_dung(ma_nguoi_dung), " +

                        "FOREIGN KEY(ma_dia_diem) " +
                        "REFERENCES dia_diem(ma_dia_diem), " +

                        "UNIQUE(ma_nguoi_dung, ma_dia_diem)" +
                        ")"
        );


        // =================================================
        // 4. BẢNG HÀNH TRÌNH
        // =================================================

        db.execSQL(
                "CREATE TABLE hanh_trinh (" +
                        "ma_hanh_trinh INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ma_nguoi_dung INTEGER NOT NULL, " +
                        "ten_hanh_trinh TEXT NOT NULL, " +
                        "ngay_bat_dau TEXT, " +
                        "ngay_ket_thuc TEXT, " +
                        "ghi_chu TEXT, " +

                        "FOREIGN KEY(ma_nguoi_dung) " +
                        "REFERENCES nguoi_dung(ma_nguoi_dung)" +
                        ")"
        );


        // =================================================
        // 5. BẢNG CHI TIẾT HÀNH TRÌNH
        // =================================================

        db.execSQL(
                "CREATE TABLE chi_tiet_hanh_trinh (" +
                        "ma_chi_tiet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ma_hanh_trinh INTEGER NOT NULL, " +
                        "ma_dia_diem INTEGER NOT NULL, " +
                        "ngay_thu INTEGER DEFAULT 1, " +
                        "thu_tu_tham_quan INTEGER DEFAULT 1, " +
                        "ghi_chu TEXT, " +

                        "FOREIGN KEY(ma_hanh_trinh) " +
                        "REFERENCES hanh_trinh(ma_hanh_trinh), " +

                        "FOREIGN KEY(ma_dia_diem) " +
                        "REFERENCES dia_diem(ma_dia_diem)" +
                        ")"
        );


        // =================================================
        // 6. BẢNG MẸO DU LỊCH
        // =================================================

        db.execSQL(
                "CREATE TABLE meo_du_lich (" +
                        "ma_meo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tieu_de TEXT NOT NULL, " +
                        "noi_dung TEXT NOT NULL, " +
                        "danh_muc TEXT" +
                        ")"
        );


        // =================================================
        // THÊM DỮ LIỆU MẪU
        // =================================================

        themDuLieuMau(db);
    }


    // =====================================================
    // THÊM DỮ LIỆU MẪU
    // =====================================================

    private void themDuLieuMau(SQLiteDatabase db) {

        // =================================================
        // ADMIN
        // =================================================

        ContentValues admin = new ContentValues();

        admin.put("ho_ten", "Quản trị viên");
        admin.put("email", "admin@gmail.com");
        admin.put("mat_khau", "123456");
        admin.put("vai_tro", "ADMIN");
        admin.put("trang_thai", 1);

        db.insert(
                "nguoi_dung",
                null,
                admin
        );


        // =================================================
        // USER
        // =================================================

        ContentValues user = new ContentValues();

        user.put("ho_ten", "Người dùng");
        user.put("email", "user@gmail.com");
        user.put("mat_khau", "123456");
        user.put("vai_tro", "USER");
        user.put("trang_thai", 1);

        db.insert(
                "nguoi_dung",
                null,
                user
        );


        // =================================================
        // VỊNH HẠ LONG
        // =================================================

        ContentValues haLong = new ContentValues();

        haLong.put(
                "ten_dia_diem",
                "Vịnh Hạ Long"
        );

        haLong.put(
                "dia_chi",
                "Quảng Ninh"
        );

        haLong.put(
                "khu_vuc",
                "Miền Bắc"
        );

        haLong.put(
                "danh_muc",
                "Thiên nhiên"
        );

        haLong.put(
                "danh_gia",
                4.8
        );

        haLong.put(
                "mo_ta",
                "Vịnh Hạ Long nổi tiếng với cảnh quan biển đảo " +
                        "và nhiều hoạt động tham quan."
        );

        haLong.put(
                "thoi_diem_thich_hop",
                "Tháng 3 - Tháng 10"
        );

        haLong.put(
                "hinh_anh",
                "halong_bay"
        );

        db.insert(
                "dia_diem",
                null,
                haLong
        );


        // =================================================
        // ĐÀ LẠT
        // =================================================

        ContentValues daLat = new ContentValues();

        daLat.put(
                "ten_dia_diem",
                "Đà Lạt"
        );

        daLat.put(
                "dia_chi",
                "Lâm Đồng"
        );

        daLat.put(
                "khu_vuc",
                "Miền Trung"
        );

        daLat.put(
                "danh_muc",
                "Nghỉ dưỡng"
        );

        daLat.put(
                "danh_gia",
                4.7
        );

        daLat.put(
                "mo_ta",
                "Đà Lạt nổi tiếng với khí hậu mát mẻ, " +
                        "rừng thông, hồ nước và những vườn hoa."
        );

        daLat.put(
                "thoi_diem_thich_hop",
                "Tháng 11 - Tháng 4"
        );

        daLat.put(
                "hinh_anh",
                "dalat"
        );

        db.insert(
                "dia_diem",
                null,
                daLat
        );


        // =================================================
        // HỘI AN
        // =================================================

        ContentValues hoiAn = new ContentValues();

        hoiAn.put(
                "ten_dia_diem",
                "Hội An"
        );

        hoiAn.put(
                "dia_chi",
                "Hội An"
        );

        hoiAn.put(
                "khu_vuc",
                "Miền Trung"
        );

        hoiAn.put(
                "danh_muc",
                "Di sản"
        );

        hoiAn.put(
                "danh_gia",
                4.9
        );

        hoiAn.put(
                "mo_ta",
                "Hội An nổi bật với kiến trúc phố cổ, " +
                        "văn hóa truyền thống và ẩm thực đặc sắc."
        );

        hoiAn.put(
                "thoi_diem_thich_hop",
                "Tháng 2 - Tháng 4"
        );

        hoiAn.put(
                "hinh_anh",
                "hoian"
        );

        db.insert(
                "dia_diem",
                null,
                hoiAn
        );
    }


    // =====================================================
    // KIỂM TRA EMAIL ĐÃ TỒN TẠI
    // =====================================================

    public boolean emailDaTonTai(String email) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ma_nguoi_dung " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(email) = LOWER(?)",
                new String[]{
                        email
                }
        );

        boolean tonTai =
                cursor.moveToFirst();

        cursor.close();

        return tonTai;
    }


    // =====================================================
    // ĐĂNG KÝ
    // =====================================================

    public boolean dangKy(
            String hoTen,
            String email,
            String matKhau
    ) {

        if (emailDaTonTai(email)) {
            return false;
        }

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "ho_ten",
                hoTen
        );

        values.put(
                "email",
                email
        );

        values.put(
                "mat_khau",
                matKhau
        );

        // Người tự đăng ký chỉ được USER
        values.put(
                "vai_tro",
                "USER"
        );

        values.put(
                "trang_thai",
                1
        );

        long ketQua = db.insert(
                "nguoi_dung",
                null,
                values
        );

        return ketQua != -1;
    }


    // =====================================================
    // ĐĂNG NHẬP
    // Trả về ADMIN / USER / null
    // =====================================================

    public String dangNhap(
            String email,
            String matKhau
    ) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT vai_tro " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(email) = LOWER(?) " +
                        "AND mat_khau = ? " +
                        "AND trang_thai = 1",
                new String[]{
                        email,
                        matKhau
                }
        );

        if (cursor.moveToFirst()) {

            String vaiTro =
                    cursor.getString(0);

            cursor.close();

            return vaiTro;
        }

        cursor.close();

        return null;
    }


    // =====================================================
    // LẤY ID NGƯỜI DÙNG
    // =====================================================

    public int layMaNguoiDung(String email) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ma_nguoi_dung " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(email) = LOWER(?)",
                new String[]{
                        email
                }
        );

        if (cursor.moveToFirst()) {

            int id =
                    cursor.getInt(0);

            cursor.close();

            return id;
        }

        cursor.close();

        return -1;
    }


    // =====================================================
    // LẤY HỌ TÊN
    // =====================================================

    public String layHoTen(String email) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT ho_ten " +
                        "FROM nguoi_dung " +
                        "WHERE LOWER(email) = LOWER(?)",
                new String[]{
                        email
                }
        );

        if (cursor.moveToFirst()) {

            String hoTen =
                    cursor.getString(0);

            cursor.close();

            return hoTen;
        }

        cursor.close();

        return "";
    }


    // =====================================================
    // 1. LẤY TẤT CẢ ĐỊA ĐIỂM
    // =====================================================

    public ArrayList<DiaDiem> layTatCaDiaDiem() {

        ArrayList<DiaDiem> danhSach =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM dia_diem " +
                        "ORDER BY ma_dia_diem DESC",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                DiaDiem diaDiem =
                        new DiaDiem();

                diaDiem.setMaDiaDiem(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        "ma_dia_diem"
                                )
                        )
                );

                diaDiem.setTenDiaDiem(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "ten_dia_diem"
                                )
                        )
                );

                diaDiem.setDiaChi(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "dia_chi"
                                )
                        )
                );

                diaDiem.setKhuVuc(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "khu_vuc"
                                )
                        )
                );

                diaDiem.setDanhMuc(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "danh_muc"
                                )
                        )
                );

                diaDiem.setDanhGia(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        "danh_gia"
                                )
                        )
                );

                diaDiem.setMoTa(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "mo_ta"
                                )
                        )
                );

                diaDiem.setThoiDiemThichHop(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "thoi_diem_thich_hop"
                                )
                        )
                );

                diaDiem.setHinhAnh(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "hinh_anh"
                                )
                        )
                );

                danhSach.add(diaDiem);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return danhSach;
    }


    // =====================================================
    // 2. THÊM ĐỊA ĐIỂM
    // =====================================================

    public boolean themDiaDiem(
            DiaDiem diaDiem
    ) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "ten_dia_diem",
                diaDiem.getTenDiaDiem()
        );

        values.put(
                "dia_chi",
                diaDiem.getDiaChi()
        );

        values.put(
                "khu_vuc",
                diaDiem.getKhuVuc()
        );

        values.put(
                "danh_muc",
                diaDiem.getDanhMuc()
        );

        values.put(
                "danh_gia",
                diaDiem.getDanhGia()
        );

        values.put(
                "mo_ta",
                diaDiem.getMoTa()
        );

        values.put(
                "thoi_diem_thich_hop",
                diaDiem.getThoiDiemThichHop()
        );

        values.put(
                "hinh_anh",
                diaDiem.getHinhAnh()
        );

        long ketQua = db.insert(
                "dia_diem",
                null,
                values
        );

        return ketQua != -1;
    }


    // =====================================================
    // 3. SỬA ĐỊA ĐIỂM
    // =====================================================

    public boolean suaDiaDiem(
            DiaDiem diaDiem
    ) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                "ten_dia_diem",
                diaDiem.getTenDiaDiem()
        );

        values.put(
                "dia_chi",
                diaDiem.getDiaChi()
        );

        values.put(
                "khu_vuc",
                diaDiem.getKhuVuc()
        );

        values.put(
                "danh_muc",
                diaDiem.getDanhMuc()
        );

        values.put(
                "danh_gia",
                diaDiem.getDanhGia()
        );

        values.put(
                "mo_ta",
                diaDiem.getMoTa()
        );

        values.put(
                "thoi_diem_thich_hop",
                diaDiem.getThoiDiemThichHop()
        );

        values.put(
                "hinh_anh",
                diaDiem.getHinhAnh()
        );

        int ketQua = db.update(
                "dia_diem",
                values,
                "ma_dia_diem = ?",
                new String[]{
                        String.valueOf(
                                diaDiem.getMaDiaDiem()
                        )
                }
        );

        return ketQua > 0;
    }


    // =====================================================
    // 4. XÓA ĐỊA ĐIỂM
    // =====================================================

    public boolean xoaDiaDiem(
            int maDiaDiem
    ) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        int ketQua = db.delete(
                "dia_diem",
                "ma_dia_diem = ?",
                new String[]{
                        String.valueOf(maDiaDiem)
                }
        );

        return ketQua > 0;
    }


    // =====================================================
    // 5. LẤY ĐỊA ĐIỂM THEO MÃ
    // =====================================================

    public DiaDiem layDiaDiemTheoMa(
            int maDiaDiem
    ) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM dia_diem " +
                        "WHERE ma_dia_diem = ?",
                new String[]{
                        String.valueOf(maDiaDiem)
                }
        );

        DiaDiem diaDiem = null;

        if (cursor.moveToFirst()) {

            diaDiem = new DiaDiem();

            diaDiem.setMaDiaDiem(
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    "ma_dia_diem"
                            )
                    )
            );

            diaDiem.setTenDiaDiem(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "ten_dia_diem"
                            )
                    )
            );

            diaDiem.setDiaChi(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "dia_chi"
                            )
                    )
            );

            diaDiem.setKhuVuc(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "khu_vuc"
                            )
                    )
            );

            diaDiem.setDanhMuc(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "danh_muc"
                            )
                    )
            );

            diaDiem.setDanhGia(
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    "danh_gia"
                            )
                    )
            );

            diaDiem.setMoTa(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "mo_ta"
                            )
                    )
            );

            diaDiem.setThoiDiemThichHop(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "thoi_diem_thich_hop"
                            )
                    )
            );

            diaDiem.setHinhAnh(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "hinh_anh"
                            )
                    )
            );
        }

        cursor.close();

        return diaDiem;
    }


    // =====================================================
    // NÂNG CẤP DATABASE
    // =====================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL(
                "DROP TABLE IF EXISTS chi_tiet_hanh_trinh"
        );

        db.execSQL(
                "DROP TABLE IF EXISTS yeu_thich"
        );

        db.execSQL(
                "DROP TABLE IF EXISTS hanh_trinh"
        );

        db.execSQL(
                "DROP TABLE IF EXISTS meo_du_lich"
        );

        db.execSQL(
                "DROP TABLE IF EXISTS dia_diem"
        );

        db.execSQL(
                "DROP TABLE IF EXISTS nguoi_dung"
        );

        onCreate(db);
    }
    // =====================================================
// KIỂM TRA ĐÃ YÊU THÍCH CHƯA
// =====================================================

    public boolean daYeuThich(int maNguoiDung, int maDiaDiem) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM yeu_thich " +
                        "WHERE ma_nguoi_dung = ? " +
                        "AND ma_dia_diem = ?",
                new String[]{
                        String.valueOf(maNguoiDung),
                        String.valueOf(maDiaDiem)
                }
        );

        boolean ketQua = cursor.moveToFirst();

        cursor.close();

        return ketQua;
    }


// =====================================================
// THÊM YÊU THÍCH
// =====================================================

    public boolean themYeuThich(int maNguoiDung, int maDiaDiem) {

        if (daYeuThich(maNguoiDung, maDiaDiem)) {
            return true;
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("ma_nguoi_dung", maNguoiDung);
        values.put("ma_dia_diem", maDiaDiem);

        long ketQua = db.insert(
                "yeu_thich",
                null,
                values
        );

        return ketQua != -1;
    }


// =====================================================
// XÓA YÊU THÍCH
// =====================================================

    public boolean xoaYeuThich(int maNguoiDung, int maDiaDiem) {

        SQLiteDatabase db = getWritableDatabase();

        int ketQua = db.delete(
                "yeu_thich",
                "ma_nguoi_dung = ? AND ma_dia_diem = ?",
                new String[]{
                        String.valueOf(maNguoiDung),
                        String.valueOf(maDiaDiem)
                }
        );

        return ketQua > 0;
    }
    // =====================================================
// LẤY DANH SÁCH ĐỊA ĐIỂM YÊU THÍCH CỦA USER
// =====================================================

    public ArrayList<DiaDiem> layDanhSachYeuThich(int maNguoiDung) {

        ArrayList<DiaDiem> danhSach = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql =
                "SELECT d.* " +
                        "FROM dia_diem d " +
                        "INNER JOIN yeu_thich y " +
                        "ON d.ma_dia_diem = y.ma_dia_diem " +
                        "WHERE y.ma_nguoi_dung = ? " +
                        "ORDER BY y.ma_yeu_thich DESC";

        Cursor cursor = db.rawQuery(
                sql,
                new String[]{
                        String.valueOf(maNguoiDung)
                }
        );

        if (cursor.moveToFirst()) {

            do {

                DiaDiem diaDiem = new DiaDiem();

                diaDiem.setMaDiaDiem(
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow("ma_dia_diem")
                        )
                );

                diaDiem.setTenDiaDiem(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("ten_dia_diem")
                        )
                );

                diaDiem.setDiaChi(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("dia_chi")
                        )
                );

                diaDiem.setKhuVuc(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("khu_vuc")
                        )
                );

                diaDiem.setDanhMuc(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("danh_muc")
                        )
                );

                diaDiem.setDanhGia(
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow("danh_gia")
                        )
                );

                diaDiem.setMoTa(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("mo_ta")
                        )
                );

                diaDiem.setThoiDiemThichHop(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        "thoi_diem_thich_hop"
                                )
                        )
                );

                diaDiem.setHinhAnh(
                        cursor.getString(
                                cursor.getColumnIndexOrThrow("hinh_anh")
                        )
                );

                danhSach.add(diaDiem);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return danhSach;
    }
}