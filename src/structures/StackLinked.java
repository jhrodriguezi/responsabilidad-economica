package structures;

import java.util.EmptyStackException;

public class StackLinked<T> extends MyLinkedList<T> {
    
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
        T element = get(size()-1);
        remove(element);
        return element;
    }
    public T push(T e){
        add(e);
        return e;
    }
    public int search(Object element){
        if (indexOf(element)!=-1){
            return 1+indexOf(element);
        } else return -1;
    }
    
}
