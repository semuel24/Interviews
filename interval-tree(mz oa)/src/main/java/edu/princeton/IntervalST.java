package edu.princeton;

import java.util.LinkedList;
import java.util.List;

public class IntervalST
{

    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        Interval1D interval;      // key
        Integer value;              // associated data
        Node left, right;         // left and right subtrees
        int N;                    // size of subtree rooted at this node
        int max;                  // max endpoint in subtree rooted at this node

        Node(Interval1D interval) {
            this.interval = interval;
            this.value    = 0;
            this.N        = 1;
            this.max      = interval.high;
        }
    }


   /***************************************************************************
    *  BST search
    ***************************************************************************/

    public boolean contains(Interval1D interval) {
        return (get(interval) != null);
    }

    // return value associated with the given key
    // if no such value, return null
    public Integer get(Interval1D interval) {
        return get(root, interval);
    }

    private Integer get(Node x, Interval1D interval) {
        if (x == null)                  return null;
        int cmp = interval.compareTo(x.interval);
        if      (cmp < 0) return get(x.left, interval);
        else if (cmp > 0) return get(x.right, interval);
        else              return x.value;
    }


   /***************************************************************************
    *  randomized insertion
    ***************************************************************************/
    public void put(Interval1D interval) {
        if (contains(interval)) { System.out.println("duplicate"); remove(interval);  }
        root = randomizedInsert(root, interval);
    }

    // make new node the root with uniform probability
    private Node randomizedInsert(Node x, Interval1D interval) {
        if (x == null) return new Node(interval);
        if (Math.random() * size(x) < 1.0) return rootInsert(x, interval);
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0)  x.left  = randomizedInsert(x.left,  interval);
        else          x.right = randomizedInsert(x.right, interval);
        fix(x);
        return x;
    }

    private Node rootInsert(Node x, Interval1D interval) {
        if (x == null) return new Node(interval);
        int cmp = interval.compareTo(x.interval);
        if (cmp < 0) { x.left  = rootInsert(x.left,  interval); x = rotR(x); }
        else         { x.right = rootInsert(x.right, interval); x = rotL(x); }
        return x;
    }


   /***************************************************************************
    *  deletion
    ***************************************************************************/
    private Node joinLR(Node a, Node b) { 
        if (a == null) return b;
        if (b == null) return a;

        if (Math.random() * (size(a) + size(b)) < size(a))  {
            a.right = joinLR(a.right, b);
            fix(a);
            return a;
        }
        else {
            b.left = joinLR(a, b.left);
            fix(b);
            return b;
        }
    }

    // remove and return value associated with given interval;
    // if no such interval exists return null
    private int remove(Interval1D interval) {
        int value = get(interval);
        root = remove(root, interval);
        return value;
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
    
    public void delete(Interval1D interval) {
        //get all overlapped intervals
        LinkedList<Interval1D> oldList = this.searchAll(interval);
        //remove old overlapped intervals
        for (Interval1D x : oldList) {
            remove(x);
        }
        //generate new intervals
        LinkedList<Interval1D> newList = new LinkedList<Interval1D>();
        for(Interval1D x : oldList) {
            List<Interval1D> removed = removeIntervalOverlap(x, interval);
            for(Interval1D _stub : removed) {
                newList.add(_stub);
            }
        }
        
        //insert new intervals
        for(Interval1D x : newList) {
            this.put(x);
        }
    }
    
    //remove x from base
    private List<Interval1D> removeIntervalOverlap(Interval1D base, Interval1D x) {
        List<Interval1D> list = new LinkedList<Interval1D>();
        if(x.low > base.low) {
            if(x.high >= base.high) {
                list.add(new Interval1D(base.low, x.low - 1));
            }
            if(x.high < base.high) {
                list.add(new Interval1D(base.low, x.low - 1));
                list.add(new Interval1D(x.high + 1, base.high));
            }
        } else if(x.low == base.low) {
            if(base.high > x.high) {
                list.add(new Interval1D(x.high + 1, base.high));
            }
        } else {//x.low < base.low
            if(base.high > x.high) {
                list.add(new Interval1D(x.high + 1, base.high));
            }
        }
        return list;
    }


   /***************************************************************************
    *  Interval searching
    ***************************************************************************/

    // return an interval in data structure that intersects the given inteval;
    // return null if no such interval exists
    // running time is proportional to log N
    public boolean search(Interval1D interval) {
        boolean found = false;
        for (Interval1D x : this.searchAll(interval)) {
            if(x.cover(interval)) {
                found = true;
                break;
            }
        }
        return found;
    }

    // return *all* intervals in data structure that intersect the given interval
    // running time is proportional to R log N, where R is the number of intersections
    private LinkedList<Interval1D> searchAll(Interval1D interval) {
        LinkedList<Interval1D> list = new LinkedList<Interval1D>();
        searchAll(root, interval, list);
        return list;
    }

    // look in subtree rooted at x
    private boolean searchAll(Node x, Interval1D interval, LinkedList<Interval1D> list) {
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
    *  useful binary tree functions
    ***************************************************************************/

    // return number of nodes in subtree rooted at x
    public int size() { return size(root); }
    private int size(Node x) { 
        if (x == null) return 0;
        else           return x.N;
    }

    // height of tree (empty tree height = 0)
    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }


   /***************************************************************************
    *  helper BST functions
    ***************************************************************************/

    // fix auxilliar information (subtree count and max fields)
    private void fix(Node x) {
        if (x == null) return;
        x.N = 1 + size(x.left) + size(x.right);
        x.max = max3(x.interval.high, max(x.left), max(x.right));
    }

    private int max(Node x) {
        if (x == null) return Integer.MIN_VALUE;
        return x.max;
    }

    // precondition: a is not null
    private int max3(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    // right rotate
    private Node rotR(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        fix(h);
        fix(x);
        return x;
    }

    // left rotate
    private Node rotL(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        fix(h);
        fix(x);
        return x;
    }


   /***************************************************************************
    *  Debugging functions that test the integrity of the tree
    ***************************************************************************/
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
        // generate N random intervals and insert into data structure
        IntervalST st = new IntervalST();
        Interval1D interval = new Interval1D(1, 5);
        System.out.println(interval);
        st.put(interval);
        
        //add
        interval = new Interval1D(10, 180);
        System.out.println(interval);
        st.put(interval);
        
        interval = new Interval1D(150, 200);
        System.out.println(interval);
        st.put(interval);
        
        interval = new Interval1D(250, 500);
        System.out.println(interval);
        st.put(interval);
 
        //query
        interval = new Interval1D(50, 100);
        System.out.println(interval + ":  " + st.search(interval));
        
        interval = new Interval1D(180, 300);
        System.out.println(interval + ":  " + st.search(interval));
        
        interval = new Interval1D(600, 1000);
        System.out.println(interval + ":  " + st.search(interval));
        
//        st.inorder();
        //delete
        interval = new Interval1D(50, 150);
        st.delete(interval);
        
        interval = new Interval1D(400, 600);
        st.delete(interval);
        
        interval = new Interval1D(50, 150);
        st.delete(interval);
        
        interval = new Interval1D(600, 800);
        st.delete(interval);
        
        System.out.println("##########");
        
        st.inorder();
        //query
        interval = new Interval1D(50, 100);
        System.out.println(interval + ":  " + st.search(interval));

    }
    
    

}
