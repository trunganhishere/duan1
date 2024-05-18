/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import model.HoaDon;

/**
 *
 * @author duong
 */
public class HoaDonService {

    public boolean hoaDonMoi(HoaDon hd) {
        String sql = """
                     INSERT INTO [dbo].[HoaDon]
                                ([Ma]
                                ,[IdKH]
                                ,[IdNV]
                                ,[TinhTrang])
                          VALUES
                                (?,1,?,0)
                     """;
        int check = 0;
        try (Connection con = ConenctionProvider.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, hd.getMa());
            ps.setObject(2, hd.getIdNV());
            check = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check > 0;
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        String sql = """
                     SELECT [Id]
                           ,[IdKH]
                           ,[IdNV]
                           ,[Ma]
                           ,[NgayThanhToan]
                           ,[TinhTrang]
                           ,[Ghichu]
                           ,[TongTien]
                           ,[Ngaytao]
                           ,[NgaySua]
                       FROM [dbo].[HoaDon]
                     """;
        try (Connection con = ConenctionProvider.getConnection(); PreparedStatement ps = con.prepareCall(sql); ResultSet rs = ps.executeQuery()) {
            ArrayList<HoaDon> ls = new ArrayList<>();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt(1));
                hd.setIdKH(rs.getInt(2));
                hd.setIdNV(rs.getInt(3));
                hd.setMa(rs.getString(4));
                hd.setNgayThanhToan(rs.getDate(5));
                hd.setTinhTrang(rs.getBoolean(6));
                hd.setGhiChu(rs.getString(7));
                hd.setTongTien(rs.getInt(8));
                hd.setNgayTao(rs.getDate(9));
                hd.setNgaySua(rs.getDate(10));
                ls.add(hd);
            }
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
