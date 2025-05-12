/*
Sort Stack: Write a program to sort a stack such that the smallest items are on the top. 
You can use an additional temporary stack, but you may not copy the elements into any other data structure (such as an array). 
The stack supports the following operations: push, pop, peek, and isEmpty.
*/
import java.util.Stack;

public class SortStack {
    
    // Corrected method name and syntax
    public static void sortStack(Stack<Integer> stack) {
        Stack<Integer> tempStack = new Stack<>();
        
        while (!stack.isEmpty()) {
            int temp = stack.pop();
            
            // Move elements from tempStack back to stack if they are greater than temp
            while (!tempStack.isEmpty() && temp > tempStack.peek()) {
                stack.push(tempStack.pop());
            }
            tempStack.push(temp);
        }
        
        // Put elements back into original stack in sorted order (smallest on top)
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(34);
        stack.push(3);
        stack.push(31);
        stack.push(98);
        stack.push(92);
        stack.push(23);

        System.out.println("Original Stack: " + stack);
        sortStack(stack);
        System.out.println("Sorted Stack (smallest on top): " + stack);
    }
}
