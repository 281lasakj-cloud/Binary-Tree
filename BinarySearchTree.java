import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  
 * @version 
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST

    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = null;
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
        return root == null;
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        return size(root);
    }

    private int size(BinaryNode<T> tree){
        if(tree == null){
            return 0;
        }
        return 1 + size(tree.getLeft()) + size(tree.getRight());
    }

    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        BinaryNode<T> newNode = new BinaryNode<>(element);
        if(root == null){
            root = newNode;
            return;
        }

        BinaryNode<T> current = root;

        while(true){
            if(element.compareTo(current.getInfo()) < 0){
                if(current.getLeft() == null){
                    current.setLeft(newNode);
                    return;
                }
                current = current.getLeft();
            }
            else if(element.compareTo(current.getInfo()) > 0){
                if(current.getRight() == null){
                    current.setRight(newNode);
                    return;
                }
                current = current.getRight();
            }
            else{
                return;
            }
        }
    }

    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        BinaryNode<T> current = root;

        while(current != null){
            if(element.compareTo(current.getInfo()) == 0){
                return true;
            }
            else if (element.compareTo(current.getInfo()) < 0)
                current = current.getLeft();
            else
                current = current.getRight();
        }

        return false;
    }

    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    // Returns a list of elements from a preorder traversal
    public List<T> preorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        Stack<BinaryNode<T>> stack = new Stack<BinaryNode<T>>();

        if (root != null)
            stack.push(root);

        while (!stack.empty())
        {
            BinaryNode<T> current = stack.pop();

            list.add(current.getInfo());

            if (current.getRight() != null)
                stack.push(current.getRight());

            if (current.getLeft() != null)
                stack.push(current.getLeft());
        }

        return list;
    }

    // Returns a list of elements from an inorder traversal
    public List<T> inorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        inorder(root, list);
        return list;    
    }

    private void inorder(BinaryNode<T> tree, List<T> list)
    {
        if (tree != null)
        {
            inorder(tree.getLeft(), list);
            list.add(tree.getInfo());
            inorder(tree.getRight(), list);
        }
    }

    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> list = new ArrayList<T>();
        postorder(root, list);
        return list;    
    }

    private void postorder(BinaryNode<T> tree, List<T> list)
    {
        if (tree != null)
        {
            postorder(tree.getLeft(), list);
            postorder(tree.getRight(), list);
            list.add(tree.getInfo());
        }
    }

    // Removes an element e from this BST such that e.compareTo(element) == 0
    public void remove (T element)
    {
        BinaryNode<T> current = root;
        BinaryNode<T> parent = null;

        while (current != null && element.compareTo(current.getInfo()) != 0)
        {
            parent = current;

            if (element.compareTo(current.getInfo()) < 0)
                current = current.getLeft();
            else
                current = current.getRight();
        }

        if (current == null)
            return;

        if (current.getLeft() != null && current.getRight() != null)
        {
            BinaryNode<T> aboveP = current;
            BinaryNode<T> above = current.getRight();

            while (above.getLeft() != null)
            {
                aboveP = above;
                above = above.getLeft();
            }

            current.setInfo(above.getInfo());

            if (aboveP.getLeft() == above)
                aboveP.setLeft(above.getRight());
            else
                aboveP.setRight(above.getRight());

            return;
        }

        BinaryNode<T> child;

        if (current.getLeft() != null)
            child = current.getLeft();
        else
            child = current.getRight();

        if (parent == null)
            root = child;
        else if (parent.getLeft() == current)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    // Restructures this BST to be optimally balanced
    public void balance()
    {
        List<T> list = inorderTraverse();

        root = null;

        int start = 0;
        int end = list.size() - 1;

        Queue<int[]> queue = new LinkedList<int[]>();

        queue.add(new int[]{start, end});

        while (!queue.isEmpty())
        {
            int[] range = queue.remove();

            start = range[0];
            end = range[1];

            if (start <= end)
            {  
                int middle = (start + end) / 2;

                add(list.get(middle));

                queue.add(new int[]{start, middle - 1});
                queue.add(new int[]{middle + 1, end});
            }
        }
    }
}