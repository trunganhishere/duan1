/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.KhuyenMaiInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KhuyenMai;

/**
 *
 * @author duong
 */
public class KhuyenMaiService implements KhuyenMaiInterface {

    Connection con = ConenctionProvider.getConnection();

    @Override
    public List<KhuyenMai> getAll() {
        try {
            String sql = "select id,ten,hinhthuckm,giatrigiam,soluong,MaCode from KhuyenMai";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<KhuyenMai> list = new ArrayList<>();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setId(rs.getInt(1));
                km.setTenKhuyenMai(rs.getString(2));
                km.setHinhThucKM(rs.getString(3));
                km.setGiaTriGiam(rs.getString(4));
                km.setSoLuong(rs.getInt(5));
                km.setCodeKhuyenMai(rs.getString("MaCode"));
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void add(KhuyenMai km) {
        try {
            String sql = "insert into  KhuyenMai(Ten,HinhthucKM,Giatrigiam,SOLUONG,MaCode) values(?,?,?,?,?); ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, km.getTenKhuyenMai());
            stmt.setString(2, km.getHinhThucKM());
            stmt.setString(3, km.getGiaTriGiam());
            stmt.setInt(4, km.getSoLuong());
            stmt.setString(5, km.getCodeKhuyenMai());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(KhuyenMai km) {
        try {
            String sql = "update KhuyenMai set Ten=?, HinhthucKM=?,Giatrigiam=?,SOLUONG=? where Id=?  ; ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, km.getTenKhuyenMai());
            stmt.setString(2, km.getHinhThucKM());
            stmt.setString(3, km.getGiaTriGiam());
            stmt.setInt(4, km.getSoLuong());
            stmt.setInt(5, km.getId());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
