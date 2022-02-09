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
import model.Record;
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
    private Record<Category> r;

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
            if(validateData('I')){
                insertCategory();
                categoryView.habilitarPanelCategoria();
                showCategory();
            }
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
            if(validateData('U')){
                Category c = categories.get(selectedRow);
                undoStack.push(new Record<Category>("update", c, selectedRow));
                emptyRedoStack();
                categoryView.getBtnUndo().setEnabled(true);
                Category newC = new Category(c.getId(), categoryView.getTxtNombreActualizar().getText(), c.getCountDebt(), c.getActiveDebt());
                categoryDAO.updateCategory(newC);
                refreshCategory();
                showCategory();
                JOptionPane.showMessageDialog(categoryView, "Categoria actualizada exitosamente");
                categoryView.habilitarPanelCategoria();
            }
        }else if(evt.getSource()==categoryView.getBtnRedo()){
            r=redoStack.pop();
            if(redoStack.empty())categoryView.getBtnRedo().setEnabled(false);
            Category c = (Category) r.getEntidad();
            switch(r.getMetodo()){
                case "update":
                    categoryDAO.updateCategory(c);
                    if(undoStack.search(new Record<Category>("update",categories.get(r.getLastRow()),r.getLastRow()))==0)
                        undoStack.push(new Record<Category>("update",categories.get(r.getLastRow()),r.getLastRow()));
                    categories.set(r.getLastRow(), c);
                    break;
                case "insert":
                    categoryDAO.deleteCategory(c);
                    if(undoStack.search(new Record<Category>("delete",c, r.getLastRow()))==0)
                        undoStack.push(new Record<Category>("delete",c, r.getLastRow()));
                    categories.remove(r.getLastRow());
                    break;
                case "delete":
                    categoryDAO.insertCategory(c);
                    if(undoStack.search(new Record<Category>("insert",c, r.getLastRow()))==0)
                        undoStack.push(new Record<Category>("insert",c, r.getLastRow()));
                    categories.add(r.getLastRow(), c);
                    break;
                default:
            }
            if(!undoStack.empty())categoryView.getBtnUndo().setEnabled(true);
            showCategory();
        }else if(evt.getSource()==categoryView.getBtnUndo()){
            r=undoStack.pop();
            if(undoStack.empty())categoryView.getBtnUndo().setEnabled(false);
            Category c = (Category) r.getEntidad();
            switch(r.getMetodo()){
                case "update":
                    categoryDAO.updateCategory(c);
                    if(redoStack.search(new Record<Category>("update",categories.get(r.getLastRow()),r.getLastRow()))==0)
                        redoStack.push(new Record<Category>("update",categories.get(r.getLastRow()),r.getLastRow()));
                    categories.set(r.getLastRow(), c);
                    System.out.println(redoStack);
                    break;
                case "insert":
                    categoryDAO.deleteCategory(c);
                    if(redoStack.search(new Record<Category>("delete",c, r.getLastRow()))==0)
                        redoStack.push(new Record<Category>("delete",c, r.getLastRow())); 
                    categories.remove(r.getLastRow());
                    break;
                case "delete":
                    categoryDAO.insertCategory(c);
                    if(redoStack.search(new Record<Category>("insert",c,r.getLastRow()))==0)
                        redoStack.push(new Record<Category>("insert",c,r.getLastRow()));
                    categories.add(r.getLastRow(), c);
                    break;
                default:
            }
            if(!redoStack.empty())categoryView.getBtnRedo().setEnabled(true);
            showCategory();
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
    
    public void emptyRedoStack(){
        while(!redoStack.empty()){
            redoStack.pop();
        }
        categoryView.getBtnRedo().setEnabled(false);
    }

    public void deleteCategory() {
        Category c = categories.remove(selectedRow);
        undoStack.push(new Record<Category>("delete", c, selectedRow));
        emptyRedoStack();
        categoryView.getBtnUndo().setEnabled(true);
        categoryDAO.deleteCategory(c);
        refreshCategory();
        showCategory();
    }
    
    public void insertCategory() {
            String name = categoryView.getTxtNombreAgregar().getText();
            Category c= new Category(CategoryDAO.getLastIndex(),name,0,0);
            undoStack.push(new Record<Category>("insert", c, categories.size()));
            emptyRedoStack();
            categoryView.getBtnUndo().setEnabled(true);
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
    
    public boolean validateData(char c){
        String name = "";
        if(c=='U'){
            name = categoryView.getTxtNombreActualizar().getText();
            if(selectedRow<0){
                JOptionPane.showMessageDialog(categoryView, "Ha ocurrido un problema al actualizar la categoria.");
                return false;
            }
            
        }else if(c=='I'){
            name = categoryView.getTxtNombreAgregar().getText();
        }
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getName().equals(name)){
                JOptionPane.showMessageDialog(categoryView, "El nombre de esa categorias ya existe, recuerde que el programa no distingue entre mayusculas y minusculas.");
                return false;
            }
        }
        
        return true;
    }
}
