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
public class MyLinkedList<T> implements List<T>{
    private Node<T> initialNode;
    private Node<T> endNode;
    private int size;
    
    public MyLinkedList(){
        clear();
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
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
        Node<T> n = initialNode.getNextNode();
        for(int i=0; i<size(); i++){
            if(n.getValue().equals(o)){
                n.getPreviousNode().setNextNode(n.getNextNode());
                n.getNextNode().setPreviousNode(n.getPreviousNode());
                size--;
                return true;
            }
            n=n.getNextNode();
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
        initialNode=new Node();
        endNode=new Node();
        initialNode.setNextNode(endNode);
        endNode.setPreviousNode(initialNode);
        size=0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        Node<T> n = getNode(index);
        T oldValue = n.getValue();
        n.setValue(element);
        return oldValue;
    }
    
    public Node<T> getNode(int index){
        Node<T> n;
        if(index<size()/2){
            n = initialNode.getNextNode();
            for(int i=0;i<index;i++)n=n.getNextNode();
        }else{
            n = endNode;
            for(int i=size();i>index;i--)n=n.getPreviousNode();
        }
        return n;
    }
    
    @Override
    public void add(int index, T element) {
        if(index!=size())checkIndex(index);
        Node<T> n = getNode(index);
        Node<T> newNode = new Node();
        newNode.setValue(element);
        newNode.setNextNode(n);
        newNode.setPreviousNode(n.getPreviousNode());
        newNode.getPreviousNode().setNextNode(newNode);
        n.setPreviousNode(newNode);
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> n = getNode(index);
        n.getNextNode().setPreviousNode(n.getPreviousNode());
        n.getPreviousNode().setNextNode(n.getNextNode());
        size--;
        return n.getValue();
    }

    @Override
    public int indexOf(Object o) {
        Node<T> n = initialNode.getNextNode();
        for(int i=0; i<size(); i++){
            if(n.getValue().equals(o))
                return i;
                
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<T> n = endNode.getPreviousNode();
        int i = size()-1;
        while(!n.getValue().equals(o) && n.getPreviousNode()!=initialNode){
            n=n.getPreviousNode();
            i--;
        }        
        if(n.getValue().equals(o))return i;
        return -1;
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
    
    public void checkIndex(int index){
        if(index<0 || index>=size()){
            throw new IndexOutOfBoundsException("index out of range");
        }
    }
    
    @Override
    public String toString(){
        String message="";
        message+="[";
        if(initialNode.getNextNode()!=null && initialNode.getNextNode()!=endNode){
            Node<T> temp=initialNode.getNextNode();
            do {
                message+=temp.getValue();
                if(temp.getNextNode()!=null && temp.getNextNode()!=endNode)message+=",";
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
    private Node previousNode;
    
    public Node(){
        this.value=null;
        this.nextNode=null;
        this.previousNode=null;
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

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
    
    
}