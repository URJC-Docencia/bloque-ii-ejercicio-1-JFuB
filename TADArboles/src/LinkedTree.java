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
        TreeNode<E> node = checkPosition(p);
        E aux = node.getElement();
        node.element=e;
        return aux;
    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node=checkPosition(p);
        if(node==root){
            root=null;
            size=0;
        }else{
            TreeNode<E> parent = node.getParent();
            parent.getChildren().remove(node);
            size-=computeSize(node);
        }
    }

    private int computeSize(TreeNode<E> node){
        int size=1;
        for(TreeNode<E> child: node.getChildren()){
            size+= computeSize(child);
        }
        return size;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        LinkedTree<E> tree = new LinkedTree<>();
        tree.root = node;
        tree.size = computeSize(node);
        return tree;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        TreeNode<E> node = checkPosition(p);
        LinkedTree<E> tree= checkTree(t);
        node.getChildren().add(tree.root);
        size+=tree.size;
    }

    private LinkedTree<E> checkTree(NAryTree<E> t){
        if(!(t instanceof LinkedTree<E>)){
            throw new RuntimeException("The tree is invalid");
        }
        return (LinkedTree<E>) t;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node= checkPosition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return !(node.getChildren().isEmpty());
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren().isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node==root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        if(isEmpty()){
            return new ArrayList<Position<E>>().iterator();
        }
        List<Position<E>> positions = new ArrayList<>();
        breadthFirstTraversal(root, positions);
        return positions.iterator();
    }

    private void breadthFirstTraversal(TreeNode<E> node, List<Position<E>> positions) {
        if(node != null){
            List<TreeNode<E>> queue = new ArrayList<>();
            queue.add(node);
            while(!queue.isEmpty()){
                TreeNode<E> nodeToVisit = queue.remove(0);
                positions.add(nodeToVisit);
                queue.addAll(nodeToVisit.getChildren());
            }
        }
    }

    public Iterator<Position<E>> iteratorPreOrder(){
        if(isEmpty()){
            return new ArrayList<Position<E>>().iterator();
        }
        List<Position<E>> positions = new ArrayList<>();
        preOrderTraversal(root,positions);
        return positions.iterator();
    }

    private void preOrderTraversal(TreeNode<E> node, List<Position<E>> positions) {
        if(node !=null){
            positions.add(node);
            for(TreeNode<E> child: node.getChildren()){
                preOrderTraversal(child,positions);
            }
        }
    }

    public Iterator<Position<E>> iteratorPostOrder(){
        if(isEmpty()){
            return new ArrayList<Position<E>>().iterator();
        }
        List<Position<E>> positions = new ArrayList<>();
        postOrderTraversal(root,positions);
        return positions.iterator();
    }

    private void postOrderTraversal(TreeNode<E> node, List<Position<E>> positions) {
        if(node!=null){
            for(TreeNode<E> child: node.getChildren()){
                postOrderTraversal(child,positions);
            }
            positions.add(node);
        }
    }

    public int size(){
        return size;
    }
}