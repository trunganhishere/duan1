/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.HoaDonChiTietInterface;
import JDBCUtil.ConenctionProvider;
import java.util.ArrayList;
import java.util.List;
import model.HoaDonChiTiet;
import java.sql.*;
import java.util.Collections;
import model.HoaDon;
import model.SanPhamChiTiet;


public class HoaDonCTService implements HoaDonChiTietInterface{

     Connection conn = ConenctionProvider.getConnection();

    @Override
    public List<HoaDonChiTiet> getAllHDCT() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDonChiTiet";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                SanPhamChiTiet spct = new SanPhamChiTiet();
                HoaDon hd = new HoaDon();
                spct.setId(rs.getString("IDCTSP"));
                hd.setId(rs.getInt("IDHD"));
                hdct.setHaoDon(hd);
                hdct.setSanPham(spct);
                hdct.setDonGia(rs.getDouble("Dongia"));
                hdct.setSoluong(rs.getInt("soLuong"));
                list.add(hdct);
            }
            Collections.reverse(list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateSoLuongSPHoaDonCT(int IDHD, int IDSPCT, int soLuong) {
        try {
            String sql = "UPDATE HOADONCHITIET SET SoLuong = ? where idHD = ? and IDCTSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, IDHD);
            stmt.setInt(3, IDSPCT);
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public boolean updateSoLuongSP(int id, int soLuong) {
        try {
            String sql = "UPDATE ChitietSP set soLuongTon = ? where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setInt(2, id);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    @Override
    public boolean addHDCT(HoaDon hoaDon, SanPhamChiTiet sanPhamChiTiet, int soLuong, double Dongia) {
        try {
            String sql = "insert into HoaDonChiTiet(IdHD,IdCTSP,SoLuong,Dongia) values(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, hoaDon.getId());
            stmt.setInt(2, Integer.parseInt(sanPhamChiTiet.getId()));
            stmt.setInt(3, soLuong);
            stmt.setDouble(4, Dongia);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteHDCT(int IdHD, int IdSP) {
        try {
            String sql = "DELETE FROM HoaDonChiTiet WHERE IdHD = ? AND IdCTSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, IdHD);
            stmt.setInt(2, IdSP);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean deleteAllHDCT( int idHD){
        int check;
        String sql = """
                     DELETE FROM [dbo].[HoaDonChiTiet]
                           WHERE IdHD = ?
                     """;
        
        try(Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, idHD);
            check = ps.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
