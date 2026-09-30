# DA_APP - Ung dung Android ho tro tu van va tra cuu du lich

## 1. Tong quan de tai

De tai: **"Xay dung ung dung Android ho tro tu van va tra cuu du lich"**.

Project hien tai la ung dung Android viet bang **Java + XML**, luu du lieu bang **SQLite local**. App dang tap trung vao cac nhom chuc nang chinh:

- Gioi thieu ung dung va vao trang chu.
- Dang ky, dang nhap, phan quyen USER/ADMIN.
- Tra cuu, tim kiem, loc va xem chi tiet dia diem du lich.
- Luu dia diem yeu thich.
- Danh gia dia diem bang sao.
- Quan ly ho so ca nhan.
- Admin quan ly dia diem: them, sua, xoa, chon anh.

Tinh chat hien tai: app da phu hop tot voi phan **tra cuu du lich**; phan **tu van/goi y hanh trinh** da co nut va y tuong giao dien nhung chua co logic hoan chinh.

## 2. Cong nghe su dung

- Ngon ngu: Java.
- Giao dien: Android XML layout.
- Database: SQLite local qua `SQLiteOpenHelper`.
- Luu phien dang nhap: `SharedPreferences`.
- Build: Gradle Android Plugin.
- Thu vien Android: AppCompat, Material, Activity, ConstraintLayout.
- Min SDK: 24.
- Target SDK: 36.

## 3. Cau truc project

- `app/src/main/java/com/example/da_app/`: cac man hinh Activity.
- `app/src/main/java/com/example/da_app/database/DatabaseHelper.java`: tao database, seed du lieu mau, xu ly user, dia diem, yeu thich, danh gia.
- `app/src/main/java/com/example/da_app/model/DiaDiem.java`: model dia diem du lich.
- `app/src/main/java/com/example/da_app/model/DanhGiaCuaToi.java`: model danh gia cua user.
- `app/src/main/java/com/example/da_app/adapter/DiaDiemAdminAdapter.java`: adapter danh sach dia diem cho admin.
- `app/src/main/res/layout/`: cac file giao dien XML.
- `app/src/main/res/drawable/`: anh va background drawable.

## 4. Tai khoan mau

Database seed san:

- Admin: `admin@gmail.com` / `123456`
- User: `user@gmail.com` / `123456` " cứ đăng ký tài khoản bình thường nhe "

Nguoi dung moi cung co the tu dang ky tai khoan USER trong app.

Luu y: mat khau dang luu plain text trong SQLite, phu hop demo/hoc tap nhung chua phu hop san pham that.

## 5. Cac man hinh/chuc nang da trien khai

### 5.1. Welcome va trang chu

- `WelcomeActivity`: man hinh khoi dong/gioi thieu.
- `MainActivity`: trang chu user.
- Cho phep khach vao xem app ma khong bat buoc dang nhap.
- Trang chu co menu: dia diem, hanh trinh, yeu thich, meo du lich.
- Bottom navigation co: Home, Kham pha, Yeu thich, Goi y, Ca nhan.

### 5.2. Dang ky, dang nhap, phan quyen

- `RegisterActivity`: dang ky tai khoan USER, co validate ho ten, email, mat khau va email trung.
- `LoginActivity`: dang nhap bang SQLite.
- Luu thong tin dang nhap vao `SharedPreferences`.
- Phan quyen:
  - ADMIN vao `AdminActivity`.
  - USER vao `MainActivity`.
- Cac man hinh can tai khoan nhu yeu thich, profile, danh gia co kiem tra dang nhap.

### 5.3. Tra cuu va kham pha dia diem

- `ExploreActivity`: hien thi danh sach dia diem tu SQLite.
- Tim kiem theo ten dia diem va dia chi.
- Loc theo khu vuc: Tat ca, Mien Bac, Mien Trung, Mien Nam.
- Co chuan hoa chuoi de tim kiem khong dau.
- Moi dia diem hien thi anh, ten, dia chi, danh gia, danh muc va nut yeu thich.

Day la phan bam sat nhat voi yeu cau "tra cuu du lich".

