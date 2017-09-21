package ADSHuffman;

import java.util.Arrays;

public class MinBinaryHeap{
	
	// Heap with HuffmanTree nodes
	public HuffmanTreeNode[] heap;
	public int size;
	public int Capacity;
	
	/* Adding Huffman tree nodes one by one and heapify */
	public MinBinaryHeap(HuffmanTreeNode[] nodes) {
		Capacity = 10;
		size = 0;
		heap = new HuffmanTreeNode[Capacity];
		for (int i = 0; i < nodes.length; i++) {
			addItem(nodes[i]);
		}

	}
	
	public int getSize() {
		return size;
	}
	
	/* Getter and setter methods*/
	public int getParentIndex(int ChildIndex){return (ChildIndex-1)/2;}
	public int getLeftChildIndex(int ParentIndex){return (2*ParentIndex+1);}
	public int getRightChildIndex(int ParentIndex){return (2*ParentIndex+2);}
	public boolean hasLeftChild(int ParentIndex){return (getLeftChildIndex(ParentIndex)<size);}
	public boolean hasRightChild(int ParentIndex){return (getRightChildIndex(ParentIndex)<size);}
	public boolean hasParent(int ChildIndex){return (getParentIndex(ChildIndex)>=0);}
	public HuffmanTreeNode getLeftChild(int ParentIndex){return heap[getLeftChildIndex(ParentIndex)];}
	public HuffmanTreeNode getRightChild(int ParentIndex){return heap[getRightChildIndex(ParentIndex)];}
	public HuffmanTreeNode getParent(int ChildIndex){return heap[getParentIndex(ChildIndex)];}
	
	/*Insert item and heapify up*/
	public void addItem(HuffmanTreeNode item){
		checkHeapCapacity();
		heap[size] = item;
		size++;
		heapifyUp();
	}
	
	/* Check heap capacity and double the size if full */
	public void checkHeapCapacity(){
		if(size == Capacity){

			Capacity = Capacity*2;
			heap = Arrays.copyOf(heap,Capacity);}
	}
	
	public HuffmanTreeNode peek(){
		if(size==0){ throw new IllegalStateException();}
		else return heap[0];
	}
	
	/* Remove HuffmanTree node and heapify down*/
	public HuffmanTreeNode removeItem(){
		if(size==0){ throw new IllegalStateException();}
		HuffmanTreeNode item = heap[0];
		heap[0] = heap[size-1];
		size--;
		heapifyDown();
		return item;
	}
	
	
	public void swap(int firstIndex, int secondIndex){
		HuffmanTreeNode temp = heap[firstIndex];
		heap[firstIndex] = heap[secondIndex];
		heap[secondIndex] = temp;
	}
	
	/* Heapify Up by comparing the new element with parent */
	public void heapifyUp(){
		int index = size-1;
		while(hasParent(index) && getParent(index).getFrequency()>heap[index].getFrequency()){
			swap(getParentIndex(index),index);
			index = getParentIndex(index);
		}
	}
	
	/* Heapify Down by comparing the frequency of new element with both left and right children */
	public void heapifyDown(){
		int index = 0;
		while(hasLeftChild(index)){
			int smallerChildIndex = getLeftChildIndex(index);
			if(hasRightChild(index) && getRightChild(index).getFrequency()<heap[smallerChildIndex].getFrequency()){
				smallerChildIndex = getRightChildIndex(index);
			}
			if(heap[index].getFrequency()<heap[smallerChildIndex].getFrequency()){break;}
			else{
				swap(smallerChildIndex,index);
				index = smallerChildIndex;
			}
			}
	}
	
}
