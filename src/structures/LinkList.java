/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author DELL
 */
public class LinkList<T> implements List<T>{
    private Node initialNode;
    private int size;
    
    public LinkList(){
        initialNode=null;
        size=0;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.initialNode==null;
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
        add(size,e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node temp=this.initialNode;
        if(temp.getValue()!=null && temp.getValue().equals(o)){
            this.initialNode=initialNode.getNextNode();
            size--;
            return true;
        }
        
        for(int i=1; i<this.size(); i++){
            if(temp.getNextNode().getValue()!=null && temp.getNextNode().getValue().equals(o)){
                temp.setNextNode(temp.getNextNode().getNextNode());
                size--;
                return true;
            }
            temp=temp.getNextNode();
        }
        return false;
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
    public void clear() {
        this.initialNode=null;
    }

    @Override
    public T get(int index) {
        return getNode(index).getValue();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Node<T> getNode(int index){
        if(index==0)
            return this.initialNode;
        else
            return getNode(index-1).getNextNode();
    }
    
    @Override
    public void add(int index, T element) {
        if(index<0 || index>size)
            throw new IndexOutOfBoundsException("Index: "+index+" out of range");
        else if(index==0){
            Node<T> temp = initialNode;
            initialNode=new Node();
            initialNode.setValue(element);
            initialNode.setNextNode(temp);
        }else{    
            Node<T> newNode=new Node();
            newNode.setValue(element);
            Node<T> temp = getNode(index-1);
            Node<T> tempNextNode = temp.getNextNode();
            temp.setNextNode(newNode);
            newNode.setNextNode(tempNextNode);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        T value = getNode(index).getValue();
        remove(value);
        return value;
    }

    @Override
    public int indexOf(Object o) {
        Node temp=this.initialNode;
        for(int i=0; i<this.size; i++){
            if(temp.getValue()!=null && temp.getValue().equals(o)){
                return i;
            }
            temp=temp.getNextNode();
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
    public String toString(){
        String message="";
        message+="[";
        if(this.initialNode!=null){
            Node temp=initialNode;
            do {
                message+=temp.getValue();
                if(temp.getNextNode()!=null)message+=",";
                else break;
                temp=temp.getNextNode();
            } while (true);
        }
        message+="]";
        return message;
    }
}

class Node<T>{
    private T value;
    private Node nextNode;
    
    public Node(){
        this.value=null;
        this.nextNode=null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
    
}