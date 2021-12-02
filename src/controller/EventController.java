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
import structures.MyArrayList;
import structures.MyLinkedList;
import structures.QueueArray;
import view.EventView;
import view.Main;

/**
 *
 * @author DELL
 */
public class EventController {
    private static EventView eventView;
    private static MyLinkedList<Event> events;
    private static EventDAO eventDAO;
    private static QueueArray<Event> eventAsc;
    private static MyArrayList<Debt> debts;
    private static DebtDAO debtDAO;
    
    public EventController(EventView eventView){
        this.eventView=eventView;
        this.eventDAO=new EventDAO();
        events=eventDAO.getAllEvent();
        eventAsc=eventDAO.getAllByFechaAsc(events);
        debtDAO=new DebtDAO();
    }
    
    public static void showEvent(){
        events=eventDAO.getAllEvent();
        String[] c={"Valor a pagar","Fecha","Numero de cuota"};
        eventView.getTableEvents().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)eventView.getTableEvents().getModel();
        Object[] row=new Object[3];
        Event event;
        int size=events.size();
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        for(int i=0; i<size;i++){
            event=events.get(i);
            row[0]=df.format(event.getValue());
            row[1]=event.getDate();
            row[2]=event.getQuota();
            tb.addRow(row);
        }
        eventView.getTableEvents().setModel(tb);
    }
    
    public static boolean areThereEvents(){
        return !eventAsc.isEmpty();
    }
    
    public static void showEventAsc(){
        Main main = MainController.getMainView();
        if(areThereEvents()){
            Debt debt = new DebtDAO().getByIdDebt(eventAsc.peek().getIdDebt());
            main.getLblAvance().setText("Avance en la deuda asociada a este fraccionamiento: "+debt.getPercent()+"%");
            main.getLblCuotaCont().setText(""+eventAsc.peek().getQuota());
            main.getLblFechaCont().setText(eventAsc.peek().getDate());
            main.getLblValorCont().setText(""+eventAsc.peek().getValue());
            main.getLblNombreCont().setText(debt.getName());
            main.repaint();
        }else{
            main.cambiarAPregunta();
        }
    }
    
    public static void startComponentsDeuda() {
        debts=debtDAO.getAllDebt();
        eventView.getCbxDeudas().removeAllItems();
        for(int i=0; i<debts.size();i++)
            if(debts.get(i).getPercent()!=100)
                eventView.getCbxDeudas().addItem(debts.get(i).getName());
        eventView.getCbxDeudas().repaint();
    }
    
    public static void deleteEvent(){
        System.out.println(eventAsc);
        Event event = eventAsc.poll();
        System.out.println(eventAsc);
        eventDAO.deleteEvent(event);
        Debt debt = new DebtDAO().getByIdDebt(event.getIdDebt());
        MainController.getMainView().getLblAvance().setText("Avance en la deuda asociada a este fraccionamiento: "+debt.getPercent()+"%");
        if(100-debt.getPercent()==0)
            JOptionPane.showMessageDialog(MainController.getMainView(), "¡Felicidades! ha terminado de pagar la deuda llamada "+debt.getName());
        else
            JOptionPane.showMessageDialog(MainController.getMainView(), "Ahora te falta el "+(100-debt.getPercent())+"% para terminar de pagar la deuda llamada "+debt.getName());
    }

    public void CompleteEvent(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void TableMouseClicked(MouseEvent evt) {
        eventView.habilitarBotones();
    }
}
