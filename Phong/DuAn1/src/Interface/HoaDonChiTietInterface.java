/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.SanPhamChiTiet;




public interface HoaDonChiTietInterface  {
    public List<HoaDonChiTiet> getAllHDCT();
     public boolean updateSoLuongSPHoaDonCT(int IDHD, int IDSPCT, int soLuong);
  public boolean updateSoLuongSP(int id, int soLuong);
    public boolean addHDCT(HoaDon hoaDon,SanPhamChiTiet sanPhamChiTiet,int soLuong, double DonGia);
    public boolean deleteHDCT(int IdHD,int IdSP);
     public boolean deleteAllHDCT( int idHD);
        public List<HoaDonChiTiet> getAllHDCTByIdHD(int idHD);
}
