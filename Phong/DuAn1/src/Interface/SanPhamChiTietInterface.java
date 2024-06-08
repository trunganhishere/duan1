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
    public int add(SanPhamChiTiet spct);
    public int update(SanPhamChiTiet sp,String id);
    public boolean updateSoLuongSPCT(int soLuong, int idSP);
    public List<SanPhamChiTiet> getAll2();
    public List<SanPhamChiTiet> Search(String ten);
    public List<model.SanPhamChiTiet> getAllForUpdate(int idSPCT);
}
