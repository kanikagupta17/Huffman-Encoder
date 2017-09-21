package huffman;


import java.util.Arrays;

public class PairingHeap {
	
	int size;
	static int capacity = 10;
	PairingHeapNode[] siblingsArray = new PairingHeapNode[capacity]; // For Siblings
	public PairingHeapNode root;
	
	public PairingHeap(){
		
	}
	
	public PairingHeap(HuffmanTreeNode[] nodes){
		root = null;
		size = 0;
		
		for(int i=0;i<nodes.length;i++){
			insertItem(nodes[i]);
		}
		
	}
	
	public boolean isEmpty(){
		
		return root==null;
	}
	
	public int getMin(){
		return root.innerNode.getFrequency();
	}
	
public void insertItem(HuffmanTreeNode node){
		
		PairingHeapNode newInsertedNode = new PairingHeapNode(node);
		
		if(root==null) {
			root = newInsertedNode;
		}
		else{		
			meld(newInsertedNode);
		}
		
		
	}
	
	/*public void insert(int item){
		
		PairingHeapNode newInsertedNode = new PairingHeapNode(item);
		
		if(root==null) {
			root = newInsertedNode;
		}
		else{		
			meld(newInsertedNode);
		}
		
		
	}*/
	
	public HuffmanTreeNode removeMin(){
		
		PairingHeapNode minimumValue = root;
		
		if (isEmpty( ))
            return null;
      
        if (root.child == null){
            root = null;}
        else{
            
        	root=combineSiblings(root.child);
        }
		
		return minimumValue.innerNode;
		
	}
	
	public PairingHeapNode combineSiblings(PairingHeapNode node){
		
		 if( node.rightSibling == null )
	            return node;
	        /* Store the subtrees in an array */
	        int numOfSiblings = 0;
	        for (numOfSiblings=0 ; node != null; numOfSiblings++)
	        {
	        	checkHeapCapacity(numOfSiblings);
	        	siblingsArray[ numOfSiblings ] = node;
	        	
	            /* break links */
	            node.leftSibling.rightSibling = null;  
	            node = node.rightSibling;
	        }
	        checkHeapCapacity( numOfSiblings );
	        siblingsArray[ numOfSiblings ] = null;
	        
	        /* Combine subtrees two at a time, going left to right */
	        int i = 0;
	        for ( i=0; i + 1 < numOfSiblings; i += 2)
	            siblingsArray[ i ] = compareLinkOperation(siblingsArray[i], siblingsArray[i + 1]);
	        int j = i - 2;
	        /* j has the result of last compareAndLink */
	        /* If an odd number of trees, get the last one */
	        if (j == numOfSiblings - 3)
	            siblingsArray[ j ] = compareLinkOperation( siblingsArray[ j ], siblingsArray[ j + 2 ] );
	        /* Now go right to left, merging last tree with */
	        /* next to last. The result becomes the new last */
	        for ( ; j >= 2; j -= 2)
	        	siblingsArray[j - 2] = compareLinkOperation(siblingsArray[j-2], siblingsArray[j]);
	        return siblingsArray[0];		
		
	}
	
	public void checkHeapCapacity(int cap){
		if(capacity == cap){
			capacity = cap*2;
			siblingsArray = Arrays.copyOf(siblingsArray,capacity);
			
		}
	}
		
	
	public void meld(PairingHeapNode node){
		
		if(node!=null){
			root = compareLinkOperation(root,node);
		}
		
	}
	
	
	
	
public PairingHeapNode compareLinkOperation(PairingHeapNode root1, PairingHeapNode root2){
		
		if (root2 == null)
            return root1;
		
        if(root2.innerNode.getFrequency()<root1.innerNode.getFrequency())
        {
            /* Attach first as leftmost child of second */
            root2.leftSibling = root1.leftSibling;
            root1.leftSibling = root2;
            root1.rightSibling = root2.child;
            if (root1.rightSibling != null)
                root1.rightSibling.leftSibling = root1;
            root2.child = root1;
            return root2;
        }
        else
        {
            /* Attach second as leftmost child of first */
            root2.leftSibling = root1;
            root1.rightSibling = root2.rightSibling;
            if (root1.rightSibling != null)
                root1.rightSibling.leftSibling = root1;
            root2.rightSibling = root1.child;
            if (root2.rightSibling != null)
                root2.rightSibling.leftSibling = root2;
            root1.child = root2;
            return root1;
        }
		
		
	}

	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PairingHeap ph = new PairingHeap();
		//PairingHeapNode x = new PairingHeapNode();
		//PairingHeapNode x1 = new PairingHeapNode(new HuffmanTreeNode(12,10));
		//PairingHeapNode x2 = new PairingHeapNode(40);
		//PairingHeapNode x3 = new PairingHeapNode(1);
		ph.insertItem(new HuffmanTreeNode(0,4));
		ph.insertItem(new HuffmanTreeNode(120,0));
		ph.insertItem(new HuffmanTreeNode(12,11));
		ph.insertItem(new HuffmanTreeNode(12,40));
		System.out.println(ph.removeMin());
		System.out.println(ph.root.innerNode.getFrequency());
		ph.removeMin();
		System.out.println(ph.root.innerNode.getFrequency());
		ph.removeMin();
		System.out.println(ph.root.innerNode.getFrequency());
		
	}

}

class PairingHeapNode{
	
	//int data;
	HuffmanTreeNode innerNode;
	PairingHeapNode child;
	PairingHeapNode leftSibling;
	PairingHeapNode rightSibling;
	
	public PairingHeapNode(HuffmanTreeNode node) {
		// TODO Auto-generated constructor stub
		
		this.innerNode = node;
		this.child = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}
	
}
