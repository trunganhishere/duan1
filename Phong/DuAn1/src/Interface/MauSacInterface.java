/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import model.MauSac;
import java.util.List;

public interface MauSacInterface {

    public List<MauSac> getAll();

    public boolean add(MauSac MauSac);

    public boolean update(MauSac cl, int id);
    
    boolean existsByName(String name);

}
