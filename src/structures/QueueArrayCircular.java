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
public class QueueArrayCircular<T> extends MyArrayCircularList<T> implements Queue<T>{
    private int front;

    //constructors
    public QueueArrayCircular(){
        front = 0;
    }

    @Override
    public boolean offer(T e) {
        add(e);
        return true;
    }

    @Override
    public T remove() {
        T element = null;
        element = remove(front);
        return element;
    }

    @Override
    public T poll() {
        T element = null;
        if(!isEmpty()){
            element = remove(front);
            return element;
        }else{
            return null;
        }
    }

    @Override
    public T element() {
        T element = null;
        element = get(front);
        return element;
    }

    @Override
    public T peek() {
        T element = null;
        if (!isEmpty()){
            element = get(front);
            return element;
        } else return null;
    }
}
