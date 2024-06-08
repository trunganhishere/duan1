/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import java.util.List;
import model.ChatLieu;


public interface ChatLieuInterface {
    public List<ChatLieu> getAll();
    public boolean add(ChatLieu ChatLieu);
    public boolean update (ChatLieu cl, int id);
    boolean existsByName(String name);
   
}
