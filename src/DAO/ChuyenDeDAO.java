package DAO;

import Model.ChuyenDe;
import Helper.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChuyenDeDAO extends SQL_queries_DAO<ChuyenDe, String> {

    String INSERT_SQL = "INSERT INTO ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE ChuyenDe SET TenCD = ?, HocPhi = ?, ThoiLuong = ?, Hinh = ?, MoTa = ? WHERE MaCD = ?";
    String DELETE_SQL = "DELETE FROM ChuyenDe WHERE MaCD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChuyenDe WHERE MaCD = ?";

    @Override
    public void insert(ChuyenDe entity) {
        DBHelper.update(INSERT_SQL, 
                entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getDuration(), entity.getImage(), entity.getInfo());
    }

    @Override
    public void update(ChuyenDe entity) {
        DBHelper.update(UPDATE_SQL, 
                entity.getTenCD(), entity.getHocPhi(), entity.getDuration(), entity.getImage(), entity.getInfo(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        DBHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectByID(String id) {
        List<ChuyenDe> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<ChuyenDe> selectBySQL(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<ChuyenDe>();
        try {
            ResultSet rs = DBHelper.query(sql, args);
            while(rs.next()){
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString("MaCD"));
                cd.setTenCD(rs.getString("TenCD"));
                cd.setHocPhi(rs.getDouble("HocPhi"));
                cd.setDuration(rs.getInt("ThoiLuong"));
                cd.setImage(rs.getString("Hinh"));
                cd.setInfo(rs.getString("MoTa"));
                list.add(cd);
            }
            
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
