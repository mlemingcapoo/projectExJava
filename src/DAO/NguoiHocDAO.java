package DAO;

import Model.NguoiHoc;
import Helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiHocDAO extends SQL_queries_DAO<NguoiHoc, String> {

    String INSERT_SQL = "INSERT INTO NguoiHoc(MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV) VALUES(?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, DienThoai = ?, Email = ?, GhiChu = ?, MaNV = ? WHERE MaNH = ?";
    String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
    String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNH = ?";

    @Override
    public void insert(NguoiHoc entity) {
        DBHelper.update(INSERT_SQL, 
                entity.getMaNH(), entity.getFullName(), entity.isGender(), 
                new java.sql.Date(entity.getBirth().getTime()), 
                entity.getPhoneNum(), entity.getMail(), entity.getNote(), entity.getMaNV());
    }

    @Override
    public void update(NguoiHoc entity) {
        DBHelper.update(UPDATE_SQL, 
                entity.getFullName(), entity.isGender(), new java.sql.Date(entity.getBirth().getTime()), 
                entity.getPhoneNum(), entity.getMail(), entity.getNote(), entity.getMaNV(),
                entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectByID(String id) {
        List<NguoiHoc> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NguoiHoc> selectBySQL(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<NguoiHoc>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while(rs.next()){
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));
                nh.setFullName(rs.getString("HoTen"));
                nh.setGender(rs.getBoolean("GioiTinh"));
                nh.setBirth(rs.getDate("NgaySinh"));
                nh.setPhoneNum(rs.getString("DienThoai"));
                nh.setMail(rs.getString("Email"));
                nh.setNote(rs.getString("GhiChu"));
                nh.setMaNV(rs.getString("MaNV"));
                nh.setJoinDate(rs.getDate("NgayDK"));
                list.add(nh);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<NguoiHoc> selectByKeyword(String keyword){
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return this.selectBySQL(sql, "%" + keyword + "%");
    }

    public List<NguoiHoc> selectNotInCourse(int makh, String keyword){
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ? AND "
                + "MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH = ?)";
        return this.selectBySQL(sql, "%" + keyword + "%", makh);
    }
}
