public class AVLTree {
    
    // Node class representing each node in the AVL tree
    class Node {
        int data;
        Node left, right;
        int height;
        
        Node(int data) {
            this.data = data;
            this.height = 1; // New nodes start with height 1
        }
    }
    
    private Node root;
    
    // Get height of a node (handles null nodes)
    private int getHeight(Node node) {
        if (node == null) return 0;
        return node.height;
    }
    
    // Calculate balance factor of a node
    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }
    
    // Update height of a node based on its children
    private void updateHeight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }
    
    // RIGHT ROTATION (RR Rotation)
    // Used when left subtree is heavier (balance factor = +2)
    private Node rightRotate(Node y) {
        Node x = y.left;        // x becomes new root
        Node temp = x.right;    // Store x's right subtree
        
        // Perform rotation
        x.right = y;    // y becomes right child of x
        y.left = temp;  // x's right subtree becomes y's left subtree
        
        // Update heights (order matters: y first, then x)
        updateHeight(y);
        updateHeight(x);
        
        return x; // x is the new root
    }
    
    // LEFT ROTATION (LL Rotation)
    // Used when right subtree is heavier (balance factor = -2)
    private Node leftRotate(Node x) {
        Node y = x.right;       // y becomes new root
        Node temp = y.left;     // Store y's left subtree
        
        // Perform rotation
        y.left = x;     // x becomes left child of y
        x.right = temp; // y's left subtree becomes x's right subtree
        
        // Update heights (order matters: x first, then y)
        updateHeight(x);
        updateHeight(y);
        
        return y; // y is the new root
    }
    
    // Public method to insert a value
    public void insert(int data) {
        root = insertHelper(root, data);
    }
    
    // Recursive helper method for insertion
    private Node insertHelper(Node node, int data) {
        // Step 1: Perform normal BST insertion
        if (node == null) {
            return new Node(data);
        }
        
        if (data < node.data) {
            node.left = insertHelper(node.left, data);
        } else if (data > node.data) {
            node.right = insertHelper(node.right, data);
        } else {
            // Duplicate values not allowed
            return node;
        }
        
        // Step 2: Update height of current node
        updateHeight(node);
        
        // Step 3: Get balance factor
        int balanceFactor = getBalanceFactor(node);
        
        // Step 4: If unbalanced, perform appropriate rotation
        
        // Left-Left Case (Right Rotation)
        if (balanceFactor > 1 && data < node.left.data) {
            return rightRotate(node);
        }
        
        // Right-Right Case (Left Rotation)
        if (balanceFactor < -1 && data > node.right.data) {
            return leftRotate(node);
        }
        
        // Left-Right Case (Left rotation on left child, then right rotation)
        if (balanceFactor > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right-Left Case (Right rotation on right child, then left rotation)
        if (balanceFactor < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        // Return unchanged node if balanced
        return node;
    }
    
    // Find minimum value node (used in deletion)
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // Public method to delete a value
    public void delete(int data) {
        root = deleteHelper(root, data);
    }
    
    // Recursive helper method for deletion
    private Node deleteHelper(Node node, int data) {
        // Step 1: Perform normal BST deletion
        if (node == null) {
            return node;
        }
        
        if (data < node.data) {
            node.left = deleteHelper(node.left, data);
        } else if (data > node.data) {
            node.right = deleteHelper(node.right, data);
        } else {
            // Node to be deleted found
            if (node.left == null || node.right == null) {
                // Node has 0 or 1 child
                Node temp = (node.left != null) ? node.left : node.right;
                
                if (temp == null) {
                    // No child case
                    temp = node;
                    node = null;
                } else {
                    // One child case
                    node = temp;
                }
            } else {
                // Node has 2 children
                Node temp = findMin(node.right); // Find inorder successor
                node.data = temp.data; // Copy successor's data
                node.right = deleteHelper(node.right, temp.data); // Delete successor
            }
        }
        
        if (node == null) return node;
        
        // Step 2: Update height
        updateHeight(node);
        
        // Step 3: Get balance factor
        int balanceFactor = getBalanceFactor(node);
        
        // Step 4: Perform rotations if unbalanced
        
        // Left-Left Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }
        
        // Left-Right Case
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right-Right Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        
        // Right-Left Case
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    // Search for a value
    public boolean search(int data) {
        return searchHelper(root, data);
    }
    
    private boolean searchHelper(Node node, int data) {
        if (node == null) return false;
        
        if (data == node.data) return true;
        else if (data < node.data) return searchHelper(node.left, data);
        else return searchHelper(node.right, data);
    }
    
    // Traversal methods
    public void inorderTraversal() {
        System.out.print("Inorder: ");
        inorderHelper(root);
        System.out.println();
    }
    
    private void inorderHelper(Node node) {
        if (node != null) {
            inorderHelper(node.left);
            System.out.print(node.data + " ");
            inorderHelper(node.right);
        }
    }
    
    public void preorderTraversal() {
        System.out.print("Preorder: ");
        preorderHelper(root);
        System.out.println();
    }
    
    private void preorderHelper(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorderHelper(node.left);
            preorderHelper(node.right);
        }
    }
    
    // Display tree structure with balance factors
    public void displayTree() {
        displayTreeHelper(root, "", true);
    }
    
    private void displayTreeHelper(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("└── ");
                indent += "    ";
            } else {
                System.out.print("├── ");
                indent += "│   ";
            }
            System.out.println(node.data + " (BF: " + getBalanceFactor(node) + ", H: " + node.height + ")");
            
            if (node.left != null || node.right != null) {
                displayTreeHelper(node.left, indent, node.right == null);
                displayTreeHelper(node.right, indent, true);
            }
        }
    }
}

public class AVLTreeExample {
    public static void main(String[] args) {
        AVLTree avl = new AVLTree();
        
        System.out.println("=== AVL Tree Example ===\n");
        
        // Insert values: 10, 20, 30, 40, 50, 25
        int[] values = {10, 20, 30, 40, 50, 25};
        
        for (int value : values) {
            System.out.println("Inserting: " + value);
            avl.insert(value);
            avl.displayTree();
            System.out.println();
        }
        
        System.out.println("Final tree traversals:");
        avl.inorderTraversal();   // Should print sorted order
        avl.preorderTraversal();
        
        System.out.println("\nSearching for values:");
        System.out.println("Search 25: " + avl.search(25));
        System.out.println("Search 35: " + avl.search(35));
        
        System.out.println("\nDeleting 30:");
        avl.delete(30);
        avl.displayTree();
        avl.inorderTraversal();
    }
}

/**
## Time and Space Complexity

| Operation | Time Complexity | Space Complexity |
|-----------|-----------------|------------------|
| Search    | O(log n)        | O(log n) - recursion stack |
| Insert    | O(log n)        | O(log n) - recursion stack |
| Delete    | O(log n)        | O(log n) - recursion stack |
| Traversal | O(n)            | O(log n) - recursion stack |
 */