/**
 * PROBLEM: Minimal Tree
 * Given a sorted (increasing order) array with unique integer elements, 
 * write an algorithm to create a binary search tree with minimal height.
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    public TreeNode(int value) {
        this.val = value;
    }
}

public class MinimalTree {
    
    // Main method to call
    public TreeNode createMinimalBST(int[] sortedArray) {
        return buildBST(sortedArray, 0, sortedArray.length - 1);
    }

    // Recursive helper method
    private TreeNode buildBST(int[] arr, int start, int end) {
        if (start > end) {
            return null;  // Base case
        }

        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(mid);  // Create root from mid

        // Recursively build left and right subtrees
        node.left = buildBST(arr, start, mid - 1);
        node.right = buildBST(arr, mid + 1, end);

        return node;
    }

    // Utility method to print tree (in-order)
    public void printInOrder(TreeNode root) {
        if (root != null) {
            printInOrder(root.left);
            System.out.print(root.val + " ");
            printInOrder(root.right);
        }
    }

    // Driver
    public static void main(String[] args) {
        MinimalTree treeBuilder = new MinimalTree();
        int[] input = {1, 2, 3, 4, 5, 6, 7};

        TreeNode root = treeBuilder.createMinimalBST(input);

        System.out.println("In-order traversal of constructed BST:");
        treeBuilder.printInOrder(root);
    }
}
