package structures;

import java.util.EmptyStackException;

public class StackArray<T> extends MyArrayList<T>{

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
    public int search(Object element){
        int index = 1+indexOf(element);
        return index;
    }



}
