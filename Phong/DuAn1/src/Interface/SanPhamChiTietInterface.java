/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.SanPhamChiTiet;



public interface SanPhamChiTietInterface {
    List<SanPhamChiTiet> getAll();
    public boolean phucHoiSoLuong(int maSP,int soLuongMoi);
   
}
