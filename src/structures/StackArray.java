package structures;

import java.util.EmptyStackException;

public class StackArray<T> extends MyArrayList<T>{
    //Atributes
    private MyArrayList<T> arr;

    //Constructor
    public StackArray(){
        this.arr = new MyArrayList<T>();
    }

    public boolean empty(){
        return arr.isEmpty();
    }
    
    public T peek(){
        if(empty())return null;
        T element = arr.get(size()-1);
        return element;
    }
    public T pop(){
        if(empty())return null;
        T element = arr.remove(size()-1);
        return element;
    }
    public T push(T e){
        arr.add(e);
        return e;
    }
    public int search(Object element){
        int index = 1+arr.indexOf(element);
        return index;
    }



}
