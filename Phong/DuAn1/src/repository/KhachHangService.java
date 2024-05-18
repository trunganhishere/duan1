/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.KhachHangInterface;
import JDBCUtil.ConenctionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;


public class KhachHangService implements KhachHangInterface {

    private Connection con = ConenctionProvider.getConnection();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<KhachHang> getAll() {
        try {
            String sSQL = "SELECT Id, Ten, Gioitinh, NgaySinh, Email, Sdt, Diemthuong FROM KhachHang";
            con = ConenctionProvider.getConnection();

            Statement pstm = con.createStatement();
            ResultSet rs = pstm.executeQuery(sSQL);

            List<KhachHang> List = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachhang = new KhachHang();
                khachhang.setId(rs.getInt(1));
                khachhang.setTen(rs.getString(2));
                khachhang.setGioitinh(rs.getBoolean(3));
                khachhang.setNgaysinh(rs.getDate(4));
                khachhang.setEmail(rs.getString(5));
                khachhang.setSdt(rs.getString(6));
                khachhang.setDiemthuong(rs.getInt(7));
                List.add(khachhang);
            }

            return List;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

  

   

    @Override
    public void find(String khachHang) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
