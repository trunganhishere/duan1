/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.KhuyenMai;

/**
 *
 * @author Luu Huynh
 */
public interface KhuyenMaiInterface {

    public List<KhuyenMai> getAll();

    public void add(KhuyenMai km);

    public void update(KhuyenMai km);
}
