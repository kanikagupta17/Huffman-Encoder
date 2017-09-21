package huffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinFourAryHeap {
	
	private HuffmanTreeNode[] heap;
	private int size;
	private int capacity;
	private static final int TOP = 3;
	//static final String code_table = "";// TO FILL

	public MinFourAryHeap(HuffmanTreeNode[] aheapStorage) {
		this.heap = new HuffmanTreeNode[aheapStorage.length + 3];
		heap[TOP] = null;
		this.size = aheapStorage.length+2;
		capacity = size;
		System.arraycopy(aheapStorage, 0, heap, 3, aheapStorage.length);
		//System.

		buildHeap(size);
	}

	private void buildHeap(int asize) {
		for (int i = getParent(asize); i >= TOP; i--) {
			heapifyUp(i);
		}
	}

	public HuffmanTreeNode extractMin() {
		HuffmanTreeNode result = heap[TOP];

		heap[TOP] = heap[size];
		heap[size] = null;
		size--;
		heapifyUp(TOP);
		return result;
	}

	public void insertItem(HuffmanTreeNode item) {
		if (size < capacity) {
			heap[++size] = item;
			int current = size;
			while (isValid(current) && isValid(getParent(current)) && heap[getParent(current)].getFrequency() > heap[current].getFrequency()) {
				swapTwoValues(getParent(current), current);
				current = current / 4;
			}
		} else {
			System.out.println("cannot insert : no space left");
		}
	}

	private void heapifyUp(int index) {
		int firstChildIdx = getFirstChild(index);
		int secondChildIdx = getSecondChild(index);
		int thirdChildIdx = getThirdChild(index);
		int fourthChildIdx = getFourthChild(index);
		int smallestIdx = firstChildIdx;

		if (isValid(secondChildIdx)) {
			//System.out.println("secondchildindex = " + secondChildIdx);
			if (heap[secondChildIdx].getFrequency() < heap[smallestIdx].getFrequency()) {
				smallestIdx = secondChildIdx;
			}
		}
		if (isValid(thirdChildIdx)) {
			if (heap[thirdChildIdx].getFrequency() < heap[smallestIdx].getFrequency()) {
				smallestIdx = thirdChildIdx;
			}
		}
		
		if (isValid(fourthChildIdx)) {
			if (heap[fourthChildIdx].getFrequency() < heap[smallestIdx].getFrequency()) {
				smallestIdx = fourthChildIdx;
			}
		}

		if (isValid(smallestIdx)&& heap[smallestIdx].getFrequency() < heap[index].getFrequency()) {
			swapTwoValues(smallestIdx, index);
			heapifyUp(smallestIdx);
		}
	}

	private void swapTwoValues(int smallestIdx, int index) {
		HuffmanTreeNode temp = heap[smallestIdx];
		heap[smallestIdx] = heap[index];
		heap[index] = temp;
	}

	private boolean isValid(int index) {
		return index >= TOP && index <= size;
	}

	private int getParent(int index) {
		return (index / 4) +2;
	}

	private int getFirstChild(int index) {
		return (4 * index) -8;
	}
	
	private int getSecondChild(int index) {
		return (4 * index) -7;
	}
	
	private int getThirdChild(int index) {
		return (4 * index) -6 ;
	}
	
	private int getFourthChild(int index) {
		return (4 * index) - 5;
	}

	public int getSize() {
		return size;
	}


}
