/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.KhoaHoc;


/**
 *
 * @author PC
 */
public class KhoaHocDAOImpl implements KhoaHocDAO{
    @Override
    public List<KhoaHoc> getList(){
        try {
            Connection cons = DBConnect.getConnection();
            String sql = "select * from khoa_hoc";
            List<KhoaHoc> list = new ArrayList<>();
            PreparedStatement ps = cons.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                KhoaHoc khoaHoc = new KhoaHoc();
                khoaHoc.setMa_khoa_hoc(rs.getInt("ma_khoa_hoc"));
                khoaHoc.setTen_khoa_hoc(rs.getString("ten_khoa_hoc"));
                khoaHoc.setMo_ta(rs.getString("mo_ta"));
                khoaHoc.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
                khoaHoc.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
                khoaHoc.setTinh_trang(rs.getBoolean("tinh_trang"));
                list.add(khoaHoc);
            }
            ps.close();
            rs.close();
            cons.close();
            return list;
            } catch (SQLException ex) {
                  ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public int createOrUpdate(KhoaHoc khoaHoc) {
        try {
            Connection cons = DBConnect.getConnection();
            String sql = "insert into khoa_hoc(ma_khoa_hoc, ten_khoa_hoc, mo_ta, ngay_bat_dau, ngay_ket_thuc, tinh_trang) VALUES(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE"
                    + " ten_khoa_hoc = VALUES(ten_khoa_hoc),mo_ta = VALUES(mo_ta), ngay_bat_dau = VALUES(ngay_bat_dau), ngay_ket_thuc = VALUES(ngay_ket_thuc) , tinh_trang = VALUES(tinh_trang);";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, khoaHoc.getMa_khoa_hoc());
            ps.setString(2, khoaHoc.getTen_khoa_hoc());
            ps.setString(3, khoaHoc.getMo_ta());
            ps.setDate(4, (Date) khoaHoc.getNgay_bat_dau());
            ps.setDate(5, (Date) khoaHoc.getNgay_ket_thuc());
            ps.setBoolean(6, khoaHoc.isTinh_trang());


            
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            ps.close();
            cons.close();
            return generatedKey;
        } catch (Exception ex) {
            
        }
        return 0;
    }
    

    @Override
    public boolean delete(KhoaHoc khoaHoc) {
        try {
            Connection cons = DBConnect.getConnection();
            String sqlKhoaHoc = "DELETE FROM khoa_hoc WHERE ma_khoa_hoc=?";
            PreparedStatement preparedStatement = cons.prepareStatement(sqlKhoaHoc);
            preparedStatement.setInt(1, khoaHoc.getMa_khoa_hoc());
            int result = preparedStatement.executeUpdate();

            if (result != 0) {

                PreparedStatement preparedStatement2 = cons.prepareStatement(sqlKhoaHoc);
                preparedStatement2.setInt(1, khoaHoc.getMa_khoa_hoc());
                preparedStatement2.executeUpdate();

                System.out.println("KhoaHoc deleted with id = " + khoaHoc.getMa_khoa_hoc());
                DBConnect.disconnect();
                return true;

            } else {
                System.out.println("No KhoaHoc was deleted with id = " + khoaHoc.getMa_khoa_hoc());
                DBConnect.disconnect();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        KhoaHocDAO khoaHocDAO = new KhoaHocDAOImpl();
        System.out.println(khoaHocDAO.getList());
    }
    
}
