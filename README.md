# DA_APP - Ung dung Android ho tro tu van va tra cuu du lich

## 1. Tong quan de tai

De tai cua nhom: **"Xay dung ung dung Android ho tro tu van va tra cuu du lich"**.

Project hien tai la ung dung Android viet bang **Java + XML**, su dung **SQLite local** thong qua `SQLiteOpenHelper`. Ung dung dang tap trung vao cac chuc nang cot loi cua mot app tra cuu du lich:

- Xem man hinh gioi thieu va vao trang chu.
- Dang ky, dang nhap, phan quyen USER/ADMIN.
- Tra cuu danh sach dia diem du lich.
- Tim kiem dia diem theo ten/dia chi.
- Loc dia diem theo khu vuc.
- Xem chi tiet dia diem.
- Luu/bo luu dia diem yeu thich.
- Admin quan ly danh sach dia diem: them, sua, xoa, chon anh.

## 2. Cong nghe va cau truc project

### Cong nghe

- Ngon ngu: Java.
- Giao dien: Android XML layout.
- Database: SQLite local.
- Thu vien chinh: AppCompat, Material, Activity, ConstraintLayout.
- Build tool: Gradle Android Plugin.

### Cau truc chinh

- `app/src/main/java/com/example/da_app/`: cac Activity cua ung dung.
- `app/src/main/java/com/example/da_app/database/DatabaseHelper.java`: tao database, seed du lieu mau, xu ly dang nhap/dang ky, dia diem, yeu thich.
- `app/src/main/java/com/example/da_app/model/DiaDiem.java`: model dia diem du lich.
- `app/src/main/java/com/example/da_app/adapter/DiaDiemAdminAdapter.java`: adapter hien thi dia diem trong man hinh admin.
- `app/src/main/res/layout/`: giao dien XML.
- `app/src/main/res/drawable/`: anh va background drawable.

## 3. Tai khoan mau

Database dang seed san 2 tai khoan:

- Admin: `admin@gmail.com` / `123456`
- User: `user@gmail.com` / `123456`   " cứ đăng ký thôi nhe có admin là mặc định  "

Luu y: mat khau hien dang luu plain text trong SQLite, chi phu hop demo/hoc tap, chua phu hop san pham that.

## 4. Cac phan da trien khai

### 4.1. Luong mo app va trang chu

- `WelcomeActivity` la man hinh khoi dong.
- Nguoi dung co the vao `MainActivity` ma khong bat buoc dang nhap.
- Trang chu co cac muc: dia diem, hanh trinh, yeu thich, meo du lich, bottom navigation.
- Phu hop voi huong app du lich vi co man hinh gioi thieu, dieu huong den kham pha va yeu thich.

### 4.2. Dang ky, dang nhap va phan quyen

- `RegisterActivity` cho phep tao tai khoan USER.
- `LoginActivity` kiem tra tai khoan trong SQLite.
- Luu phien dang nhap bang `SharedPreferences`.
- Co phan quyen ADMIN/USER:
  - ADMIN vao `AdminActivity`.
  - USER vao `MainActivity`.
- Cac man hinh yeu thich co kiem tra dang nhap.

### 4.3. Tra cuu va tim kiem dia diem

- `ExploreActivity` lay danh sach dia diem tu SQLite.
- Co tim kiem theo ten dia diem va dia chi.
- Co loc theo khu vuc: Tat ca, Mien Bac, Mien Trung, Mien Nam.
- Co chuan hoa chuoi de tim kiem khong dau.
- Moi item hien thi anh, ten, dia chi, danh gia, danh muc va nut yeu thich.

Day la phan bam rat sat de tai "tra cuu du lich".

### 4.4. Chi tiet dia diem

- `PlaceDetailActivity` hien thi thong tin dia diem:
  - Ten dia diem.
  - Dia chi.
  - Danh gia.
  - Danh muc.
  - Mo ta.
  - Thoi diem thich hop.
  - Anh dia diem.
- Co nut yeu thich.
- Co nut "Goi y hanh trinh", nhung hien moi la Toast, chua co logic tu van/tao hanh trinh.

### 4.5. Yeu thich dia diem

- Bang `yeu_thich` da duoc tao trong SQLite.
- `ExploreActivity` va `PlaceDetailActivity` co them/bo yeu thich.
- `FavoriteActivity` hien thi danh sach dia diem yeu thich cua user dang dang nhap.
- Trang yeu thich cho phep xoa khoi danh sach va bam vao item de xem chi tiet.

### 4.6. Quan tri dia diem

- `AdminActivity` co man hinh quan tri.
- `AdminPlaceActivity` hien danh sach dia diem.
- `AddEditPlaceActivity` cho phep them/sua dia diem.
- `DiaDiemAdminAdapter` cho phep sua/xoa tung dia diem.
- Admin co the chon anh tu thu vien thiet bi bang `ACTION_OPEN_DOCUMENT`.

Phan nay ho tro tot cho viec cap nhat du lieu du lich noi bo trong app.

### 4.7. Database

`DatabaseHelper` da tao cac bang:

- `nguoi_dung`
- `dia_diem`
- `yeu_thich`
- `hanh_trinh`
- `chi_tiet_hanh_trinh`
- `meo_du_lich`

Trong do cac bang dang duoc dung that su: `nguoi_dung`, `dia_diem`, `yeu_thich`.

## 5. Cac phan con ton dong

### 5.1. Chua co chuc nang tu van du lich dung nghia

De tai co cum "ho tro tu van", nhung hien app chu yeu la tra cuu va quan ly dia diem. Phan tu van moi the hien o muc giao dien/nut bam, chua co logic:

