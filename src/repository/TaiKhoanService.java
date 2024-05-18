/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.util.ArrayList;
import model.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
