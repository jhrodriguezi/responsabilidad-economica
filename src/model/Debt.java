/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Debt implements Comparable<Debt>{
    private int id;
    private String name;
    private float moneyToPaid; 
    private String startDate;
    private int numQuotas;
    private String periodicity;
    private String description; 
    private int idCategory;
    private int percent;
    
    public Debt(String name){
        this.name = name;
    }
    
    public Debt(int id, String name, float moneyToPaid, String startDate, int numQuotas, String periodicity, String description, int idCategory, int percent){
        this.id = id;
        this.name = name;
        this.moneyToPaid = moneyToPaid;
        this.startDate = startDate;
        this.numQuotas = numQuotas;
        this.periodicity=periodicity;
        this.description = description;
        this.idCategory = idCategory;
        this.percent=percent;
    }
    
    public Debt(Debt debt){
        this.id = debt.getId();
        this.name = debt.getName();
        this.moneyToPaid = debt.getMoneyToPaid();
        this.startDate = debt.getStartDate();
        this.numQuotas = debt.getNumQuotas();
        this.periodicity=debt.getPeriodicity();
        this.description = debt.getDescription();
        this.idCategory = debt.getIdCategory();
        this.percent=debt.getPercent();
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

    public float getMoneyToPaid() {
        return moneyToPaid;
    }

    public void setMoneyToPaid(float moneyToPaid) {
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Debt){
            Debt d = (Debt) obj;
            if(d.getId()==getId()){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return ""+this.id+" "+this.name;
    }
    
    @Override
    public int compareTo(Debt o) {
        return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
    }
}