- Chua goi y dia diem theo so thich, khu vuc, mua du lich, ngan sach, thoi gian.
- Chua co bo cau hoi tu van.
- Chua co thuat toan de xep hang/goi y dia diem phu hop.
- Nut "Goi y hanh trinh" trong chi tiet dia diem moi hien Toast.

Nen bo sung it nhat mot module tu van don gian, vi du: nguoi dung chon khu vuc, loai hinh, thoi gian, muc danh gia mong muon, app tra ve danh sach goi y.

### 5.2. Hanh trinh chua trien khai

Database da co `hanh_trinh` va `chi_tiet_hanh_trinh`, giao dien cung co muc "Hanh trinh", nhung:

- Chua co `JourneyActivity`.
- Chua co man hinh tao/sua/xoa hanh trinh.
- Chua co chuc nang them dia diem vao hanh trinh.
- Chua co hien thi lich trinh theo ngay.

Phan nay lien quan truc tiep den "tu van du lich", nen nen uu tien lam tiep.

### 5.3. Meo du lich chua trien khai

Database co bang `meo_du_lich`, trang chu va admin co muc "Meo du lich", nhung:

- Chua co `TipsActivity`.
- Chua co CRUD meo du lich cho admin.
- Chua co danh sach/noi dung meo cho user.

Day la phan phu hop de tai, khong nen xoa, nhung hien dang la chuc nang dang do.

### 5.4. Quan ly nguoi dung admin chua trien khai

Admin co muc "Quan ly nguoi dung", nhung hien chi Toast.

Nen bo sung neu muon hoan thien phan quan tri:

- Danh sach user.
- Khoa/mo tai khoan.
- Xem thong tin user.
- Khong cho admin xoa chinh minh.

### 5.5. Ho so ca nhan chua hoan thien

- `ProfileActivity.java` hien la class rong, khong ke thua Activity va khong co layout.
- Bottom nav "Ca nhan" trong `MainActivity` dang hien AlertDialog thong tin tai khoan, chua mo `ProfileActivity`.

Neu khong lam profile rieng, co the xoa `ProfileActivity`. Neu can tinh nang ca nhan, nen tao man hinh hoan chinh.

### 5.6. Du lieu mau va anh chua dong bo

Seed database co `dalat` va `hoian`, nhung trong `drawable` hien chi thay anh `halong_bay.png` va `icon_location.png`. Vi vay Da Lat/Hoi An se fallback ve anh Ha Long.

Nen them anh tuong ung hoac sua du lieu mau de tranh tat ca dia diem hien cung mot anh.

### 5.7. Van de encoding tieng Viet trong Java

Nhieu chuoi trong file `.java` dang bi loi ma hoa, vi du hien dang thanh dang `Ä`, `Ãª`, `á»...`. Layout XML lai hien tieng Viet dung hon.

Anh huong:

- Toast, dialog, error message co the hien loi tieng Viet trong app.
- Comment kho doc.
- Tim kiem/so sanh chuoi co dau co the sai neu du lieu bi seed loi encoding.

Nen chuan hoa toan bo file Java ve UTF-8 va sua lai cac chuoi hien thi.

### 5.8. Bao mat va chat luong code

- Mat khau dang luu plain text.
- Chua co man hinh doi mat khau/quen mat khau.
- Chua co validate email khi dang nhap.
- SQLite local phu hop demo, nhung neu app can dung nhieu thiet bi thi can backend/API.
- Logic hien thi item user/favorite/admin lap lai nhieu, co the tach helper hien thi anh.
- Test hien chi la test mau mac dinh, chua co test nghiep vu that.

## 6. Phan co dau hieu thua hoac lac de

Khong thay chuc nang nao hoan toan lac de nhu ban hang, game, mang xa hoi... Hau het cac phan deu lien quan den du lich. Tuy nhien co cac phan **thua tam thoi/chua dung den**:

- `ProfileActivity.java`: hien rong, chua duoc khai bao trong manifest, chua co tac dung.
- Bang `hanh_trinh`, `chi_tiet_hanh_trinh`: dung voi de tai, nhung hien chua co UI/logic nen dang la thiet ke truoc.
- Bang `meo_du_lich`: dung voi de tai, nhung chua co UI/logic nen dang la thiet ke truoc.
- Menu "Quan ly nguoi dung", "Quan ly meo du lich", "Hanh trinh", "Meo du lich": hien moi la placeholder/Toast/comment, can hoan thien hoac an khoi UI neu nop ban demo ngan gon.
- Thu muc `build/`, `.gradle/`, `coverage/`: la file sinh tu dong, khong nen dua vao phan mo ta chuc nang va khong nen commit len repository.

Ket luan: khong nen xoa cac phan hanh trinh/meo du lich neu nhom du dinh phat trien tiep. Neu thoi gian nop gap, nen an bot cac menu chua lam de tranh bi danh gia la app co nut nhung bam khong co chuc nang.

## 7. De xuat uu tien hoan thien

1. Sua loi encoding tieng Viet trong toan bo file Java.
2. Hoan thien module tu van/goi y dia diem theo tieu chi nguoi dung.
3. Hoan thien module hanh trinh: tao hanh trinh, them dia diem, sap xep theo ngay.
4. Hoan thien module meo du lich hoac an menu neu khong kip.
5. Them anh cho cac dia diem mau va tang so luong du lieu mau.
6. Hoan thien profile/user management neu can diem phan quyen/admin.
7. Bo sung test cho database va cac ham loc/tim kiem.
8. Doi ten app trong `strings.xml` tu `DA_APP` thanh ten than thien hon, vi du `Du lich Viet Nam`.

