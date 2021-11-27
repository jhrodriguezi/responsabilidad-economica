/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Debt {
    private int id;
    private String name;
    private long moneyToPaid; 
    private String startDate;
    private int numQuotas;
    private String description; 
    private String category;
    
    public Debt(int id, String name, long moneyToPaid, String startDate, int numQuotas, String description, String category){
        this.id = id;
        this.name = name;
        this.moneyToPaid = moneyToPaid;
        this.startDate = startDate;
        this.numQuotas = numQuotas;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoneyToPaid() {
        return moneyToPaid;
    }

    public void setMoneyToPaid(long moneyToPaid) {
        this.moneyToPaid = moneyToPaid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getNumQuotas() {
        return numQuotas;
    }

    public void setNumQuotas(int numQuotas) {
        this.numQuotas = numQuotas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public String toString(){
        return ""+this.id+" "+this.name+" "+this.description+" "+this.startDate;
    }
}
