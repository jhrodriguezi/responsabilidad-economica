package structures;
public class MyAVLTree<T extends Comparable <T>>{
    private NodeAVL<T> root;

    public MyAVLTree(){
        this.root=null;
    }

    public MyAVLTree(T dato){
        this.root = new NodeAVL<T>(dato);
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

    public NodeAVL<T> obtain(T dato) {
        NodeAVL<T> aux = root;
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
    
    public T get(T dato) {
        NodeAVL<T> aux = root;
        while(aux!=null){
            if(dato.compareTo(aux.getValue())==0)
                return aux.getValue();
            if(dato.compareTo(aux.getValue())>0)
                aux=aux.getRightChild();
            else
                aux=aux.getLeftChild();
        }
        return null;
    }

    public boolean isLeaf(NodeAVL<T> NodeAVL
    ) {
        if(NodeAVL.getLeftChild()!=null || NodeAVL.getRightChild()!=null)
            return false;
        else
            return true;
    }

    public boolean isEmpty() {
        if(root!=null)
            return false;
        return true;
    }

    public NodeAVL<T> findMin() {
        return findMin(root);
    }

    public NodeAVL<T> findMax() {
        return findMax(root);
    }

    public void makeEmpy() {
        root=null;
    }

    public void printTree() {
        printTree(root);
    }


    public NodeAVL<T> insert(T dato, NodeAVL<T> t) {
        if(t==null)
            return new NodeAVL<T>(dato);

        if(dato.compareTo(t.getValue())<0)
            t.setLeftChild(insert(dato, t.getLeftChild()));
    
        if(dato.compareTo(t.getValue())>0)
            t.setRightChild(insert(dato,t.getRightChild()));
        
        if(dato.compareTo(t.getValue())==0)
            System.out.println("el dato ya fue ingresado");

        return rebalance(t);
    }

    public NodeAVL<T> remove(T dato, NodeAVL<T> t) {
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
                NodeAVL<T> mostLeftChild = findMin(t.getRightChild());
                t.setValue(mostLeftChild.getValue());
                t.setRightChild(remove(t.getValue(), t.getRightChild()));
            }
        }
        if (t != null) {
            t = rebalance(t);
        }
        return t;
    }

    public NodeAVL<T> findMin(NodeAVL<T> t) {
        NodeAVL<T> aux = t;
        while(aux.getLeftChild()!=null){
            aux=aux.getLeftChild();
        }
        return aux;
    }

    public NodeAVL<T> findMax(NodeAVL<T> t) {
        NodeAVL<T> aux = t;
        while(aux.getRightChild()!=null){
            aux=aux.getRightChild();
        }
        return aux;
    }

    public boolean contains(T dato, NodeAVL<T> t) {
        NodeAVL<T> aux = t;
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

    public void printTree(NodeAVL<T> t) {
        if(t!=null){
            printTree(t.getLeftChild());
            System.out.println(t.getValue()+" ");
            printTree(t.getRightChild());
        }
    }

    public int height(NodeAVL<T> t) {
        return t==null?-1:t.getHeight();
    }

    private void updateHeight(NodeAVL<T> t){
        t.setHeight(1+Math.max(height(t.getLeftChild()), height(t.getRightChild())));
    }

    private int getBalancingFactor(NodeAVL<T> t){
        return t==null?0:height(t.getRightChild())-height(t.getLeftChild());
    }

    public NodeAVL<T> simpleLeftRotation(NodeAVL<T> t) {
		NodeAVL<T> right = t.getRightChild();
        NodeAVL<T> leftRight = right.getLeftChild();
        right.setLeftChild(t);
        t.setRightChild(leftRight);
        updateHeight(t);
        updateHeight(right);
        return right;
	}
	
	public NodeAVL<T> simpleRightRotation(NodeAVL<T> t) {
		NodeAVL<T> left = t.getLeftChild();
        NodeAVL<T> leftRight = left.getRightChild();
        left.setRightChild(t);
        t.setLeftChild(leftRight);
        updateHeight(t);
        updateHeight(left);
        return left;
	}
	

    public NodeAVL<T> rebalance(NodeAVL<T> t) {
        updateHeight(t);
        int balance = getBalancingFactor(t);
        if (balance > 1) {
            if (height(t.getRightChild().getRightChild()) > height(t.getRightChild().getLeftChild())) {
                t = simpleLeftRotation(t);
            } else {
                t.setRightChild(simpleRightRotation(t.getRightChild()));
                t = simpleLeftRotation(t);
            }
        } else if (balance < -1) {
            if (height(t.getLeftChild().getLeftChild()) > height(t.getLeftChild().getRightChild()))
                t=simpleRightRotation(t);
            else {
                t.setLeftChild(simpleLeftRotation(t.getLeftChild()));
                t = simpleRightRotation(t);
            }
        }
        return t;
    }
}

class NodeAVL<T>{
    private T value;
    private int height;
    private NodeAVL<T> leftChild;
    private NodeAVL<T> rightChild;
    
    public NodeAVL(){
        this.value=null;
        this.leftChild=null;
        this.rightChild=null;
    }

    public NodeAVL(T value){
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public NodeAVL<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(NodeAVL<T> leftChild) {
        this.leftChild = leftChild;
    }

    public NodeAVL<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(NodeAVL<T> rightChild) {
        this.rightChild = rightChild;
    }
}


