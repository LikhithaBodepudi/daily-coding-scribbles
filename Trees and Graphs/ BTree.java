import java.util.*;

/**
 * Complete B-Tree Implementation in Java
 * 
 * A B-tree is a self-balancing tree data structure that maintains sorted data
 * and allows searches, sequential access, insertions, and deletions in logarithmic time.
 * 
 * Key Properties:
 * 1. All leaves are at the same level
 * 2. Each node has at most (2t-1) keys where t is the minimum degree
 * 3. Each internal node has at most 2t children
 * 4. Each node (except root) has at least (t-1) keys
 * 5. Keys in each node are sorted in ascending order
 */
public class BTree {
    
    /**
     * BTreeNode represents a single node in the B-tree
     */
    public static class BTreeNode {
        int[] keys;           // Array to store keys
        BTreeNode[] children; // Array to store child pointers
        int numKeys;          // Current number of keys in the node
        boolean isLeaf;       // True if node is a leaf, false otherwise
        int minDegree;        // Minimum degree of the tree
        
        /**
         * Constructor for BTreeNode
         * @param minDegree - minimum degree of the B-tree
         * @param isLeaf - whether this node is a leaf
         */
        public BTreeNode(int minDegree, boolean isLeaf) {
            this.minDegree = minDegree;
            this.isLeaf = isLeaf;
            
            // Maximum keys = 2*minDegree - 1
            this.keys = new int[2 * minDegree - 1];
            
            // Maximum children = 2*minDegree
            this.children = new BTreeNode[2 * minDegree];
            
            this.numKeys = 0;
        }
        
        /**
         * Search for a key in the subtree rooted at this node
         * Time Complexity: O(log n)
         * 
         * @param key - key to search for
         * @return true if key is found, false otherwise
         */
        public boolean search(int key) {
            int i = 0;
            
            // Find the first key greater than or equal to the search key
            while (i < numKeys && key > keys[i]) {
                i++;
            }
            
            // If key is found at index i
            if (i < numKeys && key == keys[i]) {
                return true;
            }
            
            // If this is a leaf node, key is not present
            if (isLeaf) {
                return false;
            }
            
            // Recursively search in the appropriate child
            return children[i].search(key);
        }
        
        /**
         * Insert a key into a non-full node
         * This method assumes the node is not full
         * 
         * @param key - key to insert
         */
        public void insertNonFull(int key) {
            int i = numKeys - 1; // Start from the rightmost key
            
            if (isLeaf) {
                // If this is a leaf node, insert the key directly
                
                // Move all keys greater than the new key one position ahead
                while (i >= 0 && keys[i] > key) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                
                // Insert the new key at the found position
                keys[i + 1] = key;
                numKeys++;
                
            } else {
                // If this is an internal node
                
                // Find the child that will have the new key
                while (i >= 0 && keys[i] > key) {
                    i--;
                }
                i++; // Move to the correct child index
                
                // Check if the found child is full
                if (children[i].numKeys == 2 * minDegree - 1) {
                    // Split the child if it's full
                    splitChild(i, children[i]);
                    
                    // After splitting, decide which of the two children
                    // will have the new key
                    if (keys[i] < key) {
                        i++;
                    }
                }
                
                // Recursively insert into the appropriate child
                children[i].insertNonFull(key);
            }
        }
        
        /**
         * Split a full child of this node
         * 
         * @param index - index of the child to split
         * @param fullChild - the full child node to split
         */
        public void splitChild(int index, BTreeNode fullChild) {
            int t = minDegree;
            
            // Create a new node to store (t-1) keys of fullChild
            BTreeNode newChild = new BTreeNode(t, fullChild.isLeaf);
            newChild.numKeys = t - 1;
            
            // Copy the last (t-1) keys of fullChild to newChild
            for (int j = 0; j < t - 1; j++) {
                newChild.keys[j] = fullChild.keys[j + t];
            }
            
            // Copy the last t children of fullChild to newChild
            if (!fullChild.isLeaf) {
                for (int j = 0; j < t; j++) {
                    newChild.children[j] = fullChild.children[j + t];
                }
            }
            
            // Reduce the number of keys in fullChild
            fullChild.numKeys = t - 1;
            
            // Move children of current node one step ahead
            for (int j = numKeys; j >= index + 1; j--) {
                children[j + 1] = children[j];
            }
            
            // Link the new child to this node
            children[index + 1] = newChild;
            
            // Move keys of current node one step ahead
            for (int j = numKeys - 1; j >= index; j--) {
                keys[j + 1] = keys[j];
            }
            
            // Copy the middle key of fullChild to this node
            keys[index] = fullChild.keys[t - 1];
            
            // Increment count of keys in this node
            numKeys++;
        }
        
