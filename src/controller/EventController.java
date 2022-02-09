/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.DebtDAO;
import access.EventDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Debt;
import model.Event;
import structures.MyBinaryHeap;
import structures.MyArrayList;
import structures.MyLinkedList;
import structures.QueueArray;
import structures.QueueArrayCircular;
import view.EventView;
import view.Main;

/**
 *
 * @author DELL
 */
public class EventController {
    public static MyBinaryHeap<Event> events;
    private static EventView eventView;
    private static EventDAO eventDAO;
    private static MyArrayList<Debt> debts;
    private static DebtDAO debtDAO;
    private int selectedRow;
    
    public EventController(EventView eventView){
        this.eventView=eventView;
        this.eventDAO=new EventDAO();
        events=eventDAO.getAllEvent();
        debtDAO=new DebtDAO();
        selectedRow=-1;
    }
    
    public void ActionPerformed(java.awt.event.ActionEvent evt) {  
        if(evt.getSource()==eventView.getBtnConsultarPorDeuda()){
            eventView.deshabilitarBotones();
            if(eventView.getCbxDeudas().getSelectedIndex()>=0){
                events=eventDAO.getByDebtEvent(debts.get(eventView.getCbxDeudas().getSelectedIndex()));
                showEvent();
            }
        }
    
    }
    
    public static void refreshEvent(){
        events=eventDAO.getAllEvent();
    }
    
    public static void showEvent(){
        String[] c={"Valor a pagar","Fecha","Numero de cuota"};
        eventView.getTableEvents().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)eventView.getTableEvents().getModel();
        Object[] row=new Object[3];
        if(!events.isEmpty()){
            Event event = events.findMin();
            DecimalFormat df = new DecimalFormat("#");
            df.setMaximumFractionDigits(2);
            row[0]=df.format(event.getValue());
            row[1]=event.getDate();
            row[2]=event.getQuota();
            tb.addRow(row);
        }
        eventView.getTableEvents().setModel(tb);
    }
    
    public static boolean areThereEvents(){
        return !events.isEmpty();
    }
    
    public static void showEventMainPanel(){
        Main main = MainController.getMainView();
        if(!events.isEmpty()){
            Debt debt = new DebtDAO().getByIdDebt(events.findMin().getIdDebt());
            main.getLblAvance().setText("Avance en la deuda asociada a este fraccionamiento: "+debt.getPercent()+"%");
            main.getLblCuotaCont().setText(""+events.findMin().getQuota());
            main.getLblFechaCont().setText(events.findMin().getDate());
            main.getLblValorCont().setText(""+events.findMin().getValue());
            main.getLblNombreCont().setText(debt.getName());
            main.repaint();
        }else{
            main.cambiarAPregunta();
        }
    }
    
    public static void startComponentsDeuda() {
        debts=debtDAO.getAllDebtWithOut100Percent();
        eventView.getCbxDeudas().removeAllItems();
        for(int i=0; i<debts.size();i++)
            if(debts.get(i).getPercent()!=100)
                eventView.getCbxDeudas().addItem(debts.get(i).getName());
        eventView.getCbxDeudas().repaint();
    }
    
    public static void deleteEvent(){
        Event event = events.deleteMin();
        eventDAO.deleteEvent(event);
        Debt debt = new DebtDAO().getByIdDebt(event.getIdDebt());
        MainController.getMainView().getLblAvance().setText("Avance en la deuda asociada a este fraccionamiento: "+debt.getPercent()+"%");
        if(100-debt.getPercent()==0)
            JOptionPane.showMessageDialog(MainController.getMainView(), "¡Felicidades! ha terminado de pagar la deuda llamada "+debt.getName());
        else
            JOptionPane.showMessageDialog(MainController.getMainView(), "Ahora te falta el "+(100-debt.getPercent())+"% para terminar de pagar la deuda llamada "+debt.getName());
    }

    public void CompleteEvent(ActionEvent evt) {
        Event e = events.deleteMin();
        eventDAO.deleteEvent(e);
        startComponentsDeuda();
        showEvent();
    }

    public void TableMouseClicked(MouseEvent evt) {
        eventView.habilitarBotones();
        selectedRow = eventView.getTableEvents().getSelectedRow();
    }
    
    public static void deshabilitarBotonesTablaEvent(){
        eventView.deshabilitarBotones();
    }
}
