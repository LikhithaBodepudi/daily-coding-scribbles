/**
 * PROBLEM: List of Depths (aka Level-wise Linked Lists)
 *
 * Given a binary tree, create a list of linked lists where each linked list contains 
 * all the nodes at a particular depth in the tree.
 *
 * For example, if the tree has depth D, then the result should be a list of D linked lists,
 * where:
 * - The first linked list contains the root node (depth 0),
 * - The second linked list contains all nodes at depth 1 (i.e., the root’s children),
 * - The third linked list contains all nodes at depth 2 (i.e., grandchildren of root), and so on.
 *
 * In simpler terms: perform a level-order traversal (BFS or DFS) and group the nodes 
 * by their level into separate linked lists.
 *
 * INPUT: A binary tree (with any number of nodes)
 * OUTPUT: A list of linked lists, where each list contains all nodes at that tree level
 *
 * EXAMPLE:
 *        1
 *       / \
 *      2   3
 *     /   / \
 *    4   5   6
 *
 * Output:
 * [
 *   [1],        // depth 0
 *   [2, 3],     // depth 1
 *   [4, 5, 6]   // depth 2
 * ]
 */
import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    
    TreeNode(int x) {
        val = x;
    }
}

public class ListOfDepths {
    
    public static List<LinkedList<TreeNode>> createLevelLinkedLists(TreeNode root) {
        List<LinkedList<TreeNode>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Number of nodes at current level
            LinkedList<TreeNode> levelList = new LinkedList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                levelList.add(current);
                
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            
            result.add(levelList);
        }
        
        return result;
    }

    // For testing purposes
    public static void printLevelLists(List<LinkedList<TreeNode>> lists) {
        int depth = 0;
        for (LinkedList<TreeNode> list : lists) {
            System.out.print("Level " + depth + ": ");
            for (TreeNode node : list) {
                System.out.print(node.val + " ");
            }
            System.out.println();
            depth++;
        }
    }

    public static void main(String[] args) {
        // Build a sample tree:
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        
        List<LinkedList<TreeNode>> lists = createLevelLinkedLists(root);
        printLevelLists(lists);
    }
}
/**
 * Time Complexity: O(n) — Each node is visited once.

Space Complexity:

O(n) for storing the output lists.

O(w) auxiliary space in the queue where w = max width of the tree (worst case O(n)).
 */

/**
 * we can also do this with Depth-First Search, keeping track of depth during recursion
 * The idea is to perform a pre-order traversal (Node → Left → Right) and pass the current depth (or level) as an argument to the recursive function.

For each node, check if a list for the current depth exists:

If not, create a new list.

Then, add the current node to that list.

This way, you build a list of nodes for each depth as you recurse.

 import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) {
        this.val = val;
    }
}

public class ListOfDepthsDFS {
    
    public static List<LinkedList<TreeNode>> createLevelLinkedLists(TreeNode root) {
        List<LinkedList<TreeNode>> result = new ArrayList<>();
        dfsHelper(root, 0, result);
        return result;
    }
    
    private static void dfsHelper(TreeNode node, int level, List<LinkedList<TreeNode>> result) {
        if (node == null) return;
        
        // Create a new level if it doesn't exist
        if (level == result.size()) {
            result.add(new LinkedList<>());
        }
        
        result.get(level).add(node);
        
        dfsHelper(node.left, level + 1, result);
        dfsHelper(node.right, level + 1, result);
    }

    public static void printLevelLists(List<LinkedList<TreeNode>> lists) {
        int depth = 0;
        for (LinkedList<TreeNode> list : lists) {
            System.out.print("Level " + depth + ": ");
            for (TreeNode node : list) {
                System.out.print(node.val + " ");
            }
            System.out.println();
            depth++;
        }
    }

    public static void main(String[] args) {
        // Example tree:
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);

        List<LinkedList<TreeNode>> lists = createLevelLinkedLists(root);
        printLevelLists(lists);
    }
}

 */