go
create database edusys
go
use edusys
go

create TABLE NhanVien(
    MaNV nvarchar(20) not null,
    MatKhau NVARCHAR(50) not null,
    HoTen NVARCHAR(50) not null,
    VaiTro BIT DEFAULT 0,
    PRIMARY key (MaNV)
)

create TABLE NguoiHoc (
    MaNH NCHAR(7) not null,
    HoTen NVARCHAR(50) NOT NULL,
    GioiTinh BIT DEFAULT 1,
    NgaySinh DATE not null,
    DienThoai NVARCHAR(24) not null,
    Email NVARCHAR(50) not NULL,
    GhiChu NVARCHAR(255) null,
    MaNV NVARCHAR(20) not null,
    NgayDK Date DEFAULT GETDATE(),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV) on UPDATE CASCADE on Delete no action,
    PRIMARY key (MaNH)
)

CREATE TABLE ChuyenDe (
    MaCD NCHAR(5) not null,
    TenCD NVARCHAR(50) not null,
    HocPhi float not null,
    ThoiLuong int not null,
    Hinh NVARCHAR(255) not null,
    MoTa NVARCHAR(255) not null
    PRIMARY key (MaCD)
)

create table KhoaHoc(
    MaKH int IDENTITY(1,1) not null,
    MaCD nchar(5) not null,
    HocPhi float not null DEFAULT 0,
    ThoiLuong int not null DEFAULT 0,
    NgayKG date not null,
    GhiChu nvarchar(50) not null,
    MaNV NVARCHAR(20) not null,
    NgayTao date not null DEFAULT GETDATE(),
    PRIMARY key (MaKH) ,
    CHECK(HocPhi >= 0 and ThoiLuong >0),
    FOREIGN KEY (MaCD) REFERENCES ChuyenDe(MaCD) on UPDATE CASCADE on Delete no action,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV) on UPDATE CASCADE on Delete no action
)

CREATE table HocVien(
    MaHV int IDENTITY(1,1),
    MaKH int not null,
    MaNH NCHAR(7) not null,
    Diem FLOAT DEFAULT -1,
    FOREIGN key (MaKH) REFERENCES KhoaHoc(MaKH) on DELETE cascade on UPDATE CASCADE,
    FOREIGN key (MaNH) REFERENCES NguoiHoc(MaNH),
    PRIMARY key (MaHV)
)

go

-- result: MaNH, HoTen, Diem
create proc sp_BangDiem(@MaKH int)
as begin 
    select nh.MaNH, nh.HoTen, hv.Diem
    from HocVien hv 
    join NguoiHoc nh 
    on hv.MaNH=hv.MaNH
where hv.MaKH = @MaKH
order by hv.Diem desc
end

go
-- result: ChuyenDe, soHV, thap nhat, cao nhat, trung binh
create proc sp_ThongKeDiem
as begin
    select TenCD ChuyenDe,
        count(MaHV) SoHV,
        min(Diem) ThapNhat,
        max(diem) CaoNhat,
        avg(diem) TrungBinh
    from KhoaHoc kh
        Join HocVien hv on kh.MaKH=hv.MaKH
        Join ChuyenDe cd on cd.MaCD=kh.MaCD
        group by TenCD
end
go

-- result: ChuyenDe, SoKH, SoHV, Doanh Thu, Thap Nhat, Cao Nhat, Trung Binh
create proc sp_ThongKeDoanhThu(@Year int)
as begin 
    select TenCD ChuyenDe,
        COUNT(DISTINCT kh.MaKH) SoKH,
        Count (hv.MaHV) SoHV,
        SUM(kh.HocPhi) DoanhThu,
        MIN (kh.HocPhi) ThapNhat,
        MAX (kh.HocPhi) CaoNhat,
        avg(kh.HocPhi) TrungBinh
    from KhoaHoc kh
        join HocVien hv on hv.MaKH=kh.MaKH
        join ChuyenDe cd on cd.MaCD=kh.MaCD
    where YEAR(NgayKG) = @Year
    GROUP by TenCD
