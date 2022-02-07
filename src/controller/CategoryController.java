/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.CategoryDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Debt;
import structures.MyArrayList;
import structures.MyLinkedHashMap;
import structures.MyLinkedList;
import structures.StackArray;
import view.CategoryView;

/**
 *
 * @author DELL
 */
public class CategoryController {
    private static CategoryView categoryView;
    private static CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories;
    private static StackArray<Record> undoStack;
    private static StackArray<Record> redoStack;
    private static int selectedRow;

    public CategoryController(CategoryView categoryView){
        this.categoryView = categoryView;
        this.categoryDAO = new CategoryDAO();
        undoStack = new StackArray<>();
        redoStack = new StackArray<>();
        categories=categoryDAO.getAllCategory();
        selectedRow = -1;
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
        }else if(evt.getSource()==categoryView.getBtnCerrarVerDeudas()){
            categoryView.habilitarPanelCategoria();
        }else if(evt.getSource()==categoryView.getBtnVerDeudas()){
            categoryView.habilitarPanelVerDeudas();
            if(selectedRow>=0)
                showDebtsByCategory();
        }else if(evt.getSource()==categoryView.getBtnBorrarCat()){
            deleteCategory();
            showCategory();
        }else if(evt.getSource()==categoryView.getBtnActualizarCat()){
            categoryView.habilitarPanelActualizar();
            if(selectedRow>=0){
                Category c = categories.get(selectedRow);
                categoryView.getTxtNombreActualizar().setText(c.getName());
            }
        }else if(evt.getSource()==categoryView.getBtnCancelarUC()){
            categoryView.habilitarPanelCategoria();
        }else if(evt.getSource()==categoryView.getBtnActualizarC()){
            if(selectedRow>=0){
                Category c = categories.get(selectedRow);
                c.setName(categoryView.getTxtNombreActualizar().getText());
                CategoryDAO.updateCategory(c);
                refreshCategory();
                showCategory();
                JOptionPane.showMessageDialog(categoryView, "Categoria actualizada exitosamente");
                categoryView.habilitarPanelCategoria();
            }
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
    
    public void showDebtsByCategory(){
        MyLinkedHashMap<Category, MyArrayList<Debt>> debtsByCategory;
        debtsByCategory=categoryDAO.getAllDebtsByCategory();
        MyArrayList<Debt> debts = debtsByCategory.get(categories.get(selectedRow));
        String[] c={"Nombre","Dinero a pagar","Numero de cuotas","Fecha de inicio","Descripcion","Porcentaje de avance"};
        categoryView.getTableVerDebts().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)categoryView.getTableVerDebts().getModel();
        Object[] row=new Object[6];
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        for(int i=0; i<debts.size();i++){
            row[0]=debts.get(i).getName();
            row[1]=df.format(debts.get(i).getMoneyToPaid());
            row[2]=debts.get(i).getNumQuotas();
            row[3]=debts.get(i).getStartDate();
            row[4]=debts.get(i).getDescription();
            row[5]=debts.get(i).getPercent();
            tb.addRow(row);
        }
        categoryView.getTableVerDebts().setModel(tb);
    }

    public void deleteCategory() {
        Category c = categories.remove(selectedRow);
        categoryDAO.deteleCategory(c);
        refreshCategory();
        showCategory();
    }
    
    public void insertCategory() {
        String name = categoryView.getTxtNombreAgregar().getText();
        Category c= new Category(CategoryDAO.getLastIndex(),name,0,0);
        categoryDAO.insertCategory(c);
        categories.add(c);
        JOptionPane.showMessageDialog(categoryView, "Categoria agregada exitosamente");
        categoryView.cleanFieldsAgregar();
    }

    public void TableMouseClicked(MouseEvent evt) {
        categoryView.habilitarBotones();
        selectedRow = categoryView.getTableCategories().getSelectedRow();
        if(selectedRow==0)
            categoryView.habilitarVerDeudas();
    }
    
    public static void deshabilitarBotonesTablaCategory(){
        categoryView.deshabilitarBotones();
    }
}
