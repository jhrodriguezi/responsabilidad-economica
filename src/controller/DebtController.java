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
import view.DebtView;

/**
 *
 * @author DELL
 */
public class DebtController {
    private static DebtView debtView;
    private static DebtDAO debtDAO;
    private static CategoryDAO categoryDAO;
    private static MyLinkedList<Category> categories;
    private Debt debt;
    private static MyArrayList<Debt> debts;
    
    public DebtController(DebtView debtView){
        this.debtView=debtView;
        this.debtDAO=new DebtDAO();
        this.categoryDAO=new CategoryDAO();
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
        }
    }
    
    public void DeleteActionPerformed(java.awt.event.ActionEvent evt){
        if(evt.getSource()==debtView.getBtnBorrar()){
            
        }
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
        /*
        int row=clienteView.getCli_Table().getSelectedRow();
        if(row!=-1 && clienteView.getCbxCli_acciones().getSelectedItem().equals("Actualizar cliente")){
            clienteView.getTxtCli_id().setText(""+clienteView.getCli_Table().getValueAt(row,0));
            clienteView.getTxtCli_docId().setText(""+clienteView.getCli_Table().getValueAt(row,1));
            clienteView.getTxtCli_nombre().setText((String)clienteView.getCli_Table().getValueAt(row,2));
            clienteView.getTxtCli_apellido().setText((String)clienteView.getCli_Table().getValueAt(row,3));
            clienteView.getTxtCli_correo().setText((String)clienteView.getCli_Table().getValueAt(row,4));
            clienteView.getTxtCli_telefono().setText((String)clienteView.getCli_Table().getValueAt(row,5));
        }else if(row!=-1 && clienteView.getCbxCli_acciones().getSelectedItem().equals("Eliminar cliente")){
            clienteView.getTxtCli_id().setText(""+clienteView.getCli_Table().getValueAt(row,0));
        }*/
    }

    public void startComponentsAgregar() {
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaAgregar().removeAllItems();
        for(int i=0; i<categories.size();i++)
            debtView.getCbxCategoriaAgregar().addItem(categories.get(i).getName());
        debtView.getCbxCategoriaAgregar().repaint();
    }
    
    public void startComponentsActualizar() {
        categories=categoryDAO.getAllCategory();
        debtView.getCbxCategoriaActualizar().removeAllItems();
        for(int i=0; i<categories.size();i++)
            debtView.getCbxCategoriaActualizar().addItem(categories.get(i).getName());
        debtView.getCbxCategoriaActualizar().repaint();
    }

    public void AgregarDebt() {
        String name = debtView.getTxtNombreAgregar().getText();
        float valor = Float.parseFloat(debtView.getTxtValorAgregar().getText());
        String fecha = debtView.getTxtFechaInicialAgregar().getText();
        int cuotas = Integer.parseInt(debtView.getTxtNumCuotasAgregar().getText());
        String periodicidad = debtView.getCbxPeriodicidadAgregar().getSelectedItem().toString();
        int idCategory = categories.get(debtView.getCbxCategoriaAgregar().getSelectedIndex()).getId();
        String descripcion = debtView.getTxtDescripcionAgregar().getText();
        debtDAO.insertDebt(new Debt(0,name,valor,fecha,cuotas,periodicidad,descripcion,idCategory,0));
        JOptionPane.showMessageDialog(debtView, "Deuda agregada exitosamente");
        debtView.activarPanelPrincipalD();
        debtView.cleanFieldsAgregar();
    }
    
}
