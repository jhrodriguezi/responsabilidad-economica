/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package responsabilidadeconomica;

import java.io.File;
import java.io.FileWriter;
import model.Category;
import access.CategoryDAO;
import java.io.IOException;
import view.Main;

public class ResponsabilidadEconomica {

    public static void main(String[] args) {
        try{
            File f = new File("data");
            FileWriter fw;
            if(!f.exists()){
                f.mkdir();
            }
            f=new File("data/lastIndex.json");
            if(!f.exists()){
                f.createNewFile();
                fw=new FileWriter(f);
                fw.write("{\"idDebt\":0,\"idEvent\":0,\"idCategory\":0}");
                f.setWritable(false);
                fw.flush();
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
                    if(s.equals("categories")){
                        Category cat = new Category(0, "Sin categoria", 0, 0);
                        CategoryDAO cDAO = new CategoryDAO();
                        cDAO.insertCategory(cat);
                    }
                }
            }
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Main().setVisible(true);
                }});
        }catch(IOException e){
            System.out.println("message: "+e.getMessage());
        }
    }
}
