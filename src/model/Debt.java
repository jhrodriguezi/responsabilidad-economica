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
    private Date startDate;
    private int numQuotas;
    private String description; 
    private Category category;
    
    public Debt(int id, String name, long moneyToPaid, Date startDate, int numQuotas, String description, Category category){
        this.id = id;
        this.name = name;
        this.moneyToPaid = moneyToPaid;
        this.startDate = startDate;
        this.numQuotas = numQuotas;
        this.description = description;
        this.category = category;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
            
}
