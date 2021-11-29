/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import view.Main;

/**
 *
 * @author DELL
 */
public class MainController {
    private final Main main;
    
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
        }
    }
}
