package structures;
public class DequeLinked<T> extends MyLinkedList<T>{
    public boolean offerFirst(T e) {
        add(0,e);
        return true;
    }

    public boolean offerLast(T e){
        add(e);
        return true;
    }

    public T pollFirst(){
        if(isEmpty())
            return null;
        return remove(0);
    }
    
    public T pollLast(){
        if(isEmpty())
            return null;
        return remove(size()-1);
    }

    public T peekFirst() {
        if(isEmpty())
            return null;
        return get(0);
    }

    public T peekLast() {
        if(isEmpty())
            return null;
        return get(size()-1);            
    }

}