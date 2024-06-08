/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.ThuongHieu;


public interface ThuongHieuInterface {
    public List<ThuongHieu> getAll();
     public boolean add(ThuongHieu ThuongHieu);
    public boolean update (ThuongHieu cl, int id);
     boolean existsByName(String name);

}
