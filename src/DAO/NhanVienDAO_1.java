package DAO;

import Model.NhanVien;
import Helper.DBHelper;
import Helper.DialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO_1 extends SQL_queries_DAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NhanVien(MaNV, Password, FullName, Role) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET Password = ?, FullName = ?, Role = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    
    @Override
    public void insert(NhanVien entity) {
        DBHelper.update(INSERT_SQL, 
                entity.getMaNV(), entity.getPassword(), entity.getFullName(), entity.getRole());
    }

    @Override
    public void update(NhanVien entity) {
        DBHelper.update(UPDATE_SQL, 
                entity.getPassword(),entity.getFullName(), entity.getRole(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectByID(String id) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setPassword(rs.getString("MatKhau"));
                nv.setFullName(rs.getString("HoTen"));
                nv.setRole(rs.getBoolean("VaiTro"));
                list.add(nv);
            }
            
        } catch (Exception e) {
//            DialogHelper.showDialog(null, "Vui lòng kiểm tra lại database!");
//            throw new RuntimeException(e);
        }
        return list;
    }
    
}
