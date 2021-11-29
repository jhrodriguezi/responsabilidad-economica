/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.DebtDAO;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Debt;
import structures.ArrList;
import view.DebtView;

/**
 *
 * @author DELL
 */
public class DebtController {
    private DebtView debtView;
    private DebtDAO debtDAO;
    private Debt debt;
    private static ArrList<Debt> debts;
    
    public DebtController(DebtView debtView){
        this.debtView=debtView;
        this.debtDAO=new DebtDAO();
    }
    
    public void MoveActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if(evt.getSource()==debtView.getBtnMoverAgregar()){
            debtView.activarPanelAgregar();
        }else if(evt.getSource()==debtView.getBtnMoverActualizar()){
            debtView.activarPanelActualizar();
        }else if(evt.getSource()==debtView.getBtnRegresarActualizar() || evt.getSource()==debtView.getBtnRegresarAgregar()){
            debtView.activarPanelPrincipalD();
        }
    }
    
    public void DeleteActionPerformed(java.awt.event.ActionEvent evt){
        if(evt.getSource()==debtView.getBtnBorrar()){
            
        }
    }
    
    public void showDebt(){
        String[] c={"Nombre","Dinero a pagar","Numero de cuotas","Fecha de inicio","Descripcion","Porcentaje de avance"};
        debtView.getTableDebts().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)debtView.getTableDebts().getModel();
        Object[] row=new Object[6];
        this.debts=debtDAO.getAllDebt();
        for(int i=0; i<debts.size();i++){
            row[0]=debts.get(i).getName();
            row[1]=debts.get(i).getMoneyToPaid();
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
    
}
