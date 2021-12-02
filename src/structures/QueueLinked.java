/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.Queue;
/**
 *
 * @author DELL
 */
public class QueueLinked<T> extends MyLinkedList<T> implements Queue<T>{

    private MyLinkedList<T> list; 

    //constructors
    public QueueLinked(){
    this.list = new MyLinkedList<T>();
    }

    @Override
    public boolean offer(T e) {
        return list.add(e);
    }

    @Override
    public T remove() {
        T element = list.get(0);
            list.remove(element);
            return element;
    }

    @Override
    public T poll() {
        if (!list.isEmpty()){
            T element = list.get(0);
            list.remove(element);
            return element;
        } else return null;
    }

    @Override
    public T element() {
        T element = list.get(0);
        return element;
    }

    @Override
    public T peek() {
        if (!list.isEmpty()){
            T element = list.get(0);
            return element;
        } else return null;
    }
    
}
