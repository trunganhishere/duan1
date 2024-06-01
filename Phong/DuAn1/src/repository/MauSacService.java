/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.MauSac;
import Interface.MauSacInterface;


public class MauSacService implements MauSacInterface{
    private Connection con = ConenctionProvider.getConnection();    
    @Override
    public List<MauSac> getAll() {
         try {
            String SQL = "SELECT * FROM MauSac";
            
            Statement st =  con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            List<MauSac> List = new ArrayList<>();
            while(rs.next()){
            MauSac mauSac = new MauSac();
            mauSac.setId(rs.getInt(1));
            mauSac.setTen(rs.getString(2));
            
            List.add(mauSac);
            }
            return List;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(MauSac MauSac) {
        String SQL = "INSERT INTO MauSac (Ten) VALUES(?)";
        try {
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setString(1, MauSac.getTen());
            
            pstm.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(MauSac cl, int id) {
         String sql = "UPDATE MauSac SET  TEN = ? WHERE ID =?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cl.getTen());
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
        String SQL = "SELECT COUNT(*) FROM MauSac WHERE Ten = ?";
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
