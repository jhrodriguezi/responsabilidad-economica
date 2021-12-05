package structures;

import java.util.EmptyStackException;

public class StackLinked<T> extends MyLinkedList<T> {
    
    public boolean empty(){
        return isEmpty();
    }
    
    public T peek(){
        if(empty())throw new EmptyStackException();
        T element = get(0);
        return element;
    }
    public T pop(){
        if(empty())throw new EmptyStackException();
        T element = remove(0);
        return element;
    }
    public T push(T e){
        add(0,e);
        return e;
    }
    public int search(Object element){
        return 1+indexOf(element);
    }
    
}
