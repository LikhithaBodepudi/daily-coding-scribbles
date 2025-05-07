import java.util.*;
class MinStack {
    private class StackNode {
        private int value;
        private int minValue;
        
        public StackNode(int value, int minValue) {
            this.value = value;
            this.minValue = minValue;
        }
    }
    
    private Stack<StackNode> stack;
    
    public MinStack() {
        stack = new Stack<>();
    }
    
    public void push(int value) {
        int newMin = stack.isEmpty() ? value : Math.min(value, stack.peek().minValue);
        stack.push(new StackNode(value, newMin));
    }
    
    public int pop() {
        if (stack.isEmpty()) throw new EmptyStackException();
        return stack.pop().value;
    }
    
    public int top() {
        if (stack.isEmpty()) throw new EmptyStackException();
        return stack.peek().value;
    }
    
    public int min() {
        if (stack.isEmpty()) throw new EmptyStackException();
        return stack.peek().minValue;
    }
}
class Main {
    public static void main(String[] args) {
        MinStack obj=new MinStack();
        obj.push(29);
       obj.push(20);
       obj.push(12);
       obj.push(2);
       obj.push(3);
       
       
              System.out.println(obj.pop());
              System.out.println(obj.top());
              System.out.println(obj.min());
              System.out.println(obj.pop());
               System.out.println(obj.pop());
                System.out.println(obj.pop());
                System.out.println(obj.min());
    }
}