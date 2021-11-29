/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class Event {

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

}