        /**
         * Delete a key from the subtree rooted at this node
         * 
         * @param key - key to delete
         */
        public void delete(int key) {
            int idx = findKey(key);
            
            if (idx < numKeys && keys[idx] == key) {
                // Key is present in this node
                if (isLeaf) {
                    removeFromLeaf(idx);
                } else {
                    removeFromNonLeaf(idx);
                }
            } else {
                // Key is not present in this node
                if (isLeaf) {
                    // Key is not in the tree
                    return;
                }
                
                // Flag to check if key is in the subtree rooted at last child
                boolean flag = (idx == numKeys);
                
                // Fix child if it has too few keys
                if (children[idx].numKeys < minDegree) {
                    fill(idx);
                }
                
                // If key was in the last child and we merged it with previous child
                if (flag && idx > numKeys) {
                    children[idx - 1].delete(key);
                } else {
                    children[idx].delete(key);
                }
            }
        }
        
        /**
         * Find the index of the first key that is greater than or equal to key
         */
        private int findKey(int key) {
            int idx = 0;
            while (idx < numKeys && keys[idx] < key) {
                idx++;
            }
            return idx;
        }
        
        /**
         * Remove key from leaf node at given index
         */
        private void removeFromLeaf(int idx) {
            // Move all keys after idx one position back
            for (int i = idx + 1; i < numKeys; i++) {
                keys[i - 1] = keys[i];
            }
            numKeys--;
        }
        
        /**
         * Remove key from non-leaf node at given index
         */
        private void removeFromNonLeaf(int idx) {
            int key = keys[idx];
            
            // Case 1: Left child has at least minDegree keys
            if (children[idx].numKeys >= minDegree) {
                int pred = getPredecessor(idx);
                keys[idx] = pred;
                children[idx].delete(pred);
            }
            // Case 2: Right child has at least minDegree keys
            else if (children[idx + 1].numKeys >= minDegree) {
                int succ = getSuccessor(idx);
                keys[idx] = succ;
                children[idx + 1].delete(succ);
            }
            // Case 3: Both children have minDegree-1 keys
            else {
                merge(idx);
                children[idx].delete(key);
            }
        }
        
        /**
         * Get predecessor of key at index idx
         */
        private int getPredecessor(int idx) {
            BTreeNode cur = children[idx];
            while (!cur.isLeaf) {
                cur = cur.children[cur.numKeys];
            }
            return cur.keys[cur.numKeys - 1];
        }
        
        /**
         * Get successor of key at index idx
         */
        private int getSuccessor(int idx) {
            BTreeNode cur = children[idx + 1];
            while (!cur.isLeaf) {
                cur = cur.children[0];
            }
            return cur.keys[0];
        }
        
        /**
         * Fill child at index idx which has fewer than minDegree-1 keys
         */
        private void fill(int idx) {
            // If previous sibling has more than minDegree-1 keys, borrow from it
            if (idx != 0 && children[idx - 1].numKeys >= minDegree) {
                borrowFromPrev(idx);
            }
            // If next sibling has more than minDegree-1 keys, borrow from it
            else if (idx != numKeys && children[idx + 1].numKeys >= minDegree) {
                borrowFromNext(idx);
            }
            // Merge with sibling
            else {
                if (idx != numKeys) {
                    merge(idx);
                } else {
                    merge(idx - 1);
                }
            }
        }
        
        /**
         * Borrow a key from previous sibling
         */
        private void borrowFromPrev(int idx) {
            BTreeNode child = children[idx];
            BTreeNode sibling = children[idx - 1];
            
            // Move all keys in child one step ahead
            for (int i = child.numKeys - 1; i >= 0; i--) {
                child.keys[i + 1] = child.keys[i];
            }
            
            // Move all children in child one step ahead
            if (!child.isLeaf) {
                for (int i = child.numKeys; i >= 0; i--) {
                    child.children[i + 1] = child.children[i];
                }
            }
            
            // Move key from parent to child
            child.keys[0] = keys[idx - 1];
            
            // Move child pointer from sibling to child
            if (!child.isLeaf) {
                child.children[0] = sibling.children[sibling.numKeys];
            }
            
            // Move key from sibling to parent
            keys[idx - 1] = sibling.keys[sibling.numKeys - 1];
            
            child.numKeys++;
            sibling.numKeys--;
        }
        
        /**
         * Borrow a key from next sibling
         */
        private void borrowFromNext(int idx) {
            BTreeNode child = children[idx];
            BTreeNode sibling = children[idx + 1];
            
            // Move key from parent to child
            child.keys[child.numKeys] = keys[idx];
            
            // Move child pointer from sibling to child
            if (!child.isLeaf) {
                child.children[child.numKeys + 1] = sibling.children[0];
            }
            
            // Move key from sibling to parent
            keys[idx] = sibling.keys[0];
            
            // Move all keys in sibling one step back
            for (int i = 1; i < sibling.numKeys; i++) {
                sibling.keys[i - 1] = sibling.keys[i];
            }
            
            // Move all children in sibling one step back
            if (!sibling.isLeaf) {
                for (int i = 1; i <= sibling.numKeys; i++) {
                    sibling.children[i - 1] = sibling.children[i];
                }
            }
            
            child.numKeys++;
            sibling.numKeys--;
        }
        
