/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Interface.KichCoInterface;
import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KichCo;


public class KichCoService implements KichCoInterface{
 private Connection con = ConenctionProvider.getConnection();    

    @Override
    public List<KichCo> getAll() {
         try {
            String SQL = "SELECT * FROM KichCo";
            
            Statement st =  con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            List<KichCo> List = new ArrayList<>();
            while(rs.next()){
            KichCo cl = new KichCo();
            cl.setId(rs.getInt(1));
            cl.setTen(rs.getString(2));
            
            List.add(cl);
            }
            return List;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean add(KichCo kichCo) {
        String SQL = "INSERT INTO KichCo (Ten) VALUES(?)";
        try {
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setString(1, kichCo.getTen());
            
            pstm.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(KichCo cl, int id) {
         String sql = "UPDATE KichCo SET  TEN = ? WHERE ID =?";
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
        String SQL = "SELECT COUNT(*) FROM KichCo WHERE Ten = ?";
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
