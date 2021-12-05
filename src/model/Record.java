/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class Record<T>{
    private String metodo;
    private T entidad;
    private int lastRow;
    
    public Record(String metodo, T entidad, int lastRow){
        this.metodo=metodo;
        this.entidad=entidad;
        this.lastRow = lastRow;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public T getEntidad() {
        return entidad;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Record){
            Record r = (Record) obj;
            if(r.getMetodo().equals(getMetodo()) && r.getLastRow()==getLastRow() && r.getEntidad().equals(getEntidad())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return entidad.toString();
    }
}