end
go

-- result: Nam, so luong, dau tien, cuoi cung
create proc sp_ThongKeNguoiHoc
as BEGIN
    select YEAR(NgayDK) Nam,
    COUNT(*) SoLuong,
    Min(NgayDK) DauTien,
    MAX(NgayDk) CuoiCung
    from NguoiHoc
    Group by YEAR(NgayDK)
end 
go

INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro)
VALUES ('trungpv', '2004', N'Phạm Văn Trung', 0),
       ('nv01', '1234', N'Nguyễn Liên Hiệp', 1),
       ('nv02', '1234', N'Lê Xuân Diệu', 0),
       ('nv03', '1234', N'Trần Đình Anh', 0);
       
INSERT INTO NguoiHoc (MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV, NgayDK)
VALUES ('nh01', 'Nguyen Van X', 1, '2000-01-01', '0912345678', 'x@email.com', NULL, 'nv01', '2021-01-01'),
       ('nh02', 'Tran Thi Y', 0, '2001-03-03', '0923456789', 'y@email.com', NULL, 'nv02', '2021-03-05'),
       ('nh03', 'Le Van Z', 1, '2002-06-08', '0934567890', 'z@email.com', NULL, 'nv03', '2021-07-20');
       
INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa)  
VALUES ('cd01', N'Java cơ bản', 300, 90, 'java.png', N'Lập trình Java cơ bản'),
       ('cd02', N'Java nâng cao', 500, 120, 'java_adv.png', N'Lập trình Java nâng cao'),
       ('cd03', N'Python', 250, 60, 'python.png', N'Lập trình Python');
       
INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao)
VALUES ('cd01', 300, 90, '2021-01-01', N'Khóa học Java cơ bản', 'nv01', '2020-12-01'),  
       ('cd02', 500, 120, '2021-03-15', N'Khóa học Java nâng cao', 'nv02', '2021-02-20'),
       ('cd03', 250, 60, '2021-05-05', N'Khóa học Python', 'nv03', '2021-04-12');
       
INSERT INTO HocVien (MaKH, MaNH, Diem)
VALUES (1, 'nh01', 8),
       (2, 'nh02', 9),
       (3, 'nh03', 10);
INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro)  
VALUES ('nv04', '123', N'Nguyễn Văn D', 0),  
       ('nv05', '456', N'Trần Thị E', 1),
       ('nv06', '789', N'Lê Xuân F', 0);

INSERT INTO NguoiHoc (MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV, NgayDK)
VALUES ('nh04', N'Nguyễn Văn G', 1, '2003-07-10', '0981234567', 'g@email.com', NULL, 'nv04', '2022-01-15'),  
       ('nh05', N'Trần Thị H', 0, '2004-06-20', '0982234567', 'h@email.com', NULL, 'nv05', '2022-02-28'),
       ('nh06', N'Lê Văn I', 1, '2005-12-31', '0983234567', 'i@email.com', NULL, 'nv06', '2022-04-19');
       
INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa)
VALUES ('cd04', N'Lập trình C#', 350, 100, 'csharp.png', N'Lập trình C# cơ bản'),  
       ('cd05', N'Lập trình Android', 400, 110, 'android.png', N'Lập trình Android nâng cao'),
       ('cd06', N'Thiết kế Website', 300, 80, 'webdesign.png', N'Thiết kế giao diện Website');
       
INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao)
VALUES ('cd04', 350, 100, '2022-03-01', N'Khóa học C# cơ bản', 'nv04', '2022-01-15'),
       ('cd05', 400, 110, '2022-05-01', N'Khóa học Android nâng cao', 'nv05', '2022-03-25'),  
       ('cd06', 300, 80, '2022-07-10', N'Khóa học thiết kế Website', 'nv06', '2022-06-01');

INSERT INTO HocVien (MaKH, MaNH, Diem)  
VALUES (4, 'nh04', 9),
       (5, 'nh05', 8),
       (6, 'nh06', 10);
go