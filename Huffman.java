package ADSHuffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * @author Kanika Gupta
 *
 */
public class Huffman {

	static final String code_table = "code_table.txt";
	public static HashMap<Integer,String> codeTableHashMap= new HashMap<Integer,String>();
		
	/* Builds Frequency Table From Input File*/
	public HashMap<Integer,Integer> build_freq_table(String input_file) throws FileNotFoundException, IOException{
	
		HashMap<Integer,Integer>freq_table = new HashMap<Integer,Integer>();
		BufferedReader br = new BufferedReader(new FileReader(input_file));
		String line = "";
	
		while((line = br.readLine()) != null && !line.equals("")) {
		
			line = line.trim();
			int var = Integer.parseInt(line);		
			if(freq_table.containsKey(var)){
				freq_table.put(var,freq_table.get(var)+1);
			}
			else{
				freq_table.put(var,1);
			}
		}			
		br.close();
		return freq_table;	
	}
	
	/* Builds Huffman Tree Using Binary Heap */
	public void build_tree_using_binary_heap(HashMap<Integer,Integer> freq_table){
		
		try{
			HuffmanTreeNode treeRoot = null;
			
			HuffmanTreeNode[] nodes = new HuffmanTreeNode[freq_table.size()];
			int i=0;
			for (Map.Entry<Integer,Integer> e : freq_table.entrySet()) {
				nodes[i] = new HuffmanTreeNode(e.getKey(),e.getValue());
				i++;
			}	
			MinBinaryHeap heap = new MinBinaryHeap(nodes);
			while (heap.getSize() != 1) {				
				HuffmanTreeNode left = heap.removeItem();
				HuffmanTreeNode right = heap.removeItem();
				HuffmanTreeNode top = new HuffmanTreeNode(Integer.MAX_VALUE, left.getFrequency()+ right.getFrequency());
				top.setLeft(left);
				top.setRight(right);
				heap.addItem(top);
			}
			treeRoot = heap.removeItem();
			
			BufferedWriter bw =  new BufferedWriter(new FileWriter(code_table));			
			String s = "";
			makeCodeTable(treeRoot, s,bw);					
			bw.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/* Builds Huffman Tree Using Four-Ary Cache Optimized Heap */
	public void build_tree_using_FourAry_heap(HashMap<Integer,Integer> freq_table){
		
		try{
			HuffmanTreeNode treeRoot = null;			
		
			HuffmanTreeNode[] nodes = new HuffmanTreeNode[freq_table.size()];
			int i=0;
			for (Map.Entry<Integer,Integer> e : freq_table.entrySet()) {
				nodes[i] = new HuffmanTreeNode(e.getKey(),e.getValue());
				i++;
			}
			
			MinFourAryHeap mbheap = new MinFourAryHeap(nodes);
			while (mbheap.getSize() != 3) {				
				HuffmanTreeNode left = mbheap.extractMin();
				HuffmanTreeNode right = mbheap.extractMin();
				HuffmanTreeNode top = new HuffmanTreeNode(Integer.MAX_VALUE, left.getFrequency()+ right.getFrequency());
				top.setLeft(left);
				top.setRight(right);
				mbheap.insertItem(top);
			}
			treeRoot = mbheap.extractMin();
			
			BufferedWriter bw =  new BufferedWriter(new FileWriter(code_table));			
			String s = "";
			makeCodeTable(treeRoot, s,bw);		
			bw.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/* Builds Huffman Tree Using Pairing Heap */
	public void build_tree_using_pairing_heap(HashMap<Integer,Integer> freq_table){
				
		try{
			HuffmanTreeNode treeRoot = null;
					
			HuffmanTreeNode[] nodes = new HuffmanTreeNode[freq_table.size()];
			int i=0;
			for (Map.Entry<Integer,Integer> e : freq_table.entrySet()) {
				nodes[i] = new HuffmanTreeNode(e.getKey(),e.getValue());
				i++;
			}
							
			PairingHeap mbheap = new PairingHeap(nodes);
			while (mbheap.root.child!=null) {				
				HuffmanTreeNode left = mbheap.removeMin();
				HuffmanTreeNode right = mbheap.removeMin();
				HuffmanTreeNode top = new HuffmanTreeNode(Integer.MAX_VALUE, left.getFrequency()+ right.getFrequency());
				top.setLeft(left);
				top.setRight(right);
				mbheap.insertItem(top);
			}
			treeRoot = mbheap.removeMin();

			BufferedWriter bw =  new BufferedWriter(new FileWriter(code_table));
			String s = "";
			makeCodeTable(treeRoot, s,bw);
			bw.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	} 
	
	/* Builds Code_Table from Huffman Tree*/
	public void makeCodeTable(HuffmanTreeNode root, String code,BufferedWriter bw) throws IOException {
		
		if (root!=null){
					
			makeCodeTable(root.left, code+"0",bw);
			makeCodeTable(root.right, code+"1",bw);
			
			if(root.left==null && root.right==null){			
				bw.write(root.getData() + " " + code + "\n");
				codeTableHashMap.put(root.getData(), code);
				
			}
		}
	}

}







