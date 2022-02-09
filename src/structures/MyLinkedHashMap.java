package structures;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;

public class MyLinkedHashMap<K,V>{
    static final int INITIAL_SIZE=101;
    public int capacity;
    public int numElementos;
    final double DEFAULT_LOAD_FACTOR = 1.0;
    private MyLinkedList<MyLinkedEntry<K,V>>[] hashTable;
    
    public MyLinkedHashMap(){
        hashTable = (MyLinkedList<MyLinkedEntry<K,V>>[]) Array.newInstance(MyLinkedList.class, INITIAL_SIZE);
        numElementos=0;
        capacity=INITIAL_SIZE;
    }

    private int getHash(K key){
        int p = key.hashCode()%capacity;
        p = p<0? -1*p:p;
        return p;
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
        MyLinkedList<MyLinkedEntry<K,V>> temp = hashTable[getHash(key)];
        if(temp==null || temp.isEmpty())
            return false;
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getKey().equals(key))
                return true; 
        }
        return false;
    }

    public boolean containsValue(V value) {
        for(int i = 0; i < hashTable.length; i++){
            if(hashTable[i]!=null){
                for(int j = 0; j < hashTable[i].size(); j++){
                    if(hashTable[i].get(j).getValue().equals(value))
                        return true;
                }
            }
        }
        return false;
    }
    
    public V get(K key) {
        MyLinkedList<MyLinkedEntry<K,V>> temp = hashTable[getHash(key)];
        if(temp==null || temp.isEmpty())
            return null;
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getKey().equals(key))
                return temp.get(i).getValue();
        }
        return null;
    }

    public MyLinkedEntry<K,V> getEntry(K key) {
        MyLinkedList<MyLinkedEntry<K,V>> temp = hashTable[getHash(key)];
        if(temp==null || temp.isEmpty())
            return null;
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getKey().equals(key))
                return temp.get(i);
        }
        return null;
    }

    public void put(K key, V value) {
        if((double)numElementos/capacity>DEFAULT_LOAD_FACTOR)
            rehash();
        numElementos++;
        MyLinkedEntry<K,V> n = new MyLinkedEntry<K,V>();
        n.setKey(key);
        n.setValue(value);
        if(hashTable[getHash(key)]==null)
            hashTable[getHash(key)] = new MyLinkedList<MyLinkedEntry<K,V>>();
        hashTable[getHash(key)].add(n);
    }

    private void rehash(){
        MyLinkedList<MyLinkedEntry<K,V>>[] tempHashTable = hashTable.clone();
        MyLinkedEntry<K,V> e;
        hashTable = (MyLinkedList<MyLinkedEntry<K,V>>[]) Array.newInstance(MyLinkedList.class, capacity*2+1);
        capacity=capacity*2+1;
        numElementos=0;
        for(int i = 0; i < tempHashTable.length; i++){
            if(tempHashTable[i]!=null){
                for(int j = 0; j < tempHashTable[i].size(); j++){
                    e=tempHashTable[i].get(j);
                    put(e.getKey(), e.getValue());
                }
            }
        }
    }

    public V remove(K key) {
        MyLinkedList<MyLinkedEntry<K,V>> temp = hashTable[getHash(key)];
        if(temp==null || temp.isEmpty())
            return null;
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getKey().equals(key)){
                numElementos--;
                return temp.remove(i).getValue();
            }
        }
        return null;
    }

    public void clear() {
        hashTable = (MyLinkedList<MyLinkedEntry<K,V>>[]) Array.newInstance(MyLinkedList.class, INITIAL_SIZE);
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

    public int countSpacesEmpty(){
        int count = 0;
        for(int i = 0; i < hashTable.length; i++){
            if (hashTable[i]==null || hashTable[i].isEmpty())
                count++;
        }
        return count;
    }

    public String toString(){
        String message = "{";
        boolean flag = true;
        for(int i = 0; i<hashTable.length; i++){
            if(hashTable[i]!=null){
                for(int j = 0; j < hashTable[i].size(); j++){
                    if(flag){
                        message+=hashTable[i].get(j).getKey().toString()+":"+hashTable[i].get(j).getValue().toString();
                        flag=false;
                    }else{
                        message+=", "+hashTable[i].get(j).getKey().toString()+":"+hashTable[i].get(j).getValue().toString();
                    }
                }
            }
        }
        message+="}";
        return message;
    }

    /*public static void main(String[] args) {
        MyLinkedHashMap<Integer, String> map = new MyLinkedHashMap<>();
        for(int i = 0; i<10; i++)
            map.put(i,i+"aa");
        long startTime, endTime;
        startTime = System.nanoTime();
        map.put(-1, -1+"aa");
        endTime = System.nanoTime();
        System.out.println(map);
        System.out.println("insercion: "+ (endTime-startTime));
        startTime = System.nanoTime();
        map.get(-1);
        endTime = System.nanoTime();
        System.out.println("consulta: "+(endTime-startTime));
        startTime = System.nanoTime();
        map.remove(-1);
        endTime = System.nanoTime();
        System.out.println(map);
        System.out.println("eliminar: "+(endTime-startTime));
        System.out.println("factor de carga: "+ (map.numElementos/map.capacity));
        System.out.println(map.countSpacesEmpty());
    }*/
      
}

class MyLinkedEntry<K,V>{
    private K key;
    private V value;

    public MyLinkedEntry(){
        key=null;
        value=null;
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

