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


    //constructors
    public QueueLinked(){
    }

    @Override
    public boolean offer(T e) {
        return add(e);
    }

    @Override
    public T remove() {
        T element = get(0);
            remove(element);
            return element;
    }

    @Override
    public T poll() {
        if (!isEmpty()){
            T element = get(0);
            remove(element);
            return element;
        } else return null;
    }

    @Override
    public T element() {
        T element = get(0);
        return element;
    }

    @Override
    public T peek() {
        if (!isEmpty()){
            T element = get(0);
            return element;
        } else return null;
    }
    
}
