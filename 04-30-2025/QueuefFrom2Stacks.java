import java.util.*;
class Queue{
    Stack<Integer> orgStack;
    Stack<Integer> QStack;
    public Queue(){
        orgStack=new Stack<>();
        QStack=new Stack<>();
    }

    public void push(int value){
       orgStack.push(value);  

    }
    public int pop(){
         if (empty()) throw new NoSuchElementException("Queue is empty");
        shuffleStacks();
        return QStack.pop();
    }

    public int peek(){
         if (empty()) throw new NoSuchElementException("Queue is empty");
        shuffleStacks();
        return QStack.peek();
    }

    public void shuffleStacks(){
        if(QStack.isEmpty()){
            while(!orgStack.isEmpty()){
               QStack.push(orgStack.pop());
             }
        }
    }

    public int size(){
        return QStack.size()+orgStack.size();
    }

    public boolean empty() {
       return orgStack.isEmpty() && QStack.isEmpty();
    }



}
public class Main {
    public static void main(String[] args) {
        Queue q = new Queue();

        q.push(10);
        q.push(20);
        q.push(30);

        System.out.println("Peek: " + q.peek()); // 10
        System.out.println("Pop: " + q.pop());   // 10
        System.out.println("Pop: " + q.pop());   // 20

        q.push(40);
        System.out.println("Peek: " + q.peek()); // 30
        System.out.println("Size: " + q.size()); // 2
        System.out.println("Is empty: " + q.empty()); // false

        System.out.println("Pop: " + q.pop());   // 30
        System.out.println("Pop: " + q.pop());   // 40
        System.out.println("Is empty: " + q.empty()); // true
    }
}
