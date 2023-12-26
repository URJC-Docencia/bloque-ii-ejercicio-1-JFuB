import material.Position;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class represents a tree data structure using a linked implementation.
 * It implements the NAryTree interface.
 *
 * @param <E> the type of element stored in the tree
 */
public class LinkedTree<E> implements NAryTree<E> {

    private TreeNode<E> root;
    private int size;

    private class TreeNode<T> implements Position<T>{

        private T element;
        private final List<TreeNode<T>> children = new ArrayList<>();
        private TreeNode<T> parent;

        public TreeNode(T element){
            this.element=element;
        }

        public TreeNode(T element, TreeNode<T> parent){
            this(element);
            this.parent=parent;
        }

        @Override
        public T getElement() {
            return this.element;
        }

        public TreeNode<T> getParent(){
            return this.parent;
        }

        public List<TreeNode<T>> getChildren(){
            return this.children;
        }
    }

    @Override
    public Position<E> addRoot(E e) {
        if(!isEmpty()){
            throw new RuntimeException("The tree already has a root");
        }
        root=new TreeNode<>(e);
        size++;
        return root;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode= new TreeNode<>(element,parent);
        parent.getChildren().add(newNode);
        return newNode;
    }

    private TreeNode<E> checkPosition(Position<E> p){
        if(!(p instanceof TreeNode)){
            throw new RuntimeException("The position is invalid");
        }
        return (TreeNode<E>) p;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode = new TreeNode<>(element,parent);
        checkPositionOfChildrenList(n,parent);
        parent.getChildren().add(n,newNode);
        size++;
        return newNode;
    }

    private static <E> void checkPositionOfChildrenList(int n,LinkedTree<E>.TreeNode<E> parent){
        if(n <0 || n > parent.getChildren().size()){
            throw new RuntimeException("The position is invalid");
        }
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        TreeNode<E> node1=checkPosition(p1);
        TreeNode<E> node2=checkPosition(p2);
        E aux = node1.getElement();
        node1.element=node2.element;
        node2.element=aux;
    }

    @Override
    public E replace(Position<E> p, E e) {
        return null;
    }

    @Override
    public void remove(Position<E> p) {

    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Position<E> root() {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return false;
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return false;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }
}