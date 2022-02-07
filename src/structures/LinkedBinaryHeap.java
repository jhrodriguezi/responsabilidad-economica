package structures;
import javax.management.RuntimeErrorException;

public class LinkedBinaryHeap<T extends Comparable <T>> {
    private int currentSize;
    private MyLinkedList<T> list;
    
    public LinkedBinaryHeap() {
        this(0);
    }
    
    public LinkedBinaryHeap(int capacity) {
        currentSize=0;
        list =  new MyLinkedList<>();
    }
    
    public int getCurrentSize() {
        return currentSize;
    }
    
    public T getPosOfArray(int pos) {
        return list.get(pos);
    }

    private void percolateDown (int hole){
        int child; 
        T tmp = list.get(hole);
        for ( ; hole*2 <= currentSize; hole=child){
            child = hole*2;
            if (child!=currentSize && list.get(child+1).compareTo(list.get(child))<0) {
                child++;
            }
            if (list.get(child).compareTo(tmp)<0) list.set(hole,list.get(child));
            else break;
        }
        list.set(hole, tmp);
    }


    public T deleteMin(){
        if (isEmpty()){
            throw new RuntimeErrorException(null, "El BinaryHeap está vacio");
        }
        T minItem = findMin();
        list.set(1,list.get(currentSize--));
        percolateDown(1);
        return minItem;
        
    }

    public boolean isEmpty(){
        if (currentSize==0){
            return true;
        } else return false;
    }

    public T findMin(){
        if (this.isEmpty()){
            throw new RuntimeErrorException(null, "El BInaryHeap está vacio");
        } else return list.get(1);
    }
    
    public void insert(T dato){
        
        int hole = ++currentSize;

        for (list.set(0,dato); dato.compareTo(list.get(hole/2))<0 ; hole/=2 ) {
            list.set(hole,list.get(hole/2));
        }
        list.set(hole,dato);
    }
    
    public int indexOf(T o) {
        for(int i=0; i<this.currentSize;i++){
            if(this.list.get(i).equals(o)){
                return i;
            }
        }
        return -1;
    }
    
    public void printBinaryHeap() {
        for (int i=0; i<list.size();i++) {
            if (i!=0){
                if (list.get(i)!=null) System.out.println(list.get(i));
            } 
        }
    }

}
