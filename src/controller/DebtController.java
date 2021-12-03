/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.CategoryDAO;
import access.DebtDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

/**
 *
 * @author DELL
 */
public class DebtController {
    private static DebtView debtView;
    private static DebtDAO debtDAO;
    private static CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories;
    private Debt lastTableDebt;
    private static StackArray<Record> undoStack;
    private static StackLinked<Record> redoStack;
    private static MyArrayList<Debt> debts;
    private static Record<Debt> r;
    
    public DebtController(DebtView debtView){
        this.debtView=debtView;
        this.debtDAO=new DebtDAO();
        this.categoryDAO=new CategoryDAO();
        undoStack = new StackArray();
        redoStack = new StackLinked();
    }
    
    public void MoveActionPerformed(java.awt.event.ActionEvent evt) {  
        if(evt.getSource()==debtView.getBtnMoverAgregar()){
            startComponentsAgregar();
            debtView.activarPanelAgregar();
        }else if(evt.getSource()==debtView.getBtnMoverActualizar()){
            startComponentsActualizar();
            debtView.activarPanelActualizar();
        }else if(evt.getSource()==debtView.getBtnRegresarActualizar() || evt.getSource()==debtView.getBtnRegresarAgregar()){
            debtView.activarPanelPrincipalD();
        }else if(evt.getSource()==debtView.getBtnAgregar()){
            AgregarDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnBorrar()){
            DeleteDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnActualizar()){
            UpdateDebt();
            showDebt();
        }else if(evt.getSource()==debtView.getBtnUndo()){
            r=undoStack.pop();
            if(undoStack.empty())debtView.getBtnUndo().setEnabled(false);
            Debt d = (Debt) r.getEntidad();
            switch(r.getMetodo()){
                case "update" -> {
                    
                }
                case "insert" -> {
                    debtDAO.deleteByIdDebt(d.getId());
                    redoStack.push(new Record<Debt>("delete",d)); 
                }
                case "delete" -> {
                    debtDAO.insertDebt(d, true);
                    redoStack.push(new Record<Debt>("insert",d));
                }
                default->{}
            }
            if(!redoStack.empty())debtView.getBtnRedo().setEnabled(true);
            showDebt();
        }else if(evt.getSource()==debtView.getBtnRedo()){
            r=redoStack.pop();
            if(redoStack.empty())debtView.getBtnRedo().setEnabled(false);
            Debt d = (Debt) r.getEntidad();
            switch(r.getMetodo()){
                case "update" -> {
                    
                }
                case "insert" -> {
                    debtDAO.deleteByIdDebt(d.getId());
                    undoStack.push(new Record<Debt>("delete",d));
                    
                }
                case "delete" -> {
                    debtDAO.insertDebt(d, true);
                    undoStack.push(new Record<Debt>("insert",d));
                }
                default->{}
            }
            showDebt();
            if(!undoStack.empty())debtView.getBtnUndo().setEnabled(true);
        }
        //System.out.println("undo "+undoStack);
        //System.out.println("redo "+redoStack);
    }
    
    public void DeleteDebt(){
        undoStack.push(new Record<Debt>("delete", new Debt(lastTableDebt)));
        debtDAO.deleteByIdDebt(lastTableDebt.getId());
    }
    
    public static void showDebt(){
        String[] c={"Nombre","Dinero a pagar","Numero de cuotas","Fecha de inicio","Descripcion","Porcentaje de avance"};
        debtView.getTableDebts().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)debtView.getTableDebts().getModel();
        Object[] row=new Object[6];
        debts=debtDAO.getAllDebt();
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
    
    public void TableMouseClicked(MouseEvent evt) {
        debtView.habilitarBotones();
        int row=debtView.getTableDebts().getSelectedRow();
        lastTableDebt=debts.get(row);
    }

    public void startComponentsAgregar() {
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaAgregar().removeAllItems();
        for(int i=0; i<categories.size();i++)
            debtView.getCbxCategoriaAgregar().addItem(categories.get(i).getName());
        debtView.getCbxCategoriaAgregar().repaint();
    }
    
    public void startComponentsActualizar() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaActualizar().removeAllItems();
        for(int i=0; i<categories.size();i++)
            debtView.getCbxCategoriaActualizar().addItem(categories.get(i).getName());
        debtView.getCbxCategoriaActualizar().repaint();
        debtView.getCbxCategoriaActualizar().setSelectedIndex(lastTableDebt.getIdCategory());
        debtView.getTxtDescripcionActualizar().setText(lastTableDebt.getDescription());
        debtView.getTxtFechaInicialActualizar().setText(lastTableDebt.getStartDate());
        debtView.getTxtNombreActualizar().setText(lastTableDebt.getName());
        debtView.getTxtNumCuotasActualizar().setText(lastTableDebt.getNumQuotas()+"");
        debtView.getTxtValorActualizar().setText(df.format(lastTableDebt.getMoneyToPaid())+"");
        debtView.getCbxPeriodicidadActualizar().setSelectedItem(lastTableDebt.getPeriodicity());
        debtView.getPanelActualizarD().repaint();
    }

    public void AgregarDebt() {
        String name = debtView.getTxtNombreAgregar().getText();
        float valor = Float.parseFloat(debtView.getTxtValorAgregar().getText());
        String fecha = debtView.getTxtFechaInicialAgregar().getText();
        int cuotas = Integer.parseInt(debtView.getTxtNumCuotasAgregar().getText());
        String periodicidad = debtView.getCbxPeriodicidadAgregar().getSelectedItem().toString();
        int idCategory = categories.get(debtView.getCbxCategoriaAgregar().getSelectedIndex()).getId();
        String descripcion = debtView.getTxtDescripcionAgregar().getText();
        Debt d= new Debt(0,name,valor,fecha,cuotas,periodicidad,descripcion,idCategory,0);
        undoStack.push(new Record<Debt>("insert", d));
        debtDAO.insertDebt(d, false);
        JOptionPane.showMessageDialog(debtView, "Deuda agregada exitosamente");
        debtView.activarPanelPrincipalD();
        debtView.cleanFieldsAgregar();
    }
    
    public void UpdateDebt(){
        String name = debtView.getTxtNombreActualizar().getText();
        float valor = Float.parseFloat(debtView.getTxtValorActualizar().getText());
        String fecha = debtView.getTxtFechaInicialActualizar().getText();
        int cuotas = Integer.parseInt(debtView.getTxtNumCuotasActualizar().getText());
        String periodicidad = debtView.getCbxPeriodicidadActualizar().getSelectedItem().toString();
        int idCategory = categories.get(debtView.getCbxCategoriaActualizar().getSelectedIndex()).getId();
        String descripcion = debtView.getTxtDescripcionActualizar().getText();
        Debt temp=new Debt(lastTableDebt.getId(), name, valor, fecha, cuotas, periodicidad, descripcion, idCategory, lastTableDebt.getPercent());
        debtDAO.updateDebt(temp);
    }  
}
