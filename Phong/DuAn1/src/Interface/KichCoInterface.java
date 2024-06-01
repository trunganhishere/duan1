/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.KichCo;

public interface KichCoInterface {

    public List<KichCo> getAll();

    public boolean add(KichCo kichCo);

    public boolean update(KichCo cl, int id);
      boolean existsByName(String name);

}
