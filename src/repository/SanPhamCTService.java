/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.SanPhamChiTiet;

/**
 *
 * @author duong
 */
public class SanPhamCTService {
    
    public ArrayList<SanPhamChiTiet> getAllSanPhamCT(){
        String sql = """
                     SELECT [Id]
                           ,[IdNsx]
                           ,[IdMauSac]
                           ,[IdDMuc]
                           ,[IdKC]
                           ,[IdCL]
                           ,[IdTH]
                           ,[MoTa]
                           ,[SoLuongTon]
                           ,[GiaNhap]
                           ,[GiaBan]
                           ,[QrCode]
                           ,[IdSP]
                           ,[idkm]
                       FROM [dbo].[ChitietSP]
                     """;
        try(Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql);
                ResultSet rs = ps.executeQuery()) {
            ArrayList<SanPhamChiTiet> ls = new ArrayList<>();
            while(rs.next()){
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setId(rs.getInt(1));
                spct.setIdNSX(rs.getInt(2));
                spct.setIdMauSac(rs.getInt(3));
                spct.setIdDanhMuc(rs.getInt(4));
                spct.setIdKC(rs.getInt(5));
                spct.setIdCL(rs.getInt(6));
                spct.setIdTH(rs.getInt(7));
                spct.setMoTa(rs.getString(8));
                spct.setSoLuongTon(rs.getInt(9));
                spct.setGiaNhap(rs.getInt(10));
                spct.setGiaBan(rs.getInt(11));
                spct.setQrCode(rs.getString(12));
                spct.setIdSP(rs.getInt(13));
                spct.setIdKM(rs.getInt(14));
                ls.add(spct);
            }
            return  ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateSoLuongSPCT(int soLuong, int idSP){
        int check = 0;
        String sql = """
                     UPDATE [dbo].[ChitietSP]
                        SET[SoLuongTon] = ?
                      WHERE idSP = ?
                     """;
        
        try(Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql)) {
            ps.setObject(1, soLuong);
            ps.setObject(2, idSP);
            check = ps.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
