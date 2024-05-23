/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.KhuyenMaiInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KhuyenMai;

/**
 *
 * @author duong
 */
public class KhuyenMaiService implements KhuyenMaiInterface{
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
}