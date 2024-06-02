/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;


public class DangNhapService {
     public TaiKhoan login(String username , String password){
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
                       FROM [dbo].[Users]
                     WHERE TaiKhoan = ? AND MatKhau = ?""";
        try (Connection con = ConenctionProvider.getConnection();
                PreparedStatement ps = con.prepareCall(sql);) {
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        TaiKhoan tk = new TaiKhoan();
        if (rs.next()) {
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
                tk.setNgaySua(rs.getDate(11));
                tk.setNgaySua(rs.getDate(12));
        } 
        return tk;
        }catch (Exception e) {
                e.printStackTrace();
        }
        return null;
    }
     
    
    public static String username="";
    public static String password="";

    public DangNhapService() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DangNhapService.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DangNhapService.password = password;
    }
}
