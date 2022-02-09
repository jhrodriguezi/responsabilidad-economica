/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structures;

/**
 *
 * @author PC-LOCAL
 */
public class Test {
    public static void main(String[] args) {
        /*MyBinaryTree<Integer> tree = new MyBinaryTree<Integer>();
        MyAVLTree<Integer> avl = new MyAVLTree<Integer>();
        long l=0,c=0,lin=0,n=5,s=n;
        int p=0;
        while(n -- > 0){
            for(int i = 0; i<50000; i++){
                tree.insert(i);
                avl.insert(i);
                p=i;
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            tree.remove(p);
            endTime = System.nanoTime();
            l+=(endTime-startTime);
            startTime = System.nanoTime();
            avl.remove(p);
            endTime = System.nanoTime();
            c+=endTime-startTime;
            tree.makeEmpy();
            avl.makeEmpy();
        }
        System.out.println("tree: "+ l/s);
        System.out.println("avl: "+ c/s);
        */
        /*MyBinaryHeap<Integer> heap = new MyBinaryHeap<Integer>();
        LinkedPriorityQueue<Integer> heapLink = new LinkedPriorityQueue<Integer>();
        long l=0,c=0,lin=0,n=5,s=n;
        int p=0;
        while(n -- > 0){
            for(int i = 0; i<10000000; i++){
                heap.insert(i*-1);
                heapLink.insert(i*-1);
                p=i;
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            heap.deleteMin();
            endTime = System.nanoTime();
            l+=(endTime-startTime);
            startTime = System.nanoTime();
            heapLink.deleteMin();
            endTime = System.nanoTime();
            c+=endTime-startTime;
            heap.clear();
            heapLink.clear();
        }
        System.out.println("heap: "+ l/s);
        System.out.println("cola con link: "+ c/s);*/
        MyHashMap<Integer, String> map = new MyHashMap<Integer, String>();
        MyHashMapLineal<Integer, String> mapLin = new MyHashMapLineal<Integer, String>();
        MyLinkedHashMap<Integer, String> linkMap = new MyLinkedHashMap<>();
        long l=0,c=0,lin=0,n=5,s=n;
        int p=0;
        while(n -- > 0){
            for(int i = 0; i<10000000; i++){
                map.put(i,i+"aa");
                mapLin.put(i, i+"aa");
                linkMap.put(i,i+"aa");
                p=i;
            }
            long startTime, endTime;
            startTime = System.nanoTime();
            mapLin.remove(p);
            endTime = System.nanoTime();
            l+=(endTime-startTime);
            startTime = System.nanoTime();
            map.remove(p);
            endTime = System.nanoTime();
            c+=endTime-startTime;
            startTime = System.nanoTime();
            linkMap.remove(p);
            endTime = System.nanoTime();
            lin+=endTime-startTime;
            System.out.println((float)mapLin.numElementos/mapLin.capacity+ " soneo lineal");
            System.out.println((float)map.numElementos/map.capacity + " soneo cuadratico");
            System.out.println((float)linkMap.numElementos/linkMap.capacity+ " enlazado");
            map.clear();
            mapLin.clear();
            linkMap.clear();
        }
        float nom = (float)map.numElementos;
        float cap = (float)map.capacity;
        System.out.println("sondeo lineal: "+ l/s);
        System.out.println("sondeo cuadratico: "+ c/s);
        System.out.println("link: "+ lin/s);
    }
}
