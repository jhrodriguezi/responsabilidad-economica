/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/**
 *
 * @author DELL
 */
public class MyArrayCircularList<T> implements List<T>{
    private int size;
    private T[] array;
    private int start;
    private static final int DEFAULT_DIMENSION=10;
    
    public MyArrayCircularList(){
        clear();
    }
    
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(T e) {
        add(size(),e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        List.super.replaceAll(operator); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sort(Comparator<? super T> c) {
        List.super.sort(c); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        this.size=0;
        this.start=0;
        ensureCapacity(DEFAULT_DIMENSION);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return this.array[(start+index)%array.length];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T old = get(index);
        this.array[(start+index)%array.length] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        if(index!=size)checkIndex(index);
        if(this.array.length==size())
            ensureCapacity(size()*2+1);
        if(index<size()/2){
            start=(start-1)%array.length;
            for(int i=0;i<index;i++)
                this.array[(start+i)%array.length]=this.array[(start+i+1)%array.length];
        }else{
            for(int i=size();i>index;i--)
                this.array[(start+i)%array.length]=this.array[(start+i-1)%array.length];
        }
        this.array[(start+index)%array.length]=element;
        this.size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedItem = this.array[(index+start)%array.length];
        if(index<size()/2){
            for(int i=index;i>0;i--)
                this.array[(start+i)%array.length]=this.array[(start+i-1)%array.length];
            start=(start+1)%array.length;
        }else{
            for(int i=index;i<size();i++)
                this.array[(start+i)%array.length]=this.array[(start+i+1)%array.length];
        }
        this.size--;
        return removedItem;
    }
    
    public void checkIndex(int index){
        if(index<0 || index>=size()){
            throw new IndexOutOfBoundsException("index out of range");
        }
    }

    @Override
    public int indexOf(Object o) {
        for(int i=0; i<this.size;i++){
            if(this.array[(i+start)%array.length].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spliterator<T> spliterator() {
        return List.super.spliterator(); //To change body of generated methods, choose Tools | Templates.
    }

    public void ensureCapacity(int newCapacity){
        if(newCapacity>this.size){
            T[] old = this.array;
            this.array = (T[]) new Object[newCapacity];
            for(int i=0; i<this.size; i++)
                this.array[i]=old[(i+start)%old.length];
            start=0;
        }
    }
    
    @Override
    public String toString(){
        String message="";
        message+="[";
        if(size>0){
            for(int i=0; i<size-1; i++){
                message+=this.array[(i+start)%array.length]+",";
            }
            message+=this.array[(start+size-1)%array.length];
        }
        message+="]";
        return message;
    }
}
