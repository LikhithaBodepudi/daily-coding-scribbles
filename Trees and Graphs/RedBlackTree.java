/**
 * Red-Black Tree Implementation in Java
 * 
 * A Red-Black Tree is a self-balancing Binary Search Tree with the following properties:
 * 1. Every node is either RED or BLACK
 * 2. The root node is always BLACK
 * 3. RED nodes cannot have RED children (no two RED nodes can be adjacent)
 * 4. Every path from root to NULL contains the same number of BLACK nodes
 * 5. All leaf nodes (NULL nodes) are BLACK
 * 
 * These properties ensure the tree remains balanced with O(log n) operations.
 */

public class RedBlackTree{
     // Color constants
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        int data;
        Node left,right,
        boolean color;
        Node(int data){
            this.data=data;
            this.color=RED;
            this.left=this.right=null; //New nodes are always inserted as RED initially
        }
    }

    private Node root;

    public void insert(int data){
        root=insertHelper(root,data);
        root.color=BLACK; // Root is always BLACK 
    }

    private Node insertHelper(Node node,int data){
         // Standard BST insertion
        if(node==null){
            return new Node(data);
        }

        if(data<node.data){
            node.left=insertHelper(node.left,data);
        }
        else if(data>node.data){
            node.right=insertHelper(node.right,data);
        }
        else{ // duplicates are not allowed
            return node;
        }

        // Red-Black Tree balancing
        // Case 1: Right child is RED but left child is not RED
        if(isRed(node.right) && !isRed(node.left)){
            node = rotateLeft(node);
        }

        // Case 2: Left child is RED and left-left grandchild is also RED
        if (isRed(node.left) && isRed(node.left != null ? node.left.left : null)) {
            node = rotateRight(node);
        }

        // Case 3: Both children are RED
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    /**
     * ROTATION AND COLOR OPERATIONS
     */
    
    // Left rotation - used when right child is RED
    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        
        // Update colors
        newRoot.color = node.color;
        node.color = RED;
        
        return newRoot;
    }
    
    // Right rotation - used when left child and left-left grandchild are RED
    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        
        // Update colors
        newRoot.color = node.color;
        node.color = RED;
        
        return newRoot;
    }
    
    // Flip colors - used when both children are RED
    private void flipColors(Node node) {
        node.color = RED;
        if (node.left != null) node.left.color = BLACK;
        if (node.right != null) node.right.color = BLACK;
    }
    
    /**
     * UTILITY METHODS
     */
    
    // Check if a node is RED
    private boolean isRed(Node node) {
        if (node == null) return false;  // NULL nodes are BLACK
        return node.color == RED;
    }
}