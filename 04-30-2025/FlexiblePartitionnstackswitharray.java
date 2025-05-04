class FLexibleMultiStack{
    private class StackInfo {
        public int start;// Index where the stack starts
        public int capacity; // Total capacity allocated to this stack
        public int size;//current no.of elements in the stack 
       
        public stackInfo(int start,int capacity){
           this.start=start;
           this.capacity=capacity;
           this.size=0;
        }

        public boolean isWithinCapacaity(int index){
            if(index<0 || index>=values.length){
                return false;
            }
            int contiguousIndex=index<start?index+values.length:index; //even valid for circular  condition
            int end = start+ capacity;
            return start<=contiguousIndex && contiguousIndex<end;
        }

        public int lastCapacityIndex(int index){
            return adjustIndex(start+capacity-1);
        }

        public int lastElementIndex(int index){
            return adjustIndex(start+size-1);
        }

        public boolean isFull(){
            return size==capacity;
        }

        public boolean isEmpty(){
            return size==0;
        }
    }

    // Exception classes for stack operations
    public class FullStackException extends Exception {
        private static final long serialVersionUID = 1L;
    }
    
    public class EmptyStackException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    private stackInfo[] info;  // Array of stack metadata
    private int[] values;   // The actual array holding all stack values

    public FLexibleMultiStack(int NumberofStacks,int defaultsize){
        // Create metadata for all the stacks.
        info =new stackInfo[NumberofStacks]; // object ofclass stackInfo
        for(int i=0;i<NumberofStacks;i++){
            info[i]=new stackInfo(defaultsize*i,defaultsize);
        }
        values=new int[defaultsize*NumberofStacks];

    }

    /**
     * Push value onto stack num, shifting/expanding stacks as necessary.
     * Throws exception if all stacks are full. 
    **/
     
    public void push(int stackNum,int value) throws FullStackException{
        if (allStacksAreFull()) {
            throw new FullStackException();
        }
       
       stackInfo stack =info[stackNum];
       if(stack.isFull()){
        expand(stackNum);
       }

        // If this stack is full, expand it.

        stack.size++;
        values[stack.lastElementIndex()]=value;
    } 


    public int pop(int stackNum) throws EmptyStackException{
      stackInfo stack=info[stackNum];
      if(stack.isEmpty()){
        throw new EmptyStackException();
      }

      int value=stack.lastElementIndex();
      values[stack.lastElementIndex()] = 0;
      stack.size--;
      return value;
    }

    public int peek(int stackNum) throws EmptyStackException{
        stackInfo stack=info[stackNum];
      if(stack.isEmpty()){
        throw new EmptyStackException();
      }
      return values[stack.lastElementIndex()];
    }
    
    private void shift(int stackNum) {
        System.out.println("/// Shifting " + stackNum);
        
        StackInfo stack = info[stackNum];
        
        // If this stack is at its full capacity, then we need to move the next stack over by one element.
        // This stack can now claim the freed index.
        if (stack.size >= stack.capacity) {
            int nextStack = (stackNum + 1) % info.length;
            shift(nextStack);
            stack.capacity++; // claim index that next stack lost
        }
        
        // Shift all elements in stack over by one.
        int index = stack.lastCapacityIndex();
        while (stack.isWithinStackCapacity(index)) {
            values[index] = values[previousIndex(index)];
            index = previousIndex(index);
        }
        
        // Adjust stack data.
        values[stack.start] = 0; // Clear item
        stack.start = nextIndex(stack.start); // move start
        stack.capacity--; // Shrink capacity
    }


     private void expand(int stackNum) {
        shift((stackNum + 1) % info.length);
        info[stackNum].capacity++;
    }

     public int numberOfElements() {
        int size = 0;
        for (StackInfo sd : info) {
            size += sd.size;
        }
        return size;
    }

     public boolean allStacksAreFull() {
        return numberOfElements() == values.length;
    }

    private int adjustIndex(int index) {
        // Java's mod operator can return neg values. For example, (-11 % 5) will return -1, not 4.
        // We actually want the value to be 4 (since we're wrapping around the index).
        int max = values.length;
        return ((index % max) + max) % max;
    }

    private int nextIndex(int index) {
        return adjustIndex(index + 1);
    }

    private int previousIndex(int index) {
        return adjustIndex(index - 1);
    }
}


 public static void main(String[] args) {
        try {
            MultiStack stacks = new MultiStack(3, 3);
            
            // Stack 0 operations
            stacks.push(0, 10);
            stacks.push(0, 20);
            stacks.push(0, 30);
            System.out.println("Stack 0 peek: " + stacks.peek(0));
            
            // This should cause stack 1 to shift
            stacks.push(0, 40);
            System.out.println("Stack 0 peek after expansion: " + stacks.peek(0));
            
            // Stack 1 operations
            stacks.push(1, 100);
            stacks.push(1, 200);
            System.out.println("Stack 1 peek: " + stacks.peek(1));
            
            // Stack 2 operations
            stacks.push(2, 1000);
            stacks.push(2, 2000);
            System.out.println("Stack 2 peek: " + stacks.peek(2));
            
            // Pop operation
            System.out.println("Popping from stack 0: " + stacks.pop(0));
            System.out.println("Stack 0 peek after pop: " + stacks.peek(0));
            
        } catch (EmptyStackException | FullStackException e) {
            e.printStackTrace();
        }
    }
}