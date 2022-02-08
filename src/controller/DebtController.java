/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.CategoryDAO;
import access.DebtDAO;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Debt;
import structures.MyArrayList;
import structures.MyLinkedList;
import structures.StackArray;
import structures.StackLinked;
import view.DebtView;
import model.Record;
import structures.MyAVLTree;

/**
 *
 * @author DELL
 */
public class DebtController {
    private static DebtView debtView;
    private static DebtDAO debtDAO;
    private static CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories;
    private static MyAVLTree<Debt> searchDebt;

    private Debt lastTableDebt;
    private static StackArray<Record> undoStack;
    private static StackLinked<Record> redoStack;
    private static MyArrayList<Debt> debts;
    private static Record<Debt> r;
    private static int rowSelected;
    
    public DebtController(DebtView debtView){
        this.debtView=debtView;
        debtDAO=new DebtDAO();
        searchDebt = new MyAVLTree<Debt>();
        categoryDAO=new CategoryDAO();
        categories=categoryDAO.getAllCategory();
        debts=debtDAO.getAllDebt();
        for(int i = 0; i < debts.size(); i++)
            searchDebt.insert(debts.get(i));
        undoStack = new StackArray();
        redoStack = new StackLinked();
    }
    
    public void ActionPerformed(java.awt.event.ActionEvent evt) {  
        if(evt.getSource()==debtView.getBtnMoverAgregar()){
            startComponentsInsert();
            debtView.activarPanelAgregar();
        }else if(evt.getSource()==debtView.getBtnMoverActualizar()){
            startComponentsUpdate();
            debtView.activarPanelActualizar();
        }else if(evt.getSource()==debtView.getBtnRegresarActualizar() || evt.getSource()==debtView.getBtnRegresarAgregar()){
            debtView.activarPanelPrincipalD();
        }else if(evt.getSource()==debtView.getBtnAgregar()){
            insertDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnBorrar()){
            DeleteDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnSearch()){
            String s = debtView.getTxtSearchDebt().getText().toLowerCase();
            if(s.equals("")){
                refreshDebts();
                showDebt();
            }else{
                Debt tempD = new Debt(s);
                tempD = searchDebt.get(tempD);
                debts.clear();
                if(tempD!=null)
                    debts.add(tempD);
                showDebt();
            }
        }else if(evt.getSource()==debtView.getBtnActualizar()){
            updateDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnUndo()){
            r=undoStack.pop();
            if(undoStack.empty())debtView.getBtnUndo().setEnabled(false);
            Debt d = (Debt) r.getEntidad();
            switch(r.getMetodo()){
                case "update":
                    if(redoStack.search(new Record<Debt>("update",debts.get(r.getLastRow()),r.getLastRow()))==0)
                        redoStack.push(new Record<Debt>("update",debts.get(r.getLastRow()),r.getLastRow()));
                    searchDebt.remove(debts.get(r.getLastRow()));
                    debts.set(r.getLastRow(), d);
                    searchDebt.insert(d);
                    break;
                case "insert":
                    debtDAO.deleteByIdDebt(d.getId());
                    if(redoStack.search(new Record<Debt>("delete",d, r.getLastRow()))==0)
                        redoStack.push(new Record<Debt>("delete",d, r.getLastRow())); 
                    searchDebt.remove(d);
                    debts.remove(r.getLastRow());
                    break;
                case "delete":
                    debtDAO.insertDebt(d, true);
                    if(redoStack.search(new Record<Debt>("insert",d,r.getLastRow()))==0)
                        redoStack.push(new Record<Debt>("insert",d,r.getLastRow()));
                    debts.add(r.getLastRow(), d);
                    searchDebt.insert(d);
                    break;
                default:
            }
            if(!redoStack.empty())debtView.getBtnRedo().setEnabled(true);
            showDebt();
            CategoryController.refreshCategory();
        }else if(evt.getSource()==debtView.getBtnRedo()){
            r=redoStack.pop();
            if(redoStack.empty())debtView.getBtnRedo().setEnabled(false);
            Debt d = (Debt) r.getEntidad();
            switch(r.getMetodo()){
                case "update":
                    if(undoStack.search(new Record<Debt>("update",debts.get(r.getLastRow()),r.getLastRow()))==0)
                        undoStack.push(new Record<Debt>("update",debts.get(r.getLastRow()),r.getLastRow()));
                    searchDebt.remove(debts.get(r.getLastRow()));
                    debts.set(r.getLastRow(), d);
                    searchDebt.insert(d);
                    break;
                case "insert":
                    debtDAO.deleteByIdDebt(d.getId());
                    if(undoStack.search(new Record<Debt>("delete",d, r.getLastRow()))==0)
                        undoStack.push(new Record<Debt>("delete",d, r.getLastRow()));
                    debts.remove(r.getLastRow());
                    searchDebt.remove(d);
                    break;
                case "delete":
                    debtDAO.insertDebt(d, true);
                    if(undoStack.search(new Record<Debt>("insert",d, r.getLastRow()))==0)
                        undoStack.push(new Record<Debt>("insert",d, r.getLastRow()));
                    debts.add(r.getLastRow(), d);
                    searchDebt.insert(d);
                    break;
                default:
            }
            showDebt();
            if(!undoStack.empty())debtView.getBtnUndo().setEnabled(true);
            CategoryController.refreshCategory();
        }
        //System.out.println("undo "+undoStack);
        //System.out.println("redo "+redoStack);
    }
    