### 5.4. Chi tiet dia diem

- `PlaceDetailActivity`: hien thi thong tin chi tiet cua dia diem:
  - Ten dia diem.
  - Dia chi.
  - Danh gia trung binh.
  - So luot danh gia.
  - Danh muc.
  - Mo ta.
  - Thoi diem thich hop.
  - Anh dia diem.
- Cho phep them/bo yeu thich.
- Cho phep user danh gia 1-5 sao.
- Neu user da danh gia, man hinh hien lai so sao da chon.
- Nut "Goi y hanh trinh" hien moi dung Toast, chua tao hanh trinh that.

### 5.5. Yeu thich

- `FavoriteActivity`: hien danh sach dia diem user da luu.
- Co the bo yeu thich truc tiep.
- Bam vao item de xem chi tiet dia diem.
- Bang SQLite lien quan: `yeu_thich`.

### 5.6. Danh gia dia diem

- Bang SQLite `danh_gia` da duoc them.
- Moi user chi co mot danh gia cho moi dia diem, neu danh gia lai thi cap nhat so sao.
- Sau khi user danh gia, app tinh lai diem trung binh cua dia diem va cap nhat cot `dia_diem.danh_gia`.
- `MyRatingActivity`: hien danh sach cac dia diem user da danh gia.
- `DanhGiaCuaToi`: model phuc vu man hinh danh gia cua toi.

Day la phan moi quan trong, giup app co du lieu phan hoi nguoi dung va lam nen tang cho goi y/tu van sau nay.

### 5.7. Ho so ca nhan

- `ProfileActivity`: hien thong tin tai khoan dang dang nhap.
- Co cac muc:
  - Thong tin ca nhan.
  - Dia diem yeu thich.
  - Danh gia cua toi.
  - Chinh sua ho so.
  - Dang xuat.
- `EditProfileActivity`: cho phep sua ho ten va email.
- Khi sua thanh cong, app cap nhat ca SQLite va `SharedPreferences`.

Phan profile truoc day con rong, hien da duoc trien khai thanh man hinh that.

### 5.8. Admin va quan ly dia diem

- `AdminActivity`: man hinh quan tri.
- Co bao ve quyen admin bang `SharedPreferences`.
- `AdminPlaceActivity`: danh sach dia diem cho admin.
- `AddEditPlaceActivity`: them/sua dia diem, validate ten, dia chi, khu vuc, danh gia.
- `DiaDiemAdminAdapter`: sua/xoa dia diem trong danh sach.
- Admin co the chon anh tu thiet bi bang `ACTION_OPEN_DOCUMENT`.

Phan admin quan ly dia diem da trien khai kha day du.

## 6. Database hien tai

`DatabaseHelper` dang dung `TravelApp.db`, version `3`.

Bang da tao:

- `nguoi_dung`: luu tai khoan, email, mat khau, vai tro, trang thai.
- `dia_diem`: luu thong tin dia diem du lich.
- `yeu_thich`: luu dia diem yeu thich theo user.
- `hanh_trinh`: thiet ke truoc cho hanh trinh.
- `chi_tiet_hanh_trinh`: thiet ke truoc cho chi tiet hanh trinh.
- `meo_du_lich`: thiet ke truoc cho meo du lich.
- `danh_gia`: luu danh gia sao cua user cho dia diem.

Bang dang duoc dung that su trong UI/logic:

- `nguoi_dung`
- `dia_diem`
- `yeu_thich`
- `danh_gia`

Bang moi thiet ke/chua co man hinh hoan chinh:

- `hanh_trinh`
- `chi_tiet_hanh_trinh`
- `meo_du_lich`

## 7. Cac phan con ton dong

### 7.1. Tu van/goi y du lich chua hoan chinh

De tai co phan "ho tro tu van", nhung hien tai app chu yeu la tra cuu, yeu thich, danh gia va quan ly dia diem.

Con thieu:

