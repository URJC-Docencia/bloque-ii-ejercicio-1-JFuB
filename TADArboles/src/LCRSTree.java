import material.Position;

import java.util.Iterator;


/**
 * An implementation of the NAryTree interface using left-child, right-sibling representation.
 *
 * @param <E> the type of elements stored in the tree
 */
public class LCRSTree<E> implements NAryTree<E> {

    private LCRSnode<E> root;
    private int size;

    private class LCRSnode<T> implements Position<T> {

        private T element;
        private LCRSnode<T> parent;
        private LCRSnode<T> leftChild;
        private LCRSnode<T> rightSibling;

        public LCRSnode(T element, LCRSnode<T> parent, LCRSnode<T> leftChild, LCRSnode<T> rightSibling){
            this.element=element;
            this.leftChild=leftChild;
            this.parent=parent;
            this.rightSibling=rightSibling;
        }

        public LCRSnode(T element){
            this(element,null,null,null);
        }

        public LCRSnode(T element, LCRSnode<T> parent){
            this(element,parent,null,null);
        }

        public LCRSnode<T> getParent() {
            return parent;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public void setParent(LCRSnode<T> parent) {
            this.parent = parent;
        }

        public void setLeftChild(LCRSnode<T> leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightSibling(LCRSnode<T> rightSibling) {
            this.rightSibling = rightSibling;
        }

        public LCRSnode<T> getLeftChild() {
            return leftChild;
        }

        public LCRSnode<T> getRightSibling() {
            return rightSibling;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public String toString(){
            return element.toString();
        }
    }

    @Override
    public Position<E> addRoot(E e) {
        if(!isEmpty()){
            throw new RuntimeException("Tree already has a root");
        }
        this.root = new LCRSnode<>(e);
        this.size=1;
        return this.root;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        LCRSnode<E> parent = checkPosition(p);
        LCRSnode<E> newNode = new LCRSnode<>(element,parent);
        if(parent.getLeftChild()==null){
            parent.setLeftChild(newNode);
        }else{
            LCRSnode<E> leftChild = parent.getLeftChild();
            while(leftChild.getRightSibling()!=null){
                leftChild=leftChild.getRightSibling();
            }
            leftChild.setRightSibling(newNode);
        }
        this.size++;
        return newNode;
    }

    private LCRSnode<E> checkPosition(Position<E> p){
        if(!(p instanceof LCRSnode)){
            throw new RuntimeException("The position is invalid");
        }
        return (LCRSnode<E>) p;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        LCRSnode<E> parent = checkPosition(p);
        LCRSnode<E> newNode = new LCRSnode<>(element,parent);
        if(n< 0){
            throw new RuntimeException("The position is invalid");
        }else if(n==0){
            newNode.setRightSibling(parent.getLeftChild());
            parent.setLeftChild(newNode);
        }else{
            LCRSnode<E> leftChild = parent.getLeftChild();
            int i=1;
            while(i < n && leftChild.getRightSibling()!=null){
                leftChild = leftChild.getRightSibling();
                i++;
            }
            newNode.setRightSibling(leftChild.getRightSibling());
            leftChild.setRightSibling(newNode);
        }
        this.size++;
        return newNode;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        LCRSnode<E> node1= checkPosition(p1);
        LCRSnode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(p2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        LCRSnode<E> node = checkPosition(p);
        E aux = node.getElement();
        node.setElement(e);
        return aux;
    }

    @Override
    public void remove(Position<E> p) {
        LCRSnode<E> node = checkPosition(p);
        if(node == root){
            root=null;
            size=0;
        } else {
            LCRSnode<E> parent = node.getParent();
            if(parent.getLeftChild()==node){
                parent.setLeftChild(node.getRightSibling());
            } else {
                LCRSnode<E> leftChild = parent.getLeftChild();
                while(leftChild.getRightSibling()!= node){
                    leftChild= leftChild.getRightSibling();
                }
                leftChild.setRightSibling(node.getRightSibling());
            }
            size-= computeSize(node);
        }
    }

    private int computeSize(LCRSnode<E> node) {
        if(node == null){
            return 0;
        } else{
            int size =1;
            LCRSnode<E> child = node.getLeftChild();
            while (child != null){
                size+= computeSize(child);
                child =child.getRightSibling();
            }
            return size;
        }
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        LCRSnode<E> node = checkPosition(v);
        var tree = new LCRSTree<E>();
        tree.root = node;
        tree.size = computeSize(node);
        return tree;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        LCRSnode<E> node = checkPosition(p);
        var tree = (LCRSTree<E>) t;
        var leftChild = node.getLeftChild();
        if(leftChild == null){
            node.setLeftChild(tree.root);
        } else {
            while (leftChild.getRightSibling()!=null){
                leftChild=leftChild.getRightSibling();
            }
            leftChild.setRightSibling(tree.root);
        }
        this.size +=tree.size;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position<E> root() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position<E> parent(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isInternal(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRoot(Position<E> v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Position<E>> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
