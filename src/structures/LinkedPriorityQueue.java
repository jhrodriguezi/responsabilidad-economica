package structures;

public class LinkedPriorityQueue<T extends Comparable<? super T>>{
    private MyLinkedList<T> lista;
    
    public LinkedPriorityQueue(){
        lista = new MyLinkedList<T>();
    }
    
    public void insert(T element){
        int pos=0;
        boolean flag = true;
        for(int i = 0; i < lista.size(); i++){
            if(element.compareTo(lista.get(i))<0){
                pos = i;
                flag=false;
                break;
            }
        }
        if(flag)
            lista.add(element);
        else
            lista.add(pos,element);
    }
    
    public T findMin(){
        if(isEmpty())
            return null;
        return lista.get(0);
    }
    
    public T deleteMin(){
        if(isEmpty())
            return null;
        return lista.remove(0);
    }
    
    public boolean isEmpty(){
        if(lista.size()==0)
            return true;
        return false;
    }
    
    public String toString(){
        return lista.toString();
    }
    
    public void clear(){
        lista.clear();
    }
}
