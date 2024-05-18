/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.KhachHang;
/**
 *
 * @author Dung
 */
public interface KhachHangInterface {
    
    public List<KhachHang> getAll();   
    public void find (String khachHang);

}
