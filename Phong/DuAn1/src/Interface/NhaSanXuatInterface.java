/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.NSX;

public interface NhaSanXuatInterface {

    public List<NSX> getAll();

    public boolean add(NSX NSX);

    public boolean update(NSX nsx, int id);
 boolean existsByName(String name); 
}
