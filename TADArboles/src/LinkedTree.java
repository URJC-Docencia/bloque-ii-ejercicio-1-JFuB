import material.Position;

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

    /**
     * This class represents a node in a tree data structure.
     * It implements the Position interface.
     *
     * @param <T> the type of element stored in the node
     */
    private class TreeNode<T> implements Position<T> {

        private List<TreeNode<T>> children = new ArrayList<>();
        private T element;
        private TreeNode<T> parent;

        public TreeNode(T element) {
            this.element = element;
        }

        public TreeNode(T element, TreeNode<T> parent) {
            this.element = element;
            this.parent = parent;
        }

        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        @Override
        public T getElement() {
            return element;
        }

    }

    private TreeNode<E> root;

    private int size;

    @Override
    public Position<E> addRoot(E e) {
        if (!isEmpty()){
            throw new RuntimeException("The tree has already a root node");
        }
        root=new TreeNode<>(e);
        size++;
        return root;
    }
    /**
     * Check if a given position is valid and return the corresponding TreeNode.
     *
     * @param p The position to check
     * @return The corresponding TreeNode
     * @throws RuntimeException If the position is invalid
     */
    private TreeNode<E> checkPosition(Position<E> p) {
        if (!(p instanceof TreeNode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (TreeNode<E>) p;
    }
    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newnode = new TreeNode<>(element,parent);
        parent.getChildren().add(newnode);
        size++;
        return newnode;
    }
    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        TreeNode<E> parent = checkPosition(p);
        checkPositionOfChildrenList().add(n, parent);
        TreeNode<E> newnode = new TreeNode<>(element,parent);
        parent.getChildren().add(n, newnode);
        size++;
        return newnode;
    };
    private static <E> void checkPositionOfChildrenList(int n, LinkedTree<E>.TreeNode<E> parent) {
        if (n < 0 || n > parent.getChildren().size()) {
            throw new RuntimeException("The position is invalid");
        }
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
       TreeNode<E> node1= checkPosition(p1);
       TreeNode<E> node2=checkPosition(p2);
       E aux=node1.getElement();
       node1.element=node2.getElement();
       node2.element=aux;
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node=checkPosition(p);
        E old=node.getElement();
        node.element=e;
        return old;
    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node=checkPosition(p);
        if(node==root){
            root=null;
            size=0;
        }else{
            TreeNode<E> parent= node.getParent();
            parent.getChildren().remove(node);
            size -=computeSize(node);
        }
    }
    private int computeSize(TreeNode<E> node){
        int size =1;
        for(TreeNode<E> child : node.getChildren()){
            size +=computeSize(child);
        }
        return size;
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node= checkPosition((v));
        LinkedTree<E> tree = new LinkedTree<>();
        tree.root=node;
        tree.size=computeSize(node);
        return tree;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Position<E> root() {
        return root;
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
        List<Position<E>>positions=new ArrayList<>();
        List<Position<E>>=new ArrayList<>();
        breadthOrder(root,positions);
        return positions.iterator();
    }

    private void breadthOrder(TreeNode<E> node, List<Position<E>> positions) {
        if(node!=null){
            List<TreeNode> queue=new ArrayList<>();
            queue.add(node);
            while ((queue.isEmpty())){
                TreeNode<E> toExplore =queue.remove(0);
                positions.add(toExplore);
                queue.addAll(node.getChildren());
            }
        }
    }


    public int size() {
        return size;
    }
}
