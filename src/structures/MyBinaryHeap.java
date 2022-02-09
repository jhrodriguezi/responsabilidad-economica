package structures;
import javax.management.RuntimeErrorException;

public class MyBinaryHeap <T extends Comparable <?super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private T[] array;
    
    public MyBinaryHeap() {
        this(DEFAULT_CAPACITY);
    }
    
    public MyBinaryHeap(int capacity) {
        currentSize=0;
        array = (T[]) new Comparable[capacity+1];
    }
    
    public int getCurrentSize() {
        return currentSize;
    }
    
    public T getPosOfArray(int pos) {
        return array[pos];
    }
    
    public void enlargeArray(int newSize) {
        if (!(newSize<this.currentSize)){
            T[] old = this.array.clone();
            this.array= (T[]) new Comparable[newSize];
            for (int i=0; i<old.length; i++){
                this.array[i]=old[i];
            }
        }
    }

    private void percolateDown (int hole){
        int child; 
        T tmp = array[hole];
        for (;hole*2 <= currentSize; hole=child){
            child = hole*2;
            if (child!=currentSize && array[child+1].compareTo(array[child])<0) child++;
            if (array[child].compareTo(tmp)<0) array[hole] = array[child]; 
            else break;
        }
        array[hole] = tmp;
    }

    public T deleteMin(){
        if (isEmpty()){
            throw new RuntimeErrorException(null, "El BinaryHeap está vacio");
        }
        T minItem = findMin();
        array[1] = array[currentSize--];
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
        }
        return array[1];
    }
    
    public void insert(T dato){
        if (currentSize == array.length-1) {
            enlargeArray(array.length*2+1);
        }
        
        int hole = ++currentSize;

        for (array[0]=dato; dato.compareTo(array[hole/2])<0 ; hole/=2 ) {
            array[hole]=array[hole/2];
        }
        array[hole]=dato;
    }
    
    public int indexOf(T o) {
        for(int i=0; i<this.currentSize;i++){
            if(this.array[i].equals(o)){
                return i;
            }
        }
        return -1;
    }
    
    public void printBinaryHeap() {
        int temp = currentSize+1;
        for (int i=0; i<temp;i++) {
            if (i!=0) System.out.println(array[i]);
        }
    }
    
    public void clear(){
        currentSize=0;
        array = (T[]) new Comparable[DEFAULT_CAPACITY+1];
    }
}