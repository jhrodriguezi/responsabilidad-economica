/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package responsabilidadeconomica;

import java.io.File;
import java.io.FileWriter;
import model.Category;
import access.CategoryDAO;

/**
 *
 * @author DELL
 */
public class ResponsabilidadEconomica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            File f = new File("data");
            FileWriter fw;
            if(!f.exists()){
                f.mkdir();
            }
            String[] campos={"debts","categories","events"};
            for(String s: campos){
                f=new File("data/"+s+".json");
                if(!f.exists()){
                    f.createNewFile();
                    fw=new FileWriter(f);
                    fw.write("[ ]");
                    f.setWritable(false);
                    fw.flush();
                }
            }
            f=new File("data/lastIndex.json");
            if(!f.exists()){
                f.createNewFile();
                fw=new FileWriter(f);
                fw.write("{\"idDebt\":0,\"idEvent\":0,\"idCategory\":0}");
                f.setWritable(false);
                fw.flush();
            }
            Category cat = new Category(5, "Sin categoria");
            CategoryDAO cDAO = new CategoryDAO();
            cDAO.insertCategory(cat);
        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
    }
}
