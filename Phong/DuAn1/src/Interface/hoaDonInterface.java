/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.HoaDon;


public interface hoaDonInterface {
    public List<HoaDon> getAll();
    public boolean addHoaDon(String maHD,int idNV);
    public boolean updateMaKHforHoaDon(int idKh,int idHD);
    public boolean updateTongTien(int idHD,double tongTien);
    public boolean updateSoLuongSP(int id, int soLuong);
    public List<HoaDon> getAllHDChuaTT();
    public boolean updateThanhToan(int idHD);
}
