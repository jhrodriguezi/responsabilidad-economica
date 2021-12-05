/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.CategoryDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Category;
import structures.MyLinkedList;
import view.CategoryView;

/**
 *
 * @author DELL
 */
public class CategoryController {
    private static CategoryView categoryView;
    private static CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories;

    public CategoryController(CategoryView categoryView){
        this.categoryView = categoryView;
        this.categoryDAO = new CategoryDAO();
        categories=categoryDAO.getAllCategory();
    }
    
    public void ActionPerformed(ActionEvent evt){
        if(evt.getSource()==categoryView.getBtnAgregarCat()){
            categoryView.habilitarPanelAgregar();
        }else if(evt.getSource()==categoryView.getBtnCancelarAC()){
            categoryView.habilitarPanelCategoria();
        }else if(evt.getSource()==categoryView.getBtnAgregarC()){
            insertCategory();
            categoryView.habilitarPanelCategoria();
            showCategory();
        }else if(evt.getSource()==categoryView.getBtnCancelarAC()){
            categoryView.habilitarPanelCategoria();
        }
    }
    
    public static void refreshCategory(){
        categories=categoryDAO.getAllCategory();
    }

    public static void showCategory() {
        String[] c={"Nombre", "Conteo de deudas", "Deudas activas"};
        categoryView.getTableCategories().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)categoryView.getTableCategories().getModel();
        Object[] row=new Object[3];
        for(int i=0; i<categories.size();i++){
            row[0]=categories.get(i).getName();
            row[1]=categories.get(i).getCountDebt();
            row[2]=categories.get(i).getActiveDebt();
            tb.addRow(row);
        }
        categoryView.getTableCategories().setModel(tb);
    }

    public void deleteCategory() {
        
    }
    
    public void insertCategory() {
        String name = categoryView.getTxtNombreAgregar().getText();
        Category c= new Category(CategoryDAO.getLastIndex(),name,0,0);
        categoryDAO.insertCategory(c);
        categories.add(c);
        JOptionPane.showMessageDialog(categoryView, "Categoria agregada exitosamente");
        categoryView.cleanFieldsAgregar();
    }
    
    public void updateCateogory(){
    
    }

    public void TableMouseClicked(MouseEvent evt) {
        categoryView.habilitarBotones();
    }
}
