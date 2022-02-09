package structures;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;

public class MyHashMapLineal<K,V>{
    static final int INITIAL_SIZE=101;
    public int capacity;
    public int numElementos;
    final double DEFAULT_LOAD_FACTOR = 0.5;
    private MyEntryLineal<K,V>[] hashTable;
 
    public MyHashMapLineal(){
        hashTable = (MyEntryLineal<K,V>[]) Array.newInstance(MyEntryLineal.class, INITIAL_SIZE);
        numElementos=0;
        capacity=INITIAL_SIZE;
    }
    
    private int getHash(K key){
        int i = 0, d = key.hashCode();//obtenemos el hash por el metodo de la multiplicacion.
        //para corregir las colisiones hacemos un sondeo lineal.
        d = d<0 ? -1*d : d;
        while(hashTable[(int)(d+i)%capacity]!=null && !hashTable[(int)(d+i)%capacity].getKey().equals(key)){
            if(hashTable[(int)(d+i)%capacity].isWasEliminated())
                break;
            i++;
        }
        return (int)(d+i)%capacity;
    }

    public int size() {
        return numElementos;
    }

    public boolean isEmpty() {
        if(numElementos==0)
            return true;
        return false;
    }

    public boolean containsKey(K key) {
        if(key==null)
            return false;
        if(hashTable[getHash(key)]!= null && !hashTable[getHash(key)].isWasEliminated())
            return true;
        return false;
    }

    public boolean containsValue(V value) {
        for(int i = 0; i < hashTable.length; i++){
            if(hashTable[i]==null || hashTable[i].isWasEliminated())
                continue;
            if(hashTable[i].getValue().equals(value))
                return true;   
        }
        return false;
    }

    public V get(K key) {
        if(hashTable[getHash(key)]==null)
            return null;
        return hashTable[getHash(key)].getValue();
    }

    public MyEntryLineal<K,V> getEntry(K key) {
        if(hashTable[getHash(key)]==null)
            return null;
        return hashTable[getHash(key)];
    }
    
    public void put(K key, V value) {
        if((double)numElementos/capacity>DEFAULT_LOAD_FACTOR)
            rehash();
        numElementos++;
        MyEntryLineal<K,V> n = new MyEntryLineal<>();
        n.setKey(key);
        n.setValue(value);
        hashTable[getHash(key)]=n;
    }
    
    private void rehash(){
        MyEntryLineal<K,V>[] tempHashTable = hashTable.clone();
        hashTable = (MyEntryLineal<K,V>[]) Array.newInstance(MyEntryLineal.class, capacity*2+1);
        capacity=capacity*2+1;
        numElementos=0;
        for(MyEntryLineal<K,V> e : tempHashTable){
            if(e!=null){
                put(e.getKey(), e.getValue());
                getEntry(e.getKey()).setWasEliminated(e.isWasEliminated());
            }
        }
    }

    public V remove(K key) {
        MyEntryLineal<K,V> temp = hashTable[getHash(key)];
        temp.setWasEliminated(true);
        numElementos--;
        return temp.getValue();
    }

    public void clear() {
        hashTable = (MyEntryLineal<K,V>[]) Array.newInstance(MyEntryLineal.class, INITIAL_SIZE);
        numElementos=0;
        capacity=INITIAL_SIZE;
    }

    public Set keySet() {
        // TODO Auto-generated method stub
        return null;
    }
    public Collection values() {
        // TODO Auto-generated method stub
        return null;
    }

    public Set entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    public String toString(){
        String message = "{";
        boolean flag = true;
        for(int i = 0; i<hashTable.length; i++){
            if(hashTable[i]!=null){
                if(!hashTable[i].isWasEliminated()){
                    if(flag){
                        message+=hashTable[i].getKey().toString()+":"+hashTable[i].getValue().toString();
                        flag=false;
                    }else{
                        message+=", "+hashTable[i].getKey().toString()+":"+hashTable[i].getValue().toString();
                    }
                }
            }
        }
        message+="}";
        return message;
    }
}

class MyEntryLineal<K,V>{
    private boolean wasEliminated;
    private K key;
    private V value;

    public MyEntryLineal(){
        wasEliminated=false;
        key=null;
        value=null;
    }

    public boolean isWasEliminated() {
        return wasEliminated;
    }

    public void setWasEliminated(boolean wasEliminated) {
        this.wasEliminated = wasEliminated;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
