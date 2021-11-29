/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.EventDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import model.Event;
import structures.LinkList;
import view.EventView;

/**
 *
 * @author DELL
 */
public class EventController {
    private EventView eventView;
    private static LinkList<Event> events;
    private static EventDAO eventDAO;
    
    public EventController(EventView eventView){
        this.eventView=eventView;
        this.eventDAO=new EventDAO();
    }
    
    public void showEvent(){
        String[] c={"Valor a pagar","Fecha","Numero de cuota"};
        eventView.getTableEvents().setModel(new DefaultTableModel(c,0));
        DefaultTableModel tb=(DefaultTableModel)eventView.getTableEvents().getModel();
        Object[] row=new Object[3];
        this.events=eventDAO.getAllEvent();
        for(int i=0; i<events.size();i++){
            row[0]=events.get(i).getValue();
            row[1]=events.get(i).getDate();
            row[2]=events.get(i).getQuota();
            tb.addRow(row);
        }
        eventView.getTableEvents().setModel(tb);
    }

    public void CompleteEvent(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void TableMouseClicked(MouseEvent evt) {
        eventView.habilitarBotones();
    }
}
