/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.hoaDonInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.SanPhamChiTiet;


public class HoaDonService implements hoaDonInterface {
    Connection con = ConenctionProvider.getConnection();

    public List<HoaDon> getAll() {

        try {
            List<HoaDon> list = new ArrayList<>();
            String sql = "SELECT * FROM HOADON";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setIDKhachHang(rs.getInt("idkh"));
                hd.setIdUser(rs.getInt("idNV"));
                hd.setMa(rs.getString("Ma"));
                hd.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hd.setTinhTrang(rs.getInt("TinhTrang"));
                hd.setGhichu(rs.getString("ghiChu"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                list.add(hd);

            }
//            Collections.reverse(list); 
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public boolean updateMaKHforHoaDon(int idKh, int idHD) {
        try {
            String sql = "UPDATE HoaDon SET IDKH = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idKh);
            stmt.setInt(2, idHD);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addHoaDon(String maHD,int idNV) {
        try {
            String sql = """
                         INSERT INTO [dbo].[HoaDon]
                                    ([IdKH]
                                    ,[IdNV]
                                    ,[Ma]
                                    ,[TinhTrang])
                              VALUES
                                    (1,?,?,0)
                         """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idNV);
            stm.setString(2, maHD);
            stm.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updateTongTien(int idHD, double tongTien) {

        try {
            String sql = "UPDATE HoaDon SET tongTien = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, tongTien);
            stmt.setInt(2, idHD);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

     @Override
    public boolean updateThanhToan(int idHD) {
        
        try {
            String sql = "UPDATE HoaDon SET TinhTrang = 1,NgayThanhToan = GetDate() WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDouble(1, idHD);
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
            PreparedStatement stmt = con.prepareStatement(sql);
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
    public List<HoaDon> getAllHDChuaTT() {

        try {
            List<HoaDon> list = new ArrayList<>();
            String sql = "SELECT * FROM HOADON where TinhTrang = 0";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setIDKhachHang(rs.getInt("idkh"));
                hd.setIdUser(rs.getInt("idNV"));
                hd.setMa(rs.getString("Ma"));
                hd.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hd.setTinhTrang(rs.getInt("TinhTrang"));
                hd.setGhichu(rs.getString("ghiChu"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                list.add(hd);

            }
//            Collections.reverse(list); 
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     @Override
    public List<HoaDonChiTiet> getById(int idHD) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDonChiTiet where IDHD=" + idHD;
            Statement stmt = con.createStatement();
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
    public boolean deleteById(int idHD) {
        try {
            String sql1 = "DELETE FROM HoaDonChiTiet WHERE IdHD = ?";
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            stmt1.setInt(1, idHD);
            String sql = "delete from HOADON where Id=" + idHD;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt1.execute();
            stmt.execute();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
