/*Implementation of a Min Heap using an ArrayList*/
import java.util.ArrayList;

public class MinHeap{
    ArrayList<Integer> heap;
    public MinHeap(){
        heap=new ArrayList<>();
    }

    private int parent(int i){
        return (i-1)/2;
    }
    private int leftChild(int i){
        return 2*i+1;
    }
    private int rightChild(int i){
        return 2*i+2;
    }

    public void insert(int value){  //Time Complexity: O(log n)
        heap.add(value);
        int index=heap.size()-1;
        while(index>0 && (heap.get(parent(index)) > heap.get(index))){
            swap(index,parent(index));
            index=parent(index);
        }
    }

    public int extractMin(){ //Time Complexity: O(log n)
        if(heap.isEmpty()) throw new IllegalStateException("Heap is empty");
        int min=heap.get(0);
        int last=heap.remove(heap.size()-1);
        if(!heap.isEmpty()){
            heap.set(0,last);
            heapify(0);
        }
        return min;
    }

    private void heapify(int i){ //Time Complexity: O(log n)
        int smallest=i;
        int left=leftChild(i);
        int right=rightChild(i);

        while(left<heap.size() && heap.get(left)<heap.get(smallest)){
            smallest=left;
        }
        while(right<heap.size() && heap.get(right)<heap.get(smallest)){
            smallest=right;
        }
        if(smallest!=i){
            swap(i,smallest);
            heapify(smallest);
        }
    }

    private void swap(int i, int j){
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    //Efficiently constructs a heap from an existing list of elements using the bottom-up approach
    public void buildHeap(List<Integer> elements) {
       heap = new ArrayList<>(elements);
       for (int i = parent(heap.size() - 1); i >= 0; i--) {
           heapify(i);
      }
    }

    //Decreases the value of a key at a specific index and restores the heap property.
    public void decreaseKey(int index, int newValue) {
       if (index < 0 || index >= heap.size()) throw new IndexOutOfBoundsException();
       if (newValue > heap.get(index)) throw new IllegalArgumentException("New value is greater than current value");
       heap.set(index, newValue);
       while (index > 0 && heap.get(parent(index)) > heap.get(index)) {
           swap(index, parent(index));
           index = parent(index);
        }
    }
    
    //Removes the element at the specified index and maintains the heap property.
    public void delete(int index) {
       if (index < 0 || index >= heap.size()) throw new IndexOutOfBoundsException();
       decreaseKey(index, Integer.MIN_VALUE);
       extractMin();
    }
    
    //Returns a sorted list of elements by repeatedly extracting the minimum.
    public List<Integer> heapSort() {
      List<Integer> sorted = new ArrayList<>();
      while (!heap.isEmpty()) {
          sorted.add(extractMin());
      }
      return sorted;
    }

     // Utility method to print the heap:
     // This method constructs a string by iterating over the list's elements and formatting them in a readable way, 
     // typically as a comma-separated list enclosed in square brackets
    public void printHeap() {
        System.out.println(heap);
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(15);
        minHeap.insert(30);
        minHeap.insert(40);
        minHeap.insert(5);
        minHeap.printHeap(); // Should display the heap in min-heap order
        System.out.println("Extracted Min: " + minHeap.extractMin());
        minHeap.printHeap(); // Heap after extracting the minimum
    }
}