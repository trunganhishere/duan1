/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.KhachHangInterface;
import JDBCUtil.ConenctionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import model.TaiKhoan;


public class KhachHangService implements KhachHangInterface {

    private Connection con = ConenctionProvider.getConnection();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    long count, soTrang, trang = 1;

    @Override
    public ArrayList<KhachHang> getAll() {
        try {
            String sSQL = "SELECT Id, Ten, Gioitinh, NgaySinh, Email, Sdt, Diemthuong FROM KhachHang";
            con = ConenctionProvider.getConnection();

            Statement pstm = con.createStatement();
            ResultSet rs = pstm.executeQuery(sSQL);

            ArrayList<KhachHang> List = new ArrayList<>();
            while (rs.next()) {
                KhachHang khachhang = new KhachHang();
                khachhang.setId(rs.getInt(1));
                khachhang.setTen(rs.getString(2));
                khachhang.setGioitinh(rs.getBoolean(3));
                khachhang.setNgaysinh(rs.getDate(4));
                khachhang.setEmail(rs.getString(5));
                khachhang.setSdt(rs.getString(6));
                khachhang.setDiemthuong(rs.getInt(7));
                List.add(khachhang);
            }

            return List;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    
    @Override
    public boolean add(KhachHang khachHang) {
        String sql = "INSERT INTO KhachHang(Ten, NgaySinh, Gioitinh, Sdt, Email) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(1, khachHang.getTen());
            stm.setDate(2, new java.sql.Date(khachHang.getNgaysinh().getTime()));
            stm.setBoolean(3, khachHang.isGioitinh());
            stm.setString(4, khachHang.getSdt());
            stm.setString(5, khachHang.getEmail());
            stm.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    @Override
    public void update(KhachHang khachHang , int index) {
        String sql = "UPDATE KhachHang SET Ten = ?, NgaySinh = ?, GioiTinh = ?, Sdt = ?, Email = ? WHERE Id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(1, khachHang.getTen());
            stm.setDate(2, new java.sql.Date(khachHang.getNgaysinh().getTime()));
            stm.setBoolean(3, khachHang.isGioitinh());
            stm.setString(4, khachHang.getSdt());
            stm.setString(5, khachHang.getEmail());
            stm.setString(6, index+"");
//            stm.executeUpdate();
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void find(String khachHang) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int khachHang) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   

    @Override
    public ArrayList<KhachHang> search(String searchKey) {
        String sql = """
                     SELECT [Id]
                            ,[Ten]
                            ,[Gioitinh]
                            ,[NgaySinh]
                            ,[Email]
                            ,[Sdt]
                            ,[Diemthuong]
                            ,[Ngaytao]
                            ,[NgaySua]
                        FROM [dbo].[KhachHang] where Ten like ? or Sdt like ? or Email like ? 
                     """;
        try (Connection con = ConenctionProvider.getConnection(); PreparedStatement ps = con.prepareCall(sql);) {
            if (searchKey.length() != 0) {
                ps.setString(1, "%" + searchKey + "%");
                ps.setString(2, "%" + searchKey + "%");
                ps.setString(3, "%" + searchKey + "%");
                ps.executeQuery();
            }
            try (ResultSet rs = ps.executeQuery();) {
                ArrayList<model.KhachHang> ls = new ArrayList<>();
                while (rs.next()) {
                    KhachHang tk = new KhachHang();
                    tk.setId(rs.getInt(1));
                    tk.setTen(rs.getString(2));
                    tk.setNgaysinh(rs.getDate(4));
                    tk.setGioitinh(rs.getBoolean(3));
                    tk.setSdt(rs.getString(6));
                    tk.setEmail(rs.getString(5));
                    ls.add(tk);
                }
                return ls;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<KhachHang> getAllEmailUpdate(String email) {
        String sql = """
                     SELECT [Id]
                             ,[Ten]
                             ,[Gioitinh]
                             ,[NgaySinh]
                             ,[Email]
                             ,[Sdt]
                             ,[Diemthuong]
                             ,[Ngaytao]
                             ,[NgaySua]
                     FROM [dbo].[KhachHang] where Email <> ?
                     """;

        try (Connection con = ConenctionProvider.getConnection(); PreparedStatement ps = con.prepareCall(sql);) {
            ps.setObject(1, email);
            ArrayList<KhachHang> ls = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang tk = new KhachHang();
                tk.setId(rs.getInt(1));
                tk.setTen(rs.getString(2));
                tk.setGioitinh(rs.getBoolean(3));
                tk.setNgaysinh(rs.getDate(4));
                tk.setEmail(rs.getString(5));
                tk.setSdt(rs.getString(6));

                ls.add(tk);
            }
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

//    @Override
//    public KhachHang selectById(String id) {
//        String sql = """
//                     SELECT * FROM KhachHang WHERE Id =?
//                     """;
//        List<KhachHang> list = this.selectBySQL(sql, id);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }

    @Override
    public KhachHang selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void countDB(){
        try {
            String query = " select count(*) from KhachHang ";
            con = ConenctionProvider.getConnection();

            Statement pstm = con.createStatement();
            ResultSet rs = pstm.executeQuery(query);
            while (rs.next()) {                
                count = rs.getLong(1);
            }
            rs.close();
            pstm.close();
            con.close();
        } catch (SQLException e) {
            
        }
    }
  
}
