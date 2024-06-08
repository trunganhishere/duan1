/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.NhaSanXuatInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.NSX;

/**
 *
 * @author duong
 */
public class NSXService implements NhaSanXuatInterface{

     private Connection con = ConenctionProvider.getConnection(); 
    @Override
    public List<NSX> getAll() {
          try {
            String SQL = "SELECT * FROM NSX";
            
            Statement st =  con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            List<NSX> List = new ArrayList<>();
            while(rs.next()){
            NSX cl = new NSX();
            cl.setId(rs.getInt(1));
            cl.setTen(rs.getString(2));
            
            List.add(cl);
            }
            return List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(NSX nsx) {
           String SQL = "INSERT INTO NSX (Ten) VALUES(?)";
        try {
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setString(1, nsx.getTen());
            
            pstm.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NSX nsx, int id) {
        String sql = "UPDATE NSX SET  TEN = ? WHERE ID =?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nsx.getTen());
            stmt.setInt(2, id);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean existsByName(String name) {
        String SQL = "SELECT COUNT(*) FROM NSX WHERE Ten = ?";
        try {
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
