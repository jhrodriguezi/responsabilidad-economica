package structures;
public class MyBinaryTree<T extends Comparable <T>>{
    private NodeTree<T> root;

    public MyBinaryTree(){
        this.root=null;
    }

    public MyBinaryTree(T dato){
        this.root = new NodeTree<T>(dato);
    }

    public void insert(T dato) {
        root=insert(dato,root);
    }

    public void remove(T dato) {
        root=remove(dato,root);
    }

    public boolean contains(T dato) {
        return contains(dato, root);
    }

    public NodeTree<T> obtain(T dato) {
        NodeTree<T> aux = root;
        while(aux!=null){
            if(dato.compareTo(aux.getValue())==0)
                return aux;
            if(dato.compareTo(aux.getValue())>0)
                aux=aux.getRightChild();
            else
                aux=aux.getLeftChild();
        }
        return null;
    }

    public boolean isLeaf(NodeTree<T> NodeTree
    ) {
        if(NodeTree.getLeftChild()!=null || NodeTree.getRightChild()!=null)
            return false;
        else
            return true;
    }

    public boolean isEmpty() {
        if(root!=null)
            return false;
        return true;
    }

    public NodeTree<T> findMin() {
        return findMin(root);
    }

    public NodeTree<T> findMax() {
        return findMax(root);
    }

    public void makeEmpy() {
        root=null;
    }

    public void printTree() {
        printTree(root);
    }

    public NodeTree<T> insert(T dato, NodeTree<T> t) {
        if(t==null)
            return new NodeTree<T>(dato);

        if(dato.compareTo(t.getValue())<0)
            t.setLeftChild(insert(dato, t.getLeftChild()));
    
        if(dato.compareTo(t.getValue())>0)
            t.setRightChild(insert(dato,t.getRightChild()));
        
        if(dato.compareTo(t.getValue())==0)
            System.out.println("el dato ya fue ingresado");

        return t;
    }

    public NodeTree<T> remove(T dato, NodeTree<T> t) {
        if (t == null) {
            return t;
        } else if (t.getValue().compareTo(dato)>0) { 
            t.setLeftChild(remove(dato ,t.getLeftChild()));
        } else if (t.getValue().compareTo(dato)<0) {
            t.setRightChild(remove(dato, t.getRightChild()));
        } else {
            if (t.getLeftChild() == null || t.getRightChild() == null) {
                t = (t.getLeftChild() == null) ? t.getRightChild() : t.getLeftChild();
            } else {
                NodeTree<T> mostLeftChild = findMin(t.getRightChild());
                t.setValue(mostLeftChild.getValue());
                t.setRightChild(remove(t.getValue(), t.getRightChild()));
            }
        }
        return t;
    }

    public NodeTree<T> findMin(NodeTree<T> t) {
        NodeTree<T> aux = t;
        while(aux.getLeftChild()!=null){
            aux=aux.getLeftChild();
        }
        return aux;
    }

    public NodeTree<T> findMax(NodeTree<T> t) {
        NodeTree<T> aux = t;
        while(aux.getRightChild()!=null){
            aux=aux.getRightChild();
        }
        return aux;
    }

    public boolean contains(T dato, NodeTree<T> t) {
        NodeTree<T> aux = t;
        while(aux!=null){
            if(dato.compareTo(aux.getValue())==0)
                return true;
            if(dato.compareTo(aux.getValue())>0)
                aux=aux.getRightChild();
            else
                aux=aux.getLeftChild();
        }
        return false;
    }

    public void printTree(NodeTree<T> t) {
        if(t!=null){
            printTree(t.getLeftChild());
            System.out.println(t.getValue()+" ");
            printTree(t.getRightChild());
        }
    }

    public int height(NodeTree<T> t) {
        return -1;
    }

    /*public static void main(String[] args) {
        MyBinaryTree<Integer> b = new MyBinaryTree<>();
        b.insert(10);
        b.insert(8);
        b.insert(15);
        b.insert(3);
        b.insert(9);
        b.insert(12);
        b.printTree();
    }*/
}

class NodeTree<T extends Comparable <T>>{
    private T value;
    private NodeTree<T> leftChild;
    private NodeTree<T> rightChild;
    
    public NodeTree(){
        this.value=null;
        this.leftChild=null;
        this.rightChild=null;
    }

    public NodeTree(T value){
        this.value=value;
        this.leftChild=null;
        this.rightChild=null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public NodeTree<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(NodeTree<T> leftChild) {
        this.leftChild = leftChild;
    }

    public NodeTree<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(NodeTree<T> rightChild) {
        this.rightChild = rightChild;
    }
    
    
}


