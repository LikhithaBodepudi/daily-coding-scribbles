import java.util.*;
import java.util.*;
class MinStack{

    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    public MinStack(){
        mainStack=new Stack<>();
        minStack=new Stack<>();
    }

     public void push(int value){

        mainStack.push(value);
        
        if(minStack.isEmpty() || value<=minStack.peek()){
            minStack.push(value);
        }

     }

     public int pop(){
        if(mainStack.isEmpty()) throw new EmptyStackException();

        int value=mainStack.pop();
        if(value==minStack.peek()){
            minStack.pop();
        }
        return value;
     }

     public int peek(){
        if(mainStack.isEmpty()) throw new EmptyStackException();

        return mainStack.peek();
     }

     public int min(){
        if(minStack.isEmpty()) throw new EmptyStackException();
        return minStack.peek();
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
              System.out.println(obj.peek());
              System.out.println(obj.min());
              System.out.println(obj.pop());
               System.out.println(obj.pop());
                System.out.println(obj.pop());
                System.out.println(obj.min());
    }
}