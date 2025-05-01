public class FixedStack<T>{
    private int stackSize;
    private int tops[];
    private Object[] arr;
    public FixedStack(int stackSize){
        this.stackSize=stackSize;
        arr=new Object[stackSize*3];
        tops = new int[3];

        // initialize top indices for each stack
        tops[0]=-1;
        tops[1]=-1;
        tops[2]=-1;
    }
    public void push(int stackNum,T element){
        checkStackNumber(stackNum);
        if(isFull(stackNum)){
            throw new RuntimeException("stack "+stackNum+" is full");
        }
        tops[stackNum]++;
        arr[absolutePosition(stackNum)]=element;
    }
    @SuppressWarnings("unchecked")
    public T pop(int stackNum){
        checkStackNumber(stackNum);
        if(isEmpty(stackNum)){
            throw new RuntimeException("stack "+stackNum+" is empty");
        }
        int index=absolutePosition(stackNum);
        T value=(T) arr[index];
        arr[index]=null; //garbage collection
        tops[stackNum]--;
        return value;
    }
 
    @SuppressWarnings("unchecked")
    public T peek(int stackNum){
        checkStackNumber(stackNum);
        if(isEmpty(stackNum)){
            throw new RuntimeException("stack "+stackNum+" is empty");
        }
        int index=absolutePosition(stackNum);
        return (T) arr[index];
    }

    public boolean isEmpty(int stackNum){
        checkStackNumber(stackNum);
        return tops[stackNum]==-1;
    }

    public boolean isFull(int stackNum){
        checkStackNumber(stackNum);
        return tops[stackNum]==stackSize-1;
    }

    private int absolutePosition(int stackNum){
        return stackNum*stackSize+tops[stackNum];
    }

    private void checkStackNumber(int stackNum) {
        if (stackNum < 0 || stackNum > 2) {
            throw new IllegalArgumentException("Invalid stack number: " + stackNum);
        }
    }
}