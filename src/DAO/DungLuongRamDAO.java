package DAO;

import DTO.ThuocTinhSanPham.DungLuongRamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.JDBCUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DungLuongRamDAO implements DAOinterface<DungLuongRamDTO> {

    public static DungLuongRamDAO getInstance() {
        return new DungLuongRamDAO();
    }

    @Override
    public int insert(DungLuongRamDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO dungluongram (kichthuocram, trangthai) VALUES (?, 1)";
            PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, t.getDungluongram());
            result = pst.executeUpdate();

            // Lấy giá trị ID tự động tăng
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                t.setMadlram(rs.getInt(1));
            }

            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int update(DungLuongRamDTO t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE dungluongram SET kichthuocram = ? WHERE madlram = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getDungluongram());
            pst.setInt(2, t.getMadlram());
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE dungluongram SET trangthai = 0 WHERE madlram = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            result = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public ArrayList<DungLuongRamDTO> selectAll() {
        ArrayList<DungLuongRamDTO> result = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM dungluongram WHERE trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int madlram = rs.getInt("madlram");
                int kichthuocram = rs.getInt("kichthuocram");
                DungLuongRamDTO dlr = new DungLuongRamDTO(madlram, kichthuocram);
                result.add(dlr);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public DungLuongRamDTO selectById(String t) {
        DungLuongRamDTO result = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM dungluongram WHERE madlram = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int madlram = rs.getInt("madlram");
                int kichthuocram = rs.getInt("kichthuocram");
                result = new DungLuongRamDTO(madlram, kichthuocram);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    @Override
    public int getAutoIncrement() {
        int result = -1;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT IDENT_CURRENT('dungluongram') AS CurrentIdentity";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CurrentIdentity");
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(DungLuongRamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
