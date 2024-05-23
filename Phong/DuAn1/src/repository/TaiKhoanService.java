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
import model.TaiKhoan;

/**
 *
 * @author duong
 */
public class TaiKhoanService {
    public ArrayList<TaiKhoan> getAllTaiKhoan(){
        String sql = """
                     SELECT [Id]
                           ,[Ten]
                           ,[NgaySinh]
                           ,[Gioitinh]
                           ,[Sdt]
                           ,[IdCV]
                           ,[TaiKhoan]
                           ,[MatKhau]
                           ,[Email]
                           ,[TrangThai]
                           ,[Ngaytao]
                           ,[NgaySua]
                       FROM [dbo].[Users] where TrangThai = 1
                     """;
        
        try(Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql);
                ResultSet rs = ps.executeQuery();
                ) {
            ArrayList<TaiKhoan> ls = new ArrayList<>();
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setId(rs.getInt(1));
                tk.setTen(rs.getString(2));
                tk.setNgaySinh(rs.getDate(3));
                tk.setGioiTinh(rs.getBoolean(4));
                tk.setSdt(rs.getString(5));
                tk.setIdCV(rs.getInt(6));
                tk.setTaiKhoan(rs.getString(7));
                tk.setMatKhau(rs.getString(8));
                tk.setEmail(rs.getString(9));
                tk.setTrangThai(rs.getBoolean(10));
                tk.setNgayTao(rs.getDate(11));
                tk.setNgaySua(rs.getDate(12));
                ls.add(tk);
            }
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean them(TaiKhoan u){
        String sql = """
                     INSERT INTO [dbo].[Users]
                                ([Ten]
                                ,[NgaySinh]
                                ,[Gioitinh]
                                ,[Sdt]
                                ,[IdCV]
                                ,[TaiKhoan]
                                ,[MatKhau]
                                ,[Email]
                                ,[TrangThai])
                          VALUES
                                (?,?,?,?,?,?,?,?,?)
                     """;
        try (Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, u.getTen());
            ps.setObject(2, u.getNgaySinh());
            ps.setObject(3, u.getGioiTinh());
            ps.setObject(4, u.getSdt());
            ps.setObject(5, u.getIdCV());
            ps.setObject(6, u.getTaiKhoan());
            ps.setObject(7, u.getMatKhau());
            ps.setObject(8, u.getEmail());
            ps.setObject(9, u.getTrangThai());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }
    
    public boolean sua(TaiKhoan u ,int id){
        String sql = """
                    UPDATE [dbo].[Users]
                        SET [Ten] = ?
                           ,[NgaySinh] = ?
                           ,[Gioitinh] = ?
                           ,[Sdt] = ?
                           ,[IdCV] = ?
                           ,[TaiKhoan] = ?
                           ,[MatKhau] = ?
                           ,[Email] = ?
                           ,[TrangThai] = ?
                      WHERE Id = ? 
                     """;
        try (Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, u.getTen());
            ps.setObject(2, u.getNgaySinh());
            ps.setObject(3, u.getGioiTinh());
            ps.setObject(4, u.getSdt());
            ps.setObject(5, u.getIdCV());
            ps.setObject(6, u.getTaiKhoan());
            ps.setObject(7, u.getMatKhau());
            ps.setObject(8, u.getEmail());
            ps.setObject(9, u.getTrangThai());
            ps.setObject(10, id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return false;
    }
    
    public boolean getAllAccount(String userAccount) {
        ArrayList<TaiKhoan> ls = new ArrayList<>();
        String sql = """
                     SELECT [TaiKhoan]
                       FROM [dbo].[Users]
                     """;
        try (   Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            TaiKhoan u = new TaiKhoan();
            while (rs.next()) {
                u.setTaiKhoan(rs.getString(1));
                ls.add(u);
            }
            if (u.getTaiKhoan().equals(userAccount)) {
                return false;
            }

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return true;
    }
}
