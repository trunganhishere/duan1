/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.SanPhamInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;

/**
 *
 * @author duong
 */
public class SanPhamService implements SanPhamInterface {

    Connection con = ConenctionProvider.getConnection();

    @Override
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "Select* from SanPham";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("id") + "");
                sp.setTen(rs.getString("TenSanPham"));
                sp.setNgayTao(rs.getString("Ngaytao"));
                sp.setNgaySua(rs.getString("Ngaysua"));
                list.add(sp);
            }
            return list;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public int add(SanPham sp) {
   
    if (tenSanPhamDaTonTai(sp.getTen())) {
        System.out.println("Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác.");
        return 0; // Trả về 0 để biểu thị thất bại
    }

    // Nếu tên sản phẩm chưa tồn tại, tiến hành thêm vào cơ sở dữ liệu
    String sql = "INSERT INTO SanPham(TenSanPham) VALUES(?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, sp.getTen());
        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        return 0; // Trả về 0 để biểu thị thất bại
    }
}
    private boolean tenSanPhamDaTonTai(String tenSanPham) {
    String sql = "SELECT COUNT(*) FROM SanPham WHERE TenSanPham = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, tenSanPham);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // Trả về true nếu tên sản phẩm đã tồn tại, ngược lại trả về false
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Mặc định trả về false nếu có lỗi xảy ra
}

    @Override
    public int update(SanPham sp, int index) {
        String sql = """
                     UPDATE dbo.SanPham
                     SET TenSanPham = ?,NgaySua=GETDATE()
                     WHERE ID= ?""";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, sp.getTen());
            ps.setObject(2, index);
            return ps.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

}
