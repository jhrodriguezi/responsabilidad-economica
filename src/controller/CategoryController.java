/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.CategoryDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import model.Category;
import structures.MyLinkedList;
import view.CategoryView;

/**
 *
 * @author DELL
 */
public class CategoryController {
    private CategoryView categoryView;
    private CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories=new MyLinkedList();
    
    public CategoryController(CategoryView categoryView){
        this.categoryView = categoryView;
        this.categoryDAO = new CategoryDAO();
    }

    public void showCategory() {
        String[] c={"Nombre"};
        categoryView.getTableCategories().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)categoryView.getTableCategories().getModel();
        Object[] row=new Object[1];
        this.categories=categoryDAO.getAllCategory();
        for(int i=0; i<categories.size();i++){
            row[0]=categories.get(i).getName();
            tb.addRow(row);
        }
        categoryView.getTableCategories().setModel(tb);
    }

    public void DeleteCategory(ActionEvent evt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void TableMouseClicked(MouseEvent evt) {
        categoryView.habilitarBotones();
    }
}