- Bo cau hoi/bo loc tu van theo nhu cau nguoi dung.
- Goi y dia diem theo khu vuc, danh muc, diem danh gia, thoi diem phu hop.
- Goi y hanh trinh theo so ngay va so thich.
- Logic cho tab/nut "Goi y" hien tai chua thanh module tu van day du.

Nen uu tien lam tiep phan nay de de tai ro tinh "tu van".

### 7.2. Hanh trinh chua trien khai thanh man hinh

Database da co `hanh_trinh` va `chi_tiet_hanh_trinh`, UI cung co menu "Hanh trinh", nhung chua co:

- `JourneyActivity`.
- Tao/sua/xoa hanh trinh.
- Them dia diem vao hanh trinh.
- Sap xep dia diem theo ngay/thu tu tham quan.

### 7.3. Meo du lich chua trien khai

Database co `meo_du_lich`, trang chu va admin co muc meo du lich, nhung chua co:

- `TipsActivity`.
- Danh sach meo du lich cho user.
- CRUD meo du lich cho admin.

### 7.4. Quan ly nguoi dung cho admin chua trien khai

Admin co menu quan ly nguoi dung nhung hien moi o muc Toast/chua co man hinh.

Nen bo sung:

- Danh sach user.
- Khoa/mo tai khoan.
- Xem thong tin user.
- Khong cho admin xoa/khoa chinh minh.

### 7.5. Anh du lieu mau chua dong bo

Database seed cac dia diem `halong_bay`, `dalat`, `hoian`, nhung thu muc `drawable` hien chi co anh that cho `halong_bay`. Da Lat/Hoi An co the bi fallback ve anh Ha Long.

Nen them anh `dalat`, `hoian` hoac cap nhat lai seed data.

### 7.6. Loi encoding tieng Viet trong Java

Nhieu file Java dang bi loi ma hoa tieng Viet trong comment/string, vi du cac chuoi hien thanh dang `A`, `A~`, `a»...`.

Anh huong:

- Toast/dialog/error co the hien sai tieng Viet.
- Comment kho doc.
- Du lieu seed co dau co the bi luu sai, anh huong tim kiem/so sanh chuoi.

Nen chuan hoa cac file Java ve UTF-8 va sua lai toan bo chuoi hien thi.

### 7.7. Bao mat va chat luong code

- Mat khau luu plain text.
- Chua co doi mat khau/quen mat khau.
- Chua co backend/API, du lieu chi nam local.
- Chua co test nghiep vu cho database, tim kiem, yeu thich, danh gia.
- Co nhieu logic lap lai ve load anh, kiem tra dang nhap, hien card dia diem.

## 8. Phan thua/lac de

Khong thay chuc nang nao hoan toan lac de voi ung dung du lich. Tuy nhien co cac phan **dang thua tam thoi vi chua hoan thien**:

- Bang `hanh_trinh` va `chi_tiet_hanh_trinh`: dung voi de tai, nhung chua co UI/logic.
- Bang `meo_du_lich`: dung voi de tai, nhung chua co UI/logic.
- Menu "Hanh trinh", "Meo du lich", "Quan ly nguoi dung": nen hoan thien hoac an neu demo/nop bai.
- Thu muc `build/`, `.gradle/`, `coverage/`: file sinh tu dong, khong phai noi dung chuc nang va khong nen dua vao mo ta san pham.

`ProfileActivity` khong con la file thua nua vi hien da duoc trien khai va khai bao trong manifest.

## 9. De xuat uu tien hoan thien

1. Sua loi encoding tieng Viet trong cac file Java.
2. Hoan thien module tu van/goi y dia diem theo tieu chi nguoi dung.
3. Hoan thien module hanh trinh.
4. Them module meo du lich hoac an menu neu chua kip.
5. Them anh cho Da Lat, Hoi An va mo rong du lieu mau.
6. Hoan thien quan ly nguoi dung cho admin.
7. Bo sung doi mat khau va cai thien bao mat mat khau.
8. Viet test cho database, tim kiem, yeu thich, danh gia.
9. Doi ten app trong `strings.xml` tu `DA_APP` sang ten than thien hon, vi du `Du lich Viet Nam`.

