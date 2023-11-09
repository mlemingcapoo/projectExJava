package DAO;

import Model.KhoaHoc;
import Helper.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhoaHocDAO extends SQL_queries_DAO<KhoaHoc, Integer> {

    String INSERT_SQL = "INSERT INTO KhoaHoc(MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES(?,?,?,?,?,?,GETDATE())";
    String UPDATE_SQL = "UPDATE KhoaHoc SET MaCD = ?, HocPhi = ?, ThoiLuong = ?, NgayKG = ?, GhiChu = ?, MaNV = ? WHERE MaKH = ?";
    String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH = ?";

    @Override
    public void insert(KhoaHoc entity) {
        DBHelper.update(INSERT_SQL,
                entity.getMaCD(), entity.getHocPhi(), entity.getDuration(), 
                new java.sql.Date(entity.getKGdate().getTime()), entity.getNote(), entity.getMaNV());
    }

    @Override
    public void update(KhoaHoc entity) {
        DBHelper.update(UPDATE_SQL,
                entity.getMaCD(), entity.getHocPhi(), entity.getDuration(), 
                new java.sql.Date(entity.getKGdate().getTime()), entity.getNote(), entity.getMaNV(),
                 entity.getMaKH());
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhoaHoc> selectBySQL(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<KhoaHoc>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setMaCD(rs.getString("MaCD"));
                kh.setHocPhi(rs.getDouble("HocPhi"));
                kh.setDuration(rs.getInt("ThoiLuong"));
                kh.setKGdate(rs.getDate("NgayKG"));
                kh.setMaNV(rs.getString("MaNV"));
                kh.setDateCreated(rs.getDate("NgayTao"));
                list.add(kh);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhoaHoc> selectByChuyenDe(String macd) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaCD = ?";
        return this.selectBySQL(sql, macd);
    }

    @Override
    public void delete(Integer id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public KhoaHoc selectByID(Integer id) {
        List<KhoaHoc> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<Integer> selectYears(){
        String sql = "SELECT DISTINCT year(NgayKG) as YEAR FROM KhoaHoc ORDER BY YEAR DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DBHelper.query(sql);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(KhoaHocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
