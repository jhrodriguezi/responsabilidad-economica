/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import access.DebtDAO;
import access.EventDAO;
import java.awt.event.ActionEvent;
import model.Debt;
import model.Event;
import view.Main;

/**
 *
 * @author DELL
 */
public class MainController {
    private static Main main;
    
    
    public MainController(Main main){
        this.main=main;
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==main.getBtnDeudas()){
            main.cambiarADebt();
        }else if(e.getSource()==main.getBtnEventos()){
            main.cambiarAEvent();
        }else if(e.getSource()==main.getBtnCategorias()){
            main.cambiarACategory();
        }else if(e.getSource()==main.getBtnSi()){
            if(EventController.areThereEvents()){
                main.cambiarAInfo();
                EventController.showEventAsc();
            }else{
                main.getLblNoHayEventos().setText("¡Felicitaciones! no tiene fraccionamientos pendientes");
                main.getLblNoHayEventos().setVisible(true);
            }
        }else if(e.getSource()==main.getBtnPagado()){
            EventController.deleteEvent();
            EventController.showEventAsc();
        }
    }
    
    public static Main getMainView(){
        return main;
    }
}