        /**
         * Merge child at idx with its sibling
         */
        private void merge(int idx) {
            BTreeNode child = children[idx];
            BTreeNode sibling = children[idx + 1];
            
            // Pull key from current node and merge with right sibling
            child.keys[minDegree - 1] = keys[idx];
            
            // Copy keys from sibling to child
            for (int i = 0; i < sibling.numKeys; i++) {
                child.keys[i + minDegree] = sibling.keys[i];
            }
            
            // Copy children from sibling to child
            if (!child.isLeaf) {
                for (int i = 0; i <= sibling.numKeys; i++) {
                    child.children[i + minDegree] = sibling.children[i];
                }
            }
            
            // Move keys after idx in current node one step back
            for (int i = idx + 1; i < numKeys; i++) {
                keys[i - 1] = keys[i];
            }
            
            // Move children after (idx+1) in current node one step back
            for (int i = idx + 2; i <= numKeys; i++) {
                children[i - 1] = children[i];
            }
            
            child.numKeys += sibling.numKeys + 1;
            numKeys--;
        }
        
        /**
         * Traverse and print all keys in the subtree rooted at this node
         */
        public void traverse() {
            int i;
            for (i = 0; i < numKeys; i++) {
                // Traverse the subtree before printing the key
                if (!isLeaf) {
                    children[i].traverse();
                }
                System.out.print(keys[i] + " ");
            }
            
            // Traverse the subtree after the last key
            if (!isLeaf) {
                children[i].traverse();
            }
        }
    }
    
    // Root of the B-tree
    private BTreeNode root;
    private int minDegree; // Minimum degree
    
    /**
     * Constructor for B-tree
     * @param minDegree - minimum degree of the tree
     */
    public BTree(int minDegree) {
        this.minDegree = minDegree;
        this.root = null;
    }
    
    /**
     * Search for a key in the B-tree
     * @param key - key to search
     * @return true if found, false otherwise
     */
    public boolean search(int key) {
        return (root == null) ? false : root.search(key);
    }
    
    /**
     * Insert a key into the B-tree
     * @param key - key to insert
     */
    public void insert(int key) {
        if (root == null) {
            // Create root for empty tree
            root = new BTreeNode(minDegree, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            // Check if root is full
            if (root.numKeys == 2 * minDegree - 1) {
                // Create new root
                BTreeNode newRoot = new BTreeNode(minDegree, false);
                
                // Make old root as child of new root
                newRoot.children[0] = root;
                
                // Split the old root and move one key to new root
                newRoot.splitChild(0, root);
                
                // New root has two children now. Decide which child will have new key
                int i = 0;
                if (newRoot.keys[0] < key) {
                    i++;
                }
                newRoot.children[i].insertNonFull(key);
                
                // Change root
                root = newRoot;
            } else {
                // Insert into non-full root
                root.insertNonFull(key);
            }
        }
    }
    
    /**
     * Delete a key from the B-tree
     * @param key - key to delete
     */
    public void delete(int key) {
        if (root == null) {
            return;
        }
        
        root.delete(key);
        
        // If root has 0 keys, make its first child the new root
        if (root.numKeys == 0) {
            if (root.isLeaf) {
                root = null;
            } else {
                root = root.children[0];
            }
        }
    }
    
    /**
     * Traverse and print the B-tree
     */
    public void traverse() {
        if (root != null) {
            root.traverse();
        }
        System.out.println();
    }
    
    /**
     * Main method with comprehensive example
     */
    public static void main(String[] args) {
        System.out.println("=== B-TREE DEMONSTRATION ===");
        System.out.println("Creating B-tree with minimum degree 3");
        System.out.println("This means:");
        System.out.println("- Each node can have at most 5 keys (2*3-1)");
        System.out.println("- Each node can have at most 6 children (2*3)");
        System.out.println("- Each node (except root) must have at least 2 keys (3-1)");
        System.out.println();
        
        BTree btree = new BTree(3);
        
        // Insert example with step-by-step explanation
        int[] keys = {10, 20, 5, 6, 12, 30, 7, 17};
        
        System.out.println("=== INSERTION PROCESS ===");
        for (int key : keys) {
            System.out.println("Inserting: " + key);
            btree.insert(key);
            System.out.print("Tree after insertion: ");
            btree.traverse();
            System.out.println();
        }
        
        System.out.println("=== SEARCH OPERATIONS ===");
        int[] searchKeys = {6, 15, 20, 25};
        for (int key : searchKeys) {
            boolean found = btree.search(key);
            System.out.println("Searching for " + key + ": " + (found ? "FOUND" : "NOT FOUND"));
        }
        System.out.println();
        
        System.out.println("=== DELETION PROCESS ===");
        int[] deleteKeys = {6, 12, 10};
        for (int key : deleteKeys) {
            System.out.println("Deleting: " + key);
            btree.delete(key);
            System.out.print("Tree after deletion: ");
            btree.traverse();
            System.out.println();
        }
        
        System.out.println("=== FINAL TREE ===");
        System.out.print("Final tree: ");
        btree.traverse();
    }
}