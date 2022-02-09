/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class Event implements Comparable<Event>{

    private int id;
    private int idDebt;
    private float value;
    private String date;
    private int quota;

    public Event(int id, int idDebt, float value, String date, int quota) {
        this.id = id;
        this.idDebt = idDebt;
        this.value = value;
        this.date = date;
        this.quota = quota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDebt() {
        return idDebt;
    }

    public void setIdDebt(int idDebt) {
        this.idDebt = idDebt;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    @Override
    public int compareTo(Event o) {
        if(o!=null){
            String[] d = this.date.split("-"), m = o.getDate().split("-");
            int sumD, sumM;
            sumD = Integer.parseInt(d[0])*365+Integer.parseInt(d[1])*12+Integer.parseInt(d[2]);
            sumM = Integer.parseInt(m[0])*365+Integer.parseInt(m[1])*12+Integer.parseInt(m[2]);
            if(sumD>sumM)
                return 1;
            else if(sumD<sumM)
                return -1;
            return 0;
        }
        return -1;
    }

}
