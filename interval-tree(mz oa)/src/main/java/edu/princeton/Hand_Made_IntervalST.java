package edu.princeton;

import java.util.LinkedList;

public class Hand_Made_IntervalST<Value>
{

    private Node root; // root of the BST

    // BST helper node data type
    private class Node
    {
        Interval1D interval; // key

        Value value; // associated data

        Node left, right; // left and right subtrees

        int max; // max endpoint in subtree rooted at this node

        Node(Interval1D interval, Value value)
        {
            this.interval = interval;
            this.value = value;
            this.max = interval.high;
        }
    }

    /***************************************************************************
     * BST search
     ***************************************************************************/

    public boolean contains(Interval1D interval)
    {
        return (get(interval) != null);
    }

    // return value associated with the given key
    // if no such value, return null
    public Value get(Interval1D interval)
    {
        return get(root, interval);
    }

    private Value get(Node x, Interval1D interval)
    {
        if (x == null)
            return null;
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0)
            return get(x.left, interval);
        else if (cmp > 0)
            return get(x.right, interval);
        else
            return x.value;
    }

    /***************************************************************************
     * randomized insertion
     ***************************************************************************/
    public void put(Interval1D interval, Value value)
    {
        if (contains(interval)) {
            System.out.println("duplicate");
            return;
        }
        if(root == null) {
            root = new Node(interval, value);
            return;
        }
        Node _node = root;
        Node x = new Node(interval, value);
        while(_node != null) {
            _node.max = Math.max(_node.max, x.max);//adjust max value along the path
            int cmp = interval.compareTo(_node.interval);
            if(cmp < 0) {
                if(_node.left == null) {
                    _node.left = x;
                    break;
                }
                _node = _node.left;
            }else if(cmp > 0) {
                if(_node.right == null) {
                    _node.right = x;
                   break; 
                }
                _node = _node.right;
            }
        }
    }
   
    /***************************************************************************
     * deletion
     ***************************************************************************/

    // remove and return value associated with given interval;
    // if no such interval exists return null
    public Value remove(Interval1D interval) {
        Value value = get(interval);
        root = remove(root, interval);
        return value;
    }
    
    private Node joinLR(Node a, Node b) { 
        if (a == null) return b;
        if (b == null) return a;

        //lean left
        a.right = joinLR(a.right, b);
        fix(a);
        return a;
    }

    private Node remove(Node h, Interval1D interval) {
        if (h == null) return null;
        int cmp = interval.compareTo(h.interval);
        if      (cmp < 0) h.left  = remove(h.left,  interval);
        else if (cmp > 0) h.right = remove(h.right, interval);
        else              h = joinLR(h.left, h.right);
        fix(h);
        return h;
    }

    /***************************************************************************
     * Interval searching
     ***************************************************************************/

    // return an interval in data structure that intersects the given inteval;
    // return null if no such interval exists
    // running time is proportional to log N
    public Interval1D search(Interval1D interval)
    {
        return search(root, interval);
    }

    // look in subtree rooted at x
    public Interval1D search(Node x, Interval1D interval)
    {
        while (x != null) {
            if (interval.intersects(x.interval))
                return x.interval;
            else if (x.left == null)
                x = x.right;
            else if (x.left.max < interval.low)
                x = x.right;
            else
                x = x.left;
        }
        return null;
    }

    // return *all* intervals in data structure that intersect the given interval
    // running time is proportional to R log N, where R is the number of intersections
    public Iterable<Interval1D> searchAll(Interval1D interval)
    {
        LinkedList<Interval1D> list = new LinkedList<Interval1D>();
        searchAll(root, interval, list);
        return list;
    }

    // look in subtree rooted at x
    private boolean searchAll(Node x, Interval1D interval, LinkedList<Interval1D> list)
    {
        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;
        if (x == null)
            return false;
        if (interval.intersects(x.interval)) {
            list.add(x.interval);
            found1 = true;
        }
        if (x.left != null && x.left.max >= interval.low)
            found2 = searchAll(x.left, interval, list);
        if (found2 || x.left == null || x.left.max < interval.low)
            found3 = searchAll(x.right, interval, list);
        return found1 || found2 || found3;
    }

    /***************************************************************************
     * helper BST functions
     ***************************************************************************/

    // fix auxilliar information (subtree count and max fields)
    private void fix(Node x)
    {
        if (x == null)
            return;
        x.max = max3(x.interval.high, max(x.left), max(x.right));
    }

    private int max(Node x)
    {
        if (x == null)
            return Integer.MIN_VALUE;
        return x.max;
    }

    // precondition: a is not null
    private int max3(int a, int b, int c)
    {
        return Math.max(a, Math.max(b, c));
    }

    public void inorder() {
        doInorder(root);
    }
    
    private void doInorder(Node _node) {
        if(_node == null) {
            return;
        }
        doInorder(_node.left);
        System.out.println("max=" + _node.max + " _interval:" + _node.interval);
        doInorder(_node.right);
    }

    /***************************************************************************
     * test client
     ***************************************************************************/
    public static void main(String[] args)
    {
        int N = 5;

        // generate N random intervals and insert into data structure
        Hand_Made_IntervalST<String> st = new Hand_Made_IntervalST<String>();
        Interval1D interval = new Interval1D(1, 5);
        System.out.println(interval);
        st.put(interval, "value");
        
        interval = new Interval1D(10, 180);
        System.out.println(interval);
        st.put(interval, "value");
        
        interval = new Interval1D(150, 200);
        System.out.println(interval);
        st.put(interval, "value");
        
        interval = new Interval1D(250, 500);
        System.out.println(interval);
        st.put(interval, "value");
 
        //query
        interval = new Interval1D(50, 100);
        System.out.println(interval + ":  " + st.search(interval));
        
        interval = new Interval1D(180, 300);
        System.out.println(interval + ":  " + st.search(interval));
        
        interval = new Interval1D(600, 1000);
        System.out.println(interval + ":  " + st.search(interval));
        
        //delete
        interval = new Interval1D(4, 7);
        st.remove(interval);
        
        // check by inorder
        System.out.println("*****");
        st.inorder();

        interval = new Interval1D(2, 4);
        System.out.println(interval + ":  " + st.search(interval));
        System.out.print(interval + ":  ");
        for (Interval1D x : st.searchAll(interval))
            System.out.print(x + " ");

    }
    
    

}
