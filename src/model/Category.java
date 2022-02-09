/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class Category {
    private int id;
    private String name;
    private int countDebt;
    private int activeDebt;
    
    public Category(int id, String name, int countDebt, int activeDebt){
        this.id=id;
        this.name=name;
        this.countDebt=countDebt;
        this.activeDebt=activeDebt;
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

    public int getCountDebt() {
        return countDebt;
    }

    public void setCountDebt(int countDebt) {
        this.countDebt = countDebt;
    }

    public int getActiveDebt() {
        return activeDebt;
    }

    public void setActiveDebt(int activeDebt) {
        this.activeDebt = activeDebt;
    }
    
    @Override
    public int hashCode(){
        int hash = 0;
        for(int i = 0; i<this.name.length(); i++){
            if(i<5)
                hash+=name.charAt(i)*(27^i);
        }
        return hash>0?hash:-1*hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        return this.id == other.id && this.name.equals(other.getName());
    }
    
    @Override
    public String toString(){
        return name+" "+id;
    }
}
