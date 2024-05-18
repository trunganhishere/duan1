/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.util.ArrayList;
import model.KhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author duong
 */
public class KhuyenMaiService {
    public ArrayList<KhuyenMai> getAllKM(){
        String sql = """
                     SELECT [Id]
                           ,[Ten]
                           ,[HinhthucKM]
                           ,[Giatrigiam]
                           ,[Trangthai]
                           ,[Ngaytao]
                           ,[NgaySua]
                           ,[SOLUONG]
                           ,[MaCode]
                       FROM [dbo].[KhuyenMai]
                     """;
        try (Connection con = ConenctionProvider.getConnection(); PreparedStatement ps = con.prepareCall(sql); ResultSet rs = ps.executeQuery()) {
            ArrayList<KhuyenMai> ls = new ArrayList<>();
            while(rs.next()){
                KhuyenMai km = new KhuyenMai();
                km.setId(rs.getInt(1));
                km.setTen(rs.getString(2));
                km.setHinhThuc(rs.getString(3));
                km.setGiaTriGiam(rs.getInt(4));
                km.setTrangThai(rs.getBoolean(5));
                km.setNgayTao(rs.getDate(6));
                km.setNgaySua(rs.getDate(7));
                km.setSoLuong(rs.getInt(8));
                km.setMaCode(rs.getString(9));
                ls.add(km);
            }
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
