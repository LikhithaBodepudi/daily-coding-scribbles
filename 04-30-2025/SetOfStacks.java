/*SetOfStacks should be composed of several stacks and 
  should create a new stack once the previous one exceeds capacity.
  FOLLOW UP
  Implement a function popAt(int index) which performs a pop operation on a specific sub-stack 
*/


import java.util.*;
public class SetOfStacks{
    private ArrayList<Stack<Integer>> stacks;
    private int capacity;

    public SetOfStacks(int capacity){
        this.stacks=new ArrayList<>();
        this.capacity=capacity;
    }

     /* Adds new stack to list of stacks*/
    public void addStack(){
        stacks.add(new Stack<>());
    }

    private Stack<Integer> getLastStack(){
        if(stacks.isEmpty()) {
            addStack();
        }
        return stacks.get(stacks.size()-1);
    }

    public void push(int value){
         Stack<Integer> last = getLastStack();

        if(last.size()>=capacity){
            addStack();
            last=getLastStack();
        }
        last.push(value);
    }

    public int pop(){
        if(stacks.isEmpty()) throw new EmptyStackException();
        Stack<Integer> last=stacks.get(stacks.size()-1);
        int value=last.pop();
        if(last.isEmpty()){
            stacks.remove(stacks.size()-1);
        }
        return value;
    }

    public int peek(){
         if(stacks.isEmpty()) throw new EmptyStackException();
        Stack<Integer> last=stacks.get(stacks.size()-1);
        return last.peek();
    }

    public int popAt(int index){
        if(index<0 || index>=stacks.size()){
            throw new IndexOutOfBoundsException("substack index out of bounds");
        }

        Stack<Integer> stack =  stacks.get(index);

        if(stack.isEmpty()){
            throw new EmptyStackException();
        }

         // Pop from the specified stack
        int value = stack.pop();

        shiftsElements(index);

        return value;
    }

    /**
     * Shifts elements from higher stacks to fill the gap created by popAt.
     * This ensures all stacks except possibly the last one are at full capacity.
     */

    private void shiftsElements(int index){
        for(int i=index;i<stacks.size()-1;i++){
            Stack<Integer> currentStack=stacks.get(i);
            Stack<Integer> nextStack=stacks.get(i+1);

            if(currentStack.size()>=capacity){
                continue;
            }

            while(!nextStack.isEmpty()){
                Stack<Integer> tempStack = new Stack<>();
                while(nextStack.size()>1){
                    tempStack.push(nextStack.pop());
                }
                int bottomEl=nextStack.pop();

                while (!tempStack.isEmpty()) {
                    nextStack.push(tempStack.pop());
                }

                // Push the bottom element to the current stack
                currentStack.push(bottomEl);
            }
        }

    }

    public int size(){
        int count=0;
        for(Stack<Integer> stack:stacks){
            count+=stack.size();
        }
        return count;
    }

     public void printStacks() {
        System.out.println("Total stacks: " + stacks.size());
        for (int i = 0; i < stacks.size(); i++) {
            Stack<Integer> stack = stacks.get(i);
            System.out.print("Stack " + i + " (size=" + stack.size() + "): ");
            
            // Need to make a copy to print without destroying the stack
            Stack<Integer> tempStack = new Stack<>();
            ArrayList<Integer> elements = new ArrayList<>();
            
            // Pop all elements into temp stack
            while (!stack.isEmpty()) {
                int val = stack.pop();
                tempStack.push(val);
                elements.add(0, val); // Add to beginning of list to maintain order
            }
            
            // Push elements back to original stack
            while (!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }
            
            // Print the elements
            System.out.println(elements);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SetOfStacks stacks = new SetOfStacks(3); // Each stack can hold 3 elements
        
        // Push some elements
        for (int i = 1; i <= 10; i++) {
            stacks.push(i);
            System.out.println("Pushed " + i + ", size is now " + stacks.size());
        }
        
        // Print stack state
        stacks.printStacks();
        
        // Test regular pop
        System.out.println("Popped: " + stacks.pop());
        System.out.println("Popped: " + stacks.pop());
        
        // Print stack state
        stacks.printStacks();
        
        // Test popAt
        System.out.println("PopAt stack 0: " + stacks.popAt(0));
        stacks.printStacks();
        
        System.out.println("PopAt stack 1: " + stacks.popAt(1));
        stacks.printStacks();
        
       
        System.out.println("Re-enabling rollover behavior:");

        System.out.println("PopAt stack 0 (with rollover): " + stacks.popAt(0));
        stacks.printStacks();
       
    }
}