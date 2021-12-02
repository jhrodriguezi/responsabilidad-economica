package structures;

public class StackLinked<T> extends MyLinkedList<T> {

    //Atributos

    private MyLinkedList<T> list;

    public StackLinked(){
        this.list = new MyLinkedList<T>();
    }

    public boolean empty(){
        return list.isEmpty();
    }
    
    public T peek(){
        T element = list.get(size()-1);
        return element;
    }
    public T pop(){
        T element = list.get(size()-1);
        list.remove(element);
        return element;
    }
    public T push(T e){
        list.add(e);
        return e;
    }
    public int search(Object element){
        if (list.indexOf(element)!=-1){
            return 1+list.indexOf(element);
        } else return -1;
    }
    
}
