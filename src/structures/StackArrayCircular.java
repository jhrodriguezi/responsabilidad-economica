/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.EmptyStackException;

/**
 *
 * @author DELL
 */
public class StackArrayCircular<T> extends MyArrayCircularList<T>{

    public boolean empty(){
        return isEmpty();
    }
    
    public T peek(){
        if(empty())throw new EmptyStackException();
        T element = get(size()-1);
        return element;
    }
    public T pop(){
        if(empty())throw new EmptyStackException();
        T element = remove(size()-1);
        return element;
    }
    public T push(T e){
        add(e);
        return e;
    }
    public int search(T element){
        int index = 1+indexOf(element);
        return index;
    }
}
