/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package responsabilidadeconomica;

import java.io.File;

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
            File f = new File("data/");
            if(!f.exists()){
                f.mkdir();
            }
            new File("data/debts.json").createNewFile();
            new File("data/categories.json").createNewFile();

        }catch(Exception e){
            System.out.println("message: "+e.getMessage());
        }
        

    }
}