    public void DeleteDebt(){
        Debt d = new Debt(lastTableDebt);
        undoStack.push(new Record<Debt>("delete", d, rowSelected));
        emptyRedoStack();
        searchDebt.remove(debts.get(rowSelected));
        debts.remove(rowSelected);
        debtDAO.deleteByIdDebt(lastTableDebt.getId());
        CategoryController.refreshCategory();
    }
    
    public static void clearTextSearch(){
        debtView.getTxtSearchDebt().setText("");
    }
    
    public void emptyRedoStack(){
        while(!redoStack.empty()){
            redoStack.pop();
        }
        debtView.getBtnRedo().setEnabled(false);
    }
    
    public static void showDebt(){
        String[] c={"Nombre","Dinero a pagar","Numero de cuotas","Fecha de inicio","Descripcion","Porcentaje de avance"};
        debtView.getTableDebts().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)debtView.getTableDebts().getModel();
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
        debtView.getTableDebts().setModel(tb);
    }
    
    public static void refreshDebts() {
        debts=debtDAO.getAllDebt();
        searchDebt.makeEmpy();
        for(int i = 0; i < debts.size(); i++)
            searchDebt.insert(debts.get(i));
    }
    
    public void TableMouseClicked(MouseEvent evt) {
        rowSelected=debtView.getTableDebts().getSelectedRow();
        lastTableDebt=debts.get(rowSelected);
        if(Integer.parseInt(debtView.getTableDebts().getValueAt(rowSelected,5).toString())==100){
            debtView.botonesPorcentaje();
        }else{
            debtView.habilitarBotones();        
        }
    }
    
    public static void deshabilitarBotonesTablaDebt(){
        debtView.deshabilitarBotones();
    }

    public void startComponentsInsert() {
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaAgregar().removeAllItems();
        for(int i=0; i<categories.size();i++)
            debtView.getCbxCategoriaAgregar().addItem(categories.get(i).getName());
        debtView.getCbxCategoriaAgregar().repaint();
    }
    
    public void startComponentsUpdate() {
        int idCbx = 0;
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaActualizar().removeAllItems();
        for(int i=0; i<categories.size();i++){
            debtView.getCbxCategoriaActualizar().addItem(categories.get(i).getName());
            if(lastTableDebt.getIdCategory()==categories.get(i).getId())
                idCbx=i;
        }
        debtView.getCbxCategoriaActualizar().repaint();
        debtView.getCbxCategoriaActualizar().setSelectedIndex(idCbx);
        debtView.getTxtDescripcionActualizar().setText(lastTableDebt.getDescription());
        debtView.getTxtFechaInicialActualizar().setText(lastTableDebt.getStartDate());
        debtView.getTxtNombreActualizar().setText(lastTableDebt.getName());
        debtView.getTxtNumCuotasActualizar().setText(lastTableDebt.getNumQuotas()+"");
        debtView.getTxtValorActualizar().setText(df.format(lastTableDebt.getMoneyToPaid())+"");
        debtView.getCbxPeriodicidadActualizar().setSelectedItem(lastTableDebt.getPeriodicity());
        debtView.getPanelActualizarD().repaint();
    }

    public void insertDebt() {
        String name = debtView.getTxtNombreAgregar().getText();
        float valor = Float.parseFloat(debtView.getTxtValorAgregar().getText());
        String fecha = debtView.getTxtFechaInicialAgregar().getText();
        int cuotas = Integer.parseInt(debtView.getTxtNumCuotasAgregar().getText());
        String periodicidad = debtView.getCbxPeriodicidadAgregar().getSelectedItem().toString();
        int idCategory = categories.get(debtView.getCbxCategoriaAgregar().getSelectedIndex()).getId();
        String descripcion = debtView.getTxtDescripcionAgregar().getText();
        Debt d= new Debt(DebtDAO.getLastIndex(),name,valor,fecha,cuotas,periodicidad,descripcion,idCategory,0);
        undoStack.push(new Record<Debt>("insert", d, debts.size()));
        emptyRedoStack();
        debtDAO.insertDebt(d, true);
        debts.add(d);
        searchDebt.insert(d);
        JOptionPane.showMessageDialog(debtView, "Deuda agregada exitosamente");
        debtView.activarPanelPrincipalD();
        debtView.cleanFieldsAgregar();
        CategoryController.refreshCategory();
    }
    
    public void updateDebt(){
        String name = debtView.getTxtNombreActualizar().getText();
        float valor = Float.parseFloat(debtView.getTxtValorActualizar().getText());
        String fecha = debtView.getTxtFechaInicialActualizar().getText();
        int cuotas = Integer.parseInt(debtView.getTxtNumCuotasActualizar().getText());
        String periodicidad = debtView.getCbxPeriodicidadActualizar().getSelectedItem().toString();
        int idCategory = categories.get(debtView.getCbxCategoriaActualizar().getSelectedIndex()).getId();
        String descripcion = debtView.getTxtDescripcionActualizar().getText();
        Debt temp=new Debt(lastTableDebt.getId(), name, valor, fecha, cuotas, periodicidad, descripcion, idCategory, 0);
        undoStack.push(new Record<Debt>("update", lastTableDebt, rowSelected));
        emptyRedoStack();
        debtDAO.updateDebt(temp);
        searchDebt.remove(debts.get(rowSelected));
        debts.set(rowSelected, temp);
        searchDebt.insert(temp);
        JOptionPane.showMessageDialog(debtView, "Deuda actualizada exitosamente");
        debtView.activarPanelPrincipalD();
    }  
}
