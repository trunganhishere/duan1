/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.util.ArrayList;
import model.HoaDonCT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import model.HoaDon;
import model.SanPhamChiTiet;

/**
 *
 * @author duong
 */
public class HoaDonCTService {
    
    public ArrayList<HoaDonCT> getAllHoaDonCT(){
        String sql = """
                     SELECT [IdHD]
                           ,[IdCTSP]
                           ,[Soluong]
                           ,[Dongia]
                       FROM [dbo].[HoaDonChiTiet]
                     """;
        try(Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql);
                ResultSet rs = ps.executeQuery()) {
            ArrayList<HoaDonCT> ls = new ArrayList<>();
            while(rs.next()){
                HoaDonCT hd = new HoaDonCT();
                hd.setIdHD(rs.getInt(1));
                hd.setIdCTSP(rs.getInt(2));
                hd.setSoLuong(rs.getInt(3));
                hd.setDonGia(rs.getInt(4));
                ls.add(hd);
            }
            return  ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
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
