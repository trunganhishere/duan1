/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.ArrayList;
import java.util.List;
import model.KhachHang;

/**
 *
 * @author Dung
 */
public interface KhachHangInterface {

    public ArrayList<KhachHang> getAll();

    public void find(String khachHang);

    public boolean add(KhachHang khachHang);

    public void update(KhachHang khachHang, int index);

    public void delete(int khachHang);
    
    public ArrayList<KhachHang> search(String searchKey);
    
    public ArrayList<KhachHang> getAllEmailUpdate(String email);
    
    public KhachHang selectById(String id);
    
    public void countDB();

}
